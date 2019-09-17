/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.customerstatistics.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.customerstatistics.CustomerStatisticsDao;
import com.torinosrc.model.entity.customerstatistics.CustomerStatistics;
import com.torinosrc.model.view.customerstatistics.CustomerStatisticsView;
import com.torinosrc.service.customerstatistics.CustomerStatisticsService;
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
* <b><code>CustomerStatisticsImpl</code></b>
* <p/>
* CustomerStatistics的具体实现
* <p/>
* <b>Creation Time:</b> 2018-06-06 11:32:50.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class CustomerStatisticsServiceImpl implements CustomerStatisticsService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(CustomerStatisticsServiceImpl.class);

    @Autowired
    private CustomerStatisticsDao customerStatisticsDao;

    @Override
    public CustomerStatisticsView getEntity(long id) {
        // 获取Entity
        CustomerStatistics customerStatistics = customerStatisticsDao.getOne(id);
        // 复制Dao层属性到view属性
        CustomerStatisticsView customerStatisticsView = new CustomerStatisticsView();
        TorinoSrcBeanUtils.copyBean(customerStatistics, customerStatisticsView);
        return customerStatisticsView;
    }

    @Override
    public Page<CustomerStatisticsView> getEntitiesByParms(CustomerStatisticsView customerStatisticsView, int currentPage, int pageSize) {
        Specification<CustomerStatistics> customerStatisticsSpecification = new Specification<CustomerStatistics>() {
            @Override
            public Predicate toPredicate(Root<CustomerStatistics> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,customerStatisticsView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<CustomerStatistics> customerStatisticss = customerStatisticsDao.findAll(customerStatisticsSpecification, pageable);

        // 转换成View对象并返回
        return customerStatisticss.map(customerStatistics->{
            CustomerStatisticsView customerStatisticsView1 = new CustomerStatisticsView();
            TorinoSrcBeanUtils.copyBean(customerStatistics, customerStatisticsView1);
            return customerStatisticsView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return customerStatisticsDao.count();
    }

    @Override
    public List<CustomerStatisticsView> findAll() {
        List<CustomerStatisticsView> customerStatisticsViews = new ArrayList<>();
        List<CustomerStatistics> customerStatisticss = customerStatisticsDao.findAll();
        for (CustomerStatistics customerStatistics : customerStatisticss){
            CustomerStatisticsView customerStatisticsView = new CustomerStatisticsView();
            TorinoSrcBeanUtils.copyBean(customerStatistics, customerStatisticsView);
            customerStatisticsViews.add(customerStatisticsView);
        }
        return customerStatisticsViews;
    }

    @Override
    public CustomerStatisticsView saveEntity(CustomerStatisticsView customerStatisticsView) {
        // 保存的业务逻辑
        CustomerStatistics customerStatistics = new CustomerStatistics();
        TorinoSrcBeanUtils.copyBean(customerStatisticsView, customerStatistics);
        // user数据库映射传给dao进行存储
        customerStatistics.setCreateTime(System.currentTimeMillis());
        customerStatistics.setUpdateTime(System.currentTimeMillis());
        customerStatistics.setEnabled(1);
        customerStatisticsDao.save(customerStatistics);
        TorinoSrcBeanUtils.copyBean(customerStatistics,customerStatisticsView);
        return customerStatisticsView;
    }

    @Override
    public void deleteEntity(long id) {
        CustomerStatistics customerStatistics = new CustomerStatistics();
        customerStatistics.setId(id);
        customerStatisticsDao.delete(customerStatistics);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<CustomerStatistics> customerStatisticss = new ArrayList<>();
        for(String entityId : entityIds){
            CustomerStatistics customerStatistics = new CustomerStatistics();
            customerStatistics.setId(Long.valueOf(entityId));
            customerStatisticss.add(customerStatistics);
        }
        customerStatisticsDao.deleteInBatch(customerStatisticss);
    }

    @Override
    public void updateEntity(CustomerStatisticsView customerStatisticsView) {
        Specification<CustomerStatistics> customerStatisticsSpecification = Optional.ofNullable(customerStatisticsView).map( s -> {
            return new Specification<CustomerStatistics>() {
                @Override
                public Predicate toPredicate(Root<CustomerStatistics> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("CustomerStatisticsView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<CustomerStatistics> customerStatisticsOptionalBySearch = customerStatisticsDao.findOne(customerStatisticsSpecification);
        customerStatisticsOptionalBySearch.map(customerStatisticsBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(customerStatisticsView,customerStatisticsBySearch);
            customerStatisticsBySearch.setUpdateTime(System.currentTimeMillis());
            customerStatisticsDao.save(customerStatisticsBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + customerStatisticsView.getId() + "的数据记录"));
    }
}
