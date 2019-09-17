/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.user.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.user.UserDao;
import com.torinosrc.model.entity.user.User;
import com.torinosrc.model.view.user.UserView;
import com.torinosrc.service.user.UserService;
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
* <b><code>UserImpl</code></b>
* <p/>
* User的具体实现
* <p/>
* <b>Creation Time:</b> 2018-04-16 14:17:14.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
@Transactional
public class UserServiceImpl implements UserService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public UserView getEntity(long id) {
        // 获取Entity
        User user = userDao.getOne(id);
        // 复制Dao层属性到view属性
        UserView userView = new UserView();
        TorinoSrcBeanUtils.copyBean(user, userView);
        return userView;
    }

    @Override
    public Page<UserView> getEntitiesByParms(UserView userView, int currentPage, int pageSize) {
        Specification<User> userSpecification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,userView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<User> users = userDao.findAll(userSpecification, pageable);

        // 转换成View对象并返回
        return users.map(user->{
            UserView userView1 = new UserView();
            TorinoSrcBeanUtils.copyBean(user, userView1);
            return userView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return userDao.count();
    }

    @Override
    public List<UserView> findAll() {
        List<UserView> userViews = new ArrayList<>();
        List<User> users = userDao.findAll();
        for (User user : users){
            UserView userView = new UserView();
            TorinoSrcBeanUtils.copyBean(user, userView);
            userViews.add(userView);
        }
        return userViews;
    }

    @Override
    public UserView saveEntity(UserView userView) {
        // 保存的业务逻辑
        User user = new User();
        TorinoSrcBeanUtils.copyBean(userView, user);
        // user数据库映射传给dao进行存储
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        user.setEnabled(1);
        userDao.save(user);
        TorinoSrcBeanUtils.copyBean(user,userView);
        return userView;
    }

    @Override
    public void deleteEntity(long id) {
        User user = new User();
        user.setId(id);
        userDao.delete(user);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<User> users = new ArrayList<>();
        for(String entityId : entityIds){
            User user = new User();
            user.setId(Long.valueOf(entityId));
            users.add(user);
        }
        userDao.deleteInBatch(users);
    }

    @Override
    public void updateEntity(UserView userView) {
        Specification<User> userSpecification = Optional.ofNullable(userView).map( s -> {
            return new Specification<User>() {
                @Override
                public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("UserView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<User> userOptionalBySearch = userDao.findOne(userSpecification);
        userOptionalBySearch.map(userBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(userView,userBySearch);
            userBySearch.setUpdateTime(System.currentTimeMillis());
            userDao.save(userBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + userView.getId() + "的数据记录"));
    }

    @Override
    public UserView login(String openId) {
        User user = userDao.findByOpenId(openId);
        // 复制Dao层属性到view属性
        UserView userView = new UserView();
        if(ObjectUtils.isEmpty(user)){
            return userView;
        }
        TorinoSrcBeanUtils.copyBeanExcludeNull(user, userView);
        return userView;
    }
}
