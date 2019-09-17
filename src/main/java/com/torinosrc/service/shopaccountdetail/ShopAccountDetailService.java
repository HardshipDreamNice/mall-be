/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.shopaccountdetail;

import com.torinosrc.model.view.order.OrderView;
import com.torinosrc.model.view.shopaccountdetail.ShopAccountDetailView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>ShopAccountDetail</code></b>
* <p/>
* ShopAccountDetail的具体实现
* <p/>
* <b>Creation Time:</b> 2018-07-12 16:34:26.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface ShopAccountDetailService extends BaseService<ShopAccountDetailView> {
    public void calculateOrderProfit(OrderView orderView);

    /**
     * 计算会员订单的分销收益
     * @param orderView
     */
    void calculateMemberOrderProfit(OrderView orderView);

    /**
     *  退款进行分销收益回退
     * @param orderView
     */
    void reduceAccountByOrder(OrderView orderView);

}
