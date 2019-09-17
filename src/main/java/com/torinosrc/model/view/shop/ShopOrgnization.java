package com.torinosrc.model.view.shop;

import com.torinosrc.model.entity.shop.Shop;

/**
 * <b><code>ShopOrgnization</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>14:26
 *
 * @author PanXin
 * @version 1.0.0
 * @since mall-be  1.0.0
 */
public class ShopOrgnization {

    /**
     * 店铺的在上级上级
     */
    public Shop level1Shop;

    /**
     * 店铺的上级
     */
    public Shop level2Shop;

    /**
     * 客户支付的店铺
     */
    public Shop level3Shop;

    public Shop getLevel1Shop() {
        return level1Shop;
    }

    public void setLevel1Shop(Shop level1Shop) {
        this.level1Shop = level1Shop;
    }

    public Shop getLevel2Shop() {
        return level2Shop;
    }

    public void setLevel2Shop(Shop level2Shop) {
        this.level2Shop = level2Shop;
    }

    public Shop getLevel3Shop() {
        return level3Shop;
    }

    public void setLevel3Shop(Shop level3Shop) {
        this.level3Shop = level3Shop;
    }
}
