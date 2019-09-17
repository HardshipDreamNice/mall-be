/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.boostteam.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.DateUtils;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.boostteam.BoostTeamDao;
import com.torinosrc.dao.product.ProductDao;
import com.torinosrc.dao.productdetail.ProductDetailDao;
import com.torinosrc.dao.userboostteam.UserBoostTeamDao;
import com.torinosrc.model.entity.boostteam.BoostTeam;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.entity.userboostteam.UserBoostTeam;
import com.torinosrc.model.view.boostteam.BoostTeamView;
import com.torinosrc.model.view.boostteam.BoostTeamWithProductInfoView;
import com.torinosrc.service.boostteam.BoostTeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <b><code>BoostTeamImpl</code></b>
 * <p/>
 * BoostTeam的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-12-03 15:14:32.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public class BoostTeamServiceImpl implements BoostTeamService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(BoostTeamServiceImpl.class);

    @Autowired
    private BoostTeamDao boostTeamDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductDetailDao productDetailDao;

    @Autowired
    private UserBoostTeamDao userBoostTeamDao;

    @Override
    public BoostTeamView getEntity(long id) {
        // 获取Entity
        BoostTeam boostTeam = boostTeamDao.getOne(id);
        // 复制Dao层属性到view属性
        BoostTeamView boostTeamView = new BoostTeamView();
        TorinoSrcBeanUtils.copyBean(boostTeam, boostTeamView);
        return boostTeamView;
    }

    @Override
    public Page<BoostTeamView> getEntitiesByParms(BoostTeamView boostTeamView, int currentPage, int pageSize) {
        Specification<BoostTeam> boostTeamSpecification = new Specification<BoostTeam>() {
            @Override
            public Predicate toPredicate(Root<BoostTeam> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root, criteriaQuery, criteriaBuilder, boostTeamView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<BoostTeam> boostTeams = boostTeamDao.findAll(boostTeamSpecification, pageable);

        // 转换成View对象并返回
        return boostTeams.map(boostTeam -> {
            BoostTeamView boostTeamView1 = new BoostTeamView();
            TorinoSrcBeanUtils.copyBean(boostTeam, boostTeamView1);
            return boostTeamView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return boostTeamDao.count();
    }

    @Override
    public List<BoostTeamView> findAll() {
        List<BoostTeamView> boostTeamViews = new ArrayList<>();
        List<BoostTeam> boostTeams = boostTeamDao.findAll();
        for (BoostTeam boostTeam : boostTeams) {
            BoostTeamView boostTeamView = new BoostTeamView();
            TorinoSrcBeanUtils.copyBean(boostTeam, boostTeamView);
            boostTeamViews.add(boostTeamView);
        }
        return boostTeamViews;
    }

    @Override
    public BoostTeamView saveEntity(BoostTeamView boostTeamView) {
        // 保存的业务逻辑
        BoostTeam boostTeam = new BoostTeam();
        TorinoSrcBeanUtils.copyBean(boostTeamView, boostTeam);
        // user数据库映射传给dao进行存储
        boostTeam.setCreateTime(System.currentTimeMillis());
        boostTeam.setUpdateTime(System.currentTimeMillis());
        boostTeam.setEnabled(1);
        boostTeamDao.save(boostTeam);
        TorinoSrcBeanUtils.copyBean(boostTeam, boostTeamView);
        return boostTeamView;
    }

    @Override
    public void deleteEntity(long id) {
        BoostTeam boostTeam = new BoostTeam();
        boostTeam.setId(id);
        boostTeamDao.delete(boostTeam);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteEntities(String ids) {
        String[] entityIds = TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<BoostTeam> boostTeams = new ArrayList<>();
        for (String entityId : entityIds) {
            BoostTeam boostTeam = new BoostTeam();
            boostTeam.setId(Long.valueOf(entityId));
            boostTeams.add(boostTeam);
        }
        boostTeamDao.deleteInBatch(boostTeams);
    }

    @Override
    public void updateEntity(BoostTeamView boostTeamView) {
        Specification<BoostTeam> boostTeamSpecification = Optional.ofNullable(boostTeamView).map(s -> {
            return new Specification<BoostTeam>() {
                @Override
                public Predicate toPredicate(Root<BoostTeam> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(() -> new IllegalArgumentException("BoostTeamView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<BoostTeam> boostTeamOptionalBySearch = boostTeamDao.findOne(boostTeamSpecification);
        boostTeamOptionalBySearch.map(boostTeamBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(boostTeamView, boostTeamBySearch);
            boostTeamBySearch.setUpdateTime(System.currentTimeMillis());
            boostTeamDao.save(boostTeamBySearch);
            return "";
        }).orElseThrow(() -> new TorinoSrcServiceException("无法找到id为" + boostTeamView.getId() + "的数据记录"));
    }

    @Override
    public BoostTeamWithProductInfoView getBoostTeamViewWithProduct(Long id) {

        BoostTeamWithProductInfoView boostTeamWithProductInfoViewReturn = new BoostTeamWithProductInfoView();

        // 设置助力购的团队信息
        Optional<BoostTeam> boostTeamOpt = boostTeamDao.findById(id);
        BoostTeam boostTeam;
        if (!boostTeamOpt.isPresent()) {
            throw new TorinoSrcServiceException("id为" + id + "的助力购团队不存在");
        } else {
            boostTeam = boostTeamOpt.get();
        }
        TorinoSrcBeanUtils.copyBean(boostTeam, boostTeamWithProductInfoViewReturn);
        boostTeamWithProductInfoViewReturn.setBoostExpiredTime(DateUtils.timeStamp2Date(String.valueOf(boostTeam.getExpiredTime()), "yyyy-MM-dd HH:mm:ss"));

        // 设置助力购的商品属性
        Long productId = boostTeam.getProductId();
        Optional<Product> productOpt = productDao.findById(productId);
        Product product;
        if (!productOpt.isPresent()) {
            throw new TorinoSrcServiceException("id为" + productId + "的商品不存在");
        } else {
            product = productOpt.get();
        }
        if (!StringUtils.isEmpty(product.getSmallImageUrl())) {
            boostTeamWithProductInfoViewReturn.setSmallImageUrl(product.getSmallImageUrl());
        }
        if (!StringUtils.isEmpty(product.getImageUrl())) {
            boostTeamWithProductInfoViewReturn.setImageUrl(product.getImageUrl());
        }
        if (!StringUtils.isEmpty(product.getImageUrl1())) {
            boostTeamWithProductInfoViewReturn.setImageUrl1(product.getImageUrl1());
        }
        if (!StringUtils.isEmpty(product.getImageUrl2())) {
            boostTeamWithProductInfoViewReturn.setImageUrl2(product.getImageUrl2());
        }
        boostTeamWithProductInfoViewReturn.setProductExpiredTime(product.getExpiredTime());

        // 设置助力购的商品详情属性
        Long productDetailId = boostTeam.getProductDetailId();
        Optional<ProductDetail> productDetailOpt = productDetailDao.findById(productDetailId);
        ProductDetail productDetail;
        if (!productDetailOpt.isPresent()) {
            throw new TorinoSrcServiceException("id为" + productDetailId + "的商品详情不存在");
        } else {
            productDetail = productDetailOpt.get();
        }
        boostTeamWithProductInfoViewReturn.setInventory(productDetail.getInventory());
        Long originalPrice = productDetail.getProductDetailPriceList().get(0).getPrice();
        boostTeamWithProductInfoViewReturn.setPrice(originalPrice);

        // 设置砍后价
        List<UserBoostTeam> userBoostTeams = userBoostTeamDao.findByBoostTeamId(id);
        Integer totalDiscountAmount = 0;
        for (UserBoostTeam userBoostTeam : userBoostTeams) {
            totalDiscountAmount += userBoostTeam.getDiscountAmount();
        }
        boostTeamWithProductInfoViewReturn.setDiscountPrice(originalPrice - totalDiscountAmount);

        // 设置砍价状态 0：未完成 1：已完成
        // - 1 是减去发起者自己
        if (userBoostTeams.size() - 1 < boostTeam.getBoostNumber()) {
            boostTeamWithProductInfoViewReturn.setStatus(0);
        } else {
            boostTeamWithProductInfoViewReturn.setStatus(1);
        }

        return boostTeamWithProductInfoViewReturn;
    }
}
