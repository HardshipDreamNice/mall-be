/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.boostproductdetailprice.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.boostproductdetailprice.BoostProductDetailPriceDao;
import com.torinosrc.model.entity.boostproductdetailprice.BoostProductDetailPrice;
import com.torinosrc.model.view.boostproductdetailprice.BoostProductDetailPriceView;
import com.torinosrc.service.boostproductdetailprice.BoostProductDetailPriceService;
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
* <b><code>BoostProductDetailPriceImpl</code></b>
* <p/>
* BoostProductDetailPrice的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-30 16:12:08.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class BoostProductDetailPriceServiceImpl implements BoostProductDetailPriceService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(BoostProductDetailPriceServiceImpl.class);

    @Autowired
    private BoostProductDetailPriceDao boostProductDetailPriceDao;

    @Override
    public BoostProductDetailPriceView getEntity(long id) {
        // 获取Entity
        BoostProductDetailPrice boostProductDetailPrice = boostProductDetailPriceDao.getOne(id);
        // 复制Dao层属性到view属性
        BoostProductDetailPriceView boostProductDetailPriceView = new BoostProductDetailPriceView();
        TorinoSrcBeanUtils.copyBean(boostProductDetailPrice, boostProductDetailPriceView);
        return boostProductDetailPriceView;
    }

    @Override
    public Page<BoostProductDetailPriceView> getEntitiesByParms(BoostProductDetailPriceView boostProductDetailPriceView, int currentPage, int pageSize) {
        Specification<BoostProductDetailPrice> boostProductDetailPriceSpecification = new Specification<BoostProductDetailPrice>() {
            @Override
            public Predicate toPredicate(Root<BoostProductDetailPrice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,boostProductDetailPriceView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<BoostProductDetailPrice> boostProductDetailPrices = boostProductDetailPriceDao.findAll(boostProductDetailPriceSpecification, pageable);

        // 转换成View对象并返回
        return boostProductDetailPrices.map(boostProductDetailPrice->{
            BoostProductDetailPriceView boostProductDetailPriceView1 = new BoostProductDetailPriceView();
            TorinoSrcBeanUtils.copyBean(boostProductDetailPrice, boostProductDetailPriceView1);
            return boostProductDetailPriceView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return boostProductDetailPriceDao.count();
    }

    @Override
    public List<BoostProductDetailPriceView> findAll() {
        List<BoostProductDetailPriceView> boostProductDetailPriceViews = new ArrayList<>();
        List<BoostProductDetailPrice> boostProductDetailPrices = boostProductDetailPriceDao.findAll();
        for (BoostProductDetailPrice boostProductDetailPrice : boostProductDetailPrices){
            BoostProductDetailPriceView boostProductDetailPriceView = new BoostProductDetailPriceView();
            TorinoSrcBeanUtils.copyBean(boostProductDetailPrice, boostProductDetailPriceView);
            boostProductDetailPriceViews.add(boostProductDetailPriceView);
        }
        return boostProductDetailPriceViews;
    }

    @Override
    public BoostProductDetailPriceView saveEntity(BoostProductDetailPriceView boostProductDetailPriceView) {
        // 保存的业务逻辑
        BoostProductDetailPrice boostProductDetailPrice = new BoostProductDetailPrice();
        TorinoSrcBeanUtils.copyBean(boostProductDetailPriceView, boostProductDetailPrice);
        // user数据库映射传给dao进行存储
        boostProductDetailPrice.setCreateTime(System.currentTimeMillis());
        boostProductDetailPrice.setUpdateTime(System.currentTimeMillis());
        boostProductDetailPrice.setEnabled(1);
        boostProductDetailPriceDao.save(boostProductDetailPrice);
        TorinoSrcBeanUtils.copyBean(boostProductDetailPrice,boostProductDetailPriceView);
        return boostProductDetailPriceView;
    }

    @Override
    public void deleteEntity(long id) {
        BoostProductDetailPrice boostProductDetailPrice = new BoostProductDetailPrice();
        boostProductDetailPrice.setId(id);
        boostProductDetailPriceDao.delete(boostProductDetailPrice);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<BoostProductDetailPrice> boostProductDetailPrices = new ArrayList<>();
        for(String entityId : entityIds){
            BoostProductDetailPrice boostProductDetailPrice = new BoostProductDetailPrice();
            boostProductDetailPrice.setId(Long.valueOf(entityId));
            boostProductDetailPrices.add(boostProductDetailPrice);
        }
        boostProductDetailPriceDao.deleteInBatch(boostProductDetailPrices);
    }

    @Override
    public void updateEntity(BoostProductDetailPriceView boostProductDetailPriceView) {
        Specification<BoostProductDetailPrice> boostProductDetailPriceSpecification = Optional.ofNullable(boostProductDetailPriceView).map( s -> {
            return new Specification<BoostProductDetailPrice>() {
                @Override
                public Predicate toPredicate(Root<BoostProductDetailPrice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("BoostProductDetailPriceView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<BoostProductDetailPrice> boostProductDetailPriceOptionalBySearch = boostProductDetailPriceDao.findOne(boostProductDetailPriceSpecification);
        boostProductDetailPriceOptionalBySearch.map(boostProductDetailPriceBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(boostProductDetailPriceView,boostProductDetailPriceBySearch);
            boostProductDetailPriceBySearch.setUpdateTime(System.currentTimeMillis());
            boostProductDetailPriceDao.save(boostProductDetailPriceBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + boostProductDetailPriceView.getId() + "的数据记录"));
    }
}
