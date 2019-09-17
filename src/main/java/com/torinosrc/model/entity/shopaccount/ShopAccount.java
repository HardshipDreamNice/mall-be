/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.shopaccount;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.shopaccountdetail.ShopAccountDetail;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * <b><code>ShopAccount</code></b>
 * <p/>
 * ShopAccount的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-12 16:33:53.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_shop_account")
@DynamicInsert
@DynamicUpdate
public class ShopAccount extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public ShopAccount(){
        super();
    }

    @Column(name = "total_amount")
    private Long totalAmount;

    @Column(name = "money")
    private Long money;

    @Column(name = "shop_id")
    private Long shopId;

    @OneToMany(mappedBy = "shopAccount",fetch=FetchType.EAGER,cascade = { CascadeType.PERSIST ,CascadeType.ALL})
    private Set<ShopAccountDetail> shopAccountDetails;

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Set<ShopAccountDetail> getShopAccountDetails() {
        return shopAccountDetails;
    }

    public void setShopAccountDetails(Set<ShopAccountDetail> shopAccountDetails) {
        this.shopAccountDetails = shopAccountDetails;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }
}
