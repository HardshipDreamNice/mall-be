/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.orderdetail;

import com.torinosrc.model.entity.orderdetail.OrderDetail;
import com.torinosrc.model.entity.productdetailsnapshot.ProductDetailSnapshot;
import com.torinosrc.model.entity.shopproductdetail.ShopProductDetail;
import com.torinosrc.model.entity.shopproductdetailsnapshot.ShopProductDetailSnapshot;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * <b><code>OrderDetailView</code></b>
 * <p/>
 * OrderDetail的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-06 11:31:43.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class OrderDetailView extends OrderDetail implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 是否包含shopProductDetailId
     */
    private boolean fromShopProductDetail;

    private ShopProductDetailSnapshot shopProductDetailSnapshot;

    private ProductDetailSnapshot productDetailSnapshot;

    public boolean isFromShopProductDetail() {
        return fromShopProductDetail;
    }

    public void setFromShopProductDetail(boolean fromShopProductDetail) {
        this.fromShopProductDetail = fromShopProductDetail;
    }

    public ShopProductDetailSnapshot getShopProductDetailSnapshot() {
        return shopProductDetailSnapshot;
    }

    public void setShopProductDetailSnapshot(ShopProductDetailSnapshot shopProductDetailSnapshot) {
        this.shopProductDetailSnapshot = shopProductDetailSnapshot;
    }

    public ProductDetailSnapshot getProductDetailSnapshot() {
        return productDetailSnapshot;
    }

    public void setProductDetailSnapshot(ProductDetailSnapshot productDetailSnapshot) {
        this.productDetailSnapshot = productDetailSnapshot;
    }
}
