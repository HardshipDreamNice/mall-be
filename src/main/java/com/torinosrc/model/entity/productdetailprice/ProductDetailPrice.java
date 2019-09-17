/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.productdetailprice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>ProductDetailPrice</code></b>
 * <p/>
 * ProductDetailPrice的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-19 10:01:28.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_product_detail_price")
@DynamicInsert
@DynamicUpdate
public class ProductDetailPrice extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public ProductDetailPrice(){
        super();
    }

    @Column(name = "price")
    private Long price;

    @Column(name = "team_price")
    private Long teamPrice;

    @Column(name = "type")
    private Integer type;

    @Column(name = "product_detail_id")
    private Long productDetailId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_detail_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductDetail productDetail;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getTeamPrice() {
        return teamPrice;
    }

    public void setTeamPrice(Long teamPrice) {
        this.teamPrice = teamPrice;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(Long productDetailId) {
        this.productDetailId = productDetailId;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }
}
