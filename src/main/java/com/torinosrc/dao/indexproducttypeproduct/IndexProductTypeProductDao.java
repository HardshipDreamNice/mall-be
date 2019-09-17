/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.indexproducttypeproduct;

import com.torinosrc.model.entity.indexproducttypeproduct.IndexProductTypeProduct;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b><code>IndexProductTypeProductDao</code></b>
 * <p/>
 * IndexProductTypeProduct的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-27 11:04:15.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface IndexProductTypeProductDao extends JpaRepository<IndexProductTypeProduct, Long>, JpaSpecificationExecutor<IndexProductTypeProduct> {

    @Query(value = "select * from t_index_product_type_product where index_product_type_id = ?1 order by weight limit ?2", nativeQuery = true)
    List<IndexProductTypeProduct> findByIndexProductTypeIdOrderByWeightLimit(Long indexProductTypeId, Integer num);

    Integer countByIndexProductTypeId(Long indexProductTypeId);

    List<IndexProductTypeProduct> findByProductId(Long productId);

    void deleteByProductId(Long productId);
}
