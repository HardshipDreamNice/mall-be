/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.productdetail.impl;

import com.torinosrc.dao.shoppingcartdetail.ShoppingCartDetailDao;
import com.torinosrc.dao.shopproduct.ShopProductDao;
import com.torinosrc.dao.shopproductdetail.ShopProductDetailDao;
import com.torinosrc.model.entity.shoppingcartdetail.ShoppingCartDetail;
import com.torinosrc.model.entity.shopproductdetail.ShopProductDetail;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.core.convert.converter.Converter;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.productdetail.ProductDetailDao;
import com.torinosrc.dao.shop.ShopDao;
import com.torinosrc.dao.shoptree.ShopTreeDao;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.entity.shop.Shop;
import com.torinosrc.model.entity.shoptree.ShopTree;
import com.torinosrc.model.view.productdetail.ProductDetailView;
import com.torinosrc.service.productdetail.ProductDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
* <b><code>ProductDetailImpl</code></b>
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
public class ProductDetailServiceImpl implements ProductDetailService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ProductDetailServiceImpl.class);

    @Autowired
    private ProductDetailDao productDetailDao;

    @Autowired
    private ShopTreeDao shopTreeDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private ShopProductDetailDao shopProductDetailDao;

    @Autowired
    private ShoppingCartDetailDao shoppingCartDetailDao;

    private BeanCopier daoToViewCopier = BeanCopier.create(ProductDetail.class, ProductDetailView.class,
            false);

    @Override
    public ProductDetailView getEntity(long id) {
        // 获取Entity
        ProductDetail productDetail = productDetailDao.getOne(id);

        // 复制Dao层属性到view属性
        ProductDetailView productDetailView = new ProductDetailView();
        TorinoSrcBeanUtils.copyBean(productDetail, productDetailView);
        return productDetailView;
    }

    @Override
    public Page<ProductDetailView> getEntitiesByParms(ProductDetailView productDetailView, int currentPage, int pageSize) {
        Specification<ProductDetail> productDetailSpecification = new Specification<ProductDetail>() {
            @Override
            public Predicate toPredicate(Root<ProductDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,productDetailView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ProductDetail> productDetails = productDetailDao.findAll(productDetailSpecification, pageable);

        // 转换成View对象并返回
        return productDetails.map(productDetail->{
            ProductDetailView productDetailView1 = new ProductDetailView();
            TorinoSrcBeanUtils.copyBean(productDetail, productDetailView1);
            return productDetailView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return productDetailDao.count();
    }

    @Override
    public List<ProductDetailView> findAll() {
        List<ProductDetailView> productDetailViews = new ArrayList<>();
        List<ProductDetail> productDetails = productDetailDao.findAll();
        for (ProductDetail productDetail : productDetails){
            ProductDetailView productDetailView = new ProductDetailView();
            TorinoSrcBeanUtils.copyBean(productDetail, productDetailView);
            productDetailViews.add(productDetailView);
        }
        return productDetailViews;
    }

    @Override
    public ProductDetailView saveEntity(ProductDetailView productDetailView) {
        // 保存的业务逻辑
        ProductDetail productDetail = new ProductDetail();
        TorinoSrcBeanUtils.copyBean(productDetailView, productDetail);
        // user数据库映射传给dao进行存储
        productDetail.setCreateTime(System.currentTimeMillis());
        productDetail.setUpdateTime(System.currentTimeMillis());
        productDetail.setEnabled(1);
        productDetailDao.save(productDetail);
        TorinoSrcBeanUtils.copyBean(productDetail,productDetailView);
        return productDetailView;
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntity(long id) {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setId(id);
        // 上架明细
        List<ShopProductDetail> shopProductDetails=shopProductDetailDao.findByProductDetailId(id);
        shopProductDetailDao.deleteInBatch(shopProductDetails);
        // 购物车明细
        List<ShoppingCartDetail> shoppingCartDetails=shoppingCartDetailDao.findByProductDetailId(id);
        shoppingCartDetailDao.deleteInBatch(shoppingCartDetails);
        productDetailDao.delete(productDetail);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
//        List<ProductDetail> productDetails = new ArrayList<>();
        for(String entityId : entityIds){
//            ProductDetail productDetail = new ProductDetail();
//            productDetail.setId(Long.valueOf(entityId));
//            productDetails.add(productDetail);
            this.deleteEntity((Long.valueOf(entityId)));
        }
//        productDetailDao.deleteInBatch(productDetails);
    }

    @Override
    public void updateEntity(ProductDetailView productDetailView) {
        Specification<ProductDetail> productDetailSpecification = Optional.ofNullable(productDetailView).map( s -> {
            return new Specification<ProductDetail>() {
                @Override
                public Predicate toPredicate(Root<ProductDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("ProductDetailView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<ProductDetail> productDetailOptionalBySearch = productDetailDao.findOne(productDetailSpecification);
        productDetailOptionalBySearch.map(productDetailBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(productDetailView,productDetailBySearch);
            productDetailBySearch.setUpdateTime(System.currentTimeMillis());
            productDetailDao.save(productDetailBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + productDetailView.getId() + "的数据记录"));
    }

    /**
     * 计算商品利润
     *
     * @param productDetailView
     * @param shopId
     * @return
     */
    @Override
    public long calculateProductProfit(ProductDetailView productDetailView,long shopId) {
        ProductDetail productDetail = productDetailDao.getOne(productDetailView.getId());
        //佣金金额
        long commission =productDetail.getCommission();
        //商品差价=卖出价-商品市场价
        long profit=productDetailView.getPrice()-productDetail.getPrice();
        long incomeAmount=commission+profit;

        //1、根据shopId查询存在几层分销商
        ShopTree shopTree = shopTreeDao.findByShopId(shopId);
        if(!ObjectUtils.isEmpty(shopTree) && shopTree.getParentShopId()!=0){
            ShopTree shopTreeLevel1 = shopTreeDao.findByShopId(shopTree.getParentShopId());
            if(!ObjectUtils.isEmpty(shopTreeLevel1) && shopTreeLevel1.getParentShopId()!=0){
                //三级分销商
                Shop shop = shopDao.getOne(shopTreeLevel1.getParentShopId());
                Long level3Commission =(long) shop.getPercent3()*commission/100;
                incomeAmount=level3Commission+profit;

            }else{
                //二级分销商
                Shop shop = shopDao.getOne(shopTree.getParentShopId());
                Long level2Commission =(shop.getPercent2()+shop.getPercent3())*commission/100;
                incomeAmount=level2Commission+profit;

            }
        }

        return incomeAmount;
    }

    @Override
    public Page<ProductDetailView> getProductDetailsByParams(ProductDetailView productDetailView, int currentPage, int pageSize) {
        Specification<ProductDetail> productDetailSpecification = new Specification<ProductDetail>() {
            @Override
            public Predicate toPredicate(Root<ProductDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                // search
                if(!StringUtils.isEmpty(productDetailView.getSearch())){
                    predicates.add(criteriaBuilder.like(root.get("product").get("name").as(String.class), "%" + productDetailView.getSearch() + "%"));
                }
                //分类Id
                if(!ObjectUtils.isEmpty(productDetailView.getCategoryId())){
                    predicates.add(criteriaBuilder.equal(root.get("product").get("category").get("id").as(Long.class), productDetailView.getCategoryId()));
                }
                // enabled
                if(!ObjectUtils.isEmpty(productDetailView.getEnabled())){
                    predicates.add(criteriaBuilder.equal(root.get("enabled").as(Integer.class), productDetailView.getEnabled()));
                }

                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        // 设置排序
        int sortType = productDetailView.getSortType();
        Sort sort;
        switch (sortType) {
            case 1:
                sort = new Sort(Sort.Direction.ASC, "price");
                break;
            case 2:
                sort = new Sort(Sort.Direction.DESC, "commission");
                break;
            case 3:
                sort = new Sort(Sort.Direction.ASC, "commission");
                break;
            case 4:
                sort = new Sort(Sort.Direction.DESC, "updateTime");
                break;
            case 5:
                sort = new Sort(Sort.Direction.ASC, "updateTime");
                break;
            default:
                sort = new Sort(Sort.Direction.DESC, "price");
                break;
        }

        Pageable pageable = PageRequest.of(currentPage, pageSize, sort);
        Page<ProductDetail> productDetails = productDetailDao.findAll(productDetailSpecification, pageable);

        // 转换成View对象并返回
        return productDetails.map(productDetail->{
            ProductDetailView productDetailView1 = new ProductDetailView();
            TorinoSrcBeanUtils.copyBean(productDetail, productDetailView1);
            return productDetailView1;
        });
    }
}
