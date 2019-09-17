/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.productfreereceive;

import com.torinosrc.model.entity.productfreereceive.ProductFreeReceive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * <b><code>ProductFreeReceiveDao</code></b>
 * <p/>
 * ProductFreeReceive的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-27 19:54:27.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface ProductFreeReceiveDao extends JpaRepository<ProductFreeReceive, Long>, JpaSpecificationExecutor<ProductFreeReceive> {


//    /**
//     * 根据查询免费领商品表的id查询该商品全部信息
//     * @param productFreeReceiveId
//     * @return
//     */
//    @Query(value = "select * from t_product_free_receive t where t.product_free_receive_id=?1",nativeQuery = true)
//    ProductFreeReceive findAllProductFreeReceiveId(Long productFreeReceiveId);



}
