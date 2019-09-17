/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.usercoupon;

import com.torinosrc.model.entity.usercoupon.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b><code>UserCouponDao</code></b>
 * <p/>
 * UserCoupon的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-28 19:59:09.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface UserCouponDao extends JpaRepository<UserCoupon, Long>, JpaSpecificationExecutor<UserCoupon> {

    List<UserCoupon> findUserCouponsByUserId(Long userId);

}
