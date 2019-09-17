/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.team.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.DateUtils;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.product.ProductDao;
import com.torinosrc.dao.productsnapshot.ProductSnapshotDao;
import com.torinosrc.dao.team.TeamDao;
import com.torinosrc.dao.teamuser.TeamUserDao;
import com.torinosrc.dao.user.UserDao;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.entity.productsnapshot.ProductSnapshot;
import com.torinosrc.model.entity.team.Team;
import com.torinosrc.model.entity.teamuser.TeamUser;
import com.torinosrc.model.entity.user.User;
import com.torinosrc.model.view.team.TeamShowView;
import com.torinosrc.model.view.team.TeamView;
import com.torinosrc.model.view.teamuser.TeamUserView;
import com.torinosrc.service.team.TeamService;
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
* <b><code>TeamImpl</code></b>
* <p/>
* Team的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-16 10:53:24.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class TeamServiceImpl implements TeamService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(TeamServiceImpl.class);

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private TeamUserDao teamUserDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductSnapshotDao productSnapshotDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public TeamView getEntity(long id) {
        // 获取Entity
        Team team = teamDao.getOne(id);
        // 复制Dao层属性到view属性
        TeamView teamView = new TeamView();
        TorinoSrcBeanUtils.copyBean(team, teamView);
        return teamView;
    }

    @Override
    public Page<TeamView> getEntitiesByParms(TeamView teamView, int currentPage, int pageSize) {
        Specification<Team> teamSpecification = new Specification<Team>() {
            @Override
            public Predicate toPredicate(Root<Team> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,teamView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Team> teams = teamDao.findAll(teamSpecification, pageable);

        // 转换成View对象并返回
        return teams.map(team->{
            TeamView teamView1 = new TeamView();
            TorinoSrcBeanUtils.copyBean(team, teamView1);
            return teamView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return teamDao.count();
    }

    @Override
    public List<TeamView> findAll() {
        List<TeamView> teamViews = new ArrayList<>();
        List<Team> teams = teamDao.findAll();
        for (Team team : teams){
            TeamView teamView = new TeamView();
            TorinoSrcBeanUtils.copyBean(team, teamView);
            teamViews.add(teamView);
        }
        return teamViews;
    }

    @Override
    public TeamView saveEntity(TeamView teamView) {
        // 保存的业务逻辑
        Team team = new Team();
        TorinoSrcBeanUtils.copyBean(teamView, team);
        // user数据库映射传给dao进行存储
        team.setCreateTime(System.currentTimeMillis());
        team.setUpdateTime(System.currentTimeMillis());
        team.setEnabled(1);
        teamDao.save(team);
        TorinoSrcBeanUtils.copyBean(team,teamView);
        return teamView;
    }

    @Override
    public void deleteEntity(long id) {
        Team team = new Team();
        team.setId(id);
        teamDao.delete(team);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<Team> teams = new ArrayList<>();
        for(String entityId : entityIds){
            Team team = new Team();
            team.setId(Long.valueOf(entityId));
            teams.add(team);
        }
        teamDao.deleteInBatch(teams);
    }

    @Override
    public void updateEntity(TeamView teamView) {
        Specification<Team> teamSpecification = Optional.ofNullable(teamView).map( s -> {
            return new Specification<Team>() {
                @Override
                public Predicate toPredicate(Root<Team> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("TeamView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<Team> teamOptionalBySearch = teamDao.findOne(teamSpecification);
        teamOptionalBySearch.map(teamBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(teamView,teamBySearch);
            teamBySearch.setUpdateTime(System.currentTimeMillis());
            teamDao.save(teamBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + teamView.getId() + "的数据记录"));
    }

    @Override
    public TeamShowView getTeamShowView(Long teamId) {
        TeamShowView teamShowView=new TeamShowView();
        Team team=teamDao.findById(teamId).get();

        Integer haveCount=teamUserDao.countByTeamId(teamId);
        Integer totalCount=team.getCount();
        teamShowView.setCount(totalCount-haveCount);

        Long currentTime=System.currentTimeMillis();
        Long teamTime=DateUtils.StrToDate(team.getExpiredTime(),"yyyy-MM-dd HH:mm:ss").getTime();
        // 还差多少人
        Integer count=totalCount-haveCount;

        teamShowView.setTeamStatus(0);

        if(currentTime>=teamTime&&count!=0){
            teamShowView.setTeamStatus(1);
        }
        if(count==0){
            teamShowView.setTeamStatus(2);
        }

        List<TeamUser> teamUsers=teamUserDao.findByTeamIdOrderByTypeDesc(teamId);
        User user;
        TeamUserView teamUserView;
        List<TeamUserView> teamUserViews=new ArrayList<>();
        for(TeamUser teamUser:teamUsers){
            user= userDao.findById(teamUser.getUserId()).get();
            teamUserView=new TeamUserView();
            TorinoSrcBeanUtils.copyBean(teamUser, teamUserView);
            teamUserView.setAvatar(user.getAvatar());
            teamUserViews.add(teamUserView);
        }
        teamShowView.setTeamUserViews(teamUserViews);

        Long productId=team.getProductId();

        Product product=productDao.findById(productId).get();

        if(ObjectUtils.isEmpty(product)){
            teamShowView.setErrorStatus("500");
            teamShowView.setErrorMessage("此商品已不存在啦！");
        }else {
            teamShowView.setErrorStatus("200");
        }

        teamShowView.setProductName(team.getProductName());

        teamShowView.setProductId(productId);

        teamShowView.setTeamId(teamId);

        teamShowView.setEndTime(team.getExpiredTime());

        return teamShowView;
    }
}
