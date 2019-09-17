/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.shoppingcartdetail;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.entity.shopproductdetail.ShopProductDetail;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>ShopCartDetail</code></b>
 * <p/>
 * ShopCartDetail的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-13 21:17:59.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_shopping_cart_detail")
@DynamicInsert
@DynamicUpdate
public class ShoppingCartDetail extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public ShoppingCartDetail(){
        super();
    }

    @Column(name = "count")
    private Integer count;

    @Column(name = "shopping_cart_id")
    private Long shoppingCartId;

    @Column(name = "shop_product_detail_id")
    private Long shopProductDetailId;

    @Column(name = "product_detail_id")
    private Long productDetailId;

    @Column(name = "shop_id")
    private Long shopId;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Long getShopProductDetailId() {
        return shopProductDetailId;
    }

    public void setShopProductDetailId(Long shopProductDetailId) {
        this.shopProductDetailId = shopProductDetailId;
    }

    public Long getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(Long productDetailId) {
        this.productDetailId = productDetailId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
