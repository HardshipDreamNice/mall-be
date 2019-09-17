/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.userredenvelopeteam.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.redenvelope.RedEnvelopeDao;
import com.torinosrc.dao.redenvelopeteam.RedEnvelopeTeamDao;
import com.torinosrc.dao.user.UserDao;
import com.torinosrc.dao.userredenvelopeteam.UserRedEnvelopeTeamDao;
import com.torinosrc.model.entity.redenvelope.RedEnvelope;
import com.torinosrc.model.entity.redenvelopeteam.RedEnvelopeTeam;
import com.torinosrc.model.entity.user.User;
import com.torinosrc.model.entity.userredenvelopeteam.UserRedEnvelopeTeam;
import com.torinosrc.model.view.userredenvelopeteam.UserRedEnvelopeTeamView;
import com.torinosrc.service.usercoupon.UserCouponService;
import com.torinosrc.service.userredenvelopeteam.UserRedEnvelopeTeamService;
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
import java.util.Random;

/**
 * <b><code>UserRedEnvelopeTeamImpl</code></b>
 * <p/>
 * UserRedEnvelopeTeam的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-12-04 12:18:22.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public class UserRedEnvelopeTeamServiceImpl implements UserRedEnvelopeTeamService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(UserRedEnvelopeTeamServiceImpl.class);

    @Autowired
    private UserRedEnvelopeTeamDao userRedEnvelopeTeamDao;

    @Autowired
    private RedEnvelopeTeamDao redEnvelopeTeamDao;

    @Autowired
    private RedEnvelopeDao redEnvelopeDao;

    @Autowired
    private UserCouponService userCouponService;

    @Autowired
    private UserDao userDao;

    @Override
    public UserRedEnvelopeTeamView getEntity(long id) {
        // 获取Entity
        UserRedEnvelopeTeam userRedEnvelopeTeam = userRedEnvelopeTeamDao.getOne(id);
        // 复制Dao层属性到view属性
        UserRedEnvelopeTeamView userRedEnvelopeTeamView = new UserRedEnvelopeTeamView();
        TorinoSrcBeanUtils.copyBean(userRedEnvelopeTeam, userRedEnvelopeTeamView);
        return userRedEnvelopeTeamView;
    }

    @Override
    public Page<UserRedEnvelopeTeamView> getEntitiesByParms(UserRedEnvelopeTeamView userRedEnvelopeTeamView, int currentPage, int pageSize) {
        Specification<UserRedEnvelopeTeam> userRedEnvelopeTeamSpecification = new Specification<UserRedEnvelopeTeam>() {
            @Override
            public Predicate toPredicate(Root<UserRedEnvelopeTeam> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root, criteriaQuery, criteriaBuilder, userRedEnvelopeTeamView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<UserRedEnvelopeTeam> userRedEnvelopeTeams = userRedEnvelopeTeamDao.findAll(userRedEnvelopeTeamSpecification, pageable);

        // 转换成View对象并返回
        return userRedEnvelopeTeams.map(userRedEnvelopeTeam -> {
            UserRedEnvelopeTeamView userRedEnvelopeTeamView1 = new UserRedEnvelopeTeamView();
            TorinoSrcBeanUtils.copyBean(userRedEnvelopeTeam, userRedEnvelopeTeamView1);
            return userRedEnvelopeTeamView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return userRedEnvelopeTeamDao.count();
    }

    @Override
    public List<UserRedEnvelopeTeamView> findAll() {
        List<UserRedEnvelopeTeamView> userRedEnvelopeTeamViews = new ArrayList<>();
        List<UserRedEnvelopeTeam> userRedEnvelopeTeams = userRedEnvelopeTeamDao.findAll();
        for (UserRedEnvelopeTeam userRedEnvelopeTeam : userRedEnvelopeTeams) {
            UserRedEnvelopeTeamView userRedEnvelopeTeamView = new UserRedEnvelopeTeamView();
            TorinoSrcBeanUtils.copyBean(userRedEnvelopeTeam, userRedEnvelopeTeamView);
            userRedEnvelopeTeamViews.add(userRedEnvelopeTeamView);
        }
        return userRedEnvelopeTeamViews;
    }

    @Override
    public UserRedEnvelopeTeamView saveEntity(UserRedEnvelopeTeamView userRedEnvelopeTeamView) {
        // 保存的业务逻辑
        UserRedEnvelopeTeam userRedEnvelopeTeam = new UserRedEnvelopeTeam();
        TorinoSrcBeanUtils.copyBean(userRedEnvelopeTeamView, userRedEnvelopeTeam);
        // user数据库映射传给dao进行存储
        userRedEnvelopeTeam.setCreateTime(System.currentTimeMillis());
        userRedEnvelopeTeam.setUpdateTime(System.currentTimeMillis());
        userRedEnvelopeTeam.setEnabled(1);
        userRedEnvelopeTeamDao.save(userRedEnvelopeTeam);
        TorinoSrcBeanUtils.copyBean(userRedEnvelopeTeam, userRedEnvelopeTeamView);
        return userRedEnvelopeTeamView;
    }

    @Override
    public void deleteEntity(long id) {
        UserRedEnvelopeTeam userRedEnvelopeTeam = new UserRedEnvelopeTeam();
        userRedEnvelopeTeam.setId(id);
        userRedEnvelopeTeamDao.delete(userRedEnvelopeTeam);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteEntities(String ids) {
        String[] entityIds = TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<UserRedEnvelopeTeam> userRedEnvelopeTeams = new ArrayList<>();
        for (String entityId : entityIds) {
            UserRedEnvelopeTeam userRedEnvelopeTeam = new UserRedEnvelopeTeam();
            userRedEnvelopeTeam.setId(Long.valueOf(entityId));
            userRedEnvelopeTeams.add(userRedEnvelopeTeam);
        }
        userRedEnvelopeTeamDao.deleteInBatch(userRedEnvelopeTeams);
    }

    @Override
    public void updateEntity(UserRedEnvelopeTeamView userRedEnvelopeTeamView) {
        Specification<UserRedEnvelopeTeam> userRedEnvelopeTeamSpecification = Optional.ofNullable(userRedEnvelopeTeamView).map(s -> {
            return new Specification<UserRedEnvelopeTeam>() {
                @Override
                public Predicate toPredicate(Root<UserRedEnvelopeTeam> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(() -> new IllegalArgumentException("UserRedEnvelopeTeamView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<UserRedEnvelopeTeam> userRedEnvelopeTeamOptionalBySearch = userRedEnvelopeTeamDao.findOne(userRedEnvelopeTeamSpecification);
        userRedEnvelopeTeamOptionalBySearch.map(userRedEnvelopeTeamBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(userRedEnvelopeTeamView, userRedEnvelopeTeamBySearch);
            userRedEnvelopeTeamBySearch.setUpdateTime(System.currentTimeMillis());
            userRedEnvelopeTeamDao.save(userRedEnvelopeTeamBySearch);
            return "";
        }).orElseThrow(() -> new TorinoSrcServiceException("无法找到id为" + userRedEnvelopeTeamView.getId() + "的数据记录"));
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public UserRedEnvelopeTeamView saveUserRedEnvelopeTeam(UserRedEnvelopeTeamView userRedEnvelopeTeamView) {

        Long userId = userRedEnvelopeTeamView.getUserId();
        Long redEnvelopeTeamId = userRedEnvelopeTeamView.getRedEnvelopeTeamId();

        Optional<RedEnvelopeTeam> redEnvelopeTeamOpt = redEnvelopeTeamDao.findById(redEnvelopeTeamId);
        RedEnvelopeTeam redEnvelopeTeam;
        if (!redEnvelopeTeamOpt.isPresent()) {
            throw new TorinoSrcServiceException("当前抢红包团队不存在");
        } else {
            redEnvelopeTeam = redEnvelopeTeamOpt.get();
        }
        if (System.currentTimeMillis() > redEnvelopeTeam.getExpiredTime()) {
            throw new TorinoSrcServiceException("团队已过期，请重新邀请好友抢红包");
        }

        UserRedEnvelopeTeam userRedEnvelopeTeamFromDB = userRedEnvelopeTeamDao.findByUserIdAndRedEnvelopeTeamId(userId, redEnvelopeTeamId);
        if (!ObjectUtils.isEmpty(userRedEnvelopeTeamFromDB)) {
            throw new TorinoSrcServiceException("已参与当前红包");
        } else {
            // no need to do anything...
        }

        // 检测是否已经满人
        UserRedEnvelopeTeamView userRedEnvelopeTeamViewReturn;
        Integer teamStatus = getRedEnvelopeTeamStatus(redEnvelopeTeam);
        switch (teamStatus) {
            case 0:
                throw new TorinoSrcServiceException("当前团队已满员");
            case 1:
                // 0：不是发起人 1：是发起人 在创建 redEnvelopeTeam 时，会自动为发起人创建一条记录，因此后来者一定不是发起人
                userRedEnvelopeTeamView.setType(0);
                userRedEnvelopeTeamViewReturn = this.saveEntity(userRedEnvelopeTeamView);
                // 开红包，发放福利
                handleWhenRedEnvelopeSuccess(redEnvelopeTeam);
                break;
            case 2:
                userRedEnvelopeTeamView.setType(0);
                userRedEnvelopeTeamViewReturn = this.saveEntity(userRedEnvelopeTeamView);
                break;
            default:
                throw new TorinoSrcServiceException("当前红包团队状态异常");
        }

        // 返回团员头像
        List<UserRedEnvelopeTeam> userRedEnvelopeTeams = userRedEnvelopeTeamDao.findByRedEnvelopeTeamId(redEnvelopeTeamId);
        List<String> teamMemberAvatars = new ArrayList<>();
        for (UserRedEnvelopeTeam userRedEnvelopeTeam : userRedEnvelopeTeams) {
            User user = userDao.getOne(userRedEnvelopeTeam.getUserId());
            teamMemberAvatars.add(user.getAvatar());
        }
        userRedEnvelopeTeamViewReturn.setTeamMemberAvatars(teamMemberAvatars);

        return userRedEnvelopeTeamViewReturn;
    }

    /**
     * 获取抢红包团队状态
     * 0：已满人
     * 1：加上当前用户刚好满人
     * 2：尚未满人
     * @param redEnvelopeTeam
     * @return
     */
    private Integer getRedEnvelopeTeamStatus(RedEnvelopeTeam redEnvelopeTeam) {
        // 可抢红包的人数
        Integer takeNumber = redEnvelopeTeam.getTakeNumber();
        // 当前参与人数
        Integer participateNumber = userRedEnvelopeTeamDao.countByRedEnvelopeTeamId(redEnvelopeTeam.getId());
        if (participateNumber >= takeNumber) {
            // 已满人
            return 0;
        } else if (participateNumber == takeNumber - 1) {
            // 没错，我就是那最后一块拼图
            return 1;
        } else {
            // 尚未满人
            return 2;
        }
    }

    /**
     * 领红包成功，进行相关的福利发送
     * @param redEnvelopeTeam
     */
    private void handleWhenRedEnvelopeSuccess(RedEnvelopeTeam redEnvelopeTeam) {
        Long redEnvelopeId = redEnvelopeTeam.getRedEnvelopeId();
        RedEnvelope redEnvelope;
        Optional<RedEnvelope> redEnvelopeOpt = redEnvelopeDao.findById(redEnvelopeId);
        if (!redEnvelopeOpt.isPresent()) {
            throw new TorinoSrcServiceException("红包不存在");
        } else {
            redEnvelope = redEnvelopeOpt.get();
        }

        List<UserRedEnvelopeTeam> userRedEnvelopeTeams = userRedEnvelopeTeamDao.findByRedEnvelopeTeamId(redEnvelopeTeam.getId());

        Integer redEnvelopeType = redEnvelope.getType();
        if (redEnvelopeType == 0) {
            // 优惠券红包
            Long couponCategoryId = redEnvelope.getCouponCategoryId();
            for (UserRedEnvelopeTeam userRedEnvelopeTeam : userRedEnvelopeTeams) {
                userCouponService.saveUserCouponsByUserIdAndCategoryId(userRedEnvelopeTeam.getUserId(), couponCategoryId);
            }
        } else if (redEnvelopeType == 1) {
            // 现金红包
            Integer totalAmount = redEnvelope.getTotalAmount();
            Integer takeNumber = redEnvelope.getTakeNumber();
            // TODO: liori 拆分现金红包
        } else {
            throw new TorinoSrcServiceException("红包类型异常");
        }
    }


}
