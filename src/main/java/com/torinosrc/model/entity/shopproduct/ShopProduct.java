/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.shopproduct;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.entity.shop.Shop;
import com.torinosrc.model.entity.shopproductdetail.ShopProductDetail;
import com.torinosrc.model.entity.user.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * <b><code>ShopProduct</code></b>
 * <p/>
 * ShopProduct的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-11 19:22:11.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_shop_product")
@DynamicInsert
@DynamicUpdate
public class ShopProduct extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public ShopProduct(){
        super();
    }

    @Column(name = "on_sale")
    private Integer onSale;

    @Column(name = "recommend_reason")
    private String recommendReason;

    @OneToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private  Product product;

    @Column(name = "shop_id")
    private   Long shopId;

    //,fetch=FetchType.EAGER,cascade = { CascadeType.PERSIST ,CascadeType.ALL}
    @OneToMany(mappedBy = "shopProduct",fetch=FetchType.EAGER,cascade = { CascadeType.PERSIST ,CascadeType.ALL})
    @OrderBy("createTime asc")
    private  Set<ShopProductDetail> shopProductDetails;

    public Integer getOnSale() {
        return onSale;
    }

    public void setOnSale(Integer onSale) {
        this.onSale = onSale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Set<ShopProductDetail> getShopProductDetails() {
        return shopProductDetails;
    }

    public void setShopProductDetails(Set<ShopProductDetail> shopProductDetails) {
        this.shopProductDetails = shopProductDetails;
    }

    public String getRecommendReason() {
        return recommendReason;
    }

    public void setRecommendReason(String recommendReason) {
        this.recommendReason = recommendReason;
    }

    @Override
    public String toString() {
        return "ShopProduct{" +
                "onSale=" + onSale +
                ", recommendReason='" + recommendReason + '\'' +
                ", product=" + product +
                ", shopId=" + shopId +
                ", shopProductDetails=" + shopProductDetails +
                '}';
    }
}
