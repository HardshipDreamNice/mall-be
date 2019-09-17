/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.shopproduct;

import com.torinosrc.model.entity.shopproduct.ShopProduct;
import com.torinosrc.model.view.common.CustomPage;
import com.torinosrc.model.view.shopproduct.ShopProductView;
import com.torinosrc.model.view.shopproductdetail.ShopProductDetailView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>ShopProduct</code></b>
* <p/>
* ShopProduct的具体实现
* <p/>
* <b>Creation Time:</b> 2018-07-11 19:22:11.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface ShopProductService extends BaseService<ShopProductView> {

    public CustomPage<ShopProductView> findShopProductViewsForWx(ShopProductView shopProductView1, int pageNumber, int pageSize);

    ShopProductView getEntityByShopIdAndProductId(Long shopId, Long productId);

    ShopProductView saveShopProductAndDetails(ShopProductView shopProductView);

    /**
     * 获取商店商品时，自动补全还没上架的商品详情
     * @param id
     * @return
     */
    ShopProductView getEntityAutomaticCompletion(long id);

    // 更新购物车详情
    public void updateShoppingcartDetail(ShopProductDetailView shopProductDetailView);

    // 下架商品（删除，更新购物车）
    public void updateEntityAndDelete(ShopProductView shopProductView);
}
