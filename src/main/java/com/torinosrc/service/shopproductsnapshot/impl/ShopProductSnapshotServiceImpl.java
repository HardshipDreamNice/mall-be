/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.shopproductsnapshot.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.shopproductsnapshot.ShopProductSnapshotDao;
import com.torinosrc.model.entity.shopproductsnapshot.ShopProductSnapshot;
import com.torinosrc.model.view.shopproductsnapshot.ShopProductSnapshotView;
import com.torinosrc.service.shopproductsnapshot.ShopProductSnapshotService;
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
* <b><code>ShopProductSnapshotImpl</code></b>
* <p/>
* ShopProductSnapshot的具体实现
* <p/>
* <b>Creation Time:</b> 2018-07-16 18:36:22.
*
* @author ${model.author}
* @version 2.0.0
* @since torinosrc-spring-boot-be 2.0.0
*/
@Service
public class ShopProductSnapshotServiceImpl implements ShopProductSnapshotService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ShopProductSnapshotServiceImpl.class);

    @Autowired
    private ShopProductSnapshotDao shopProductSnapshotDao;

    @Override
    public ShopProductSnapshotView getEntity(long id) {
        // 获取Entity
        ShopProductSnapshot shopProductSnapshot = shopProductSnapshotDao.getOne(id);
        // 复制Dao层属性到view属性
        ShopProductSnapshotView shopProductSnapshotView = new ShopProductSnapshotView();
        TorinoSrcBeanUtils.copyBean(shopProductSnapshot, shopProductSnapshotView);
        return shopProductSnapshotView;
    }

    @Override
    public Page<ShopProductSnapshotView> getEntitiesByParms(ShopProductSnapshotView shopProductSnapshotView, int currentPage, int pageSize) {
        Specification<ShopProductSnapshot> shopProductSnapshotSpecification = new Specification<ShopProductSnapshot>() {
            @Override
            public Predicate toPredicate(Root<ShopProductSnapshot> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,shopProductSnapshotView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ShopProductSnapshot> shopProductSnapshots = shopProductSnapshotDao.findAll(shopProductSnapshotSpecification, pageable);

        // 转换成View对象并返回
        return shopProductSnapshots.map(shopProductSnapshot->{
            ShopProductSnapshotView shopProductSnapshotView1 = new ShopProductSnapshotView();
            TorinoSrcBeanUtils.copyBean(shopProductSnapshot, shopProductSnapshotView1);
            return shopProductSnapshotView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return shopProductSnapshotDao.count();
    }

    @Override
    public List<ShopProductSnapshotView> findAll() {
        List<ShopProductSnapshotView> shopProductSnapshotViews = new ArrayList<>();
        List<ShopProductSnapshot> shopProductSnapshots = shopProductSnapshotDao.findAll();
        for (ShopProductSnapshot shopProductSnapshot : shopProductSnapshots){
            ShopProductSnapshotView shopProductSnapshotView = new ShopProductSnapshotView();
            TorinoSrcBeanUtils.copyBean(shopProductSnapshot, shopProductSnapshotView);
            shopProductSnapshotViews.add(shopProductSnapshotView);
        }
        return shopProductSnapshotViews;
    }

    @Override
    public ShopProductSnapshotView saveEntity(ShopProductSnapshotView shopProductSnapshotView) {
        // 保存的业务逻辑
        ShopProductSnapshot shopProductSnapshot = new ShopProductSnapshot();
        TorinoSrcBeanUtils.copyBean(shopProductSnapshotView, shopProductSnapshot);
        // user数据库映射传给dao进行存储
        shopProductSnapshot.setCreateTime(System.currentTimeMillis());
        shopProductSnapshot.setUpdateTime(System.currentTimeMillis());
        shopProductSnapshot.setEnabled(1);
        shopProductSnapshotDao.save(shopProductSnapshot);
        TorinoSrcBeanUtils.copyBean(shopProductSnapshot,shopProductSnapshotView);
        return shopProductSnapshotView;
    }

    @Override
    public void deleteEntity(long id) {
        ShopProductSnapshot shopProductSnapshot = new ShopProductSnapshot();
        shopProductSnapshot.setId(id);
        shopProductSnapshotDao.delete(shopProductSnapshot);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<ShopProductSnapshot> shopProductSnapshots = new ArrayList<>();
        for(String entityId : entityIds){
            ShopProductSnapshot shopProductSnapshot = new ShopProductSnapshot();
            shopProductSnapshot.setId(Long.valueOf(entityId));
            shopProductSnapshots.add(shopProductSnapshot);
        }
        shopProductSnapshotDao.deleteInBatch(shopProductSnapshots);
    }

    @Override
    public void updateEntity(ShopProductSnapshotView shopProductSnapshotView) {
        Specification<ShopProductSnapshot> shopProductSnapshotSpecification = Optional.ofNullable(shopProductSnapshotView).map( s -> {
            return new Specification<ShopProductSnapshot>() {
                @Override
                public Predicate toPredicate(Root<ShopProductSnapshot> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("ShopProductSnapshotView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<ShopProductSnapshot> shopProductSnapshotOptionalBySearch = shopProductSnapshotDao.findOne(shopProductSnapshotSpecification);
        shopProductSnapshotOptionalBySearch.map(shopProductSnapshotBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(shopProductSnapshotView,shopProductSnapshotBySearch);
            shopProductSnapshotBySearch.setUpdateTime(System.currentTimeMillis());
            shopProductSnapshotDao.save(shopProductSnapshotBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + shopProductSnapshotView.getId() + "的数据记录"));
    }
}
