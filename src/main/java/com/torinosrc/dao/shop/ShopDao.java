/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.shop;

import com.torinosrc.model.entity.shop.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b><code>ShopDao</code></b>
 * <p/>
 * Shop的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-11 18:07:23.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface ShopDao extends JpaRepository<Shop, Long>, JpaSpecificationExecutor<Shop> {

    public Shop findShopByUserId(Long userId);

    public List<Shop> findShopsByIdIn(List<Long> shopIds);

//    public Page<Shop> findShopsByIdIn(List<Long> shopIds);

    @Modifying
    @Query(value = "update t_shop set enabled=0 where id=?1",nativeQuery = true)
    public int disabledShop(Long shopId);

    public Shop findByPhone(String phone);
}
