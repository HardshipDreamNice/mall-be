/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.shoptree.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.shoptree.ShopTreeDao;
import com.torinosrc.model.entity.shoptree.ShopTree;
import com.torinosrc.model.view.shoptree.ShopTreeView;
import com.torinosrc.service.shoptree.ShopTreeService;
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
* <b><code>ShopTreeImpl</code></b>
* <p/>
* ShopTree的具体实现
* <p/>
* <b>Creation Time:</b> 2018-07-12 16:11:55.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class ShopTreeServiceImpl implements ShopTreeService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ShopTreeServiceImpl.class);

    @Autowired
    private ShopTreeDao shopTreeDao;

    @Override
    public ShopTreeView getEntity(long id) {
        // 获取Entity
        ShopTree shopTree = shopTreeDao.getOne(id);
        // 复制Dao层属性到view属性
        ShopTreeView shopTreeView = new ShopTreeView();
        TorinoSrcBeanUtils.copyBean(shopTree, shopTreeView);
        return shopTreeView;
    }

    @Override
    public Page<ShopTreeView> getEntitiesByParms(ShopTreeView shopTreeView, int currentPage, int pageSize) {
        Specification<ShopTree> shopTreeSpecification = new Specification<ShopTree>() {
            @Override
            public Predicate toPredicate(Root<ShopTree> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,shopTreeView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ShopTree> shopTrees = shopTreeDao.findAll(shopTreeSpecification, pageable);

        // 转换成View对象并返回
        return shopTrees.map(shopTree->{
            ShopTreeView shopTreeView1 = new ShopTreeView();
            TorinoSrcBeanUtils.copyBean(shopTree, shopTreeView1);
            return shopTreeView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return shopTreeDao.count();
    }

    @Override
    public List<ShopTreeView> findAll() {
        List<ShopTreeView> shopTreeViews = new ArrayList<>();
        List<ShopTree> shopTrees = shopTreeDao.findAll();
        for (ShopTree shopTree : shopTrees){
            ShopTreeView shopTreeView = new ShopTreeView();
            TorinoSrcBeanUtils.copyBean(shopTree, shopTreeView);
            shopTreeViews.add(shopTreeView);
        }
        return shopTreeViews;
    }

    @Override
    public ShopTreeView saveEntity(ShopTreeView shopTreeView) {
        // 保存的业务逻辑
        ShopTree shopTree = new ShopTree();
        TorinoSrcBeanUtils.copyBean(shopTreeView, shopTree);
        // user数据库映射传给dao进行存储
        shopTree.setCreateTime(System.currentTimeMillis());
        shopTree.setUpdateTime(System.currentTimeMillis());
        shopTree.setEnabled(1);
        shopTreeDao.save(shopTree);
        TorinoSrcBeanUtils.copyBean(shopTree,shopTreeView);
        return shopTreeView;
    }

    @Override
    public void deleteEntity(long id) {
        ShopTree shopTree = new ShopTree();
        shopTree.setId(id);
        shopTreeDao.delete(shopTree);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<ShopTree> shopTrees = new ArrayList<>();
        for(String entityId : entityIds){
            ShopTree shopTree = new ShopTree();
            shopTree.setId(Long.valueOf(entityId));
            shopTrees.add(shopTree);
        }
        shopTreeDao.deleteInBatch(shopTrees);
    }

    @Override
    public void updateEntity(ShopTreeView shopTreeView) {
        Specification<ShopTree> shopTreeSpecification = Optional.ofNullable(shopTreeView).map( s -> {
            return new Specification<ShopTree>() {
                @Override
                public Predicate toPredicate(Root<ShopTree> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("ShopTreeView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<ShopTree> shopTreeOptionalBySearch = shopTreeDao.findOne(shopTreeSpecification);
        shopTreeOptionalBySearch.map(shopTreeBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(shopTreeView,shopTreeBySearch);
            shopTreeBySearch.setUpdateTime(System.currentTimeMillis());
            shopTreeDao.save(shopTreeBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + shopTreeView.getId() + "的数据记录"));
    }
}
