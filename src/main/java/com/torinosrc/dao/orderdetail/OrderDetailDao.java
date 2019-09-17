/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.orderdetail;

import com.torinosrc.model.entity.orderdetail.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * <b><code>OrderDetailDao</code></b>
 * <p/>
 * OrderDetail的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-06 11:31:43.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface OrderDetailDao extends JpaRepository<OrderDetail, Long>, JpaSpecificationExecutor<OrderDetail> {

    /**
     * 根据orderId获取所有OrderDetail
     */
    public List<OrderDetail> findByOrderId(Long orderId);

    public void deleteByOrderId(Long orderId);


    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "update t_order_detail set enabled=0 where order_id=?1")
    public void updateOrderDetailEnable(Long orderId);

}
