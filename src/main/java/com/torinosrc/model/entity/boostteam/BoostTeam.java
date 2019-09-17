/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.boostteam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>BoostTeam</code></b>
 * <p/>
 * BoostTeam的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-12-03 15:14:32.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name = "t_boost_team")
@DynamicInsert
@DynamicUpdate
public class BoostTeam extends BaseEntity implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    public BoostTeam() {
        super();
    }

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "expired_time")
    private Long expiredTime;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_detail_id")
    private Long productDetailId;

    @Column(name = "boost_number")
    private Integer boostNumber;

    @Column(name = "user_id")
    private Long userId;

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Integer getBoostNumber() {
        return boostNumber;
    }

    public void setBoostNumber(Integer boostNumber) {
        this.boostNumber = boostNumber;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(Long productDetailId) {
        this.productDetailId = productDetailId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
