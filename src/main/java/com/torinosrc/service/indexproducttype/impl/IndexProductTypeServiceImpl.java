/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.indexproducttype.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.indexproducttype.IndexProductTypeDao;
import com.torinosrc.dao.indexproducttypeproduct.IndexProductTypeProductDao;
import com.torinosrc.model.entity.indexproducttype.IndexProductType;
import com.torinosrc.model.view.indexproducttype.IndexProductTypeView;
import com.torinosrc.service.indexproducttype.IndexProductTypeService;
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
* <b><code>IndexProductTypeImpl</code></b>
* <p/>
* IndexProductType的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-27 11:03:01.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class IndexProductTypeServiceImpl implements IndexProductTypeService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(IndexProductTypeServiceImpl.class);

    @Autowired
    private IndexProductTypeDao indexProductTypeDao;

    @Autowired
    private IndexProductTypeProductDao indexProductTypeProductDao;

    @Override
    public IndexProductTypeView getEntity(long id) {
        // 获取Entity
        IndexProductType indexProductType = indexProductTypeDao.getOne(id);
        // 复制Dao层属性到view属性
        IndexProductTypeView indexProductTypeView = new IndexProductTypeView();
        TorinoSrcBeanUtils.copyBean(indexProductType, indexProductTypeView);
        return indexProductTypeView;
    }

    @Override
    public Page<IndexProductTypeView> getEntitiesByParms(IndexProductTypeView indexProductTypeView, int currentPage, int pageSize) {
        Specification<IndexProductType> indexProductTypeSpecification = new Specification<IndexProductType>() {
            @Override
            public Predicate toPredicate(Root<IndexProductType> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,indexProductTypeView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<IndexProductType> indexProductTypes = indexProductTypeDao.findAll(indexProductTypeSpecification, pageable);

        // 转换成View对象并返回
        return indexProductTypes.map(indexProductType->{
            IndexProductTypeView indexProductTypeView1 = new IndexProductTypeView();
            TorinoSrcBeanUtils.copyBean(indexProductType, indexProductTypeView1);
            return indexProductTypeView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return indexProductTypeDao.count();
    }

    @Override
    public List<IndexProductTypeView> findAll() {
        List<IndexProductTypeView> indexProductTypeViews = new ArrayList<>();
        List<IndexProductType> indexProductTypes = indexProductTypeDao.findAll();
        for (IndexProductType indexProductType : indexProductTypes){
            IndexProductTypeView indexProductTypeView = new IndexProductTypeView();
            TorinoSrcBeanUtils.copyBean(indexProductType, indexProductTypeView);
            indexProductTypeViews.add(indexProductTypeView);
        }
        return indexProductTypeViews;
    }

    @Override
    public IndexProductTypeView saveEntity(IndexProductTypeView indexProductTypeView) {
        // 保存的业务逻辑
        IndexProductType indexProductType = new IndexProductType();
        TorinoSrcBeanUtils.copyBean(indexProductTypeView, indexProductType);
        // user数据库映射传给dao进行存储
        indexProductType.setCreateTime(System.currentTimeMillis());
        indexProductType.setUpdateTime(System.currentTimeMillis());
        indexProductType.setEnabled(1);
        indexProductTypeDao.save(indexProductType);
        TorinoSrcBeanUtils.copyBean(indexProductType,indexProductTypeView);
        return indexProductTypeView;
    }

    @Override
    public void deleteEntity(long id) {
        IndexProductType indexProductType = new IndexProductType();
        indexProductType.setId(id);
        indexProductTypeDao.delete(indexProductType);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<IndexProductType> indexProductTypes = new ArrayList<>();
        for(String entityId : entityIds){
            IndexProductType indexProductType = new IndexProductType();
            indexProductType.setId(Long.valueOf(entityId));
            indexProductTypes.add(indexProductType);
        }
        indexProductTypeDao.deleteInBatch(indexProductTypes);
    }

    @Override
    public void updateEntity(IndexProductTypeView indexProductTypeView) {
        Specification<IndexProductType> indexProductTypeSpecification = Optional.ofNullable(indexProductTypeView).map( s -> {
            return new Specification<IndexProductType>() {
                @Override
                public Predicate toPredicate(Root<IndexProductType> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("IndexProductTypeView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<IndexProductType> indexProductTypeOptionalBySearch = indexProductTypeDao.findOne(indexProductTypeSpecification);
        indexProductTypeOptionalBySearch.map(indexProductTypeBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(indexProductTypeView,indexProductTypeBySearch);
            indexProductTypeBySearch.setUpdateTime(System.currentTimeMillis());
            indexProductTypeDao.save(indexProductTypeBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + indexProductTypeView.getId() + "的数据记录"));
    }

    @Override
    public Page<IndexProductTypeView> getEntitiesByParmsHaveProductCount(IndexProductTypeView indexProductTypeView, int currentPage, int pageSize) {
        Specification<IndexProductType> indexProductTypeSpecification = new Specification<IndexProductType>() {
            @Override
            public Predicate toPredicate(Root<IndexProductType> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,indexProductTypeView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<IndexProductType> indexProductTypes = indexProductTypeDao.findAll(indexProductTypeSpecification, pageable);

        // 转换成View对象并返回
        return indexProductTypes.map(indexProductType->{
            IndexProductTypeView indexProductTypeView1 = new IndexProductTypeView();
            TorinoSrcBeanUtils.copyBean(indexProductType, indexProductTypeView1);

            Long indexProductTypeId=indexProductType.getId();
            Integer productCount= indexProductTypeProductDao.countByIndexProductTypeId(indexProductTypeId);
            indexProductTypeView1.setProductCount(productCount);
            return indexProductTypeView1;
        });
    }
}
