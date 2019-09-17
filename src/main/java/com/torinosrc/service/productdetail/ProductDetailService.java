/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.productdetail;

import com.torinosrc.model.view.productdetail.ProductDetailView;
import com.torinosrc.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>ProductDetail</code></b>
* <p/>
* ProductDetail的具体实现
* <p/>
* <b>Creation Time:</b> 2018-06-06 11:28:08.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface ProductDetailService extends BaseService<ProductDetailView> {
    long calculateProductProfit(ProductDetailView productDetailView,long shopId);

    /**
     * 根据参数获取商品详情列表
     * @param productDetailView
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<ProductDetailView> getProductDetailsByParams(ProductDetailView productDetailView, int currentPage, int pageSize);

}
