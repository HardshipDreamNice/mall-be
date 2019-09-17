/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.product.impl;

import com.torinosrc.commons.constants.MallConstant;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.DateUtils;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.category.CategoryDao;
import com.torinosrc.dao.indexproducttype.IndexProductTypeDao;
import com.torinosrc.dao.indexproducttypeproduct.IndexProductTypeProductDao;
import com.torinosrc.dao.product.ProductDao;
import com.torinosrc.dao.productdetail.ProductDetailDao;
import com.torinosrc.dao.productdetailprice.ProductDetailPriceDao;
import com.torinosrc.dao.shoppingcartdetail.ShoppingCartDetailDao;
import com.torinosrc.dao.shopproduct.ShopProductDao;
import com.torinosrc.dao.shopproductdetail.ShopProductDetailDao;
import com.torinosrc.model.entity.category.Category;
import com.torinosrc.model.entity.indexproducttype.IndexProductType;
import com.torinosrc.model.entity.indexproducttypeproduct.IndexProductTypeProduct;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.entity.productdetailprice.ProductDetailPrice;
import com.torinosrc.model.entity.shoppingcartdetail.ShoppingCartDetail;
import com.torinosrc.model.entity.shopproduct.ShopProduct;
import com.torinosrc.model.entity.shopproductdetail.ShopProductDetail;
import com.torinosrc.model.view.common.CustomPage;
import com.torinosrc.model.view.indexproducttypeproduct.IndexProductTypeProductView;
import com.torinosrc.model.view.product.*;
import com.torinosrc.model.view.productcategorytable.ProductCategoryTableView;
import com.torinosrc.model.view.productdetail.ProductDetailView;
import com.torinosrc.model.view.shopproduct.ShopProductView;
import com.torinosrc.service.product.ProductService;
import com.torinosrc.service.productcategorytable.ProductCategoryTableService;
import com.torinosrc.service.productdetail.ProductDetailService;
import com.torinosrc.service.shopproduct.ShopProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

