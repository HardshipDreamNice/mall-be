/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.user;

import com.torinosrc.model.entity.user.User;
import com.torinosrc.model.view.shoppingcart.ShoppingCartView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.dialect.Ingres9Dialect;

import java.io.Serializable;

/**
 * <b><code>UserView</code></b>
 * <p/>
 * User的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-04-16 14:17:14.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class UserView extends User implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    private String code;

    private ShoppingCartView shoppingCartView;

    private String token;

    // haveShop
    private Boolean haveShop;

    @ApiModelProperty(value = "上级可得分红")
    private Long highLevelDevidend;

    @ApiModelProperty(value = "我的销售金额")
    private Integer salesAmount;

    public Boolean getHaveShop() {
        return haveShop;
    }

    public void setHaveShop(Boolean haveShop) {
        this.haveShop = haveShop;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ShoppingCartView getShoppingCartView() {
        return shoppingCartView;
    }

    public void setShoppingCartView(ShoppingCartView shoppingCartView) {
        this.shoppingCartView = shoppingCartView;
    }

    public Long getHighLevelDevidend() {
        return highLevelDevidend;
    }

    public void setHighLevelDevidend(Long highLevelDevidend) {
        this.highLevelDevidend = highLevelDevidend;
    }

    public Integer getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Integer salesAmount) {
        this.salesAmount = salesAmount;
    }
}
