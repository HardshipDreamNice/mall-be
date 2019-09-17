/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.shoppingcart;

import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.entity.shoppingcart.ShoppingCart;
import com.torinosrc.model.view.productdetail.ProductDetailView;
import com.torinosrc.model.view.shoppingcartdetail.ShoppingCartDetailView;
import com.torinosrc.model.view.shopproductdetail.ShopProductDetailView;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>ShopCartView</code></b>
 * <p/>
 * ShopCart的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-06 11:30:32.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class ShoppingCartView extends ShoppingCart implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    List<ShoppingCartDetailView> shoppingCartDetailViews;

    public List<ShoppingCartDetailView> getShoppingCartDetailViews() {
        return shoppingCartDetailViews;
    }

    public void setShoppingCartDetailViews(List<ShoppingCartDetailView> shoppingCartDetailViews) {
        this.shoppingCartDetailViews = shoppingCartDetailViews;
    }
}
