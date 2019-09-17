/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.productdetailprice;

import com.torinosrc.model.entity.productdetailprice.ProductDetailPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <b><code>ProductDetailPriceDao</code></b>
 * <p/>
 * ProductDetailPrice的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-19 10:01:28.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface ProductDetailPriceDao extends JpaRepository<ProductDetailPrice, Long>, JpaSpecificationExecutor<ProductDetailPrice> {

    public ProductDetailPrice findByProductDetailId(Long productDetailId);

    public List<ProductDetailPrice> findByProductDetailIdIn(List<Long> ids);

    public List<ProductDetailPrice> findAllByProductDetailId(Long productDetailIds);


    /**
     * 根据商品 Id 查找该商品的最低价和最高价
     * @param productId
     * @return
     */
    @Query(value = "select MIN(IFNULL(s.price,0)) as min_price, MAX(IFNULL(s.price,0)) as max_price from t_product_detail p left join\n" +
            "t_product_detail_price s on p.id= s.product_detail_id where p.product_id =?1",nativeQuery = true)
    Map findProductPriceRange(Long productId);
}
