package com.torinosrc.model.view.weixin;

import io.swagger.annotations.ApiModelProperty;

public class WxQrCodeView {

    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    @ApiModelProperty(value = "S或PshopProductId或productId")
    private Long productId;

    @ApiModelProperty(value = "目标Url id|")
    private Long redirectUtlId;

    @ApiModelProperty(value = "是否开店，Y为是，N为否")
    private String distribution;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getRedirectUtlId() {
        return redirectUtlId;
    }

    public void setRedirectUtlId(Long redirectUtlId) {
        this.redirectUtlId = redirectUtlId;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }
}
