/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.withdrawmoney.impl;

import com.torinosrc.commons.constants.MallConstant;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.shopaccount.ShopAccountDao;
import com.torinosrc.dao.withdrawmoney.WithdrawMoneyDao;
import com.torinosrc.model.entity.shopaccount.ShopAccount;
import com.torinosrc.model.entity.withdrawmoney.WithdrawMoney;
import com.torinosrc.model.view.withdrawmoney.WithdrawMoneyView;
import com.torinosrc.service.withdrawmoney.WithdrawMoneyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * <b><code>WithdrawMoneyImpl</code></b>
 * <p/>
 * WithdrawMoney的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-08-02 11:19:16.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public class WithdrawMoneyServiceImpl implements WithdrawMoneyService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(WithdrawMoneyServiceImpl.class);

    @Autowired
    private WithdrawMoneyDao withdrawMoneyDao;

    @Autowired
    private ShopAccountDao shopAccountDao;

    @Override
    public WithdrawMoneyView getEntity(long id) {
        // 获取Entity
        WithdrawMoney withdrawMoney = withdrawMoneyDao.getOne(id);
        // 复制Dao层属性到view属性
        WithdrawMoneyView withdrawMoneyView = new WithdrawMoneyView();
        TorinoSrcBeanUtils.copyBean(withdrawMoney, withdrawMoneyView);
        return withdrawMoneyView;
    }

    @Override
    public Page<WithdrawMoneyView> getEntitiesByParms(WithdrawMoneyView withdrawMoneyView, int currentPage, int pageSize) {
        Specification<WithdrawMoney> withdrawMoneySpecification = new Specification<WithdrawMoney>() {
            @Override
            public Predicate toPredicate(Root<WithdrawMoney> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root, criteriaQuery, criteriaBuilder, withdrawMoneyView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<WithdrawMoney> withdrawMoneys = withdrawMoneyDao.findAll(withdrawMoneySpecification, pageable);

        // 转换成View对象并返回
        return withdrawMoneys.map(withdrawMoney -> {
            WithdrawMoneyView withdrawMoneyView1 = new WithdrawMoneyView();
            TorinoSrcBeanUtils.copyBean(withdrawMoney, withdrawMoneyView1);
            return withdrawMoneyView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return withdrawMoneyDao.count();
    }

    @Override
    public List<WithdrawMoneyView> findAll() {
        List<WithdrawMoneyView> withdrawMoneyViews = new ArrayList<>();
        List<WithdrawMoney> withdrawMoneys = withdrawMoneyDao.findAll();
        for (WithdrawMoney withdrawMoney : withdrawMoneys) {
            WithdrawMoneyView withdrawMoneyView = new WithdrawMoneyView();
            TorinoSrcBeanUtils.copyBean(withdrawMoney, withdrawMoneyView);
            withdrawMoneyViews.add(withdrawMoneyView);
        }
        return withdrawMoneyViews;
    }

    @Override
    public WithdrawMoneyView findByShopIdAndStatus(Long shopId, Integer status) {
        WithdrawMoneyView withdrawMoneyView = new WithdrawMoneyView();
        if (!ObjectUtils.isEmpty(withdrawMoneyDao.findByShopIdAndStatus(shopId, status))) {
            WithdrawMoney withdrawMoney = withdrawMoneyDao.findByShopIdAndStatus(shopId, status);
            TorinoSrcBeanUtils.copyBean(withdrawMoney, withdrawMoneyView);
        }
        return withdrawMoneyView;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public WithdrawMoneyView saveEntity(WithdrawMoneyView withdrawMoneyView) {
        // 是否有足够的余额可提现
        ShopAccount shopAccount = shopAccountDao.findShopAccountByShopId(withdrawMoneyView.getShopId());
        if (shopAccount.getMoney() < withdrawMoneyView.getAmount()) {
            throw new TorinoSrcServiceException("提现余额不足");
        }

        // 账户余额 - 提现金额（审核通过再减）
//        shopAccount.setMoney(shopAccount.getMoney() - withdrawMoneyView.getAmount());
//        shopAccountDao.save(shopAccount);

        // 保存的业务逻辑
        WithdrawMoney withdrawMoney = new WithdrawMoney();
        TorinoSrcBeanUtils.copyBean(withdrawMoneyView, withdrawMoney);
        // user数据库映射传给dao进行存储
        withdrawMoney.setCreateTime(System.currentTimeMillis());
        withdrawMoney.setUpdateTime(System.currentTimeMillis());
        withdrawMoney.setEnabled(1);
        withdrawMoney.setStatus(MallConstant.WITHDRAW_MONEY_STATUS_AUDIT_GOING);
        withdrawMoneyDao.save(withdrawMoney);
        TorinoSrcBeanUtils.copyBean(withdrawMoney, withdrawMoneyView);
        return withdrawMoneyView;
    }

    @Override
    public void deleteEntity(long id) {
        WithdrawMoney withdrawMoney = new WithdrawMoney();
        withdrawMoney.setId(id);
        withdrawMoneyDao.delete(withdrawMoney);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteEntities(String ids) {
        String[] entityIds = TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<WithdrawMoney> withdrawMoneys = new ArrayList<>();
        for (String entityId : entityIds) {
            WithdrawMoney withdrawMoney = new WithdrawMoney();
            withdrawMoney.setId(Long.valueOf(entityId));
            withdrawMoneys.add(withdrawMoney);
        }
        withdrawMoneyDao.deleteInBatch(withdrawMoneys);
    }

    @Override
    public void updateEntity(WithdrawMoneyView withdrawMoneyView) {
        Specification<WithdrawMoney> withdrawMoneySpecification = Optional.ofNullable(withdrawMoneyView).map(s -> {
            return new Specification<WithdrawMoney>() {
                @Override
                public Predicate toPredicate(Root<WithdrawMoney> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(() -> new IllegalArgumentException("WithdrawMoneyView is null"));

        // 获取原有的属性，把不变的属性覆盖
        Optional<WithdrawMoney> withdrawMoneyOptionalBySearch = withdrawMoneyDao.findOne(withdrawMoneySpecification);
        withdrawMoneyOptionalBySearch.map(withdrawMoneyBySearch -> {

            // 如果状态是更新为审核通过且原来的状态不是审核通过，账户余额 - 提现金额
            if (!MallConstant.WITHDRAW_MONEY_STATUS_AUDIT_SUCCESS.equals(withdrawMoneyBySearch.getStatus()) &&
                    MallConstant.WITHDRAW_MONEY_STATUS_AUDIT_SUCCESS.equals(withdrawMoneyView.getStatus())) {
                ShopAccount shopAccount = shopAccountDao.findShopAccountByShopId(withdrawMoneyBySearch.getShopId());
                shopAccount.setMoney(shopAccount.getMoney() - withdrawMoneyBySearch.getAmount());
                shopAccountDao.save(shopAccount);
            }

            // 如果状态是更新为提现失败且原来的状态不是提现失败，将审核成功减去的提现金额加回总账户
            if (!MallConstant.WITHDRAW_MONEY_STATUS_WITHDRAW_FAIL.equals(withdrawMoneyBySearch.getStatus()) &&
                    MallConstant.WITHDRAW_MONEY_STATUS_WITHDRAW_FAIL.equals(withdrawMoneyView.getStatus())) {
                ShopAccount shopAccount = shopAccountDao.findShopAccountByShopId(withdrawMoneyBySearch.getShopId());
                shopAccount.setMoney(shopAccount.getMoney() + withdrawMoneyBySearch.getAmount());
                shopAccountDao.save(shopAccount);
            }

            TorinoSrcBeanUtils.copyBeanExcludeNull(withdrawMoneyView, withdrawMoneyBySearch);
            withdrawMoneyBySearch.setUpdateTime(System.currentTimeMillis());
            withdrawMoneyDao.save(withdrawMoneyBySearch);
            return "";
        }).orElseThrow(() -> new TorinoSrcServiceException("无法找到id为" + withdrawMoneyView.getId() + "的数据记录"));
    }

    @Override
    public WithdrawMoneyView findWithdrawMoneyViewProcessingByShopId(Long shopId) {

        List<Integer> statusInSuccessOrFail = Arrays.asList(
                MallConstant.WITHDRAW_MONEY_STATUS_WITHDRAW_SUCCESS, MallConstant.WITHDRAW_MONEY_STATUS_WITHDRAW_FAIL);
        List<WithdrawMoney> withdrawMoneyStatusNotInSuccessOrFailList = withdrawMoneyDao.findByShopIdAndStatusNotIn(shopId, statusInSuccessOrFail);

        if (CollectionUtils.isEmpty(withdrawMoneyStatusNotInSuccessOrFailList)) {
            return new WithdrawMoneyView();
        } else if (withdrawMoneyStatusNotInSuccessOrFailList.size() > 1) {
            throw new TorinoSrcServiceException("提现中的流水大于 2 条，请联系管理员修改");
        }

        WithdrawMoney withdrawMoney = withdrawMoneyStatusNotInSuccessOrFailList.get(0);
        WithdrawMoneyView withdrawMoneyViewReturn = new WithdrawMoneyView();
        TorinoSrcBeanUtils.copyBean(withdrawMoney, withdrawMoneyViewReturn);

        return withdrawMoneyViewReturn;
    }

    @Override
    public WithdrawMoneyView findWithdrawMoneyViewCreateLastestByShopId(Long shopId) {
        WithdrawMoney withdrawMoney = withdrawMoneyDao.findFirstByShopIdOrderByCreateTimeDesc(shopId);
        WithdrawMoneyView withdrawMoneyViewReturn = new WithdrawMoneyView();
        if (ObjectUtils.isEmpty(withdrawMoney)) {
            return withdrawMoneyViewReturn;
        }

        TorinoSrcBeanUtils.copyBean(withdrawMoney, withdrawMoneyViewReturn);
        return withdrawMoneyViewReturn;
    }
}
