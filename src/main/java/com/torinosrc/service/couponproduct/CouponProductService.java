/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.couponproduct;

import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.view.couponproduct.CouponProductView;
import com.torinosrc.model.view.product.ProductView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>CouponProduct</code></b>
* <p/>
* CouponProduct的具体实现
* <p/>
* <b>Creation Time:</b> 2018-12-06 15:30:48.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface CouponProductService extends BaseService<CouponProductView> {

    /**
     * 通过 优惠券id 获取它的可用商品
     * @param couponId
     * @return
     */
    List<ProductView> getProductsByCouponId(Long couponId);
}
