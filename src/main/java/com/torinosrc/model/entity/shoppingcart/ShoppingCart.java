/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.shoppingcart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.shoppingcartdetail.ShoppingCartDetail;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * <b><code>ShopCart</code></b>
 * <p/>
 * ShopCart的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-06 11:30:32.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_shopping_cart")
@DynamicInsert
@DynamicUpdate
public class ShoppingCart extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public ShoppingCart(){
        super();
    }

    @Column(name = "user_id")
    private Long userId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="shopping_cart_id")
    @OrderBy("updateTime desc")
    private Set<ShoppingCartDetail> shoppingCartDetails;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<ShoppingCartDetail> getShoppingCartDetails() {
        return shoppingCartDetails;
    }

    public void setShoppingCartDetails(Set<ShoppingCartDetail> shoppingCartDetails) {
        this.shoppingCartDetails = shoppingCartDetails;
    }
}
