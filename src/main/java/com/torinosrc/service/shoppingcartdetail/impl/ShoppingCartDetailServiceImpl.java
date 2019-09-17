/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.shoppingcartdetail.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.productdetail.ProductDetailDao;
import com.torinosrc.dao.shoppingcart.ShoppingCartDao;
import com.torinosrc.dao.shoppingcartdetail.ShoppingCartDetailDao;
import com.torinosrc.model.entity.shoppingcart.ShoppingCart;
import com.torinosrc.model.entity.shoppingcartdetail.ShoppingCartDetail;
import com.torinosrc.model.view.shoppingcartdetail.ShoppingCartDetailView;
import com.torinosrc.service.productdetail.ProductDetailService;
import com.torinosrc.service.shoppingcartdetail.ShoppingCartDetailService;
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
import java.util.Set;

/**
* <b><code>ShopCartDetailImpl</code></b>
* <p/>
* ShopCartDetail的具体实现
* <p/>
* <b>Creation Time:</b> 2018-06-13 21:17:59.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class ShoppingCartDetailServiceImpl implements ShoppingCartDetailService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ShoppingCartDetailServiceImpl.class);

    @Autowired
    private ShoppingCartDetailDao shoppingCartDetailDao;

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    @Autowired
    private ProductDetailService productDetailService;

    @Override
    public ShoppingCartDetailView getEntity(long id) {
        // 获取Entity
        ShoppingCartDetail shoppingCartDetail = shoppingCartDetailDao.getOne(id);
        // 复制Dao层属性到view属性
        ShoppingCartDetailView shoppingCartDetailView = new ShoppingCartDetailView();
        TorinoSrcBeanUtils.copyBean(shoppingCartDetail, shoppingCartDetailView);
        return shoppingCartDetailView;
    }

    @Override
    public Page<ShoppingCartDetailView> getEntitiesByParms(ShoppingCartDetailView shoppingCartDetailView, int currentPage, int pageSize) {
        Specification<ShoppingCartDetail> shopCartDetailSpecification = new Specification<ShoppingCartDetail>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCartDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder, shoppingCartDetailView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ShoppingCartDetail> shopCartDetails = shoppingCartDetailDao.findAll(shopCartDetailSpecification, pageable);

        // 转换成View对象并返回
        return shopCartDetails.map(shoppingCartDetail ->{
            ShoppingCartDetailView shoppingCartDetailView1 = new ShoppingCartDetailView();
            TorinoSrcBeanUtils.copyBean(shoppingCartDetail, shoppingCartDetailView1);
            return shoppingCartDetailView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return shoppingCartDetailDao.count();
    }

    @Override
    public List<ShoppingCartDetailView> findAll() {
        List<ShoppingCartDetailView> shoppingCartDetailViews = new ArrayList<>();
        List<ShoppingCartDetail> shoppingCartDetails = shoppingCartDetailDao.findAll();
        for (ShoppingCartDetail shoppingCartDetail : shoppingCartDetails){
            ShoppingCartDetailView shoppingCartDetailView = new ShoppingCartDetailView();
            TorinoSrcBeanUtils.copyBean(shoppingCartDetail, shoppingCartDetailView);
            shoppingCartDetailViews.add(shoppingCartDetailView);
        }
        return shoppingCartDetailViews;
    }

    @Override
    public ShoppingCartDetailView saveEntity(ShoppingCartDetailView shoppingCartDetailView) {
        // 保存的业务逻辑
        ShoppingCartDetail shoppingCartDetail = new ShoppingCartDetail();
        TorinoSrcBeanUtils.copyBean(shoppingCartDetailView, shoppingCartDetail);
        // user数据库映射传给dao进行存储
        shoppingCartDetail.setCreateTime(System.currentTimeMillis());
        shoppingCartDetail.setUpdateTime(System.currentTimeMillis());
        shoppingCartDetail.setEnabled(1);
        shoppingCartDetailDao.save(shoppingCartDetail);
        TorinoSrcBeanUtils.copyBean(shoppingCartDetail, shoppingCartDetailView);
        return shoppingCartDetailView;
    }

    @Override
    public void deleteEntity(long id) {
        ShoppingCartDetail shoppingCartDetail = new ShoppingCartDetail();
        shoppingCartDetail.setId(id);
        shoppingCartDetailDao.delete(shoppingCartDetail);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<ShoppingCartDetail> shoppingCartDetails = new ArrayList<>();
        for(String entityId : entityIds){
            ShoppingCartDetail shoppingCartDetail = new ShoppingCartDetail();
            shoppingCartDetail.setId(Long.valueOf(entityId));
            shoppingCartDetails.add(shoppingCartDetail);
        }
        shoppingCartDetailDao.deleteInBatch(shoppingCartDetails);
    }

    @Override
    public void updateEntity(ShoppingCartDetailView shoppingCartDetailView) {
        Specification<ShoppingCartDetail> shopCartDetailSpecification = Optional.ofNullable(shoppingCartDetailView).map(s -> {
            return new Specification<ShoppingCartDetail>() {
                @Override
                public Predicate toPredicate(Root<ShoppingCartDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("ShopCartDetailView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<ShoppingCartDetail> shopCartDetailOptionalBySearch = shoppingCartDetailDao.findOne(shopCartDetailSpecification);
        shopCartDetailOptionalBySearch.map(shoppingCartDetailBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(shoppingCartDetailView, shoppingCartDetailBySearch);
            shoppingCartDetailBySearch.setUpdateTime(System.currentTimeMillis());
            shoppingCartDetailDao.save(shoppingCartDetailBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + shoppingCartDetailView.getId() + "的数据记录"));
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void addShopCart(Long shopCartId,ShoppingCartDetailView shoppingCartDetailView){
        ShoppingCart shopCart= shoppingCartDao.getOne(shopCartId);
        Set<ShoppingCartDetail> shoppingCartDetails =shopCart.getShoppingCartDetails();
        // 购物车详情记录为空的时候直接添加
        if (shoppingCartDetails.isEmpty()){
            shoppingCartDetailView.setShoppingCartId(shopCartId);
            saveEntity(shoppingCartDetailView);
        }else {
                boolean isExisted = false;
                for(ShoppingCartDetail shoppingCartDetail : shoppingCartDetails){
                    if(shoppingCartDetail.getProductDetailId().longValue()==shoppingCartDetailView.getProductDetailId().longValue()&& shoppingCartDetail.getShopId().longValue()== shoppingCartDetailView.getShopId().longValue()){
                        Long id= shoppingCartDetail.getId();
                        //已存在的更新数量
                        Integer count= shoppingCartDetail.getCount()+ shoppingCartDetailView.getCount();
                        shoppingCartDetailView.setId(id);
                        shoppingCartDetailView.setCount(count);
                        updateEntity(shoppingCartDetailView);
                        isExisted = true;
                        break;
                    }
                }
                if(!isExisted){
                    //新增记购物车详情记录(先判断是否已增加过的)
                    shoppingCartDetailView.setShoppingCartId(shopCartId);
                    saveEntity(shoppingCartDetailView);
                }
        }
    }

    @Override
    public void removeShopCartByProductDetailId(Long shopCartId, Long productDetailId) {
        shoppingCartDetailDao.removeShopCartByProductDetailId(shopCartId,productDetailId);
    }

    @Override
    public void removeShopCartByShopProductDetailId(Long shopCartId, Long shopProductDetailId) {
        shoppingCartDetailDao.removeShopCartByProductDetailId(shopCartId,shopProductDetailId);
    }
}
