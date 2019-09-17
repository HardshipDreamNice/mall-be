/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.globalconfig.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.globalconfig.GlobalConfigDao;
import com.torinosrc.model.entity.globalconfig.GlobalConfig;
import com.torinosrc.model.view.globalconfig.GlobalConfigView;
import com.torinosrc.service.globalconfig.GlobalConfigService;
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
* <b><code>GlobalConfigImpl</code></b>
* <p/>
* GlobalConfig的具体实现
* <p/>
* <b>Creation Time:</b> 2018-12-13 15:26:00.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class GlobalConfigServiceImpl implements GlobalConfigService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(GlobalConfigServiceImpl.class);

    @Autowired
    private GlobalConfigDao globalConfigDao;

    @Override
    public GlobalConfigView getEntity(long id) {
        // 获取Entity
        GlobalConfig globalConfig = globalConfigDao.getOne(id);
        // 复制Dao层属性到view属性
        GlobalConfigView globalConfigView = new GlobalConfigView();
        TorinoSrcBeanUtils.copyBean(globalConfig, globalConfigView);
        return globalConfigView;
    }

    @Override
    public Page<GlobalConfigView> getEntitiesByParms(GlobalConfigView globalConfigView, int currentPage, int pageSize) {
        Specification<GlobalConfig> globalConfigSpecification = new Specification<GlobalConfig>() {
            @Override
            public Predicate toPredicate(Root<GlobalConfig> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,globalConfigView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<GlobalConfig> globalConfigs = globalConfigDao.findAll(globalConfigSpecification, pageable);

        // 转换成View对象并返回
        return globalConfigs.map(globalConfig->{
            GlobalConfigView globalConfigView1 = new GlobalConfigView();
            TorinoSrcBeanUtils.copyBean(globalConfig, globalConfigView1);
            return globalConfigView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return globalConfigDao.count();
    }

    @Override
    public List<GlobalConfigView> findAll() {
        List<GlobalConfigView> globalConfigViews = new ArrayList<>();
        List<GlobalConfig> globalConfigs = globalConfigDao.findAll();
        for (GlobalConfig globalConfig : globalConfigs){
            GlobalConfigView globalConfigView = new GlobalConfigView();
            TorinoSrcBeanUtils.copyBean(globalConfig, globalConfigView);
            globalConfigViews.add(globalConfigView);
        }
        return globalConfigViews;
    }

    @Override
    public GlobalConfigView saveEntity(GlobalConfigView globalConfigView) {
        // 保存的业务逻辑
        GlobalConfig globalConfig = new GlobalConfig();
        TorinoSrcBeanUtils.copyBean(globalConfigView, globalConfig);
        // user数据库映射传给dao进行存储
        globalConfig.setCreateTime(System.currentTimeMillis());
        globalConfig.setUpdateTime(System.currentTimeMillis());
        globalConfig.setEnabled(1);
        globalConfigDao.save(globalConfig);
        TorinoSrcBeanUtils.copyBean(globalConfig,globalConfigView);
        return globalConfigView;
    }

    @Override
    public void deleteEntity(long id) {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setId(id);
        globalConfigDao.delete(globalConfig);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<GlobalConfig> globalConfigs = new ArrayList<>();
        for(String entityId : entityIds){
            GlobalConfig globalConfig = new GlobalConfig();
            globalConfig.setId(Long.valueOf(entityId));
            globalConfigs.add(globalConfig);
        }
        globalConfigDao.deleteInBatch(globalConfigs);
    }

    @Override
    public void updateEntity(GlobalConfigView globalConfigView) {
        Specification<GlobalConfig> globalConfigSpecification = Optional.ofNullable(globalConfigView).map( s -> {
            return new Specification<GlobalConfig>() {
                @Override
                public Predicate toPredicate(Root<GlobalConfig> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("GlobalConfigView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<GlobalConfig> globalConfigOptionalBySearch = globalConfigDao.findOne(globalConfigSpecification);
        globalConfigOptionalBySearch.map(globalConfigBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(globalConfigView,globalConfigBySearch);
            globalConfigBySearch.setUpdateTime(System.currentTimeMillis());
            globalConfigDao.save(globalConfigBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + globalConfigView.getId() + "的数据记录"));
    }
}
