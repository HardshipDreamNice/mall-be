/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.usercoupon;

import com.torinosrc.model.view.usercoupon.UserCouponView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>UserCoupon</code></b>
* <p/>
* UserCoupon的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-28 19:59:09.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface UserCouponService extends BaseService<UserCouponView> {

    /**
     * 给指定用户发放指定优惠券分类下的所有优惠券
     * @param userId
     * @param couponCategoryId
     */
    void saveUserCouponsByUserIdAndCategoryId(Long userId, Long couponCategoryId);

}
