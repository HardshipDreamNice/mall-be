/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.withdrawmoney;

import com.torinosrc.model.entity.withdrawmoney.WithdrawMoney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b><code>WithdrawMoneyDao</code></b>
 * <p/>
 * WithdrawMoney的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-08-02 11:19:16.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface WithdrawMoneyDao extends JpaRepository<WithdrawMoney, Long>, JpaSpecificationExecutor<WithdrawMoney> {

    public WithdrawMoney findByShopIdAndStatus(Long shopId,Integer status);

    List<WithdrawMoney> findByShopIdAndStatusNotIn(Long shopId, List<Integer> statusList);

    WithdrawMoney findFirstByShopIdOrderByCreateTimeDesc(Long shopId);
}
