/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.shopproduct;

import com.torinosrc.model.entity.shopproduct.ShopProduct;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * <b><code>ShopProductView</code></b>
 * <p/>
 * ShopProduct的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-11 19:22:11.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class ShopProductView extends ShopProduct implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    private Integer priceIntervalUp;

    private Integer priceIntervaDown;

    private Long productDetailId;

    private Integer shopProductDetailPrice;

    public Integer getPriceIntervalUp() {
        return priceIntervalUp;
    }

    public void setPriceIntervalUp(Integer priceIntervalUp) {
        this.priceIntervalUp = priceIntervalUp;
    }

    public Integer getPriceIntervaDown() {
        return priceIntervaDown;
    }

    public void setPriceIntervaDown(Integer priceIntervaDown) {
        this.priceIntervaDown = priceIntervaDown;
    }

    public Long getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(Long productDetailId) {
        this.productDetailId = productDetailId;
    }

    public Integer getShopProductDetailPrice() {
        return shopProductDetailPrice;
    }

    public void setShopProductDetailPrice(Integer shopProductDetailPrice) {
        this.shopProductDetailPrice = shopProductDetailPrice;
    }
}
