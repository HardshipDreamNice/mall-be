/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.shop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.membershipgrade.MembershipGrade;
import com.torinosrc.model.entity.shop.Shop;
import com.torinosrc.model.view.membershipgrade.MembershipGradeView;
import com.torinosrc.model.view.shoppingcartdetail.ShoppingCartDetailView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>ShopView</code></b>
 * <p/>
 * Shop的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-11 18:07:23.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class
ShopView extends Shop implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;


    private Long parentId;

    //用户头像。
    private String userAvatar;

    @ApiModelProperty(value = "上级可得分红")
    private Long highLevelDevidend;

    @ApiModelProperty(value = "我的销售金额")
    private Integer salesAmount;

    public Integer getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Integer salesAmount) {
        this.salesAmount = salesAmount;
    }

    public Long getHighLevelDevidend() {
        return highLevelDevidend;
    }

    public void setHighLevelDevidend(Long highLevelDevidend) {
        this.highLevelDevidend = highLevelDevidend;
    }

    //当前店铺的上级店铺
    List<ShopView> ShopView;

    public List<com.torinosrc.model.view.shop.ShopView> getShopView() {
        return ShopView;
    }

    public void setShopView(List<com.torinosrc.model.view.shop.ShopView> shopView) {
        ShopView = shopView;
    }

    //当前店铺的下一级店铺
    List<ShopView> ShopViews;

    public List<ShopView> getShopViews() {
        return ShopViews;
    }

    public void setShopViews(List<ShopView> shopViews) {
        ShopViews = shopViews;
    }

    // 是否可邀请注册(true:可邀请，false:不可邀请)
    private Boolean invite;

    @ApiModelProperty(value = "要转发的url的id，小程序端中定义的")
    private Long redirectUrlId;

    @JsonIgnoreProperties
    @ApiModelProperty(value = "会员等级")
    private MembershipGradeView membershipGradeView;

    @ApiModelProperty(value = "是否有店铺")
    private Boolean haveShop;

    public Boolean getHaveShop() {
        return haveShop;
    }

    public void setHaveShop(Boolean haveShop) {
        this.haveShop = haveShop;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public Boolean getInvite() {
        return invite;
    }

    public void setInvite(Boolean invite) {
        this.invite = invite;
    }

    public Long getRedirectUrlId() {
        return redirectUrlId;
    }

    public void setRedirectUrlId(Long redirectUrlId) {
        this.redirectUrlId = redirectUrlId;
    }

    public MembershipGradeView getMembershipGradeView() {
        return membershipGradeView;
    }

    public void setMembershipGradeView(MembershipGradeView membershipGradeView) {
        this.membershipGradeView = membershipGradeView;
    }
}
