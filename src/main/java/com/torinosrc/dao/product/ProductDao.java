/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.product;

import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.entity.shopproduct.ShopProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <b><code>ProductDao</code></b>
 * <p/>
 * Product的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-06 11:17:58.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface ProductDao extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    /**
     * 查询某一分类下的所有商品
     * @param categoryId
     * @return
     */
    @Query(value = "select * from t_product t where t.category_id=?1",nativeQuery = true)
    List<Product> findAllByCategoryId(Long categoryId);

    /**
     * 根据商品名称和分类 查询商品
     * @param categoryId
     * @param name
     * @return
     */
    @Query(value = "select * from t_product t where t.category_id=?1 AND t.name LIKE ?2",nativeQuery = true)
    List<Product> findAllByCategoryIdAndName(Long categoryId, String name);

    /**
     * 根据商品 Id 查找该商品的最低价和最低拼团价
     * @param productId
     * @return
     */
    @Query(value = "select MIN(IFNULL(s.price,0)) as min_price,MIN(IFNULL(s.team_price,0)) as min_team_price from  t_product_detail p left join\n" +
            "t_product_detail_price s on p.id= s.product_detail_id where p.product_id =?1",nativeQuery = true)
    Map findGroupProductsPriceRange(Long productId);

    /**
     * 根据商品 Id 查找该商品的最低价和最高价
     * @param productId
     * @return
     */
    @Query(value = "select MIN(IFNULL(s.price,0)) as min_price, MAX(IFNULL(s.price,0)) as max_price from t_product_detail p left join\n" +
            "t_product_detail_price s on p.id= s.product_detail_id where p.product_id =?1",nativeQuery = true)
    Map findProductPriceRange(Long productId);


    List<Product> findByEnabledAndType(Integer enabled,Integer type);
}
