/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.usersignlog.impl;

import com.torinosrc.commons.constants.MallConstant;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.DateUtils;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.coupon.CouponDao;
import com.torinosrc.dao.usersignlog.UserSignLogDao;
import com.torinosrc.model.entity.coupon.Coupon;
import com.torinosrc.model.entity.usersignlog.UserSignLog;
import com.torinosrc.model.view.usercoupon.UserCouponView;
import com.torinosrc.model.view.usersignlog.UserSignLogView;
import com.torinosrc.service.coupon.CouponService;
import com.torinosrc.service.usercoupon.UserCouponService;
import com.torinosrc.service.usersignlog.UserSignLogService;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <b><code>UserSignLogImpl</code></b>
 * <p/>
 * UserSignLog的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-29 16:44:39.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public class UserSignLogServiceImpl implements UserSignLogService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(UserSignLogServiceImpl.class);

    @Autowired
    private UserSignLogDao userSignLogDao;

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private UserCouponService userCouponService;

    @Override
    public UserSignLogView getEntity(long id) {
        // 获取Entity
        UserSignLog userSignLog = userSignLogDao.getOne(id);
        // 复制Dao层属性到view属性
        UserSignLogView userSignLogView = new UserSignLogView();
        TorinoSrcBeanUtils.copyBean(userSignLog, userSignLogView);
        return userSignLogView;
    }

    @Override
    public Page<UserSignLogView> getEntitiesByParms(UserSignLogView userSignLogView, int currentPage, int pageSize) {
        Specification<UserSignLog> userSignLogSpecification = new Specification<UserSignLog>() {
            @Override
            public Predicate toPredicate(Root<UserSignLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root, criteriaQuery, criteriaBuilder, userSignLogView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<UserSignLog> userSignLogs = userSignLogDao.findAll(userSignLogSpecification, pageable);

        // 转换成View对象并返回
        return userSignLogs.map(userSignLog -> {
            UserSignLogView userSignLogView1 = new UserSignLogView();
            TorinoSrcBeanUtils.copyBean(userSignLog, userSignLogView1);
            return userSignLogView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return userSignLogDao.count();
    }

    @Override
    public List<UserSignLogView> findAll() {
        List<UserSignLogView> userSignLogViews = new ArrayList<>();
        List<UserSignLog> userSignLogs = userSignLogDao.findAll();
        for (UserSignLog userSignLog : userSignLogs) {
            UserSignLogView userSignLogView = new UserSignLogView();
            TorinoSrcBeanUtils.copyBean(userSignLog, userSignLogView);
            userSignLogViews.add(userSignLogView);
        }
        return userSignLogViews;
    }

    @Override
    public UserSignLogView saveEntity(UserSignLogView userSignLogView) {
        // 保存的业务逻辑
        UserSignLog userSignLog = new UserSignLog();
        TorinoSrcBeanUtils.copyBean(userSignLogView, userSignLog);
        // user数据库映射传给dao进行存储
        userSignLog.setCreateTime(System.currentTimeMillis());
        userSignLog.setUpdateTime(System.currentTimeMillis());
        userSignLog.setEnabled(1);
        userSignLogDao.save(userSignLog);
        TorinoSrcBeanUtils.copyBean(userSignLog, userSignLogView);
        return userSignLogView;
    }

    @Override
    public void deleteEntity(long id) {
        UserSignLog userSignLog = new UserSignLog();
        userSignLog.setId(id);
        userSignLogDao.delete(userSignLog);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteEntities(String ids) {
        String[] entityIds = TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<UserSignLog> userSignLogs = new ArrayList<>();
        for (String entityId : entityIds) {
            UserSignLog userSignLog = new UserSignLog();
            userSignLog.setId(Long.valueOf(entityId));
            userSignLogs.add(userSignLog);
        }
        userSignLogDao.deleteInBatch(userSignLogs);
    }

    @Override
    public void updateEntity(UserSignLogView userSignLogView) {
        Specification<UserSignLog> userSignLogSpecification = Optional.ofNullable(userSignLogView).map(s -> {
            return new Specification<UserSignLog>() {
                @Override
                public Predicate toPredicate(Root<UserSignLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(() -> new IllegalArgumentException("UserSignLogView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<UserSignLog> userSignLogOptionalBySearch = userSignLogDao.findOne(userSignLogSpecification);
        userSignLogOptionalBySearch.map(userSignLogBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(userSignLogView, userSignLogBySearch);
            userSignLogBySearch.setUpdateTime(System.currentTimeMillis());
            userSignLogDao.save(userSignLogBySearch);
            return "";
        }).orElseThrow(() -> new TorinoSrcServiceException("无法找到id为" + userSignLogView.getId() + "的数据记录"));
    }

    @Override
    public UserSignLogView saveUserSignLog(UserSignLogView userSignLogView) {

        Long signUserId = userSignLogView.getSignUserId();
        UserSignLogView userSignLogViewReturn;

        long currentTimeMillis = System.currentTimeMillis();
        long currentDayStartTime = com.torinosrc.commons.utils.DateUtils.getCurrentDayStartTime();
        long currentDayEndTime = com.torinosrc.commons.utils.DateUtils.getCurrentDayEndTime();

        // 今天的签到记录
        UserSignLog userSignLog = userSignLogDao.findBySignUserIdAndSignTimeBetween(signUserId, currentDayStartTime, currentDayEndTime);

        if (!ObjectUtils.isEmpty(userSignLog) && !ObjectUtils.isEmpty(userSignLog.getHelpUserId())) {
            throw new TorinoSrcServiceException("你今天已经签到过啦");
        }

        // 已签到但还没有好友助力签
        if (!ObjectUtils.isEmpty(userSignLog) && ObjectUtils.isEmpty(userSignLog.getHelpUserId())) {
            userSignLogViewReturn = new UserSignLogView();
            TorinoSrcBeanUtils.copyBean(userSignLog, userSignLogViewReturn);
            return userSignLogViewReturn;
        }

        userSignLogView.setSignTime(currentTimeMillis);
        userSignLogViewReturn = this.saveEntity(userSignLogView);

        return userSignLogViewReturn;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public UserSignLogView saveHelpSignLog(UserSignLogView userSignLogView) {

        Long signUserId = userSignLogView.getSignUserId();
        Long helpUserId = userSignLogView.getHelpUserId();

        if (signUserId.longValue() == helpUserId.longValue()) {
            throw new TorinoSrcServiceException("不能为自己助签");
        }

        long signTime = userSignLogView.getSignTime();
        long signDayStartTime = DateUtils.getDayStartTime(signTime);
        long currentDayStartTime = DateUtils.getCurrentDayStartTime();
        long currentDayEndTime = DateUtils.getCurrentDayEndTime();

        // 邀请好友助力签的日期与好友进行助力签的日期不是同一天
        if (signDayStartTime != currentDayStartTime) {
            throw new TorinoSrcServiceException("此链接已失效");
        }

        // 签到记录
        UserSignLog signUserSignLog = userSignLogDao.findBySignUserIdAndSignTimeBetween(signUserId, currentDayStartTime, currentDayEndTime);
        if (ObjectUtils.isEmpty(signUserSignLog)) {
            throw new TorinoSrcServiceException("分享者尚未签到");
        }

        if (!ObjectUtils.isEmpty(signUserSignLog.getHelpUserId())) {
            throw new TorinoSrcServiceException("你来晚啦，已经有人帮他助签了");
        }

        // 助签者签到记录
        UserSignLog helpUserSignLog = userSignLogDao.findBySignUserIdAndSignTimeBetween(helpUserId, currentDayStartTime, currentDayEndTime);
        boolean isSignByEachOther = !ObjectUtils.isEmpty(helpUserSignLog) && !ObjectUtils.isEmpty(helpUserSignLog.getHelpUserId()) &&
                (helpUserSignLog.getHelpUserId().longValue() == signUserId.longValue());
        if (isSignByEachOther) {
            throw new TorinoSrcServiceException( "不能相互助签");
        }

        // 更新签到信息，即添加助签者的id
        signUserSignLog.setHelpUserId(helpUserId);
        signUserSignLog.setStatus(1);
        UserSignLog userSignLog = userSignLogDao.save(signUserSignLog);
        UserSignLogView userSignLogViewReturn = new UserSignLogView();
        TorinoSrcBeanUtils.copyBean(userSignLog, userSignLogViewReturn);

        // 助签成功，发放优惠券
        UserCouponView userCouponView;
        List<Coupon> signUserCoupons = couponDao.getCouponsByCouponCategoryId(MallConstant.SIGN_COUPON_CATEGORY_ID);
        for (Coupon coupon : signUserCoupons) {
            userCouponView = new UserCouponView();
            userCouponView.setUserId(signUserId);
            userCouponView.setCouponId(coupon.getId());
            userCouponService.saveEntity(userCouponView);
        }

        List<Coupon> helpUserCoupons = couponDao.getCouponsByCouponCategoryId(MallConstant.HELP_SIGN_COUPON_CATEGORY_ID);
        for (Coupon coupon : helpUserCoupons) {
            userCouponView = new UserCouponView();
            userCouponView.setUserId(helpUserId);
            userCouponView.setCouponId(coupon.getId());
            userCouponService.saveEntity(userCouponView);
        }

        return userSignLogViewReturn;
    }

    @Override
    public UserSignLogView getEntityBySignUserIdAndSignTime(Long signUserId, Long signTime) {

        long signDayStartTime = DateUtils.getDayStartTime(signTime);
        long signDayEndTime = DateUtils.getDayEndTime(signTime);
        UserSignLog userSignLog = userSignLogDao.findBySignUserIdAndSignTimeBetween(signUserId, signDayStartTime, signDayEndTime);
        UserSignLogView userSignLogView = new UserSignLogView();
        TorinoSrcBeanUtils.copyBean(userSignLog, userSignLogView);

        // 设置助签截止时间
        userSignLogView.setExpiredTime(signDayEndTime);

        return userSignLogView;
    }
}
