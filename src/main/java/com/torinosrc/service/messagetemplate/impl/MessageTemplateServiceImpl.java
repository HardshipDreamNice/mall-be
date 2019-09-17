package com.torinosrc.service.messagetemplate.impl;

import com.torinosrc.commons.constants.MallConstant;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.DateUtils;
import com.torinosrc.commons.utils.wechat.MiniProgramTemplateData;
import com.torinosrc.commons.utils.wechat.WeChatUtils;
import com.torinosrc.dao.boostteam.BoostTeamDao;
import com.torinosrc.dao.membershipgrade.MembershipGradeDao;
import com.torinosrc.dao.order.OrderDao;
import com.torinosrc.dao.orderdetail.OrderDetailDao;
import com.torinosrc.dao.productdetail.ProductDetailDao;
import com.torinosrc.dao.productdetailprice.ProductDetailPriceDao;
import com.torinosrc.dao.productfreereceivedetail.ProductFreeReceiveDetailDao;
import com.torinosrc.dao.shop.ShopDao;
import com.torinosrc.dao.team.TeamDao;
import com.torinosrc.dao.teamuser.TeamUserDao;
import com.torinosrc.dao.user.UserDao;
import com.torinosrc.dao.weixin.WxRefundDao;
import com.torinosrc.model.entity.boostteam.BoostTeam;
import com.torinosrc.model.entity.membershipgrade.MembershipGrade;
import com.torinosrc.model.entity.order.Order;
import com.torinosrc.model.entity.orderdetail.OrderDetail;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.entity.productdetailprice.ProductDetailPrice;
import com.torinosrc.model.entity.productfreereceive.ProductFreeReceive;
import com.torinosrc.model.entity.productfreereceivedetail.ProductFreeReceiveDetail;
import com.torinosrc.model.entity.shop.Shop;
import com.torinosrc.model.entity.team.Team;
import com.torinosrc.model.entity.teamuser.TeamUser;
import com.torinosrc.model.entity.user.User;
import com.torinosrc.model.entity.weixin.WxRefund;
import com.torinosrc.model.view.order.OrderView;
import com.torinosrc.model.view.teamuser.TeamUserView;
import com.torinosrc.service.accesstoken.AccessTokenService;
import com.torinosrc.service.messagetemplate.MessageTemplateService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author liori
 */
@Service
public class MessageTemplateServiceImpl implements MessageTemplateService {

