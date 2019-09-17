/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.shoppingcart.impl;

import com.alibaba.fastjson.JSON;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.productdetail.ProductDetailDao;
import com.torinosrc.dao.shoppingcart.ShoppingCartDao;
import com.torinosrc.dao.shopproductdetail.ShopProductDetailDao;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.entity.shoppingcart.ShoppingCart;
import com.torinosrc.model.entity.shoppingcartdetail.ShoppingCartDetail;
import com.torinosrc.model.entity.shopproductdetail.ShopProductDetail;
import com.torinosrc.model.view.productdetail.ProductDetailView;
import com.torinosrc.model.view.shoppingcart.ShoppingCartView;
import com.torinosrc.model.view.shoppingcartdetail.ShoppingCartDetailView;
import com.torinosrc.model.view.shopproductdetail.ShopProductDetailView;
import com.torinosrc.service.shoppingcart.ShoppingCartService;
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
import java.util.Set;

/**
* <b><code>ShopCartImpl</code></b>
* <p/>
* ShopCart的具体实现
* <p/>
* <b>Creation Time:</b> 2018-06-06 11:30:32.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ShoppingCartServiceImpl.class);

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    @Autowired
    private ProductDetailDao productDetailDao;

    @Autowired
    private ShopProductDetailDao shopProductDetailDao;

    @Override
    public ShoppingCartView getEntity(long id) {
        // 获取Entity
        ShoppingCart shopCart = shoppingCartDao.getOne(id);
        // 复制Dao层属性到view属性
        ShoppingCartView shopCartView = new ShoppingCartView();
        TorinoSrcBeanUtils.copyBean(shopCart, shopCartView);
        return shopCartView;
    }

    @Override
    public Page<ShoppingCartView> getEntitiesByParms(ShoppingCartView shopCartView, int currentPage, int pageSize) {
        Specification<ShoppingCart> shopCartSpecification = new Specification<ShoppingCart>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCart> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,shopCartView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ShoppingCart> shopCarts = shoppingCartDao.findAll(shopCartSpecification, pageable);

        // 转换成View对象并返回
        return shopCarts.map(shopCart->{
            ShoppingCartView shopCartView1 = new ShoppingCartView();
            TorinoSrcBeanUtils.copyBean(shopCart, shopCartView1);
            return shopCartView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return shoppingCartDao.count();
    }

    @Override
    public List<ShoppingCartView> findAll() {
        List<ShoppingCartView> shopCartViews = new ArrayList<>();
        List<ShoppingCart> shopCarts = shoppingCartDao.findAll();
        for (ShoppingCart shopCart : shopCarts){
            ShoppingCartView shopCartView = new ShoppingCartView();
            TorinoSrcBeanUtils.copyBean(shopCart, shopCartView);
            shopCartViews.add(shopCartView);
        }
        return shopCartViews;
    }

    @Override
    public ShoppingCartView saveEntity(ShoppingCartView shopCartView) {
           // 保存的业务逻辑
        ShoppingCart shopCart = new ShoppingCart();
           TorinoSrcBeanUtils.copyBean(shopCartView, shopCart);
           // user数据库映射传给dao进行存储
           shopCart.setCreateTime(System.currentTimeMillis());
           shopCart.setUpdateTime(System.currentTimeMillis());
           shopCart.setEnabled(1);
           shoppingCartDao.save(shopCart);
           TorinoSrcBeanUtils.copyBean(shopCart,shopCartView);
           return shopCartView;
    }



    @Override
    public void deleteEntity(long id) {
        ShoppingCart shopCart = new ShoppingCart();
        shopCart.setId(id);
        shoppingCartDao.delete(shopCart);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<ShoppingCart> shopCarts = new ArrayList<>();
        for(String entityId : entityIds){
            ShoppingCart shopCart = new ShoppingCart();
            shopCart.setId(Long.valueOf(entityId));
            shopCarts.add(shopCart);
        }
        shoppingCartDao.deleteInBatch(shopCarts);
    }

    @Override
    public void updateEntity(ShoppingCartView shopCartView) {
        Specification<ShoppingCart> shopCartSpecification = Optional.ofNullable(shopCartView).map( s -> {
            return new Specification<ShoppingCart>() {
                @Override
                public Predicate toPredicate(Root<ShoppingCart> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("ShopCartView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<ShoppingCart> shopCartOptionalBySearch = shoppingCartDao.findOne(shopCartSpecification);
        shopCartOptionalBySearch.map(shopCartBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(shopCartView,shopCartBySearch);
            shopCartBySearch.setUpdateTime(System.currentTimeMillis());
            shoppingCartDao.save(shopCartBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + shopCartView.getId() + "的数据记录"));
    }

    @Override
    public ShoppingCartView findByUserId(Long userId,Long shopId) {
        // 获取Entity
        ShoppingCart shopCart = shoppingCartDao.findByUserId(userId);
        // 复制Dao层属性到view属性
        ShoppingCartView shopCartView = new ShoppingCartView();
        TorinoSrcBeanUtils.copyBean(shopCart, shopCartView);

        // 购物车明细集合
        List<ShoppingCartDetailView> shoppingCartDetailViews=new ArrayList<>();
        if(!ObjectUtils.isEmpty(shopCart.getShoppingCartDetails())) {
            List<ShoppingCartDetail> ShoppingCartDetails=  new ArrayList<>(shopCart.getShoppingCartDetails());
            for (ShoppingCartDetail shoppingCartDetail : ShoppingCartDetails) {
                // 对应商店下的购物车才会显示
                if(shoppingCartDetail.getShopId().longValue()==shopId.longValue()){
                    ShoppingCartDetailView shoppingCartDetailView=new ShoppingCartDetailView();
                    TorinoSrcBeanUtils.copyBean(shoppingCartDetail,shoppingCartDetailView);
                    // 商品明细
                    ProductDetailView productDetailView = new ProductDetailView();
                    ProductDetail productDetail = productDetailDao.findById(shoppingCartDetailView.getProductDetailId()).get();
//                    ProductDetail productDetail = productDetailDao.getOne(shoppingCartDetailView.getProductDetailId());
                    TorinoSrcBeanUtils.copyBean(productDetail,productDetailView);
                    shoppingCartDetailView.setProductDetailView( productDetailView);
                    //上架商品明细
                    if(!ObjectUtils.isEmpty(shoppingCartDetailView.getShopProductDetailId())){
                        ShopProductDetail shopProductDetail=new ShopProductDetail();
                        shopProductDetail=shopProductDetailDao.findById(shoppingCartDetailView.getShopProductDetailId()).get();
                        shoppingCartDetailView.setShopProductId(shopProductDetail.getShopProduct().getId());
//                        shopProductDetail=shopProductDetailDao.getOne(shoppingCartDetailView.getShopProductDetailId());
                        shoppingCartDetailView.setShopProductDetail(shopProductDetail);
                    }
                    shoppingCartDetailViews.add(shoppingCartDetailView);
                }
            }
        }
        shopCartView.setShoppingCartDetailViews(shoppingCartDetailViews);
        return shopCartView;
    }

    @Override
    public ShoppingCartView saveEntityIfHasNot(ShoppingCartView shopCartView) {
        ShoppingCart shopCart1 = shoppingCartDao.findByUserId(shopCartView.getUserId());
        if (ObjectUtils.isEmpty(shopCart1)) {
            // 保存的业务逻辑
            ShoppingCart shopCart = new ShoppingCart();
            TorinoSrcBeanUtils.copyBean(shopCartView, shopCart);
            // user数据库映射传给dao进行存储
            shopCart.setCreateTime(System.currentTimeMillis());
            shopCart.setUpdateTime(System.currentTimeMillis());
            shopCart.setEnabled(1);
            shoppingCartDao.save(shopCart);
            TorinoSrcBeanUtils.copyBean(shopCart, shopCartView);
            return shopCartView;
        }else {
            // 复制Dao层属性到view属性
            ShoppingCartView shopCartView1 = new ShoppingCartView();
            TorinoSrcBeanUtils.copyBean(shopCart1, shopCartView1);
            return shopCartView1;
        }
    }

}
