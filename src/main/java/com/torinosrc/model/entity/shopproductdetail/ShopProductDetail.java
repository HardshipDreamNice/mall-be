/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.shopproductdetail;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.entity.shopproduct.ShopProduct;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>ShopProductDetail</code></b>
 * <p/>
 * ShopProductDetail的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-16 17:27:33.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_shop_product_detail")
@DynamicInsert
@DynamicUpdate
public class ShopProductDetail extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public ShopProductDetail(){
        super();
    }

    @Column(name = "uppest_price")
    private Integer uppestPrice;

    @Column(name = "advise_price")
    private Integer advisePrice;

    @Column(name = "product_price")
    private Integer productPrice;

    @OneToOne(targetEntity = ProductDetail.class)
    @JoinColumn(name = "product_detail_id",referencedColumnName = "id")
    private ProductDetail productDetail;

    @JsonBackReference
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="shop_product_id",referencedColumnName = "id")
    private  ShopProduct shopProduct;

    public Integer getUppestPrice() {
        return uppestPrice;
    }

    public void setUppestPrice(Integer uppestPrice) {
        this.uppestPrice = uppestPrice;
    }

    public Integer getAdvisePrice() {
        return advisePrice;
    }

    public void setAdvisePrice(Integer advisePrice) {
        this.advisePrice = advisePrice;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public ShopProduct getShopProduct() {
        return shopProduct;
    }

    public void setShopProduct(ShopProduct shopProduct) {
        this.shopProduct = shopProduct;
    }
}
