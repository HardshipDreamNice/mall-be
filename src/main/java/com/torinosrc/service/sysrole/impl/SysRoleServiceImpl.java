/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.sysrole.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.sysrole.SysRoleDao;
import com.torinosrc.model.entity.sysrole.SysRole;
import com.torinosrc.model.view.sysrole.SysRoleView;
import com.torinosrc.service.sysrole.SysRoleService;
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
* <b><code>SysRoleImpl</code></b>
* <p/>
* SysRole的具体实现
* <p/>
* <b>Creation Time:</b> 2018-04-13 14:41:37.
*
* @author lvxin
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class SysRoleServiceImpl implements SysRoleService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(SysRoleServiceImpl.class);

    @Autowired
    private SysRoleDao sysRoleDao;

    @Override
    public SysRoleView getEntity(long id) {
        // 获取Entity
        SysRole sysRole = sysRoleDao.getOne(id);
        // 复制Dao层属性到view属性
        SysRoleView sysRoleView = new SysRoleView();
        TorinoSrcBeanUtils.copyBean(sysRole, sysRoleView);
        return sysRoleView;
    }

    @Override
    public Page<SysRoleView> getEntitiesByParms(SysRoleView sysRoleView, int currentPage, int pageSize) {
        Specification<SysRole> sysRoleSpecification = new Specification<SysRole>() {
            @Override
            public Predicate toPredicate(Root<SysRole> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,sysRoleView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<SysRole> sysRoles = sysRoleDao.findAll(sysRoleSpecification, pageable);

        // 转换成View对象并返回
        return sysRoles.map(sysRole->{
            SysRoleView sysRoleView1 = new SysRoleView();
            TorinoSrcBeanUtils.copyBean(sysRole, sysRoleView1);
            return sysRoleView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return sysRoleDao.count();
    }

    @Override
    public List<SysRoleView> findAll() {
        List<SysRoleView> sysRoleViews = new ArrayList<>();
        List<SysRole> sysRoles = sysRoleDao.findAll();
        for (SysRole sysRole : sysRoles){
            SysRoleView sysRoleView = new SysRoleView();
            TorinoSrcBeanUtils.copyBean(sysRole, sysRoleView);
            sysRoleViews.add(sysRoleView);
        }
        return sysRoleViews;
    }

    @Override
    public SysRoleView saveEntity(SysRoleView sysRoleView) {
        // 保存的业务逻辑
        SysRole sysRole = new SysRole();
        TorinoSrcBeanUtils.copyBean(sysRoleView, sysRole);
        // user数据库映射传给dao进行存储
        sysRole.setCreateTime(System.currentTimeMillis());
        sysRole.setUpdateTime(System.currentTimeMillis());
//        sysRole.setEnabled(1);
        sysRoleDao.save(sysRole);
        TorinoSrcBeanUtils.copyBean(sysRole,sysRoleView);
        return sysRoleView;
    }

    @Override
    public void deleteEntity(long id) {
        SysRole sysRole = new SysRole();
        sysRole.setId(id);
        sysRoleDao.delete(sysRole);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<SysRole> sysRoles = new ArrayList<>();
        for(String entityId : entityIds){
            SysRole sysRole = new SysRole();
            sysRole.setId(Long.valueOf(entityId));
            sysRoles.add(sysRole);
        }
        sysRoleDao.deleteInBatch(sysRoles);
    }

    @Override
    public void updateEntity(SysRoleView sysRoleView) {
        Specification<SysRole> sysRoleSpecification = Optional.ofNullable(sysRoleView).map( s -> {
            return new Specification<SysRole>() {
                @Override
                public Predicate toPredicate(Root<SysRole> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("SysRoleView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<SysRole> sysRoleOptionalBySearch = sysRoleDao.findOne(sysRoleSpecification);
        sysRoleOptionalBySearch.map(sysRoleBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(sysRoleView,sysRoleBySearch);
            sysRoleBySearch.setUpdateTime(System.currentTimeMillis());
            sysRoleDao.save(sysRoleBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + sysRoleView.getId() + "的数据记录"));
    }

    @Override
    public boolean checkIfExist(String chineseName) {
        SysRole sysRole=sysRoleDao.findByChineseName(chineseName);
        if(!ObjectUtils.isEmpty(sysRole)){
            return true;
        }
        return false;
    }
}