/**
 * <b><code>ProductImpl</code></b>
 * <p/>
 * Product的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-06 11:17:58.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public class ProductServiceImpl implements ProductService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductDetailDao productDetailDao;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private ShopProductDao shopProductDao;

    @Autowired
    private ShopProductDetailDao shopProductDetailDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ShoppingCartDetailDao shoppingCartDetailDao;

    @Autowired
    private ProductCategoryTableService productCategoryTableService;

    @Autowired
    private ProductDetailPriceDao productDetailPriceDao;

    @Autowired
    private IndexProductTypeProductDao indexProductTypeProductDao;

    @Autowired
    private ShopProductService shopProductService;

    @Autowired
    private IndexProductTypeDao indexProductTypeDao;

    private static Long DEFAULT_SHOPID = 1L;

    @Override
    public ProductView getProductProfit(long id, long shopId) {
        //获取Entity
        Product product = productDao.getOne(id);
        ProductView productView = new ProductView();
        TorinoSrcBeanUtils.copyBean(product, productView);
        List<ProductDetailView> productDetailViewList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(productDetailDao.findByProductId(id))) {
            List<ProductDetail> productDetails = productDetailDao.findByProductId(id);
            for (ProductDetail productDetail : productDetails) {
                ProductDetailView productDetailView = new ProductDetailView();
                TorinoSrcBeanUtils.copyBean(productDetail, productDetailView);
                // 利润
                Long profit = productDetailService.calculateProductProfit(productDetailView, shopId);
                productDetailView.setProfit(profit);
                productDetailViewList.add(productDetailView);
            }
        }
        productView.setProductDetailViews(productDetailViewList);
        return productView;
    }

    @Override
    public ProductView getEntity(long id) {
//        // 获取Entity
//        Product product = productDao.getOne(id);
//        // 复制Dao层属性到view属性
//        ProductView productView = new ProductView();
//        TorinoSrcBeanUtils.copyBean(product, productView);
//        return productView;
        // 获取Entity
        Product product = productDao.getOne(id);
        ProductView productView = new ProductView();
        TorinoSrcBeanUtils.copyBean(product, productView);
        productView=this.getIndexProductTypeView(productView);
        List<ProductDetail> productDetails = productDetailDao.findByProductId(id);

        // 设置商品的价格区间及拼团状态等信息
        productView = setProductPriceRangeAndSpellStatus(productView, productDetails);

        productView.setProductDetails(productDetails);
        return productView;
    }

    /**
     * 普通商品：设置商品的价格区间、最高市场价
     * 拼团商品：设置商品的原始价格最低价、拼团最低价，最高市场价，拼团状态及商品详情中的拼团价
     * @param productView
     * @param productDetails
     * @return
     */
    private ProductView setProductPriceRangeAndSpellStatus(ProductView productView ,List<ProductDetail> productDetails) {
        long productId = productView.getId();
        ProductDetailPrice productDetailPrice;

        // 如果该商品是拼团商品
        if (productView.getType().intValue() == MallConstant.PRODUCT_TYPE_TEAM.intValue()) {
            if (this.checkProductExpiredTime(productId)) {
                productView.setSpellStatus(1);
            } else {
                productView.setSpellStatus(0);
            }

            // 拼团商品的原始价格的最低和拼团的最低价
            Map map = productDao.findGroupProductsPriceRange(productId);
            productView.setPriceIntervaDown(Integer.valueOf(map.get("min_price").toString()));
            productView.setGroupPriceIntervaDown(Integer.valueOf(map.get("min_team_price").toString()));

            for (ProductDetail productDetail : productDetails) {
                productDetailPrice = productDetailPriceDao.findByProductDetailId(productDetail.getId());
                productDetail.setGroupPrice(productDetailPrice.getTeamPrice());
            }
            productView.setProductDetails(productDetails);
        } else if(productView.getType().intValue() == MallConstant.PRODUCT_TYPE_GENERAL.intValue()) {
            // 普通商品，设置最高价和最低价
            Map productPriceRangeMap = productDao.findProductPriceRange(productId);
            Integer minProductPrice = Integer.valueOf(productPriceRangeMap.get("min_price").toString());
            Integer maxProductPrice = Integer.valueOf(productPriceRangeMap.get("max_price").toString());

            productView.setPriceIntervalUp(maxProductPrice);
            productView.setPriceIntervaDown(minProductPrice);
        }

        // 最高市场价的商品详情
        ProductDetail uppestPriceProductDetail = productDetailDao.findFirstByProductIdOrderByUppestPriceDesc(productId);
        productView.setMarketPriceIntervalUp(uppestPriceProductDetail.getUppestPrice());
        return productView;
    }

    /**
     * 商品过期时间检测
     * @param productId
     * @return
     */
    private Boolean checkProductExpiredTime(Long productId) {
        Boolean isTimeEnd = false;
        Product product = productDao.findById(productId).get();
        Long currentTime = System.currentTimeMillis();
        Long expiredTime = DateUtils.StrToDate(product.getExpiredTime(), "yyyy-MM-dd HH:mm:ss").getTime();
        if (currentTime > expiredTime) {
            isTimeEnd = true;
        }
        return isTimeEnd;
    }

    public Page<ProductView> getEntitiesByParmsByType(ProductView productView, int currentPage, int pageSize) {
        Specification<Product> productSpecification = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (productView.getType() == 99) {
                    // 所有商品类别
                    predicates.add(criteriaBuilder.le(root.get("type").as(Integer.class), productView.getType()));
                }
                if(productView.getType()==0){
                    // 普通商品类别
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("type"));
                    in.value(0);
                    in.value(2);
                    predicates.add(criteriaBuilder.and(in));
                }

                if(productView.getType()==1){
                    // 拼团商品类别
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("type"));
                    in.value(1);
                    in.value(2);
                    predicates.add(criteriaBuilder.and(in));
                }

                if(!StringUtils.isEmpty(productView.getName())){
                    predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + productView.getName() + "%"));
                }

                if (!ObjectUtils.isEmpty(productView.getEnabled())) {
                    predicates.add(criteriaBuilder.equal(root.get("enabled").as(Integer.class), productView.getEnabled()));
                }

                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        int sortType=productView.getSortType();

        Sort sort;

        switch (sortType) {
            case 0:
                sort = new Sort(Sort.Direction.DESC, "updateTime");
                break;
            case 1:
                sort = new Sort(Sort.Direction.DESC, "weight");
                break;
            default:
                sort = new Sort(Sort.Direction.DESC, "weight");
                break;
        }


        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize,sort);

        Page<Product> products = productDao.findAll(productSpecification, pageable);


        // 转换成View对象并返回
        return products.map(product -> {
            ProductView productView1 = new ProductView();
            TorinoSrcBeanUtils.copyBean(product, productView1);
            productView1.setProductDetails(productDetailDao.findByProductId(product.getId()));
            return productView1;
        });
    }

    @Override
    public Page<ProductView> getEntitiesByParms(ProductView productView, int currentPage, int pageSize) {
        Specification<Product> productSpecification = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root, criteriaQuery, criteriaBuilder, productView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Product> products = productDao.findAll(productSpecification, pageable);


        // 转换成View对象并返回
        return products.map(product -> {
            ProductView productView1 = new ProductView();
            TorinoSrcBeanUtils.copyBean(product, productView1);

            List<ProductDetail> productDetails = productDetailDao.findByProductId(product.getId());
            productView1.setProductDetails(productDetailDao.findByProductId(product.getId()));
            // 设置商品的价格区间及拼团状态等信息
            productView1 = this.setProductPriceRangeAndSpellStatus(productView1, productDetails);
            return productView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return productDao.count();
    }

    @Override
    public List<ProductView> findAll() {
        List<ProductView> productViews = new ArrayList<>();
        List<Product> products = productDao.findAll();
        for (Product product : products) {
            ProductView productView = new ProductView();
            TorinoSrcBeanUtils.copyBean(product, productView);
            productViews.add(productView);
        }
        return productViews;
    }

    @Override
    public ProductView saveEntity(ProductView productView) {
        // 保存的业务逻辑
        Product product = new Product();
        TorinoSrcBeanUtils.copyBean(productView, product);
        // user数据库映射传给dao进行存储
        product.setCreateTime(System.currentTimeMillis());
        product.setUpdateTime(System.currentTimeMillis());
        product.setEnabled(1);
        productDao.save(product);
        TorinoSrcBeanUtils.copyBean(product, productView);
        return productView;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteEntity(long id) {
        Product product = new Product();
//        productDao.deleteById(id);
        this.deleteAssociated(id);
        product.setId(id);
        productDao.delete(product);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteEntities(String ids) {
        String[] entityIds = TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
//        List<Product> products = new ArrayList<>();
        for (String entityId : entityIds) {
//            Product product = new Product();
            deleteEntity(Long.valueOf(entityId));
//            products.add(product);
        }
//        productDao.deleteInBatch(products);
    }

    @Transactional(rollbackOn = {Exception.class})
    public void deleteAssociated(Long id) {
        //删除商品明细
        List<ProductDetail> productDetails = productDao.getOne(id).getProductDetailList();
        //删除上架商品明细
        List<ShopProductDetail> shopProductDetails;
        //删除购物车明细
        List<ShoppingCartDetail> shoppingCartDetails;
        for (ProductDetail productDetail : productDetails) {
            shopProductDetails = shopProductDetailDao.findByProductDetailId(productDetail.getId());
            shopProductDetailDao.deleteInBatch(shopProductDetails);
            shoppingCartDetails = shoppingCartDetailDao.findByProductDetailId(productDetail.getId());
            shoppingCartDetailDao.deleteInBatch(shoppingCartDetails);
        }
        productDetailDao.deleteInBatch(productDetails);
        //删除上架商品
        List<ShopProduct> shopProducts = shopProductDao.findByProductId(id);
        shopProductDao.deleteInBatch(shopProducts);
        // 删除商品与商品分组的关联表
        indexProductTypeProductDao.deleteByProductId(id);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void updateEntity(ProductView productView) {
        Specification<Product> productSpecification = Optional.ofNullable(productView).map(s -> {
            return new Specification<Product>() {
                @Override
                public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(() -> new IllegalArgumentException("ProductView is null"));

        // 获取原有的属性，把不变的属性覆盖
        Optional<Product> productOptionalBySearch = productDao.findOne(productSpecification);
        productOptionalBySearch.map(productBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(productView, productBySearch);
            productBySearch.setUpdateTime(System.currentTimeMillis());
            productDao.save(productBySearch);
            return "";
        }).orElseThrow(() -> new TorinoSrcServiceException("无法找到id为" + productView.getId() + "的数据记录"));
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void updateEntityByProductView(ProductView productView) {
        Specification<Product> productSpecification = Optional.ofNullable(productView).map(s -> {
            return new Specification<Product>() {
                @Override
                public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(() -> new IllegalArgumentException("ProductView is null"));

        // 保存分组商品与商品关联表
        this.saveIndexProductType(productView);

        // 获取原有的属性，把不变的属性覆盖
        Optional<Product> productOptionalBySearch = productDao.findOne(productSpecification);
        productOptionalBySearch.map(productBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(productView, productBySearch);
            productBySearch.setUpdateTime(System.currentTimeMillis());
            productBySearch.setCategoryId(productBySearch.getCategoryId());
            productDao.save(productBySearch);

            Long productId = productBySearch.getId();

            if (!ObjectUtils.isEmpty(productView.getProductDetails())) {
                List<ProductDetail> productDetails = productView.getProductDetails();
                ProductDetailView productDetailView = new ProductDetailView();
                for (ProductDetail productDetail : productDetails) {
                    TorinoSrcBeanUtils.copyBean(productDetail, productDetailView);
                    if (!ObjectUtils.isEmpty(productDetail.getId())) {
                        // 判断商品明细价格（前端参数）与 商品明细价格（数据库获取），是否相同，不相同进行下列操作或商品类型为拼团类型进行if操作
                        if(productDetailView.getPrice().longValue()!=productDetailDao.getOne(productDetailView.getId()).getPrice().longValue()||productDetailView.getUppestPrice().longValue()!=productDetailDao.getOne(productDetailView.getId()).getUppestPrice().longValue()){
                            this.deleteShopProduct(productId);
                        }else {
                            // nothing to do
                        }
                        // update
                        productDetailService.updateEntity(productDetailView);
                        this.saveProductDetailPrice(productDetail);
                    } else {
                        // save
                        productDetailView.setProductId(productId);
                        ProductDetail productDetail1 = productDetailService.saveEntity(productDetailView);
                        this.saveProductDetailPrice(productDetail1);
                    }
                }
            }
            // 删除
            if (!ObjectUtils.isEmpty(productBySearch.getCondition())) {
                productDetailService.deleteEntities(productBySearch.getCondition());
            }

            return "";
        }).orElseThrow(() -> new TorinoSrcServiceException("无法找到id为" + productView.getId() + "的数据记录"));
    }

    @Override
    public CustomPage<ProductView> findProductListForWx(ProductView productView, int currentPage, int pageSize) {

        List<ProductView> productViews = new ArrayList<>();
        CustomPage<ProductView> customPage = new CustomPage<ProductView>();
        Long shopId = productView.getShopId();

        Integer type = productView.getType();

        ProductView productView2;
        if (type == MallConstant.PRODUCT_TYPE_GENERAL) {
            // 普通商品
            Page<ProductView> productPage = this.getEntitiesByParmsByType(productView, currentPage, pageSize);

            for (ProductView productView1 : productPage.getContent()) {
                productView2 = new ProductView();
                //判断商品是否已经上架
                productView2 = this.judgeProductSales(productView1, shopId);
                //获取最高价和最低价
                Map map = shopProductDao.findProductsPriceRange(shopId,productView2.getId());
                productView2.setPriceIntervalUp(Integer.valueOf(map.get("max_price").toString()));
                productView2.setPriceIntervaDown(Integer.valueOf(map.get("min_price").toString()));
                productViews.add(productView2);
            }

            customPage.setContent(productViews);
            customPage.setTotalElements(productPage.getTotalElements());
            return customPage;
        } else if (type == MallConstant.PRODUCT_TYPE_TEAM) {
            // 拼团商品
            Page<ProductView> productPage = this.getEntitiesByParmsByType(productView, currentPage, pageSize);

            for (ProductView productView1 : productPage.getContent()) {

                Map map = productDao.findGroupProductsPriceRange(productView1.getId());
                productView1.setPriceIntervaDown(Integer.valueOf(map.get("min_price").toString()));
                productView1.setGroupPriceIntervaDown(Integer.valueOf(map.get("min_team_price").toString()));
                productViews.add(productView1);
            }

            customPage.setContent(productViews);
            customPage.setTotalElements(productPage.getTotalElements());
            return customPage;
        } else {
            Page<ProductView> productPage = this.getEntitiesByParmsByType(productView, currentPage, pageSize);
            customPage.setContent(productPage.getContent());
            customPage.setTotalElements(productPage.getTotalElements());
            return customPage;
        }
    }


    @Override
    public ProductPageView getProductsByCategoryId(ProductView productView, int pageNumber, int pageSize) {
        Category category = productView.getCategory();
        ProductPageView productPageView = new ProductPageView();
        //如果分类为空，返回空
        if (ObjectUtils.isEmpty(category) || ObjectUtils.isEmpty(category.getId())) {
            return productPageView;
        }

        Long categoryId = category.getId();
        List<Product> products = new ArrayList<>();
        ProductCategoryTableView productCategoryTableView = new ProductCategoryTableView();
        productCategoryTableView.setCategoryId(categoryId);
        productCategoryTableView.setSearch(productView.getSearchName());
        productCategoryTableView.setSortType(productView.getSortType());
        productCategoryTableView.setEnabled(1);
        Page<ProductCategoryTableView> productCategoryTableViews = productCategoryTableService.getEntitiesByParms(productCategoryTableView, pageNumber, pageSize);
        Product product;
        ProductView productView1;
        List<ProductView> productViewList = new ArrayList<>();
        for (ProductCategoryTableView productCategoryTableView1 : productCategoryTableViews) {
            product = productDao.getOne(productCategoryTableView1.getId());
            products.add(product);
            productView1 = new ProductView();
            TorinoSrcBeanUtils.copyBean(product, productView1);
            // 价格区间
            productView1.setPriceIntervalUp(productCategoryTableView1.getMaxPrice().intValue());
            productView1.setPriceIntervaDown(productCategoryTableView1.getMinPrice().intValue());
            // 是否上架
            productView1 = this.judgeProductSales(productView1, productView.getShopId());
            // 利润区间
            productView1 = this.getProductUpPriceAndDownProce(productView1, productView.getShopId());
            productViewList.add(productView1);
        }

        productPageView.setProductViewList(productViewList);
        productPageView.setTotalNum(productCategoryTableViews.getTotalElements());

        return productPageView;
    }

    @Override
    public List<ProductView> dealProducts(List<Product> products, Long shopId) {
        ProductView productView;
        ProductView productView1;
        ProductView productView2;
        List<ProductView> productViewList = new ArrayList<>();
        for (Product product : products) {
            productView = new ProductView();
            TorinoSrcBeanUtils.copyBean(product, productView);
            productView.setProductDetails(productDetailDao.findByProductId(product.getId()));

            //判断商品是否已经上架，如果已经上架，将shopProductId添加进来
            productView1 = this.judgeProductSales(productView, shopId);
            //计算商品的最高价和最低价 和最高利润、最低利润
            productView2 = this.getProductPriceAndProfit(productView1, shopId);

            productViewList.add(productView2);
        }

        return productViewList;
    }

    /**
     * 获取未上架商品的最高价和最低价 和最高佣金、最低佣金
     *
     * @param productView
     * @return
     */
    private ProductView getProductUpPriceAndDownProce(ProductView productView, Long shopId) {
        Long shopProductId = productView.getShopProductId();
        boolean isSale = false;
        if (ObjectUtils.isEmpty(shopProductId) || shopProductId.longValue() == 0L) {
            // 未上架
        } else {
            // 上架
            isSale = true;
        }

        List<ProductDetail> productDetails = productDetailDao.findByProductId(productView.getId());
        ProductDetailView productDetailView = new ProductDetailView();

//        int upperPrice = 0;
//        int downPrice = 0;
        long upperProfit = 0;
        long downProfit = 0;

        long tempPrice = 0;

        if (CollectionUtils.isEmpty(productDetails)) {
//            productView.setPriceIntervalUp(upperPrice);
//            productView.setPriceIntervaDown(downPrice);
            productView.setProfitIntervalDown(downProfit);
            productView.setProfitIntervalUp(upperProfit);
            return productView;
        }

        if (productDetails.size() == 1) {
            for (ProductDetail productDetail : productDetails) {
                if (ObjectUtils.isEmpty(productDetail.getPrice())) {
                    productDetail.setPrice(0L);
                }
//                upperPrice = Integer.parseInt(productDetail.getPrice().toString());
//                downPrice  = Integer.parseInt(productDetail.getPrice().toString());
                TorinoSrcBeanUtils.copyBean(productDetail, productDetailView);
                if (isSale && !ObjectUtils.isEmpty(shopProductDetailDao.findByShopProductIdAndProductDetailId(shopProductId, productDetailView.getId()))) {
                    tempPrice = shopProductDetailDao.findByShopProductIdAndProductDetailId(shopProductId, productDetailView.getId()).getProductPrice();
                    productDetailView.setPrice(tempPrice);
                }
                upperProfit = productDetailService.calculateProductProfit(productDetailView, shopId);
                downProfit = upperProfit;
            }

//            productView.setPriceIntervalUp(upperPrice);
//            productView.setPriceIntervaDown(downPrice);
            productView.setProfitIntervalUp(upperProfit);
            productView.setProfitIntervalDown(downProfit);
            return productView;
        }

        int i = 0;
        for (ProductDetail productDetail : productDetails) {
            TorinoSrcBeanUtils.copyBean(productDetail, productDetailView);
            if (isSale && !ObjectUtils.isEmpty(shopProductDetailDao.findByShopProductIdAndProductDetailId(shopProductId, productDetailView.getId()))) {
                tempPrice = shopProductDetailDao.findByShopProductIdAndProductDetailId(shopProductId, productDetailView.getId()).getProductPrice();
                productDetailView.setPrice(tempPrice);
            }
            long profit = productDetailService.calculateProductProfit(productDetailView, shopId);
            if (ObjectUtils.isEmpty(productDetail.getPrice())) {
                productDetail.setPrice(0L);
            }
            if (ObjectUtils.isEmpty(productDetail.getCommission())) {
                productDetail.setCommission(0L);
            }
            if (i == 0) {
                System.out.println("productDetail.getProduct().toString(): " + productDetail.getPrice().toString());
//                upperPrice = Integer.parseInt(productDetail.getPrice().toString());
//                downPrice  = Integer.parseInt(productDetail.getPrice().toString());
                upperProfit = profit;
                downProfit = profit;
                i++;
                continue;
            }

//            if(Integer.parseInt(productDetail.getPrice().toString()) > upperPrice){
//                upperPrice = Integer.parseInt(productDetail.getPrice().toString());
//            }
//            if(Integer.parseInt(productDetail.getPrice().toString()) < downPrice){
//                downPrice =  Integer.parseInt(productDetail.getPrice().toString());
//            }
            if (profit > upperProfit) {
                upperProfit = profit;
            }
            if (profit < downProfit) {
                downProfit = profit;
            }
        }

//        productView.setPriceIntervalUp(upperPrice);
//        productView.setPriceIntervaDown(downPrice);
        productView.setProfitIntervalDown(downProfit);
        productView.setProfitIntervalUp(upperProfit);
        return productView;
    }

    /**
     * 获取商品的最高价、利润和最低价、利润
     *
     * @param productView
     * @return
     */
    private ProductView getProductPriceAndProfit(ProductView productView, Long shopId) {
        Long shopProductId = productView.getShopProductId();
        ProductView productView1;
        if (ObjectUtils.isEmpty(shopProductId) || shopProductId.longValue() == 0L) {
            //商品还没上架
            productView1 = this.getProductUpPriceAndDownProce(productView, shopId);
        } else {
            //商品已经上架
            productView1 = this.getShopProductUpAndDown(productView, shopId);
        }

        return productView1;
    }

    /**
     * 判断商品是否已经上架
     *
     * @return
     */
    private ProductView judgeProductSales(ProductView productView, Long shopId) {
        Long productId = productView.getId();
        ShopProduct shopProduct = shopProductDao.findByShopIdAndProductId(shopId, productId);
        if (!ObjectUtils.isEmpty(shopProduct) && !ObjectUtils.isEmpty(shopProduct.getId())) {
            productView.setShopProductId(shopProduct.getId());
            // 上架商品是否已被推荐
            productView.setOnSale(shopProductDao.getOne(shopProduct.getId()).getOnSale());
        } else {
            productView.setShopProductId(0L);
            productView.setOnSale(0);
        }

        return productView;
    }

    /**
     * 获取上架商品的最高价、利润和最低价、利润
     *
     * @param productView
     * @param shopId
     * @return
     */
    private ProductView getShopProductUpAndDown(ProductView productView, Long shopId) {
        Long shopProductId = productView.getShopProductId();
        ProductDetailView productDetailView = new ProductDetailView();
        List<ShopProductDetail> shopProductDetailList = shopProductDetailDao.getShopProductDetailsByShopProductId(shopProductId);

//        int upperPrice = 0;
//        int downPrice = 0;
        long upperProfit = 0;
        long downProfit = 0;

        //如果商品没有详情，则售价和利润都设置为0
        if (CollectionUtils.isEmpty(shopProductDetailList)) {
//            productView.setPriceIntervalUp(upperPrice);
//            productView.setPriceIntervaDown(downPrice);
            productView.setProfitIntervalDown(downProfit);
            productView.setProfitIntervalUp(upperProfit);
            return productView;
        }

        if (shopProductDetailList.size() == 1) {
            ShopProductDetail shopProductDetail = shopProductDetailList.get(0);

            //如果售价为空，则默认是商品的建议零售价
            if (ObjectUtils.isEmpty(shopProductDetail.getProductPrice())) {
                ProductDetail productDetail = productDetailDao.getOne(shopProductDetail.getProductDetail().getId());
                shopProductDetail.setProductPrice(Integer.parseInt(productDetail.getPrice().toString()));
            }

//            upperPrice = shopProductDetail.getProductPrice();
//            downPrice  = upperPrice;
            //根据店铺和售价查询商品利润
            productDetailView.setId(shopProductDetail.getProductDetail().getId());
            productDetailView.setPrice(shopProductDetail.getProductPrice().longValue());
            upperProfit = productDetailService.calculateProductProfit(productDetailView, shopId);
            downProfit = upperProfit;

//            productView.setPriceIntervalUp(upperPrice);
//            productView.setPriceIntervaDown(downPrice);
            productView.setProfitIntervalUp(upperProfit);
            productView.setProfitIntervalDown(downProfit);
            return productView;
        }

        int i = 0;
        for (ShopProductDetail shopProductDetail : shopProductDetailList) {
            if (ObjectUtils.isEmpty(shopProductDetail.getProductPrice())) {
                ProductDetail productDetail = productDetailDao.getOne(shopProductDetail.getProductDetail().getId());
                shopProductDetail.setProductPrice(Integer.parseInt(productDetail.getPrice().toString()));
            }

            productDetailView.setPrice(shopProductDetail.getProductPrice().longValue());
            productDetailView.setId(shopProductDetail.getProductDetail().getId());
            long profit = productDetailService.calculateProductProfit(productDetailView, shopId);

            if (i == 0) {
//                upperPrice = shopProductDetail.getProductPrice();
//                downPrice = upperPrice;
                upperProfit = profit;
                downProfit = upperProfit;
                i++;
                continue;
            }

//            if(shopProductDetail.getProductPrice() > upperPrice){
//                upperPrice = shopProductDetail.getProductPrice();
//            }
//            if(shopProductDetail.getProductPrice() < downPrice){
//                downPrice = shopProductDetail.getProductPrice();
//            }
            if (profit > upperProfit) {
                upperProfit = profit;
            }
            if (profit < downProfit) {
                downProfit = profit;
            }
        }

//        productView.setPriceIntervalUp(upperPrice);
//        productView.setPriceIntervaDown(downPrice);
        productView.setProfitIntervalDown(downProfit);
        productView.setProfitIntervalUp(upperProfit);
        return productView;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public ProductView saveProductProductDetail(ProductView productView) {
        // 保存的业务逻辑
        Product product = new Product();
        TorinoSrcBeanUtils.copyBean(productView, product);
        // user数据库映射传给dao进行存储
        product.setCreateTime(System.currentTimeMillis());
        product.setUpdateTime(System.currentTimeMillis());
        product.setCategoryId(productView.getCategoryId());
        productDao.save(product);
        TorinoSrcBeanUtils.copyBean(product, productView);

        Long productId = product.getId();

        if (!ObjectUtils.isEmpty(productView.getProductDetails())) {
            List<ProductDetail> productDetails = productView.getProductDetails();
            for (ProductDetail productDetail : productDetails) {
                productDetail.setCreateTime(System.currentTimeMillis());
                productDetail.setUpdateTime(System.currentTimeMillis());
                productDetail.setEnabled(1);
                productDetail.setProductId(productId);
                productDetailDao.save(productDetail);

                if(productView.getType()==MallConstant.PRODUCT_TYPE_TEAM||product.getType()==MallConstant.PRODUCT_TYPE_GENERAL_AND_TEAM) {
                    this.saveProductDetailPrice(productDetail);
                }

            }
        }
        // 保存分组商品关联表
        this.saveIndexProductType(productView);

        return productView;
    }

    /**
     * 保存商品详情的价格
     * @param productDetail
     */
    private void saveProductDetailPrice(ProductDetail productDetail) {
        Long productDetailId = productDetail.getId();
        ProductDetailPrice productDetailPrice = productDetailPriceDao.findByProductDetailId(productDetailId);

        if (ObjectUtils.isEmpty(productDetailPrice)) {
            // create
            ProductDetailPrice productDetailPriceNew = new ProductDetailPrice();
            productDetailPriceNew.setPrice(productDetail.getPrice());
            productDetailPriceNew.setTeamPrice(productDetail.getGroupPrice());
            productDetailPriceNew.setProductDetailId(productDetail.getId());
            productDetailPriceNew.setCreateTime(System.currentTimeMillis());
            productDetailPriceNew.setUpdateTime(System.currentTimeMillis());
            productDetailPriceNew.setEnabled(1);
            productDetailPriceDao.save(productDetailPriceNew);

        } else {
            // update
            productDetailPrice.setUpdateTime(System.currentTimeMillis());
            productDetailPrice.setPrice(productDetail.getPrice());
            productDetailPrice.setTeamPrice(productDetail.getGroupPrice());
            productDetailPriceDao.save(productDetailPrice);
        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public Long saveProductView(Product product) {
        Product productView = new Product();
        TorinoSrcBeanUtils.copyBean(product, productView);
        //保存商品表
        String productName=DateUtils.timeStamp2Date(System.currentTimeMillis()+"","yyyyMMdd HH:mm:ss")+product.getName();
        productView.setName(productName);
        productView.setCreateTime(System.currentTimeMillis());
        productView.setUpdateTime(System.currentTimeMillis());
        productView.setProductDetailList(null);
        productView.setId(null);
        productDao.save(productView);
        return productView.getId();
    }

    @Transactional(rollbackOn = {Exception.class})
    public void saveProductDetailList(List<ProductDetail> productDetails,Long productId) {
        for (ProductDetail productDetail:productDetails){
            ProductDetail productDetailView = new ProductDetail();
            TorinoSrcBeanUtils.copyBean(productDetail, productDetailView);
            productDetailView.setCreateTime(System.currentTimeMillis());
            productDetailView.setUpdateTime(System.currentTimeMillis());
            productDetailView.setProductId(productId);
            productDetailView.setId(null);
            productDetailView.setProductDetailPrice(null);
            productDetailView.setProductDetailPriceList(null);
            productDetailDao.save(productDetailView);
        }
    }
    @Transactional(rollbackOn = {Exception.class})
    public void saveProductDetailAndPriceList(List<ProductDetail> productDetails,Long productId,List<ProductDetailPrice> productDetailPrices) {

        Long productDetailId=0L;
        for (ProductDetail productDetail:productDetails){
            ProductDetail productDetailView = new ProductDetail();
            TorinoSrcBeanUtils.copyBean(productDetail, productDetailView);

            productDetailView.setCreateTime(System.currentTimeMillis());
            productDetailView.setUpdateTime(System.currentTimeMillis());
            productDetailView.setProductId(productId);
            productDetailView.setId(null);
            productDetailView.setProductDetailPrice(null);
            productDetailView.setProductDetailPriceList(null);
            productDetailDao.save(productDetailView);

            productDetailId=productDetail.getId();

            for (ProductDetailPrice productDetailPrice:productDetailPrices){
                if(productDetailPrice.getProductDetailId().longValue()==productDetailId.longValue()){
                    ProductDetailPrice productDetailPriceNew = new ProductDetailPrice();
                    TorinoSrcBeanUtils.copyBean(productDetailPrice,productDetailPriceNew);
                    productDetailPriceNew.setProductDetailId(productDetailView.getId());
                    productDetailPriceNew.setId(null);
                    productDetailPriceNew.setCreateTime(System.currentTimeMillis());
                    productDetailPriceNew.setUpdateTime(System.currentTimeMillis());
                    productDetailPriceNew.setEnabled(1);
                    productDetailPriceDao.save(productDetailPriceNew);
                }else{

                }
            }
        }
    }
    @Transactional(rollbackOn = {Exception.class})
    public void saveProductDetailPrices(List<ProductDetailPrice> productDetailPrices,Long productDetailId) {

    }
    @Transactional(rollbackOn = {Exception.class})
    public void saveCategory(Category category) {
        category.setCreateTime(System.currentTimeMillis());
        category.setUpdateTime(System.currentTimeMillis());
        category.setEnabled(1);
        categoryDao.save(category);
    }
    @Transactional(rollbackOn = {Exception.class})
    public void saveProductDetailPriceList(List<ProductDetailPrice> productDetailPrices,List<Long> productDetailIds,List<Long> productDetailIdsNew) {

        //    for (Long productDetailId : productDetailIds) {
        for (int i=0;i>productDetailIds.size();i++){
            //查询一共有多少条记录
            List<ProductDetailPrice> productDetailPrices1 = productDetailPriceDao.findAllByProductDetailId(productDetailIds.get(i));
//
//                    if (i>=1){
//                        for (int j=i;j>i+productDetailPrices1.size();j++){
//                            productDetailPrices.get(i).setCreateTime(System.currentTimeMillis());
//                            productDetailPrices.get(i).setUpdateTime(System.currentTimeMillis());
//                            productDetailPrices.get(i).setProductDetailId(productDetailIdsNew.get(i));
//                            productDetailPrices.get(i).setId(null);
//                            productDetailPriceDao.save(productDetailPrices.get(i));
//                        }
//                    }else {
//                        for (int j=0;j>productDetailPrices1.size();j++){
//                            productDetailPrices.get(i).setCreateTime(System.currentTimeMillis());
//                            productDetailPrices.get(i).setUpdateTime(System.currentTimeMillis());
//                            productDetailPrices.get(i).setProductDetailId(productDetailIdsNew.get(i));
//                            productDetailPrices.get(i).setId(null);
//                            productDetailPriceDao.save(productDetailPrices.get(i));
//                        }
//                    }



        }
    }


    @Override
    public IndexProductView getIndexProductView() {

        // 要查询出来的记录数
        Integer num = 6;

        // 热门活动商品
        List<IndexProductTypeProduct> indexProductTypeProductsHot = indexProductTypeProductDao.findByIndexProductTypeIdOrderByWeightLimit(MallConstant.INDEX_PRODUCT_TYPE_HOT, num);
        List<ProductView> hotProducts = getIndexProductViewList(indexProductTypeProductsHot);

        // 新品
        List<IndexProductTypeProduct> indexProductTypeProductsNew = indexProductTypeProductDao.findByIndexProductTypeIdOrderByWeightLimit(MallConstant.INDEX_PRODUCT_TYPE_NEW, num);
        List<ProductView> newProducts = getIndexProductViewList(indexProductTypeProductsNew);

        // 精选优品
        List<IndexProductTypeProduct> indexProductTypeProductsChosen = indexProductTypeProductDao.findByIndexProductTypeIdOrderByWeightLimit(MallConstant.INDEX_PRODUCT_TYPE_CHOSEN, num);
        List<ProductView> chosenProducts = getIndexProductViewList(indexProductTypeProductsChosen);

        IndexProductView indexProductViewReturn = new IndexProductView();
        indexProductViewReturn.setHotProducts(hotProducts);
        indexProductViewReturn.setNewProducts(newProducts);
        indexProductViewReturn.setChosenProducts(chosenProducts);
        return indexProductViewReturn;
    }

    /**
     * 获取首页单个模块的商品列表
     * @param indexProductTypeProducts
     * @return
     */
    private List<ProductView> getIndexProductViewList(List<IndexProductTypeProduct> indexProductTypeProducts) {
        List<ProductView> productViewList = new ArrayList<>();
        ProductView productView;
        for (IndexProductTypeProduct indexProductTypeProduct : indexProductTypeProducts) {
            productView = this.getEntity(indexProductTypeProduct.getProductId());
            productView.setCategory(null);
            productView.setProductDetails(new ArrayList<>());

            productViewList.add(productView);
        }
        return productViewList;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public ProductView getCopyEntity(Long id) {
        //通过ID获取商品表信息
        Product product = productDao.findById(id).get();

        //获取商品表（t_product）ID
        Long productId = product.getId();
        //通过商品表（t_product）ID获取商品详情表（t_product_detail）信息
        List<ProductDetail> productDetails = productDetailDao.findByProductId(productId);


        //获取每个商品表信息中ID
        List<Long> productDetailId = new ArrayList<>();
        for (ProductDetail productDetail : productDetails) {
            Long Id = productDetail.getId();
            productDetailId.add(Id);
        }
        //通过商品详情表（t_product_detail）ID获取商品详情价格表（t_product_detail_price）信息
        List<ProductDetailPrice> productDetailPrices = productDetailPriceDao.findByProductDetailIdIn(productDetailId);

        //保存商品表并返回新增ID
        Long productid = this.saveProductView(product);

        if (MallConstant.PRODUCT_TYPE_GENERAL == product.getType()){
            //保存商品详情表
            this.saveProductDetailList(productDetails,productid);
        }else{
            //保存商品详情表并返回新增ID
            this.saveProductDetailAndPriceList(productDetails,productid,productDetailPrices);
        }

        // 保存商品与分组商品的关联
        ProductView productView=new ProductView();
        TorinoSrcBeanUtils.copyBean(product,productView);
        productView=this.getIndexProductTypeView(productView);
        productView.setId(productid);
        this.saveIndexProductType(productView);

        return productView;
    }

    @Override
    public IntegratedProductView getProductByProductIdAndShopId(Long productId, Long shopId) {

        Optional<Product> productOpt = productDao.findById(productId);
        if (!productOpt.isPresent()) {
            throw new TorinoSrcServiceException("商品不存在");
        } else {
            // no need to do anything...
        }

        ProductView productView = new ProductView();
        Product product = productOpt.get();
        TorinoSrcBeanUtils.copyBean(product, productView);

        IntegratedProductView integratedProductView;
        Integer productType = product.getType();
        switch (productType) {
            case 0:
                // 普通商品
                integratedProductView = getGeneralProduct(productView, shopId);
                break;
            case 1:
                // 拼团商品
                integratedProductView = getSpellTeamProduct(productView);
                break;
            case 2:
                // 兼有（普通商品+拼团商品）
                integratedProductView = getGeneralAndSpellProduct(productView, shopId);
                break;
            case 3:
                // 助力购商品
                integratedProductView =getBoostProduct(productView);
                break;
            default:
                throw new TorinoSrcServiceException("商品类型异常");
        }

        return integratedProductView;
    }

    /**
     * 获取普通商品
     * @param productView
     * @param shopId
     * @return
     */
    private IntegratedProductView getGeneralProduct(ProductView productView, Long shopId) {
        Long productId = productView.getId();
        IntegratedProductView integratedProductView = new IntegratedProductView();
        ShopProduct shopProduct = shopProductDao.findByShopIdAndProductId(shopId, productId);
        if (ObjectUtils.isEmpty(shopProduct)) {
            // 商品没有上架
            productView = getProductProfit(productId, shopId);
            integratedProductView.setProductView(productView);
            integratedProductView.setIsShopProduct(0);
        } else {
            // 商品已在当前店铺上架
            Long shopProductId = shopProduct.getId();
            ShopProductView shopProductView = shopProductService.getEntityAutomaticCompletion(shopProductId);
            integratedProductView.setShopProductView(shopProductView);
            integratedProductView.setIsShopProduct(1);
        }

        return integratedProductView;
    }

    /**
     * 获取拼团商品
     * @param productView
     * @return
     */
    private IntegratedProductView getSpellTeamProduct(ProductView productView) {
        Long productId = productView.getId();
        IntegratedProductView integratedProductView = new IntegratedProductView();
        productView = getEntity(productId);
        // 拼团商品不可以上架
        integratedProductView.setIsShopProduct(0);
        integratedProductView.setProductView(productView);
        return integratedProductView;
    }

    /**
     * 获取兼有商品（普通+拼团）
     * @param productView
     * @param shopId
     * @return
     */
    private IntegratedProductView getGeneralAndSpellProduct(ProductView productView, Long shopId) {
        Long productId = productView.getId();
        IntegratedProductView integratedProductView = new IntegratedProductView();
        productView = getEntity(productId);
        integratedProductView.setProductView(productView);

        ShopProduct shopProduct = shopProductDao.findByShopIdAndProductId(shopId, productId);
        if (!ObjectUtils.isEmpty(shopProduct)) {
            // 商品已上架
            Long shopProductId = shopProduct.getId();
            ShopProductView shopProductView = shopProductService.getEntityAutomaticCompletion(shopProductId);
            integratedProductView.setShopProductView(shopProductView);
            integratedProductView.setIsShopProduct(1);
        } else {
            integratedProductView.setIsShopProduct(0);
        }
        return integratedProductView;
    }

    /**
     * 获取助力购商品
     * @param productView
     */
    private IntegratedProductView getBoostProduct(ProductView productView) {
        Long productId = productView.getId();
        IntegratedProductView integratedProductView = new IntegratedProductView();
        Map productPriceRangeMap = productDao.findProductPriceRange(productId);
        Integer minProductPrice = Integer.valueOf(productPriceRangeMap.get("min_price").toString());
        Integer maxProductPrice = Integer.valueOf(productPriceRangeMap.get("max_price").toString());
        productView.setPriceIntervalUp(maxProductPrice);
        productView.setPriceIntervaDown(minProductPrice);
        // 最高市场价的商品详情
        ProductDetail uppestPriceProductDetail = productDetailDao.findFirstByProductIdOrderByUppestPriceDesc(productId);
        productView.setMarketPriceIntervalUp(uppestPriceProductDetail.getUppestPrice());
        integratedProductView.setProductView(productView);
        integratedProductView.setIsShopProduct(0);

        return integratedProductView;
    }

    @Override
    public ProductPageView getEntitiesByParmsSearch(ProductView productView, int currentPage, int pageSize) {

        ProductCategoryTableView productCategoryTableView = new ProductCategoryTableView();
        productCategoryTableView.setCondition(productView.getCondition());
        Page<ProductCategoryTableView> productCategoryTableViews = productCategoryTableService.getEntitiesByContion(productCategoryTableView, currentPage, pageSize);

        ProductPageView productPageView = new ProductPageView();
        List<Product> products = new ArrayList<>();
        Product product;
        ProductView productView1;
        List<ProductView> productViewList = new ArrayList<>();
        for (ProductCategoryTableView productCategoryTableView1 : productCategoryTableViews) {
            product = productDao.getOne(productCategoryTableView1.getId());
            products.add(product);
            productView1 = new ProductView();
            TorinoSrcBeanUtils.copyBean(product, productView1);
            productViewList.add(productView1);
        }

        productPageView.setProductViewList(productViewList);
        productPageView.setTotalElements(productCategoryTableViews.getTotalElements());

        return productPageView;
    }

    // 保存商品与分组商品的关联表（先删除后新增）
    @Transactional(rollbackOn = {Exception.class})
    public void saveIndexProductType(ProductView productView){

        Long productId=productView.getId();
        // 删除
        indexProductTypeProductDao.deleteByProductId(productId);

        List<IndexProductTypeProduct> indexProductTypeProducts=new ArrayList<>();
        IndexProductTypeProduct indexProductTypeProduct;
        // 新增
        for(IndexProductTypeProduct indexProductTypeProduct1:productView.getIndexProductTypeProductView()){
            indexProductTypeProduct = new IndexProductTypeProduct();

            // user数据库映射传给dao进行存储
            indexProductTypeProduct.setCreateTime(System.currentTimeMillis());
            indexProductTypeProduct.setUpdateTime(System.currentTimeMillis());
            indexProductTypeProduct.setEnabled(1);

            indexProductTypeProduct.setProductId(productView.getId());
            indexProductTypeProduct.setWeight(productView.getWeight());
            indexProductTypeProduct.setName(productView.getName());
            indexProductTypeProduct.setTitle(productView.getTitle());

            indexProductTypeProduct.setIndexProductTypeId(indexProductTypeProduct1.getIndexProductTypeId());
            indexProductTypeProducts.add(indexProductTypeProduct);
        }

        indexProductTypeProductDao.saveAll(indexProductTypeProducts);

    }

    // 获取分组商品信息
    private ProductView getIndexProductTypeView(ProductView productView){

        Long productId=productView.getId();
        List<IndexProductTypeProduct> indexProductTypeProducts=indexProductTypeProductDao.findByProductId(productId);
        List<IndexProductTypeProductView> indexProductTypeProductViews;
        if(!ObjectUtils.isEmpty(indexProductTypeProducts)&&indexProductTypeProducts.size()>0){
            indexProductTypeProductViews=new ArrayList<>();
            for(IndexProductTypeProduct indexProductTypeProduct:indexProductTypeProducts){
                IndexProductTypeProductView indexProductTypeProductView=new IndexProductTypeProductView();
                IndexProductType indexProductType=indexProductTypeDao.findById(indexProductTypeProduct.getIndexProductTypeId()).get();
                if(!ObjectUtils.isEmpty(indexProductType)){
                    indexProductTypeProductView.setIndexProductTypeName(indexProductType.getName());
                }
                TorinoSrcBeanUtils.copyBean(indexProductTypeProduct,indexProductTypeProductView);
                indexProductTypeProductViews.add(indexProductTypeProductView);
            }
            productView.setIndexProductTypeProductView(indexProductTypeProductViews);
        }

        return productView;
    }

    // 删除上架商品，上架商品明细和清空购物车的上架商品id
    @Transactional(rollbackOn = {Exception.class})
    @Override
    public void deleteShopProduct(Long productId){
        List<ShopProduct> shopProducts=shopProductDao.findByProductId(productId);
        Long shopProductId;
        Long shopProductDetailId;
        for(ShopProduct shopProduct:shopProducts){
            Set<ShopProductDetail> shopProductDetails=shopProduct.getShopProductDetails();
            for(ShopProductDetail shopProductDetail:shopProductDetails){
                shopProductDetailId=shopProductDetail.getId();
                List<ShoppingCartDetail> shoppingCartDetails=shoppingCartDetailDao.findByShopProductDetailId(shopProductDetailId);
                // udpate
                for(ShoppingCartDetail shoppingCartDetail:shoppingCartDetails){
                    shoppingCartDetail.setShopProductDetailId(null);
                    shoppingCartDetailDao.save(shoppingCartDetail);
                }
            }
            shopProductDetailDao.deleteInBatch(shopProductDetails);
        }
        shopProductDao.deleteInBatch(shopProducts);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void saveBoostProduct(ProductReviceView productReviceView){

        Integer validDay=productReviceView.getValidDay();
        Integer boostAmount=productReviceView.getBoostAmount();
        Integer boostNumber=productReviceView.getBoostNumber();

        List<Product> products=null;

        if(productReviceView.getProductUsingRange()==0){
            // 全部商品
            products= productDao.findByEnabledAndType(1,0);
        } else if(productReviceView.getProductUsingRange()==1){
            // 指定商品
            products= productReviceView.getProducts();
        }else {
            //nothing to do
        }
        ProductView productView=null;
        // update操作
        if(!ObjectUtils.isEmpty(products)){
            for(Product product:products){
                productView=new ProductView();
                TorinoSrcBeanUtils.copyBean(product,productView);
                productView.setType(3);
                productView.setValidDay(validDay);
                productView.setBoostAmount(boostAmount);
                productView.setBoostNumber(boostNumber);
                this.updateEntity(productView);
            }
        }
    }

    public void deleteBoostProduct(Long id){
        Product product=productDao.findById(id).get();
        product.setType(0);
        product.setValidDay(null);
        product.setBoostAmount(null);
        product.setBoostNumber(null);
        productDao.save(product);
    }

    public void deleteBoostProductEntities(String ids) {
        String[] entityIds = TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        for (String entityId : entityIds) {
            this.deleteBoostProduct(Long.valueOf(entityId));
        }
    }



}
