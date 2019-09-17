/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.orderdetail.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.orderdetail.OrderDetailDao;
import com.torinosrc.model.entity.orderdetail.OrderDetail;
import com.torinosrc.model.view.orderdetail.OrderDetailView;
import com.torinosrc.service.orderdetail.OrderDetailService;
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
* <b><code>OrderDetailImpl</code></b>
* <p/>
* OrderDetail的具体实现
* <p/>
* <b>Creation Time:</b> 2018-06-06 11:31:43.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(OrderDetailServiceImpl.class);

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Override
    public OrderDetailView getEntity(long id) {
        // 获取Entity
        OrderDetail orderDetail = orderDetailDao.getOne(id);
        // 复制Dao层属性到view属性
        OrderDetailView orderDetailView = new OrderDetailView();
        TorinoSrcBeanUtils.copyBean(orderDetail, orderDetailView);
        return orderDetailView;
    }

    @Override
    public Page<OrderDetailView> getEntitiesByParms(OrderDetailView orderDetailView, int currentPage, int pageSize) {
        Specification<OrderDetail> orderDetailSpecification = new Specification<OrderDetail>() {
            @Override
            public Predicate toPredicate(Root<OrderDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,orderDetailView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<OrderDetail> orderDetails = orderDetailDao.findAll(orderDetailSpecification, pageable);

        // 转换成View对象并返回
        return orderDetails.map(orderDetail->{
            OrderDetailView orderDetailView1 = new OrderDetailView();
            TorinoSrcBeanUtils.copyBean(orderDetail, orderDetailView1);
            return orderDetailView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return orderDetailDao.count();
    }

    @Override
    public List<OrderDetailView> findAll() {
        List<OrderDetailView> orderDetailViews = new ArrayList<>();
        List<OrderDetail> orderDetails = orderDetailDao.findAll();
        for (OrderDetail orderDetail : orderDetails){
            OrderDetailView orderDetailView = new OrderDetailView();
            TorinoSrcBeanUtils.copyBean(orderDetail, orderDetailView);
            orderDetailViews.add(orderDetailView);
        }
        return orderDetailViews;
    }

    @Override
    public OrderDetailView saveEntity(OrderDetailView orderDetailView) {
        // 保存的业务逻辑
        OrderDetail orderDetail = new OrderDetail();
        TorinoSrcBeanUtils.copyBean(orderDetailView, orderDetail);
        // user数据库映射传给dao进行存储
        orderDetail.setCreateTime(System.currentTimeMillis());
        orderDetail.setUpdateTime(System.currentTimeMillis());
        orderDetail.setEnabled(1);
        orderDetailDao.save(orderDetail);
        TorinoSrcBeanUtils.copyBean(orderDetail,orderDetailView);
        return orderDetailView;
    }

    @Override
    public void deleteEntity(long id) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(id);
        orderDetailDao.delete(orderDetail);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for(String entityId : entityIds){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(Long.valueOf(entityId));
            orderDetails.add(orderDetail);
        }
        orderDetailDao.deleteInBatch(orderDetails);
    }

    @Override
    public void updateEntity(OrderDetailView orderDetailView) {
        Specification<OrderDetail> orderDetailSpecification = Optional.ofNullable(orderDetailView).map( s -> {
            return new Specification<OrderDetail>() {
                @Override
                public Predicate toPredicate(Root<OrderDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("OrderDetailView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<OrderDetail> orderDetailOptionalBySearch = orderDetailDao.findOne(orderDetailSpecification);
        orderDetailOptionalBySearch.map(orderDetailBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(orderDetailView,orderDetailBySearch);
            orderDetailBySearch.setUpdateTime(System.currentTimeMillis());
            orderDetailDao.save(orderDetailBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + orderDetailView.getId() + "的数据记录"));
    }

}
