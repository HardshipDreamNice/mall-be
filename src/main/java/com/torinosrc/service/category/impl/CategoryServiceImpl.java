/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.category.impl;

import com.torinosrc.commons.constants.MallConstant;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.category.CategoryDao;
import com.torinosrc.dao.product.ProductDao;
import com.torinosrc.model.entity.category.Category;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.view.category.CategoryView;
import com.torinosrc.service.category.CategoryService;
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
* <b><code>CategoryImpl</code></b>
* <p/>
* Category的具体实现
* <p/>
* <b>Creation Time:</b> 2018-08-01 14:35:12.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class CategoryServiceImpl implements CategoryService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public CategoryView getEntity(long id) {
        // 获取Entity
        Category category = categoryDao.getOne(id);
        // 复制Dao层属性到view属性
        CategoryView categoryView = new CategoryView();
        TorinoSrcBeanUtils.copyBean(category, categoryView);
        return categoryView;
    }

    @Override
    public Page<CategoryView> getEntitiesByParms(CategoryView categoryView, int currentPage, int pageSize) {
        Specification<Category> categorySpecification = new Specification<Category>() {
            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,categoryView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Category> categorys = categoryDao.findAll(categorySpecification, pageable);

        // 转换成View对象并返回
        return categorys.map(category->{
            CategoryView categoryView1 = new CategoryView();
            TorinoSrcBeanUtils.copyBean(category, categoryView1);
            return categoryView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return categoryDao.count();
    }

    @Override
    public List<CategoryView> findAll() {
        List<CategoryView> categoryViews = new ArrayList<>();
        List<Category> categorys = categoryDao.findAll();
        for (Category category : categorys){
            CategoryView categoryView = new CategoryView();
            TorinoSrcBeanUtils.copyBean(category, categoryView);
            categoryViews.add(categoryView);
        }
        return categoryViews;
    }

    @Override
    public CategoryView saveEntity(CategoryView categoryView) {
        // 保存的业务逻辑
        Category category = new Category();
        TorinoSrcBeanUtils.copyBean(categoryView, category);
        // user数据库映射传给dao进行存储
        category.setCreateTime(System.currentTimeMillis());
        category.setUpdateTime(System.currentTimeMillis());
        category.setEnabled(1);
        categoryDao.save(category);
        TorinoSrcBeanUtils.copyBean(category,categoryView);
        return categoryView;
    }

    @Override
    public void deleteEntity(long id) {
        Category category = new Category();
        category.setId(id);

        List<Product> productList = productDao.findAllByCategoryId(id);
        if(!org.springframework.util.CollectionUtils.isEmpty(productList)){
            throw new TorinoSrcServiceException("该分类下还有商品，不能删除");
        }
        categoryDao.delete(category);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<Category> categorys = new ArrayList<>();
        for(String entityId : entityIds){
            Category category = new Category();
            category.setId(Long.valueOf(entityId));
            categorys.add(category);
        }
        categoryDao.deleteInBatch(categorys);
    }

    @Override
    public void updateEntity(CategoryView categoryView) {
        Specification<Category> categorySpecification = Optional.ofNullable(categoryView).map( s -> {
            return new Specification<Category>() {
                @Override
                public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("CategoryView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<Category> categoryOptionalBySearch = categoryDao.findOne(categorySpecification);
        categoryOptionalBySearch.map(categoryBySearch -> {

            Integer enabled = categoryView.getEnabled();

            // 如果原分类状态为 启用，且更新为 禁用
            if(categoryBySearch.getEnabled() == MallConstant.CATEGORY_ENABLED_STATUS_SUCCESS && enabled == MallConstant.CATEGORY_ENABLED_STATUS_FALL){
                List<Product> productList = productDao.findAllByCategoryId(categoryBySearch.getId());
                if(!org.springframework.util.CollectionUtils.isEmpty(productList)){
                    throw new TorinoSrcServiceException("该分类下还有商品，不能禁用");
                }
            }

            TorinoSrcBeanUtils.copyBeanExcludeNull(categoryView,categoryBySearch);
            categoryBySearch.setUpdateTime(System.currentTimeMillis());
            categoryDao.save(categoryBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + categoryView.getId() + "的数据记录"));
    }
}
