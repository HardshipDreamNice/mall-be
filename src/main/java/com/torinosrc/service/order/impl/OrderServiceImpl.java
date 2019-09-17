/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.order.impl;

import com.torinosrc.commons.constants.MallConstant;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.DateUtils;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.commons.utils.message.MessageCode;
import com.torinosrc.dao.boostproductdetail.BoostProductDetailDao;
import com.torinosrc.dao.globalconfig.GlobalConfigDao;
import com.torinosrc.dao.membershipgrade.MembershipGradeDao;
import com.torinosrc.dao.merchantconsignee.MerchantConsigneeDao;
import com.torinosrc.dao.order.OrderDao;
import com.torinosrc.dao.orderdetail.OrderDetailDao;
import com.torinosrc.dao.product.ProductDao;
import com.torinosrc.dao.productdetail.ProductDetailDao;
import com.torinosrc.dao.productdetailsnapshot.ProductDetailSnapshotDao;
import com.torinosrc.dao.productfreereceive.ProductFreeReceiveDao;
import com.torinosrc.dao.productfreereceivedetail.ProductFreeReceiveDetailDao;
import com.torinosrc.dao.productsnapshot.ProductSnapshotDao;
import com.torinosrc.dao.shop.ShopDao;
import com.torinosrc.dao.shopaccountdetail.ShopAccountDetailDao;
import com.torinosrc.dao.shoppingcart.ShoppingCartDao;
import com.torinosrc.dao.shoppingcartdetail.ShoppingCartDetailDao;
import com.torinosrc.dao.shopproduct.ShopProductDao;
import com.torinosrc.dao.shopproductdetail.ShopProductDetailDao;
import com.torinosrc.dao.shopproductdetailsnapshot.ShopProductDetailSnapshotDao;
import com.torinosrc.dao.shopproductsnapshot.ShopProductSnapshotDao;
import com.torinosrc.dao.team.TeamDao;
import com.torinosrc.dao.teamuser.TeamUserDao;
import com.torinosrc.dao.userproductfreereceive.UserProductFreeReceiveDao;
import com.torinosrc.model.entity.globalconfig.GlobalConfig;
import com.torinosrc.model.entity.membershipgrade.MembershipGrade;
import com.torinosrc.model.entity.merchantconsignee.MerchantConsignee;
import com.torinosrc.model.entity.order.Order;
import com.torinosrc.model.entity.orderdetail.OrderDetail;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.entity.productdetailprice.ProductDetailPrice;
import com.torinosrc.model.entity.productdetailsnapshot.ProductDetailSnapshot;
import com.torinosrc.model.entity.productfreereceive.ProductFreeReceive;
import com.torinosrc.model.entity.productfreereceivedetail.ProductFreeReceiveDetail;
import com.torinosrc.model.entity.productsnapshot.ProductSnapshot;
import com.torinosrc.model.entity.shop.Shop;
import com.torinosrc.model.entity.shoppingcart.ShoppingCart;
import com.torinosrc.model.entity.shopproduct.ShopProduct;
import com.torinosrc.model.entity.shopproductdetail.ShopProductDetail;
import com.torinosrc.model.entity.shopproductdetailsnapshot.ShopProductDetailSnapshot;
import com.torinosrc.model.entity.shopproductsnapshot.ShopProductSnapshot;
import com.torinosrc.model.entity.team.Team;
import com.torinosrc.model.entity.teamuser.TeamUser;
import com.torinosrc.model.entity.userproductfreereceive.UserProductFreeReceive;
import com.torinosrc.model.view.boostteam.BoostTeamView;
import com.torinosrc.model.view.membershipgrade.MembershipGradeView;
import com.torinosrc.model.view.order.GroupOrderView;
import com.torinosrc.model.view.order.MembershipOrderView;
import com.torinosrc.model.view.order.OrderPageView;
import com.torinosrc.model.view.order.OrderView;
import com.torinosrc.model.view.orderdetail.OrderDetailView;
import com.torinosrc.model.view.userboostteam.UserBoostTeamView;
import com.torinosrc.service.boostteam.BoostTeamService;
import com.torinosrc.service.order.OrderService;
import com.torinosrc.service.shopaccountdetail.ShopAccountDetailService;
import com.torinosrc.service.userboostteam.UserBoostTeamService;
import com.torinosrc.service.weixin.WechatService;
import net.sf.json.JSONObject;
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
 * <b><code>OrderImpl</code></b>
 * <p/>
 * Order的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-06 11:31:03.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Transactional(rollbackOn = {Exception.class})
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDetailDao productDetailDao;

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    @Autowired
    private ShoppingCartDetailDao shoppingCartDetailDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private MerchantConsigneeDao merchantConsigneeDao;

    @Autowired
    private ShopProductSnapshotDao shopProductSnapshotDao;

    @Autowired
    private ShopProductDetailSnapshotDao shopProductDetailSnapshotDao;

    @Autowired
    private ProductDetailSnapshotDao productDetailSnapshotDao;

    @Autowired
    private ProductSnapshotDao productSnapshotDao;

    @Autowired
    private ShopProductDao shopProductDao;

    @Autowired
    private ShopProductDetailDao shopProductDetailDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private TeamUserDao teamUserDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private WechatService wechatService;

    @Autowired
    private GlobalConfigDao globalConfigDao;

    @Autowired
    private MembershipGradeDao membershipGradeDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private ProductFreeReceiveDetailDao productFreeReceiveDetailDao;

    @Autowired
    private UserProductFreeReceiveDao userProductFreeReceiveDao;

    @Autowired
    private BoostProductDetailDao boostProductDetailDao;

    @Autowired
    private BoostTeamService boostTeamService;

    @Autowired
    private UserBoostTeamService userBoostTeamService;

    @Autowired
    private ProductFreeReceiveDao productFreeReceiveDao;

    @Autowired
    private ShopAccountDetailDao shopAccountDetailDao;

    @Autowired
    private ShopAccountDetailService shopAccountDetailService;


    @Override
    public OrderView getEntity(long id) {
        // 获取Entity
        Order order = orderDao.getOne(id);
        // 复制Dao层属性到view属性
        OrderView orderView = new OrderView();
        TorinoSrcBeanUtils.copyBean(order, orderView);

        if (order.getOrderType().intValue() == MallConstant.ORDER_TYPE_TEAM && order.getTeamId().longValue() != 0) {
            if (this.checkOrderTeamCount(order)) {
                orderView.setOrderSpellTeamStatus(1);
            } else {
                orderView.setOrderSpellTeamStatus(0);
            }
        } else {
            orderView.setOrderSpellTeamStatus(0);
        }

        Integer orderType = order.getOrderType();
        if (!ObjectUtils.isEmpty(order.getOrderDetails())) {
            List<OrderDetail> orderDetailList = new ArrayList<>(order.getOrderDetails());
            List<OrderDetailView> orderDetailViews = new ArrayList<>();
            for (OrderDetail orderDetail : orderDetailList) {
                OrderDetailView orderDetailView = new OrderDetailView();
                // 如果普通订单或拼团订单，将商品详情设置为空
//                if (orderType.intValue() == MallConstant.ORDER_TYPE_GENERAL.intValue() || orderType.intValue() == MallConstant.ORDER_TYPE_TEAM.intValue()) {
                orderDetailView = dealOrderDetail(orderDetail, orderDetailView);
//                } else {
//                    orderDetailView.setFromShopProductDetail(false);
//                }
                orderDetailView.setCount(orderDetail.getCount());
                orderDetailViews.add(orderDetailView);
            }
            orderView.setOrderDetailViews(orderDetailViews);
        }
        return orderView;
    }


    /**
     * 将 orderDetail 中的 productSnapshot 设置为空
     * @param orderDetail
     * @param orderDetailView
     * @return
     */
    private OrderDetailView dealOrderDetail(OrderDetail orderDetail, OrderDetailView orderDetailView) {
        // 如果 ShopProductDetailSnapshotId 不为空，查询 shopProductDetailSnapshot 并将 productSnapshot 中的规格和描述致空
        if (!ObjectUtils.isEmpty(orderDetail.getShopProductDetailSnapshotId())) {
            ShopProductDetailSnapshot shopProductDetailSnapshot = shopProductDetailSnapshotDao.findById(orderDetail.getShopProductDetailSnapshotId()).get();
            shopProductDetailSnapshot.getProductDetailSnapshot().getProductSnapshot().setDescription("");
            shopProductDetailSnapshot.getProductDetailSnapshot().getProductSnapshot().setSizeDesc("");
            //标志位： 证明是有shopproductId
            orderDetailView.setFromShopProductDetail(true);
            orderDetailView.setShopProductDetailSnapshot(shopProductDetailSnapshot);
        } else {
            // 查询 productDetailSnapshot 并将 productSnapshot 中的规格和描述致空
            ProductDetailSnapshot productDetailSnapshot = productDetailSnapshotDao.findById(orderDetail.getProductDetailSnapshotId()).get();
            productDetailSnapshot.getProductSnapshot().setDescription("");
            productDetailSnapshot.getProductSnapshot().setSizeDesc("");
            orderDetailView.setFromShopProductDetail(false);

            // 拼团流程的快照表情况
            Order order=orderDao.findById(orderDetail.getOrderId()).get();
            Integer orderType=order.getOrderType();
            if(orderType==MallConstant.PRODUCT_TYPE_TEAM||orderType==MallConstant.PRODUCT_TYPE_GENERAL_AND_TEAM){
                productDetailSnapshot=this.setProductDetailSnapshot(productDetailSnapshot);
            }else {
                //nothing to do
            }

            orderDetailView.setProductDetailSnapshot(productDetailSnapshot);
        }
        return orderDetailView;
    }

    // 拼团快照价格(拼团价覆盖价格)
    private ProductDetailSnapshot setProductDetailSnapshot(ProductDetailSnapshot productDetailSnapshot){
        productDetailSnapshot.setPrice(productDetailSnapshot.getTeamPrice());
        return productDetailSnapshot;
    }

    @Override
    public Page<OrderView> getEntitiesByParms(OrderView orderView, int currentPage, int pageSize) {
        Specification<Order> orderSpecification = new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root, criteriaQuery, criteriaBuilder, orderView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Order> orders = orderDao.findAll(orderSpecification, pageable);

        // 转换成View对象并返回
        return orders.map(order -> {
            OrderView orderView1 = new OrderView();
            TorinoSrcBeanUtils.copyBean(order, orderView1);
            return orderView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return orderDao.count();
    }

    @Override
    public List<OrderView> findAll() {
        List<OrderView> orderViews = new ArrayList<>();
        List<Order> orders = orderDao.findAll();
        for (Order order : orders) {
            OrderView orderView = new OrderView();
            TorinoSrcBeanUtils.copyBean(order, orderView);
            orderViews.add(orderView);
        }
        return orderViews;
    }

    @Transactional(rollbackOn = {Exception.class})
    @Override
    public OrderView saveEntity(OrderView orderView) throws TorinoSrcServiceException {
        //订单确定前业务逻辑
        // 1、库存是否足够
        // 2、总价是否正确
        long tolPrice = 0;
        //获取了用户的购物车
        ShoppingCart shoppingCart = shoppingCartDao.findByUserId(orderView.getUserId());
        //根据userId获取购物车id
        ShoppingCart shopCart = shoppingCartDao.findByUserId(orderView.getUserId());

        Set<OrderDetail> orderDetails = orderView.getOrderDetails();

        for (OrderDetail orderDetail : orderDetails) {
            // 移除购物车对应的商品
            Long productDetailId = orderDetail.getProductDetailId();
            //产品从购物车移除
            shoppingCartDetailDao.removeShopCartDetailById(orderDetail.getShoppingCartDetailId());
            // 判断库存是否足够并且减少库存
            this.checkAndDecreaseInvetory(orderDetail.getProductDetailId(), orderDetail.getCount());
            //如果是有shopProductId的情况
            if (!ObjectUtils.isEmpty(orderDetail.getShopProductDetailId())) {
                orderDetail = this.createOrderDetailWithShopProductDetailIdForSnapShot(orderDetail, orderView.getShopId());
                tolPrice += orderDetail.getCount() * orderDetail.getPrice();
            } else {
                orderDetail = createOrderDetailNullWithShopProductDetailIdForSnapShot(orderDetail);
                tolPrice += orderDetail.getCount() * orderDetail.getPrice();
            }

        }

        if (orderView.getTotalFee() != tolPrice) {
            throw new TorinoSrcServiceException("下单失败");
        }

        // 保存的业务逻辑
        Order order = new Order();
        TorinoSrcBeanUtils.copyBean(orderView, order);
        // user数据库映射传给dao进行存储
        order.setOrderDetails(new HashSet<>());
        order.setCreateTime(System.currentTimeMillis());
        order.setUpdateTime(System.currentTimeMillis());
        order.setOrderNo(System.currentTimeMillis() + "" + Math.round(Math.random() * 100000));
        order.setEnabled(1);
        order.setTotalFee(Integer.parseInt(tolPrice + ""));
        order.setShopId(orderView.getShopId());
        order.setOrderType(0);
        order.setLastTotalFee(Integer.parseInt(tolPrice + ""));
        orderDao.save(order);

        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setCreateTime(System.currentTimeMillis());
            orderDetail.setUpdateTime(System.currentTimeMillis());
            orderDetail.setOrderId(order.getId());
            orderDetailDao.save(orderDetail);
        }
        TorinoSrcBeanUtils.copyBean(order, orderView);
        return orderView;
    }

    // 创建拼团下单
    @Transactional(rollbackOn = {Exception.class})
    @Override
    public GroupOrderView spellGroupOrder(GroupOrderView groupOrderView) throws TorinoSrcServiceException {
//        System.out.println(JSON.toJSONString(groupOrderView));

        Long teamId = 0L;

        OrderDetailView orderDetailView = groupOrderView.getOrderDetailView();

        Long productDetailId = orderDetailView.getProductDetailId();

        ProductDetail productDetail = productDetailDao.findById(productDetailId).get();

        Long productId = productDetail.getProductId();

        Product product = productDao.findById(productId).get();

        this.checkProductExpiredTime(productId);

        GroupOrderView orderView1 = this.saveOrderAndOrderDetail(groupOrderView);

        return orderView1;
    }

    // 加入拼团下单
    @Transactional(rollbackOn = {Exception.class})
    @Override
    public GroupOrderView addGroupOrder(GroupOrderView groupOrderView) {

        Long teamId = groupOrderView.getTeamId();

        OrderDetailView orderDetailView = groupOrderView.getOrderDetailView();
        Long productDetailId = orderDetailView.getProductDetailId();
        ProductDetail productDetail = productDetailDao.findById(productDetailId).get();
        Long productId = productDetail.getProductId();

        this.checkTeamExpiredTime(teamId);

        this.checkCount(teamId);

        Long userId = groupOrderView.getUserId();

        this.checkTeam(teamId, userId);

        GroupOrderView orderView1 = this.saveOrderAndOrderDetail(groupOrderView);

        return orderView1;
    }

    // 检测人员是否在团
    public void checkTeam(Long teamId, Long userId) {
        List<TeamUser> teamUsers = teamUserDao.findByTeamId(teamId);
        for (TeamUser teamUser : teamUsers) {
            if (teamUser.getUserId().longValue() == userId.longValue()) {
                throw new TorinoSrcServiceException("用户已在团啦！");
            } else {

            }
        }
    }

    // 团队人员（团员）
    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void saveTeamUser(Long teamId, Long userId, Long productId) {

        if (this.checkSuccessGroup(teamId)) {
            Product product = productDao.findById(productId).get();
            product.setCount(product.getCount() + 1);
            productDao.save(product);
        }

        TeamUser teamUser = new TeamUser();
        teamUser.setCreateTime(System.currentTimeMillis());
        teamUser.setUpdateTime(System.currentTimeMillis());
        teamUser.setTeamId(teamId);
        teamUser.setUserId(userId);
        teamUser.setType(0);

        teamUserDao.save(teamUser);
    }

    // 创建团队（团长）
    @Override
    @Transactional(rollbackOn = {Exception.class})
    public Long saveTeam(Long productId, Long userId) {

        Product product = productDao.findById(productId).get();

        GlobalConfig globalConfig = globalConfigDao.findByKey(MallConstant.TEAM_TIME);

        Long value = Long.valueOf(globalConfig.getValue());

        String expiredTime = "";

        Long currentTime = System.currentTimeMillis();

        currentTime = currentTime + value;

        expiredTime = DateUtils.timeStamp2Date(currentTime.toString(), "yyyy-MM-dd HH:mm:ss");

        Team team = new Team();
        team.setCreateTime(System.currentTimeMillis());
        team.setUpdateTime(System.currentTimeMillis());
        team.setExpiredTime(expiredTime);
        team.setProductId(productId);
        team.setProductName(product.getName());
        team.setCount(product.getMemberCount());
        teamDao.save(team);

        Long teamId = team.getId();

        TeamUser teamUser = new TeamUser();
        teamUser.setCreateTime(System.currentTimeMillis());
        teamUser.setUpdateTime(System.currentTimeMillis());
        teamUser.setUserId(userId);
        teamUser.setTeamId(teamId);
        teamUser.setType(1);
        teamUserDao.save(teamUser);

        return teamId;
    }


    // 拼团人数检测
    private void checkCount(Long teamId) {
        Team team = teamDao.findById(teamId).get();
        Integer count = team.getCount();
        Integer teamUserCount = teamUserDao.countByTeamId(teamId);
        if (teamUserCount >= count) {
            throw new TorinoSrcServiceException("此团人数已满啦！");
        }
    }

    // 团队过期时间检测
    private void checkTeamExpiredTime(Long teamId) {
        Team team = teamDao.findById(teamId).get();
        Long currentTime = System.currentTimeMillis();
        Long expiredTime = DateUtils.StrToDate(team.getExpiredTime(), "yyyy-MM-dd HH:mm:ss").getTime();
        if (currentTime > expiredTime) {
            throw new TorinoSrcServiceException("拼团时间已到，请重新开团啦！");
        }
    }

    // 商品过期时间检测
    private void checkProductExpiredTime(Long productId) {
        Product product = productDao.findById(productId).get();
        Long currentTime = System.currentTimeMillis();
        Long expiredTime = DateUtils.StrToDate(product.getExpiredTime(), "yyyy-MM-dd HH:mm:ss").getTime();
        if (currentTime > expiredTime) {
            throw new TorinoSrcServiceException("拼团时间已到，请重新开团啦！");
        }
    }

    // 保存订单与订单明细
    @Transactional(rollbackOn = {Exception.class})
    public GroupOrderView saveOrderAndOrderDetail(GroupOrderView orderView) {

        OrderDetail orderDetail = new OrderDetail();
        TorinoSrcBeanUtils.copyBean(orderView.getOrderDetailView(), orderDetail);

        //判断库存是否足够并且减少库存
        this.checkAndDecreaseInvetory(orderDetail.getProductDetailId(), orderDetail.getCount());

        long tolPrice = 0L;

        orderDetail = createGroupOrderDetailNullWithShopProductDetailIdForSnapShot(orderDetail);

        // TODO: 需要根据 type 获取商品价格
        Long productDetailId = orderDetail.getProductDetailId();
        ProductDetail productDetail = productDetailDao.findById(productDetailId).get();
        Long teamPrice = productDetail.getProductDetailPrice().getTeamPrice();

        tolPrice += orderDetail.getCount() * teamPrice;

        if (orderView.getTotalFee() != tolPrice) {
            throw new TorinoSrcServiceException("下单失败");
        }

        // 保存的业务逻辑
        Order order = new Order();
        TorinoSrcBeanUtils.copyBean(orderView, order);
        // user数据库映射传给dao进行存储
        order.setOrderDetails(new HashSet<>());
        order.setCreateTime(System.currentTimeMillis());
        order.setUpdateTime(System.currentTimeMillis());
        order.setOrderNo(System.currentTimeMillis() + "" + Math.round(Math.random() * 100000));
        order.setEnabled(1);
        order.setTotalFee(Integer.parseInt(tolPrice + ""));
        order.setShopId(orderView.getShopId());
        order.setOrderType(1);
        order.setLastTotalFee(Integer.parseInt(tolPrice + ""));
        orderDao.save(order);


        orderDetail.setCreateTime(System.currentTimeMillis());
        orderDetail.setUpdateTime(System.currentTimeMillis());
        orderDetail.setOrderId(order.getId());
        orderDetailDao.save(orderDetail);

        TorinoSrcBeanUtils.copyBean(order, orderView);

        return orderView;
    }

    // 检测是否准备人数达到成团人数(设置已团数)
    public Boolean checkSuccessGroup(Long teamId) {
        Boolean isSuccessGroup = false;
        Team team = teamDao.findById(teamId).get();
        Integer teamUserCount = teamUserDao.countByTeamId(teamId);
        Product product;
        if (team.getCount() == teamUserCount + 1) {
            isSuccessGroup = true;
        }

        return isSuccessGroup;
    }

    // 成团失败检测
    @Override
    public void checkFailGroup() {
        Long currentTime = System.currentTimeMillis();

        Long sTime = currentTime - MallConstant.CHECK_REFUND_MS;
        Long eTime = currentTime;

        List<Team> teams = teamDao.findByExpiredTimeLong(sTime, eTime);
        Integer teamUserCount;
        Long teamId;
        for (Team team : teams) {
            teamId = team.getId();
            teamUserCount = teamUserDao.countByTeamId(teamId);
            if (teamUserCount < team.getCount()) {
                this.refundByOrderId(teamId);
            }
        }
    }

    // 退款，删除订单，删除团队，删除成员
    @Transactional(rollbackOn = {Exception.class})
    public void refundByOrderId(Long teamId) {
        List<Order> orders = orderDao.findByTeamId(teamId);
        Long orderId;
        OrderView orderView;
        for (Order order : orders) {
            orderId = order.getId();
            JSONObject result = wechatService.applyRefund(order.getId());
            if ("success".equals(result.getString("status"))) {
                orderDao.updateOrderStatus(teamId);
                orderDetailDao.updateOrderDetailEnable(orderId);
                teamDao.updateTeamEnable(teamId);
                teamUserDao.updateTeamUserEnable(teamId);
                orderView=new OrderView();
                orderView.setId(orderId);
                this.refundThenAddInventory(orderView);
            }
        }
    }

    // 调起支付订单判断
    @Override
    public void checkOrder(Long orderId) {

        Order order = orderDao.findById(orderId).get();

        if (order.getOrderType() == MallConstant.ORDER_TYPE_TEAM && order.getStatus() == 0) {

            Set<OrderDetail> orderDetails = order.getOrderDetails();
            List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>(orderDetails);
            OrderDetail orderDetail = orderDetailList.get(0);
            Long productDetailId = orderDetail.getProductDetailId();
            ProductDetail productDetail = productDetailDao.findById(productDetailId).get();
            Long productId = productDetail.getProduct().getId();

            Long teamId = order.getTeamId();
            if (teamId != 0) {
                //加入拼团
                this.checkTeamExpiredTime(teamId);
                this.checkCount(teamId);

            } else {
                //创建拼团
                this.checkProductExpiredTime(productId);
            }
        }
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public OrderView saveMembershipOrderAndOrderDetail(MembershipOrderView membershipOrderView) {

        MembershipGradeView membershipGradView = membershipOrderView.getMembershipGradeView();
        Long userId = membershipOrderView.getUserId();
        Long shopId = membershipOrderView.getShopId();
        Long price = membershipGradView.getPrice();
        Long membershipGradeId = membershipGradView.getId();
        MembershipGrade membershipGrad = membershipGradeDao.findById(membershipGradeId).get();
        if (membershipGrad.getPrice().longValue() != price.longValue()) {
            throw new TorinoSrcServiceException("价格不对，无法进行购买操作！");
        }

        // 保存快照
        Long currentTimeMillis = System.currentTimeMillis();
        ProductSnapshot productSnapshot = new ProductSnapshot();
        TorinoSrcBeanUtils.copyBean(membershipGrad, productSnapshot);
        productSnapshot.setProductType(MallConstant.PRODUCT_SNAPSHOT_PRODUCT_TYPE_MEMBER);
        productSnapshot.setMembershipGradeId(membershipGradeId);
        productSnapshot.setCreateTime(currentTimeMillis);
        productSnapshot.setUpdateTime(currentTimeMillis);
        productSnapshot = productSnapshotDao.save(productSnapshot);

        ProductDetailSnapshot productDetailSnapshot = new ProductDetailSnapshot();
        productDetailSnapshot.setProductSnapshotId(productSnapshot.getId());
        productDetailSnapshot.setPrice(membershipGrad.getPrice());
        productDetailSnapshot.setCreateTime(currentTimeMillis);
        productDetailSnapshot.setUpdateTime(currentTimeMillis);
        productDetailSnapshot = productDetailSnapshotDao.save(productDetailSnapshot);


        // 保存订单
        Order order = new Order();
        order.setOrderDetails(new HashSet<>());
        order.setCreateTime(currentTimeMillis);
        order.setUpdateTime(currentTimeMillis);
        order.setOrderNo(System.currentTimeMillis() + "" + Math.round(Math.random() * 100000));
        order.setEnabled(1);
        order.setTotalFee(Integer.parseInt(price + ""));
        order.setShopId(shopId);
        order.setUserId(userId);
        // 订单类型 0：普通商品订单 1：拼团订单 2：购买会员
        order.setOrderType(MallConstant.ORDER_TYPE_MEMBER);
        orderDao.save(order);

        // 保存订单详情
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setPrice(price);
        orderDetail.setEnabled(1);
        orderDetail.setCount(1);
        orderDetail.setCreateTime(currentTimeMillis);
        orderDetail.setUpdateTime(currentTimeMillis);
        orderDetail.setOrderId(order.getId());
        orderDetail.setMembershipGradeId(membershipGradeId);
        orderDetail.setProductDetailSnapshotId(productDetailSnapshot.getId());
        orderDetailDao.save(orderDetail);

        OrderView orderView = new OrderView();
        TorinoSrcBeanUtils.copyBean(order, orderView);
        return orderView;
    }

    @Override
    public void upgradeShop(OrderView orderView) {
        LOG.info("进入 upgradeShop 方法");
        long userId = orderView.getUserId();
        Shop shop = shopDao.findShopByUserId(userId);

        int maxMemberLevel = 0;
        long maxMembershipGradeId = 0L;
        long orderId = orderView.getId();
        List<OrderDetail> orderDetails = orderDetailDao.findByOrderId(orderId);
        for (OrderDetail orderDetail : orderDetails) {
            long membershipGradeId = orderDetail.getMembershipGradeId();
            MembershipGrade membershipGrade = membershipGradeDao.getOne(membershipGradeId);
            int memberLevel = membershipGrade.getGrade();
            if (memberLevel > maxMemberLevel) {
                maxMemberLevel = memberLevel;
                maxMembershipGradeId = membershipGradeId;
            }
        }
        LOG.info("shoplevel: " + maxMemberLevel + " - " + shop.getShopLevel());
        if (maxMemberLevel > shop.getShopLevel()) {
            // 可升级
            shop.setMembershipGradeId(maxMembershipGradeId);
            shop.setShopLevel(maxMemberLevel);
            shopDao.save(shop);
            LOG.info("升级完成");
        } else {
            LOG.info("不可升级 " + maxMemberLevel + " - " + shop.getShopLevel());
        }
    }


    /**
     * 有shopProductDetail的情况
     */
    private OrderDetail createOrderDetailWithShopProductDetailIdForSnapShot(OrderDetail orderDetail, Long shopId) {

        System.out.println("orderDetail is null 1 ? " + orderDetail.getPrice() + "," + orderDetail.getShopProductDetailId());
        //获取了商品 Productdetail的实体类
        ProductDetail productDetailDb = productDetailDao.getOne(orderDetail.getProductDetailId());

        //获取商品的实体类 Product
        Product productDb = productDetailDb.getProduct();

        //商店里的商品shopProductDetail实体类
        ShopProductDetail shopProductDetailDb = shopProductDetailDao.getOne(orderDetail.getShopProductDetailId());

        //商店里的商品shopProduct实体类实体类
        ShopProduct shopProductDb = shopProductDetailDb.getShopProduct();

        //价格回填orderDetail
        if (ObjectUtils.isEmpty(shopProductDetailDb.getProductPrice())) {

            if (ObjectUtils.isEmpty(shopProductDetailDb.getAdvisePrice())) {

                orderDetail.setPrice(0L);
            } else {

                orderDetail.setPrice(Long.parseLong(shopProductDetailDb.getAdvisePrice().toString()));
            }

        } else {

            orderDetail.setPrice(Long.parseLong(shopProductDetailDb.getProductPrice().toString()));
        }

        //创建productSnapshot
        Long productSnapShotId = null;
        ProductSnapshot productSnapshotDb = productSnapshotDao.findByProductIdAndUpdateTime(productDb.getId(), productDb.getUpdateTime());
//        ProductSnapshot productSnapshotDb=productSnapshotDao.findByProductId(productDb.getId());
        if (ObjectUtils.isEmpty(productSnapshotDb)) {
            ProductSnapshot productSnapshot = new ProductSnapshot();
            TorinoSrcBeanUtils.copyBean(productDb, productSnapshot);
            productSnapshot.setProductId(productDb.getId());
            productSnapshot.setId(null);
            productSnapshot.setCreateTime(productDb.getCreateTime());
            productSnapshot.setUpdateTime(productDb.getUpdateTime());
            productSnapshotDao.save(productSnapshot);
            productSnapShotId = productSnapshot.getId();
        } else {

            productSnapShotId = productSnapshotDb.getId();
        }

        //创建 productDetailSnapshot

        Long productDetailSnapshotId = null;

        ProductDetailSnapshot productDetailSnapshotDb = productDetailSnapshotDao.findByProductDetailIdAndUpdateTime(productDetailDb.getId(), productDetailDb.getUpdateTime());

        if (ObjectUtils.isEmpty(productDetailSnapshotDb)) {

            ProductDetailSnapshot productDetailSnapshot = new ProductDetailSnapshot();
            TorinoSrcBeanUtils.copyBean(productDetailDb, productDetailSnapshot);
            productDetailSnapshot.setProductDetailId(productDetailDb.getId());
            productDetailSnapshot.setId(null);
            productDetailSnapshot.setCreateTime(productDetailDb.getCreateTime());
            productDetailSnapshot.setUpdateTime(productDetailDb.getUpdateTime());
            productDetailSnapshot.setProductSnapshotId(productSnapShotId);
            productDetailSnapshotDao.save(productDetailSnapshot);
            productDetailSnapshotId = productDetailSnapshot.getId();
        } else {

            productDetailSnapshotId = productDetailSnapshotDb.getId();
        }
        //保存到orderDetail
        orderDetail.setProductDetailSnapshotId(productDetailSnapshotId);

        Long shopProductSnapshotId = null;
        ShopProductSnapshot shopProductSnapshotDb = shopProductSnapshotDao.findByShopProductIdAndUpdateTime(shopProductDb.getId(), shopProductDb.getUpdateTime());
        if (ObjectUtils.isEmpty(shopProductSnapshotDb)) {

            ShopProductSnapshot shopProductSnapshot = new ShopProductSnapshot();
            TorinoSrcBeanUtils.copyBean(shopProductDb, shopProductSnapshot);
            shopProductSnapshot.setShopProductId(shopProductDb.getId());
            shopProductSnapshot.setId(null);
            shopProductSnapshot.setUpdateTime(shopProductDb.getUpdateTime());
            shopProductSnapshot.setCreateTime(shopProductDb.getCreateTime());
            shopProductSnapshot.setProductSnapshotId(productSnapShotId);
            shopProductSnapshot.setShopId(shopId);
            shopProductSnapshotDao.save(shopProductSnapshot);
            shopProductSnapshotId = shopProductSnapshot.getId();
        } else {

            shopProductSnapshotId = shopProductSnapshotDb.getId();
        }


        //创建shopProductDetailSnapshot
        Long shopProductDetailSnapshotId = null;

        ShopProductDetailSnapshot shopProductDetailSnapshotDb = shopProductDetailSnapshotDao.findByShopProductDetailIdAndUpdateTime(shopProductDetailDb.getId(), productDetailDb.getUpdateTime());

        if (ObjectUtils.isEmpty(shopProductDetailSnapshotDb)) {

            ShopProductDetailSnapshot shopProductDetailSnapshot = new ShopProductDetailSnapshot();
            TorinoSrcBeanUtils.copyBean(shopProductDetailDb, shopProductDetailSnapshot);
            shopProductDetailSnapshot.setShopProductDetailId(shopProductDetailDb.getId());
            shopProductDetailSnapshot.setId(null);
            shopProductDetailSnapshot.setUpdateTime(shopProductDetailDb.getUpdateTime());
            shopProductDetailSnapshot.setCreateTime(shopProductDetailDb.getCreateTime());
            shopProductDetailSnapshot.setProductDetailSnapshotId(productDetailSnapshotId);
            shopProductDetailSnapshot.setShopProductSnapshotId(shopProductSnapshotId);
            shopProductDetailSnapshotDao.save(shopProductDetailSnapshot);
            shopProductDetailSnapshotId = shopProductDetailSnapshot.getId();
        } else {
            shopProductDetailSnapshotId = shopProductDetailSnapshotDb.getId();
        }

        orderDetail.setShopProductDetailSnapshotId(shopProductDetailSnapshotId);
        System.out.println("orderDetail is null？ " + orderDetail);
        return orderDetail;
    }

    /**
     * 没有有shopProductDetail的情况
     */
    private OrderDetail createOrderDetailNullWithShopProductDetailIdForSnapShot(OrderDetail orderDetail) {


        //获取了商品 Productdetail的实体类
        ProductDetail productDetailDb = productDetailDao.getOne(orderDetail.getProductDetailId());

        //获取商品的实体类 Product
        Product productDb = productDetailDb.getProduct();

        //价格回填orderDetail
        if (ObjectUtils.isEmpty(productDetailDb.getPrice())) {

            orderDetail.setPrice(0L);

        } else {

            orderDetail.setPrice(productDetailDb.getPrice());
        }

        //创建productSnapshot
        Long productSnapShotId = 0L;
        ProductSnapshot productSnapshotDb = productSnapshotDao.findByProductIdAndUpdateTime(productDb.getId(), productDb.getUpdateTime());
//        ProductSnapshot productSnapshotDb=productSnapshotDao.findByProductId(productDb.getId());
        if (ObjectUtils.isEmpty(productSnapshotDb)) {
            ProductSnapshot productSnapshot = new ProductSnapshot();
            TorinoSrcBeanUtils.copyBean(productDb, productSnapshot);
            productSnapshot.setProductId(productDb.getId());
            productSnapshot.setId(null);
            productSnapshot.setCreateTime(productDb.getCreateTime());
            productSnapshot.setUpdateTime(productDb.getUpdateTime());
            productSnapshotDao.save(productSnapshot);
            productSnapShotId = productSnapshot.getId();
        } else {

            productSnapShotId = productSnapshotDb.getId();
        }

        //创建创建productDetailSnapshot

        Long productDetailSnapshotId = 0L;

        ProductDetailSnapshot productDetailSnapshotDb = productDetailSnapshotDao.findByProductDetailIdAndUpdateTime(productDetailDb.getId(), productDetailDb.getUpdateTime());

        if (ObjectUtils.isEmpty(productDetailSnapshotDb)) {

            ProductDetailSnapshot productDetailSnapshot = new ProductDetailSnapshot();
            TorinoSrcBeanUtils.copyBean(productDetailDb, productDetailSnapshot);
            productDetailSnapshot.setProductDetailId(productDetailDb.getId());
            productDetailSnapshot.setId(null);
            productDetailSnapshot.setCreateTime(productDetailDb.getCreateTime());
            productDetailSnapshot.setUpdateTime(productDetailDb.getUpdateTime());
            productDetailSnapshot.setProductSnapshotId(productSnapShotId);
            productDetailSnapshotDao.save(productDetailSnapshot);
            productDetailSnapshotId = productDetailSnapshot.getId();
        } else {

            productDetailSnapshotId = productDetailSnapshotDb.getId();
        }
        //保存到orderDetail
        orderDetail.setProductDetailSnapshotId(productDetailSnapshotId);

        return orderDetail;
    }

    /**
     * 拼团流程快照保存
     */
    private OrderDetail createGroupOrderDetailNullWithShopProductDetailIdForSnapShot(OrderDetail orderDetail) {


        //获取了商品 Productdetail的实体类
        ProductDetail productDetailDb = productDetailDao.getOne(orderDetail.getProductDetailId());

        //获取商品的实体类 Product
        Product productDb = productDetailDb.getProduct();

        //价格回填orderDetail
        if (ObjectUtils.isEmpty(productDetailDb.getPrice())) {

            orderDetail.setPrice(0L);

        } else {

            orderDetail.setPrice(productDetailDb.getPrice());
        }

        //创建productSnapshot
        Long productSnapShotId = 0L;
        ProductSnapshot productSnapshotDb = productSnapshotDao.findByProductIdAndUpdateTime(productDb.getId(), productDb.getUpdateTime());
//        ProductSnapshot productSnapshotDb=productSnapshotDao.findByProductId(productDb.getId());
        if (ObjectUtils.isEmpty(productSnapshotDb)) {
            ProductSnapshot productSnapshot = new ProductSnapshot();
            TorinoSrcBeanUtils.copyBean(productDb, productSnapshot);
            productSnapshot.setProductId(productDb.getId());
            productSnapshot.setId(null);
            productSnapshot.setCreateTime(productDb.getCreateTime());
            productSnapshot.setUpdateTime(productDb.getUpdateTime());
            productSnapshotDao.save(productSnapshot);
            productSnapShotId = productSnapshot.getId();
        } else {

            productSnapShotId = productSnapshotDb.getId();
        }

        //创建创建productDetailSnapshot

        Long productDetailSnapshotId = 0L;

        ProductDetailSnapshot productDetailSnapshotDb = productDetailSnapshotDao.findByProductDetailIdAndUpdateTime(productDetailDb.getId(), productDetailDb.getUpdateTime());

        if (ObjectUtils.isEmpty(productDetailSnapshotDb)) {

            ProductDetailSnapshot productDetailSnapshot = new ProductDetailSnapshot();
            TorinoSrcBeanUtils.copyBean(productDetailDb, productDetailSnapshot);
            productDetailSnapshot.setProductDetailId(productDetailDb.getId());
            productDetailSnapshot.setId(null);
            productDetailSnapshot.setCreateTime(productDetailDb.getCreateTime());
            productDetailSnapshot.setUpdateTime(productDetailDb.getUpdateTime());
            productDetailSnapshot.setProductSnapshotId(productSnapShotId);

            // 拼团价
            Long teamPrice = productDetailDb.getProductDetailPrice().getTeamPrice();
            productDetailSnapshot.setTeamPrice(teamPrice);

            productDetailSnapshotDao.save(productDetailSnapshot);
            productDetailSnapshotId = productDetailSnapshot.getId();
        } else {

            productDetailSnapshotId = productDetailSnapshotDb.getId();
        }
        //保存到orderDetail
        orderDetail.setProductDetailSnapshotId(productDetailSnapshotId);

        return orderDetail;
    }

    /**
     * 检查并且减少库存
     *
     * @param productDetailId
     * @param saleCount
     */
    private void checkAndDecreaseInvetory(Long productDetailId, int saleCount) {

        ProductDetail productDetail = productDetailDao.getOne(productDetailId);

        if (productDetail.getInventory() < saleCount) {
            throw new TorinoSrcServiceException(productDetail.getProduct().getName() + "库存不足", MessageCode.INSUFFICIENT_INVENTORY);
        } else {
            productDetail.setInventory(productDetail.getInventory() - saleCount);
            productDetail.setUpdateTime(System.currentTimeMillis());
            productDetailDao.save(productDetail);
        }
    }


    @Override
    public void deleteEntity(long id) {
//        Order order = new Order();
//        order.setId(id);
//        orderDao.delete(order);
        orderDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteEntities(String ids) {
        String[] entityIds = TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        //List<Order> orders = new ArrayList<>();
        for (String entityId : entityIds) {
//            Order order = new Order();
//            order.setId(Long.valueOf(entityId));
//            orders.add(order);
            orderDao.deleteById(Long.valueOf(entityId));
        }
//        orderDao.deleteAll(orders);
    }

    @Override
    public void updateEntity(OrderView orderView) {
        Specification<Order> orderSpecification = Optional.ofNullable(orderView).map(s -> {
            return new Specification<Order>() {
                @Override
                public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(() -> new IllegalArgumentException("OrderView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<Order> orderOptionalBySearch = orderDao.findOne(orderSpecification);
        orderOptionalBySearch.map(orderBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(orderView, orderBySearch);
            orderBySearch.setUpdateTime(System.currentTimeMillis());
            orderDao.save(orderBySearch);

//            if (orderView.getStatus() == MallConstant.)

            return "";
        }).orElseThrow(() -> new TorinoSrcServiceException("无法找到id为" + orderView.getId() + "的数据记录"));
    }

    @Override
    public void updateEntityStatus(OrderView orderView) {
        Specification<Order> orderSpecification = Optional.ofNullable(orderView).map(s -> {
            return new Specification<Order>() {
                @Override
                public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(() -> new IllegalArgumentException("OrderView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<Order> orderOptionalBySearch = orderDao.findOne(orderSpecification);
        orderOptionalBySearch.map(orderBySearch -> {
            if (orderBySearch.getStatus() + 1 != orderView.getStatus()) {
                throw new TorinoSrcServiceException("更新的状态不符合要求", MessageCode.REFRESH_ORDER_STATUS);
            }
            TorinoSrcBeanUtils.copyBeanExcludeNull(orderView, orderBySearch);
            orderBySearch.setUpdateTime(System.currentTimeMillis());
            orderDao.save(orderBySearch);
            return "";
        }).orElseThrow(() -> new TorinoSrcServiceException("无法找到id为" + orderView.getId() + "的数据记录"));
    }

    @Override
    public OrderView findByOrderNo(String orderNo) {
        Order order = orderDao.findByOrderNo(orderNo);
        // 复制Dao层属性到view属性
        OrderView orderView = new OrderView();
        TorinoSrcBeanUtils.copyBean(order, orderView);
        LOG.warn("根据交易单号获取订单，映射：" + orderView.toString());
        return orderView;
    }

    @Override
    public OrderPageView findByUserIdAndstatus(Long userId, int status, int pageNumber, int pageSize) {
        OrderPageView orderPageView = new OrderPageView();
        List<OrderView> orderViews = new ArrayList<>();

        Specification<Order> orderSpecification = new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();

                if (!ObjectUtils.isEmpty(userId)) {
                    predicates.add(criteriaBuilder.equal(root.get("userId").as(Long.class), userId));
                }

                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("status"));
                //99代表全部
                if (status == 99) {
                    in.value(0);
                    in.value(1);
                    in.value(2);
                    in.value(3);
                    in.value(4);
                    in.value(5);
                    in.value(6);
                    in.value(7);
                    in.value(8);
                } else if (status == 4) {
                    in.value(4);
                    in.value(7);
                    in.value(8);
                } else {
                    in.value(status);
                }

                predicates.add(criteriaBuilder.and(in));

                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        // 设置分页
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Order> orderPages = orderDao.findAll(orderSpecification, pageable);
        List<Order> orders = orderPages.getContent();
        orderPageView.setTotalNum(orderPages.getTotalElements());

        for (Order order : orders) {
            // 0：普通订单 1：拼团订单 2：购买会员订单 3：免费领订单 4：助力购订单
            Integer orderType = order.getOrderType();

            // 设置商品详情为空
            List<OrderDetailView> orderDetailViews = new ArrayList<>();
            if (!ObjectUtils.isEmpty(order.getOrderDetails())) {
                for (OrderDetail orderDetail : order.getOrderDetails()) {
                    OrderDetailView orderDetailView = new OrderDetailView();
                    TorinoSrcBeanUtils.copyBean(orderDetail, orderDetailView);
                    // 如果是普通订单或拼团订单，设置商品详情为空
//                    if (orderType.intValue() == MallConstant.ORDER_TYPE_TEAM.intValue() || orderType.intValue() == MallConstant.ORDER_TYPE_GENERAL.intValue()) {
                    orderDetailView = dealOrderDetail(orderDetail, orderDetailView);
//                    } else {
//                        orderDetailView.setFromShopProductDetail(false);
//                    }
                    orderDetailViews.add(orderDetailView);
                }
            }
            OrderView orderView = new OrderView();
            TorinoSrcBeanUtils.copyBean(order, orderView);

            if (order.getOrderType().intValue() == MallConstant.ORDER_TYPE_TEAM&&order.getTeamId().longValue()!=0) {
                if (this.checkOrderTeamCount(order)) {
                    orderView.setOrderSpellTeamStatus(1);
                } else {
                    orderView.setOrderSpellTeamStatus(0);
                }
            } else {
                orderView.setOrderSpellTeamStatus(0);
            }

            // 商家地址
            MerchantConsignee merchantConsignee;
            // order退货信息
            if (!ObjectUtils.isEmpty(order.getReturnAddress()) && !ObjectUtils.isEmpty(order.getReturnName()) && !ObjectUtils.isEmpty(order.getReturnPhone())) {
                merchantConsignee = new MerchantConsignee();
                merchantConsignee.setAddress(order.getReturnAddress());
                merchantConsignee.setContact(order.getReturnName());
                merchantConsignee.setPhone(order.getReturnPhone());
                orderView.setMerchantConsignee(merchantConsignee);
            } else {
                merchantConsignee = merchantConsigneeDao.findById(1L).get();
                orderView.setMerchantConsignee(merchantConsignee);
            }
            orderView.setOrderDetailViews(orderDetailViews);
            orderViews.add(orderView);
        }

        orderPageView.setOrderViewList(orderViews);

        return orderPageView;
    }

    // 检测拼团订单对应的team是否满人
    private Boolean checkOrderTeamCount(Order order) {
        Boolean isCountReached = false;

        Long teamId = order.getTeamId();

        Team team = teamDao.getOne(teamId);
        Integer count = team.getCount();
        Integer teamUserCount = teamUserDao.countByTeamId(teamId);
        if (teamUserCount >= count) {
            isCountReached = true;
        }
        return isCountReached;
    }

    @Override
    public OrderPageView findByUserIdAndShopIdAndStatus(Long userId, Long shopId, int status, int pageNumber, int pageSize) {

        OrderPageView orderPageView = new OrderPageView();
        List<OrderView> orderViews = new ArrayList<>();

        Specification<Order> orderSpecification = new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();

                if (!ObjectUtils.isEmpty(shopId)) {
                    predicates.add(criteriaBuilder.equal(root.get("shopId").as(Long.class), shopId));
                }

                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("status"));
                //99代表全部
                if (status == 99) {
                    in.value(0);
                    in.value(1);
                    in.value(2);
                    in.value(3);
                    in.value(4);
                    in.value(5);
                    in.value(6);
                    in.value(7);
                    in.value(8);
                } else {
                    in.value(status);
                }

                predicates.add(criteriaBuilder.and(in));

                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        // 设置分页
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Order> orderPages = orderDao.findAll(orderSpecification, pageable);
        List<Order> orders = orderPages.getContent();
        orderPageView.setTotalNum(orderPages.getTotalElements());
        for (Order order : orders) {
            Integer orderType = order.getOrderType();

            //设置商品详情为空
            List<OrderDetailView> orderDetailViews = new ArrayList<>();
            if (!ObjectUtils.isEmpty(order.getOrderDetails())) {
                for (OrderDetail orderDetail : order.getOrderDetails()) {
                    OrderDetailView orderDetailView = new OrderDetailView();
                    TorinoSrcBeanUtils.copyBean(orderDetail, orderDetailView);
                    // 如果是普通订单或拼团订单，将商品详情设置为空
//                    if (orderType.intValue() == MallConstant.ORDER_TYPE_GENERAL || orderType.intValue() == MallConstant.ORDER_TYPE_TEAM) {
                        orderDetailView = dealOrderDetail(orderDetail, orderDetailView);
//                    } else {
//                        orderDetailView.setFromShopProductDetail(false);
//                    }
                    orderDetailViews.add(orderDetailView);
                }
            }

            OrderView orderView = new OrderView();
            TorinoSrcBeanUtils.copyBean(order, orderView);
            orderView.setOrderDetailViews(orderDetailViews);
            orderViews.add(orderView);
        }
        orderPageView.setOrderViewList(orderViews);
        return orderPageView;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public OrderView saveProductFreeReceiveOrder(OrderView orderView) {
        long totalPrice = 0;
        long productFreeReceiveDetailId = orderView.getProductFreeReceiveDetailId();
        ProductFreeReceiveDetail productFreeReceiveDetail = productFreeReceiveDetailDao.findById(productFreeReceiveDetailId).get();

        long currentTimeMillis = System.currentTimeMillis();
        if (productFreeReceiveDetail.getInventory() <= 0) {
            throw new TorinoSrcServiceException(productFreeReceiveDetail.getProductFreeReceive().getName() + "库存不足", MessageCode.INSUFFICIENT_INVENTORY);
        } else {
            // 减库存
            productFreeReceiveDetail.setInventory(productFreeReceiveDetail.getInventory() - 1);
            productFreeReceiveDetail.setUpdateTime(currentTimeMillis);
            productFreeReceiveDetailDao.save(productFreeReceiveDetail);
        }

        // 更新用户免费领状态
        UserProductFreeReceive userProductFreeReceive = userProductFreeReceiveDao.findOneByShareUserIdAndProductFreeReceiveId(orderView.getUserId(), productFreeReceiveDetailId);
        // 用户免费领状态 0：已分享免费领 1：已取得免费领资格 2：已下单
        userProductFreeReceive.setStatus(2);
        userProductFreeReceiveDao.save(userProductFreeReceive);

        // 保存快照信息
        Long productFreeReceiveId = productFreeReceiveDetail.getProductFreeReceiveId();
        ProductFreeReceive productFreeReceive = productFreeReceiveDao.getOne(productFreeReceiveId);
        ProductSnapshot productSnapshot = new ProductSnapshot();
        TorinoSrcBeanUtils.copyBean(productFreeReceive, productSnapshot);
        productSnapshot.setExpiredTime(DateUtils.timeStamp2Date(String.valueOf(productFreeReceive.getExpiredTime()), "yyyy-MM-dd HH:mm:ss"));
        productSnapshot.setProductType(MallConstant.PRODUCT_SNAPSHOT_PRODUCT_TYPE_TEAM);
        productSnapshot.setProductFreeReceiveId(productFreeReceiveId);
        productSnapshot.setCreateTime(currentTimeMillis);
        productSnapshot.setUpdateTime(currentTimeMillis);
        productSnapshot = productSnapshotDao.save(productSnapshot);

        ProductDetailSnapshot productDetailSnapshot = new ProductDetailSnapshot();
        TorinoSrcBeanUtils.copyBean(productFreeReceiveDetail, productDetailSnapshot);
        productDetailSnapshot.setProductFreeReceiveDetailId(productFreeReceiveDetailId);
        productDetailSnapshot.setProductSnapshotId(productSnapshot.getId());
        productDetailSnapshot.setCreateTime(currentTimeMillis);
        productDetailSnapshot.setUpdateTime(currentTimeMillis);
        productDetailSnapshot = productDetailSnapshotDao.save(productDetailSnapshot);

        // 保存订单
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductFreeReceiveDetailId(productFreeReceiveDetailId);
        orderDetail.setPrice(totalPrice);
        orderDetail.setCount(1);
        orderDetail.setProductDetailSnapshotId(productDetailSnapshot.getId());
        Set<OrderDetail> orderDetails = new HashSet<>();
        orderDetails.add(orderDetail);
        orderView.setOrderDetails(orderDetails);
        // 免费领下单不需要支付，因此默认状态为待发货
        orderView.setStatus(1);
        orderView.setOrderType(MallConstant.ORDER_TYPE_FREE);
        orderView = saveOrderCommon(orderView);

        return orderView;

    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public OrderView saveBoostOrder(OrderView orderView) {

        long totalPrice = 0;
        Long currentTimeMillis = System.currentTimeMillis();

        Set<OrderDetail> orderDetails = orderView.getOrderDetails();
        if (CollectionUtils.isEmpty(orderDetails)) {
            throw new TorinoSrcServiceException("请选择要购买的商品规格");
        }

        List<OrderDetail> orderDetailList = new ArrayList<>(orderDetails);
        OrderDetail orderDetail = orderDetailList.get(0);
        Long productDetailId = orderDetail.getProductDetailId();
        ProductDetail productDetailFromDB = productDetailDao.getOne(productDetailId);
        Integer buyCount = orderDetail.getCount();

        if (ObjectUtils.isEmpty(buyCount)) {
            throw new TorinoSrcServiceException("请选择要购买的数量");
        }
        if (StringUtils.isEmpty(orderView.getCustomerConsigneeString())) {
            throw new TorinoSrcServiceException("请选择收货地址");
        }

        // 判断库存是否足够并且减少库存
        if (productDetailFromDB.getInventory() < buyCount) {
            Product product = productDetailFromDB.getProduct();
            throw new TorinoSrcServiceException(product.getName() + "库存不足", MessageCode.INSUFFICIENT_INVENTORY);
        } else {
            productDetailFromDB.setInventory(productDetailFromDB.getInventory() - buyCount);
            productDetailFromDB.setUpdateTime(currentTimeMillis);
            productDetailDao.save(productDetailFromDB);
        }

        // 暂时只有一个价格，默认拿第一条记录
        List<ProductDetailPrice> productDetailPrices = productDetailFromDB.getProductDetailPriceList();
        long price = productDetailPrices.get(0).getPrice();
        totalPrice += price * buyCount;

        if (orderView.getTotalFee() != totalPrice) {
            throw new TorinoSrcServiceException("下单失败，订单价格有错或已更新");
        }

        // 保存快照信息
        ProductDetailSnapshot productDetailSnapshot = saveBoostSnapshot(productDetailFromDB);

        // 开团
        BoostTeamView boostTeamView = createBoostTeam(orderView.getUserId(),productDetailFromDB);
        // 成为第一个团员
        UserBoostTeamView userBoostTeamView = new UserBoostTeamView();
        userBoostTeamView.setBoostTeamId(boostTeamView.getId());
        userBoostTeamView.setUserId(orderView.getUserId());
        userBoostTeamService.saveOrReturnUserBoostTeam(userBoostTeamView);

        // 保存订单
        orderDetail.setProductDetailSnapshotId(productDetailSnapshot.getId());
        Set<OrderDetail> orderDetailNew = new HashSet<>();
        orderDetailNew.add(orderDetail);
        orderView.setOrderDetails(orderDetailNew);
        orderView.setOrderType(MallConstant.ORDER_TYPE_BOOST);
        orderView.setBoostTeamId(boostTeamView.getId());
        orderView = saveOrderCommon(orderView);

        return orderView;
    }

    /**
     * 保存助力购下单快照
     * @param productDetail
     * @return
     */
    private ProductDetailSnapshot saveBoostSnapshot(ProductDetail productDetail) {
        Long currentTimeMillis = System.currentTimeMillis();
        Long productDetailId = productDetail.getId();
        // 保存快照信息
        Product product = productDetail.getProduct();
        ProductSnapshot productSnapshot = new ProductSnapshot();
        TorinoSrcBeanUtils.copyBean(product, productSnapshot);
        productSnapshot.setExpiredTime(product.getExpiredTime());
        productSnapshot.setProductType(MallConstant.PRODUCT_SNAPSHOT_PRODUCT_TYPE_BOOST);
        productSnapshot.setProductId(product.getId());
        productSnapshot.setCreateTime(currentTimeMillis);
        productSnapshot.setUpdateTime(currentTimeMillis);
        productSnapshot = productSnapshotDao.save(productSnapshot);

        ProductDetailSnapshot productDetailSnapshot = new ProductDetailSnapshot();
        TorinoSrcBeanUtils.copyBean(productDetail, productDetailSnapshot);
        productDetailSnapshot.setProductDetailId(productDetailId);
        productDetailSnapshot.setProductSnapshotId(productSnapshot.getId());
        productDetailSnapshot.setCreateTime(currentTimeMillis);
        productDetailSnapshot.setUpdateTime(currentTimeMillis);
        productDetailSnapshot = productDetailSnapshotDao.save(productDetailSnapshot);
        return productDetailSnapshot;
    }

    /**
     * 助力购开团
     * @param productDetail
     */
    private BoostTeamView createBoostTeam(Long userId, ProductDetail productDetail) {

        Product product = productDetail.getProduct();

        BoostTeamView boostTeamView = new BoostTeamView();
        boostTeamView.setProductDetailId(productDetail.getId());
        boostTeamView.setProductId(product.getId());
        boostTeamView.setProductName(product.getName());
        boostTeamView.setBoostNumber(product.getBoostNumber());
        boostTeamView.setUserId(userId);

        Integer validDay = product.getValidDay();
        long currentTimeMillis = System.currentTimeMillis();
        long productExpiredTime = DateUtils.StrToDate(product.getExpiredTime(), "yyyy-MM-dd HH:mm:ss").getTime();
        if (currentTimeMillis > productExpiredTime) {
            throw new TorinoSrcServiceException("该商品已不在助力购活动期内");
        } else {
            // no need to do anything...
        }
        long oneDayMillis = 60000 * 60 * 24L;
        long teamExpiredTime = currentTimeMillis + validDay * oneDayMillis;
        if (productExpiredTime < teamExpiredTime) {
            boostTeamView.setExpiredTime(productExpiredTime);
        } else {
            boostTeamView.setExpiredTime(teamExpiredTime);
        }

        boostTeamView = boostTeamService.saveEntity(boostTeamView);

        return boostTeamView;
    }

    @Override
    public void closeUnpaidOrderAndRestoreInventory() {
        // 30 分钟的时间戮
        Long PAY_DEAD_LINE = 1800000L;

        // 根据没有支付状态查找对应的订单
        List<Order> orders = orderDao.findByStatus(0);
        Long currentDateTime = System.currentTimeMillis();

        for (Order order : orders) {
            // 如果当前时间大于updateTime 30分钟，则关闭订单
            Long timeSlap = currentDateTime - order.getUpdateTime();
            if (timeSlap > PAY_DEAD_LINE) {
                // 关闭交易
                order.setStatus(6);
                order.setUpdateTime(currentDateTime);
                orderDao.save(order);

                // 对应增加库存
                resotreInventory(order);
            } else {
                // no need to do anything...
            }
        }
    }

    /**
     * 关闭订单后恢复对应库存
     * @param order
     */
    private void resotreInventory(Order order) {
        List<OrderDetail> orderDetails = orderDetailDao.findByOrderId(order.getId());

        Long currentTimeMillis = System.currentTimeMillis();
        // 0：普通订单 1：拼团订单 2：购买会员订单 3：免费领订单 4：助力购订单
        Integer orderType = order.getOrderType();

        if (orderType.intValue() == MallConstant.ORDER_TYPE_TEAM || orderType.intValue() == MallConstant.ORDER_TYPE_GENERAL
                || orderType.intValue() == MallConstant.ORDER_TYPE_BOOST) {
            for (OrderDetail orderDetail : orderDetails) {
                Integer buyCount = orderDetail.getCount();
                Long productDetailId = orderDetail.getProductDetailId();
                ProductDetail productDetail = productDetailDao.getOne(productDetailId);
                // 加到库存
                productDetail.setInventory(productDetail.getInventory() + buyCount);
                productDetail.setUpdateTime(currentTimeMillis);
                productDetailDao.save(productDetail);
            }
        } else if (orderType.intValue() == MallConstant.ORDER_TYPE_FREE) {
            for (OrderDetail orderDetail : orderDetails) {
                Integer buyCount = orderDetail.getCount();
                Long productFreeReceiveDetailId = orderDetail.getProductFreeReceiveDetailId();
                ProductFreeReceiveDetail productFreeReceiveDetail = productFreeReceiveDetailDao.getOne(productFreeReceiveDetailId);
                // 加到免费领商品库存
                productFreeReceiveDetail.setInventory(productFreeReceiveDetail.getInventory() + buyCount);
                productFreeReceiveDetail.setUpdateTime(currentTimeMillis);
                productFreeReceiveDetailDao.save(productFreeReceiveDetail);
            }
        } else {
            // type of non-existent order or member order, no need to do anything...
        }
    }

    /**
     * 保存订单
     * @param orderView
     */
    private OrderView saveOrderCommon(OrderView orderView) {
        long totalPrice = orderView.getTotalFee();
        Set<OrderDetail> orderDetails = orderView.getOrderDetails();
        long currentTimeMillis = System.currentTimeMillis();

        // 保存订单的业务逻辑
        Order order = new Order();
        TorinoSrcBeanUtils.copyBean(orderView, order);
        // user数据库映射传给dao进行存储
        order.setOrderDetails(new HashSet<>());
        order.setCreateTime(currentTimeMillis);
        order.setUpdateTime(currentTimeMillis);
        order.setOrderNo(currentTimeMillis + "" + Math.round(Math.random() * 100000));
        order.setEnabled(1);
        order.setTotalFee(Integer.parseInt(totalPrice + ""));
        order.setShopId(orderView.getShopId());
        order.setOrderType(orderView.getOrderType());
        order.setLastTotalFee(Integer.parseInt(totalPrice + ""));
        orderDao.save(order);

        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setCreateTime(currentTimeMillis);
            orderDetail.setUpdateTime(currentTimeMillis);
            orderDetail.setOrderId(order.getId());
            orderDetail.setEnabled(1);
            orderDetailDao.save(orderDetail);
        }
        TorinoSrcBeanUtils.copyBean(order, orderView);

        return orderView;
    }

    /**
     * 退款操作(全额退款情况)
     * @param orderView
     */
    @Transactional(rollbackOn = {Exception.class})
    @Override
    public void refundThenAddInventory(OrderView orderView){

        Long orderId=orderView.getId();
        Order order=orderDao.findById(orderId).get();
        TorinoSrcBeanUtils.copyBean(order,orderView);

        // 增加库存
        Set<OrderDetail> orderDetails=orderView.getOrderDetails();
        ProductDetail productDetail;
        Long productDetailId;
        Integer count;
        for(OrderDetail orderDetail:orderDetails){
            productDetailId=orderDetail.getProductDetailId();
            productDetail=productDetailDao.findById(productDetailId).get();
            count=orderDetail.getCount();
            Long inventory=productDetail.getInventory();
            productDetail.setInventory(inventory+count);
            productDetailDao.save(productDetail);
        }

        // 分销订单(操作)
        if(orderView.getOrderType()==MallConstant.ORDER_STATUS_WAIT_PAY&&shopAccountDetailDao.countByOrderIdAndRecordStatus(orderId,0)>0){
            shopAccountDetailService.reduceAccountByOrder(orderView);
        }else {

        }
    }

    /**
     *  获取订单
     * @param teamId
     * @param userId
     * @return
     */
    @Override
    public OrderView getOrderIdByTeamIdAndUserId(Long teamId,Long userId){
        Order order=orderDao.findByTeamIdAndUserId(teamId,userId);
        OrderView orderView=new OrderView();
        TorinoSrcBeanUtils.copyBean(order,orderView);
        return orderView;
    }

}
