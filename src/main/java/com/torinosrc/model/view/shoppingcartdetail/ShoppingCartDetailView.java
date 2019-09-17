/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.shoppingcartdetail;

import com.torinosrc.model.entity.shoppingcartdetail.ShoppingCartDetail;
import com.torinosrc.model.entity.shopproductdetail.ShopProductDetail;
import com.torinosrc.model.view.productdetail.ProductDetailView;
import com.torinosrc.model.view.shopproductdetail.ShopProductDetailView;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>ShopCartDetailView</code></b>
 * <p/>
 * ShopCartDetail的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-13 21:17:59.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class ShoppingCartDetailView extends ShoppingCartDetail implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;
    private Long ShopProductId;

    private ProductDetailView productDetailView;

    private ShopProductDetail shopProductDetail;

    public ProductDetailView getProductDetailView() {
        return productDetailView;
    }

    public void setProductDetailView(ProductDetailView productDetailView) {
        this.productDetailView = productDetailView;
    }

    public ShopProductDetail getShopProductDetail() {
        return shopProductDetail;
    }

    public void setShopProductDetail(ShopProductDetail shopProductDetail) {
        this.shopProductDetail = shopProductDetail;
    }

    public Long getShopProductId() {
        return ShopProductId;
    }

    public void setShopProductId(Long shopProductId) {
        ShopProductId = shopProductId;
    }
}
