package com.torinosrc.service.weixin.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.torinosrc.commons.constants.MallConstant;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.*;
import com.torinosrc.commons.utils.image.CompoundImageInfo;
import com.torinosrc.commons.utils.image.ImageUtils;
import com.torinosrc.commons.utils.wechat.HttpRequestor;
import com.torinosrc.commons.utils.wechat.MiniProgramTemplateData;
import com.torinosrc.commons.utils.wechat.WeChatUtils;
import com.torinosrc.dao.accesstoken.AccessTokenDao;
import com.torinosrc.dao.order.OrderDao;
import com.torinosrc.dao.product.ProductDao;
import com.torinosrc.dao.productdetail.ProductDetailDao;
import com.torinosrc.dao.shop.ShopDao;
import com.torinosrc.dao.team.TeamDao;
import com.torinosrc.dao.weixin.WxPaymentConfigDao;
import com.torinosrc.dao.weixin.WxRefundDao;
import com.torinosrc.model.entity.accesstoken.AccessToken;
import com.torinosrc.model.entity.order.Order;
import com.torinosrc.model.entity.orderdetail.OrderDetail;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.entity.productsnapshot.ProductSnapshot;
import com.torinosrc.model.entity.shop.Shop;
import com.torinosrc.model.entity.team.Team;
import com.torinosrc.model.entity.weixin.WxPaymentConfig;
import com.torinosrc.model.entity.weixin.WxRefund;
import com.torinosrc.model.view.code.GroupCodeView;
import com.torinosrc.model.view.order.OrderView;
import com.torinosrc.model.view.orderdetail.OrderDetailView;
import com.torinosrc.model.view.user.UserView;
import com.torinosrc.model.view.weixin.WxOrderView;
import com.torinosrc.model.view.weixin.WxPaymentView;
import com.torinosrc.model.view.weixin.WxQrCodeView;
import com.torinosrc.model.view.weixin.WxRefundView;
import com.torinosrc.service.accesstoken.AccessTokenService;
import com.torinosrc.service.order.OrderService;
import com.torinosrc.service.user.UserService;
import com.torinosrc.service.weixin.WechatService;
import com.torinosrc.service.weixin.WxRefundService;
import net.sf.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
@Service
public class WechatServiceImpl implements WechatService {
    private static final Logger LOG = LoggerFactory
            .getLogger(WechatServiceImpl.class);

    @Value("${weixin.appId}")
    private String APP_ID;

    @Value("${weixin.secret}")
    private String SECRET;

    @Value("${weixin.mchId}")
    private String MCH_ID;

    @Value("${weixin.key}")
    private String KEY;

    @Value("${weixin.qrCodePath}")
    private String qrcodePath;

    @Value("${weixin.backgroundPic}")
    private String backgroundPic;

    @Value("${weixin.backgroundGroupPic}")
    private String backgroundGroupPic;

    @Value("${weixin.notifyUrl}")
    private String notifyUrlConfig;

    @Value("${weixin.body}")
    private String contentBody;

    @Value("${weixin.notifyRUrl}")
    private String notifyRefundUrlConfig;

    @Value("${weixin.templateId}")
    private String templateId;

    @Value("${weixin.shopQrCodeBackGroupPic}")
    private String SHOP_QRCODE_BACKGROUP_PIC;

    private final String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    private final String GET_QRCODE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit";

    //永久图片素材的上传路径
    private final String IMAGE_POST_URL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE";

    //二维码跳转页面
    private final String QRCODE_PAGE = "pages/index/authorization/authorization";

    private final String ACCESS_TOKEN = "ACCESS_TOKEN";

    @Autowired
    private WxPaymentConfigDao wxPaymentConfigDao;

    @Autowired
    private WxRefundDao wxRefundDao;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserService userService;

    @Autowired
    private WxRefundService wxRefundService;

    @Autowired
    private ProductDetailDao productDetailDao;

    @Autowired
    private AccessTokenDao accessTokenDao;

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private ProductDao productDao;

