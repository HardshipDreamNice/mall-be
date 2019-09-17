/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.coupon;

import com.torinosrc.model.entity.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * <b><code>CouponDao</code></b>
 * <p/>
 * Coupon的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-28 19:57:38.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface CouponDao extends JpaRepository<Coupon, Long>, JpaSpecificationExecutor<Coupon> {

    /**
     * 优惠券剩余数量 - 1
     * @param couponId
     */
    @Transactional
    @Modifying
    @Query(value = "update t_coupon c set c.remaining_number = c.remaining_number -1 where c.id = ?1", nativeQuery = true)
    void updateCouponRemainingNumberMinusOne(long couponId);

    /**
     * 根据优惠券分类 id 获取优惠券
     * @param caouponCategoryId
     * @return
     */
    List<Coupon> getCouponsByCouponCategoryId(Long caouponCategoryId);


}
