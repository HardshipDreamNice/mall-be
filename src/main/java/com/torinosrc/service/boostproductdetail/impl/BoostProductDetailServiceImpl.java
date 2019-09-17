/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.boostproductdetail.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.boostproductdetail.BoostProductDetailDao;
import com.torinosrc.model.entity.boostproductdetail.BoostProductDetail;
import com.torinosrc.model.view.boostproductdetail.BoostProductDetailView;
import com.torinosrc.service.boostproductdetail.BoostProductDetailService;
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
* <b><code>BoostProductDetailImpl</code></b>
* <p/>
* BoostProductDetail的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-30 16:10:27.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class BoostProductDetailServiceImpl implements BoostProductDetailService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(BoostProductDetailServiceImpl.class);

    @Autowired
    private BoostProductDetailDao boostProductDetailDao;

    @Override
    public BoostProductDetailView getEntity(long id) {
        // 获取Entity
        BoostProductDetail boostProductDetail = boostProductDetailDao.getOne(id);
        // 复制Dao层属性到view属性
        BoostProductDetailView boostProductDetailView = new BoostProductDetailView();
        TorinoSrcBeanUtils.copyBean(boostProductDetail, boostProductDetailView);
        return boostProductDetailView;
    }

    @Override
    public Page<BoostProductDetailView> getEntitiesByParms(BoostProductDetailView boostProductDetailView, int currentPage, int pageSize) {
        Specification<BoostProductDetail> boostProductDetailSpecification = new Specification<BoostProductDetail>() {
            @Override
            public Predicate toPredicate(Root<BoostProductDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,boostProductDetailView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<BoostProductDetail> boostProductDetails = boostProductDetailDao.findAll(boostProductDetailSpecification, pageable);

        // 转换成View对象并返回
        return boostProductDetails.map(boostProductDetail->{
            BoostProductDetailView boostProductDetailView1 = new BoostProductDetailView();
            TorinoSrcBeanUtils.copyBean(boostProductDetail, boostProductDetailView1);
            return boostProductDetailView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return boostProductDetailDao.count();
    }

    @Override
    public List<BoostProductDetailView> findAll() {
        List<BoostProductDetailView> boostProductDetailViews = new ArrayList<>();
        List<BoostProductDetail> boostProductDetails = boostProductDetailDao.findAll();
        for (BoostProductDetail boostProductDetail : boostProductDetails){
            BoostProductDetailView boostProductDetailView = new BoostProductDetailView();
            TorinoSrcBeanUtils.copyBean(boostProductDetail, boostProductDetailView);
            boostProductDetailViews.add(boostProductDetailView);
        }
        return boostProductDetailViews;
    }

    @Override
    public BoostProductDetailView saveEntity(BoostProductDetailView boostProductDetailView) {
        // 保存的业务逻辑
        BoostProductDetail boostProductDetail = new BoostProductDetail();
        TorinoSrcBeanUtils.copyBean(boostProductDetailView, boostProductDetail);
        // user数据库映射传给dao进行存储
        boostProductDetail.setCreateTime(System.currentTimeMillis());
        boostProductDetail.setUpdateTime(System.currentTimeMillis());
        boostProductDetail.setEnabled(1);
        boostProductDetailDao.save(boostProductDetail);
        TorinoSrcBeanUtils.copyBean(boostProductDetail,boostProductDetailView);
        return boostProductDetailView;
    }

    @Override
    public void deleteEntity(long id) {
        BoostProductDetail boostProductDetail = new BoostProductDetail();
        boostProductDetail.setId(id);
        boostProductDetailDao.delete(boostProductDetail);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<BoostProductDetail> boostProductDetails = new ArrayList<>();
        for(String entityId : entityIds){
            BoostProductDetail boostProductDetail = new BoostProductDetail();
            boostProductDetail.setId(Long.valueOf(entityId));
            boostProductDetails.add(boostProductDetail);
        }
        boostProductDetailDao.deleteInBatch(boostProductDetails);
    }

    @Override
    public void updateEntity(BoostProductDetailView boostProductDetailView) {
        Specification<BoostProductDetail> boostProductDetailSpecification = Optional.ofNullable(boostProductDetailView).map( s -> {
            return new Specification<BoostProductDetail>() {
                @Override
                public Predicate toPredicate(Root<BoostProductDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("BoostProductDetailView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<BoostProductDetail> boostProductDetailOptionalBySearch = boostProductDetailDao.findOne(boostProductDetailSpecification);
        boostProductDetailOptionalBySearch.map(boostProductDetailBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(boostProductDetailView,boostProductDetailBySearch);
            boostProductDetailBySearch.setUpdateTime(System.currentTimeMillis());
            boostProductDetailDao.save(boostProductDetailBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + boostProductDetailView.getId() + "的数据记录"));
    }
}
