/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.sysuser.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.sysuser.SysUserDao;
import com.torinosrc.model.entity.sysauthority.SysAuthority;
import com.torinosrc.model.entity.sysinterfacepermission.SysInterfacePermission;
import com.torinosrc.model.entity.sysmenupermission.SysMenuPermission;
import com.torinosrc.model.entity.sysrole.SysRole;
import com.torinosrc.model.entity.sysuser.SysUser;
import com.torinosrc.model.view.sysuser.SysUserView;
import com.torinosrc.security.JWTUtil;
import com.torinosrc.security.TorinoSrcPasswordHelper;
import com.torinosrc.service.sysuser.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

/**
* <b><code>SysUserImpl</code></b>
* <p/>
* SysUser的具体实现
* <p/>
* <b>Creation Time:</b> 2018-04-13 14:33:50.
*
* @author lvxin
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class SysUserServiceImpl implements SysUserService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public SysUserView getEntity(long id) {
        // 获取Entity
        SysUser sysUser = sysUserDao.getOne(id);
        // 复制Dao层属性到view属性
        SysUserView sysUserView = new SysUserView();
        TorinoSrcBeanUtils.copyBean(sysUser, sysUserView);
        return sysUserView;
    }

    @Override
    public Page<SysUserView> getEntitiesByParms(SysUserView sysUserView, int currentPage, int pageSize) {
        Specification<SysUser> sysUserSpecification = new Specification<SysUser>() {
            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,sysUserView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<SysUser> sysUsers = sysUserDao.findAll(sysUserSpecification, pageable);

        // 转换成View对象并返回
        return sysUsers.map(sysUser->{
            SysUserView sysUserView1 = new SysUserView();
            TorinoSrcBeanUtils.copyBean(sysUser, sysUserView1);
            return sysUserView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return sysUserDao.count();
    }

    @Override
    public List<SysUserView> findAll() {
        List<SysUserView> sysUserViews = new ArrayList<>();
        List<SysUser> sysUsers = sysUserDao.findAll();
        for (SysUser sysUser : sysUsers){
            SysUserView sysUserView = new SysUserView();
            TorinoSrcBeanUtils.copyBean(sysUser, sysUserView);
            sysUserViews.add(sysUserView);
        }
        return sysUserViews;
    }

    @Override
    public SysUserView saveEntity(SysUserView sysUserView) {
        // 保存的业务逻辑
        SysUser sysUser = new SysUser();
        TorinoSrcBeanUtils.copyBean(sysUserView, sysUser);
        // user数据库映射传给dao进行存储

        sysUser.setPassword(TorinoSrcPasswordHelper.encode(sysUser.getUserName(), sysUser.getPassword()));
        sysUser.setCreateTime(System.currentTimeMillis());
        sysUser.setUpdateTime(System.currentTimeMillis());
//        sysUser.setEnabled(1);
        sysUserDao.save(sysUser);
        TorinoSrcBeanUtils.copyBean(sysUser,sysUserView);
        return sysUserView;
    }

    @Override
    public void deleteEntity(long id) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUserDao.delete(sysUser);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<SysUser> sysUsers = new ArrayList<>();
        for(String entityId : entityIds){
            SysUser sysUser = new SysUser();
            sysUser.setId(Long.valueOf(entityId));
            sysUsers.add(sysUser);
        }
        sysUserDao.deleteInBatch(sysUsers);
    }

    @Override
    public void updateEntity(SysUserView sysUserView) {
        Specification<SysUser> sysUserSpecification = Optional.ofNullable(sysUserView).map( s -> {
            return new Specification<SysUser>() {
                @Override
                public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("SysUserView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<SysUser> sysUserOptionalBySearch = sysUserDao.findOne(sysUserSpecification);
        sysUserOptionalBySearch.map(sysUserBySearch -> {
            if(!StringUtils.isEmpty(sysUserView.getPassword()) && TorinoSrcPasswordHelper.match(sysUserView.getUserName(),sysUserView.getPassword(),sysUserBySearch.getPassword())){
                sysUserBySearch.setPassword(TorinoSrcPasswordHelper.encode(sysUserView.getUserName(),sysUserView.getNewPassword()));
            }
            TorinoSrcBeanUtils.copyBeanExcludeNull(sysUserView,sysUserBySearch);
            sysUserBySearch.setUpdateTime(System.currentTimeMillis());
            sysUserDao.save(sysUserBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + sysUserView.getId() + "的数据记录"));
    }

    @Override
    public SysUserView getUserRoleAuthorityPermissions(String username) throws TorinoSrcServiceException {
        SysUserView sysUserView = new SysUserView();
        Set<String> userRoles = new HashSet<>();
        Set<String> userAuthorities = new HashSet<>();
        Set<String> userMenuPermissions = new HashSet<>();
        Set<String> userInterfacePermissions = new HashSet<>();
        Optional<SysUser> sysUserOptional = getUserByUsername(username);

        sysUserOptional.map(sysUser -> {
            TorinoSrcBeanUtils.copyBean(sysUser,sysUserView);

            for(SysRole sysRole : sysUser.getSysRoles()){
                // TODO: 修改
                userRoles.add(sysRole.getId().toString());
                for(SysAuthority sysAuthority : sysRole.getSysAuthorities()){
                    // TODO: 修改
                    userAuthorities.add(sysAuthority.getId().toString());
                    for (SysMenuPermission sysMenuPermission : sysAuthority.getSysMenuPermissions()){
                        // TODO: 修改
                        userMenuPermissions.add(sysMenuPermission.getId().toString());
                    }
                    // 该用户所有接口权限
                    for(SysInterfacePermission sysInterfacePermission : sysAuthority.getSysInterfacePermissions()){
                        userInterfacePermissions.add(sysInterfacePermission.getPermission());
                    }
                }
            }
            sysUserView.setUserRoles(userRoles);
            sysUserView.setUserAuthorities(userAuthorities);
            sysUserView.setUserMenuPermissions(userMenuPermissions);
            sysUserView.setUserInterfacePermissions(userInterfacePermissions);
            return sysUser;
        }).orElseThrow(()-> new TorinoSrcServiceException("账号/密码不正确，请重新输入！"));

        // 遍历所有信息
        return sysUserView;
    }

    @Override
    public SysUserView login(String username, String password) throws TorinoSrcServiceException {
        Optional<SysUserView> sysUserOptional = Optional.ofNullable(getUserRoleAuthorityPermissions(username));

        return sysUserOptional.map(sysUserView -> {
            if(TorinoSrcPasswordHelper.match(username,password, sysUserView.getPassword())) {
                sysUserView.setToken(JWTUtil.sign(String.valueOf(sysUserView.getId()),username,sysUserView.getPassword()));
            }else{
                throw new TorinoSrcServiceException("账号/密码不正确，请重新输入！");
            }
            return sysUserView;
        }).orElseThrow(()-> new TorinoSrcServiceException("账号/密码不正确，请重新输入！"));
    }

    /**
     * 通过用户名查询用户信息
     * @param username 用户名
     * @return 用户信息
     */
    private Optional<SysUser> getUserByUsername(String username){
        Specification<SysUser> sysUserSpecification = new Specification<SysUser>() {
            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaQuery.where(criteriaBuilder.equal(root.get("userName").as(String.class), username)).getRestriction();
            }
        };
        Optional<SysUser> sysUserOptional = sysUserDao.findOne(sysUserSpecification);

        return sysUserOptional;
    }

    @Override
    public boolean checkIfExist(String userName) {
        SysUser sysUser=sysUserDao.findByUserName(userName);
        if(!ObjectUtils.isEmpty(sysUser)){
            return true;
        }
        return false;
    }
}
