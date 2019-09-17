/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.indexproducttypeproduct.impl;

import com.torinosrc.commons.constants.MallConstant;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.indexproducttypeproduct.IndexProductTypeProductDao;
import com.torinosrc.dao.product.ProductDao;
import com.torinosrc.model.entity.indexproducttypeproduct.IndexProductTypeProduct;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.view.indexproducttypeproduct.IndexProductTypeProductView;
import com.torinosrc.model.view.product.ProductView;
import com.torinosrc.service.indexproducttypeproduct.IndexProductTypeProductService;
import com.torinosrc.service.product.ProductService;
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
 * <b><code>IndexProductTypeProductImpl</code></b>
 * <p/>
 * IndexProductTypeProduct的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-27 11:04:15.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public class IndexProductTypeProductServiceImpl implements IndexProductTypeProductService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(IndexProductTypeProductServiceImpl.class);

    @Autowired
    private IndexProductTypeProductDao indexProductTypeProductDao;

    @Autowired
    private ProductService productService;

    @Override
    public IndexProductTypeProductView getEntity(long id) {
        // 获取Entity
        IndexProductTypeProduct indexProductTypeProduct = indexProductTypeProductDao.getOne(id);
        // 复制Dao层属性到view属性
        IndexProductTypeProductView indexProductTypeProductView = new IndexProductTypeProductView();
        TorinoSrcBeanUtils.copyBean(indexProductTypeProduct, indexProductTypeProductView);
        return indexProductTypeProductView;
    }

    @Override
    public Page<IndexProductTypeProductView> getEntitiesByParms(IndexProductTypeProductView indexProductTypeProductView, int currentPage, int pageSize) {
        Specification<IndexProductTypeProduct> indexProductTypeProductSpecification = new Specification<IndexProductTypeProduct>() {
            @Override
            public Predicate toPredicate(Root<IndexProductTypeProduct> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root, criteriaQuery, criteriaBuilder, indexProductTypeProductView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<IndexProductTypeProduct> indexProductTypeProducts = indexProductTypeProductDao.findAll(indexProductTypeProductSpecification, pageable);

        // 转换成View对象并返回
        return indexProductTypeProducts.map(indexProductTypeProduct -> {
            IndexProductTypeProductView indexProductTypeProductView1 = new IndexProductTypeProductView();
            TorinoSrcBeanUtils.copyBean(indexProductTypeProduct, indexProductTypeProductView1);

            Long productId = indexProductTypeProductView1.getProductId();
            ProductView productView = productService.getEntity(productId);
            indexProductTypeProductView1.setProductView(productView);

            return indexProductTypeProductView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return indexProductTypeProductDao.count();
    }

    @Override
    public List<IndexProductTypeProductView> findAll() {
        List<IndexProductTypeProductView> indexProductTypeProductViews = new ArrayList<>();
        List<IndexProductTypeProduct> indexProductTypeProducts = indexProductTypeProductDao.findAll();
        for (IndexProductTypeProduct indexProductTypeProduct : indexProductTypeProducts) {
            IndexProductTypeProductView indexProductTypeProductView = new IndexProductTypeProductView();
            TorinoSrcBeanUtils.copyBean(indexProductTypeProduct, indexProductTypeProductView);
            indexProductTypeProductViews.add(indexProductTypeProductView);
        }
        return indexProductTypeProductViews;
    }

    @Override
    public IndexProductTypeProductView saveEntity(IndexProductTypeProductView indexProductTypeProductView) {
        // 保存的业务逻辑
        IndexProductTypeProduct indexProductTypeProduct = new IndexProductTypeProduct();
        TorinoSrcBeanUtils.copyBean(indexProductTypeProductView, indexProductTypeProduct);
        // user数据库映射传给dao进行存储
        indexProductTypeProduct.setCreateTime(System.currentTimeMillis());
        indexProductTypeProduct.setUpdateTime(System.currentTimeMillis());
        indexProductTypeProduct.setEnabled(1);

        // TODO: 保存时需要将商品的 name、title 也保存进去
        indexProductTypeProductDao.save(indexProductTypeProduct);
        TorinoSrcBeanUtils.copyBean(indexProductTypeProduct, indexProductTypeProductView);
        return indexProductTypeProductView;
    }

    @Override
    public void deleteEntity(long id) {
        IndexProductTypeProduct indexProductTypeProduct = new IndexProductTypeProduct();
        indexProductTypeProduct.setId(id);
        indexProductTypeProductDao.delete(indexProductTypeProduct);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteEntities(String ids) {
        String[] entityIds = TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<IndexProductTypeProduct> indexProductTypeProducts = new ArrayList<>();
        for (String entityId : entityIds) {
            IndexProductTypeProduct indexProductTypeProduct = new IndexProductTypeProduct();
            indexProductTypeProduct.setId(Long.valueOf(entityId));
            indexProductTypeProducts.add(indexProductTypeProduct);
        }
        indexProductTypeProductDao.deleteInBatch(indexProductTypeProducts);
    }

    @Override
    public void updateEntity(IndexProductTypeProductView indexProductTypeProductView) {
        Specification<IndexProductTypeProduct> indexProductTypeProductSpecification = Optional.ofNullable(indexProductTypeProductView).map(s -> {
            return new Specification<IndexProductTypeProduct>() {
                @Override
                public Predicate toPredicate(Root<IndexProductTypeProduct> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(() -> new IllegalArgumentException("IndexProductTypeProductView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<IndexProductTypeProduct> indexProductTypeProductOptionalBySearch = indexProductTypeProductDao.findOne(indexProductTypeProductSpecification);
        indexProductTypeProductOptionalBySearch.map(indexProductTypeProductBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(indexProductTypeProductView, indexProductTypeProductBySearch);
            indexProductTypeProductBySearch.setUpdateTime(System.currentTimeMillis());
            indexProductTypeProductDao.save(indexProductTypeProductBySearch);
            return "";
        }).orElseThrow(() -> new TorinoSrcServiceException("无法找到id为" + indexProductTypeProductView.getId() + "的数据记录"));
    }

    @Override
    public List<IndexProductTypeProduct> getEntitiesByIndexProductTypeIds(String indexProductTypeIds) {

        String[] entityIds = TorinoSrcCommonUtils.splitString(indexProductTypeIds,
                TorinoSrcCommonUtils.COMMA);

        List<IndexProductTypeProduct> indexProductTypeProductsReturn = new ArrayList<>();
        for (String indexProductTypeId : entityIds) {
            List<IndexProductTypeProduct> indexProductTypeProducts = indexProductTypeProductDao.findByIndexProductTypeIdOrderByWeightLimit(Long.parseLong(indexProductTypeId), 6);
            indexProductTypeProductsReturn.addAll(indexProductTypeProducts);
        }
        return indexProductTypeProductsReturn;
    }
}
