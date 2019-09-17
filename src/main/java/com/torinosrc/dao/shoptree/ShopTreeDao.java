/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.shoptree;

import com.torinosrc.model.entity.shop.Shop;
import com.torinosrc.model.entity.shoptree.ShopTree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b><code>ShopTreeDao</code></b>
 * <p/>
 * ShopTree的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-12 16:11:55.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface ShopTreeDao extends JpaRepository<ShopTree, Long>, JpaSpecificationExecutor<ShopTree> {

    /**
     * 获取所有下一级对象
     * @param shopId
     * @return
     */
    public List<ShopTree> findShopTreesByParentShopId(Long shopId);

    /**
     * 获取所有下一级ID
     * @param shopId
     * @return
     */
    @Query("select u.shopId from ShopTree u where u.parentShopId = ?1")
    public List<Long> findShopTreesByParentShopIdReturnIds(Long shopId);

/*
分页获取下一级的ID
 */
  Page<ShopTree> findShopTreeByParentShopId(Long shopId, Pageable pageable);

    /*
    * 分页获取下两级的所有的ID
    * */
    Page<ShopTree> findShopTreeByParentShopIdIn(List<Long> ids, Pageable pageable);
/*
* 获取所有的下级对象
* */
    List<ShopTree> findShopTreeByParentShopIdIn(List<Long> ids);

    /**
     * 获取所有上级对象
     * @param shopId
     * @return
     */
    public ShopTree findByShopId(Long shopId);

    /**
     * 获取上级对象
     * @param shopId
     * @return
     */
    public ShopTree findShopTreeByShopId(Long shopId);

    /**
     * 获取所有下级对象
     * @param parentShopId
     * @return
     */
    List<ShopTree> findAllByParentShopId(Long parentShopId);


}
