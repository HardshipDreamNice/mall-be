/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.shopproductdetail;

import com.torinosrc.model.entity.shopproductdetail.ShopProductDetail;
import com.torinosrc.model.view.shopproductdetail.ShopProductDetailView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>ShopProductDetail</code></b>
* <p/>
* ShopProductDetail的具体实现
* <p/>
* <b>Creation Time:</b> 2018-07-16 17:27:33.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface ShopProductDetailService extends BaseService<ShopProductDetailView> {

    List<ShopProductDetailView> getShopProductDetailViewsByShopProductId(Long shopProductId);

    /**
     * 通过 商店商品Id 和 商品详情Id 查询唯一一个商店商品详情
     * @param shopProductId
     * @param productDetailId
     * @return
     */
    ShopProductDetailView getEntityByShopProductIdAndProductDetailId(Long shopProductId, Long productDetailId);

}
