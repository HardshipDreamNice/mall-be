/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.productdetailprice.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.productdetailprice.ProductDetailPriceDao;
import com.torinosrc.model.entity.productdetailprice.ProductDetailPrice;
import com.torinosrc.model.view.productdetailprice.ProductDetailPriceView;
import com.torinosrc.service.productdetailprice.ProductDetailPriceService;
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
* <b><code>ProductDetailPriceImpl</code></b>
* <p/>
* ProductDetailPrice的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-19 10:01:28.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class ProductDetailPriceServiceImpl implements ProductDetailPriceService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ProductDetailPriceServiceImpl.class);

    @Autowired
    private ProductDetailPriceDao productDetailPriceDao;

    @Override
    public ProductDetailPriceView getEntity(long id) {
        // 获取Entity
        ProductDetailPrice productDetailPrice = productDetailPriceDao.getOne(id);
        // 复制Dao层属性到view属性
        ProductDetailPriceView productDetailPriceView = new ProductDetailPriceView();
        TorinoSrcBeanUtils.copyBean(productDetailPrice, productDetailPriceView);
        return productDetailPriceView;
    }

    @Override
    public Page<ProductDetailPriceView> getEntitiesByParms(ProductDetailPriceView productDetailPriceView, int currentPage, int pageSize) {
        Specification<ProductDetailPrice> productDetailPriceSpecification = new Specification<ProductDetailPrice>() {
            @Override
            public Predicate toPredicate(Root<ProductDetailPrice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,productDetailPriceView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ProductDetailPrice> productDetailPrices = productDetailPriceDao.findAll(productDetailPriceSpecification, pageable);

        // 转换成View对象并返回
        return productDetailPrices.map(productDetailPrice->{
            ProductDetailPriceView productDetailPriceView1 = new ProductDetailPriceView();
            TorinoSrcBeanUtils.copyBean(productDetailPrice, productDetailPriceView1);
            return productDetailPriceView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return productDetailPriceDao.count();
    }

    @Override
    public List<ProductDetailPriceView> findAll() {
        List<ProductDetailPriceView> productDetailPriceViews = new ArrayList<>();
        List<ProductDetailPrice> productDetailPrices = productDetailPriceDao.findAll();
        for (ProductDetailPrice productDetailPrice : productDetailPrices){
            ProductDetailPriceView productDetailPriceView = new ProductDetailPriceView();
            TorinoSrcBeanUtils.copyBean(productDetailPrice, productDetailPriceView);
            productDetailPriceViews.add(productDetailPriceView);
        }
        return productDetailPriceViews;
    }

    @Override
    public ProductDetailPriceView saveEntity(ProductDetailPriceView productDetailPriceView) {
        // 保存的业务逻辑
        ProductDetailPrice productDetailPrice = new ProductDetailPrice();
        TorinoSrcBeanUtils.copyBean(productDetailPriceView, productDetailPrice);
        // user数据库映射传给dao进行存储
        productDetailPrice.setCreateTime(System.currentTimeMillis());
        productDetailPrice.setUpdateTime(System.currentTimeMillis());
        productDetailPrice.setEnabled(1);
        productDetailPriceDao.save(productDetailPrice);
        TorinoSrcBeanUtils.copyBean(productDetailPrice,productDetailPriceView);
        return productDetailPriceView;
    }

    @Override
    public void deleteEntity(long id) {
        ProductDetailPrice productDetailPrice = new ProductDetailPrice();
        productDetailPrice.setId(id);
        productDetailPriceDao.delete(productDetailPrice);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<ProductDetailPrice> productDetailPrices = new ArrayList<>();
        for(String entityId : entityIds){
            ProductDetailPrice productDetailPrice = new ProductDetailPrice();
            productDetailPrice.setId(Long.valueOf(entityId));
            productDetailPrices.add(productDetailPrice);
        }
        productDetailPriceDao.deleteInBatch(productDetailPrices);
    }

    @Override
    public void updateEntity(ProductDetailPriceView productDetailPriceView) {
        Specification<ProductDetailPrice> productDetailPriceSpecification = Optional.ofNullable(productDetailPriceView).map( s -> {
            return new Specification<ProductDetailPrice>() {
                @Override
                public Predicate toPredicate(Root<ProductDetailPrice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("ProductDetailPriceView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<ProductDetailPrice> productDetailPriceOptionalBySearch = productDetailPriceDao.findOne(productDetailPriceSpecification);
        productDetailPriceOptionalBySearch.map(productDetailPriceBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(productDetailPriceView,productDetailPriceBySearch);
            productDetailPriceBySearch.setUpdateTime(System.currentTimeMillis());
            productDetailPriceDao.save(productDetailPriceBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + productDetailPriceView.getId() + "的数据记录"));
    }
}
