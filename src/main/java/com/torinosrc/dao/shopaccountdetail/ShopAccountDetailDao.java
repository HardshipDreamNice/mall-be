/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.shopaccountdetail;

import com.torinosrc.model.entity.shopaccountdetail.ShopAccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * <b><code>ShopAccountDetailDao</code></b>
 * <p/>
 * ShopAccountDetail的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-12 16:34:26.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface ShopAccountDetailDao extends JpaRepository<ShopAccountDetail, Long>, JpaSpecificationExecutor<ShopAccountDetail> {

    @Transactional
    public int deleteShopAccountDetailsByShopAccountId(Long shopAccountId);

    @Modifying
    @Query(value = "update t_shop_account_detail set enabled=0 where shop_account_id=?1", nativeQuery = true)
    public int disabledShopAccountDetailsByShopAccountId(Long shopAccountId);

    /**
     * 获取已进账的销售收入记录数目
     * @param orderId
     * @param recordStatus
     * @return
     */
    Integer countByOrderIdAndRecordStatus(Long orderId,Integer recordStatus);
}
