/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.usercoupon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.coupon.Coupon;
import com.torinosrc.model.entity.user.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>UserCoupon</code></b>
 * <p/>
 * UserCoupon的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-28 19:59:09.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name = "t_user_coupon")
@DynamicInsert
@DynamicUpdate
public class UserCoupon extends BaseEntity implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    public UserCoupon() {
        super();
    }

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "coupon_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Coupon coupon;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "expired_time")
    private Long expiredTime;

    @Column(name = "status")
    private Integer status;

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
