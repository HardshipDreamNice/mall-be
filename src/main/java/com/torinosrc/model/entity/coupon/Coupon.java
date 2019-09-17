/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.coupon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.couponcategory.CouponCategory;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>Coupon</code></b>
 * <p/>
 * Coupon的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-28 19:57:38.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_coupon")
@DynamicInsert
@DynamicUpdate
public class Coupon extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public Coupon(){
        super();
    }


    @Column(name = "name")
    private String name;

    @Column(name = "total_number")
    private Integer totalNumber;

    @Column(name = "coupon_type")
    private Integer couponType;

    @Column(name = "discount_amount")
    private Integer discountAmount;

    @Column(name = "usable_amount")
    private Integer usableAmount;

    @Column(name = "is_release_to_wechat")
    private Integer isReleaseToWechat;

    @Column(name = "card_coupon_color")
    private String cardCouponColor;

    @Column(name = "card_coupon_title")
    private String cardCouponTitle;

    @Column(name = "card_coupon_sub_title")
    private String cardCouponSubTitle;

    @Column(name = "membership_grade_id")
    private Long membershipGradeId;

    @Column(name = "available_number")
    private Integer availableNumber;

    @Column(name = "valid_period_type")
    private Integer validPeriodType;

    @Column(name = "start_time")
    private Long startTime;

    @Column(name = "end_time")
    private Long endTime;

    @Column(name = "is_expired_reminder")
    private Integer isExpiredReminder;

    @ApiModelProperty(value = "适用范围 0：全店商品 1：指定商品 2：指定商品不可用")
    @Column(name = "usable_scope")
    private Integer usableScope;

    @Column(name = "use_introduction")
    private String useIntroduction;

    @Column(name = "coupon_category_id")
    private Long couponCategoryId;

    @Column(name = "remaining_number")
    private Integer remainingNumber;

    @Column(name = "valid_day")
    private Integer validDay;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getUsableAmount() {
        return usableAmount;
    }

    public void setUsableAmount(Integer usableAmount) {
        this.usableAmount = usableAmount;
    }

    public Integer getIsReleaseToWechat() {
        return isReleaseToWechat;
    }

    public void setIsReleaseToWechat(Integer isReleaseToWechat) {
        this.isReleaseToWechat = isReleaseToWechat;
    }

    public String getCardCouponColor() {
        return cardCouponColor;
    }

    public void setCardCouponColor(String cardCouponColor) {
        this.cardCouponColor = cardCouponColor;
    }

    public String getCardCouponTitle() {
        return cardCouponTitle;
    }

    public void setCardCouponTitle(String cardCouponTitle) {
        this.cardCouponTitle = cardCouponTitle;
    }

    public String getCardCouponSubTitle() {
        return cardCouponSubTitle;
    }

    public void setCardCouponSubTitle(String cardCouponSubTitle) {
        this.cardCouponSubTitle = cardCouponSubTitle;
    }

    public Long getMembershipGradeId() {
        return membershipGradeId;
    }

    public void setMembershipGradeId(Long membershipGradeId) {
        this.membershipGradeId = membershipGradeId;
    }

    public Integer getAvailableNumber() {
        return availableNumber;
    }

    public void setAvailableNumber(Integer availableNumber) {
        this.availableNumber = availableNumber;
    }

    public Integer getValidPeriodType() {
        return validPeriodType;
    }

    public void setValidPeriodType(Integer validPeriodType) {
        this.validPeriodType = validPeriodType;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getIsExpiredReminder() {
        return isExpiredReminder;
    }

    public void setIsExpiredReminder(Integer isExpiredReminder) {
        this.isExpiredReminder = isExpiredReminder;
    }

    public Integer getUsableScope() {
        return usableScope;
    }

    public void setUsableScope(Integer usableScope) {
        this.usableScope = usableScope;
    }

    public String getUseIntroduction() {
        return useIntroduction;
    }

    public void setUseIntroduction(String useIntroduction) {
        this.useIntroduction = useIntroduction;
    }

    public Long getCouponCategoryId() {
        return couponCategoryId;
    }

    public void setCouponCategoryId(Long couponCategoryId) {
        this.couponCategoryId = couponCategoryId;
    }

    public Integer getRemainingNumber() {
        return remainingNumber;
    }

    public void setRemainingNumber(Integer remainingNumber) {
        this.remainingNumber = remainingNumber;
    }

    public Integer getValidDay() {
        return validDay;
    }

    public void setValidDay(Integer validDay) {
        this.validDay = validDay;
    }

}
