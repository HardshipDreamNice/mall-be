/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.distributionpriceconfig.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.distributionpriceconfig.DistributionPriceConfigDao;
import com.torinosrc.model.entity.distributionpriceconfig.DistributionPriceConfig;
import com.torinosrc.model.view.distributionpriceconfig.DistributionPriceConfigView;
import com.torinosrc.service.distributionpriceconfig.DistributionPriceConfigService;
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
* <b><code>DistributionPriceConfigImpl</code></b>
* <p/>
* DistributionPriceConfig的具体实现
* <p/>
* <b>Creation Time:</b> 2018-07-12 15:13:23.
*
* @author ${model.author}
* @version 2.0.0
* @since torinosrc-spring-boot-be 2.0.0
*/
@Service
public class DistributionPriceConfigServiceImpl implements DistributionPriceConfigService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(DistributionPriceConfigServiceImpl.class);

    @Autowired
    private DistributionPriceConfigDao distributionPriceConfigDao;

    @Override
    public DistributionPriceConfigView getEntity(long id) {
        // 获取Entity
        DistributionPriceConfig distributionPriceConfig = distributionPriceConfigDao.getOne(id);
        // 复制Dao层属性到view属性
        DistributionPriceConfigView distributionPriceConfigView = new DistributionPriceConfigView();
        TorinoSrcBeanUtils.copyBean(distributionPriceConfig, distributionPriceConfigView);
        return distributionPriceConfigView;
    }

    @Override
    public Page<DistributionPriceConfigView> getEntitiesByParms(DistributionPriceConfigView distributionPriceConfigView, int currentPage, int pageSize) {
        Specification<DistributionPriceConfig> distributionPriceConfigSpecification = new Specification<DistributionPriceConfig>() {
            @Override
            public Predicate toPredicate(Root<DistributionPriceConfig> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,distributionPriceConfigView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<DistributionPriceConfig> distributionPriceConfigs = distributionPriceConfigDao.findAll(distributionPriceConfigSpecification, pageable);

        // 转换成View对象并返回
        return distributionPriceConfigs.map(distributionPriceConfig->{
            DistributionPriceConfigView distributionPriceConfigView1 = new DistributionPriceConfigView();
            TorinoSrcBeanUtils.copyBean(distributionPriceConfig, distributionPriceConfigView1);
            return distributionPriceConfigView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return distributionPriceConfigDao.count();
    }

    @Override
    public List<DistributionPriceConfigView> findAll() {
        List<DistributionPriceConfigView> distributionPriceConfigViews = new ArrayList<>();
        List<DistributionPriceConfig> distributionPriceConfigs = distributionPriceConfigDao.findAll();
        for (DistributionPriceConfig distributionPriceConfig : distributionPriceConfigs){
            DistributionPriceConfigView distributionPriceConfigView = new DistributionPriceConfigView();
            TorinoSrcBeanUtils.copyBean(distributionPriceConfig, distributionPriceConfigView);
            distributionPriceConfigViews.add(distributionPriceConfigView);
        }
        return distributionPriceConfigViews;
    }

    @Override
    public DistributionPriceConfigView saveEntity(DistributionPriceConfigView distributionPriceConfigView) {
        // 保存的业务逻辑
        DistributionPriceConfig distributionPriceConfig = new DistributionPriceConfig();
        TorinoSrcBeanUtils.copyBean(distributionPriceConfigView, distributionPriceConfig);
        // user数据库映射传给dao进行存储
        distributionPriceConfig.setCreateTime(System.currentTimeMillis());
        distributionPriceConfig.setUpdateTime(System.currentTimeMillis());
        distributionPriceConfig.setEnabled(1);
        distributionPriceConfigDao.save(distributionPriceConfig);
        TorinoSrcBeanUtils.copyBean(distributionPriceConfig,distributionPriceConfigView);
        return distributionPriceConfigView;
    }

    @Override
    public void deleteEntity(long id) {
        DistributionPriceConfig distributionPriceConfig = new DistributionPriceConfig();
        distributionPriceConfig.setId(id);
        distributionPriceConfigDao.delete(distributionPriceConfig);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<DistributionPriceConfig> distributionPriceConfigs = new ArrayList<>();
        for(String entityId : entityIds){
            DistributionPriceConfig distributionPriceConfig = new DistributionPriceConfig();
            distributionPriceConfig.setId(Long.valueOf(entityId));
            distributionPriceConfigs.add(distributionPriceConfig);
        }
        distributionPriceConfigDao.deleteInBatch(distributionPriceConfigs);
    }

    @Override
    public void updateEntity(DistributionPriceConfigView distributionPriceConfigView) {
        Specification<DistributionPriceConfig> distributionPriceConfigSpecification = Optional.ofNullable(distributionPriceConfigView).map( s -> {
            return new Specification<DistributionPriceConfig>() {
                @Override
                public Predicate toPredicate(Root<DistributionPriceConfig> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("DistributionPriceConfigView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<DistributionPriceConfig> distributionPriceConfigOptionalBySearch = distributionPriceConfigDao.findOne(distributionPriceConfigSpecification);
        distributionPriceConfigOptionalBySearch.map(distributionPriceConfigBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(distributionPriceConfigView,distributionPriceConfigBySearch);
            distributionPriceConfigBySearch.setUpdateTime(System.currentTimeMillis());
            distributionPriceConfigDao.save(distributionPriceConfigBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + distributionPriceConfigView.getId() + "的数据记录"));
    }

    @Override
    public DistributionPriceConfigView findByPercentConfig(String percentConfig) {
        // 获取Entity
        DistributionPriceConfig distributionPriceConfig = distributionPriceConfigDao.findByPercentConfig(percentConfig);
        // 复制Dao层属性到view属性
        DistributionPriceConfigView distributionPriceConfigView = new DistributionPriceConfigView();
        TorinoSrcBeanUtils.copyBean(distributionPriceConfig, distributionPriceConfigView);
        return distributionPriceConfigView;
    }
}
