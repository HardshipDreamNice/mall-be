/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.shoppingcartdetail;

import com.torinosrc.model.view.shoppingcartdetail.ShoppingCartDetailView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>ShopCartDetail</code></b>
* <p/>
* ShopCartDetail的具体实现
* <p/>
* <b>Creation Time:</b> 2018-06-13 21:17:59.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface ShoppingCartDetailService extends BaseService<ShoppingCartDetailView> {
    // 加入购物车
    public void addShopCart(Long shopCartId,ShoppingCartDetailView shoppingCartDetailView);

    // 移除购物车通过productDetailId
    public void removeShopCartByProductDetailId(Long shopCartId, Long productDetailId);

    // 移除购物车通过shopProductDetailId
    public void removeShopCartByShopProductDetailId(Long shopCartId, Long shopProductDetailId);
}
