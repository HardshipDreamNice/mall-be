/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.shopvisitrecord.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.shopvisitrecord.ShopVisitRecordDao;
import com.torinosrc.model.entity.shopvisitrecord.ShopVisitRecord;
import com.torinosrc.model.view.shopvisitrecord.ShopVisitRecordView;
import com.torinosrc.service.shopvisitrecord.ShopVisitRecordService;
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
 * <b><code>ShopVisitRecordImpl</code></b>
 * <p/>
 * ShopVisitRecord的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-19 18:21:46.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public class ShopVisitRecordServiceImpl implements ShopVisitRecordService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ShopVisitRecordServiceImpl.class);

    @Autowired
    private ShopVisitRecordDao shopVisitRecordDao;

    @Override
    public ShopVisitRecordView getEntity(long id) {
        // 获取Entity
        ShopVisitRecord shopVisitRecord = shopVisitRecordDao.getOne(id);
        // 复制Dao层属性到view属性
        ShopVisitRecordView shopVisitRecordView = new ShopVisitRecordView();
        TorinoSrcBeanUtils.copyBean(shopVisitRecord, shopVisitRecordView);
        return shopVisitRecordView;
    }

    @Override
    public Page<ShopVisitRecordView> getEntitiesByParms(ShopVisitRecordView shopVisitRecordView, int currentPage, int pageSize) {
        Specification<ShopVisitRecord> shopVisitRecordSpecification = new Specification<ShopVisitRecord>() {
            @Override
            public Predicate toPredicate(Root<ShopVisitRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root, criteriaQuery, criteriaBuilder, shopVisitRecordView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ShopVisitRecord> shopVisitRecords = shopVisitRecordDao.findAll(shopVisitRecordSpecification, pageable);

        // 转换成View对象并返回
        return shopVisitRecords.map(shopVisitRecord -> {
            ShopVisitRecordView shopVisitRecordView1 = new ShopVisitRecordView();
            TorinoSrcBeanUtils.copyBean(shopVisitRecord, shopVisitRecordView1);
            return shopVisitRecordView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return shopVisitRecordDao.count();
    }

    @Override
    public List<ShopVisitRecordView> findAll() {
        List<ShopVisitRecordView> shopVisitRecordViews = new ArrayList<>();
        List<ShopVisitRecord> shopVisitRecords = shopVisitRecordDao.findAll();
        for (ShopVisitRecord shopVisitRecord : shopVisitRecords) {
            ShopVisitRecordView shopVisitRecordView = new ShopVisitRecordView();
            TorinoSrcBeanUtils.copyBean(shopVisitRecord, shopVisitRecordView);
            shopVisitRecordViews.add(shopVisitRecordView);
        }
        return shopVisitRecordViews;
    }

    @Override
    public ShopVisitRecordView saveEntity(ShopVisitRecordView shopVisitRecordView) {
        // 保存的业务逻辑
        ShopVisitRecord shopVisitRecord = new ShopVisitRecord();
        TorinoSrcBeanUtils.copyBean(shopVisitRecordView, shopVisitRecord);
        // user数据库映射传给dao进行存储
        shopVisitRecord.setCreateTime(System.currentTimeMillis());
        shopVisitRecord.setUpdateTime(System.currentTimeMillis());
        shopVisitRecord.setEnabled(1);
        shopVisitRecordDao.save(shopVisitRecord);
        TorinoSrcBeanUtils.copyBean(shopVisitRecord, shopVisitRecordView);
        return shopVisitRecordView;
    }

    @Override
    public void deleteEntity(long id) {
        ShopVisitRecord shopVisitRecord = new ShopVisitRecord();
        shopVisitRecord.setId(id);
        shopVisitRecordDao.delete(shopVisitRecord);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteEntities(String ids) {
        String[] entityIds = TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<ShopVisitRecord> shopVisitRecords = new ArrayList<>();
        for (String entityId : entityIds) {
            ShopVisitRecord shopVisitRecord = new ShopVisitRecord();
            shopVisitRecord.setId(Long.valueOf(entityId));
            shopVisitRecords.add(shopVisitRecord);
        }
        shopVisitRecordDao.deleteInBatch(shopVisitRecords);
    }

    @Override
    public void updateEntity(ShopVisitRecordView shopVisitRecordView) {
        Specification<ShopVisitRecord> shopVisitRecordSpecification = Optional.ofNullable(shopVisitRecordView).map(s -> {
            return new Specification<ShopVisitRecord>() {
                @Override
                public Predicate toPredicate(Root<ShopVisitRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(() -> new IllegalArgumentException("ShopVisitRecordView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<ShopVisitRecord> shopVisitRecordOptionalBySearch = shopVisitRecordDao.findOne(shopVisitRecordSpecification);
        shopVisitRecordOptionalBySearch.map(shopVisitRecordBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(shopVisitRecordView, shopVisitRecordBySearch);
            shopVisitRecordBySearch.setUpdateTime(System.currentTimeMillis());
            shopVisitRecordDao.save(shopVisitRecordBySearch);
            return "";
        }).orElseThrow(() -> new TorinoSrcServiceException("无法找到id为" + shopVisitRecordView.getId() + "的数据记录"));
    }

    @Override
    public ShopVisitRecordView saveEntityIfNoExistAndUpdateIfExist(ShopVisitRecordView shopVisitRecordView) {

        Long userId = shopVisitRecordView.getUserId();
        Long shopId = shopVisitRecordView.getShopId();
        ShopVisitRecord shopVisitRecordFromDB = shopVisitRecordDao.findByUserIdAndShopId(userId, shopId);

        ShopVisitRecordView shopVisitRecordViewReturn = new ShopVisitRecordView();
        if (ObjectUtils.isEmpty(shopVisitRecordFromDB)) {
            shopVisitRecordViewReturn = this.saveEntity(shopVisitRecordView);
        } else {
            shopVisitRecordFromDB.setUpdateTime(System.currentTimeMillis());
            shopVisitRecordDao.save(shopVisitRecordFromDB);
            TorinoSrcBeanUtils.copyBean(shopVisitRecordFromDB, shopVisitRecordViewReturn);
        }

        return shopVisitRecordViewReturn;
    }

    @Override
    public ShopVisitRecordView getEntityUpdateLastestByUserIdAndEnabled(Long userId, Integer enabled) {
        ShopVisitRecordView shopVisitRecordViewReturn = new ShopVisitRecordView();
        ShopVisitRecord shopVisitRecordLastest = shopVisitRecordDao.findFirstByUserIdAndEnabledOrderByUpdateTimeDesc(userId, 1);

        if (ObjectUtils.isEmpty(shopVisitRecordLastest)) {
            return shopVisitRecordViewReturn;
        }

        TorinoSrcBeanUtils.copyBean(shopVisitRecordLastest, shopVisitRecordViewReturn);

        return shopVisitRecordViewReturn;
    }
}
