package com.torinosrc.model.view.weixin;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel
public class WxOrderView implements Serializable {
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    private String openId;
    private String orderNo;
    private int fee;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}
