/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.shop;

import com.torinosrc.model.entity.shop.Shop;
import com.torinosrc.model.view.common.CustomPage;
import com.torinosrc.model.view.shop.ShopOrgnization;
import com.torinosrc.model.view.shop.ShopView;
import com.torinosrc.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

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
@Service
public interface ShopService extends BaseService<ShopView> {

    public ShopView findShopByUserId(Long userId);


    /*
    *根据商店id获取商店的信息，上级商店的信息
     */
    public ShopView getEntityAndParent(long id);

    /*
    通过用户id获取该用户的下级商店有分页
     */
    public CustomPage<ShopView> getEntityChild(Long shopId, ShopView shopView, int currentPage, int pageSize);

    /*
    * 通过用户id获取该用户的下下级商店信息有分页
    * */
    public CustomPage<ShopView> getEntityNextChild(Long userId,ShopView shopView, int currentPage, int pageSize);


    /**
     * 通过用户ID获取该用户的下级商铺
     * @param userId
     * @return
     */
    public List<ShopView> findAllNextLevlShopsByUserId(Long userId);

    /**
     * 支付的店铺和它的两个上级
     * @param shopId
     * @return
     */
    public ShopOrgnization findShop3LevelOrgnization(Long shopId);

    /**
     * 生成店铺二维码
     * @param shopView
     * @return
     */
    String createShopQrCode(ShopView shopView);

    /**
     * 通过用户ID获取推荐人店铺
     * @param userId
     * @return
     */
    public ShopView getRefereesShop(Long userId);
}
