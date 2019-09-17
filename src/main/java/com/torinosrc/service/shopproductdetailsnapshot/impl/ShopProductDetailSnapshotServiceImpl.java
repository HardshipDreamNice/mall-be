/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.shopproductdetailsnapshot.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.shopproductdetailsnapshot.ShopProductDetailSnapshotDao;
import com.torinosrc.model.entity.shopproductdetailsnapshot.ShopProductDetailSnapshot;
import com.torinosrc.model.view.shopproductdetailsnapshot.ShopProductDetailSnapshotView;
import com.torinosrc.service.shopproductdetailsnapshot.ShopProductDetailSnapshotService;
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
* <b><code>ShopProductDetailSnapshotImpl</code></b>
* <p/>
* ShopProductDetailSnapshot的具体实现
* <p/>
* <b>Creation Time:</b> 2018-07-16 18:36:51.
*
* @author ${model.author}
* @version 2.0.0
* @since torinosrc-spring-boot-be 2.0.0
*/
@Service
public class ShopProductDetailSnapshotServiceImpl implements ShopProductDetailSnapshotService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ShopProductDetailSnapshotServiceImpl.class);

    @Autowired
    private ShopProductDetailSnapshotDao shopProductDetailSnapshotDao;

    @Override
    public ShopProductDetailSnapshotView getEntity(long id) {
        // 获取Entity
        ShopProductDetailSnapshot shopProductDetailSnapshot = shopProductDetailSnapshotDao.getOne(id);
        // 复制Dao层属性到view属性
        ShopProductDetailSnapshotView shopProductDetailSnapshotView = new ShopProductDetailSnapshotView();
        TorinoSrcBeanUtils.copyBean(shopProductDetailSnapshot, shopProductDetailSnapshotView);
        return shopProductDetailSnapshotView;
    }

    @Override
    public Page<ShopProductDetailSnapshotView> getEntitiesByParms(ShopProductDetailSnapshotView shopProductDetailSnapshotView, int currentPage, int pageSize) {
        Specification<ShopProductDetailSnapshot> shopProductDetailSnapshotSpecification = new Specification<ShopProductDetailSnapshot>() {
            @Override
            public Predicate toPredicate(Root<ShopProductDetailSnapshot> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,shopProductDetailSnapshotView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ShopProductDetailSnapshot> shopProductDetailSnapshots = shopProductDetailSnapshotDao.findAll(shopProductDetailSnapshotSpecification, pageable);

        // 转换成View对象并返回
        return shopProductDetailSnapshots.map(shopProductDetailSnapshot->{
            ShopProductDetailSnapshotView shopProductDetailSnapshotView1 = new ShopProductDetailSnapshotView();
            TorinoSrcBeanUtils.copyBean(shopProductDetailSnapshot, shopProductDetailSnapshotView1);
            return shopProductDetailSnapshotView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return shopProductDetailSnapshotDao.count();
    }

    @Override
    public List<ShopProductDetailSnapshotView> findAll() {
        List<ShopProductDetailSnapshotView> shopProductDetailSnapshotViews = new ArrayList<>();
        List<ShopProductDetailSnapshot> shopProductDetailSnapshots = shopProductDetailSnapshotDao.findAll();
        for (ShopProductDetailSnapshot shopProductDetailSnapshot : shopProductDetailSnapshots){
            ShopProductDetailSnapshotView shopProductDetailSnapshotView = new ShopProductDetailSnapshotView();
            TorinoSrcBeanUtils.copyBean(shopProductDetailSnapshot, shopProductDetailSnapshotView);
            shopProductDetailSnapshotViews.add(shopProductDetailSnapshotView);
        }
        return shopProductDetailSnapshotViews;
    }

    @Override
    public ShopProductDetailSnapshotView saveEntity(ShopProductDetailSnapshotView shopProductDetailSnapshotView) {
        // 保存的业务逻辑
        ShopProductDetailSnapshot shopProductDetailSnapshot = new ShopProductDetailSnapshot();
        TorinoSrcBeanUtils.copyBean(shopProductDetailSnapshotView, shopProductDetailSnapshot);
        // user数据库映射传给dao进行存储
        shopProductDetailSnapshot.setCreateTime(System.currentTimeMillis());
        shopProductDetailSnapshot.setUpdateTime(System.currentTimeMillis());
        shopProductDetailSnapshot.setEnabled(1);
        shopProductDetailSnapshotDao.save(shopProductDetailSnapshot);
        TorinoSrcBeanUtils.copyBean(shopProductDetailSnapshot,shopProductDetailSnapshotView);
        return shopProductDetailSnapshotView;
    }

    @Override
    public void deleteEntity(long id) {
        ShopProductDetailSnapshot shopProductDetailSnapshot = new ShopProductDetailSnapshot();
        shopProductDetailSnapshot.setId(id);
        shopProductDetailSnapshotDao.delete(shopProductDetailSnapshot);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<ShopProductDetailSnapshot> shopProductDetailSnapshots = new ArrayList<>();
        for(String entityId : entityIds){
            ShopProductDetailSnapshot shopProductDetailSnapshot = new ShopProductDetailSnapshot();
            shopProductDetailSnapshot.setId(Long.valueOf(entityId));
            shopProductDetailSnapshots.add(shopProductDetailSnapshot);
        }
        shopProductDetailSnapshotDao.deleteInBatch(shopProductDetailSnapshots);
    }

    @Override
    public void updateEntity(ShopProductDetailSnapshotView shopProductDetailSnapshotView) {
        Specification<ShopProductDetailSnapshot> shopProductDetailSnapshotSpecification = Optional.ofNullable(shopProductDetailSnapshotView).map( s -> {
            return new Specification<ShopProductDetailSnapshot>() {
                @Override
                public Predicate toPredicate(Root<ShopProductDetailSnapshot> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("ShopProductDetailSnapshotView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<ShopProductDetailSnapshot> shopProductDetailSnapshotOptionalBySearch = shopProductDetailSnapshotDao.findOne(shopProductDetailSnapshotSpecification);
        shopProductDetailSnapshotOptionalBySearch.map(shopProductDetailSnapshotBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(shopProductDetailSnapshotView,shopProductDetailSnapshotBySearch);
            shopProductDetailSnapshotBySearch.setUpdateTime(System.currentTimeMillis());
            shopProductDetailSnapshotDao.save(shopProductDetailSnapshotBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + shopProductDetailSnapshotView.getId() + "的数据记录"));
    }
}
