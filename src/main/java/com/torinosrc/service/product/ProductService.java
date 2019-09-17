/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.product;

import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.view.common.CustomPage;
import com.torinosrc.model.view.product.*;
import com.torinosrc.model.view.shopproduct.ShopProductView;
import com.torinosrc.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>Product</code></b>
* <p/>
* Product的具体实现
* <p/>
* <b>Creation Time:</b> 2018-06-06 11:17:58.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface ProductService extends BaseService<ProductView> {

    public CustomPage<ProductView> findProductListForWx(ProductView productView, int currentPage, int pageSize);
    public ProductView getProductProfit(long id,long shopId);

    /**
     * 获取某一分类下的所有商品
     * @param productView
     * @return
     */
    ProductPageView getProductsByCategoryId(ProductView productView, int pageNumber, int pageSize);

    /**
     * 处理商品列表，如果是已上架商品，则将上架商品的id添加进支
     * 计算商品的最高价、最低价、最高利润、最低利润
     * @param products
     * @return
     */
    List<ProductView> dealProducts(List<Product> products, Long shopId);
    public ProductView saveProductProductDetail(ProductView productView);

    IndexProductView getIndexProductView();

    /**
     * 通过ID去获取商品表信息
     * @param id
     * @return
     */
    ProductView getCopyEntity(Long id);

    /**
     * 通过 productId 和 shopId 获取商品或上架商品，并存放到IntegratedProductView
     * @param productId
     * @param shopId
     * @return
     */
    IntegratedProductView getProductByProductIdAndShopId(Long productId, Long shopId);

    /**
     * 后台管理端查询用的
     * @param productView
     * @param currentPage
     * @param pageSize
     * @return
     */

    ProductPageView getEntitiesByParmsSearch(ProductView productView, int currentPage, int pageSize);

    /**
     * 删除上架商品
     * @param productId
     */
    void deleteShopProduct(Long productId);

    /**
     * 管理端更新商品信息用的
     * @param productView
     */
    void updateEntityByProductView(ProductView productView);

    /**
     *  保存助力购商品
     * @param productReviceView
     */
    void saveBoostProduct(ProductReviceView productReviceView);

    /**
     * 删除助力购商品
     * @param id
     */
    void deleteBoostProduct(Long id);

    /**
     * 批量删除助力购商品
     * @param ids
     */
    void deleteBoostProductEntities(String ids);
}
