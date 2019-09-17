/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.couponproduct.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.couponproduct.CouponProductDao;
import com.torinosrc.model.entity.couponproduct.CouponProduct;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.view.couponproduct.CouponProductView;
import com.torinosrc.model.view.product.ProductView;
import com.torinosrc.service.couponproduct.CouponProductService;
import com.torinosrc.service.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
* <b><code>CouponProductImpl</code></b>
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
public class CouponProductServiceImpl implements CouponProductService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(CouponProductServiceImpl.class);

    @Autowired
    private CouponProductDao couponProductDao;

    @Autowired
    private ProductService productService;

    @Override
    public CouponProductView getEntity(long id) {
        // 获取Entity
        CouponProduct couponProduct = couponProductDao.getOne(id);
        // 复制Dao层属性到view属性
        CouponProductView couponProductView = new CouponProductView();
        TorinoSrcBeanUtils.copyBean(couponProduct, couponProductView);
        return couponProductView;
    }

    @Override
    public Page<CouponProductView> getEntitiesByParms(CouponProductView couponProductView, int currentPage, int pageSize) {
        Specification<CouponProduct> couponProductSpecification = new Specification<CouponProduct>() {
            @Override
            public Predicate toPredicate(Root<CouponProduct> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,couponProductView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<CouponProduct> couponProducts = couponProductDao.findAll(couponProductSpecification, pageable);

        // 转换成View对象并返回
        return couponProducts.map(couponProduct->{
            CouponProductView couponProductView1 = new CouponProductView();
            TorinoSrcBeanUtils.copyBean(couponProduct, couponProductView1);
            return couponProductView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return couponProductDao.count();
    }

    @Override
    public List<CouponProductView> findAll() {
        List<CouponProductView> couponProductViews = new ArrayList<>();
        List<CouponProduct> couponProducts = couponProductDao.findAll();
        for (CouponProduct couponProduct : couponProducts){
            CouponProductView couponProductView = new CouponProductView();
            TorinoSrcBeanUtils.copyBean(couponProduct, couponProductView);
            couponProductViews.add(couponProductView);
        }
        return couponProductViews;
    }

    @Override
    public CouponProductView saveEntity(CouponProductView couponProductView) {
        // 保存的业务逻辑
        CouponProduct couponProduct = new CouponProduct();
        TorinoSrcBeanUtils.copyBean(couponProductView, couponProduct);
        // user数据库映射传给dao进行存储
        couponProduct.setCreateTime(System.currentTimeMillis());
        couponProduct.setUpdateTime(System.currentTimeMillis());
        couponProduct.setEnabled(1);
        couponProductDao.save(couponProduct);
        TorinoSrcBeanUtils.copyBean(couponProduct,couponProductView);
        return couponProductView;
    }

    @Override
    public void deleteEntity(long id) {
        CouponProduct couponProduct = new CouponProduct();
        couponProduct.setId(id);
        couponProductDao.delete(couponProduct);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<CouponProduct> couponProducts = new ArrayList<>();
        for(String entityId : entityIds){
            CouponProduct couponProduct = new CouponProduct();
            couponProduct.setId(Long.valueOf(entityId));
            couponProducts.add(couponProduct);
        }
        couponProductDao.deleteInBatch(couponProducts);
    }

    @Override
    public void updateEntity(CouponProductView couponProductView) {
        Specification<CouponProduct> couponProductSpecification = Optional.ofNullable(couponProductView).map( s -> {
            return new Specification<CouponProduct>() {
                @Override
                public Predicate toPredicate(Root<CouponProduct> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("CouponProductView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<CouponProduct> couponProductOptionalBySearch = couponProductDao.findOne(couponProductSpecification);
        couponProductOptionalBySearch.map(couponProductBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(couponProductView,couponProductBySearch);
            couponProductBySearch.setUpdateTime(System.currentTimeMillis());
            couponProductDao.save(couponProductBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + couponProductView.getId() + "的数据记录"));
    }

    @Override
    public List<ProductView> getProductsByCouponId(Long couponId) {

        List<CouponProduct> couponProducts = couponProductDao.findByCouponId(couponId);

        List<ProductView> productViews = new ArrayList<>();
        for (CouponProduct couponProduct : couponProducts) {
            Long productId = couponProduct.getProductId();
            ProductView productView = productService.getEntity(productId);
            productViews.add(productView);
        }

        return productViews;
    }
}
