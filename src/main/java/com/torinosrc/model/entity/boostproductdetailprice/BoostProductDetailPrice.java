/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.boostproductdetailprice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>BoostProductDetailPrice</code></b>
 * <p/>
 * BoostProductDetailPrice的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-30 16:12:08.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_boost_product_detail_price")
@DynamicInsert
@DynamicUpdate
public class BoostProductDetailPrice extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public BoostProductDetailPrice(){
        super();
    }

    @Column(name = "price")
    private Long price;

    @Column(name = "discount_price")
    private Long discountPrice;

    @Column(name = "type")
    private Integer type;

    @Column(name = "boost_product_detail_id")
    private Long boostProductDetailId;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getBoostProductDetailId() {
        return boostProductDetailId;
    }

    public void setBoostProductDetailId(Long boostProductDetailId) {
        this.boostProductDetailId = boostProductDetailId;
    }
}
