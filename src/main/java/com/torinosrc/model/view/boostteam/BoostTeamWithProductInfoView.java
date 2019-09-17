/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.boostteam;

import com.torinosrc.model.entity.boostteam.BoostTeam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <b><code>BoostTeamView</code></b>
 * <p/>
 * BoostTeam的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-12-03 15:14:32.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class BoostTeamWithProductInfoView extends BoostTeam implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    private String boostExpiredTime;

    private String productExpiredTime;

    private Long price;

    private Long discountPrice;

    private Long inventory;

    private String imageUrl;

    private String imageUrl1;

    private String imageUrl2;

    private String smallImageUrl;

    @ApiModelProperty(value = "是否砍价完成 0：否 1：是")
    private Integer status;

    public String getBoostExpiredTime() {
        return boostExpiredTime;
    }

    public void setBoostExpiredTime(String boostExpiredTime) {
        this.boostExpiredTime = boostExpiredTime;
    }

    public String getProductExpiredTime() {
        return productExpiredTime;
    }

    public void setProductExpiredTime(String productExpiredTime) {
        this.productExpiredTime = productExpiredTime;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Long getInventory() {
        return inventory;
    }

    public void setInventory(Long inventory) {
        this.inventory = inventory;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
