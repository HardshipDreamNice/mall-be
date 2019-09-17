/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.productdetailsnapshot.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.productdetailsnapshot.ProductDetailSnapshotDao;
import com.torinosrc.model.entity.productdetailsnapshot.ProductDetailSnapshot;
import com.torinosrc.model.view.productdetailsnapshot.ProductDetailSnapshotView;
import com.torinosrc.service.productdetailsnapshot.ProductDetailSnapshotService;
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
* <b><code>ProductDetailSnapshotImpl</code></b>
* <p/>
* ProductDetailSnapshot的具体实现
* <p/>
* <b>Creation Time:</b> 2018-07-16 18:35:34.
*
* @author ${model.author}
* @version 2.0.0
* @since torinosrc-spring-boot-be 2.0.0
*/
@Service
public class ProductDetailSnapshotServiceImpl implements ProductDetailSnapshotService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ProductDetailSnapshotServiceImpl.class);

    @Autowired
    private ProductDetailSnapshotDao productDetailSnapshotDao;

    @Override
    public ProductDetailSnapshotView getEntity(long id) {
        // 获取Entity
        ProductDetailSnapshot productDetailSnapshot = productDetailSnapshotDao.getOne(id);
        // 复制Dao层属性到view属性
        ProductDetailSnapshotView productDetailSnapshotView = new ProductDetailSnapshotView();
        TorinoSrcBeanUtils.copyBean(productDetailSnapshot, productDetailSnapshotView);
        return productDetailSnapshotView;
    }

    @Override
    public Page<ProductDetailSnapshotView> getEntitiesByParms(ProductDetailSnapshotView productDetailSnapshotView, int currentPage, int pageSize) {
        Specification<ProductDetailSnapshot> productDetailSnapshotSpecification = new Specification<ProductDetailSnapshot>() {
            @Override
            public Predicate toPredicate(Root<ProductDetailSnapshot> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,productDetailSnapshotView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ProductDetailSnapshot> productDetailSnapshots = productDetailSnapshotDao.findAll(productDetailSnapshotSpecification, pageable);

        // 转换成View对象并返回
        return productDetailSnapshots.map(productDetailSnapshot->{
            ProductDetailSnapshotView productDetailSnapshotView1 = new ProductDetailSnapshotView();
            TorinoSrcBeanUtils.copyBean(productDetailSnapshot, productDetailSnapshotView1);
            return productDetailSnapshotView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return productDetailSnapshotDao.count();
    }

    @Override
    public List<ProductDetailSnapshotView> findAll() {
        List<ProductDetailSnapshotView> productDetailSnapshotViews = new ArrayList<>();
        List<ProductDetailSnapshot> productDetailSnapshots = productDetailSnapshotDao.findAll();
        for (ProductDetailSnapshot productDetailSnapshot : productDetailSnapshots){
            ProductDetailSnapshotView productDetailSnapshotView = new ProductDetailSnapshotView();
            TorinoSrcBeanUtils.copyBean(productDetailSnapshot, productDetailSnapshotView);
            productDetailSnapshotViews.add(productDetailSnapshotView);
        }
        return productDetailSnapshotViews;
    }

    @Override
    public ProductDetailSnapshotView saveEntity(ProductDetailSnapshotView productDetailSnapshotView) {
        // 保存的业务逻辑
        ProductDetailSnapshot productDetailSnapshot = new ProductDetailSnapshot();
        TorinoSrcBeanUtils.copyBean(productDetailSnapshotView, productDetailSnapshot);
        // user数据库映射传给dao进行存储
        productDetailSnapshot.setCreateTime(System.currentTimeMillis());
        productDetailSnapshot.setUpdateTime(System.currentTimeMillis());
        productDetailSnapshot.setEnabled(1);
        productDetailSnapshotDao.save(productDetailSnapshot);
        TorinoSrcBeanUtils.copyBean(productDetailSnapshot,productDetailSnapshotView);
        return productDetailSnapshotView;
    }

    @Override
    public void deleteEntity(long id) {
        ProductDetailSnapshot productDetailSnapshot = new ProductDetailSnapshot();
        productDetailSnapshot.setId(id);
        productDetailSnapshotDao.delete(productDetailSnapshot);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<ProductDetailSnapshot> productDetailSnapshots = new ArrayList<>();
        for(String entityId : entityIds){
            ProductDetailSnapshot productDetailSnapshot = new ProductDetailSnapshot();
            productDetailSnapshot.setId(Long.valueOf(entityId));
            productDetailSnapshots.add(productDetailSnapshot);
        }
        productDetailSnapshotDao.deleteInBatch(productDetailSnapshots);
    }

    @Override
    public void updateEntity(ProductDetailSnapshotView productDetailSnapshotView) {
        Specification<ProductDetailSnapshot> productDetailSnapshotSpecification = Optional.ofNullable(productDetailSnapshotView).map( s -> {
            return new Specification<ProductDetailSnapshot>() {
                @Override
                public Predicate toPredicate(Root<ProductDetailSnapshot> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("ProductDetailSnapshotView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<ProductDetailSnapshot> productDetailSnapshotOptionalBySearch = productDetailSnapshotDao.findOne(productDetailSnapshotSpecification);
        productDetailSnapshotOptionalBySearch.map(productDetailSnapshotBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(productDetailSnapshotView,productDetailSnapshotBySearch);
            productDetailSnapshotBySearch.setUpdateTime(System.currentTimeMillis());
            productDetailSnapshotDao.save(productDetailSnapshotBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + productDetailSnapshotView.getId() + "的数据记录"));
    }
}
