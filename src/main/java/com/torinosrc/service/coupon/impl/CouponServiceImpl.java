/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.coupon.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.DateUtils;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.coupon.CouponDao;
import com.torinosrc.model.entity.coupon.Coupon;
import com.torinosrc.model.view.coupon.CouponView;
import com.torinosrc.service.coupon.CouponService;
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
 * <b><code>CouponImpl</code></b>
 * <p/>
 * Coupon的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-28 19:57:38.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public class CouponServiceImpl implements CouponService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(CouponServiceImpl.class);

    @Autowired
    private CouponDao couponDao;

    @Override
    public CouponView getEntity(long id) {
        // 获取Entity
        Coupon coupon = couponDao.getOne(id);
        // 复制Dao层属性到view属性
        CouponView couponView = new CouponView();
        TorinoSrcBeanUtils.copyBean(coupon, couponView);
        return couponView;
    }

    @Override
    public Page<CouponView> getEntitiesByParms(CouponView couponView, int currentPage, int pageSize) {
        Specification<Coupon> couponSpecification = new Specification<Coupon>() {
            @Override
            public Predicate toPredicate(Root<Coupon> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root, criteriaQuery, criteriaBuilder, couponView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Coupon> coupons = couponDao.findAll(couponSpecification, pageable);

        // 转换成View对象并返回
        return coupons.map(coupon -> {
            CouponView couponView1 = new CouponView();
            TorinoSrcBeanUtils.copyBean(coupon, couponView1);
            return couponView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return couponDao.count();
    }

    @Override
    public List<CouponView> findAll() {
        List<CouponView> couponViews = new ArrayList<>();
        List<Coupon> coupons = couponDao.findAll();
        for (Coupon coupon : coupons) {
            CouponView couponView = new CouponView();
            TorinoSrcBeanUtils.copyBean(coupon, couponView);
            couponViews.add(couponView);
        }
        return couponViews;
    }

    @Override
    public CouponView saveEntity(CouponView couponView) {
        // 保存的业务逻辑
        Coupon coupon = new Coupon();
        TorinoSrcBeanUtils.copyBean(couponView, coupon);
        // user数据库映射传给dao进行存储
        coupon.setCreateTime(System.currentTimeMillis());
        coupon.setUpdateTime(System.currentTimeMillis());
        coupon.setEnabled(1);
        coupon.setRemainingNumber(coupon.getTotalNumber());

        couponDao.save(coupon);
        TorinoSrcBeanUtils.copyBean(coupon, couponView);
        return couponView;
    }

    @Override
    public void deleteEntity(long id) {
        Coupon coupon = new Coupon();
        coupon.setId(id);
        couponDao.delete(coupon);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteEntities(String ids) {
        String[] entityIds = TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<Coupon> coupons = new ArrayList<>();
        for (String entityId : entityIds) {
            Coupon coupon = new Coupon();
            coupon.setId(Long.valueOf(entityId));
            coupons.add(coupon);
        }
        couponDao.deleteInBatch(coupons);
    }

    @Override
    public void updateEntity(CouponView couponView) {
        Specification<Coupon> couponSpecification = Optional.ofNullable(couponView).map(s -> {
            return new Specification<Coupon>() {
                @Override
                public Predicate toPredicate(Root<Coupon> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(() -> new IllegalArgumentException("CouponView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<Coupon> couponOptionalBySearch = couponDao.findOne(couponSpecification);
        couponOptionalBySearch.map(couponBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(couponView, couponBySearch);
            couponBySearch.setUpdateTime(System.currentTimeMillis());
            couponDao.save(couponBySearch);
            return "";
        }).orElseThrow(() -> new TorinoSrcServiceException("无法找到id为" + couponView.getId() + "的数据记录"));
    }

}
