/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.redenvelope;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import io.swagger.models.auth.In;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>RedEnvelope</code></b>
 * <p/>
 * RedEnvelope的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-12-04 12:09:47.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_red_envelope")
@DynamicInsert
@DynamicUpdate
public class RedEnvelope extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public RedEnvelope(){
        super();
    }


    @Column(name = "name")
    private String name;

    @Column(name = "expired_time")
    private Long expiredTime;

    @Column(name = "coupon_category_id")
    private Long couponCategoryId;

    @Column(name = "take_number")
    private Integer takeNumber;

    @Column(name = "total_amount")
    private Integer totalAmount;

    @Column(name = "type")
    private Integer type;

    @Column(name = "valid_day")
    private Integer validDay;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Long getCouponCategoryId() {
        return couponCategoryId;
    }

    public void setCouponCategoryId(Long couponCategoryId) {
        this.couponCategoryId = couponCategoryId;
    }

    public Integer getTakeNumber() {
        return takeNumber;
    }

    public void setTakeNumber(Integer takeNumber) {
        this.takeNumber = takeNumber;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getValidDay() {
        return validDay;
    }

    public void setValidDay(Integer validDay) {
        this.validDay = validDay;
    }
}
