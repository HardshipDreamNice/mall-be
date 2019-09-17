/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.productfreereceivedetail.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.productfreereceivedetail.ProductFreeReceiveDetailDao;
import com.torinosrc.model.entity.productfreereceivedetail.ProductFreeReceiveDetail;
import com.torinosrc.model.view.productfreereceivedetail.ProductFreeReceiveDetailView;
import com.torinosrc.service.productfreereceivedetail.ProductFreeReceiveDetailService;
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
* <b><code>ProductFreeReceiveDetailImpl</code></b>
* <p/>
* ProductFreeReceiveDetail的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-27 19:53:01.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class ProductFreeReceiveDetailServiceImpl implements ProductFreeReceiveDetailService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ProductFreeReceiveDetailServiceImpl.class);

    @Autowired
    private ProductFreeReceiveDetailDao productFreeReceiveDetailDao;

    @Override
    public ProductFreeReceiveDetailView getEntity(long id) {
        // 获取Entity
        ProductFreeReceiveDetail productFreeReceiveDetail = productFreeReceiveDetailDao.getOne(id);
        // 复制Dao层属性到view属性
        ProductFreeReceiveDetailView productFreeReceiveDetailView = new ProductFreeReceiveDetailView();
        TorinoSrcBeanUtils.copyBean(productFreeReceiveDetail, productFreeReceiveDetailView);
        return productFreeReceiveDetailView;
    }

    @Override
    public Page<ProductFreeReceiveDetailView> getEntitiesByParms(ProductFreeReceiveDetailView productFreeReceiveDetailView, int currentPage, int pageSize) {
        Specification<ProductFreeReceiveDetail> productFreeReceiveDetailSpecification = new Specification<ProductFreeReceiveDetail>() {
            @Override
            public Predicate toPredicate(Root<ProductFreeReceiveDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,productFreeReceiveDetailView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ProductFreeReceiveDetail> productFreeReceiveDetails = productFreeReceiveDetailDao.findAll(productFreeReceiveDetailSpecification, pageable);

        // 转换成View对象并返回
        return productFreeReceiveDetails.map(productFreeReceiveDetail->{
            ProductFreeReceiveDetailView productFreeReceiveDetailView1 = new ProductFreeReceiveDetailView();
            TorinoSrcBeanUtils.copyBean(productFreeReceiveDetail, productFreeReceiveDetailView1);
            return productFreeReceiveDetailView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return productFreeReceiveDetailDao.count();
    }

    @Override
    public List<ProductFreeReceiveDetailView> findAll() {
        List<ProductFreeReceiveDetailView> productFreeReceiveDetailViews = new ArrayList<>();
        List<ProductFreeReceiveDetail> productFreeReceiveDetails = productFreeReceiveDetailDao.findAll();
        for (ProductFreeReceiveDetail productFreeReceiveDetail : productFreeReceiveDetails){
            ProductFreeReceiveDetailView productFreeReceiveDetailView = new ProductFreeReceiveDetailView();
            TorinoSrcBeanUtils.copyBean(productFreeReceiveDetail, productFreeReceiveDetailView);
            productFreeReceiveDetailViews.add(productFreeReceiveDetailView);
        }
        return productFreeReceiveDetailViews;
    }

    @Override
    public ProductFreeReceiveDetailView saveEntity(ProductFreeReceiveDetailView productFreeReceiveDetailView) {
        // 保存的业务逻辑
        ProductFreeReceiveDetail productFreeReceiveDetail = new ProductFreeReceiveDetail();
        TorinoSrcBeanUtils.copyBean(productFreeReceiveDetailView, productFreeReceiveDetail);
        // user数据库映射传给dao进行存储
        productFreeReceiveDetail.setCreateTime(System.currentTimeMillis());
        productFreeReceiveDetail.setUpdateTime(System.currentTimeMillis());
        productFreeReceiveDetail.setEnabled(1);
        productFreeReceiveDetailDao.save(productFreeReceiveDetail);
        TorinoSrcBeanUtils.copyBean(productFreeReceiveDetail,productFreeReceiveDetailView);
        return productFreeReceiveDetailView;
    }

    @Override
    public void deleteEntity(long id) {
        ProductFreeReceiveDetail productFreeReceiveDetail = new ProductFreeReceiveDetail();
        productFreeReceiveDetail.setId(id);
        productFreeReceiveDetailDao.delete(productFreeReceiveDetail);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<ProductFreeReceiveDetail> productFreeReceiveDetails = new ArrayList<>();
        for(String entityId : entityIds){
            ProductFreeReceiveDetail productFreeReceiveDetail = new ProductFreeReceiveDetail();
            productFreeReceiveDetail.setId(Long.valueOf(entityId));
            productFreeReceiveDetails.add(productFreeReceiveDetail);
        }
        productFreeReceiveDetailDao.deleteInBatch(productFreeReceiveDetails);
    }

    @Override
    public void updateEntity(ProductFreeReceiveDetailView productFreeReceiveDetailView) {
        Specification<ProductFreeReceiveDetail> productFreeReceiveDetailSpecification = Optional.ofNullable(productFreeReceiveDetailView).map( s -> {
            return new Specification<ProductFreeReceiveDetail>() {
                @Override
                public Predicate toPredicate(Root<ProductFreeReceiveDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("ProductFreeReceiveDetailView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<ProductFreeReceiveDetail> productFreeReceiveDetailOptionalBySearch = productFreeReceiveDetailDao.findOne(productFreeReceiveDetailSpecification);
        productFreeReceiveDetailOptionalBySearch.map(productFreeReceiveDetailBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(productFreeReceiveDetailView,productFreeReceiveDetailBySearch);
            productFreeReceiveDetailBySearch.setUpdateTime(System.currentTimeMillis());
            productFreeReceiveDetailDao.save(productFreeReceiveDetailBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + productFreeReceiveDetailView.getId() + "的数据记录"));
    }
}
