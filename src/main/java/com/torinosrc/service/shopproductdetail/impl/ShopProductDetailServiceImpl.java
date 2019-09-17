/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.shopproductdetail.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.productdetail.ProductDetailDao;
import com.torinosrc.dao.shopproduct.ShopProductDao;
import com.torinosrc.dao.shopproductdetail.ShopProductDetailDao;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.entity.shoppingcart.ShoppingCart;
import com.torinosrc.model.entity.shopproduct.ShopProduct;
import com.torinosrc.model.entity.shopproductdetail.ShopProductDetail;
import com.torinosrc.model.view.shopproductdetail.ShopProductDetailView;
import com.torinosrc.service.shopproductdetail.ShopProductDetailService;
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
* <b><code>ShopProductDetailImpl</code></b>
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
public class ShopProductDetailServiceImpl implements ShopProductDetailService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ShopProductDetailServiceImpl.class);

    @Autowired
    private ShopProductDetailDao shopProductDetailDao;

    @Autowired
    private ShopProductDao shopProductDao;

    @Autowired
    private ProductDetailDao productDetailDao;

    @Override
    public ShopProductDetailView getEntity(long id) {
        // 获取Entity
        ShopProductDetail shopProductDetail = shopProductDetailDao.getOne(id);
        // 复制Dao层属性到view属性
        ShopProductDetailView shopProductDetailView = new ShopProductDetailView();
        TorinoSrcBeanUtils.copyBean(shopProductDetail, shopProductDetailView);
        return shopProductDetailView;
    }

    @Override
    public Page<ShopProductDetailView> getEntitiesByParms(ShopProductDetailView shopProductDetailView, int currentPage, int pageSize) {
        Specification<ShopProductDetail> shopProductDetailSpecification = new Specification<ShopProductDetail>() {
            @Override
            public Predicate toPredicate(Root<ShopProductDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,shopProductDetailView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ShopProductDetail> shopProductDetails = shopProductDetailDao.findAll(shopProductDetailSpecification, pageable);

        // 转换成View对象并返回
        return shopProductDetails.map(shopProductDetail->{
            ShopProductDetailView shopProductDetailView1 = new ShopProductDetailView();
            TorinoSrcBeanUtils.copyBean(shopProductDetail, shopProductDetailView1);
            return shopProductDetailView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return shopProductDetailDao.count();
    }

    @Override
    public List<ShopProductDetailView> findAll() {
        List<ShopProductDetailView> shopProductDetailViews = new ArrayList<>();
        List<ShopProductDetail> shopProductDetails = shopProductDetailDao.findAll();
        for (ShopProductDetail shopProductDetail : shopProductDetails){
            ShopProductDetailView shopProductDetailView = new ShopProductDetailView();
            TorinoSrcBeanUtils.copyBean(shopProductDetail, shopProductDetailView);
            shopProductDetailViews.add(shopProductDetailView);
        }
        return shopProductDetailViews;
    }

    @Override
    public ShopProductDetailView saveEntity(ShopProductDetailView shopProductDetailView) {
        // 保存的业务逻辑
        ShopProductDetail shopProductDetail = new ShopProductDetail();
        TorinoSrcBeanUtils.copyBean(shopProductDetailView, shopProductDetail);
        // user数据库映射传给dao进行存储
        shopProductDetail.setCreateTime(System.currentTimeMillis());
        shopProductDetail.setUpdateTime(System.currentTimeMillis());
        shopProductDetail.setEnabled(1);
        shopProductDetailDao.save(shopProductDetail);
        TorinoSrcBeanUtils.copyBean(shopProductDetail,shopProductDetailView);
        return shopProductDetailView;
    }

    @Override
    public void deleteEntity(long id) {
        ShopProductDetail shopProductDetail = new ShopProductDetail();
        shopProductDetail.setId(id);
        shopProductDetailDao.delete(shopProductDetail);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<ShopProductDetail> shopProductDetails = new ArrayList<>();
        for(String entityId : entityIds){
            ShopProductDetail shopProductDetail = new ShopProductDetail();
            shopProductDetail.setId(Long.valueOf(entityId));
            shopProductDetails.add(shopProductDetail);
        }
        shopProductDetailDao.deleteInBatch(shopProductDetails);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void updateEntity(ShopProductDetailView shopProductDetailView) {
        Specification<ShopProductDetail> shopProductDetailSpecification = Optional.ofNullable(shopProductDetailView).map( s -> {
            return new Specification<ShopProductDetail>() {
                @Override
                public Predicate toPredicate(Root<ShopProductDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("ShopProductDetailView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<ShopProductDetail> shopProductDetailOptionalBySearch = shopProductDetailDao.findOne(shopProductDetailSpecification);
        shopProductDetailOptionalBySearch.map(shopProductDetailBySearch -> {
            if(!ObjectUtils.isEmpty(shopProductDetailView.getRecommendReason())){
               ShopProduct shopProduct = shopProductDao.getOne(shopProductDetailBySearch.getShopProduct().getId());
               shopProduct.setRecommendReason(shopProductDetailView.getRecommendReason());
               shopProduct.setOnSale(1);
               shopProduct.setUpdateTime(System.currentTimeMillis());
               shopProductDao.save(shopProduct);
            }
            TorinoSrcBeanUtils.copyBeanExcludeNull(shopProductDetailView,shopProductDetailBySearch);
            shopProductDetailBySearch.setUpdateTime(System.currentTimeMillis());
            shopProductDetailDao.save(shopProductDetailBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + shopProductDetailView.getId() + "的数据记录"));
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public List<ShopProductDetailView> getShopProductDetailViewsByShopProductId(Long shopProductId) {
        ShopProduct shopProduct = shopProductDao.getOne(shopProductId);
        if (ObjectUtils.isEmpty(shopProduct) || ObjectUtils.isEmpty(shopProduct.getId())) {
            return new ArrayList<>();
        }
        Long productId = shopProduct.getProduct().getId();
        List<ProductDetail> productDetailList = productDetailDao.findByProductId(productId);
        List<ShopProductDetail> shopProductDetailList = shopProductDetailDao.getShopProductDetailsByShopProductId(shopProductId);

        // 更新上架商品的零售价与建议价
        for(ShopProductDetail shopProductDetail: shopProductDetailList ){
            Integer advisePrice= shopProductDetail.getProductDetail().getPrice().intValue();
            Integer uppestPrice=shopProductDetail.getProductDetail().getUppestPrice().intValue();
            if(shopProductDetail.getAdvisePrice()!=advisePrice||shopProductDetail.getUppestPrice()!=uppestPrice){
                shopProductDetail.setUpdateTime(System.currentTimeMillis());
                shopProductDetail.setAdvisePrice(advisePrice);
                shopProductDetail.setUppestPrice(uppestPrice);
                shopProductDetailDao.save(shopProductDetail);
            }
        }

        if (productDetailList.size() > shopProductDetailList.size()) {
            //商店中有一些还没上架的商品详情
            Long productDetailId;
            Set<Long> shopProductDetailIdSet = new HashSet<>();
            for (ShopProductDetail shopProductDetail : shopProductDetailList) {
                productDetailId = shopProductDetail.getProductDetail().getId();
                shopProductDetailIdSet.add(productDetailId);
            }

            //缺少的商品详情
            ShopProductDetailView shopProductDetailView;
            for (ProductDetail productDetail : productDetailList) {
                if (!shopProductDetailIdSet.contains(productDetail.getId())) {
                    shopProductDetailView = new ShopProductDetailView();
                    shopProductDetailView.setProductDetail(productDetail);
                    shopProductDetailView.setShopProduct(shopProduct);
//                    shopProductDetailView.setId(productId);
                    shopProductDetailView.setUppestPrice(productDetail.getUppestPrice().intValue());
                    shopProductDetailView.setAdvisePrice(productDetail.getPrice().intValue());
                    shopProductDetailView.setProductPrice(productDetail.getPrice().intValue());
                    this.saveEntity(shopProductDetailView);
                }
            }
        }

        shopProductDetailList = shopProductDetailDao.getShopProductDetailsByShopProductId(shopProductId);
        List<ShopProductDetailView> shopProductDetailViewList = new ArrayList<>();
        ShopProductDetailView shopProductDetailView;
        for (ShopProductDetail shopProductDetail : shopProductDetailList) {
            shopProductDetailView = new ShopProductDetailView();
            TorinoSrcBeanUtils.copyBean(shopProductDetail, shopProductDetailView);
            shopProductDetailViewList.add(shopProductDetailView);
        }

        return shopProductDetailViewList;
    }

    @Override
    public ShopProductDetailView getEntityByShopProductIdAndProductDetailId(Long shopProductId, Long productDetailId) {

        ShopProductDetail shopProductDetail = shopProductDetailDao.findByShopProductIdAndProductDetailId(shopProductId, productDetailId);

        if (ObjectUtils.isEmpty(shopProductDetail)) {
            return new ShopProductDetailView();
        }

        ShopProductDetailView shopProductDetailView = new ShopProductDetailView() ;
        TorinoSrcBeanUtils.copyBean(shopProductDetail, shopProductDetailView);

        return shopProductDetailView;
    }
}
