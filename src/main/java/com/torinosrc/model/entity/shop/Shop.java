/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.shop;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.shopproduct.ShopProduct;
import com.torinosrc.model.entity.user.User;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * <b><code>Shop</code></b>
 * <p/>
 * Shop的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-11 18:07:23.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_shop")
@DynamicInsert
@DynamicUpdate
public class Shop extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public Shop(){
        super();
    }

    @Column(name = "title")
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "description")
    private String description;

    @Column(name = "background_url")
    private String backgroundUrl;

    @Column(name = "phone")
    private String phone;

    @Column(name = "qcode")
    private String qcode;

    @Column(name = "percent_1")
    private Integer percent1;

    @Column(name = "percent_2")
    private Integer percent2;

    @Column(name = "percent_3")
    private Integer percent3;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "shop_level")
    private Integer shopLevel;

    @Column(name = "membership_grade_id")
    private Long membershipGradeId;

    public Integer getShopLevel() {
        return shopLevel;
    }

    public void setShopLevel(Integer shopLevel) {
        this.shopLevel = shopLevel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQcode() {
        return qcode;
    }

    public void setQcode(String qcode) {
        this.qcode = qcode;
    }

    public Integer getPercent1() {
        return percent1;
    }

    public void setPercent1(Integer percent1) {
        this.percent1 = percent1;
    }

    public Integer getPercent2() {
        return percent2;
    }

    public void setPercent2(Integer percent2) {
        this.percent2 = percent2;
    }

    public Integer getPercent3() {
        return percent3;
    }

    public void setPercent3(Integer percent3) {
        this.percent3 = percent3;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMembershipGradeId() {
        return membershipGradeId;
    }

    public void setMembershipGradeId(Long membershipGradeId) {
        this.membershipGradeId = membershipGradeId;
    }
}
