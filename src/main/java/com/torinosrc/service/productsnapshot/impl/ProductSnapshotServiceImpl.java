/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.productsnapshot.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.productsnapshot.ProductSnapshotDao;
import com.torinosrc.model.entity.productsnapshot.ProductSnapshot;
import com.torinosrc.model.view.productsnapshot.ProductSnapshotView;
import com.torinosrc.service.productsnapshot.ProductSnapshotService;
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
* <b><code>ProductSnapshotImpl</code></b>
* <p/>
* ProductSnapshot的具体实现
* <p/>
* <b>Creation Time:</b> 2018-07-16 18:35:02.
*
* @author ${model.author}
* @version 2.0.0
* @since torinosrc-spring-boot-be 2.0.0
*/
@Service
public class ProductSnapshotServiceImpl implements ProductSnapshotService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ProductSnapshotServiceImpl.class);

    @Autowired
    private ProductSnapshotDao productSnapshotDao;

    @Override
    public ProductSnapshotView getEntity(long id) {
        // 获取Entity
        ProductSnapshot productSnapshot = productSnapshotDao.getOne(id);
        // 复制Dao层属性到view属性
        ProductSnapshotView productSnapshotView = new ProductSnapshotView();
        TorinoSrcBeanUtils.copyBean(productSnapshot, productSnapshotView);
        return productSnapshotView;
    }

    @Override
    public Page<ProductSnapshotView> getEntitiesByParms(ProductSnapshotView productSnapshotView, int currentPage, int pageSize) {
        Specification<ProductSnapshot> productSnapshotSpecification = new Specification<ProductSnapshot>() {
            @Override
            public Predicate toPredicate(Root<ProductSnapshot> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,productSnapshotView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ProductSnapshot> productSnapshots = productSnapshotDao.findAll(productSnapshotSpecification, pageable);

        // 转换成View对象并返回
        return productSnapshots.map(productSnapshot->{
            ProductSnapshotView productSnapshotView1 = new ProductSnapshotView();
            TorinoSrcBeanUtils.copyBean(productSnapshot, productSnapshotView1);
            return productSnapshotView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return productSnapshotDao.count();
    }

    @Override
    public List<ProductSnapshotView> findAll() {
        List<ProductSnapshotView> productSnapshotViews = new ArrayList<>();
        List<ProductSnapshot> productSnapshots = productSnapshotDao.findAll();
        for (ProductSnapshot productSnapshot : productSnapshots){
            ProductSnapshotView productSnapshotView = new ProductSnapshotView();
            TorinoSrcBeanUtils.copyBean(productSnapshot, productSnapshotView);
            productSnapshotViews.add(productSnapshotView);
        }
        return productSnapshotViews;
    }

    @Override
    public ProductSnapshotView saveEntity(ProductSnapshotView productSnapshotView) {
        // 保存的业务逻辑
        ProductSnapshot productSnapshot = new ProductSnapshot();
        TorinoSrcBeanUtils.copyBean(productSnapshotView, productSnapshot);
        // user数据库映射传给dao进行存储
        productSnapshot.setCreateTime(System.currentTimeMillis());
        productSnapshot.setUpdateTime(System.currentTimeMillis());
        productSnapshot.setEnabled(1);
        productSnapshotDao.save(productSnapshot);
        TorinoSrcBeanUtils.copyBean(productSnapshot,productSnapshotView);
        return productSnapshotView;
    }

    @Override
    public void deleteEntity(long id) {
        ProductSnapshot productSnapshot = new ProductSnapshot();
        productSnapshot.setId(id);
        productSnapshotDao.delete(productSnapshot);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<ProductSnapshot> productSnapshots = new ArrayList<>();
        for(String entityId : entityIds){
            ProductSnapshot productSnapshot = new ProductSnapshot();
            productSnapshot.setId(Long.valueOf(entityId));
            productSnapshots.add(productSnapshot);
        }
        productSnapshotDao.deleteInBatch(productSnapshots);
    }

    @Override
    public void updateEntity(ProductSnapshotView productSnapshotView) {
        Specification<ProductSnapshot> productSnapshotSpecification = Optional.ofNullable(productSnapshotView).map( s -> {
            return new Specification<ProductSnapshot>() {
                @Override
                public Predicate toPredicate(Root<ProductSnapshot> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("ProductSnapshotView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<ProductSnapshot> productSnapshotOptionalBySearch = productSnapshotDao.findOne(productSnapshotSpecification);
        productSnapshotOptionalBySearch.map(productSnapshotBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(productSnapshotView,productSnapshotBySearch);
            productSnapshotBySearch.setUpdateTime(System.currentTimeMillis());
            productSnapshotDao.save(productSnapshotBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + productSnapshotView.getId() + "的数据记录"));
    }
}
