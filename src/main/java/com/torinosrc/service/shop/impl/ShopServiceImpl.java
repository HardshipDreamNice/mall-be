/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.shop.impl;

import com.torinosrc.commons.constants.MallConstant;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.*;
import com.torinosrc.commons.utils.image.CompoundImageInfo;
import com.torinosrc.commons.utils.image.ImageUtils;
import com.torinosrc.dao.distributionpriceconfig.DistributionPriceConfigDao;
import com.torinosrc.dao.membershipgrade.MembershipGradeDao;
import com.torinosrc.dao.order.OrderDao;
import com.torinosrc.dao.orderdetail.OrderDetailDao;
import com.torinosrc.dao.productdetailsnapshot.ProductDetailSnapshotDao;
import com.torinosrc.dao.shop.ShopDao;
import com.torinosrc.dao.shopaccount.ShopAccountDao;
import com.torinosrc.dao.shopaccountdetail.ShopAccountDetailDao;
import com.torinosrc.dao.shopproduct.ShopProductDao;
import com.torinosrc.dao.shopproductdetail.ShopProductDetailDao;
import com.torinosrc.dao.shoptree.ShopTreeDao;
import com.torinosrc.dao.user.UserDao;
import com.torinosrc.model.entity.accesstoken.AccessToken;
import com.torinosrc.model.entity.distributionpriceconfig.DistributionPriceConfig;
import com.torinosrc.model.entity.membershipgrade.MembershipGrade;
import com.torinosrc.model.entity.order.Order;
import com.torinosrc.model.entity.orderdetail.OrderDetail;
import com.torinosrc.model.entity.productdetailsnapshot.ProductDetailSnapshot;
import com.torinosrc.model.entity.shop.Shop;
import com.torinosrc.model.entity.shopaccount.ShopAccount;
import com.torinosrc.model.entity.shopaccountdetail.ShopAccountDetail;
import com.torinosrc.model.entity.shopproduct.ShopProduct;
import com.torinosrc.model.entity.shopproductdetail.ShopProductDetail;
import com.torinosrc.model.entity.shoptree.ShopTree;
import com.torinosrc.model.entity.user.User;
import com.torinosrc.model.view.common.CustomPage;
import com.torinosrc.model.view.membershipgrade.MembershipGradeView;
import com.torinosrc.model.view.shop.ShopOrgnization;
import com.torinosrc.model.view.shop.ShopView;
import com.torinosrc.model.view.user.UserView;
import com.torinosrc.service.accesstoken.AccessTokenService;
import com.torinosrc.service.shop.ShopService;
import com.torinosrc.service.user.UserService;
import net.sf.json.JSONObject;
import org.mockito.internal.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.io.File;
import java.util.*;

