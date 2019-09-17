/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.redenvelopeteam.impl;

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
import com.torinosrc.model.view.redenvelopeteam.RedEnvelopeTeamView;
import com.torinosrc.model.view.userredenvelopeteam.UserRedEnvelopeTeamView;
import com.torinosrc.service.redenvelopeteam.RedEnvelopeTeamService;
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

/**
 * <b><code>RedEnvelopeTeamImpl</code></b>
 * <p/>
 * RedEnvelopeTeam的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-12-04 12:10:39.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public class RedEnvelopeTeamServiceImpl implements RedEnvelopeTeamService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(RedEnvelopeTeamServiceImpl.class);

    @Autowired
    private RedEnvelopeTeamDao redEnvelopeTeamDao;

    @Autowired
    private RedEnvelopeDao redEnvelopeDao;

    @Autowired
    private UserRedEnvelopeTeamService userRedEnvelopeTeamService;

    @Autowired
    private UserRedEnvelopeTeamDao userRedEnvelopeTeamDao;

    @Autowired
    private UserDao userDao;

    @Override
    public RedEnvelopeTeamView getEntity(long id) {
        // 获取Entity
        RedEnvelopeTeam redEnvelopeTeam = redEnvelopeTeamDao.getOne(id);
        // 复制Dao层属性到view属性
        RedEnvelopeTeamView redEnvelopeTeamView = new RedEnvelopeTeamView();
        TorinoSrcBeanUtils.copyBean(redEnvelopeTeam, redEnvelopeTeamView);
        return redEnvelopeTeamView;
    }

    @Override
    public Page<RedEnvelopeTeamView> getEntitiesByParms(RedEnvelopeTeamView redEnvelopeTeamView, int currentPage, int pageSize) {
        Specification<RedEnvelopeTeam> redEnvelopeTeamSpecification = new Specification<RedEnvelopeTeam>() {
            @Override
            public Predicate toPredicate(Root<RedEnvelopeTeam> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root, criteriaQuery, criteriaBuilder, redEnvelopeTeamView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<RedEnvelopeTeam> redEnvelopeTeams = redEnvelopeTeamDao.findAll(redEnvelopeTeamSpecification, pageable);

        // 转换成View对象并返回
        return redEnvelopeTeams.map(redEnvelopeTeam -> {
            RedEnvelopeTeamView redEnvelopeTeamView1 = new RedEnvelopeTeamView();
            TorinoSrcBeanUtils.copyBean(redEnvelopeTeam, redEnvelopeTeamView1);
            return redEnvelopeTeamView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return redEnvelopeTeamDao.count();
    }

    @Override
    public List<RedEnvelopeTeamView> findAll() {
        List<RedEnvelopeTeamView> redEnvelopeTeamViews = new ArrayList<>();
        List<RedEnvelopeTeam> redEnvelopeTeams = redEnvelopeTeamDao.findAll();
        for (RedEnvelopeTeam redEnvelopeTeam : redEnvelopeTeams) {
            RedEnvelopeTeamView redEnvelopeTeamView = new RedEnvelopeTeamView();
            TorinoSrcBeanUtils.copyBean(redEnvelopeTeam, redEnvelopeTeamView);
            redEnvelopeTeamViews.add(redEnvelopeTeamView);
        }
        return redEnvelopeTeamViews;
    }

    @Override
    public RedEnvelopeTeamView saveEntity(RedEnvelopeTeamView redEnvelopeTeamView) {
        // 保存的业务逻辑
        RedEnvelopeTeam redEnvelopeTeam = new RedEnvelopeTeam();
        TorinoSrcBeanUtils.copyBean(redEnvelopeTeamView, redEnvelopeTeam);
        // user数据库映射传给dao进行存储
        redEnvelopeTeam.setCreateTime(System.currentTimeMillis());
        redEnvelopeTeam.setUpdateTime(System.currentTimeMillis());
        redEnvelopeTeam.setEnabled(1);
        redEnvelopeTeamDao.save(redEnvelopeTeam);
        TorinoSrcBeanUtils.copyBean(redEnvelopeTeam, redEnvelopeTeamView);
        return redEnvelopeTeamView;
    }

    @Override
    public void deleteEntity(long id) {
        RedEnvelopeTeam redEnvelopeTeam = new RedEnvelopeTeam();
        redEnvelopeTeam.setId(id);
        redEnvelopeTeamDao.delete(redEnvelopeTeam);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteEntities(String ids) {
        String[] entityIds = TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<RedEnvelopeTeam> redEnvelopeTeams = new ArrayList<>();
        for (String entityId : entityIds) {
            RedEnvelopeTeam redEnvelopeTeam = new RedEnvelopeTeam();
            redEnvelopeTeam.setId(Long.valueOf(entityId));
            redEnvelopeTeams.add(redEnvelopeTeam);
        }
        redEnvelopeTeamDao.deleteInBatch(redEnvelopeTeams);
    }

    @Override
    public void updateEntity(RedEnvelopeTeamView redEnvelopeTeamView) {
        Specification<RedEnvelopeTeam> redEnvelopeTeamSpecification = Optional.ofNullable(redEnvelopeTeamView).map(s -> {
            return new Specification<RedEnvelopeTeam>() {
                @Override
                public Predicate toPredicate(Root<RedEnvelopeTeam> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(() -> new IllegalArgumentException("RedEnvelopeTeamView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<RedEnvelopeTeam> redEnvelopeTeamOptionalBySearch = redEnvelopeTeamDao.findOne(redEnvelopeTeamSpecification);
        redEnvelopeTeamOptionalBySearch.map(redEnvelopeTeamBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(redEnvelopeTeamView, redEnvelopeTeamBySearch);
            redEnvelopeTeamBySearch.setUpdateTime(System.currentTimeMillis());
            redEnvelopeTeamDao.save(redEnvelopeTeamBySearch);
            return "";
        }).orElseThrow(() -> new TorinoSrcServiceException("无法找到id为" + redEnvelopeTeamView.getId() + "的数据记录"));
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public RedEnvelopeTeamView createOrReturnRedEnvelopeTeam(RedEnvelopeTeamView redEnvelopeTeamView) {

        Long userId = redEnvelopeTeamView.getUserId();
        Long redEnvelopeId = redEnvelopeTeamView.getRedEnvelopeId();

        Optional<RedEnvelope> redEnvelopeOpt = redEnvelopeDao.findById(redEnvelopeId);
        RedEnvelope redEnvelope;
        if (!redEnvelopeOpt.isPresent()) {
            throw new TorinoSrcServiceException("红包不存在");
        } else {
            redEnvelope = redEnvelopeOpt.get();
        }
        // 判断红包是否过期 暂时不做过期的判断
//        if (System.currentTimeMillis() > redEnvelope.getExpiredTime()) {
//            throw new TorinoSrcServiceException("红包已过期");
//        }

        RedEnvelopeTeam redEnvelopeTeam = redEnvelopeTeamDao.findByUserIdAndRedEnvelopeId(userId, redEnvelopeId);
        RedEnvelopeTeamView redEnvelopeTeamViewReturn;
        if (!ObjectUtils.isEmpty(redEnvelopeTeam)) {
            // 已经有团
            redEnvelopeTeamViewReturn = new RedEnvelopeTeamView();
            TorinoSrcBeanUtils.copyBean(redEnvelopeTeam, redEnvelopeTeamViewReturn);
        } else {
            // 还没有团，创建团
            Integer validDay = redEnvelope.getValidDay();
            long currentTimeMillis = System.currentTimeMillis();
            long oneDayMillis = 60000 * 60 * 24L;
            long expiredTime = currentTimeMillis + validDay * oneDayMillis;
            redEnvelopeTeamView.setExpiredTime(expiredTime);
            redEnvelopeTeamView.setTakeNumber(redEnvelope.getTakeNumber());
            redEnvelopeTeamView.setRedEnvelopeName(redEnvelope.getName());
            redEnvelopeTeamViewReturn = this.saveEntity(redEnvelopeTeamView);
            // 成为第一个团员
            UserRedEnvelopeTeamView userRedEnvelopeTeamView = new UserRedEnvelopeTeamView();
            // type 0：不是发起人 1：是发起人
            userRedEnvelopeTeamView.setType(1);
            userRedEnvelopeTeamView.setUserId(userId);
            userRedEnvelopeTeamView.setRedEnvelopeTeamId(redEnvelopeTeamViewReturn.getId());
            userRedEnvelopeTeamService.saveEntity(userRedEnvelopeTeamView);
        }

        // 设置当前红包团队的团员头像、领取状态、剩余人数
        redEnvelopeTeamViewReturn = setTeamMemberAvatarsAndStatusAndRemainingNumber(redEnvelopeTeamViewReturn);

        return redEnvelopeTeamViewReturn;
    }

    @Override
    public RedEnvelopeTeamView getRedEnvelopeTeamByUserIdAndRedEnvelopeId(Long userId, Long redEnvelopeId) {
        RedEnvelopeTeamView redEnvelopeTeamViewReturn = new RedEnvelopeTeamView();

        Optional<RedEnvelope> redEnvelopeOpt = redEnvelopeDao.findById(redEnvelopeId);
        RedEnvelope redEnvelope;
        if (!redEnvelopeOpt.isPresent()) {
            throw new TorinoSrcServiceException("红包不存在");
        } else {
            redEnvelope = redEnvelopeOpt.get();
        }
        redEnvelopeTeamViewReturn.setTotalAmount(redEnvelope.getTotalAmount());
        Integer takeNumber = redEnvelope.getTakeNumber();

        RedEnvelopeTeam redEnvelopeTeam = redEnvelopeTeamDao.findByUserIdAndRedEnvelopeId(userId, redEnvelopeId);
        // 如果用户尚未开团领红包，设置基础参数后直接返回
        if (ObjectUtils.isEmpty(redEnvelopeTeam)) {
            redEnvelopeTeamViewReturn.setStatus(2);
            redEnvelopeTeamViewReturn.setRemainingNumber(takeNumber);
            redEnvelopeTeamViewReturn.setTakeNumber(takeNumber);
            redEnvelopeTeamViewReturn.setExpiredTime(redEnvelope.getExpiredTime());
            return redEnvelopeTeamViewReturn;
        } else {
            TorinoSrcBeanUtils.copyBean(redEnvelopeTeam, redEnvelopeTeamViewReturn);
            // 设置当前红包团队的团员头像、领取状态、剩余人数
            redEnvelopeTeamViewReturn = setTeamMemberAvatarsAndStatusAndRemainingNumber(redEnvelopeTeamViewReturn);
            return redEnvelopeTeamViewReturn;
        }
    }

    /**
     * 设置当前红包团队的团员头像、领取状态、剩余人数
     * @param redEnvelopeTeamView
     * @return
     */
    private RedEnvelopeTeamView setTeamMemberAvatarsAndStatusAndRemainingNumber(RedEnvelopeTeamView redEnvelopeTeamView) {
        Long redEnvelopeTeamId = redEnvelopeTeamView.getId();
        List<UserRedEnvelopeTeam> userRedEnvelopeTeams = userRedEnvelopeTeamDao.findByRedEnvelopeTeamId(redEnvelopeTeamId);
        // 参数当前红包团队的人员头像
        List<String> teamMemberAvatars = new ArrayList<>();
        for (UserRedEnvelopeTeam userRedEnvelopeTeam : userRedEnvelopeTeams) {
            User user = userDao.getOne(userRedEnvelopeTeam.getUserId());
            teamMemberAvatars.add(user.getAvatar());
        }
        redEnvelopeTeamView.setTeamMemberAvatars(teamMemberAvatars);

        // 当前 team 的红包领取状态 0：未完成 1：完成
        Integer takeNumber = redEnvelopeTeamView.getTakeNumber();
        Integer nowNumber = userRedEnvelopeTeams.size();
        if (nowNumber.intValue() == takeNumber.intValue()) {
            redEnvelopeTeamView.setStatus(1);
        } else {
            redEnvelopeTeamView.setStatus(0);
        }

        // 还差多少人
        redEnvelopeTeamView.setRemainingNumber(takeNumber - nowNumber);

        return redEnvelopeTeamView;
    }
}