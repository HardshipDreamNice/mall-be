/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.couponcategory.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.couponcategory.CouponCategoryDao;
import com.torinosrc.model.entity.couponcategory.CouponCategory;
import com.torinosrc.model.view.couponcategory.CouponCategoryView;
import com.torinosrc.service.couponcategory.CouponCategoryService;
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
* <b><code>CouponCategoryImpl</code></b>
* <p/>
* CouponCategory的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-28 19:57:12.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class CouponCategoryServiceImpl implements CouponCategoryService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(CouponCategoryServiceImpl.class);

    @Autowired
    private CouponCategoryDao couponCategoryDao;

    @Override
    public CouponCategoryView getEntity(long id) {
        // 获取Entity
        CouponCategory couponCategory = couponCategoryDao.getOne(id);
        // 复制Dao层属性到view属性
        CouponCategoryView couponCategoryView = new CouponCategoryView();
        TorinoSrcBeanUtils.copyBean(couponCategory, couponCategoryView);
        return couponCategoryView;
    }

    @Override
    public Page<CouponCategoryView> getEntitiesByParms(CouponCategoryView couponCategoryView, int currentPage, int pageSize) {
        Specification<CouponCategory> couponCategorySpecification = new Specification<CouponCategory>() {
            @Override
            public Predicate toPredicate(Root<CouponCategory> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,couponCategoryView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<CouponCategory> couponCategorys = couponCategoryDao.findAll(couponCategorySpecification, pageable);

        // 转换成View对象并返回
        return couponCategorys.map(couponCategory->{
            CouponCategoryView couponCategoryView1 = new CouponCategoryView();
            TorinoSrcBeanUtils.copyBean(couponCategory, couponCategoryView1);
            return couponCategoryView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return couponCategoryDao.count();
    }

    @Override
    public List<CouponCategoryView> findAll() {
        List<CouponCategoryView> couponCategoryViews = new ArrayList<>();
        List<CouponCategory> couponCategorys = couponCategoryDao.findAll();
        for (CouponCategory couponCategory : couponCategorys){
            CouponCategoryView couponCategoryView = new CouponCategoryView();
            TorinoSrcBeanUtils.copyBean(couponCategory, couponCategoryView);
            couponCategoryViews.add(couponCategoryView);
        }
        return couponCategoryViews;
    }

    @Override
    public CouponCategoryView saveEntity(CouponCategoryView couponCategoryView) {
        // 保存的业务逻辑
        CouponCategory couponCategory = new CouponCategory();
        TorinoSrcBeanUtils.copyBean(couponCategoryView, couponCategory);
        // user数据库映射传给dao进行存储
        couponCategory.setCreateTime(System.currentTimeMillis());
        couponCategory.setUpdateTime(System.currentTimeMillis());
        couponCategory.setEnabled(1);
        couponCategoryDao.save(couponCategory);
        TorinoSrcBeanUtils.copyBean(couponCategory,couponCategoryView);
        return couponCategoryView;
    }

    @Override
    public void deleteEntity(long id) {
        CouponCategory couponCategory = new CouponCategory();
        couponCategory.setId(id);
        couponCategoryDao.delete(couponCategory);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<CouponCategory> couponCategorys = new ArrayList<>();
        for(String entityId : entityIds){
            CouponCategory couponCategory = new CouponCategory();
            couponCategory.setId(Long.valueOf(entityId));
            couponCategorys.add(couponCategory);
        }
        couponCategoryDao.deleteInBatch(couponCategorys);
    }

    @Override
    public void updateEntity(CouponCategoryView couponCategoryView) {
        Specification<CouponCategory> couponCategorySpecification = Optional.ofNullable(couponCategoryView).map( s -> {
            return new Specification<CouponCategory>() {
                @Override
                public Predicate toPredicate(Root<CouponCategory> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("CouponCategoryView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<CouponCategory> couponCategoryOptionalBySearch = couponCategoryDao.findOne(couponCategorySpecification);
        couponCategoryOptionalBySearch.map(couponCategoryBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(couponCategoryView,couponCategoryBySearch);
            couponCategoryBySearch.setUpdateTime(System.currentTimeMillis());
            couponCategoryDao.save(couponCategoryBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + couponCategoryView.getId() + "的数据记录"));
    }
}