/**
 * <b><code>ShopImpl</code></b>
 * <p/>
 * Shop的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-11 18:07:23.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Transactional
@Service
public class ShopServiceImpl implements ShopService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ShopServiceImpl.class);

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private ShopTreeDao shopTreeDao;

    @Autowired
    private ShopProductDao shopProductDao;

    @Autowired
    private ShopAccountDao shopAccountDao;

    @Autowired
    private ShopAccountDetailDao shopAccountDetailDao;

    @Autowired
    private ShopProductDetailDao shopProductDetailDao;

    @Autowired
    private DistributionPriceConfigDao distributionPriceConfigDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private MembershipGradeDao membershipGradeDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductDetailSnapshotDao productDetailSnapshotDao;

    /**
     * 订单时间段类型 0是全部 1是本周 2是本月 3是本日
     */
    private final static Integer ORDER_PERIOD_ALL = 0;

    private final static Integer ORDER_PERIOD_CURRENTWEEK = 1 ;

    private final static Integer ORDER_PERIOD_CURRENTMONTH = 2;

    private final static Integer ORDER_PERIOD_CURRENTDAY = 3;


    //订单交易成功状态
    private final static Integer ORDER_TRANSACTION_SUCCESS_STATUS = 5;

    private final String GET_QRCODE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit";
    // 小程序二维码跳转的页面
    private final String QRCODE_PAGE = "pages/index/authorization/authorization";

    /**
     * 小程序二维码的生成路径
     */
    @Value("${weixin.qrCodePath}")
    private String QRCODE_PATH;

    @Value("${weixin.shopQrCodeBackGroupPic}")
    private String SHOP_QRCODE_BACKGROUP_PIC;

    @Override
    public ShopView getEntity(long id) {
        // 获取Entity
        Shop shop = shopDao.getOne(id);
        // 复制Dao层属性到view属性
        ShopView shopView = new ShopView();
        TorinoSrcBeanUtils.copyBean(shop, shopView);
        String avtar = userDao.findAvatarByUserId(shopView.getUserId());
        shopView.setUserAvatar(avtar);
        return shopView;
    }

    @Override
    public Page<ShopView> getEntitiesByParms(ShopView shopView, int currentPage, int pageSize) {

        Specification<Shop> shopSpecification = new Specification<Shop>() {
            @Override
            public Predicate toPredicate(Root<Shop> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                return TorinoSrcConditionUtils.getPredicate(root, criteriaQuery, criteriaBuilder, shopView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Shop> shops = shopDao.findAll(shopSpecification, pageable);

        // 转换成View对象并返回
        return shops.map(shop -> {
            ShopView shopView1 = new ShopView();
            TorinoSrcBeanUtils.copyBean(shop, shopView1);
            return shopView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return shopDao.count();
    }

    @Override
    public List<ShopView> findAll() {
        List<ShopView> shopViews = new ArrayList<>();
        List<Shop> shops = shopDao.findAll();
        for (Shop shop : shops) {
            ShopView shopView = new ShopView();
            TorinoSrcBeanUtils.copyBean(shop, shopView);
            shopViews.add(shopView);
        }
        return shopViews;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public ShopView saveEntity(ShopView shopView) {
        // 更新user
        User user = userDao.getOne(shopView.getUserId());
        UserView userView = new UserView();
        TorinoSrcBeanUtils.copyBean(user, userView);
        userView.setPhone(shopView.getPhone());
        userService.updateEntity(userView);
        // 保存的业务逻辑
        Shop shop = new Shop();
        TorinoSrcBeanUtils.copyBean(shopView, shop);
        String nickName = userDao.findNickNameByUserId(shopView.getUserId());
        // user数据库映射传给dao进行存储
        shop.setCreateTime(System.currentTimeMillis());
        shop.setUpdateTime(System.currentTimeMillis());
        shop.setEnabled(1);
        DistributionPriceConfig distributionPriceConfig = distributionPriceConfigDao.findByPercentConfig(MallConstant.PERCENT_DEFAULT);
        shop.setPercent1(distributionPriceConfig.getPercent1());
        shop.setPercent2(distributionPriceConfig.getPercent2());
        shop.setPercent3(distributionPriceConfig.getPercent3());
        shop.setPhone(shopView.getPhone());
        // 店铺名字与描述（默认值）
        if (nickName.length() > 6) {
            shop.setTitle(nickName.substring(0, 6) + "小店");
        } else {
            shop.setTitle(nickName + "小店");
        }
        shop.setDescription("我的小店铺开业啦");
        shopDao.save(shop);
        TorinoSrcBeanUtils.copyBean(shop, shopView);
        //保存树结构中间表
        ShopTree shopTree = new ShopTree();
        shopTree.setCreateTime(System.currentTimeMillis());
        shopTree.setUpdateTime(System.currentTimeMillis());
        shopTree.setEnabled(1);
        shopTree.setShopId(shop.getId());
        shopTree.setParentShopId(shopView.getParentId());
        shopTreeDao.save(shopTree);
        //默认创建账号
        ShopAccount shopAccount = new ShopAccount();
        shopAccount.setShopId(shop.getId());
        shopAccount.setTotalAmount(0L);
        shopAccount.setMoney(0L);
        shopAccount.setCreateTime(System.currentTimeMillis());
        shopAccount.setUpdateTime(System.currentTimeMillis());
        shopAccountDao.save(shopAccount);
        return shopView;
    }

    @Override
    public void deleteEntity(long id) {
        Shop shop = new Shop();
        shop.setId(id);
        //删除账号
        ShopAccount shopAccount = shopAccountDao.findShopAccountByShopId(id);
        shopAccountDetailDao.disabledShopAccountDetailsByShopAccountId(shopAccount.getId());
        shopAccountDao.disabledShopAccount(shopAccount.getId());
        //删除商店商品
        List<ShopProduct> shopProducts = shopProductDao.findShopProductsByShopId(shop.getId());

        if (shopProducts.size() != 0) {
            for (ShopProduct shopProduct : shopProducts) {
                shopProductDetailDao.deleteShopProductDetailsByShopProductId(shopProduct.getId());
            }
            shopProductDao.deleteShopProductsByShopId(id);
        }
        //冻结店铺
        shopDao.disabledShop(shop.getId());
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteEntities(String ids) {
        String[] entityIds = TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<Shop> shops = new ArrayList<>();
        for (String entityId : entityIds) {
            this.deleteEntity(Long.valueOf(entityId));
        }
    }

    @Override
    public void updateEntity(ShopView shopView) {
        Specification<Shop> shopSpecification = Optional.ofNullable(shopView).map(s -> {
            return new Specification<Shop>() {
                @Override
                public Predicate toPredicate(Root<Shop> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(() -> new IllegalArgumentException("ShopView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<Shop> shopOptionalBySearch = shopDao.findOne(shopSpecification);
        shopOptionalBySearch.map(shopBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(shopView, shopBySearch);
            shopBySearch.setUpdateTime(System.currentTimeMillis());
            shopDao.save(shopBySearch);
            return "";
        }).orElseThrow(() -> new TorinoSrcServiceException("无法找到id为" + shopView.getId() + "的数据记录"));
    }

    @Override
    public ShopView findShopByUserId(Long userId) {
        ShopView shopView = new ShopView();
        Boolean isInvite = true;
        if (!ObjectUtils.isEmpty(shopDao.findShopByUserId(userId))) {
            Shop shop = shopDao.findShopByUserId(userId);
            TorinoSrcBeanUtils.copyBean(shop, shopView);
            // 获取当前店铺树结构
            ShopTree shopTree = shopTreeDao.findByShopId(shop.getId());
            if (!ObjectUtils.isEmpty(shopTreeDao.findByShopId(shopTree.getParentShopId()))) {
                shopTree = shopTreeDao.findByShopId(shopTree.getParentShopId());
                if (!ObjectUtils.isEmpty(shopTreeDao.findByShopId(shopTree.getParentShopId()))) {
                    isInvite = false;
                }
            }

            if (!ObjectUtils.isEmpty(shop.getMembershipGradeId()) && shop.getMembershipGradeId() != 0) {
                MembershipGrade membershipGrade = membershipGradeDao.getOne(shop.getMembershipGradeId());
                MembershipGradeView membershipGradeView = new MembershipGradeView();
                TorinoSrcBeanUtils.copyBean(membershipGrade, membershipGradeView);
                shopView.setMembershipGradeView(membershipGradeView);
            } else {
                MembershipGradeView membershipGradeView = new MembershipGradeView();
                membershipGradeView.setName("普通会员");
                membershipGradeView.setGrade(0);
                membershipGradeView.setEnabled(1);
                shopView.setMembershipGradeView(membershipGradeView);
            }
        }
        shopView.setInvite(isInvite);

        return shopView;
    }

    @Override
    public List<ShopView> findAllNextLevlShopsByUserId(Long userId) {

        //获取用户的店铺
        Shop shop = shopDao.findShopByUserId(userId);

        //获取用户的下级店铺
        List<Long> shopTreeIds = shopTreeDao.findShopTreesByParentShopIdReturnIds(shop.getId());

        List<ShopView> shopViews = new ArrayList<>();
        List<Shop> nextLevelShops = shopDao.findShopsByIdIn(shopTreeIds);
//        System.out.println("子店铺的集合1："+nextLevelShops);
        for (Shop shop1 : nextLevelShops) {
            ShopView shopView = new ShopView();
            TorinoSrcBeanUtils.copyBean(shop1, shopView);
            shopViews.add(shopView);
        }
//        System.out.println("子店铺集合："+shopViews);
        return shopViews;
    }

     //获取下级店铺
    public CustomPage<ShopView> getEntityChild(Long userId,ShopView shopView, int currentPage, int pageSize) {

        //获取用户的店铺
        Shop shop = shopDao.findShopByUserId(userId);
        //获取用户的店铺
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        //获取用户的下级店铺
        Page<ShopTree> ShopTrees = shopTreeDao.findShopTreeByParentShopId(shop.getId(), pageable);
        List<Long> shopTreeIds = shopTreeDao.findShopTreesByParentShopIdReturnIds(shop.getId());
        List<ShopView> nextshopViews = new ArrayList<>();
        if (!org.springframework.util.CollectionUtils.isEmpty(shopTreeIds)) {
            //拿到一级，二级，三级分配
            Integer percent1 = 0;
            Shop myShop = shopDao.getOne(shop.getId());
            Long parentShopId = shopTreeDao.findByShopId(shop.getId()).getParentShopId();
            //level1
            if (parentShopId.longValue() == 0) {
                percent1 = myShop.getPercent1();
            } else {
                //level2
                Shop parenetShop = shopDao.getOne(parentShopId);
                percent1 = parenetShop.getPercent2();
            }
//        System.out.println("percent1"+percent1);
            List<Long> shopIds = new ArrayList<>();

            for (ShopTree shopTree : ShopTrees) {
                shopIds.add(shopTree.getShopId());
            }

            //得到下级店铺
            List<Shop> nextLevelShops = shopDao.findShopsByIdIn(shopIds);
            if (!org.springframework.util.CollectionUtils.isEmpty(nextLevelShops)) {
                //获取下级店铺分红和销售额。
                List<Order> orderList;
                for (Shop shop1 : nextLevelShops) {
                    //获取店铺订单
                    orderList = orderDao.getAllByShopIdAndStatus(shop1.getId(), ORDER_TRANSACTION_SUCCESS_STATUS);
                    //获取销售额
                    Integer salesAmount = this.getOrdersSalesAmount(orderList);
                    //获取分红
                    Long highLevelDevidend = this.getLowLevelShopDividend(shop1.getId(), percent1, ORDER_PERIOD_ALL);

                    // 复制Dao层属性到view属性
                    ShopView shopView1 = new ShopView();
                    shopView1.setSalesAmount(salesAmount);
                    shopView1.setHighLevelDevidend(highLevelDevidend);
                    TorinoSrcBeanUtils.copyBean(shop1, shopView1);
                    nextshopViews.add(shopView1);
                }
            }
        }
            CustomPage<ShopView> page = new CustomPage<>();
            page.setContent(nextshopViews);
            page.setTotalElements(shopTreeIds.size());
            return page;

    }


//获取下下级店铺的方法


        public CustomPage<ShopView> getEntityNextChild(Long userId, ShopView shopView, int currentPage, int pageSize) {

//            获取用户的店铺

            Shop shop = shopDao.findShopByUserId(userId);

            Pageable pageable = PageRequest.of(currentPage, pageSize);

            //获取用户的下级店铺
            List<Long> shopTreeIds = shopTreeDao.findShopTreesByParentShopIdReturnIds(shop.getId());
            List<Long> shopIds = new ArrayList<>();
            List<ShopView> nextnextshopViews = new ArrayList<>();
            List<ShopTree> ShopTreenextall= new ArrayList<>();

            if (!org.springframework.util.CollectionUtils.isEmpty(shopTreeIds)) {
                //拿到下下级所有的商店id
                Page<ShopTree> ShopTreenext = shopTreeDao.findShopTreeByParentShopIdIn(shopTreeIds, pageable);
                ShopTreenextall = shopTreeDao.findShopTreeByParentShopIdIn(shopTreeIds);
                     //下下级店铺不空
                if (!org.springframework.util.CollectionUtils.isEmpty(ShopTreenextall)) {
                    for (ShopTree shopTree : ShopTreenext) {
                        shopIds.add(shopTree.getShopId());
                    }
                    //拿到下下级商店信息
                    List<Shop> nextLevelShops = shopDao.findShopsByIdIn(shopIds);

                    //获取下下级店铺的分红和销售额

                    List<Order> nextorderList;
                    for (Shop shop1 : nextLevelShops) {
                        //拿到一级，二级，三级分配
                        Integer percent1 = 0;
                        Long parentShopId = shopTreeDao.findByShopId(shop1.getId()).getParentShopId();

                        //level2
                        Shop parenetShop = shopDao.getOne(parentShopId);
                        percent1 = parenetShop.getPercent2();

                        //获取店铺订单
                        nextorderList = orderDao.getAllByShopIdAndStatus(shop1.getId(), ORDER_TRANSACTION_SUCCESS_STATUS);
                        //获取销售额
                        Integer salesAmount = this.getOrdersSalesAmount(nextorderList);
                        //获取分红
                        Long highLevelDevidend = this.getLowLevelShopDividend(shop1.getId(), percent1, ORDER_PERIOD_ALL);

                        // 复制Dao层属性到view属性
                        ShopView shopView2 = new ShopView();
                        shopView2.setSalesAmount(salesAmount);
                        shopView2.setHighLevelDevidend(highLevelDevidend);
                        TorinoSrcBeanUtils.copyBean(shop1, shopView2);
                        nextnextshopViews.add(shopView2);
                    }
                }
            }
            CustomPage<ShopView> page = new CustomPage<>();
            page.setContent(nextnextshopViews);
            page.setTotalElements(ShopTreenextall.size());
            return page;


        }

    /**
     * 获取订单销售总金额
     * @param orderList
     * @return
     */




    private Integer getOrdersSalesAmount(List<Order> orderList) {
        Integer salesAmount = 0;
        Integer totalFee;
        for (Order order : orderList) {
            totalFee = order.getTotalFee();
            salesAmount = salesAmount + totalFee;
        }

        return salesAmount;
    }

    /**
     * 获取可得的 下级店铺分红
     * @param shopId 店铺id
     * @param percent 分成
     * @param period 时期（0 所有日期 1 当前周 2 当前月 3 是当天）
     * @return
     */
    private Long getLowLevelShopDividend(Long shopId, Integer percent, Integer period) {
        List<com.torinosrc.model.entity.order.Order> orderList = new ArrayList<>();
        switch (period) {
            case 1:
                Long monday = DateUtils.getCurrentMonday();
                Long sunday = DateUtils.getCurrentSunday();
                orderList = orderDao.getAllByShopIdAndStatusAndUpdateTimeBetween(shopId, ORDER_TRANSACTION_SUCCESS_STATUS, monday, sunday);
                break;
            case 2:
                Long currentMonthFirstDay = DateUtils.getCurrentMonthFirstDay();
                Long currentMonthLastDay = DateUtils.getCurrentMonthLastDay();
                orderList = orderDao.getAllByShopIdAndStatusAndUpdateTimeBetween(shopId, ORDER_TRANSACTION_SUCCESS_STATUS, currentMonthFirstDay, currentMonthLastDay);
                break;
            case 3:
                Long currentDayStartTime = DateUtils.getCurrentDayStartTime();
                Long currentDayEndTime = DateUtils.getCurrentDayEndTime();
                orderList = orderDao.getAllByShopIdAndStatusAndUpdateTimeBetween(shopId, ORDER_TRANSACTION_SUCCESS_STATUS, currentDayStartTime, currentDayEndTime);
                break;
            default:
                orderList = orderDao.getAllByShopIdAndStatus(shopId, ORDER_TRANSACTION_SUCCESS_STATUS);
                break;
        }

        List<OrderDetail> orderDetailList;
        Long dividend = 0L;
        for (Order order : orderList) {
            orderDetailList = orderDetailDao.findByOrderId(order.getId());
            Long d = this.getDividendByOrder(orderDetailList, percent);
            dividend = dividend + d;
        }

        return dividend;
    }

    /**
     * 根据订单详情列表和分成比例，返回总分成
     * @param orderDetailList
     * @param percent
     * @return
     */
    private Long getDividendByOrder(List<OrderDetail> orderDetailList, Integer percent) {
        ProductDetailSnapshot productDetailSnapshot;
        Long commission;
        Long dividend = 0L;
        int count;
        for (OrderDetail orderDetail : orderDetailList) {
            productDetailSnapshot=new ProductDetailSnapshot();
            productDetailSnapshot.setId(orderDetail.getProductDetailSnapshotId());
            Example<ProductDetailSnapshot> example = Example.of(productDetailSnapshot);
            Optional<ProductDetailSnapshot> optional = productDetailSnapshotDao.findOne(example);
            count = orderDetail.getCount();
            if (optional.isPresent()) {
                //获取商品详情
                productDetailSnapshot = optional.get();
                //佣金
                commission = productDetailSnapshot.getCommission();
                dividend = dividend + commission * percent/100 * count;
            }
        }
        return dividend;
    }


    public ShopView getEntityAndParent(long id) {
        // 获取Entity
        Shop shop = shopDao.getOne(id);
        // 复制Dao层属性到view属性
        ShopView shopView = new ShopView();
        TorinoSrcBeanUtils.copyBean(shop, shopView);
        String avtar = userDao.findAvatarByUserId(shopView.getUserId());
        //获取上级对象
        ShopTree shopTree=shopTreeDao.findShopTreeByShopId(shop.getId());
        if(!ObjectUtils.isEmpty(shopTree)&& shopTree.getParentShopId()!=0){
               //上级对象的商店信息
              Shop shop1=shopDao.getOne(shopTree.getParentShopId());
              if(!ObjectUtils.isEmpty(shop1)) {
                  //默认是一级店铺
                  Integer percent1 = 1;
                  Long highLevelDevidend = this.getLowLevelShopDividend(shopTree.getShopId(), percent1, ORDER_PERIOD_ALL);
                  // 复制Dao层属性到view属性
                  ShopView shopView1 = new ShopView();
                  shopView1.setHighLevelDevidend(highLevelDevidend);
                  TorinoSrcBeanUtils.copyBean(shop1, shopView1);
                  List<ShopView> shopViewsList = new ArrayList<>();
                  shopViewsList.add(shopView1);
                  shopView.setShopView(shopViewsList);
              }
           }
        if(!StringUtils.isEmpty(avtar)) {
            shopView.setUserAvatar(avtar);
        }
        return shopView;
    }
    /**
     * level3 是客户支付的店铺 level2是上级 level1是上上级
     *
     * @param shopId
     * @return
     */
    @Override
    public ShopOrgnization findShop3LevelOrgnization(Long shopId) {

        ShopOrgnization shopOrgnization = new ShopOrgnization();
        //支付的店铺
        ShopTree shopTreeLevel3 = shopTreeDao.findShopTreeByShopId(shopId);

        Shop shopLevel3 = shopDao.getOne(shopTreeLevel3.getShopId());

        shopOrgnization.setLevel3Shop(shopLevel3);

        if (shopTreeLevel3.getParentShopId() == 0) {

            return shopOrgnization;
        }

        //第二级
        ShopTree shopTreeLevel2 = shopTreeDao.findShopTreeByShopId(shopTreeLevel3.getParentShopId());

        Shop shopLevel2 = shopDao.getOne(shopTreeLevel2.getShopId());

        shopOrgnization.setLevel2Shop(shopLevel2);

        if (shopTreeLevel2.getParentShopId() == 0) {

            return shopOrgnization;
        }

        //第一级
        ShopTree shopTreeLevel1 = shopTreeDao.findShopTreeByShopId(shopTreeLevel2.getParentShopId());

        Shop shopLevel1 = shopDao.getOne(shopTreeLevel1.getShopId());

        shopOrgnization.setLevel1Shop(shopLevel1);

        return shopOrgnization;
    }

    @Override
    public String createShopQrCode(ShopView shopView) {
        Long redirectUrlId = shopView.getRedirectUrlId();
        Long shopId = shopView.getId();
        String accessToken = accessTokenService.getAccessToken();
        String getQrCodeUrl = GET_QRCODE_URL + "?access_token=" + accessToken;

        JSONObject getQrCodeParams = new JSONObject();
        //携带在二维码中的参数，进行BASE64处理 shopId&productId&redirectUrlId&distribution&teamId
        String params = shopId + "&" + "&" + redirectUrlId + "&" + "&";
        getQrCodeParams.put("scene", params);
        getQrCodeParams.put("page", QRCODE_PAGE);
        getQrCodeParams.put("width", 430);
        getQrCodeParams.put("is_hyaline", true);

        String outputPath = QRCODE_PATH + "/shopoutimages/";
        String fileName = shopId + ".jpg";
        String preShopQrCodePath = QRCODE_PATH + "/shopqrcode/";
        String shopQrCodePath = preShopQrCodePath + fileName;
        File file = new File(shopQrCodePath);
        // 返回给前端的相对路径
        String shopQrCodePathReturn = "";
        if (file.exists() && file.length() > 1000) {
            shopQrCodePathReturn = shopQrCodePath;
            System.out.println("不重新生成小程序二维码: " + shopQrCodePathReturn);
            LOG.info("不重新生成小程序二维码：" + shopQrCodePathReturn);
        } else {
            // 返回给前端的相对路径
            shopQrCodePathReturn = HttpPostUtils.getQrCode(getQrCodeParams, getQrCodeUrl, preShopQrCodePath, fileName);
            System.out.println("重新生成小程序二维码: " + shopQrCodePathReturn);
            LOG.info("重新生成小程序二维码：" + shopQrCodePathReturn);
        }

        String backgroundPic = QRCODE_PATH + "/shopbpic/" + SHOP_QRCODE_BACKGROUP_PIC;
        List<CompoundImageInfo> compoundImageInfoList = Arrays.asList(new CompoundImageInfo(shopQrCodePathReturn, 138, 445, 144, 144));
        String resultImageFileName = ImageUtils.compoundPirtureAndFont(backgroundPic, outputPath, fileName, new ArrayList<>(), compoundImageInfoList);

        return "/shopoutimages/" + resultImageFileName;

    }

    @Override
    public ShopView getRefereesShop(Long userId) {
        ShopView shopView = new ShopView();
        Boolean haveShop = false;
        if (!ObjectUtils.isEmpty(shopDao.findShopByUserId(userId))) {
            Shop shop = shopDao.findShopByUserId(userId);
            Long shopId = shop.getId();
            ShopTree shopTree = shopTreeDao.findByShopId(shopId);
            // 推荐人店铺
            if (!ObjectUtils.isEmpty(shopTreeDao.findByShopId(shopTree.getParentShopId()))) {
                Long parentShopId = shopTree.getParentShopId();
                Shop pShop = shopDao.findById(parentShopId).get();
                if (pShop.getEnabled() == 0) {
                    if (shop.getEnabled() == 0) {

                    } else {
                        //当前店铺
                        TorinoSrcBeanUtils.copyBean(shop, shopView);
                        haveShop = true;
                    }
                } else {
                    //推荐人店铺
                    TorinoSrcBeanUtils.copyBean(pShop, shopView);
                    haveShop = true;
                }
            } else {
            }
        }
        shopView.setHaveShop(haveShop);
        return shopView;
    }

}
