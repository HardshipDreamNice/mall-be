/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.boostproduct.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.boostproduct.BoostProductDao;
import com.torinosrc.model.entity.boostproduct.BoostProduct;
import com.torinosrc.model.view.boostproduct.BoostProductView;
import com.torinosrc.service.boostproduct.BoostProductService;
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
* <b><code>BoostProductImpl</code></b>
* <p/>
* BoostProduct的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-30 16:09:26.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class BoostProductServiceImpl implements BoostProductService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(BoostProductServiceImpl.class);

    @Autowired
    private BoostProductDao boostProductDao;

    @Override
    public BoostProductView getEntity(long id) {
        // 获取Entity
        BoostProduct boostProduct = boostProductDao.getOne(id);
        // 复制Dao层属性到view属性
        BoostProductView boostProductView = new BoostProductView();
        TorinoSrcBeanUtils.copyBean(boostProduct, boostProductView);
        return boostProductView;
    }

    @Override
    public Page<BoostProductView> getEntitiesByParms(BoostProductView boostProductView, int currentPage, int pageSize) {
        Specification<BoostProduct> boostProductSpecification = new Specification<BoostProduct>() {
            @Override
            public Predicate toPredicate(Root<BoostProduct> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,boostProductView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<BoostProduct> boostProducts = boostProductDao.findAll(boostProductSpecification, pageable);

        // 转换成View对象并返回
        return boostProducts.map(boostProduct->{
            BoostProductView boostProductView1 = new BoostProductView();
            TorinoSrcBeanUtils.copyBean(boostProduct, boostProductView1);
            return boostProductView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return boostProductDao.count();
    }

    @Override
    public List<BoostProductView> findAll() {
        List<BoostProductView> boostProductViews = new ArrayList<>();
        List<BoostProduct> boostProducts = boostProductDao.findAll();
        for (BoostProduct boostProduct : boostProducts){
            BoostProductView boostProductView = new BoostProductView();
            TorinoSrcBeanUtils.copyBean(boostProduct, boostProductView);
            boostProductViews.add(boostProductView);
        }
        return boostProductViews;
    }

    @Override
    public BoostProductView saveEntity(BoostProductView boostProductView) {
        // 保存的业务逻辑
        BoostProduct boostProduct = new BoostProduct();
        TorinoSrcBeanUtils.copyBean(boostProductView, boostProduct);
        // user数据库映射传给dao进行存储
        boostProduct.setCreateTime(System.currentTimeMillis());
        boostProduct.setUpdateTime(System.currentTimeMillis());
        boostProduct.setEnabled(1);
        boostProductDao.save(boostProduct);
        TorinoSrcBeanUtils.copyBean(boostProduct,boostProductView);
        return boostProductView;
    }

    @Override
    public void deleteEntity(long id) {
        BoostProduct boostProduct = new BoostProduct();
        boostProduct.setId(id);
        boostProductDao.delete(boostProduct);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<BoostProduct> boostProducts = new ArrayList<>();
        for(String entityId : entityIds){
            BoostProduct boostProduct = new BoostProduct();
            boostProduct.setId(Long.valueOf(entityId));
            boostProducts.add(boostProduct);
        }
        boostProductDao.deleteInBatch(boostProducts);
    }

    @Override
    public void updateEntity(BoostProductView boostProductView) {
        Specification<BoostProduct> boostProductSpecification = Optional.ofNullable(boostProductView).map( s -> {
            return new Specification<BoostProduct>() {
                @Override
                public Predicate toPredicate(Root<BoostProduct> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("BoostProductView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<BoostProduct> boostProductOptionalBySearch = boostProductDao.findOne(boostProductSpecification);
        boostProductOptionalBySearch.map(boostProductBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(boostProductView,boostProductBySearch);
            boostProductBySearch.setUpdateTime(System.currentTimeMillis());
            boostProductDao.save(boostProductBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + boostProductView.getId() + "的数据记录"));
    }
}