    private static final Logger LOG = LoggerFactory
            .getLogger(MessageTemplateServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductDetailDao productDetailDao;

    @Autowired
    private ProductFreeReceiveDetailDao productFreeReceiveDetailDao;

    @Autowired
    private BoostTeamDao boostTeamDao;

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private MembershipGradeDao membershipGradeDao;

    @Autowired
    private WxRefundDao wxRefundDao;

    @Autowired
    private ProductDetailPriceDao productDetailPriceDao;

    @Autowired
    private TeamUserDao teamUserDao;

    private final static Integer TEMPLATE_SEND_SUCCESS = 0;

    @Override
    public JSONObject sendShipMessage(OrderView orderView) {

        String templateId = "bVKG__7fSbWV3m249CIaPxTH3IfiX-ZH3MbOA6uiv2Q";
        String prepayId = orderView.getPrepayId();
        Long orderId = orderView.getId();
        Long shopId = orderView.getShopId();
        // 发货时间
        Long deliveryTime = orderView.getUpdateTime();
        // 物流公司
        String logisticsType = orderView.getLogisticsType();
        // 物流单号
        String logisticsNum = orderView.getLogisticsNum();

        // 所需参数是否齐全
        boolean isParamtersComplete = !ObjectUtils.isEmpty(shopId) && StringUtils.isEmpty(logisticsType) && !ObjectUtils.isEmpty(deliveryTime)
                && !StringUtils.isEmpty(logisticsNum) && !StringUtils.isEmpty(prepayId) && !ObjectUtils.isEmpty(orderId);
        if (!isParamtersComplete) {
            throw new TorinoSrcServiceException("推送订单发货通知，所需参数不齐全");
        } else {
            // no need to do anything...
        }

        // set keywords
        List<String> keywordVaules = new LinkedList<>();
        // 快递公司
        keywordVaules.add(logisticsType);
        // 快递单号
        keywordVaules.add(logisticsNum);
        // 发货时间
        String deliveryTimeString = DateUtils.timeStamp2Date(String.valueOf(deliveryTime), "yyyyMMddHHmmss");
        keywordVaules.add(deliveryTimeString);
        // 物品名称
        String productName = getOrderProductName(orderId);
        keywordVaules.add(productName);

        User user = userDao.getOne(orderView.getUserId());
        String openId = user.getOpenId();
        String accessToken = accessTokenService.getAccessToken();
        String page = "pages/index/authorization/authorization?shopId=" + shopId +
                "&redirectUrlId=" + MallConstant.REDIRECT_ID_TO_ORDERDETAIL + "&redirectType=" + MallConstant.REDIRECT_TYPE_NO_TABBAR;

        // 封装请求参数
        JSONObject requestParamsJson = setTemplateRequestJson(accessToken, templateId, prepayId, openId, page, keywordVaules);
        // 开始推送
        String sendResult = WeChatUtils.sendMiniProgramTemplate(accessToken, requestParamsJson);

        LOG.info("到货通知小程序模板消息推送结果：\n" + sendResult);

        JSONObject sendResultJson = JSONObject.fromObject(sendResult);
        // 如果因为token而请求失败，更新token并重新发送；否则抛出异常
        handleResultIfFail(requestParamsJson, sendResultJson);

        return sendResultJson;
    }

    @Override
    public JSONObject sendWaitingForYouToPayMessage(OrderView orderView) {
        String templateId = "li9jhjSZgZDqFbfyO-1ctzltlSzWXczuou9SI2uHI_w";

        // set keywords
        List<String> keywordVaules = new LinkedList<>();
        // 商品名称
        String productName = getOrderProductName(orderView.getId());
        keywordVaules.add(productName);
        // 待支付金额
        keywordVaules.add(String.valueOf(orderView.getTotalFee()));
        // 订单价格
        keywordVaules.add(String.valueOf(orderView.getTotalFee()));
        // 下单时间
        String createOrderTime = DateUtils.timeStamp2Date(String.valueOf(orderView.getCreateTime()), "yyyyMMddHHmmss");
        keywordVaules.add(createOrderTime);
        // 商家名称
        Long shopId = orderView.getShopId();
        Shop shop = shopDao.getOne(shopId);
        keywordVaules.add(shop.getTitle());
        // 温馨提示
        String remark = "点击进入订单详情页完成支付";
        keywordVaules.add(remark);

        User user = userDao.getOne(orderView.getUserId());
        String openId = user.getOpenId();
        String accessToken = accessTokenService.getAccessToken();
        String page = "pages/index/authorization/authorization?shopId=" + shopId +
                "&redirectUrlId=" + MallConstant.REDIRECT_ID_TO_ORDERDETAIL + "&redirectType=" + MallConstant.REDIRECT_TYPE_NO_TABBAR;

        // 封装请求参数
        String prepayId = orderView.getPrepayId();
        JSONObject requestParamsJson = setTemplateRequestJson(accessToken, templateId, prepayId, openId, page, keywordVaules);
        // 开始推送
        String sendResult = WeChatUtils.sendMiniProgramTemplate(accessToken, requestParamsJson);

        LOG.info("待支付通知小程序模板消息推送结果：\n" + sendResult);

        JSONObject sendResultJson = JSONObject.fromObject(sendResult);
        handleResultIfFail(requestParamsJson, sendResultJson);

        return sendResultJson;
    }

    @Override
    public JSONObject sendRefundSuccessMessage(OrderView orderView) {
        String templateId = "qdkWdfcxcaI-CdSgs7ba3FMPSzCbesmE0BZijXNZD00";

        // set keywords
        List<String> keywordVaules = new LinkedList<>();
        // 商品名称
        String productName = getOrderProductName(orderView.getId());
        keywordVaules.add(productName);
        // 订单金额
        keywordVaules.add(String.valueOf(orderView.getTotalFee()));
        // 退款金额
        String transactionId = orderView.getTransactionId();
        WxRefund wxRefund = wxRefundDao.findByTransactionId(transactionId);
        keywordVaules.add(String.valueOf(wxRefund.getRefundFee() / 100));
        // 退款说明
        String refundRemark = "退款已原路返回，具体到账时间可能有1-3天的延迟";
        keywordVaules.add(refundRemark);

        User user = userDao.getOne(orderView.getUserId());
        String openId = user.getOpenId();
        String accessToken = accessTokenService.getAccessToken();
        Long shopId = orderView.getShopId();
        String page = "pages/index/authorization/authorization?shopId=" + shopId +
                "&redirectUrlId=" + MallConstant.REDIRECT_ID_TO_ORDERDETAIL + "&redirectType=" + MallConstant.REDIRECT_TYPE_NO_TABBAR;

        // 封装请求参数
        String prepayId = orderView.getPrepayId();
        JSONObject requestParamsJson = setTemplateRequestJson(accessToken, templateId, prepayId, openId, page, keywordVaules);
        // 开始推送
        String sendResult = WeChatUtils.sendMiniProgramTemplate(accessToken, requestParamsJson);

        LOG.info("退款成功通知小程序模板消息推送结果：\n" + sendResult);

        JSONObject sendResultJson = JSONObject.fromObject(sendResult);
        handleResultIfFail(requestParamsJson, sendResultJson);

        return sendResultJson;
    }

    @Override
    public JSONObject sendWaitGroupCompleteMessage(TeamUserView teamUserView, String formId) {

        String templateId = "zipXBg3cKlv82y6cZ-IonxvNnikYlH5lGV8kpMHrKyI";

        Long teamId = teamUserView.getTeamId();
        Team team = teamDao.getOne(teamId);

        // set keywords
        List<String> keywordVaules = new LinkedList<>();
        // 商品名称
        keywordVaules.add(team.getProductName());
        // 拼团价
        List<Order> orderList = orderDao.findByTeamId(teamId);
        Long orderId = orderList.get(0).getId();
        List<OrderDetail> orderDetails = orderDetailDao.findByOrderId(orderId);
        Long productDetailId = orderDetails.get(0).getProductDetailId();
        ProductDetailPrice productDetailPrices = productDetailPriceDao.findByProductDetailId(productDetailId);
        Long price = productDetailPrices.getTeamPrice();
        keywordVaules.add(String.valueOf(price/100));
        // 开团时间
        String createTime = DateUtils.timeStamp2Date(String.valueOf(team.getCreateTime()), "yyyyMMddHHmmss");
        keywordVaules.add(createTime);
        // 拼团人数
        keywordVaules.add(String.valueOf(team.getCount()));
        // 剩余人数
        Integer joinCount = teamUserDao.countByTeamId(teamId);
        keywordVaules.add(String.valueOf(team.getCount() - joinCount));
        // 拼团结束时间
        String expiredTime = DateUtils.timeStamp2Date(String.valueOf(team.getExpiredTime()), "yyyyMMddHHmmss");
        keywordVaules.add(expiredTime);
        // 商家名称
        Long shopId = orderList.get(0).getShopId();
        Shop shop = shopDao.getOne(shopId);
        keywordVaules.add(shop.getTitle());

        Long userId = teamUserView.getUserId();
        User user = userDao.getOne(userId);
        String openId = user.getOpenId();
        String accessToken = accessTokenService.getAccessToken();
        String page = "pages/index/authorization/authorization?shopId=" + shopId +
                "&redirectUrlId=" + MallConstant.REDIRECT_ID_TO_ORDERDETAIL + "&redirectType=" + MallConstant.REDIRECT_TYPE_NO_TABBAR;

        // 封装请求参数
        JSONObject requestParamsJson = setTemplateRequestJson(accessToken, templateId, formId, openId, page, keywordVaules);
        // 开始推送
        String sendResult = WeChatUtils.sendMiniProgramTemplate(accessToken, requestParamsJson);

        LOG.info("拼团待成团通知小程序模板消息推送结果：\n" + sendResult);

        JSONObject sendResultJson = JSONObject.fromObject(sendResult);
        handleResultIfFail(requestParamsJson, sendResultJson);

        return sendResultJson;
    }

    @Override
    public JSONObject sendGroupSuccessMessage(TeamUserView teamUserView, String formId) {

        String templateId = "Eoo_L5NU2r205ddbOHMK8xRlM26KqV0jD0lC-Gd8xfo";

        Long teamId = teamUserView.getTeamId();
        Team team = teamDao.getOne(teamId);

        // set keywords
        List<String> keywordVaules = new LinkedList<>();
        // 商品名称
        keywordVaules.add(team.getProductName());
        // 拼团价
        List<Order> orderList = orderDao.findByTeamId(teamId);
        Long orderId = orderList.get(0).getId();
        List<OrderDetail> orderDetails = orderDetailDao.findByOrderId(orderId);
        Long productDetailId = orderDetails.get(0).getProductDetailId();
        ProductDetailPrice productDetailPrices = productDetailPriceDao.findByProductDetailId(productDetailId);
        Long price = productDetailPrices.getTeamPrice();
        keywordVaules.add(String.valueOf(price/100));
        // 团长
        StringBuilder teamMembers = new StringBuilder();
        String teamOwner = "";
        List<TeamUser> teamUsers = teamUserDao.findByTeamId(teamId);
        for (TeamUser teamUser : teamUsers) {
            Long userId = teamUser.getUserId();
            User user = userDao.getOne(userId);
            String userNickName = user.getNickName();
            if (teamUser.getType() == 1) {
                teamOwner = userNickName;
            } else {
                teamMembers.append(userNickName);
                teamMembers.append("、");
            }
        }
        keywordVaules.add(teamOwner);
        // 团长成员
        keywordVaules.add(teamMembers.substring(0, teamMembers.length() - 2));
        // 温馨提示
        Integer joinCount = teamUserDao.countByTeamId(teamId);
        keywordVaules.add(String.valueOf(team.getCount() - joinCount));

        Long userId = teamUserView.getUserId();
        User user = userDao.getOne(userId);
        String openId = user.getOpenId();
        String accessToken = accessTokenService.getAccessToken();
        Long shopId = orderList.get(0).getShopId();
        String page = "pages/index/authorization/authorization?shopId=" + shopId +
                "&redirectUrlId=" + MallConstant.REDIRECT_ID_TO_ORDERDETAIL + "&redirectType=" + MallConstant.REDIRECT_TYPE_NO_TABBAR;

        // 封装请求参数
        JSONObject requestParamsJson = setTemplateRequestJson(accessToken, templateId, formId, openId, page, keywordVaules);
        // 开始推送
        String sendResult = WeChatUtils.sendMiniProgramTemplate(accessToken, requestParamsJson);

        LOG.info("拼团成功通知小程序模板消息推送结果：\n" + sendResult);

        JSONObject sendResultJson = JSONObject.fromObject(sendResult);
        handleResultIfFail(requestParamsJson, sendResultJson);

        return sendResultJson;
    }

    @Override
    public JSONObject sendGroupFailMessage(TeamUserView teamUserView, String formId) {

        String templateId = "k4GvQHNTeFHNhqHls5uAwggxBjqC_8U21ojaccs7BJ0";

        Long teamId = teamUserView.getTeamId();
        Team team = teamDao.getOne(teamId);

        // set keywords
        List<String> keywordVaules = new LinkedList<>();
        // 商品名称
        keywordVaules.add(team.getProductName());
        // 拼团价
        List<Order> orderList = orderDao.findByTeamId(teamId);
        Long orderId = orderList.get(0).getId();
        List<OrderDetail> orderDetails = orderDetailDao.findByOrderId(orderId);
        Long productDetailId = orderDetails.get(0).getProductDetailId();
        ProductDetailPrice productDetailPrices = productDetailPriceDao.findByProductDetailId(productDetailId);
        Long price = productDetailPrices.getTeamPrice();
        keywordVaules.add(String.valueOf(price/100));
        // 团长
        StringBuilder teamMembers = new StringBuilder();
        String teamOwner = "";
        List<TeamUser> teamUsers = teamUserDao.findByTeamId(teamId);
        for (TeamUser teamUser : teamUsers) {
            if (teamUser.getType() == 1) {
                Long userId = teamUser.getUserId();
                User user = userDao.getOne(userId);
                String userNickName = user.getNickName();
                teamOwner = userNickName;
                break;
            } else{
                // no need to do anything...
            }
        }
        keywordVaules.add(teamOwner);
        // 参团人数
        keywordVaules.add(teamMembers.substring(0, teamMembers.length() - 2));
        // 已参加人数
        Integer joinCount = teamUserDao.countByTeamId(teamId);
        // 剩余人数
        keywordVaules.add(String.valueOf(team.getCount() - joinCount));
        // 拼团状态

        // 拼团结束时间

        Long userId = teamUserView.getUserId();
        User user = userDao.getOne(userId);
        String openId = user.getOpenId();
        String accessToken = accessTokenService.getAccessToken();
        Long shopId = orderList.get(0).getShopId();
        String page = "pages/index/authorization/authorization?shopId=" + shopId +
                "&redirectUrlId=" + MallConstant.REDIRECT_ID_TO_ORDERDETAIL + "&redirectType=" + MallConstant.REDIRECT_TYPE_NO_TABBAR;

        // 封装请求参数
        JSONObject requestParamsJson = setTemplateRequestJson(accessToken, templateId, formId, openId, page, keywordVaules);
        // 开始推送
        String sendResult = WeChatUtils.sendMiniProgramTemplate(accessToken, requestParamsJson);

        LOG.info("拼团失败通知小程序模板消息推送结果：\n" + sendResult);

        JSONObject sendResultJson = JSONObject.fromObject(sendResult);
        handleResultIfFail(requestParamsJson, sendResultJson);

        return sendResultJson;
    }

    private String getOrderProductName(Long orderId) {
        String productName = "";
        Optional<Order> orderOpt = orderDao.findById(orderId);
        Order order;
        if (!orderOpt.isPresent()) {
            throw new TorinoSrcServiceException("orderId = " + orderId + " 的订单不存在");
        } else {
            order = orderOpt.get();
        }
        List<OrderDetail> orderDetails = orderDetailDao.findByOrderId(orderId);
        OrderDetail orderDetail = orderDetails.get(0);
        Integer orderType = order.getOrderType();
        switch (orderType) {
            case 0:
                // 普通订单
                Long productDetailId = orderDetail.getProductDetailId();
                ProductDetail productDetail = productDetailDao.getOne(productDetailId);
                Product product = productDetail.getProduct();
                productName = product.getName();
                break;
            case 1:
                // 拼团订单
                Long teamId = order.getTeamId();
                Team team = teamDao.getOne(teamId);
                productName = team.getProductName();
                break;
            case 2:
                // 购买会员订单
                Long membershipGradeId = orderDetail.getMembershipGradeId();
                MembershipGrade membershipGrade = membershipGradeDao.getOne(membershipGradeId);
                productName = membershipGrade.getName();
                break;
            case 3:
                // 免费领订单
                Long productFreeReceiveDetailId = orderDetail.getProductFreeReceiveDetailId();
                ProductFreeReceiveDetail productFreeReceiveDetail = productFreeReceiveDetailDao.getOne(productFreeReceiveDetailId);
                ProductFreeReceive productFreeReceive = productFreeReceiveDetail.getProductFreeReceive();
                productName = productFreeReceive.getName();
                break;
            case 4:
                // 助力购订单
                Long boostTeamId = order.getBoostTeamId();
                BoostTeam boostTeam = boostTeamDao.getOne(boostTeamId);
                productName = boostTeam.getProductName();
                break;
            default:
                throw new TorinoSrcServiceException("订单类型异常");
        }

        if (productName.length() > 12) {
            productName = productName.substring(0, 12) + "...";
        } else {
            // no need to do anything...
        }

        return productName;
    }

    private JSONObject setTemplateRequestJson(String accessToken, String templateId, String formId, String openId, String page, List<String> keywordVaules) {
        MiniProgramTemplateData miniProgramTemplateData = new MiniProgramTemplateData();
        miniProgramTemplateData.setAccessToken(accessToken);
        miniProgramTemplateData.setTemplateId(templateId);
        miniProgramTemplateData.setFormId(formId);
        miniProgramTemplateData.setTouser(openId);
        miniProgramTemplateData.setPage(page);
        miniProgramTemplateData.setData(keywordVaules);

        JSONObject requestParmsJson = WeChatUtils.setMiniProgramTemplateData(miniProgramTemplateData);
        return requestParmsJson;
    }

    private void handleResultIfFail(JSONObject requestParamsJson, JSONObject sendResultJson) {
        Integer errcode = (Integer) sendResultJson.get("errcode");
        // 发送模板消息成功时，errcode = 0
        if (!ObjectUtils.isEmpty(errcode) && errcode != TEMPLATE_SEND_SUCCESS.intValue()) {
            handleError(requestParamsJson, sendResultJson);
        } else {
            // no need to do anything...
        }
    }

    private void handleError(JSONObject requestParamsJson, JSONObject sendResultJson) {
        Integer errcode = sendResultJson.getInt("errcode");
        // 如果错误原因是 token 失效或 token 过期，更新 token 并重新推送模板消息
        if (errcode == 40001 || errcode == 42001) {
            String accessTokenNew = accessTokenService.createOrUpdateAccessToken();
            String sendResult = WeChatUtils.sendMiniProgramTemplate(accessTokenNew, requestParamsJson);
            JSONObject resultJson = JSONObject.fromObject(sendResult);
            // 再次推送模板消息仍然失败
            if (!ObjectUtils.isEmpty(resultJson.getInt("errcode") ) && resultJson.getInt("errcode") != TEMPLATE_SEND_SUCCESS.intValue()) {
                throw new TorinoSrcServiceException("模板消息推送失败：" + resultJson.getString("errmsg"));
            } else {
                // no need to dong anything...
            }
        } else {
            // 失败了，但不是 token 问题
            throw new TorinoSrcServiceException("模板消息推送失败：" + sendResultJson.getString("errmsg"));
        }
    }
}
