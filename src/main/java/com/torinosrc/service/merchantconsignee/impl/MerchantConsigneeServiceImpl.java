/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.merchantconsignee.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.merchantconsignee.MerchantConsigneeDao;
import com.torinosrc.model.entity.merchantconsignee.MerchantConsignee;
import com.torinosrc.model.view.merchantconsignee.MerchantConsigneeView;
import com.torinosrc.service.merchantconsignee.MerchantConsigneeService;
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
* <b><code>MerchantConsigneeImpl</code></b>
* <p/>
* MerchantConsignee的具体实现
* <p/>
* <b>Creation Time:</b> 2018-06-08 19:11:22.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class MerchantConsigneeServiceImpl implements MerchantConsigneeService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(MerchantConsigneeServiceImpl.class);

    @Autowired
    private MerchantConsigneeDao merchantConsigneeDao;

    @Override
    public MerchantConsigneeView getEntity(long id) {
        // 获取Entity
        MerchantConsignee merchantConsignee = merchantConsigneeDao.getOne(id);
        // 复制Dao层属性到view属性
        MerchantConsigneeView merchantConsigneeView = new MerchantConsigneeView();
        TorinoSrcBeanUtils.copyBean(merchantConsignee, merchantConsigneeView);
        return merchantConsigneeView;
    }

    @Override
    public Page<MerchantConsigneeView> getEntitiesByParms(MerchantConsigneeView merchantConsigneeView, int currentPage, int pageSize) {
        Specification<MerchantConsignee> merchantConsigneeSpecification = new Specification<MerchantConsignee>() {
            @Override
            public Predicate toPredicate(Root<MerchantConsignee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,merchantConsigneeView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<MerchantConsignee> merchantConsignees = merchantConsigneeDao.findAll(merchantConsigneeSpecification, pageable);

        // 转换成View对象并返回
        return merchantConsignees.map(merchantConsignee->{
            MerchantConsigneeView merchantConsigneeView1 = new MerchantConsigneeView();
            TorinoSrcBeanUtils.copyBean(merchantConsignee, merchantConsigneeView1);
            return merchantConsigneeView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return merchantConsigneeDao.count();
    }

    @Override
    public List<MerchantConsigneeView> findAll() {
        List<MerchantConsigneeView> merchantConsigneeViews = new ArrayList<>();
        List<MerchantConsignee> merchantConsignees = merchantConsigneeDao.findAll();
        for (MerchantConsignee merchantConsignee : merchantConsignees){
            MerchantConsigneeView merchantConsigneeView = new MerchantConsigneeView();
            TorinoSrcBeanUtils.copyBean(merchantConsignee, merchantConsigneeView);
            merchantConsigneeViews.add(merchantConsigneeView);
        }
        return merchantConsigneeViews;
    }

    @Override
    public MerchantConsigneeView saveEntity(MerchantConsigneeView merchantConsigneeView) {
        // 保存的业务逻辑
        MerchantConsignee merchantConsignee = new MerchantConsignee();
        TorinoSrcBeanUtils.copyBean(merchantConsigneeView, merchantConsignee);
        // user数据库映射传给dao进行存储
        merchantConsignee.setCreateTime(System.currentTimeMillis());
        merchantConsignee.setUpdateTime(System.currentTimeMillis());
//        merchantConsignee.setEnabled(1);
        merchantConsigneeDao.save(merchantConsignee);
        TorinoSrcBeanUtils.copyBean(merchantConsignee,merchantConsigneeView);
        return merchantConsigneeView;
    }

    @Override
    public void deleteEntity(long id) {
        MerchantConsignee merchantConsignee = new MerchantConsignee();
        merchantConsignee.setId(id);
        merchantConsigneeDao.delete(merchantConsignee);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<MerchantConsignee> merchantConsignees = new ArrayList<>();
        for(String entityId : entityIds){
            MerchantConsignee merchantConsignee = new MerchantConsignee();
            merchantConsignee.setId(Long.valueOf(entityId));
            merchantConsignees.add(merchantConsignee);
        }
        merchantConsigneeDao.deleteInBatch(merchantConsignees);
    }

    @Override
    public void updateEntity(MerchantConsigneeView merchantConsigneeView) {
        Specification<MerchantConsignee> merchantConsigneeSpecification = Optional.ofNullable(merchantConsigneeView).map( s -> {
            return new Specification<MerchantConsignee>() {
                @Override
                public Predicate toPredicate(Root<MerchantConsignee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("MerchantConsigneeView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<MerchantConsignee> merchantConsigneeOptionalBySearch = merchantConsigneeDao.findOne(merchantConsigneeSpecification);
        merchantConsigneeOptionalBySearch.map(merchantConsigneeBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(merchantConsigneeView,merchantConsigneeBySearch);
            merchantConsigneeBySearch.setUpdateTime(System.currentTimeMillis());
            merchantConsigneeDao.save(merchantConsigneeBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + merchantConsigneeView.getId() + "的数据记录"));
    }
}
