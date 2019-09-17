/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.withdrawmoney;

import com.torinosrc.model.entity.withdrawmoney.WithdrawMoney;
import com.torinosrc.model.view.withdrawmoney.WithdrawMoneyView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

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
@Service
public interface WithdrawMoneyService extends BaseService<WithdrawMoneyView> {

    public WithdrawMoneyView findByShopIdAndStatus(Long shopId, Integer status);

    /**
     * 获取提现未完成的提现流水 status not in (1, 2)
     * @param shopId
     * @return
     */
    WithdrawMoneyView findWithdrawMoneyViewProcessingByShopId(Long shopId);

    WithdrawMoneyView findWithdrawMoneyViewCreateLastestByShopId(Long shopId);
}