    /**
     * 调用支付统一下单api
     * @return
     */
    @Override
    @Transactional(rollbackOn = {Exception.class})
    public WxPaymentView payOrder(WxOrderView wxOrderView, String userIp) {
        // 换行符
        String LINE_SEPARATOR =  System.getProperty("line.separator");

        String appId = APP_ID;
        String mchId = MCH_ID;
        //生成随机字符串
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
        String body = contentBody;
        String outTradeNo = wxOrderView.getOrderNo();
        int totalFee = wxOrderView.getFee();
        String spbillCreateIp = userIp;
        String notifyUrl = notifyUrlConfig;
        String tradeType = "JSAPI";

        Order orderFromDB = orderDao.findByOrderNo(wxOrderView.getOrderNo());
        if (ObjectUtils.isEmpty(orderFromDB)) {
            throw new TorinoSrcServiceException("订单不存在，请重新下单");
        } else {

            if (totalFee != orderFromDB.getTotalFee()) {
                throw new TorinoSrcServiceException("订单价格已更新，请刷新后重新支付");
            }

            Integer lastTotalFee = orderFromDB.getLastTotalFee();

            if (ObjectUtils.isEmpty(lastTotalFee)) {
                lastTotalFee = 0;
            }

            // 已申请支付，且支付状态为未支付以及订单金额发生改动，则重新生成 order_no，并更新 lastTotalFee
            if (!StringUtils.isEmpty(orderFromDB.getPrepayId()) && orderFromDB.getStatus() == 0 && totalFee != lastTotalFee) {
                LOG.info("WechatServiceImple - payOrder 重新生成 order_no，lastTotalFee = " + lastTotalFee);
                String orderNoNew = System.currentTimeMillis() + "" + Math.round(Math.random() * 100000);
                orderFromDB.setOrderNo(orderNoNew);
                orderFromDB.setUpdateTime(System.currentTimeMillis());
                orderFromDB.setLastTotalFee(totalFee);
                orderDao.save(orderFromDB);

                outTradeNo = orderNoNew;
                wxOrderView.setOrderNo(orderNoNew);
            } else {
                // no need to do anything...
            }
        }



        // 生成签名
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("appid", appId);
        parameters.put("body", body);
        parameters.put("mch_id", mchId);
        parameters.put("nonce_str", nonceStr);
        parameters.put("notify_url", notifyUrl);
        parameters.put("openid", wxOrderView.getOpenId());
        parameters.put("out_trade_no", outTradeNo);
        parameters.put("spbill_create_ip", spbillCreateIp);
        parameters.put("total_fee", totalFee);
        parameters.put("trade_type", tradeType);
        String sign = WeChatUtils.createSign(parameters, KEY);
        LOG.info("###############生成下单签名##############" + LINE_SEPARATOR + sign);

        //发起请求
        String xmlParams = "<xml>"
                + "<appid>" + appId + "</appid>"
                + "<body>" + body + "</body>"
                + "<mch_id>" + mchId + "</mch_id>"
                + "<nonce_str>" + nonceStr + "</nonce_str>"
                + "<notify_url>" + notifyUrl + "</notify_url>"
                + "<openid>" + wxOrderView.getOpenId() + "</openid>"
                + "<out_trade_no>" + outTradeNo + "</out_trade_no>"
                + "<spbill_create_ip>" + spbillCreateIp + "</spbill_create_ip>"
                + "<sign>" + sign + "</sign>"
                + "<total_fee>" + totalFee + "</total_fee>"
                + "<trade_type>" + tradeType + "</trade_type>"
                + "</xml>";
        LOG.info("###############发起请求的xml###############" + LINE_SEPARATOR + xmlParams);

        // 微信统一下单接口
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        JSONObject result = HttpRequestor.weChatPayOrderPost(url, xmlParams);
        LOG.info("###############下单结果###############" + LINE_SEPARATOR + result);

        //将下单信息插入数据库
        if ("success".equals(result.getString("status"))) {
            OrderView orderView = orderService.findByOrderNo(wxOrderView.getOrderNo());
            if (ObjectUtils.isEmpty(orderView)) {
                throw new TorinoSrcServiceException("此订单不存在，orderNo:" + wxOrderView.getOrderNo());
            }
            orderView.setPrepayId(result.getString("prepay_id"));
            orderView.setUpdateTime(System.currentTimeMillis());
        } else {
            throw new TorinoSrcServiceException("微信支付下单失败，orderNo：" + wxOrderView.getOrderNo());
        }

        String prepayId = result.getString("prepay_id");

        //支付数据签名
        SortedMap<Object, Object> payParameters = new TreeMap<Object, Object>();
        String payNonceStr = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
        String timeStamp = Long.toString(System.currentTimeMillis() / 1000);
        payParameters.put("appId", appId);
        payParameters.put("nonceStr", payNonceStr);
        payParameters.put("package", "prepay_id=" + prepayId);
        payParameters.put("signType", "MD5");
        payParameters.put("timeStamp", timeStamp);
        // 生成微信支付签名
        String paySign = WeChatUtils.createSign(payParameters, KEY);

        WxPaymentView wxPaymentView = new WxPaymentView();
        wxPaymentView.setNonceStr(payNonceStr);
        wxPaymentView.setPaySign(paySign);
        wxPaymentView.setSignType("MD5");
        wxPaymentView.setTimeStamp(timeStamp);
        wxPaymentView.setWxPackage("prepay_id=" + prepayId);
        wxPaymentView.setAppId(APP_ID);
        //1为已申请支付
        orderDao.updatePayStatusTo1(wxOrderView.getOrderNo());
        orderDao.updatePrePayId(wxOrderView.getOrderNo(), prepayId);

        return wxPaymentView;
    }


