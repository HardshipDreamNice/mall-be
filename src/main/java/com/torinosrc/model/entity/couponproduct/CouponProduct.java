/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.couponproduct;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.product.Product;
import io.swagger.models.auth.In;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>CouponProduct</code></b>
 * <p/>
 * CouponProduct的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-12-06 15:30:48.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_coupon_product")
@DynamicInsert
@DynamicUpdate
public class CouponProduct extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public CouponProduct(){
        super();
    }


    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "type")
    private Integer type;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
