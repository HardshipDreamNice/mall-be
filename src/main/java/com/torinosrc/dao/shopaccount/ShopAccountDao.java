/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.shopaccount;

import com.torinosrc.model.entity.shopaccount.ShopAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <b><code>ShopAccountDao</code></b>
 * <p/>
 * ShopAccount的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-12 16:33:53.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface ShopAccountDao extends JpaRepository<ShopAccount, Long>, JpaSpecificationExecutor<ShopAccount> {

    public ShopAccount findShopAccountByShopId(Long shopId);

    @Modifying
    @Query(value = "update t_shop_account set enabled=0 where id=?1",nativeQuery = true)
    public int disabledShopAccount(Long id);

}
