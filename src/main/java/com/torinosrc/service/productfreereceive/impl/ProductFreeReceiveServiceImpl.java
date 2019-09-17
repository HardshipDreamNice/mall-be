/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.productfreereceive.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.productfreereceive.ProductFreeReceiveDao;
import com.torinosrc.dao.productfreereceivedetail.ProductFreeReceiveDetailDao;
import com.torinosrc.dao.userproductfreereceive.UserProductFreeReceiveDao;
import com.torinosrc.model.entity.productfreereceive.ProductFreeReceive;
import com.torinosrc.model.entity.productfreereceivedetail.ProductFreeReceiveDetail;
import com.torinosrc.model.entity.userproductfreereceive.UserProductFreeReceive;
import com.torinosrc.model.view.productfreereceive.ProductFreeReceiveView;
import com.torinosrc.model.view.productfreereceivedetail.ProductFreeReceiveDetailView;
import com.torinosrc.service.productfreereceive.ProductFreeReceiveService;
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
* <b><code>ProductFreeReceiveImpl</code></b>
* <p/>
* ProductFreeReceive的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-27 19:54:27.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class ProductFreeReceiveServiceImpl implements ProductFreeReceiveService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ProductFreeReceiveServiceImpl.class);

    @Autowired
    private ProductFreeReceiveDao productFreeReceiveDao;

    @Autowired
    private UserProductFreeReceiveDao userProductFreeReceiveDao;

    @Autowired
    private ProductFreeReceiveDetailDao productFreeReceiveDetailDao;

    @Override
    public ProductFreeReceiveView getEntity(long id) {
        // 获取Entity
        ProductFreeReceive productFreeReceive = productFreeReceiveDao.getOne(id);
        // 复制Dao层属性到view属性
        ProductFreeReceiveView productFreeReceiveView = new ProductFreeReceiveView();
        TorinoSrcBeanUtils.copyBean(productFreeReceive, productFreeReceiveView);
        return productFreeReceiveView;
    }

    @Override
    public Page<ProductFreeReceiveView> getEntitiesByParms(ProductFreeReceiveView productFreeReceiveView, int currentPage, int pageSize) {
        Specification<ProductFreeReceive> productFreeReceiveSpecification = new Specification<ProductFreeReceive>() {
            @Override
            public Predicate toPredicate(Root<ProductFreeReceive> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root, criteriaQuery, criteriaBuilder, productFreeReceiveView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ProductFreeReceive> productFreeReceives = productFreeReceiveDao.findAll(productFreeReceiveSpecification, pageable);

        // 转换成View对象并返回
        return productFreeReceives.map(productFreeReceive -> {
            ProductFreeReceiveView productFreeReceiveView1 = new ProductFreeReceiveView();
            TorinoSrcBeanUtils.copyBean(productFreeReceive, productFreeReceiveView1);
            return productFreeReceiveView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return productFreeReceiveDao.count();
    }

    @Override
    public List<ProductFreeReceiveView> findAll() {
        List<ProductFreeReceiveView> productFreeReceiveViews = new ArrayList<>();
        List<ProductFreeReceive> productFreeReceives = productFreeReceiveDao.findAll();
        for (ProductFreeReceive productFreeReceive : productFreeReceives) {
            ProductFreeReceiveView productFreeReceiveView = new ProductFreeReceiveView();
            TorinoSrcBeanUtils.copyBean(productFreeReceive, productFreeReceiveView);
            productFreeReceiveViews.add(productFreeReceiveView);
        }
        return productFreeReceiveViews;
    }

    @Override
    public ProductFreeReceiveView saveEntity(ProductFreeReceiveView productFreeReceiveView) {
        // 保存的业务逻辑
        ProductFreeReceive productFreeReceive = new ProductFreeReceive();
        TorinoSrcBeanUtils.copyBean(productFreeReceiveView, productFreeReceive);
        // user数据库映射传给dao进行存储
        productFreeReceive.setCreateTime(System.currentTimeMillis());
        productFreeReceive.setUpdateTime(System.currentTimeMillis());
        productFreeReceive.setEnabled(1);
        productFreeReceiveDao.save(productFreeReceive);
        TorinoSrcBeanUtils.copyBean(productFreeReceive, productFreeReceiveView);
        return productFreeReceiveView;
    }

    @Override
    public void deleteEntity(long id) {
        ProductFreeReceive productFreeReceive = new ProductFreeReceive();
        productFreeReceive.setId(id);
        productFreeReceiveDao.delete(productFreeReceive);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteEntities(String ids) {
        String[] entityIds = TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<ProductFreeReceive> productFreeReceives = new ArrayList<>();
        for (String entityId : entityIds) {
            ProductFreeReceive productFreeReceive = new ProductFreeReceive();
            productFreeReceive.setId(Long.valueOf(entityId));
            productFreeReceives.add(productFreeReceive);
        }
        productFreeReceiveDao.deleteInBatch(productFreeReceives);
    }

    @Override
    public void updateEntity(ProductFreeReceiveView productFreeReceiveView) {
        Specification<ProductFreeReceive> productFreeReceiveSpecification = Optional.ofNullable(productFreeReceiveView).map(s -> {
            return new Specification<ProductFreeReceive>() {
                @Override
                public Predicate toPredicate(Root<ProductFreeReceive> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));
                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(() -> new IllegalArgumentException("ProductFreeReceiveView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<ProductFreeReceive> productFreeReceiveOptionalBySearch = productFreeReceiveDao.findOne(productFreeReceiveSpecification);
        productFreeReceiveOptionalBySearch.map(productFreeReceiveBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(productFreeReceiveView, productFreeReceiveBySearch);
            productFreeReceiveBySearch.setUpdateTime(System.currentTimeMillis());
            productFreeReceiveDao.save(productFreeReceiveBySearch);
            return "";
        }).orElseThrow(() -> new TorinoSrcServiceException("无法找到id为" + productFreeReceiveView.getId() + "的数据记录"));
    }


    //判断免费领的订单状态。
    public ProductFreeReceiveView getProductFreeReceives(long userId, long productFreeReceiveId) {
        //获取Entity
        //商品详情
        ProductFreeReceive productFreeReceive = productFreeReceiveDao.getOne(productFreeReceiveId);
        //中间表
        UserProductFreeReceive userProductFreeReceive = userProductFreeReceiveDao.findOneByShareUserIdAndProductFreeReceiveId(userId, productFreeReceiveId);

        // 复制Dao层属性到view属性
        ProductFreeReceiveView productFreeReceiveView = new ProductFreeReceiveView();

        if (!ObjectUtils.isEmpty(userProductFreeReceive)) {
            if (userProductFreeReceive.getStatus() == 1) {
                ProductFreeReceiveDetail productFreeReceiveDetail = productFreeReceiveDetailDao.getOne(userProductFreeReceive.getProductFreeReceiveDetailId());
                ProductFreeReceiveDetailView productFreeReceiveDetailView = new ProductFreeReceiveDetailView();
                TorinoSrcBeanUtils.copyBean(productFreeReceiveDetail, productFreeReceiveDetailView);
                productFreeReceiveView.setProductFreeReceiveDetail(productFreeReceiveDetailView);
                productFreeReceiveView.setStatus(userProductFreeReceive.getStatus());
            } else if (userProductFreeReceive.getStatus() == 2) {
                ProductFreeReceiveDetail productFreeReceiveDetail = productFreeReceiveDetailDao.getOne(userProductFreeReceive.getProductFreeReceiveDetailId());
                ProductFreeReceiveDetailView productFreeReceiveDetailView = new ProductFreeReceiveDetailView();
                TorinoSrcBeanUtils.copyBean(productFreeReceiveDetail, productFreeReceiveDetailView);
                productFreeReceiveView.setProductFreeReceiveDetail(productFreeReceiveDetailView);
                productFreeReceiveView.setStatus(2);
            }
        }
        TorinoSrcBeanUtils.copyBean(productFreeReceive, productFreeReceiveView);
        return productFreeReceiveView;
    }

}
