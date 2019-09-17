/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.shopproduct.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.product.ProductDao;
import com.torinosrc.dao.productdetail.ProductDetailDao;
import com.torinosrc.dao.shoppingcartdetail.ShoppingCartDetailDao;
import com.torinosrc.dao.shopproduct.ShopProductDao;
import com.torinosrc.dao.shopproductdetail.ShopProductDetailDao;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.entity.shoppingcart.ShoppingCart;
import com.torinosrc.model.entity.shoppingcartdetail.ShoppingCartDetail;
import com.torinosrc.model.entity.shopproduct.ShopProduct;
import com.torinosrc.model.entity.shopproductdetail.ShopProductDetail;
import com.torinosrc.model.view.common.CustomPage;
import com.torinosrc.model.view.shopproduct.ShopProductView;
import com.torinosrc.model.view.shopproductdetail.ShopProductDetailView;
import com.torinosrc.response.json.JSON;
import com.torinosrc.service.product.ProductService;
import com.torinosrc.service.shopproduct.ShopProductService;
import com.torinosrc.service.shopproductdetail.ShopProductDetailService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

/**
* <b><code>ShopProductImpl</code></b>
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
public class ShopProductServiceImpl implements ShopProductService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ShopProductServiceImpl.class);

    @Autowired
    private ShopProductDao shopProductDao;

    @Autowired
    private ShopProductDetailDao shopProductDetailDao;

    @Autowired
    private ProductDetailDao productDetailDao;

    @Autowired
    private ShopProductDetailService shopProductDetailService;

    @Autowired
    private ShoppingCartDetailDao shoppingCartDetailDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductService productService;

    @Override
    public ShopProductView getEntity(long id) {
        // 获取Entity
        ShopProduct shopProduct = shopProductDao.getOne(id);
        // 复制Dao层属性到view属性
        ShopProductView shopProductView = new ShopProductView();
        TorinoSrcBeanUtils.copyBean(shopProduct, shopProductView);
        return shopProductView;
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public ShopProductView getEntityAutomaticCompletion(long id) {
        // 获取Entity
        ShopProduct shopProduct = shopProductDao.getOne(id);
        // 复制Dao层属性到view属性
        ShopProductView shopProductView = new ShopProductView();
        TorinoSrcBeanUtils.copyBean(shopProduct, shopProductView);

        Set<ShopProductDetail> shopProductDetailSet = shopProductView.getShopProductDetails();

        // 更新上架商品的零售价与建议价
        for(ShopProductDetail shopProductDetail: shopProductDetailSet){
            Integer advisePrice= shopProductDetail.getProductDetail().getPrice().intValue();
            Integer uppestPrice=shopProductDetail.getProductDetail().getUppestPrice().intValue();
            if(shopProductDetail.getAdvisePrice()!=advisePrice||shopProductDetail.getUppestPrice()!=uppestPrice){
                shopProductDetail.setUpdateTime(System.currentTimeMillis());
                shopProductDetail.setAdvisePrice(advisePrice);
                shopProductDetail.setUppestPrice(uppestPrice);
                shopProductDetailDao.save(shopProductDetail);
            }
        }

        List<ProductDetail> productDetailList = productDetailDao.findByProductId(shopProductView.getProduct().getId());

        if (shopProductDetailSet.size() < productDetailList.size()) {
            //商店中有一些还没上架的商品详情
            Set<Long> shopProductDetailIdSet = new HashSet<>();
            for (ShopProductDetail shopProductDetail : shopProductDetailSet) {
                shopProductDetailIdSet.add(shopProductDetail.getProductDetail().getId());
            }

            ShopProductDetail shopProductDetail;
            ShopProductDetailView shopProductDetailView;
            //缺少的商品详情
            for (ProductDetail productDetail : productDetailList) {
                if (!shopProductDetailIdSet.contains(productDetail.getId())) {
                    shopProductDetail = new ShopProductDetail();
                    shopProductDetailView = new ShopProductDetailView();

                    shopProductDetailView.setShopProduct(shopProduct);
                    shopProductDetailView.setProductDetail(productDetail);
                    shopProductDetailView.setUppestPrice(productDetail.getUppestPrice().intValue());
                    shopProductDetailView.setAdvisePrice(productDetail.getPrice().intValue());
                    shopProductDetailView.setProductPrice(productDetail.getPrice().intValue());
                    shopProductDetailView = shopProductDetailService.saveEntity(shopProductDetailView);
                    TorinoSrcBeanUtils.copyBean(shopProductDetailView, shopProductDetail);
                }
            }
        }

        return shopProductView;
    }

    @Override
    public Page<ShopProductView> getEntitiesByParms(ShopProductView shopProductView, int currentPage, int pageSize) {
        Specification<ShopProduct> shopProductSpecification = new Specification<ShopProduct>() {
            @Override
            public Predicate toPredicate(Root<ShopProduct> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,shopProductView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ShopProduct> shopProducts = shopProductDao.findAll(shopProductSpecification, pageable);

        // 转换成View对象并返回
        return shopProducts.map(shopProduct->{
            ShopProductView shopProductView1 = new ShopProductView();
            TorinoSrcBeanUtils.copyBean(shopProduct, shopProductView1);
            return shopProductView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return shopProductDao.count();
    }

    @Override
    public List<ShopProductView> findAll() {
        List<ShopProductView> shopProductViews = new ArrayList<>();
        List<ShopProduct> shopProducts = shopProductDao.findAll();
        for (ShopProduct shopProduct : shopProducts){
            ShopProductView shopProductView = new ShopProductView();
            TorinoSrcBeanUtils.copyBean(shopProduct, shopProductView);
            shopProductViews.add(shopProductView);
        }
        return shopProductViews;
    }

    @Override
    public ShopProductView saveEntity(ShopProductView shopProductView) {
        // 保存的业务逻辑
        ShopProduct shopProduct = new ShopProduct();
        TorinoSrcBeanUtils.copyBean(shopProductView, shopProduct);
        // user数据库映射传给dao进行存储
        shopProduct.setCreateTime(System.currentTimeMillis());
        shopProduct.setUpdateTime(System.currentTimeMillis());
        shopProduct.setEnabled(1);
        shopProductDao.save(shopProduct);
        TorinoSrcBeanUtils.copyBean(shopProduct,shopProductView);
        return shopProductView;
    }

    @Override
    public void deleteEntity(long id) {
        ShopProduct shopProduct = new ShopProduct();
        shopProduct.setId(id);
        shopProductDao.delete(shopProduct);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<ShopProduct> shopProducts = new ArrayList<>();
        for(String entityId : entityIds){
            ShopProduct shopProduct = new ShopProduct();
            shopProduct.setId(Long.valueOf(entityId));
            shopProducts.add(shopProduct);

            this.deleteShopProductDetails(Long.valueOf(entityId));
            shopProductDao.delete(shopProduct);
        }
//        shopProductDao.deleteInBatch(shopProducts);
    }

    /**
     * 根据店铺商品Id 删除店铺商品详情
     * @param shopProductId
     */
    private void deleteShopProductDetails(Long shopProductId) {
        shopProductDetailDao.deleteShopProductDetailsByShopProductId(shopProductId);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void updateEntity(ShopProductView shopProductView) {
        Specification<ShopProduct> shopProductSpecification = Optional.ofNullable(shopProductView).map( s -> {
            return new Specification<ShopProduct>() {
                @Override
                public Predicate toPredicate(Root<ShopProduct> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("ShopProductView is null"));

        // 获取原有的属性，把不变的属性覆盖
        Optional<ShopProduct> shopProductOptionalBySearch = shopProductDao.findOne(shopProductSpecification);
        shopProductOptionalBySearch.map(shopProductBySearch -> {
            // 下架商品
//            if(!ObjectUtils.isEmpty(shopProductView.getOnSale())&&shopProductView.getOnSale()==0){
//                shopProductView.setRecommendReason(productDao.getOne(shopProductBySearch.getProduct().getId()).getName());
//            }
            TorinoSrcBeanUtils.copyBeanExcludeNull(shopProductView,shopProductBySearch);
            shopProductBySearch.setUpdateTime(System.currentTimeMillis());
            shopProductDao.save(shopProductBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + shopProductView.getId() + "的数据记录"));
    }

    @Override
    public CustomPage<ShopProductView> findShopProductViewsForWx(ShopProductView shopProductView1,int pageNumber,int pageSize){

        Long shopId = shopProductView1.getShopId();

        List<ShopProductView> shopProductViews = new ArrayList<>();

        CustomPage<ShopProductView> customPage = new CustomPage<>();

        Sort sort = new Sort(Sort.Direction.DESC,"create_time"); //创建时间降序排序

        Pageable pageable = new PageRequest(pageNumber,pageSize,sort);

        Page<ShopProduct> shopProductViewsPage = shopProductDao.findShopProductsByShopIdForWx(shopId, pageable);

        for(ShopProduct shopProduct : shopProductViewsPage.getContent()){
            ShopProductView shopProductView = new ShopProductView();
            TorinoSrcBeanUtils.copyBean(shopProduct, shopProductView);
            //获取最高价和最低价
//            shopProductView = this.getShopProductUpPriceAndDownProce(shopProductView);
//            shopProductViews.add(shopProductView);
            Map map=shopProductDao.findProductsPriceRange(shopId,shopProductView.getProduct().getId());
            shopProductView.setPriceIntervalUp(Integer.valueOf(map.get("max_price").toString()));
            shopProductView.setPriceIntervaDown(Integer.valueOf(map.get("min_price").toString()));
            shopProductViews.add(shopProductView);
        }
        customPage.setContent(shopProductViews);

        customPage.setTotalElements(shopProductViewsPage.getTotalElements());

        return customPage;
    }

    @Override
    public ShopProductView getEntityByShopIdAndProductId(Long shopId, Long productId) {
        // 获取Entity
        ShopProduct shopProduct = shopProductDao.findByShopIdAndProductId(shopId, productId);

        if (ObjectUtils.isEmpty(shopProduct)) {
            return new ShopProductView();
        }

        ShopProductView shopProductView = new ShopProductView();
        TorinoSrcBeanUtils.copyBean(shopProduct, shopProductView);

        return shopProductView;
    }

    @Transactional(rollbackOn = { Exception.class })
    @Override
    public ShopProductView saveShopProductAndDetails(ShopProductView shopProductView) {

        Long shopId = shopProductView.getShopId();
        Long productId = shopProductView.getProduct().getId();
        Long productDetailId = shopProductView.getProductDetailId();
        ShopProduct shopProduct = shopProductDao.findByShopIdAndProductId(shopId, productId);
        if (!ObjectUtils.isEmpty(shopProduct) && !ObjectUtils.isEmpty(shopProduct.getId())) {
            //已经有此商品，将之删除
            List<ShopProductDetail> shopProductDetailList = shopProductDetailDao.getShopProductDetailsByShopProductId(shopProduct.getId());
            for (ShopProductDetail shopProductDetail : shopProductDetailList) {
                shopProductDetailDao.delete(shopProductDetail);
            }
            shopProductDao.delete(shopProduct);
        }

        // 保存的业务逻辑
        ShopProductView shopProductView1 =  this.saveEntity(shopProductView);
        if (ObjectUtils.isEmpty(shopProduct)) {
            shopProduct = new ShopProduct();
        }
        TorinoSrcBeanUtils.copyBean(shopProductView1, shopProduct);
        //新建商店商品详情
        List<ProductDetail> productDetailList = productDetailDao.findByProductId(productId);
        ShopProductDetailView shopProductDetailView;
        for (ProductDetail productDetail : productDetailList) {
            shopProductDetailView = new ShopProductDetailView();

            if (productDetailId.longValue() == productDetail.getId().longValue()) {
                //是用户选中的商品详情
                shopProductDetailView.setProductPrice(shopProductView.getShopProductDetailPrice());
            } else {
                shopProductDetailView.setProductPrice(productDetail.getPrice().intValue());
            }

            shopProductDetailView.setAdvisePrice(productDetail.getPrice().intValue());
            shopProductDetailView.setUppestPrice(productDetail.getUppestPrice().intValue());
            shopProductDetailView.setShopProduct(shopProduct);
            shopProductDetailView.setProductDetail(productDetail);
            // 更新购物车的
            ShopProductDetailView shopProductDetailView1=shopProductDetailService.saveEntity(shopProductDetailView);
            updateShoppingcartDetail(shopProductDetailView1);
        }

        return shopProductView1;
    }


    private ShopProductView getShopProductUpPriceAndDownProce(ShopProductView shopProductView){

            Set<ShopProductDetail> shopProductDetails = shopProductView.getShopProductDetails();

            int upperPrice = 0;

            int downPrice = 0;

            if(shopProductDetails.size() ==0 || ObjectUtils.isEmpty(shopProductDetails)){

                shopProductView.setPriceIntervalUp(upperPrice);
                shopProductView.setPriceIntervaDown(downPrice);
                return shopProductView;
            }

            if(shopProductDetails.size() ==1){

                for(ShopProductDetail shopProductDetail : shopProductDetails){

                    if(ObjectUtils.isEmpty(shopProductDetail.getProductPrice())){

                        shopProductDetail.setProductPrice(0);
                    }

                    upperPrice = shopProductDetail.getProductPrice();
                    downPrice  = shopProductDetail.getProductPrice();
                }

                shopProductView.setPriceIntervalUp(upperPrice);
                shopProductView.setPriceIntervaDown(downPrice);
                return shopProductView;
            }

            int i = 0;

            for(ShopProductDetail shopProductDetail : shopProductDetails){

                if(ObjectUtils.isEmpty(shopProductDetail.getProductPrice())){

                    shopProductDetail.setProductPrice(0);
                }
                if(i==0){

                    upperPrice = shopProductDetail.getProductPrice();
                    downPrice  = shopProductDetail.getProductPrice();
                    i++;
                    continue;

                }

                if(shopProductDetail.getProductPrice() > upperPrice){

                    upperPrice = shopProductDetail.getProductPrice();
                }

                if(shopProductDetail.getProductPrice() < downPrice){

                    downPrice = shopProductDetail.getProductPrice();
                }


            }

            shopProductView.setPriceIntervalUp(upperPrice);
            shopProductView.setPriceIntervaDown(downPrice);
            return shopProductView;
    }


    @Override
    public void updateShoppingcartDetail(ShopProductDetailView shopProductDetailView) {
        if (!ObjectUtils.isEmpty(shoppingCartDetailDao.findByProductDetailIdAndShopId(shopProductDetailView.getProductDetail().getId(), shopProductDetailView.getShopProduct().getShopId()))) {
            List<ShoppingCartDetail> shoppingCartDetailList=shoppingCartDetailDao.findByProductDetailIdAndShopId(shopProductDetailView.getProductDetail().getId(), shopProductDetailView.getShopProduct().getShopId());
            for(ShoppingCartDetail shoppingCartDetail:shoppingCartDetailList){
                shoppingCartDetail.setShopProductDetailId(shopProductDetailView.getId());
                shoppingCartDetail.setUpdateTime(System.currentTimeMillis());
                shoppingCartDetailDao.save(shoppingCartDetail);
            }

        }

    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void updateEntityAndDelete(ShopProductView shopProductView) {

        Long shopProductId=shopProductView.getId();
        ShopProduct shopProduct=shopProductDao.findById(shopProductId).get();

        // 下架商品(删除上架操作)
        if(!ObjectUtils.isEmpty(shopProductView.getOnSale())&&shopProductView.getOnSale()==0){
            Long productId=shopProduct.getProduct().getId();
            productService.deleteShopProduct(productId);
        }

    }

}
