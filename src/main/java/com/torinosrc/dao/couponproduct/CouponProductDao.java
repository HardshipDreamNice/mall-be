/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.couponproduct;

import com.torinosrc.model.entity.couponproduct.CouponProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b><code>CouponProductDao</code></b>
 * <p/>
 * CouponProduct的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-12-06 15:30:48.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface CouponProductDao extends JpaRepository<CouponProduct, Long>, JpaSpecificationExecutor<CouponProduct> {

    List<CouponProduct> findByCouponId(Long couponId);

}
