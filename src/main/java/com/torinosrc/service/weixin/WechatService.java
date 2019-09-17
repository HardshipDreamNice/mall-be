package com.torinosrc.service.weixin;

import com.torinosrc.model.view.code.GroupCodeView;
import com.torinosrc.model.view.weixin.WxOrderView;
import com.torinosrc.model.view.weixin.WxPaymentView;
import com.torinosrc.model.view.weixin.WxQrCodeView;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface WechatService {

    /**
     * payOrder
     * @param wxOrderView
     * @param userIp
     * @return
     */
    WxPaymentView payOrder(WxOrderView wxOrderView, String userIp);

    /**
     * 请求退款
     * @param orderId
     * @return
     */
    JSONObject applyRefund(Long orderId);

    /**
     * 微信支付回调
     * @param result
     * @return
     */
    String payStatus(String result);

    /**
     * 微信退款回调
     * @param result
     * @return
     */
    String refundStatus(String result);

    /**
     * 获取用户的openId
     * @param code
     * @return
     */
    String getOpenId(String code);

    /**
     * 获取微信小程序二维码
     * @param wxQrCodeView
     * @return
     */
    String getQrCode(WxQrCodeView wxQrCodeView);

    /**
     * 获取拼团分享二维码
     * @param groupCodeView
     * @return
     */
    String getGroupQrCode(GroupCodeView groupCodeView);


    /*
    * 公众号点击导航栏发送二维码图片
    *
    */
     String wxTurnWeChatApplet(String result) throws Exception;
}
