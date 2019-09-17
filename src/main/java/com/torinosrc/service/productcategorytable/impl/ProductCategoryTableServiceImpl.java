/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.productcategorytable.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.productcategorytable.ProductCategoryTableDao;
import com.torinosrc.model.entity.productcategorytable.ProductCategoryTable;
import com.torinosrc.model.view.productcategorytable.ProductCategoryTableView;
import com.torinosrc.service.productcategorytable.ProductCategoryTableService;
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
* <b><code>ProductCategoryTableImpl</code></b>
* <p/>
* ProductCategoryTable的具体实现
* <p/>
* <b>Creation Time:</b> 2018-08-30 10:51:55.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class ProductCategoryTableServiceImpl implements ProductCategoryTableService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ProductCategoryTableServiceImpl.class);

    @Autowired
    private ProductCategoryTableDao productCategoryTableDao;

    @Override
    public ProductCategoryTableView getEntity(long id) {
        // 获取Entity
        ProductCategoryTable productCategoryTable = productCategoryTableDao.getOne(id);
        // 复制Dao层属性到view属性
        ProductCategoryTableView productCategoryTableView = new ProductCategoryTableView();
        TorinoSrcBeanUtils.copyBean(productCategoryTable, productCategoryTableView);
        return productCategoryTableView;
    }

    @Override
    public Page<ProductCategoryTableView> getEntitiesByParms(ProductCategoryTableView productCategoryTableView, int currentPage, int pageSize) {
        Specification<ProductCategoryTable> productCategoryTableSpecification = new Specification<ProductCategoryTable>() {
            @Override
            public Predicate toPredicate(Root<ProductCategoryTable> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                // search
                if(!StringUtils.isEmpty(productCategoryTableView.getSearch())){
                    predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + productCategoryTableView.getSearch() + "%"));
                }
                //分类Id
                if(!ObjectUtils.isEmpty(productCategoryTableView.getCategoryId())){
                    predicates.add(criteriaBuilder.equal(root.get("categoryId").as(Long.class), productCategoryTableView.getCategoryId()));
                }
                // enabled
                if(!ObjectUtils.isEmpty(productCategoryTableView.getEnabled())){
                    predicates.add(criteriaBuilder.equal(root.get("enabled").as(Integer.class), productCategoryTableView.getEnabled()));
                }

                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();

            }
        };

        // 设置排序
        int sortType = productCategoryTableView.getSortType();
        Sort sort;
        switch (sortType) {
            case 1:
                sort = new Sort(Sort.Direction.ASC, "minPrice");
                break;
            case 2:
                sort = new Sort(Sort.Direction.DESC, "maxCommission");
                break;
            case 3:
                sort = new Sort(Sort.Direction.ASC, "minCommission");
                break;
            case 4:
                sort = new Sort(Sort.Direction.DESC, "updateTime");
                break;
            case 5:
                sort = new Sort(Sort.Direction.ASC, "updateTime");
                break;
            default:
                sort = new Sort(Sort.Direction.DESC, "maxPrice");
                break;
        }



        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize,sort);

        Page<ProductCategoryTable> productCategoryTables = productCategoryTableDao.findAll(productCategoryTableSpecification, pageable);

        // 转换成View对象并返回
        return productCategoryTables.map(productCategoryTable->{
            ProductCategoryTableView productCategoryTableView1 = new ProductCategoryTableView();
            TorinoSrcBeanUtils.copyBean(productCategoryTable, productCategoryTableView1);
            return productCategoryTableView1;
        });
    }


    @Override
    public Page<ProductCategoryTableView> getEntitiesByContion(ProductCategoryTableView productCategoryTableView, int currentPage, int pageSize) {
        Specification<ProductCategoryTable> productCategoryTableSpecification = new Specification<ProductCategoryTable>() {
            @Override
            public Predicate toPredicate(Root<ProductCategoryTable> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,productCategoryTableView.getCondition());
            }
         };

        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ProductCategoryTable> productCategoryTables = productCategoryTableDao.findAll(productCategoryTableSpecification, pageable);

        // 转换成View对象并返回
        return productCategoryTables.map(productCategoryTable->{
            ProductCategoryTableView productCategoryTableView1 = new ProductCategoryTableView();
            TorinoSrcBeanUtils.copyBean(productCategoryTable, productCategoryTableView1);
            return productCategoryTableView1;
        });
    }


    @Override
    public long getEntitiesCount() {
        return productCategoryTableDao.count();
    }

    @Override
    public List<ProductCategoryTableView> findAll() {
        List<ProductCategoryTableView> productCategoryTableViews = new ArrayList<>();
        List<ProductCategoryTable> productCategoryTables = productCategoryTableDao.findAll();
        for (ProductCategoryTable productCategoryTable : productCategoryTables){
            ProductCategoryTableView productCategoryTableView = new ProductCategoryTableView();
            TorinoSrcBeanUtils.copyBean(productCategoryTable, productCategoryTableView);
            productCategoryTableViews.add(productCategoryTableView);
        }
        return productCategoryTableViews;
    }

    @Override
    public ProductCategoryTableView saveEntity(ProductCategoryTableView productCategoryTableView) {
        // 保存的业务逻辑
        ProductCategoryTable productCategoryTable = new ProductCategoryTable();
        TorinoSrcBeanUtils.copyBean(productCategoryTableView, productCategoryTable);
        // user数据库映射传给dao进行存储
        productCategoryTable.setCreateTime(System.currentTimeMillis());
        productCategoryTable.setUpdateTime(System.currentTimeMillis());
        productCategoryTable.setEnabled(1);
        productCategoryTableDao.save(productCategoryTable);
        TorinoSrcBeanUtils.copyBean(productCategoryTable,productCategoryTableView);
        return productCategoryTableView;
    }

    @Override
    public void deleteEntity(long id) {
        ProductCategoryTable productCategoryTable = new ProductCategoryTable();
        productCategoryTable.setId(id);
        productCategoryTableDao.delete(productCategoryTable);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<ProductCategoryTable> productCategoryTables = new ArrayList<>();
        for(String entityId : entityIds){
            ProductCategoryTable productCategoryTable = new ProductCategoryTable();
            productCategoryTable.setId(Long.valueOf(entityId));
            productCategoryTables.add(productCategoryTable);
        }
        productCategoryTableDao.deleteInBatch(productCategoryTables);
    }

    @Override
    public void updateEntity(ProductCategoryTableView productCategoryTableView) {
        Specification<ProductCategoryTable> productCategoryTableSpecification = Optional.ofNullable(productCategoryTableView).map( s -> {
            return new Specification<ProductCategoryTable>() {
                @Override
                public Predicate toPredicate(Root<ProductCategoryTable> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("ProductCategoryTableView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<ProductCategoryTable> productCategoryTableOptionalBySearch = productCategoryTableDao.findOne(productCategoryTableSpecification);
        productCategoryTableOptionalBySearch.map(productCategoryTableBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(productCategoryTableView,productCategoryTableBySearch);
            productCategoryTableBySearch.setUpdateTime(System.currentTimeMillis());
            productCategoryTableDao.save(productCategoryTableBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + productCategoryTableView.getId() + "的数据记录"));
    }
}
