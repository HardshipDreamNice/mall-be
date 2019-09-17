/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.usercoupon.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.DateUtils;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.coupon.CouponDao;
import com.torinosrc.dao.membershipgrade.MembershipGradeDao;
import com.torinosrc.dao.shop.ShopDao;
import com.torinosrc.dao.usercoupon.UserCouponDao;
import com.torinosrc.model.entity.coupon.Coupon;
import com.torinosrc.model.entity.membershipgrade.MembershipGrade;
import com.torinosrc.model.entity.shop.Shop;
import com.torinosrc.model.entity.usercoupon.UserCoupon;
import com.torinosrc.model.view.usercoupon.UserCouponView;
import com.torinosrc.service.usercoupon.UserCouponService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <b><code>UserCouponImpl</code></b>
 * <p/>
 * UserCoupon的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-28 19:59:09.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public class UserCouponServiceImpl implements UserCouponService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(UserCouponServiceImpl.class);

    @Autowired
    private UserCouponDao userCouponDao;

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private MembershipGradeDao membershipGradeDao;

    private final static Object obj1 = new Object();

    @Override
    public UserCouponView getEntity(long id) {
        // 获取Entity
        UserCoupon userCoupon = userCouponDao.getOne(id);
        // 复制Dao层属性到view属性
        UserCouponView userCouponView = new UserCouponView();
        TorinoSrcBeanUtils.copyBean(userCoupon, userCouponView);
        return userCouponView;
    }

    @Override
    public Page<UserCouponView> getEntitiesByParms(UserCouponView userCouponView, int currentPage, int pageSize) {
        Specification<UserCoupon> userCouponSpecification = new Specification<UserCoupon>() {
            @Override
            public Predicate toPredicate(Root<UserCoupon> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root, criteriaQuery, criteriaBuilder, userCouponView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<UserCoupon> userCoupons = userCouponDao.findAll(userCouponSpecification, pageable);

        // 转换成View对象并返回
        return userCoupons.map(userCoupon -> {
            UserCouponView userCouponView1 = new UserCouponView();
            TorinoSrcBeanUtils.copyBean(userCoupon, userCouponView1);
            return userCouponView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return userCouponDao.count();
    }

    @Override
    public List<UserCouponView> findAll() {
        List<UserCouponView> userCouponViews = new ArrayList<>();
        List<UserCoupon> userCoupons = userCouponDao.findAll();
        for (UserCoupon userCoupon : userCoupons) {
            UserCouponView userCouponView = new UserCouponView();
            TorinoSrcBeanUtils.copyBean(userCoupon, userCouponView);
            userCouponViews.add(userCouponView);
        }
        return userCouponViews;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public UserCouponView saveEntity(UserCouponView userCouponView) {
        synchronized (obj1) {
            Long couponId = userCouponView.getCouponId();
            Long userId = userCouponView.getUserId();
            Coupon coupon = couponDao.getOne(couponId);

            //检测优惠券是否可用及用户是否有资格领取
            checkCoupon(userId, coupon);
            // 获取用户优惠券的过期时间
            Long userCouponExpirationTime = getUserCouponExpirationTime(coupon);

            // 保存的业务逻辑
            UserCoupon userCoupon = new UserCoupon();
            TorinoSrcBeanUtils.copyBean(userCouponView, userCoupon);

            // user数据库映射传给dao进行存储
            userCoupon.setCreateTime(System.currentTimeMillis());
            userCoupon.setUpdateTime(System.currentTimeMillis());
            userCoupon.setEnabled(1);
            userCoupon.setExpiredTime(userCouponExpirationTime);
            userCoupon.setStatus(0);

            // 优惠券剩余数量 - 1
            couponDao.updateCouponRemainingNumberMinusOne(userCouponView.getCouponId());
            // 保存优惠券领取记录
            userCouponDao.save(userCoupon);

            TorinoSrcBeanUtils.copyBean(userCoupon, userCouponView);
            return userCouponView;
        }
    }

    /**
     * 检测优惠券是否可用及用户是否有资格领取
     * 如果优惠券不可用或用户没有资格领取，抛出 Exception
     * @param userId
     * @param coupon
     */
    private void checkCoupon(Long userId, Coupon coupon) {
        if (ObjectUtils.isEmpty(coupon)) {
            throw new TorinoSrcServiceException("优惠券不存在");
        }

        if (coupon.getEnabled().intValue() == 0) {
            throw new TorinoSrcServiceException("该优惠券已下架");
        }

        if (coupon.getRemainingNumber() <= 0) {
            throw new TorinoSrcServiceException("该优惠券已经被领完了");
        }

        long currentTimeMillis = System.currentTimeMillis();
        boolean isExpired = coupon.getValidPeriodType().intValue() == 0 && currentTimeMillis > coupon.getEndTime();
        if (isExpired) {
            throw new TorinoSrcServiceException("活动已经截止");
        }

        Long couponeMembershipGradeId = coupon.getMembershipGradeId();
        if (couponeMembershipGradeId != 0) {
            Shop shop = shopDao.findShopByUserId(userId);
            Integer shopLevel = shop.getShopLevel();
            MembershipGrade membershipGrade = membershipGradeDao.getOne(couponeMembershipGradeId);
            Integer couponLevel = membershipGrade.getGrade();
            if (shopLevel < couponLevel) {
                throw new TorinoSrcServiceException("不够等级领取当前优惠券");
            } else {
                // no need to do anything...
            }
        } else {
            // couponeMembershipGradeId = 0，所有用户都能领取
        }


        Integer availableNumber = coupon.getAvailableNumber();
        List<UserCoupon> userCoupons = userCouponDao.findUserCouponsByUserId(userId);
        if (userCoupons.size() >= availableNumber && availableNumber != 0) {
            throw new TorinoSrcServiceException("每个账号只能领 " + availableNumber + " 张优惠券");
        }
    }

    /**
     * 获取用户优惠券的过期时间
     * @param coupon
     * @return
     */
    private Long getUserCouponExpirationTime(Coupon coupon) {
        long currentTimeMillis = System.currentTimeMillis();
        long oneDayMillis = 60000 * 60 * 24L;
        long validTime;
        long expirationTime;
        Integer validPeriodType = coupon.getValidPeriodType();
        switch (validPeriodType) {
            case 0:
                // 固定日期
                expirationTime = coupon.getEndTime();
                break;
            case 1:
                // 当日开始，从领取时间开始计算
                validTime = coupon.getValidDay() * oneDayMillis;
                expirationTime = currentTimeMillis + validTime;
                break;
            case 2:
                // 次日开始，从领取时间开始计算
                validTime = coupon.getValidDay() * oneDayMillis;
                long currentDayEndTime = DateUtils.getCurrentDayEndTime();
                expirationTime = currentDayEndTime + validTime;
                break;
            default:
                throw new TorinoSrcServiceException("请选择正确的优惠券");
        }
        return expirationTime;
    }

    @Override
    public void deleteEntity(long id) {
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setId(id);
        userCouponDao.delete(userCoupon);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteEntities(String ids) {
        String[] entityIds = TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<UserCoupon> userCoupons = new ArrayList<>();
        for (String entityId : entityIds) {
            UserCoupon userCoupon = new UserCoupon();
            userCoupon.setId(Long.valueOf(entityId));
            userCoupons.add(userCoupon);
        }
        userCouponDao.deleteInBatch(userCoupons);
    }

    @Override
    public void updateEntity(UserCouponView userCouponView) {
        Specification<UserCoupon> userCouponSpecification = Optional.ofNullable(userCouponView).map(s -> {
            return new Specification<UserCoupon>() {
                @Override
                public Predicate toPredicate(Root<UserCoupon> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(() -> new IllegalArgumentException("UserCouponView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<UserCoupon> userCouponOptionalBySearch = userCouponDao.findOne(userCouponSpecification);
        userCouponOptionalBySearch.map(userCouponBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(userCouponView, userCouponBySearch);
            userCouponBySearch.setUpdateTime(System.currentTimeMillis());
            userCouponDao.save(userCouponBySearch);
            return "";
        }).orElseThrow(() -> new TorinoSrcServiceException("无法找到id为" + userCouponView.getId() + "的数据记录"));
    }

    @Override
    public void saveUserCouponsByUserIdAndCategoryId(Long userId, Long couponCategoryId) {

        List<Coupon> coupons = couponDao.getCouponsByCouponCategoryId(couponCategoryId);
        for (Coupon coupon : coupons) {
            UserCouponView userCouponView = new UserCouponView();
            userCouponView.setCouponId(coupon.getId());
            userCouponView.setUserId(userId);
            this.saveEntity(userCouponView);
        }

    }
}
