/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.sysmenupermission.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.sysmenupermission.SysMenuPermissionDao;
import com.torinosrc.model.entity.sysmenupermission.SysMenuPermission;
import com.torinosrc.model.view.sysmenupermission.SysMenuPermissionView;
import com.torinosrc.service.sysmenupermission.SysMenuPermissionService;
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
* <b><code>SysMenuPermissionImpl</code></b>
* <p/>
* SysMenuPermission的具体实现
* <p/>
* <b>Creation Time:</b> 2018-04-13 14:43:29.
*
* @author lvxin
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class SysMenuPermissionServiceImpl implements SysMenuPermissionService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(SysMenuPermissionServiceImpl.class);

    @Autowired
    private SysMenuPermissionDao sysMenuPermissionDao;

    @Override
    public SysMenuPermissionView getEntity(long id) {
        // 获取Entity
        SysMenuPermission sysMenuPermission = sysMenuPermissionDao.getOne(id);
        // 复制Dao层属性到view属性
        SysMenuPermissionView sysMenuPermissionView = new SysMenuPermissionView();
        TorinoSrcBeanUtils.copyBean(sysMenuPermission, sysMenuPermissionView);
        return sysMenuPermissionView;
    }

    @Override
    public Page<SysMenuPermissionView> getEntitiesByParms(SysMenuPermissionView sysMenuPermissionView, int currentPage, int pageSize) {
        Specification<SysMenuPermission> sysMenuPermissionSpecification = new Specification<SysMenuPermission>() {
            @Override
            public Predicate toPredicate(Root<SysMenuPermission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,sysMenuPermissionView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<SysMenuPermission> sysMenuPermissions = sysMenuPermissionDao.findAll(sysMenuPermissionSpecification, pageable);

        // 转换成View对象并返回
        return sysMenuPermissions.map(sysMenuPermission->{
            SysMenuPermissionView sysMenuPermissionView1 = new SysMenuPermissionView();
            TorinoSrcBeanUtils.copyBean(sysMenuPermission, sysMenuPermissionView1);
            return sysMenuPermissionView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return sysMenuPermissionDao.count();
    }

    @Override
    public List<SysMenuPermissionView> findAll() {
        List<SysMenuPermissionView> sysMenuPermissionViews = new ArrayList<>();
        List<SysMenuPermission> sysMenuPermissions = sysMenuPermissionDao.findAll();
        for (SysMenuPermission sysMenuPermission : sysMenuPermissions){
            SysMenuPermissionView sysMenuPermissionView = new SysMenuPermissionView();
            TorinoSrcBeanUtils.copyBean(sysMenuPermission, sysMenuPermissionView);
            sysMenuPermissionViews.add(sysMenuPermissionView);
        }
        return sysMenuPermissionViews;
    }

    @Override
    public SysMenuPermissionView saveEntity(SysMenuPermissionView sysMenuPermissionView) {
        // 保存的业务逻辑
        SysMenuPermission sysMenuPermission = new SysMenuPermission();
        TorinoSrcBeanUtils.copyBean(sysMenuPermissionView, sysMenuPermission);
        // user数据库映射传给dao进行存储
        sysMenuPermission.setCreateTime(System.currentTimeMillis());
        sysMenuPermission.setUpdateTime(System.currentTimeMillis());
        sysMenuPermission.setEnabled(1);
        sysMenuPermissionDao.save(sysMenuPermission);
        TorinoSrcBeanUtils.copyBean(sysMenuPermission,sysMenuPermissionView);
        return sysMenuPermissionView;
    }

    @Override
    public void deleteEntity(long id) {
        SysMenuPermission sysMenuPermission = new SysMenuPermission();
        sysMenuPermission.setId(id);
        sysMenuPermissionDao.delete(sysMenuPermission);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<SysMenuPermission> sysMenuPermissions = new ArrayList<>();
        for(String entityId : entityIds){
            SysMenuPermission sysMenuPermission = new SysMenuPermission();
            sysMenuPermission.setId(Long.valueOf(entityId));
            sysMenuPermissions.add(sysMenuPermission);
        }
        sysMenuPermissionDao.deleteInBatch(sysMenuPermissions);
    }

    @Override
    public void updateEntity(SysMenuPermissionView sysMenuPermissionView) {
        Specification<SysMenuPermission> sysMenuPermissionSpecification = Optional.ofNullable(sysMenuPermissionView).map( s -> {
            return new Specification<SysMenuPermission>() {
                @Override
                public Predicate toPredicate(Root<SysMenuPermission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("SysMenuPermissionView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<SysMenuPermission> sysMenuPermissionOptionalBySearch = sysMenuPermissionDao.findOne(sysMenuPermissionSpecification);
        sysMenuPermissionOptionalBySearch.map(sysMenuPermissionBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(sysMenuPermissionView,sysMenuPermissionBySearch);
            sysMenuPermissionBySearch.setUpdateTime(System.currentTimeMillis());
            sysMenuPermissionDao.save(sysMenuPermissionBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + sysMenuPermissionView.getId() + "的数据记录"));
    }
}
