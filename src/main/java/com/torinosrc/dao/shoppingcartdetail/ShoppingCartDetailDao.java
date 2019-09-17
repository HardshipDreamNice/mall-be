/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.shoppingcartdetail;

import com.torinosrc.model.entity.shoppingcartdetail.ShoppingCartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * <b><code>ShopCartDetailDao</code></b>
 * <p/>
 * ShopCartDetail的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-13 21:17:59.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface ShoppingCartDetailDao extends JpaRepository<ShoppingCartDetail, Long>, JpaSpecificationExecutor<ShoppingCartDetail> {

    /**
     * 移除购物车中商品
     */
    @Transactional
    @Modifying
    @Query(value = "delete from ShoppingCartDetail p where p.shoppingCartId = ?1 and p.shopProductDetailId = ?2")
    public void removeShopCartByShopProductDetailId (Long shoppingCartId, Long shopProductDetailId);

    @Transactional
    @Modifying
    @Query(value = "delete from ShoppingCartDetail p where p.shoppingCartId = ?1 and p.productDetailId = ?2")
    public void removeShopCartByProductDetailId(Long shoppingCartId, Long productDetailId);

    @Transactional
    @Modifying
    @Query(value = "delete from ShoppingCartDetail p where p.id = ?1")
    public void removeShopCartDetailById(Long id);


    public List<ShoppingCartDetail> findByProductDetailIdAndShopId(Long productDetailId, Long shopId);

    public List<ShoppingCartDetail> findByProductDetailId(Long productDetailId);

    /**
     * 上架商品明细找购物车明细
     * @param shopProductDetailId
     * @return
     */
    List<ShoppingCartDetail> findByShopProductDetailId(Long shopProductDetailId);

}
