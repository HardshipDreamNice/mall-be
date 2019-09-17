/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.shopaccount;

import com.torinosrc.model.entity.shopaccount.ShopAccount;
import com.torinosrc.model.entity.user.User;
import com.torinosrc.model.view.shopaccount.ShopAccountView;
import com.torinosrc.model.view.user.UserView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

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
@Service
public interface ShopAccountService extends BaseService<ShopAccountView> {

    /**
     * 获取店铺实际收入金额
     * @return
     */
    ShopAccountView getRealIncome(ShopAccountView shopAccountView);

    /**
     * 获取店铺实际销售金额
     * @param shopAccountView
     * @return
     */
    ShopAccountView getSalesAmount(ShopAccountView shopAccountView);

    /**
     * 获取我的团队收益
     * @return
     */
    ShopAccountView getTeamIncome(ShopAccountView shopAccountView);

    /**
     * 获取我的队员列表
     * @param shopAccountView
     * @return
     */
    List<UserView> getTeamMembers(ShopAccountView shopAccountView);

    public ShopAccountView findShopAccountByShopId(Long shopId);

    /**
     * 获取店铺预收入金额
     * @return
     */
    ShopAccountView getAdvanceIncome(ShopAccountView shopAccountView);

    /**
     * 更新销售会员商户收益
     * 定时器用
     */
    void updateShopAccountSaleMember();

}
