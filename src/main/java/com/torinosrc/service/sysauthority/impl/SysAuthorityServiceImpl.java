/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.sysauthority.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.sysauthority.SysAuthorityDao;
import com.torinosrc.model.entity.sysauthority.SysAuthority;
import com.torinosrc.model.entity.sysrole.SysRole;
import com.torinosrc.model.view.sysauthority.SysAuthorityView;
import com.torinosrc.service.sysauthority.SysAuthorityService;
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
* <b><code>SysAuthorityImpl</code></b>
* <p/>
* SysAuthority的具体实现
* <p/>
* <b>Creation Time:</b> 2018-04-13 14:31:03.
*
* @author lvxin
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class SysAuthorityServiceImpl implements SysAuthorityService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(SysAuthorityServiceImpl.class);

    @Autowired
    private SysAuthorityDao sysAuthorityDao;

    @Override
    public SysAuthorityView getEntity(long id) {
        // 获取Entity
        SysAuthority sysAuthority = sysAuthorityDao.getOne(id);
        // 复制Dao层属性到view属性
        SysAuthorityView sysAuthorityView = new SysAuthorityView();
        TorinoSrcBeanUtils.copyBean(sysAuthority, sysAuthorityView);
        return sysAuthorityView;
    }

    @Override
    public Page<SysAuthorityView> getEntitiesByParms(SysAuthorityView sysAuthorityView, int currentPage, int pageSize) {
        Specification<SysAuthority> sysAuthoritySpecification = new Specification<SysAuthority>() {
            @Override
            public Predicate toPredicate(Root<SysAuthority> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,sysAuthorityView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<SysAuthority> sysAuthoritys = sysAuthorityDao.findAll(sysAuthoritySpecification, pageable);

        // 转换成View对象并返回
        return sysAuthoritys.map(sysAuthority->{
            SysAuthorityView sysAuthorityView1 = new SysAuthorityView();
            TorinoSrcBeanUtils.copyBean(sysAuthority, sysAuthorityView1);
            return sysAuthorityView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return sysAuthorityDao.count();
    }

    @Override
    public List<SysAuthorityView> findAll() {
        List<SysAuthorityView> sysAuthorityViews = new ArrayList<>();
        List<SysAuthority> sysAuthoritys = sysAuthorityDao.findAll();
        for (SysAuthority sysAuthority : sysAuthoritys){
            SysAuthorityView sysAuthorityView = new SysAuthorityView();
            TorinoSrcBeanUtils.copyBean(sysAuthority, sysAuthorityView);
            sysAuthorityViews.add(sysAuthorityView);
        }
        return sysAuthorityViews;
    }

    @Override
    public SysAuthorityView saveEntity(SysAuthorityView sysAuthorityView) {
        // 保存的业务逻辑
        SysAuthority sysAuthority = new SysAuthority();
        TorinoSrcBeanUtils.copyBean(sysAuthorityView, sysAuthority);
        // user数据库映射传给dao进行存储
        sysAuthority.setCreateTime(System.currentTimeMillis());
        sysAuthority.setUpdateTime(System.currentTimeMillis());
//        sysAuthority.setEnabled(1);
        sysAuthorityDao.save(sysAuthority);
        TorinoSrcBeanUtils.copyBean(sysAuthority,sysAuthorityView);
        return sysAuthorityView;
    }

    @Override
    public void deleteEntity(long id) {
        SysAuthority sysAuthority = new SysAuthority();
        sysAuthority.setId(id);
        sysAuthorityDao.delete(sysAuthority);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<SysAuthority> sysAuthoritys = new ArrayList<>();
        for(String entityId : entityIds){
            SysAuthority sysAuthority = new SysAuthority();
            sysAuthority.setId(Long.valueOf(entityId));
            sysAuthoritys.add(sysAuthority);
        }
        sysAuthorityDao.deleteInBatch(sysAuthoritys);
    }

    @Override
    public void updateEntity(SysAuthorityView sysAuthorityView) {
        Specification<SysAuthority> sysAuthoritySpecification = Optional.ofNullable(sysAuthorityView).map( s -> {
            return new Specification<SysAuthority>() {
                @Override
                public Predicate toPredicate(Root<SysAuthority> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("SysAuthorityView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<SysAuthority> sysAuthorityOptionalBySearch = sysAuthorityDao.findOne(sysAuthoritySpecification);
        sysAuthorityOptionalBySearch.map(sysAuthorityBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(sysAuthorityView,sysAuthorityBySearch);
            sysAuthorityBySearch.setUpdateTime(System.currentTimeMillis());
            sysAuthorityDao.save(sysAuthorityBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + sysAuthorityView.getId() + "的数据记录"));
    }

    @Override
    public boolean checkIfExist(String name){
        SysAuthority sysAuthority=sysAuthorityDao.findByName(name);
        if(!ObjectUtils.isEmpty(sysAuthority)){
            return true;
        }
        return false;
    }
}
