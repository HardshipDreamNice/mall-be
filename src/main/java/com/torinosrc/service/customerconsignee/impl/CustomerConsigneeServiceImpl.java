/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.customerconsignee.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.customerconsignee.CustomerConsigneeDao;
import com.torinosrc.model.entity.customerconsignee.CustomerConsignee;
import com.torinosrc.model.view.customerconsignee.CustomerConsigneeView;
import com.torinosrc.service.customerconsignee.CustomerConsigneeService;
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
* <b><code>CustomerConsigneeImpl</code></b>
* <p/>
* CustomerConsignee的具体实现
* <p/>
* <b>Creation Time:</b> 2018-06-06 11:29:50.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class CustomerConsigneeServiceImpl implements CustomerConsigneeService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(CustomerConsigneeServiceImpl.class);

    @Autowired
    private CustomerConsigneeDao customerConsigneeDao;

    @Override
    public CustomerConsigneeView getEntity(long id) {
        // 获取Entity
        CustomerConsignee customerConsignee = customerConsigneeDao.getOne(id);
        // 复制Dao层属性到view属性
        CustomerConsigneeView customerConsigneeView = new CustomerConsigneeView();
        TorinoSrcBeanUtils.copyBean(customerConsignee, customerConsigneeView);
        return customerConsigneeView;
    }

    @Override
    public Page<CustomerConsigneeView> getEntitiesByParms(CustomerConsigneeView customerConsigneeView, int currentPage, int pageSize) {
        Specification<CustomerConsignee> customerConsigneeSpecification = new Specification<CustomerConsignee>() {
            @Override
            public Predicate toPredicate(Root<CustomerConsignee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,customerConsigneeView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<CustomerConsignee> customerConsignees = customerConsigneeDao.findAll(customerConsigneeSpecification, pageable);

        // 转换成View对象并返回
        return customerConsignees.map(customerConsignee->{
            CustomerConsigneeView customerConsigneeView1 = new CustomerConsigneeView();
            TorinoSrcBeanUtils.copyBean(customerConsignee, customerConsigneeView1);
            return customerConsigneeView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return customerConsigneeDao.count();
    }

    @Override
    public List<CustomerConsigneeView> findAll() {
        List<CustomerConsigneeView> customerConsigneeViews = new ArrayList<>();
        List<CustomerConsignee> customerConsignees = customerConsigneeDao.findAll();
        for (CustomerConsignee customerConsignee : customerConsignees){
            CustomerConsigneeView customerConsigneeView = new CustomerConsigneeView();
            TorinoSrcBeanUtils.copyBean(customerConsignee, customerConsigneeView);
            customerConsigneeViews.add(customerConsigneeView);
        }
        return customerConsigneeViews;
    }

    @Override
    public CustomerConsigneeView saveEntity(CustomerConsigneeView customerConsigneeView) {
        // 保存的业务逻辑
        CustomerConsignee customerConsignee = new CustomerConsignee();
        TorinoSrcBeanUtils.copyBean(customerConsigneeView, customerConsignee);
        // user数据库映射传给dao进行存储
        customerConsignee.setCreateTime(System.currentTimeMillis());
        customerConsignee.setUpdateTime(System.currentTimeMillis());
        customerConsignee.setEnabled(1);
        //判断是否存在默认的地址（存在默认地址就不创建默认地址）
        List<CustomerConsignee> customerConsignees=customerConsigneeDao.findByUserId(customerConsigneeView.getUserId());
        for(CustomerConsignee customerConsignee1:customerConsignees){
            if(customerConsignee1.getAutoAddr()==1){
                customerConsignee.setAutoAddr(0);
            }
        }
        customerConsigneeDao.save(customerConsignee);
        TorinoSrcBeanUtils.copyBean(customerConsignee,customerConsigneeView);
        return customerConsigneeView;
    }

    @Override
    public void deleteEntity(long id) {
        CustomerConsignee customerConsignee = customerConsigneeDao.getOne(id);;
        customerConsignee.setId(id);
        customerConsigneeDao.delete(customerConsignee);

        //设置最近一个地址是默认地址
        List<CustomerConsignee> customerConsignees = customerConsigneeDao.findByUserId(customerConsignee.getUserId());
        if(!ObjectUtils.isEmpty(customerConsignees)){
            CustomerConsignee customerConsignee2=customerConsignees.get(0);
            customerConsignee2.setAutoAddr(1);
            customerConsignee2.setUpdateTime(System.currentTimeMillis());
            customerConsigneeDao.save(customerConsignee2);
        }

    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<CustomerConsignee> customerConsignees = new ArrayList<>();
        for(String entityId : entityIds){
            CustomerConsignee customerConsignee = new CustomerConsignee();
            customerConsignee.setId(Long.valueOf(entityId));
            customerConsignees.add(customerConsignee);
        }
        customerConsigneeDao.deleteInBatch(customerConsignees);
    }

    @Override
    public void updateEntity(CustomerConsigneeView customerConsigneeView) {
        Specification<CustomerConsignee> customerConsigneeSpecification = Optional.ofNullable(customerConsigneeView).map( s -> {
            return new Specification<CustomerConsignee>() {
                @Override
                public Predicate toPredicate(Root<CustomerConsignee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("CustomerConsigneeView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<CustomerConsignee> customerConsigneeOptionalBySearch = customerConsigneeDao.findOne(customerConsigneeSpecification);
        customerConsigneeOptionalBySearch.map(customerConsigneeBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(customerConsigneeView,customerConsigneeBySearch);
            customerConsigneeBySearch.setUpdateTime(System.currentTimeMillis());
            customerConsigneeDao.save(customerConsigneeBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + customerConsigneeView.getId() + "的数据记录"));
    }


    @Override
    public List<CustomerConsigneeView> findByUserId(Long userId) {
        List<CustomerConsigneeView> customerConsigneeViews = new ArrayList<>();
        List<CustomerConsignee> customerConsignees = customerConsigneeDao.findByUserId(userId);
        for (CustomerConsignee customerConsignee : customerConsignees){
            CustomerConsigneeView customerConsigneeView = new CustomerConsigneeView();
            TorinoSrcBeanUtils.copyBean(customerConsignee, customerConsigneeView);
            customerConsigneeViews.add(customerConsigneeView);
        }
        return customerConsigneeViews;
    }

    @Override
    public CustomerConsigneeView findByUserIdIsAutoaAddr(Long userId){
        CustomerConsigneeView customerConsigneeView=new CustomerConsigneeView();
        if(!ObjectUtils.isEmpty(customerConsigneeDao.findByUserIdAndAutoAddr(userId,1))){
            CustomerConsignee customerConsignee=customerConsigneeDao.findByUserIdAndAutoAddr(userId,1);
            TorinoSrcBeanUtils.copyBean(customerConsignee, customerConsigneeView);
        }
        return customerConsigneeView;
    }

    @Override
    public void updateAutoaAddr(CustomerConsigneeView customerConsigneeView) {
        CustomerConsignee customerConsignee=customerConsigneeDao.findByUserIdAndAutoAddr(customerConsigneeView.getUserId(),1);
        customerConsignee.setAutoAddr(0);
        customerConsignee.setUpdateTime(System.currentTimeMillis());
        customerConsigneeDao.save(customerConsignee);

        updateEntity(customerConsigneeView);

    }

}
