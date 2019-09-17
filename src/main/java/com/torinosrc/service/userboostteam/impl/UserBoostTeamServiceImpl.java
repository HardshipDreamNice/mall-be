/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.userboostteam.impl;

import com.torinosrc.commons.constants.MallConstant;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.boostproduct.BoostProductDao;
import com.torinosrc.dao.boostteam.BoostTeamDao;
import com.torinosrc.dao.order.OrderDao;
import com.torinosrc.dao.product.ProductDao;
import com.torinosrc.dao.userboostteam.UserBoostTeamDao;
import com.torinosrc.model.entity.boostproduct.BoostProduct;
import com.torinosrc.model.entity.boostteam.BoostTeam;
import com.torinosrc.model.entity.order.Order;
import com.torinosrc.model.entity.orderdetail.OrderDetail;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.entity.userboostteam.UserBoostTeam;
import com.torinosrc.model.view.userboostteam.UserBoostTeamView;
import com.torinosrc.service.userboostteam.UserBoostTeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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
 * <b><code>UserBoostTeamImpl</code></b>
 * <p/>
 * UserBoostTeam的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-12-03 15:15:48.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public class UserBoostTeamServiceImpl implements UserBoostTeamService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(UserBoostTeamServiceImpl.class);

    @Autowired
    private UserBoostTeamDao userBoostTeamDao;

    @Autowired
    private BoostTeamDao boostTeamDao;

    @Autowired
    private BoostProductDao boostProductDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public UserBoostTeamView getEntity(long id) {
        // 获取Entity
        UserBoostTeam userBoostTeam = userBoostTeamDao.getOne(id);
        // 复制Dao层属性到view属性
        UserBoostTeamView userBoostTeamView = new UserBoostTeamView();
        TorinoSrcBeanUtils.copyBean(userBoostTeam, userBoostTeamView);
        return userBoostTeamView;
    }

    @Override
    public Page<UserBoostTeamView> getEntitiesByParms(UserBoostTeamView userBoostTeamView, int currentPage, int pageSize) {
        Specification<UserBoostTeam> userBoostTeamSpecification = new Specification<UserBoostTeam>() {
            @Override
            public Predicate toPredicate(Root<UserBoostTeam> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root, criteriaQuery, criteriaBuilder, userBoostTeamView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<UserBoostTeam> userBoostTeams = userBoostTeamDao.findAll(userBoostTeamSpecification, pageable);

        // 转换成View对象并返回
        return userBoostTeams.map(userBoostTeam -> {
            UserBoostTeamView userBoostTeamView1 = new UserBoostTeamView();
            TorinoSrcBeanUtils.copyBean(userBoostTeam, userBoostTeamView1);
            return userBoostTeamView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return userBoostTeamDao.count();
    }

    @Override
    public List<UserBoostTeamView> findAll() {
        List<UserBoostTeamView> userBoostTeamViews = new ArrayList<>();
        List<UserBoostTeam> userBoostTeams = userBoostTeamDao.findAll();
        for (UserBoostTeam userBoostTeam : userBoostTeams) {
            UserBoostTeamView userBoostTeamView = new UserBoostTeamView();
            TorinoSrcBeanUtils.copyBean(userBoostTeam, userBoostTeamView);
            userBoostTeamViews.add(userBoostTeamView);
        }
        return userBoostTeamViews;
    }

    @Override
    public UserBoostTeamView saveEntity(UserBoostTeamView userBoostTeamView) {
        // 保存的业务逻辑
        UserBoostTeam userBoostTeam = new UserBoostTeam();
        TorinoSrcBeanUtils.copyBean(userBoostTeamView, userBoostTeam);
        // user数据库映射传给dao进行存储
        userBoostTeam.setCreateTime(System.currentTimeMillis());
        userBoostTeam.setUpdateTime(System.currentTimeMillis());
        userBoostTeam.setEnabled(1);
        userBoostTeamDao.save(userBoostTeam);
        TorinoSrcBeanUtils.copyBean(userBoostTeam, userBoostTeamView);
        return userBoostTeamView;
    }

    @Override
    public void deleteEntity(long id) {
        UserBoostTeam userBoostTeam = new UserBoostTeam();
        userBoostTeam.setId(id);
        userBoostTeamDao.delete(userBoostTeam);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteEntities(String ids) {
        String[] entityIds = TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<UserBoostTeam> userBoostTeams = new ArrayList<>();
        for (String entityId : entityIds) {
            UserBoostTeam userBoostTeam = new UserBoostTeam();
            userBoostTeam.setId(Long.valueOf(entityId));
            userBoostTeams.add(userBoostTeam);
        }
        userBoostTeamDao.deleteInBatch(userBoostTeams);
    }

    @Override
    public void updateEntity(UserBoostTeamView userBoostTeamView) {
        Specification<UserBoostTeam> userBoostTeamSpecification = Optional.ofNullable(userBoostTeamView).map(s -> {
            return new Specification<UserBoostTeam>() {
                @Override
                public Predicate toPredicate(Root<UserBoostTeam> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(() -> new IllegalArgumentException("UserBoostTeamView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<UserBoostTeam> userBoostTeamOptionalBySearch = userBoostTeamDao.findOne(userBoostTeamSpecification);
        userBoostTeamOptionalBySearch.map(userBoostTeamBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(userBoostTeamView, userBoostTeamBySearch);
            userBoostTeamBySearch.setUpdateTime(System.currentTimeMillis());
            userBoostTeamDao.save(userBoostTeamBySearch);
            return "";
        }).orElseThrow(() -> new TorinoSrcServiceException("无法找到id为" + userBoostTeamView.getId() + "的数据记录"));
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public UserBoostTeamView saveOrReturnUserBoostTeam(UserBoostTeamView userBoostTeamView) {

        System.out.println("userBoostTeam formId: " + userBoostTeamView.getFormId());

        UserBoostTeamView userBoostTeamViewReturn = new UserBoostTeamView();
        Long boostTeamId = userBoostTeamView.getBoostTeamId();
        Long userId = userBoostTeamView.getUserId();
        UserBoostTeam userBoostTeamFromDB = userBoostTeamDao.findOneByUserIdAndBoostTeamId(userId, boostTeamId);
        if (!ObjectUtils.isEmpty(userBoostTeamFromDB)) {
            TorinoSrcBeanUtils.copyBean(userBoostTeamFromDB, userBoostTeamViewReturn);
            return userBoostTeamViewReturn;
        } else {
            // no need to do anything...
        }

        BoostTeam boostTeam;
        Optional<BoostTeam> boostTeamOpt = boostTeamDao.findById(boostTeamId);
        if (boostTeamOpt.isPresent()) {
            boostTeam = boostTeamOpt.get();
        } else {
            throw new TorinoSrcServiceException("当前助力购团队不存在");
        }

        Long expiredTime = boostTeam.getExpiredTime();
        Long currentTimeMillis = System.currentTimeMillis();
        if (expiredTime < currentTimeMillis) {
            throw new TorinoSrcServiceException("当前团队的助力购已失效");
        }

        Long productId = boostTeam.getProductId();
        Product product;
        Optional<Product> productOpt = productDao.findById(productId);
        if (productOpt.isPresent()) {
            product = productOpt.get();
        } else {
            throw new TorinoSrcServiceException("当前助力购商品已下架");
        }

        Integer boostNumber = product.getBoostNumber();
        Integer boostAmount = product.getBoostAmount();

        List<UserBoostTeam> userBoostTeams = userBoostTeamDao.findByBoostTeamId(boostTeamId);
        if (CollectionUtils.isEmpty(userBoostTeams)) {
            // 第一个人，一定是发起人
            userBoostTeamView.setType(1);
            // 团长不能帮自己砍价
            userBoostTeamView.setDiscountAmount(0);
        } else {
            // 已助力购人数 >= 可助力购人数 + 1（+1 是加上发起人，发起人不能砍价，但是又占据一条记录）
            if (userBoostTeams.size() >= boostNumber + 1) {
                throw new TorinoSrcServiceException("你来晚啦，好友已助力购成功");
            }

            userBoostTeamView.setType(0);
            if (boostNumber == 1) {
                // 如果只能有一个人帮助砍价，则一次砍掉所有可砍金额
                userBoostTeamView.setDiscountAmount(boostAmount);
                // 更新订单金额
                Long orderId = userBoostTeamView.getOrderId();
                updateOrderAmount(orderId, boostAmount);
            } else {
                // TODO：liori 暂时只能有一个人砍价，待补充可以有多人砍价的情况
            }
        }

        userBoostTeamViewReturn = this.saveEntity(userBoostTeamView);

        return userBoostTeamViewReturn;
    }

    private void updateOrderAmount(Long orderId, Integer boostAmount) {
        if (ObjectUtils.isEmpty(orderId) || ObjectUtils.isEmpty(boostAmount)) {
            throw new TorinoSrcServiceException("请传入订单ID或砍价总金额");
        } else {
           // no need to do anything...
        }

        Order order = orderDao.getOne(orderId);

        if (!MallConstant.ORDER_STATUS_WAIT_PAY.equals(order.getStatus())) {
            throw new TorinoSrcServiceException("你已支付，如需退款请卖家");
        } else {
            // no need to do anything...
        }

        Integer totalAmount = order.getTotalFee();
        order.setTotalFee(totalAmount - boostAmount);
        orderDao.save(order);
    }
}
