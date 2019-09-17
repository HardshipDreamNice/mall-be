/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.withdrawmoney;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.shop.Shop;
import com.torinosrc.model.entity.user.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>WithdrawMoney</code></b>
 * <p/>
 * WithdrawMoney的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-08-02 11:19:16.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_withdraw_money")
@DynamicInsert
@DynamicUpdate
public class WithdrawMoney extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public WithdrawMoney(){
        super();
    }

    @Column(name = "item")
    private Long item;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "status")
    private Integer status;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id",insertable = false,updatable = false)
    private User user;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "shop_id",referencedColumnName = "id",insertable = false,updatable = false)
    private Shop shop;

    public Long getItem() {
        return item;
    }

    public void setItem(Long item) {
        this.item = item;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
