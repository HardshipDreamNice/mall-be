/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.shoppingcart;


import com.torinosrc.model.view.shoppingcart.ShoppingCartView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

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
@Service
public interface ShoppingCartService extends BaseService<ShoppingCartView> {
    public ShoppingCartView findByUserId(Long userId,Long shopId);
    public ShoppingCartView saveEntityIfHasNot(ShoppingCartView shopCartView);
}
