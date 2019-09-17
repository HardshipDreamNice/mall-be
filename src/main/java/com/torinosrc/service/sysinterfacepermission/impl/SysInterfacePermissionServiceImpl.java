/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.sysinterfacepermission.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.sysinterfacepermission.SysInterfacePermissionDao;
import com.torinosrc.model.entity.sysinterfacepermission.SysInterfacePermission;
import com.torinosrc.model.view.sysinterfacepermission.SysInterfacePermissionView;
import com.torinosrc.service.sysinterfacepermission.SysInterfacePermissionService;
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
* <b><code>SysInterfacePermissionImpl</code></b>
* <p/>
* SysInterfacePermission的具体实现
* <p/>
* <b>Creation Time:</b> 2018-04-14 17:27:52.
*
* @author lvxin
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class SysInterfacePermissionServiceImpl implements SysInterfacePermissionService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(SysInterfacePermissionServiceImpl.class);

    @Autowired
    private SysInterfacePermissionDao sysInterfacePermissionDao;

    @Override
    public SysInterfacePermissionView getEntity(long id) {
        // 获取Entity
        SysInterfacePermission sysInterfacePermission = sysInterfacePermissionDao.getOne(id);
        // 复制Dao层属性到view属性
        SysInterfacePermissionView sysInterfacePermissionView = new SysInterfacePermissionView();
        TorinoSrcBeanUtils.copyBean(sysInterfacePermission, sysInterfacePermissionView);
        return sysInterfacePermissionView;
    }

    @Override
    public Page<SysInterfacePermissionView> getEntitiesByParms(SysInterfacePermissionView sysInterfacePermissionView, int currentPage, int pageSize) {
        Specification<SysInterfacePermission> sysInterfacePermissionSpecification = new Specification<SysInterfacePermission>() {
            @Override
            public Predicate toPredicate(Root<SysInterfacePermission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,sysInterfacePermissionView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<SysInterfacePermission> sysInterfacePermissions = sysInterfacePermissionDao.findAll(sysInterfacePermissionSpecification, pageable);

        // 转换成View对象并返回
        return sysInterfacePermissions.map(sysInterfacePermission->{
            SysInterfacePermissionView sysInterfacePermissionView1 = new SysInterfacePermissionView();
            TorinoSrcBeanUtils.copyBean(sysInterfacePermission, sysInterfacePermissionView1);
            return sysInterfacePermissionView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return sysInterfacePermissionDao.count();
    }

    @Override
    public List<SysInterfacePermissionView> findAll() {
        List<SysInterfacePermissionView> sysInterfacePermissionViews = new ArrayList<>();
        List<SysInterfacePermission> sysInterfacePermissions = sysInterfacePermissionDao.findAll();
        for (SysInterfacePermission sysInterfacePermission : sysInterfacePermissions){
            SysInterfacePermissionView sysInterfacePermissionView = new SysInterfacePermissionView();
            TorinoSrcBeanUtils.copyBean(sysInterfacePermission, sysInterfacePermissionView);
            sysInterfacePermissionViews.add(sysInterfacePermissionView);
        }
        return sysInterfacePermissionViews;
    }

    @Override
    public SysInterfacePermissionView saveEntity(SysInterfacePermissionView sysInterfacePermissionView) {
        // 保存的业务逻辑
        SysInterfacePermission sysInterfacePermission = new SysInterfacePermission();
        TorinoSrcBeanUtils.copyBean(sysInterfacePermissionView, sysInterfacePermission);
        // user数据库映射传给dao进行存储
        sysInterfacePermission.setCreateTime(System.currentTimeMillis());
        sysInterfacePermission.setUpdateTime(System.currentTimeMillis());
        sysInterfacePermission.setEnabled(1);
        sysInterfacePermissionDao.save(sysInterfacePermission);
        TorinoSrcBeanUtils.copyBean(sysInterfacePermission,sysInterfacePermissionView);
        return sysInterfacePermissionView;
    }

    @Override
    public void deleteEntity(long id) {
        SysInterfacePermission sysInterfacePermission = new SysInterfacePermission();
        sysInterfacePermission.setId(id);
        sysInterfacePermissionDao.delete(sysInterfacePermission);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<SysInterfacePermission> sysInterfacePermissions = new ArrayList<>();
        for(String entityId : entityIds){
            SysInterfacePermission sysInterfacePermission = new SysInterfacePermission();
            sysInterfacePermission.setId(Long.valueOf(entityId));
            sysInterfacePermissions.add(sysInterfacePermission);
        }
        sysInterfacePermissionDao.deleteInBatch(sysInterfacePermissions);
    }

    @Override
    public void updateEntity(SysInterfacePermissionView sysInterfacePermissionView) {
        Specification<SysInterfacePermission> sysInterfacePermissionSpecification = Optional.ofNullable(sysInterfacePermissionView).map( s -> {
            return new Specification<SysInterfacePermission>() {
                @Override
                public Predicate toPredicate(Root<SysInterfacePermission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("SysInterfacePermissionView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<SysInterfacePermission> sysInterfacePermissionOptionalBySearch = sysInterfacePermissionDao.findOne(sysInterfacePermissionSpecification);
        sysInterfacePermissionOptionalBySearch.map(sysInterfacePermissionBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(sysInterfacePermissionView,sysInterfacePermissionBySearch);
            sysInterfacePermissionBySearch.setUpdateTime(System.currentTimeMillis());
            sysInterfacePermissionDao.save(sysInterfacePermissionBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + sysInterfacePermissionView.getId() + "的数据记录"));
    }
}
