package com.torinosrc.model.view.weixin;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel
public class WxPaymentView implements Serializable {
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    private String timeStamp;
    private String nonceStr;
    private String wxPackage;
    private String signType;
    private String paySign;
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getWxPackage() {
        return wxPackage;
    }

    public void setWxPackage(String wxPackage) {
        this.wxPackage = wxPackage;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }
}
