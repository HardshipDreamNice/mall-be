/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.teamuser.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.teamuser.TeamUserDao;
import com.torinosrc.model.entity.teamuser.TeamUser;
import com.torinosrc.model.view.teamuser.TeamUserView;
import com.torinosrc.service.teamuser.TeamUserService;
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
* <b><code>TeamUserImpl</code></b>
* <p/>
* TeamUser的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-16 10:54:01.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class TeamUserServiceImpl implements TeamUserService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(TeamUserServiceImpl.class);

    @Autowired
    private TeamUserDao teamUserDao;

    @Override
    public TeamUserView getEntity(long id) {
        // 获取Entity
        TeamUser teamUser = teamUserDao.getOne(id);
        // 复制Dao层属性到view属性
        TeamUserView teamUserView = new TeamUserView();
        TorinoSrcBeanUtils.copyBean(teamUser, teamUserView);
        return teamUserView;
    }

    @Override
    public Page<TeamUserView> getEntitiesByParms(TeamUserView teamUserView, int currentPage, int pageSize) {
        Specification<TeamUser> teamUserSpecification = new Specification<TeamUser>() {
            @Override
            public Predicate toPredicate(Root<TeamUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,teamUserView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<TeamUser> teamUsers = teamUserDao.findAll(teamUserSpecification, pageable);

        // 转换成View对象并返回
        return teamUsers.map(teamUser->{
            TeamUserView teamUserView1 = new TeamUserView();
            TorinoSrcBeanUtils.copyBean(teamUser, teamUserView1);
            return teamUserView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return teamUserDao.count();
    }

    @Override
    public List<TeamUserView> findAll() {
        List<TeamUserView> teamUserViews = new ArrayList<>();
        List<TeamUser> teamUsers = teamUserDao.findAll();
        for (TeamUser teamUser : teamUsers){
            TeamUserView teamUserView = new TeamUserView();
            TorinoSrcBeanUtils.copyBean(teamUser, teamUserView);
            teamUserViews.add(teamUserView);
        }
        return teamUserViews;
    }

    @Override
    public TeamUserView saveEntity(TeamUserView teamUserView) {
        // 保存的业务逻辑
        TeamUser teamUser = new TeamUser();
        TorinoSrcBeanUtils.copyBean(teamUserView, teamUser);
        // user数据库映射传给dao进行存储
        teamUser.setCreateTime(System.currentTimeMillis());
        teamUser.setUpdateTime(System.currentTimeMillis());
        teamUser.setEnabled(1);
        teamUserDao.save(teamUser);
        TorinoSrcBeanUtils.copyBean(teamUser,teamUserView);
        return teamUserView;
    }

    @Override
    public void deleteEntity(long id) {
        TeamUser teamUser = new TeamUser();
        teamUser.setId(id);
        teamUserDao.delete(teamUser);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<TeamUser> teamUsers = new ArrayList<>();
        for(String entityId : entityIds){
            TeamUser teamUser = new TeamUser();
            teamUser.setId(Long.valueOf(entityId));
            teamUsers.add(teamUser);
        }
        teamUserDao.deleteInBatch(teamUsers);
    }

    @Override
    public void updateEntity(TeamUserView teamUserView) {
        Specification<TeamUser> teamUserSpecification = Optional.ofNullable(teamUserView).map( s -> {
            return new Specification<TeamUser>() {
                @Override
                public Predicate toPredicate(Root<TeamUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("TeamUserView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<TeamUser> teamUserOptionalBySearch = teamUserDao.findOne(teamUserSpecification);
        teamUserOptionalBySearch.map(teamUserBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(teamUserView,teamUserBySearch);
            teamUserBySearch.setUpdateTime(System.currentTimeMillis());
            teamUserDao.save(teamUserBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + teamUserView.getId() + "的数据记录"));
    }
}
