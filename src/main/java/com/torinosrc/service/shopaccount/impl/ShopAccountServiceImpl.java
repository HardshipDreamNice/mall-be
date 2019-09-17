/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.shopaccount.impl;

import com.alibaba.fastjson.JSON;
import com.torinosrc.commons.constants.MallConstant;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.DateUtils;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.order.OrderDao;
import com.torinosrc.dao.orderdetail.OrderDetailDao;
import com.torinosrc.dao.productdetail.ProductDetailDao;
import com.torinosrc.dao.productdetailsnapshot.ProductDetailSnapshotDao;
import com.torinosrc.dao.shop.ShopDao;
import com.torinosrc.dao.shopaccount.ShopAccountDao;
import com.torinosrc.dao.shoptree.ShopTreeDao;
import com.torinosrc.dao.user.UserDao;
import com.torinosrc.model.entity.order.Order;
import com.torinosrc.model.entity.orderdetail.OrderDetail;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.entity.productdetailsnapshot.ProductDetailSnapshot;
import com.torinosrc.model.entity.shop.Shop;
import com.torinosrc.model.entity.shopaccount.ShopAccount;
import com.torinosrc.model.entity.shoptree.ShopTree;
import com.torinosrc.model.entity.sysuser.SysUser;
import com.torinosrc.model.entity.user.User;
import com.torinosrc.model.view.order.OrderView;
import com.torinosrc.model.view.productdetail.ProductDetailView;
import com.torinosrc.model.view.productdetailsnapshot.ProductDetailSnapshotView;
import com.torinosrc.model.view.shopaccount.ShopAccountView;
import com.torinosrc.model.view.user.UserView;
import com.torinosrc.service.productdetail.ProductDetailService;
import com.torinosrc.service.shopaccount.ShopAccountService;
import com.torinosrc.service.shopaccountdetail.ShopAccountDetailService;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
* <b><code>ShopAccountImpl</code></b>
* <p/>
* ShopAccount的具体实现
* <p/>
* <b>Creation Time:</b> 2018-07-12 16:33:53.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class ShopAccountServiceImpl implements ShopAccountService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ShopAccountServiceImpl.class);

    @Autowired
    private ShopAccountDao shopAccountDao;

    @Autowired
    private ShopTreeDao shopTreeDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductDetailDao productDetailDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private ProductDetailSnapshotDao productDetailSnapshotDao;

    @Autowired
    private ShopAccountDetailService shopAccountDetailService;

    //订单交易成功状态
    private final static Integer ORDER_TRANSACTION_SUCCESS_STATUS = 5;

    /**
     * 订单时间段类型 0是全部 1是本周 2是本月 3是本日
     */
    private final static Integer ORDER_PERIOD_ALL = 0;

    private final static Integer ORDER_PERIOD_CURRENTWEEK = 1 ;

    private final static Integer ORDER_PERIOD_CURRENTMONTH = 2;

    private final static Integer ORDER_PERIOD_CURRENTDAY = 3;



    @Override
    public ShopAccountView getEntity(long id) {
        // 获取Entity
        ShopAccount shopAccount = shopAccountDao.getOne(id);
        // 复制Dao层属性到view属性
        ShopAccountView shopAccountView = new ShopAccountView();
        TorinoSrcBeanUtils.copyBean(shopAccount, shopAccountView);
        return shopAccountView;
    }

    @Override
    public ShopAccountView findShopAccountByShopId(Long shopId){
        // 获取Entity
        ShopAccount shopAccount = shopAccountDao.findShopAccountByShopId(shopId);
        // 复制Dao层属性到view属性
        ShopAccountView shopAccountView = new ShopAccountView();
        TorinoSrcBeanUtils.copyBean(shopAccount, shopAccountView);
        return shopAccountView;
    }

    @Override
    public Page<ShopAccountView> getEntitiesByParms(ShopAccountView shopAccountView, int currentPage, int pageSize) {
        Specification<ShopAccount> shopAccountSpecification = new Specification<ShopAccount>() {
            @Override
            public Predicate toPredicate(Root<ShopAccount> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,shopAccountView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ShopAccount> shopAccounts = shopAccountDao.findAll(shopAccountSpecification, pageable);

        // 转换成View对象并返回
        return shopAccounts.map(shopAccount->{
            ShopAccountView shopAccountView1 = new ShopAccountView();
            TorinoSrcBeanUtils.copyBean(shopAccount, shopAccountView1);
            return shopAccountView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return shopAccountDao.count();
    }

    @Override
    public List<ShopAccountView> findAll() {
        List<ShopAccountView> shopAccountViews = new ArrayList<>();
        List<ShopAccount> shopAccounts = shopAccountDao.findAll();
        for (ShopAccount shopAccount : shopAccounts){
            ShopAccountView shopAccountView = new ShopAccountView();
            TorinoSrcBeanUtils.copyBean(shopAccount, shopAccountView);
            shopAccountViews.add(shopAccountView);
        }
        return shopAccountViews;
    }

    @Override
    public ShopAccountView saveEntity(ShopAccountView shopAccountView) {
        // 保存的业务逻辑
        ShopAccount shopAccount = new ShopAccount();
        TorinoSrcBeanUtils.copyBean(shopAccountView, shopAccount);
        // user数据库映射传给dao进行存储
        shopAccount.setCreateTime(System.currentTimeMillis());
        shopAccount.setUpdateTime(System.currentTimeMillis());
        shopAccount.setEnabled(1);
        shopAccountDao.save(shopAccount);
        TorinoSrcBeanUtils.copyBean(shopAccount,shopAccountView);
        return shopAccountView;
    }

    @Override
    public void deleteEntity(long id) {
        ShopAccount shopAccount = new ShopAccount();
        shopAccount.setId(id);
        shopAccountDao.delete(shopAccount);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<ShopAccount> shopAccounts = new ArrayList<>();
        for(String entityId : entityIds){
            ShopAccount shopAccount = new ShopAccount();
            shopAccount.setId(Long.valueOf(entityId));
            shopAccounts.add(shopAccount);
        }
        shopAccountDao.deleteInBatch(shopAccounts);
    }

    @Override
    public void updateEntity(ShopAccountView shopAccountView) {
        Specification<ShopAccount> shopAccountSpecification = Optional.ofNullable(shopAccountView).map( s -> {
            return new Specification<ShopAccount>() {
                @Override
                public Predicate toPredicate(Root<ShopAccount> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("ShopAccountView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<ShopAccount> shopAccountOptionalBySearch = shopAccountDao.findOne(shopAccountSpecification);
        shopAccountOptionalBySearch.map(shopAccountBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(shopAccountView,shopAccountBySearch);
            shopAccountBySearch.setUpdateTime(System.currentTimeMillis());
            shopAccountDao.save(shopAccountBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + shopAccountView.getId() + "的数据记录"));
    }

    @Override
    public ShopAccountView getRealIncome(ShopAccountView shopAccountView) {

        if (ObjectUtils.isEmpty(shopAccountView) || ObjectUtils.isEmpty(shopAccountView.getShopId())) {
            return new ShopAccountView();
        }

        Long shopId = shopAccountView.getShopId();
        Shop shop = shopDao.getOne(shopId);
        Integer percent1 = shop.getPercent1();
        Integer percent2 = 0;
        Integer percent3 = shop.getPercent3();

        Long parentShopId = shopTreeDao.findByShopId(shop.getId()).getParentShopId();
        //顶层
        if(parentShopId.longValue() == 0){

            percent2 = shop.getPercent1();

        } else {
            // 不是顶层
            Shop parenetShop = shopDao.getOne(parentShopId);
            percent2 = parenetShop.getPercent2();
        }


        //本店销售利润
        List<Order> orderListAll = orderDao.getAllByShopIdAndStatusAndOrderType(shopId, ORDER_TRANSACTION_SUCCESS_STATUS,MallConstant.ORDER_TYPE_GENERAL);
        Long monday = DateUtils.getCurrentMonday();
        Long sunday = DateUtils.getCurrentSunday();
        List<Order> orderListWeek = orderDao.getAllByShopIdAndStatusAndUpdateTimeBetweenAndOrderType(shopId, ORDER_TRANSACTION_SUCCESS_STATUS, monday, sunday,MallConstant.ORDER_TYPE_GENERAL);
        Long currentMonthFirstDay = DateUtils.getCurrentMonthFirstDay();
        Long currentMonthLastDay = DateUtils.getCurrentMonthLastDay();
        List <Order> orderListMonth = orderDao.getAllByShopIdAndStatusAndUpdateTimeBetweenAndOrderType(shopId, ORDER_TRANSACTION_SUCCESS_STATUS, currentMonthFirstDay, currentMonthLastDay,MallConstant.ORDER_TYPE_GENERAL);
        Long currentDayStartTime = DateUtils.getCurrentDayStartTime();
        Long currentDayEndTime = DateUtils.getCurrentDayEndTime();
        List<Order> orderListDay = orderDao.getAllByShopIdAndStatusAndUpdateTimeBetweenAndOrderType(shopId, ORDER_TRANSACTION_SUCCESS_STATUS, currentDayStartTime, currentDayEndTime,MallConstant.ORDER_TYPE_GENERAL);

        Long profitAll = this.getOrdersTotalProfit(orderListAll, shopId);
        Long profitWeek = this.getOrdersTotalProfit(orderListWeek, shopId);
        Long profitMonth = this.getOrdersTotalProfit(orderListMonth, shopId);
        Long profitDay = this.getOrdersTotalProfit(orderListDay, shopId);


        Long ividendAll;
        Long ividendWeek;
        Long ividendMonth;
        Long ividendDay;

        Long shopIdLevel2;
        Long ividendAllLevel2 = 0L;
        Long ividendWeekLevel2 = 0L;
        Long ividendMonthLevel2 = 0L;
        Long ividendDayLevel2 = 0L;
        //所有二级分销商店铺
        List<ShopTree> shopTreesLevel2 = shopTreeDao.findAllByParentShopId(shopId);
        List<ShopTree> shopTreesLevel31;
        List<ShopTree> shopTreesLevel3 = new ArrayList<>();
        for (ShopTree shopTreeLevel2 : shopTreesLevel2) {
            shopIdLevel2 = shopTreeLevel2.getShopId();
            //
            // 分红
            ividendAll = this.getLowLevelShopDividend(shopIdLevel2, percent2, ORDER_PERIOD_ALL);
            ividendWeek = this.getLowLevelShopDividend(shopIdLevel2, percent2, ORDER_PERIOD_CURRENTWEEK);
            ividendMonth = this.getLowLevelShopDividend(shopIdLevel2, percent2, ORDER_PERIOD_CURRENTMONTH);
            ividendDay = this.getLowLevelShopDividend(shopIdLevel2, percent2, ORDER_PERIOD_CURRENTDAY);
            ividendAllLevel2 = ividendAll + ividendAllLevel2;
            ividendWeekLevel2 = ividendWeek + ividendWeekLevel2;
            ividendMonthLevel2 = ividendMonth + ividendMonthLevel2;
            ividendDayLevel2 = ividendDay + ividendDayLevel2;

            //所有三级分销商店铺
            shopTreesLevel31 = shopTreeDao.findAllByParentShopId(shopIdLevel2);
            shopTreesLevel3.addAll(shopTreesLevel31);
        }

        Long shopIdLevel3;
        Long ividendAllLevel3 = 0L;
        Long ividendWeekLevel3 = 0L;
        Long ividendMonthLevel3 = 0L;
        Long ividendDayLevel3 =  0L;
        for (ShopTree shopTreeLevel3 : shopTreesLevel3) {
            shopIdLevel3 = shopTreeLevel3.getShopId();
            ividendAll = this.getLowLevelShopDividend(shopIdLevel3, percent1, ORDER_PERIOD_ALL);
            ividendWeek = this.getLowLevelShopDividend(shopIdLevel3, percent1, ORDER_PERIOD_CURRENTWEEK);
            ividendMonth = this.getLowLevelShopDividend(shopIdLevel3, percent1, ORDER_PERIOD_CURRENTMONTH);
            ividendDay = this.getLowLevelShopDividend(shopIdLevel3, percent1, ORDER_PERIOD_CURRENTDAY);
            ividendAllLevel3 = ividendAllLevel3 + ividendAll;
            ividendWeekLevel3 = ividendWeekLevel3 + ividendWeek;
            ividendMonthLevel3 = ividendMonthLevel3 + ividendMonth;
            ividendDayLevel3 = ividendDayLevel3 + ividendDay;
        }

        ShopAccountView shopAccountView1 = new ShopAccountView();
        shopAccountView1.setIncomeAmountAll(profitAll + ividendAllLevel2 + ividendAllLevel3);
        shopAccountView1.setIncomeAmountWeek(profitWeek + ividendWeekLevel2 + ividendWeekLevel3);
        shopAccountView1.setIncomeAmountMonth(profitMonth + ividendMonthLevel2 + ividendMonthLevel3);
        shopAccountView1.setIncomeAmountDay(profitDay + ividendDayLevel2 + ividendDayLevel3);

        return shopAccountView1;
    }

    @Override
    public ShopAccountView getSalesAmount(ShopAccountView shopAccountView) {

        if (ObjectUtils.isEmpty(shopAccountView) || ObjectUtils.isEmpty(shopAccountView.getShopId())) {
            return new ShopAccountView();
        }

        Long shopId = shopAccountView.getShopId();

        List<Order> orderListAll = orderDao.getAllByShopIdAndStatus(shopId, ORDER_TRANSACTION_SUCCESS_STATUS);
        Long monday = DateUtils.getCurrentMonday();
        Long sunday = DateUtils.getCurrentSunday();
        List<Order> orderListWeek = orderDao.getAllByShopIdAndStatusAndUpdateTimeBetween(shopId, ORDER_TRANSACTION_SUCCESS_STATUS, monday, sunday);
        Long currentMonthFirstDay = DateUtils.getCurrentMonthFirstDay();
        Long currentMonthLastDay = DateUtils.getCurrentMonthLastDay();
        List <Order> orderListMonth = orderDao.getAllByShopIdAndStatusAndUpdateTimeBetween(shopId, ORDER_TRANSACTION_SUCCESS_STATUS, currentMonthFirstDay, currentMonthLastDay);
        Long currentDayStartTime = DateUtils.getCurrentDayStartTime();
        Long currentDayEndTime = DateUtils.getCurrentDayEndTime();
        List<Order> orderListDay = orderDao.getAllByShopIdAndStatusAndUpdateTimeBetween(shopId, ORDER_TRANSACTION_SUCCESS_STATUS, currentDayStartTime, currentDayEndTime);

        Integer salesAmountAll = getOrdersSalesAmount(orderListAll);
        Integer salesAmountWeek = getOrdersSalesAmount(orderListWeek);
        Integer salesAmountMonth = getOrdersSalesAmount(orderListMonth);
        Integer salesAmountDay = getOrdersSalesAmount(orderListDay);

        ShopAccountView shopAccountView1 = new ShopAccountView();
        shopAccountView1.setSalesAmountAll(salesAmountAll);
        shopAccountView1.setSalesAmountWeek(salesAmountWeek);
        shopAccountView1.setSalesAmountMonth(salesAmountMonth);
        shopAccountView1.setSalesAmountDay(salesAmountDay);

        return shopAccountView1;
    }

    @Override
    public ShopAccountView getTeamIncome(ShopAccountView shopAccountView) {

        if (ObjectUtils.isEmpty(shopAccountView) || ObjectUtils.isEmpty(shopAccountView.getShopId())) {
            return new ShopAccountView();
        }

        Long shopId = shopAccountView.getShopId();
        //我的实际收入
        ShopAccountView shopAccountViewMyself = this.getRealIncome(shopAccountView);

        Long teamIncomeAmountAll = shopAccountViewMyself.getIncomeAmountAll();
        Long teamIncomeAmountDay = shopAccountViewMyself.getIncomeAmountDay();
        Long teamIncomeAmountWeek = shopAccountViewMyself.getIncomeAmountWeek();
        Long teamIncomeAmountMonth = shopAccountViewMyself.getIncomeAmountMonth();

        //所有下级店铺
        List<ShopTree> shopTreeList = shopTreeDao.findAllByParentShopId(shopId);
        Long shopIdLevel2;
        ShopAccountView shopAccountView1;
        ShopAccountView shopAccountViewLevel2;
        for (ShopTree shopTree : shopTreeList) {
            shopIdLevel2 = shopTree.getShopId();
            shopAccountView1 = new ShopAccountView();
            shopAccountView1.setShopId(shopIdLevel2);
            shopAccountViewLevel2 = this.getRealIncome(shopAccountView1);
            teamIncomeAmountAll += shopAccountViewLevel2.getIncomeAmountAll();
            teamIncomeAmountDay += shopAccountViewLevel2.getIncomeAmountDay();
            teamIncomeAmountWeek += shopAccountViewLevel2.getIncomeAmountWeek();
            teamIncomeAmountMonth += shopAccountViewLevel2.getIncomeAmountMonth();
        }

        ShopAccountView shopAccountView2 = new ShopAccountView();
        shopAccountView2.setTeamIncomeAmountAll(teamIncomeAmountAll == null ? 0 : teamIncomeAmountAll);
        shopAccountView2.setTeamIncomeAmountDay(teamIncomeAmountDay == null ? 0 : teamIncomeAmountDay);
        shopAccountView2.setTeamIncomeAmountWeek(teamIncomeAmountWeek == null ? 0 : teamIncomeAmountWeek);
        shopAccountView2.setTeamIncomeAmountMonth(teamIncomeAmountMonth  == null ? 0 : teamIncomeAmountMonth);

        return shopAccountView2;
    }

    @Override
    public List<UserView> getTeamMembers(ShopAccountView shopAccountView) {

        if (ObjectUtils.isEmpty(shopAccountView) || ObjectUtils.isEmpty(shopAccountView.getShopId())) {
            return new ArrayList<>();
        }

        Long shopId = shopAccountView.getShopId();
        Shop myShop = shopDao.getOne(shopId);

        Integer percent1 = 0;

        Long parentShopId = shopTreeDao.findByShopId(myShop.getId()).getParentShopId();
        //level1
        if(parentShopId.longValue() == 0){

            percent1 = myShop.getPercent1();

        }else {
            //level2
            Shop parenetShop = shopDao.getOne(parentShopId);
            percent1  = parenetShop.getPercent2();
        }

        System.out.println("percent1"+percent1);
//         percent1 = myShop.getPercent1();
        //获取所有下级店铺
        List<ShopTree> shopTreeList = shopTreeDao.findAllByParentShopId(shopId);

        Long shopIdLevel2;
        Shop shop;
        UserView userView;
        User user;
        List<Order> orderList;
        List<UserView> userViewList = new ArrayList<>();
        for (ShopTree shopTree : shopTreeList) {
            shopIdLevel2 = shopTree.getShopId();
            shop = shopDao.getOne(shopIdLevel2);

            //获取店铺订单
            orderList = orderDao.getAllByShopIdAndStatusAndOrderType(shopIdLevel2, ORDER_TRANSACTION_SUCCESS_STATUS,MallConstant.ORDER_TYPE_GENERAL);
            //获取销售额
            Integer salesAmount = this.getOrdersSalesAmount(orderList);
            //获取分红
            Long highLevelDevidend = this.getLowLevelShopDividend(shopIdLevel2, percent1, ORDER_PERIOD_ALL);

            user = userDao.getOne(shop.getUserId());
            // 复制Dao层属性到view属性
            userView = new UserView();
            TorinoSrcBeanUtils.copyBean(user, userView);
            userView.setSalesAmount(salesAmount);
            userView.setHighLevelDevidend(highLevelDevidend);
            userViewList.add(userView);
        }

        return userViewList;
    }

    /**
     * 获取订单总利润
     * @param orderList
     * @return
     */
    private Long getOrdersTotalProfit(List<Order> orderList, Long shopId) {
        List<OrderDetail> orderDetailList;
        ProductDetailSnapshotView productDetailSnapshotView;
        Long totalProfit = 0L;
        Long profit;
        Integer count;
        for (Order order : orderList) {
            orderDetailList = orderDetailDao.findByOrderId(order.getId());
            for (OrderDetail orderDetail : orderDetailList) {
                count = orderDetail.getCount();
                productDetailSnapshotView=new ProductDetailSnapshotView();
                productDetailSnapshotView.setId(orderDetail.getProductDetailSnapshotId());
                productDetailSnapshotView.setPrice(orderDetail.getPrice());
                //计算利润 （佣金分成 + 商品差价）
                profit = this.calculateProductProfit(productDetailSnapshotView, shopId);
                totalProfit = totalProfit + profit * count;
            }
        }
        return totalProfit;
    }

    /**
     * 计算商品利润
     *
     * @param productDetailSnapshotView
     * @param shopId
     * @return
     */
    public long calculateProductProfit(ProductDetailSnapshotView productDetailSnapshotView, long shopId) {
        ProductDetailSnapshot productDetailSnapshot=productDetailSnapshotDao.getOne(productDetailSnapshotView.getId());
        //佣金金额
        long commission =productDetailSnapshot.getCommission();
        //商品差价=卖出价-商品市场价
        long profit=productDetailSnapshotView.getPrice()-productDetailSnapshot.getPrice();
        long incomeAmount=commission+profit;

        //1、根据shopId查询存在几层分销商
        ShopTree shopTree = shopTreeDao.findByShopId(shopId);
        if(!ObjectUtils.isEmpty(shopTree) && shopTree.getParentShopId()!=0){
            ShopTree shopTreeLevel1 = shopTreeDao.findByShopId(shopTree.getParentShopId());
            if(!ObjectUtils.isEmpty(shopTreeLevel1) && shopTreeLevel1.getParentShopId()!=0){
                //三级分销商
                Shop shop = shopDao.getOne(shopTreeLevel1.getParentShopId());
                Long level3Commission =(long) shop.getPercent3()*commission/100;
                incomeAmount=level3Commission+profit;

            }else{
                //二级分销商
                Shop shop = shopDao.getOne(shopTree.getParentShopId());
                Long level2Commission =(shop.getPercent2()+shop.getPercent3())*commission/100;
                incomeAmount=level2Commission+profit;

            }
        }

        return incomeAmount;
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
                orderList = orderDao.getAllByShopIdAndStatusAndUpdateTimeBetweenAndOrderType(shopId, ORDER_TRANSACTION_SUCCESS_STATUS, monday, sunday,MallConstant.ORDER_TYPE_GENERAL);
                break;
            case 2:
                Long currentMonthFirstDay = DateUtils.getCurrentMonthFirstDay();
                Long currentMonthLastDay = DateUtils.getCurrentMonthLastDay();
                orderList = orderDao.getAllByShopIdAndStatusAndUpdateTimeBetweenAndOrderType(shopId, ORDER_TRANSACTION_SUCCESS_STATUS, currentMonthFirstDay, currentMonthLastDay,MallConstant.ORDER_TYPE_GENERAL);
                break;
            case 3:
                Long currentDayStartTime = DateUtils.getCurrentDayStartTime();
                Long currentDayEndTime = DateUtils.getCurrentDayEndTime();
                orderList = orderDao.getAllByShopIdAndStatusAndUpdateTimeBetweenAndOrderType(shopId, ORDER_TRANSACTION_SUCCESS_STATUS, currentDayStartTime, currentDayEndTime,MallConstant.ORDER_TYPE_GENERAL);
                break;
            default:
                orderList = orderDao.getAllByShopIdAndStatusAndOrderType(shopId, ORDER_TRANSACTION_SUCCESS_STATUS,MallConstant.ORDER_TYPE_GENERAL);
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
     * 获取可得的 下级店铺分红
     * @param shopId 店铺id
     * @param percent 分成
     * @param period 时期（0 所有日期 1 当前周 2 当前月 3 是当天）
     * @return
     */
    private Long getLowLevelShopDividendAdvance(Long shopId, Integer percent, Integer period) {
        List<com.torinosrc.model.entity.order.Order> orderList = new ArrayList<>();
        // 订单状态
        List<Integer> statusIds = new ArrayList<>();
        statusIds.add(1); //待发货
        statusIds.add(2); //待收货
        statusIds.add(3); //已收货
        statusIds.add(8); //退款关闭

//        switch (period) {
//            case 1:
//                Long monday = DateUtils.getCurrentMonday();
//                Long sunday = DateUtils.getCurrentSunday();
//                orderList = orderDao.findByShopIdAndStatusInAndUpdateTimeBetween(shopId, statusIds, monday, sunday);
//                break;
//            case 2:
//                Long currentMonthFirstDay = DateUtils.getCurrentMonthFirstDay();
//                Long currentMonthLastDay = DateUtils.getCurrentMonthLastDay();
//                orderList = orderDao.findByShopIdAndStatusInAndUpdateTimeBetween(shopId, statusIds, currentMonthFirstDay, currentMonthLastDay);
//                break;
//            case 3:
//                Long currentDayStartTime = DateUtils.getCurrentDayStartTime();
//                Long currentDayEndTime = DateUtils.getCurrentDayEndTime();
//                orderList = orderDao.findByShopIdAndStatusInAndUpdateTimeBetween(shopId, statusIds, currentDayStartTime, currentDayEndTime);
//                break;
//            default:
//                orderList = orderDao.findByShopIdAndStatusIn(shopId, statusIds);
//                break;
//        }
        orderList = orderDao.findByShopIdAndStatusInAndOrderType(shopId, statusIds,MallConstant.ORDER_TYPE_GENERAL);

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

    /*
     * 获取店铺的预收入
     */
    @Override
    public ShopAccountView getAdvanceIncome(ShopAccountView shopAccountView){

        if (ObjectUtils.isEmpty(shopAccountView) || ObjectUtils.isEmpty(shopAccountView.getShopId())) {
            return new ShopAccountView();
        }

        Long shopId = shopAccountView.getShopId();
        Shop shop = shopDao.getOne(shopId);
        Integer percent1 = shop.getPercent1();
        Integer percent2 = 0;
        Integer percent3 = shop.getPercent3();

        Long parentShopId = shopTreeDao.findByShopId(shop.getId()).getParentShopId();
        //顶层
        if(parentShopId.longValue() == 0){

            percent2 = shop.getPercent1();

        } else {
            // 不是顶层
            Shop parenetShop = shopDao.getOne(parentShopId);
            percent2 = parenetShop.getPercent2();
        }


        // 订单状态
        List<Integer> statusIds = new ArrayList<>();
        statusIds.add(1); //待发货
        statusIds.add(2); //待收货
        statusIds.add(3); //已收货
        statusIds.add(8); //退款关闭

        //本店销售利润
        List<Order> orderListAll = orderDao.findByShopIdAndStatusInAndOrderType(shopId,statusIds,MallConstant.ORDER_TYPE_GENERAL);
//        Long monday = DateUtils.getCurrentMonday();
//        Long sunday = DateUtils.getCurrentSunday();
//        List<Order> orderListWeek = orderDao.findByShopIdAndStatusInAndUpdateTimeBetween(shopId, statusIds, monday, sunday);
//        Long currentMonthFirstDay = DateUtils.getCurrentMonthFirstDay();
//        Long currentMonthLastDay = DateUtils.getCurrentMonthLastDay();
//        List <Order> orderListMonth = orderDao.findByShopIdAndStatusInAndUpdateTimeBetween(shopId, statusIds, currentMonthFirstDay, currentMonthLastDay);
//        Long currentDayStartTime = DateUtils.getCurrentDayStartTime();
//        Long currentDayEndTime = DateUtils.getCurrentDayEndTime();
//        List<Order> orderListDay = orderDao.findByShopIdAndStatusInAndUpdateTimeBetween(shopId, statusIds, currentDayStartTime, currentDayEndTime);

        Long profitAll = this.getOrdersTotalProfit(orderListAll, shopId);
//        Long profitWeek = this.getOrdersTotalProfit(orderListWeek, shopId);
//        Long profitMonth = this.getOrdersTotalProfit(orderListMonth, shopId);
//        Long profitDay = this.getOrdersTotalProfit(orderListDay, shopId);


        Long ividendAll;
//        Long ividendWeek;
//        Long ividendMonth;
//        Long ividendDay;

        Long shopIdLevel2;
        Long ividendAllLevel2 = 0L;
//        Long ividendWeekLevel2 = 0L;
//        Long ividendMonthLevel2 = 0L;
//        Long ividendDayLevel2 = 0L;
        //所有二级分销商店铺
        List<ShopTree> shopTreesLevel2 = shopTreeDao.findAllByParentShopId(shopId);
        List<ShopTree> shopTreesLevel31;
        List<ShopTree> shopTreesLevel3 = new ArrayList<>();
        for (ShopTree shopTreeLevel2 : shopTreesLevel2) {
            shopIdLevel2 = shopTreeLevel2.getShopId();
            //分红
            ividendAll = this.getLowLevelShopDividendAdvance(shopIdLevel2, percent2, ORDER_PERIOD_ALL);
//            ividendWeek = this.getLowLevelShopDividendAdvance(shopIdLevel2, percent2, ORDER_PERIOD_CURRENTWEEK);
//            ividendMonth = this.getLowLevelShopDividendAdvance(shopIdLevel2, percent2, ORDER_PERIOD_CURRENTMONTH);
//            ividendDay = this.getLowLevelShopDividendAdvance(shopIdLevel2, percent2, ORDER_PERIOD_CURRENTDAY);
            ividendAllLevel2 = ividendAll + ividendAllLevel2;
//            ividendWeekLevel2 = ividendWeek + ividendWeekLevel2;
//            ividendMonthLevel2 = ividendMonth + ividendMonthLevel2;
//            ividendDayLevel2 = ividendDay + ividendDayLevel2;

            //所有三级分销商店铺
            shopTreesLevel31 = shopTreeDao.findAllByParentShopId(shopIdLevel2);
            shopTreesLevel3.addAll(shopTreesLevel31);
        }

        Long shopIdLevel3;
        Long ividendAllLevel3 = 0L;
//        Long ividendWeekLevel3 = 0L;
//        Long ividendMonthLevel3 = 0L;
//        Long ividendDayLevel3 =  0L;
        for (ShopTree shopTreeLevel3 : shopTreesLevel3) {
            shopIdLevel3 = shopTreeLevel3.getShopId();
            ividendAll = this.getLowLevelShopDividendAdvance(shopIdLevel3, percent1, ORDER_PERIOD_ALL);
//            ividendWeek = this.getLowLevelShopDividendAdvance(shopIdLevel3, percent1, ORDER_PERIOD_CURRENTWEEK);
//            ividendMonth = this.getLowLevelShopDividendAdvance(shopIdLevel3, percent1, ORDER_PERIOD_CURRENTMONTH);
//            ividendDay = this.getLowLevelShopDividendAdvance(shopIdLevel3, percent1, ORDER_PERIOD_CURRENTDAY);
            ividendAllLevel3 = ividendAllLevel3 + ividendAll;
//            ividendWeekLevel3 = ividendWeekLevel3 + ividendWeek;
//            ividendMonthLevel3 = ividendMonthLevel3 + ividendMonth;
//            ividendDayLevel3 = ividendDayLevel3 + ividendDay;
        }

        ShopAccountView shopAccountView1 = new ShopAccountView();
        shopAccountView1.setIncomeAmountAll(profitAll + ividendAllLevel2 + ividendAllLevel3);
//        shopAccountView1.setIncomeAmountWeek(profitWeek + ividendWeekLevel2 + ividendWeekLevel3);
//        shopAccountView1.setIncomeAmountMonth(profitMonth + ividendMonthLevel2 + ividendMonthLevel3);
//        shopAccountView1.setIncomeAmountDay(profitDay + ividendDayLevel2 + ividendDayLevel3);

        return shopAccountView1;
    }

    @Override
    public void updateShopAccountSaleMember() {
        List<Order> memberOrderList = orderDao.findByStatusAndUpdateStatusAndOrderType(5, 0, 2);

        for (Order memberOrder : memberOrderList) {
            OrderView orderView = new OrderView();
            TorinoSrcBeanUtils.copyBean(memberOrder, orderView);
            shopAccountDetailService.calculateMemberOrderProfit(orderView);
            // 更新 updateStatus
            memberOrder.setUpdateStatus(1);
            memberOrder.setUpdateTime(System.currentTimeMillis());
            orderDao.save(memberOrder);
            LOG.info("以下会员订单的分成已成功更新到各商户的收入中：" + JSON.toJSONString(memberOrder));
        }
    }
}