    @Override
    @Transactional(rollbackOn = {Exception.class})
    public JSONObject applyRefund(Long orderId) {
        OrderView orderView=new OrderView();
        Order order=orderDao.findById(orderId).get();
        TorinoSrcBeanUtils.copyBean(order,orderView);

        if (ObjectUtils.isEmpty(orderView)) {
            throw new TorinoSrcServiceException("此订单不存在！");
        }
        String notifyUrl = notifyRefundUrlConfig;
        UserView userView = userService.getEntity(orderView.getUserId());
        WxPaymentConfig wxPaymentConfig = wxPaymentConfigDao.getOne(1L);
        //支付订单信息
        String transactionId = orderView.getTransactionId();
        String appid = "";
        String key = "";
        String mchId = "";
        String certPath = "";
        String openid = userView.getOpenId();
        //退款单号
        String outRefundNo;
        //生成随机字符串
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
        String totalFee = Integer.toString(orderView.getTotalFee());
        String refundFee = Integer.toString(orderView.getTotalFee() - orderView.getPostage());

        if (!ObjectUtils.isEmpty(wxPaymentConfig)) {
            appid = wxPaymentConfig.getAppId();
            key = wxPaymentConfig.getWechatKey();
            mchId = wxPaymentConfig.getMchId();
//            certPath = "/www/root/zhaoqingmarathon" + wxPaymentConfig.getSslcertPath();
            certPath = wxPaymentConfig.getSslcertPath();
        }

        WxRefund wxRefund = wxRefundDao.findByTransactionId(transactionId);

        // 生成退款单号，如果退款失败，使用原单号
        WxRefund wxRefund1 = new WxRefund();
        if (!ObjectUtils.isEmpty(wxRefund) && wxRefund.getStatus() == 0) {
            outRefundNo = wxRefund.getOutRefundNo();
            BeanCopier copier = BeanCopier.create(WxRefund.class, WxRefund.class, false);
            copier.copy(wxRefund, wxRefund1, null);
        }
        if (!ObjectUtils.isEmpty(wxRefund) && wxRefund.getStatus() == 1) {
            throw new TorinoSrcServiceException("此订单已经退款，请勿重复操作！");
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime = format.format(System.currentTimeMillis());
            outRefundNo = mchId + currentTime;
            wxRefund1.setCreateTime(System.currentTimeMillis());
            wxRefund1.setOpenId(openid);
            wxRefund1.setRefundFee(orderView.getTotalFee() - orderView.getPostage());
            wxRefund1.setOutRefundNo(outRefundNo);
            wxRefund1.setTransactionId(transactionId);

            orderView.setStatus(7);
            orderView.setUpdateTime(System.currentTimeMillis());
        }

        //生成签名
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("mch_id", mchId);
        parameters.put("appid", appid);
        parameters.put("nonce_str", nonceStr);
        parameters.put("transaction_id", transactionId);
        parameters.put("out_refund_no", outRefundNo);
        parameters.put("total_fee", totalFee);
        parameters.put("refund_fee", refundFee);
        parameters.put("notify_url", notifyUrl);
        String sign = WeChatUtils.createSign(parameters, key);
        LOG.info("###############生成退款签名##############" + sign);

        //发起请求
        String xmlParams = "<xml>"
                + "<appid>" + appid + "</appid>"
                + "<mch_id>" + mchId + "</mch_id>"
                + "<nonce_str>" + nonceStr + "</nonce_str>"
                + "<transaction_id>" + transactionId + "</transaction_id>"
                + "<out_refund_no>" + outRefundNo + "</out_refund_no>"
                + "<refund_fee>" + refundFee + "</refund_fee>"
                + "<total_fee>" + totalFee + "</total_fee>"
                + "<notify_url>" + notifyUrl + "</notify_url>"
                + "<sign>" + sign + "</sign>"
                + "</xml>";

        JSONObject result = HttpPostUtils.weChatRefundPost(xmlParams, mchId, certPath);
        // 将退款信息插入数据库
        if ("success".equals(result.getString("status"))) {
            wxRefund1.setStatus(1);
            wxRefund1.setUpdateTime(System.currentTimeMillis());
            //更新订单状态
            orderService.updateEntity(orderView);
        } else {
            wxRefund1.setStatus(0);
            wxRefund1.setUpdateTime(System.currentTimeMillis());
        }
        LOG.info("###############退款结果###############" + result);
        wxRefundDao.save(wxRefund1);
        return result;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public String payStatus(String result) {

        //将xml转为json
        org.json.JSONObject xmlJSONObj = XML.toJSONObject(result);
        String unToXmlResult = result;
        String wxResult = XML.toString(result);

        LOG.info("微信的回调来啦XML: \n" + wxResult);
        String confirmResult = "<xml>" +
                "<return_code><![CDATA[SUCCESS]]></return_code>" +
                "<return_msg><![CDATA[OK]]></return_msg>" +
                "</xml>";

        /*  微信官方提醒：
         *  商户系统对于支付结果通知的内容一定要做【签名验证】,
         *  并校验返回的【订单金额是否与商户侧的订单金额】一致，
         *  防止数据泄漏导致出现“假通知”，造成资金损失。
         */
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        TorinoSrcCommonUtils.analysisJson(xmlJSONObj, parameters);
        String payCallbackSign = WeChatUtils.createSign(parameters, KEY);
        xmlJSONObj = xmlJSONObj.getJSONObject("xml");
        String wxPayCallbackSign = xmlJSONObj.getString("sign");
        String orderNo = xmlJSONObj.getString("out_trade_no");
        if (!wxPayCallbackSign.equals(payCallbackSign)) {
            confirmResult = "<xml>" +
                    "<return_code><![CDATA[FAIL]]></return_code>" +
                    "<return_msg><![CDATA[签名失败]]></return_msg>" +
                    "</xml>";
            LOG.error("签名失败，orderNo：" + orderNo);
            return confirmResult;
        }

        if ("SUCCESS".equals(xmlJSONObj.getString("return_code"))) {
            String transactionId = xmlJSONObj.getString("transaction_id");
            int totalFee = xmlJSONObj.getInt("total_fee");
            String timeEnd = xmlJSONObj.getString("time_end");
            OrderView orderView = orderService.findByOrderNo(orderNo);

            if (ObjectUtils.isEmpty(orderView)) {
                LOG.error("微信支付结果通知失败，订单不存在：" + orderNo);
                throw new TorinoSrcServiceException("微信支付结果通知失败，订单不存在：" + orderNo);
            }

            if (totalFee != orderView.getTotalFee()) {
                confirmResult = "<xml>" +
                        "<return_code><![CDATA[FAIL]]></return_code>" +
                        "<return_msg><![CDATA[参数格式校验错误]]></return_msg>" +
                        "</xml>";
                LOG.warn("支付金额不匹配，orderNo：" + orderNo + ";微信回调结果：" + wxResult);
                return confirmResult;
            }

            // 未接收过微信通知时执行更新操作 if(orderView.getPayStatus()!=1 || orderView.getPayStatus()!=3)
            if (orderView.getPayStatus() == 1 || orderView.getPayStatus() != 3) {
                //更新支付状态
                orderView.setTransactionId(transactionId);
                if ("SUCCESS".equals(xmlJSONObj.getString("result_code"))) {
                    orderView.setStatus(1);
                    orderView.setPayStatus(3);

                    // 是否拼团类型订单
                    Long teamId = 0L;
                    if (orderView.getOrderType() == MallConstant.PRODUCT_TYPE_TEAM) {
                        teamId = this.saveTeamAndTeamUser(orderView);
                    } else {
                        teamId = 0L;
                    }
                    orderView.setTeamId(teamId);

                    // 订单是购买会员的订单，则更新店铺等级
                    if (orderView.getOrderType().intValue() == MallConstant.ORDER_TYPE_MEMBER.intValue()) {
                        LOG.info("订单是购买会员的订单");
                        orderService.upgradeShop(orderView);
                    } else {
                        LOG.info("订单不是购买会员的订单，开始推送小程序消息模板");
                        // 推送小程序消息模板
                        orderView.setPayTimeEnd(timeEnd);
                        this.sendMiniProgramTemplate(orderView);
                    }

                } else if ("FAIL".equals(xmlJSONObj.getString("result_code"))) {
                    orderView.setPayStatus(4);
                }
                orderView.setUpdateTime(System.currentTimeMillis());
                orderView.setWxResult(unToXmlResult);
                orderService.updateEntity(orderView);
            }
            //微信确认支付成功计算分销商利润
//            shopAccountDetailService.calculateOrderProfit(orderView);
        } else {
            LOG.error("微信支付失败");
        }

        LOG.info("回调成功了");
        return confirmResult;

    }

    private void sendMiniProgramTemplate(OrderView orderView) {
        String accessToken = accessTokenService.getAccessToken();

        // 设置推送消息模板需要的参数
        JSONObject resultJson = setMiniProgramTemplateParams(orderView, accessToken);

        // 开始推送模板消息
        String sendTemplateResult = WeChatUtils.sendMiniProgramTemplate(accessToken, resultJson);
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(sendTemplateResult);

        Integer errcode = jsonObject.getInteger("errcode");
        // 如果错误原因是 token 失效或 token 过期，更新 token 并重新推送模板消息
        if (errcode == 40001 || errcode == 42001) {
            String accessTokenNew = accessTokenService.createOrUpdateAccessToken();
            // 重试一次
            WeChatUtils.sendMiniProgramTemplate(accessTokenNew, resultJson);
        }
    }

    /**
     * 设置小程序消息模板需要的参数
     * @param orderView
     * @return
     */
    private JSONObject setMiniProgramTemplateParams(OrderView orderView, String accessToken) {
        Long shopId = orderView.getShopId();
        String prepayId = orderView.getPrepayId();
        String page = "pages/index/authorization/authorization?shopId=" + shopId +
                "&redirectUrlId=" + MallConstant.REDIRECT_ID_TO_ORDERDETAIL + "&redirectType=" + MallConstant.REDIRECT_TYPE_NO_TABBAR;

        Long userId = orderView.getUserId();
        UserView userView = userService.getEntity(userId);
        String openId = userView.getOpenId();

        // set keywords
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        List<String> keywordVaules = new LinkedList<>();
        Shop shop = shopDao.getOne(shopId);
        // 商家名称
        keywordVaules.add(shop.getTitle());
        // 订单编号
        keywordVaules.add(orderView.getOrderNo());
        // 订单内容
        String orderContent = "";
        OrderView orderViewWithProduct = orderService.getEntity(orderView.getId());
//        OrderView orderViewWithProduct = orderDao.getOne(orderView.getId());
        OrderDetailView orderDetailView = orderViewWithProduct.getOrderDetailViews().get(0);
        ProductSnapshot productSnapshot = orderDetailView.getProductDetailSnapshot().getProductSnapshot();
        String productName = productSnapshot.getName();
        if (productName.length() > 12) {
            orderContent = productName.substring(0, 12) + "...";
        } else {
            orderContent = productName;
        }

        keywordVaules.add(orderContent);
        // 下单时间
        keywordVaules.add(simpleDateFormat.format(new Date(orderView.getCreateTime())));
        // 支付时间
        keywordVaules.add(orderView.getPayTimeEnd());
        // 订单金额
        keywordVaules.add(String.valueOf((double) orderView.getTotalFee() / 100));
        // 订单状态
        int status = orderView.getStatus();
        String orderStatus = getOrderStautsName(status);
        keywordVaules.add(orderStatus);
        // 温馨提示
        String remark = "欢迎下次再来购买哟~";
        keywordVaules.add(remark);


        MiniProgramTemplateData miniProgramTemplateData = new MiniProgramTemplateData();
        miniProgramTemplateData.setAccessToken(accessToken);
        miniProgramTemplateData.setTouser(openId);
        miniProgramTemplateData.setTemplateId(templateId);
        miniProgramTemplateData.setPage(page);
        miniProgramTemplateData.setFormId(prepayId);
        miniProgramTemplateData.setData(keywordVaules);

        JSONObject resultJson = WeChatUtils.setMiniProgramTemplateData(miniProgramTemplateData);
        return resultJson;
    }

    private String getOrderStautsName(int status) {
        String orderStatus = "";
        // 状态，0为待支付，1为待发货，2为待收货，3为已收货，4为退款退货中，5为交易成功，6为交易关闭,7为退款成功,8为退款关闭
        switch (status) {
            case 0:
                orderStatus = "待支付";
                break;
            case 1:
                orderStatus = "待发货";
                break;
            case 2:
                orderStatus = "待收货";
                break;
            case 3:
                orderStatus = "已收货";
                break;
            case 4:
                orderStatus = "退款退货中";
                break;
            case 5:
                orderStatus = "交易成功";
                break;
            case 6:
                orderStatus = "交易关闭";
                break;
            case 7:
                orderStatus = "退款成功";
                break;
            case 8:
                orderStatus = "退款关闭";
                break;
            default:
                break;
        }
        return orderStatus;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Long saveTeamAndTeamUser(OrderView orderView) {
        Long teamId = 0L;

        Long userId = orderView.getUserId();

        Set<OrderDetail> orderDetails = orderView.getOrderDetails();
        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>(orderDetails);
        OrderDetail orderDetail = orderDetailList.get(0);
        Long productDetailId = orderDetail.getProductDetailId();
        ProductDetail productDetail = productDetailDao.findById(productDetailId).get();
        Long productId = productDetail.getProduct().getId();

        if (orderView.getTeamId() != 0) {
            // 加入拼团

            teamId = orderView.getTeamId();

            orderService.saveTeamUser(teamId, userId, productId);

        } else {
            //创建拼团

            teamId = orderService.saveTeam(productId, userId);

        }
        return teamId;
    }

    @Override
    public String refundStatus(String result) {
        LOG.info("微信退款回调来啦" + result);
        //将xml转为json
        org.json.JSONObject xmlJSONObj = XML.toJSONObject(result);
        String wxResult = XML.toString(result);
        String confirmResult = "<xml>\n" +
                "\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";

        /*  （1）对加密串A做base64解码，得到加密串B
            （2）对商户key做md5，得到32位小写key* ( key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置 )
            （3）用key*对加密串B做AES-256-ECB解密（PKCS7Padding）*/
        xmlJSONObj = xmlJSONObj.getJSONObject("xml");
        if ("SUCCESS".equals(xmlJSONObj.getString("return_code"))) {
            String reqInfo = xmlJSONObj.getString("req_info");
            String appId = xmlJSONObj.getString("appid");
            String mchId = xmlJSONObj.getString("mch_id");
            if (!(APP_ID.equals(appId) && MCH_ID.equals(mchId))) {
                confirmResult = "<xml>\n" +
                        "\n" +
                        "  <return_code><![CDATA[FAIL]]></return_code>\n" +
                        "  <return_msg><![CDATA[参数格式校验错误]]></return_msg>\n" +
                        "</xml>";
                LOG.warn("参数格式校验错误");
                return confirmResult;
            }

            SecretKeySpec key = new SecretKeySpec(MD5By32Utils.MD5Encode(KEY).toLowerCase().getBytes(), AESUtil.ALGORITHM);
            try {
                String decodeReqInfo = AESUtil.decryptData(reqInfo, key);
                //将xml转为json
                org.json.JSONObject reqInfoJson = XML.toJSONObject(decodeReqInfo);
                reqInfoJson = reqInfoJson.getJSONObject("root");
                String transactionId = reqInfoJson.getString("transaction_id");
                String wxRefundId = reqInfoJson.getString("refund_id");
                int refundfee = Integer.parseInt(reqInfoJson.getString("refund_fee"));
                int wxRefundFee = Integer.parseInt(reqInfoJson.getString("settlement_refund_fee"));
                String wxRefundStatus = reqInfoJson.getString("refund_status");
                String wxSuccessTime = reqInfoJson.getString("success_time");
                String orderNo = reqInfoJson.getString("out_trade_no");
                WxRefundView wxRefundView = wxRefundService.findByTransactionId(transactionId);
                if (ObjectUtils.isEmpty(wxRefundView)) {
                    LOG.error("微信退款结果通知失败，微信订单号不存在：" + transactionId);
                    throw new TorinoSrcServiceException("微信支付结果通知失败，微信订单号不存在：" + transactionId);
                }

                if (refundfee != wxRefundView.getRefundFee()) {
                    confirmResult = "<xml>\n" +
                            "\n" +
                            "  <return_code><![CDATA[FAIL]]></return_code>\n" +
                            "  <return_msg><![CDATA[参数格式校验错误]]></return_msg>\n" +
                            "</xml>";
                    LOG.warn("退款金额不匹配，transactionId：" + transactionId + ";微信回调结果：" + decodeReqInfo);
                    return confirmResult;
                }

                //未接收过微信通知时执行更新操作
                if (wxRefundView.getStatus() != 2 || wxRefundView.getStatus() != 3 || wxRefundView.getStatus() != 4) {
                    wxRefundView.setWxRefundId(wxRefundId);
                    wxRefundView.setWxRefundFee(wxRefundFee);
                    wxRefundView.setWxResultDetail(decodeReqInfo);
                    wxRefundView.setWxResult(wxResult);
                    wxRefundView.setUpdateTime(System.currentTimeMillis());
                    wxRefundView.setWxSuccessTime(wxSuccessTime);
                    if (wxRefundStatus.equals("SUCCESS")) {
                        wxRefundView.setStatus(2);
                        //更新订单退款状态
                        OrderView orderView = orderService.findByOrderNo(orderNo);
                        orderView.setStatus(7);
                        orderView.setUpdateTime(System.currentTimeMillis());
                        orderService.updateEntity(orderView);
                        orderService.refundThenAddInventory(orderView);
                    } else if (wxRefundStatus.equals("CHANGE")) {
                        wxRefundView.setStatus(3);
                    } else if (wxRefundStatus.equals("REFUNDCLOSE")) {
                        wxRefundView.setStatus(4);
                    }
                    wxRefundService.updateEntity(wxRefundView);

                }

            } catch (Exception e) {
                LOG.error("Fail to take action in refund status by wx call", e);
                throw new TorinoSrcServiceException("Fail to take action in refund status by wx call", e);
            }

        } else {
            LOG.error("微信退款失败");
        }

        return confirmResult;

    }

    ///**
    // * 微信获取用户openid
    // *
    // * @param code
    // * @return
    // */
    //@Override
    //public String getOpenId(String code) {
    //
    //    String openId = WeChatUtils.getOpenId(code, APP_ID, SECRET);
    //    return openId;
    //
    //    try {
    //        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APP_ID + "&secret=" + SECRET + "&js_code=" + code + "&grant_type=authorization_code";
    //        //第一次请求 获取access_token 和 openid
    //        String result = new HttpRequestor().doGet(requestUrl);
    //        ObjectMapper objectMapper = new ObjectMapper();
    //        Map map = objectMapper.readValue(result, Map.class);
    //        String openId = (String) map.get("openid");
    //        return openId;
    //    } catch (Exception e1) {
    //        LOG.error(e1.getMessage());
    //        throw new TorinoSrcServiceException("获取openId失败");
    //    }
    //}

    /**
     * 微信获取用户openid
     *
     * @param code
     * @return
     */
    @Override
    public String getOpenId(String code) {
        try {
            String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APP_ID + "&secret=" + SECRET + "&js_code=" + code + "&grant_type=authorization_code";
            //第一次请求 获取access_token 和 openid
//            System.out.println(LocalTime.now() + " TEST:requestUrl:  " + requestUrl);
            String result = new HttpRequestor().doGet(requestUrl);
//            System.out.println("result"+result);
            ObjectMapper objectMapper = new ObjectMapper();
            Map map = objectMapper.readValue(result, Map.class);
            String openId = (String) map.get("openid");
            LOG.info("=============openId=====" + openId);
            // TODO:本地测试
            return openId;
        } catch (Exception e1) {
            LOG.error(e1.getMessage());
            throw new TorinoSrcServiceException("获取openId失败");
        }
    }

    // 邀请开店二维码
    @Override
    public String getQrCode(WxQrCodeView wxQrCodeView) {
        Long shopId = wxQrCodeView.getShopId();
        Long productId = wxQrCodeView.getProductId();
        Long redirectUtlId = wxQrCodeView.getRedirectUtlId();
        String distribution = wxQrCodeView.getDistribution();
        Long userId = wxQrCodeView.getUserId();

        String accessToken = accessTokenService.getAccessToken();

        String getQrCodeUrl = GET_QRCODE_URL + "?access_token=" + accessToken;
        JSONObject getQrCodeParams = new JSONObject();
        //携带在二维码中的参数，进行BASE64处理
        String params = shopId + "&" + productId + "&" + redirectUtlId + "&" + distribution;
        getQrCodeParams.put("scene", params);
        getQrCodeParams.put("page", QRCODE_PAGE);
        getQrCodeParams.put("width", 430);
        getQrCodeParams.put("is_hyaline", true);


        String fileName = "/qrcode/" + userId + ".png";
        File file = new File(qrcodePath + fileName);
        // 返回给前端的相对路径
        String qrCodePath = "";
        if (file.exists() && file.length() > 1000) {
            qrCodePath = fileName;
            LOG.info("不重新生成小程序二维码");
        } else {
            qrCodePath = HttpPostUtils.getQrCode(getQrCodeParams, getQrCodeUrl, qrcodePath, fileName);
            LOG.info("重新生成小程序二维码");
        }

        // 背景图
        String backgroundPic = qrcodePath + "/bpic/" + this.backgroundPic;
        // 二维码图
        String codePic = qrcodePath + fileName;
        // 生成图片的类型
        String picType = "jpg";
        // 生成的图片的路径
        String outFileName = "/outimages/" + userId + "." + picType;
        String outPic = qrcodePath + outFileName;

        // 新建输出文件的文件夹
        File file1 = new File(qrcodePath + "/outimages");
        if (!file1.exists()) {
            file1.mkdir();
        }

        File file2 = new File(outPic);

        if (file2.exists()) {
            LOG.info("不重新生成小程序二维码合成图");
        } else {
            makePicture.ImgYin(backgroundPic, codePic, outPic, picType);
            LOG.info("重新生成小程序二维码合成图");
        }


        return outFileName;
    }


    // 邀请拼团二维码
    @Override
    public String getGroupQrCode(GroupCodeView groupCodeView) {

//        String currentName=System.currentTimeMillis()+""+Math.round(Math.random()*100000);

        Long redirectUtlId = groupCodeView.getRedirectUtlId();
        Long teamId = groupCodeView.getTeamId();
//        Long userId = groupCodeView.getUserId();

        AccessToken accessTokenEntity = accessTokenDao.findByName("ACCESS_TOKEN");
        String accessToken = accessTokenEntity.getValue();

        String getQrCodeUrl = GET_QRCODE_URL + "?access_token=" + accessToken;

        JSONObject getQrCodeParams = new JSONObject();
        //携带在二维码中的参数，进行BASE64处理

        String params = "&" + "&" + redirectUtlId + "&" + "&" + teamId;
        getQrCodeParams.put("scene", params);
        getQrCodeParams.put("page", QRCODE_PAGE);
        getQrCodeParams.put("width", 430);
        getQrCodeParams.put("is_hyaline", true);

        String fileName = "/groupqrcode/" + teamId + ".jpg";
        File file = new File(qrcodePath + fileName);
        // 返回给前端的相对路径
        String qrCodePath = "";
        if (file.exists() && file.length() > 1000) {
            qrCodePath = fileName;
            LOG.info("不重新生成小程序二维码");
        } else {
            // 返回给前端的相对路径
            qrCodePath = HttpPostUtils.getQrCode(getQrCodeParams, getQrCodeUrl, qrcodePath, fileName);
            LOG.info("重新生成小程序二维码");
        }

        String outFileName;

        // 背景图
        String backgroundPic = qrcodePath + "/groupbpic/" + backgroundGroupPic;
        // 二维码图
        String codePic = qrcodePath + fileName;
        // 生成图片的类型
        String picType = "jpg";
        // 生成的图片的路径
        outFileName = "/groupoutimages/" + teamId + "." + picType;
        String outPic = qrcodePath + outFileName;

        // 新建输出文件的文件夹
        File file1 = new File(qrcodePath + "/groupoutimages");
        if (!file1.exists()) {
            file1.mkdir();
        }

        File file2 = new File(outPic);


        Team team=teamDao.findById(teamId).get();
        Long productId=team.getProductId();
        Product product=productDao.findById(productId).get();
//        String productUrl=qrcodePath+product.getTeamImageUrl();
        String productUrl=qrcodePath+ product.getSmallImageUrl();

        // todo
        String yyyyMMdd = product.getExpiredTime().substring(0,10);
        String[] ymd = yyyyMMdd.split("-");

        String timeString="此二维码有效期至"+ymd[0]+"年"+ymd[1]+"月"+ymd[2]+"日";

        if (file2.exists()) {
            LOG.info("不重新生成小程序二维码合成图");
        } else {
//            makePicture.ImgYin(backgroundPic, codePic, outPic, picType);
            // TODO: 待优化 原先的方法中，附加图片的位置和大小都是写死的，为了快直接copy了一份然后改了参数
            makePicture.ImgYinPinTuan(backgroundPic, codePic,productUrl, outPic, picType,timeString);
            LOG.info("重新生成小程序二维码合成图");
        }


        return outFileName;

    }


    //xujiahao20181205
    //公众号点击导航栏发送二维码图片
    @Override
    public String wxTurnWeChatApplet(String result) throws Exception {
        //将xml转换成json
        org.json.JSONObject reqInfoJson = XML.toJSONObject(result);
        reqInfoJson = reqInfoJson.getJSONObject("root");
        String toUserName = reqInfoJson.getString("ToUserName");
        String fromUserName = reqInfoJson.getString("FromUserName");

        //////////////////生成二维码图片
        String accessToken = accessTokenService.getAccessToken();
        String getQrCodeUrl = GET_QRCODE_URL + "?access_token=" + accessToken;

        //生成二维码后的路径
        String outputPath = qrcodePath + "/outImages/";
        //生成二维码的名字
        String fileName = 1111 + ".jpg";
        String preShopQrCodePath = qrcodePath + "/shopqrcode/";
        String shopQrCodePath = preShopQrCodePath + fileName;
        File file = new File(shopQrCodePath);
        // 返回给前端的相对路径
        String shopQrCodePathReturn = "";
        if (file.exists() && file.length() > 1000) {
            shopQrCodePathReturn = shopQrCodePath;
            System.out.println("不重新生成小程序二维码: " + shopQrCodePathReturn);
        } else {
            // 返回给前端的相对路径
            shopQrCodePathReturn = "E:\\www\\root\\mall_160\\public\\twoImage\\1.png";
            System.out.println("重新生成小程序二维码: " + shopQrCodePathReturn);
        }
        //背景图
        String backgroundPic = qrcodePath + "/shopbpic/" + SHOP_QRCODE_BACKGROUP_PIC;
        //图片信息
        List<CompoundImageInfo> compoundImageInfoList = Arrays.asList(new CompoundImageInfo(shopQrCodePathReturn, 138, 445, 144, 144));
        String resultImageFileName = ImageUtils.compoundPirtureAndFont(backgroundPic, outputPath, fileName, new ArrayList<>(), compoundImageInfoList);
        LOG.info("生成小程序转跳二维码合成图成功");

        ////////////////////////////将图片的素材上传到远程
        //本地合成的二维码合成图片地址
        String urlPath="https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3204965147,1604536632&fm=15&gp=0.jpg";
        //上传类型
        String image="image";
        String mediaId=this.getMediaIdFromUrl(urlPath,image);

        //将合成的二维码使用被动信息回复给微信公众号
       // 获取发送方账号
//        String toUserName = reqInfoJson.getString("ToUserName");
       //接收方账号(开发者微信号)
//        String fromUserName = reqInfoJson.getString("FromUserName");
        //消息类型
        String msgType ="image";
        //远程素材地址id
        // String mediaId=this.getMediaIdFromUrl(urlPath,image);
//      System.out.println("发送方账号:" + toUserName + ",接收方账号(开发者微信号):" + fromUserName + ",消息类型:" + msgType + ",远程素材地址:" + mediaId);
    //回复消息
        //根据开发文档要求构造XML字符串，本文为了让流程更加清晰，直接拼接
        //这里在开发的时候可以优化，将回复的文本内容构造成一个java类
        //然后使用XStream(com.thoughtworks.xstream.XStream)将java类转换成XML字符串，后面将会使用这个方法。
        //而且由于参数中没有任何特殊字符，为简单起见，没有添加<![CDATA[xxxxx]]>
        String xmlParams = "<xml>"
                + "<ToUserName>< ![CDATA["+toUserName+"] ]></ToUserName>"
                + "<FromUserName>< ![CDATA["+fromUserName+"] ]></FromUserName>"
                + "<CreateTime>< ![CDATA[" + System.currentTimeMillis()+"] ]></CreateTime>"
                + "<MsgType><![CDATA["+msgType+"]]></MsgType>"
                + "<Image>" + "<MediaId>< ![CDATA["+mediaId+"] ]></MediaId>" + "</Image>"
                + "</xml>";

        return xmlParams;
    }
    /**
     * 公众号登录转跳到小程序二维码图片上传到微信服务器方法
     * 将公众号的临时素材上传到服务器方法。
     */
    public String getMediaIdFromUrl(String urlPath, String fileType) throws Exception {
        String result = null;
        String accessToken = accessTokenService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+accessToken+"&type=" + fileType;
        String fileName = urlPath.substring(urlPath.lastIndexOf("/") + 1);
        // 获取网络图片
        URL mediaUrl = new URL(urlPath);
        HttpURLConnection meidaConn = (HttpURLConnection) mediaUrl.openConnection();
        meidaConn.setDoOutput(true);
        meidaConn.setRequestMethod("GET");

        /**
         * 第一部分
         */
        URL urlObj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + fileName + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);
        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(meidaConn.getInputStream());
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        meidaConn.disconnect();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            InputStream inputStream = con.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
//            log.info("发送POST请求出现异常！ {}", e);
            e.printStackTrace();
            throw new IOException("数据读取异常");
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        System.out.println("resoufasdff "+result);
        JSONObject jsonObject = JSONObject.fromObject(result);
        System.out.println(jsonObject.getString("media_id"));
        return jsonObject.getString("media_id");
    }
}
