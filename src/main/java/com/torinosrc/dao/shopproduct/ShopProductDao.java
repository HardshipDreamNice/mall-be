/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.shopproduct;

import com.torinosrc.model.entity.shop.Shop;
import com.torinosrc.model.entity.shopproduct.ShopProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * <b><code>ShopProductDao</code></b>
 * <p/>
 * ShopProduct的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-11 19:22:11.
 *
 * @author ${model.author}
 * @version 1.0.0 t_shop_product
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface ShopProductDao extends JpaRepository<ShopProduct, Long>, JpaSpecificationExecutor<ShopProduct> {

    @Transactional
    public int deleteShopProductsByShopId(Long shopId);

    public List<ShopProduct> findShopProductsByShopId(Long shopId);


    @Query(value = "SELECT * FROM t_shop_product s  left JOIN t_product p on s.product_id=p.id WHERE s.shop_id = ?1 and s.on_sale=1 and p.enabled=1",
            countQuery = "SELECT count(*) FROM t_shop_product WHERE shop_id = ?1",
            nativeQuery = true)
    public Page<ShopProduct> findShopProductsByShopIdForWx(Long shopId,Pageable pageable);

    List<ShopProduct> findAllByShopIdAndProductId(Long shopId, Long productId);

    ShopProduct findByShopIdAndProductId(Long shopId, Long productId);

    List<ShopProduct> findByProductId(Long productId);

    @Query(value = "select MAX(IFNULL(ssp.product_price,p.price)) as max_price,MIN(IFNULL(ssp.product_price,p.price)) as min_price from\n" +
            "  t_product_detail p\n" +
            "  left join (select product_detail_id,shop_id,product_price from  t_shop_product_detail s left join  t_shop_product sp  on s.shop_product_id=sp.id where shop_id=?1) as ssp\n" +
            "      on p.id= ssp.product_detail_id\n" +
            "where p.product_id =?2",nativeQuery = true)
    public Map findProductsPriceRange(Long shopId,Long productId);

}
