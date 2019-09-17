/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.shopaccountdetail.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.membershipgrade.MembershipGradeDao;
import com.torinosrc.dao.productdetailsnapshot.ProductDetailSnapshotDao;
import com.torinosrc.dao.shop.ShopDao;
import com.torinosrc.dao.shopaccount.ShopAccountDao;
import com.torinosrc.dao.shopaccountdetail.ShopAccountDetailDao;
import com.torinosrc.dao.shopproduct.ShopProductDao;
import com.torinosrc.dao.shopproductdetailsnapshot.ShopProductDetailSnapshotDao;
import com.torinosrc.dao.shopproductsnapshot.ShopProductSnapshotDao;
import com.torinosrc.dao.shoptree.ShopTreeDao;
import com.torinosrc.model.entity.membershipgrade.MembershipGrade;
import com.torinosrc.model.entity.orderdetail.OrderDetail;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.entity.productdetailsnapshot.ProductDetailSnapshot;
import com.torinosrc.model.entity.shop.Shop;
import com.torinosrc.model.entity.shopaccount.ShopAccount;
import com.torinosrc.model.entity.shopaccountdetail.ShopAccountDetail;
import com.torinosrc.model.entity.shopproduct.ShopProduct;
import com.torinosrc.model.entity.shopproductdetail.ShopProductDetail;
import com.torinosrc.model.entity.shopproductdetailsnapshot.ShopProductDetailSnapshot;
import com.torinosrc.model.entity.shopproductsnapshot.ShopProductSnapshot;
import com.torinosrc.model.entity.shoptree.ShopTree;
import com.torinosrc.model.view.order.MembershipOrderView;
import com.torinosrc.model.view.order.OrderView;
import com.torinosrc.model.view.shopaccount.ShopAccountView;
import com.torinosrc.model.view.shopaccountdetail.ShopAccountDetailView;
import com.torinosrc.service.shopaccount.ShopAccountService;
import com.torinosrc.service.shopaccountdetail.ShopAccountDetailService;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
* <b><code>ShopAccountDetailImpl</code></b>
* <p/>
* ShopAccountDetail的具体实现
* <p/>
* <b>Creation Time:</b> 2018-07-12 16:34:26.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class ShopAccountDetailServiceImpl implements ShopAccountDetailService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ShopAccountDetailServiceImpl.class);

    @Autowired
    private ShopAccountDao shopAccountDao;

    @Autowired
    private ShopAccountDetailDao shopAccountDetailDao;

    @Autowired
    private ShopProductSnapshotDao shopProductSnapshotDao;

    @Autowired
    private ShopProductDetailSnapshotDao shopProductDetailSnapshotDao;

    @Autowired
    private ProductDetailSnapshotDao productDetailSnapshotDao;

    @Autowired
    private ShopTreeDao shopTreeDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private ShopAccountService shopAccountService;

    @Autowired
    private MembershipGradeDao membershipGradeDao;

    @Override
    public ShopAccountDetailView getEntity(long id) {
        // 获取Entity
        ShopAccountDetail shopAccountDetail = shopAccountDetailDao.getOne(id);
        // 复制Dao层属性到view属性
        ShopAccountDetailView shopAccountDetailView = new ShopAccountDetailView();
        TorinoSrcBeanUtils.copyBean(shopAccountDetail, shopAccountDetailView);
        return shopAccountDetailView;
    }

    @Override
    public Page<ShopAccountDetailView> getEntitiesByParms(ShopAccountDetailView shopAccountDetailView, int currentPage, int pageSize) {
        Specification<ShopAccountDetail> shopAccountDetailSpecification = new Specification<ShopAccountDetail>() {
            @Override
            public Predicate toPredicate(Root<ShopAccountDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,shopAccountDetailView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ShopAccountDetail> shopAccountDetails = shopAccountDetailDao.findAll(shopAccountDetailSpecification, pageable);

        // 转换成View对象并返回
        return shopAccountDetails.map(shopAccountDetail->{
            ShopAccountDetailView shopAccountDetailView1 = new ShopAccountDetailView();
            TorinoSrcBeanUtils.copyBean(shopAccountDetail, shopAccountDetailView1);
            return shopAccountDetailView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return shopAccountDetailDao.count();
    }

    @Override
    public List<ShopAccountDetailView> findAll() {
        List<ShopAccountDetailView> shopAccountDetailViews = new ArrayList<>();
        List<ShopAccountDetail> shopAccountDetails = shopAccountDetailDao.findAll();
        for (ShopAccountDetail shopAccountDetail : shopAccountDetails){
            ShopAccountDetailView shopAccountDetailView = new ShopAccountDetailView();
            TorinoSrcBeanUtils.copyBean(shopAccountDetail, shopAccountDetailView);
            shopAccountDetailViews.add(shopAccountDetailView);
        }
        return shopAccountDetailViews;
    }

    @Override
    public ShopAccountDetailView saveEntity(ShopAccountDetailView shopAccountDetailView) {
        // 保存的业务逻辑
        ShopAccountDetail shopAccountDetail = new ShopAccountDetail();
        TorinoSrcBeanUtils.copyBean(shopAccountDetailView, shopAccountDetail);
        // user数据库映射传给dao进行存储
        shopAccountDetail.setCreateTime(System.currentTimeMillis());
        shopAccountDetail.setUpdateTime(System.currentTimeMillis());
        shopAccountDetail.setEnabled(0);
        shopAccountDetailDao.save(shopAccountDetail);
        TorinoSrcBeanUtils.copyBean(shopAccountDetail,shopAccountDetailView);
        return shopAccountDetailView;
    }

    /**
     * 计算各级分销商利润
     * @param orderView
     */
    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void calculateOrderProfit(OrderView orderView) {
        // 计算订单各层分销商利润
        Set<OrderDetail> orderDetails=orderView.getOrderDetails();

        Long totalAmount1=0L,totalAmount2=0L,totalAmount3=0L;  // 对应层分销商账号总金额
        Long money1=0L,money2=0L,money3=0L;  // 对应层分销商账号现有金额

        for(OrderDetail orderDetail:orderDetails){
            ShopAccountDetailView shopAccountDetailView =new ShopAccountDetailView();
//            ShopProductDetailSnapshot shopProductDetailSnapshot = orderDetail.getShopProductDetailSnapshot();
            //ProductDetailSnapshot productDetailSnapshot=shopProductDetailSnapshot.getProductDetailSnapshot();
            ProductDetailSnapshot productDetailSnapshot=productDetailSnapshotDao.getOne(orderDetail.getProductDetailSnapshotId());
            shopAccountDetailView.setOrderDetailId(orderDetail.getId());
            shopAccountDetailView.setOrderId(orderView.getId());
            shopAccountDetailView.setShopProductDetailSnapshotId(orderDetail.getShopProductDetailSnapshotId());
            shopAccountDetailView.setProductName(productDetailSnapshot.getProductSnapshot().getName());
            if(!ObjectUtils.isEmpty(orderDetail.getShopProductDetailSnapshotId())){
                ShopProductDetailSnapshot shopProductDetailSnapshot = shopProductDetailSnapshotDao.getOne(orderDetail.getShopProductDetailSnapshotId());
                shopAccountDetailView.setShopProductSnapshotId(shopProductDetailSnapshot.getShopProductSnapshotId());
            }

            shopAccountDetailView.setProductDetailSnapshotId(orderDetail.getProductDetailSnapshotId());
//            ShopProductSnapshot shopProductSnapshot = shopProductSnapshotDao.getOne(orderDetail.getShopProductDetailSnapshot().getShopProductSnapshotId());
            shopAccountDetailView.setShopId(orderView.getShopId());
            ShopAccount shopAccount=shopAccountDao.findShopAccountByShopId(orderView.getShopId());
            shopAccountDetailView.setShopAccountId(shopAccount.getId());
            shopAccountDetailView.setSaleCount(orderDetail.getCount());
            //佣金金额
            long commission =productDetailSnapshot.getCommission()* orderDetail.getCount();
            //商品差价=卖出价-商品市场价
            long profit=(orderDetail.getPrice()-productDetailSnapshot.getPrice())*orderDetail.getCount();
//            if(!ObjectUtils.isEmpty(shopProductDetailSnapshot.getProductPrice())){
//                profit=shopProductDetailSnapshot.getProductPrice()-shopProductDetailSnapshot.getAdvisePrice();
//            }
            //1、根据shopId查询存在几层分销商
            ShopTree shopTree = shopTreeDao.findByShopId(orderView.getShopId());
            if(!ObjectUtils.isEmpty(shopTree) && shopTree.getParentShopId()!=0){
                ShopTree shopTreeLevel1 = shopTreeDao.findByShopId(shopTree.getParentShopId());
                ShopAccount shopAccount2=shopAccountDao.findShopAccountByShopId(shopTree.getParentShopId());
                if(!ObjectUtils.isEmpty(shopTreeLevel1) && shopTreeLevel1.getParentShopId()!=0){
                    //三级分销商
                    Shop shop = shopDao.getOne(shopTreeLevel1.getParentShopId());
                    Long level3Commission =(long) shop.getPercent3()*commission/100;
                    shopAccountDetailView.setIncomeAmount(level3Commission+profit);
                    shopAccountDetailView.setIncomeSource(1);

                    //二级佣金
                    ShopAccountDetailView level2ShopAccountDetailView = new ShopAccountDetailView();
                    TorinoSrcBeanUtils.copyBean(shopAccountDetailView,level2ShopAccountDetailView);
                    level2ShopAccountDetailView.setShopId(shopTreeLevel1.getShopId());

                    level2ShopAccountDetailView.setShopAccountId(shopAccount2.getId());
                    level2ShopAccountDetailView.setIncomeSource(0);
                    Long level2Commission =(long) shop.getPercent2()*commission/100;
                    level2ShopAccountDetailView.setIncomeAmount(level2Commission);

                    //一级佣金
                    ShopAccountDetailView parentShopAccountDetailView = new ShopAccountDetailView();
                    TorinoSrcBeanUtils.copyBean(shopAccountDetailView,parentShopAccountDetailView);
                    parentShopAccountDetailView.setShopId(shop.getId());
                    ShopAccount shopAccount3=shopAccountDao.findShopAccountByShopId(shopTreeLevel1.getParentShopId());
                    parentShopAccountDetailView.setShopAccountId(shopAccount3.getId());
                    parentShopAccountDetailView.setIncomeSource(0);
                    Long level1Commission =commission-level3Commission-level2Commission;
                    parentShopAccountDetailView.setIncomeAmount(level1Commission);

                    saveEntity(shopAccountDetailView);
                    saveEntity(level2ShopAccountDetailView);
                    saveEntity(parentShopAccountDetailView);

                    // 店铺总收入,现有金额+原有
                    totalAmount3=totalAmount3+level3Commission+profit;
                    money3=money3+level3Commission+profit;
                    totalAmount2=totalAmount2+level2Commission ;
                    money2=money2+level2Commission ;
                    totalAmount1=totalAmount1+level1Commission;
                    money1=money1+level1Commission;

                }else{
                    //二级分销商
                    Shop shop = shopDao.getOne(shopTree.getParentShopId());
//                    BigDecimal percent=new BigDecimal((double)(shop.getPercent2()+shop.getPercent3())/100).setScale(2,BigDecimal.ROUND_HALF_UP);
//                    System.out.println(percent);
                    Long level2Commission =(shop.getPercent2()+shop.getPercent3())*commission/100;
//                    System.out.println(level2Commission);
                    shopAccountDetailView.setIncomeAmount(level2Commission+profit);
                    shopAccountDetailView.setIncomeSource(1);

                    //父级佣金
                    ShopAccountDetailView parentShopAccountDetailView = new ShopAccountDetailView();
                    TorinoSrcBeanUtils.copyBean(shopAccountDetailView,parentShopAccountDetailView);
                    parentShopAccountDetailView.setShopId(shopTreeLevel1.getShopId());
                    parentShopAccountDetailView.setShopAccountId(shopAccount2.getId());
                    parentShopAccountDetailView.setIncomeSource(0);
                    parentShopAccountDetailView.setIncomeAmount(commission-level2Commission);

                    saveEntity(shopAccountDetailView);
                    saveEntity(parentShopAccountDetailView);

                    // 店铺总收入,现有金额+原有
                    totalAmount2=totalAmount2+level2Commission+profit;
                    money2=money2+level2Commission+profit;
                    totalAmount1=totalAmount1+commission-level2Commission;
                    money1=money1+commission-level2Commission;
                }

            }else{
                //一级分销商
                shopAccountDetailView.setIncomeSource(1);
                shopAccountDetailView.setIncomeAmount(commission+profit);
                saveEntity(shopAccountDetailView);

                // 店铺总收入,现有金额+原有
                totalAmount1=totalAmount1+commission+profit;
                money1=money1+commission+profit;
            }
        }

        ShopTree shopTree = shopTreeDao.findByShopId(orderView.getShopId());
        if(!ObjectUtils.isEmpty(shopTree) && shopTree.getParentShopId()!=0){
            ShopTree shopTreeLevel1 = shopTreeDao.findByShopId(shopTree.getParentShopId());
            if(!ObjectUtils.isEmpty(shopTreeLevel1) && shopTreeLevel1.getParentShopId()!=0){
                // 三层
                ShopAccount shopAccount3=shopAccountDao.findShopAccountByShopId(orderView.getShopId());
                shopAccount3.setTotalAmount(totalAmount3+shopAccount3.getTotalAmount());
                shopAccount3.setMoney(money3+shopAccount3.getMoney());
                ShopAccountView shopAccountView3=new ShopAccountView();
                TorinoSrcBeanUtils.copyBean(shopAccount3,shopAccountView3);
                shopAccountService.updateEntity(shopAccountView3);

                ShopAccount shopAccount2=shopAccountDao.findShopAccountByShopId(shopTreeLevel1.getShopId());
                shopAccount2.setTotalAmount(totalAmount2+shopAccount2.getTotalAmount());
                shopAccount2.setMoney(money2+shopAccount2.getMoney());
                ShopAccountView shopAccountView2=new ShopAccountView();
                TorinoSrcBeanUtils.copyBean(shopAccount2,shopAccountView2);
                shopAccountService.updateEntity(shopAccountView2);

                ShopAccount shopAccount1=shopAccountDao.findShopAccountByShopId(shopTreeLevel1.getParentShopId());
                shopAccount1.setTotalAmount(totalAmount1+shopAccount1.getTotalAmount());
                shopAccount1.setMoney(money1+shopAccount1.getMoney());
                ShopAccountView shopAccountView1=new ShopAccountView();
                TorinoSrcBeanUtils.copyBean(shopAccount1,shopAccountView1);
                System.out.println("第三层：shopAccountView money" + shopAccountView1.getMoney());
                shopAccountService.updateEntity(shopAccountView1);

            }else {
                // 两层
                ShopAccount shopAccount2=shopAccountDao.findShopAccountByShopId(orderView.getShopId());
                shopAccount2.setTotalAmount(totalAmount2+shopAccount2.getTotalAmount());
                shopAccount2.setMoney(money2+shopAccount2.getMoney());
                ShopAccountView shopAccountView2=new ShopAccountView();
                TorinoSrcBeanUtils.copyBean(shopAccount2,shopAccountView2);
                shopAccountService.updateEntity(shopAccountView2);

                ShopAccount shopAccount1=shopAccountDao.findShopAccountByShopId(shopTreeLevel1.getShopId());
                shopAccount1.setTotalAmount(totalAmount1+shopAccount1.getTotalAmount());
                shopAccount1.setMoney(money1+shopAccount1.getMoney());
                ShopAccountView shopAccountView1=new ShopAccountView();
                TorinoSrcBeanUtils.copyBean(shopAccount1,shopAccountView1);
                System.out.println("第二层：shopAccountView money" + shopAccountView1.getMoney());
                shopAccountService.updateEntity(shopAccountView1);
            }
        }else{
            // 一层
            ShopAccount shopAccount1=shopAccountDao.findShopAccountByShopId(orderView.getShopId());

            System.out.println("第一层：shopAccount1 money" + shopAccount1.getMoney());
            System.out.println("第一层：totalAmount1 money" + totalAmount1);
            shopAccount1.setTotalAmount(totalAmount1+shopAccount1.getTotalAmount());
            shopAccount1.setMoney(money1+shopAccount1.getMoney());
            ShopAccountView shopAccountView=new ShopAccountView();
            TorinoSrcBeanUtils.copyBean(shopAccount1,shopAccountView);
            System.out.println("第一层：shopAccountView money" + shopAccountView.getMoney());
            shopAccountService.updateEntity(shopAccountView);
        }

    }

    @Override
    public void deleteEntity(long id) {
        ShopAccountDetail shopAccountDetail = new ShopAccountDetail();
        shopAccountDetail.setId(id);
        shopAccountDetailDao.delete(shopAccountDetail);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<ShopAccountDetail> shopAccountDetails = new ArrayList<>();
        for(String entityId : entityIds){
            ShopAccountDetail shopAccountDetail = new ShopAccountDetail();
            shopAccountDetail.setId(Long.valueOf(entityId));
            shopAccountDetails.add(shopAccountDetail);
        }
        shopAccountDetailDao.deleteInBatch(shopAccountDetails);
    }

    @Override
    public void updateEntity(ShopAccountDetailView shopAccountDetailView) {
        Specification<ShopAccountDetail> shopAccountDetailSpecification = Optional.ofNullable(shopAccountDetailView).map( s -> {
            return new Specification<ShopAccountDetail>() {
                @Override
                public Predicate toPredicate(Root<ShopAccountDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("ShopAccountDetailView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<ShopAccountDetail> shopAccountDetailOptionalBySearch = shopAccountDetailDao.findOne(shopAccountDetailSpecification);
        shopAccountDetailOptionalBySearch.map(shopAccountDetailBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(shopAccountDetailView,shopAccountDetailBySearch);
            shopAccountDetailBySearch.setUpdateTime(System.currentTimeMillis());
            shopAccountDetailDao.save(shopAccountDetailBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + shopAccountDetailView.getId() + "的数据记录"));
    }


    /**
     * 计算会员订单各级分销商利润
     * @param orderView
     */
    @Override
    @Transactional(rollbackOn = { Exception.class})
    public void calculateMemberOrderProfit(OrderView orderView) {

        // 计算订单各层分销商利润
        Set<OrderDetail> orderDetails = orderView.getOrderDetails();
        // 对应层分销商账号总金额
        Long totalAmount1 = 0L, totalAmount2 = 0L, totalAmount3 = 0L;
        // 对应层分销商账号现有金额
        Long money1 = 0L, money2 = 0L, money3 = 0L;

        long currentShopId = orderView.getShopId();
        int shopLevel = this.getShopLevel(currentShopId);
        ShopTree currentShopTree = shopTreeDao.findByShopId(currentShopId);
        ShopTree parentShopTree = shopTreeDao.findByShopId(currentShopTree.getParentShopId());
        ShopAccount parentShopAccount = shopAccountDao.findShopAccountByShopId(currentShopTree.getParentShopId());

        for (OrderDetail orderDetail : orderDetails) {
            // 销售流水基础信息
            ShopAccountDetailView shopAccountDetailView = new ShopAccountDetailView();
            MembershipGrade membershipGrade = membershipGradeDao.getOne(orderDetail.getMembershipGradeId());
            shopAccountDetailView.setOrderDetailId(orderDetail.getId());
            shopAccountDetailView.setOrderId(orderView.getId());
            shopAccountDetailView.setProductName(membershipGrade.getName());
            shopAccountDetailView.setShopId(orderView.getShopId());
            ShopAccount shopAccount = shopAccountDao.findShopAccountByShopId(orderView.getShopId());
            shopAccountDetailView.setShopAccountId(shopAccount.getId());
            shopAccountDetailView.setSaleCount(orderDetail.getCount());

            // 佣金金额
            long totalCommission = membershipGrade.getCommission() * orderDetail.getCount();
            // 商品差价=卖出价-商品市场价
            long profit = (orderDetail.getPrice() - membershipGrade.getPrice()) * orderDetail.getCount();

            // 会员销售分成
            int percent1 = membershipGrade.getPercent1();
            int percent2 = membershipGrade.getPercent2();
            int percent3 = membershipGrade.getPercent3();

            Long level2Commission;
            switch (shopLevel) {
                case 1:
                    // 我是一级分销商，拿全部分成
                    shopAccountDetailView.setIncomeSource(1);
                    shopAccountDetailView.setIncomeAmount(totalCommission + profit);
                    saveEntity(shopAccountDetailView);

                    // 店铺总收入,现有金额+原有
                    totalAmount1 = totalAmount1 + totalCommission + profit;
                    money1 = money1 + totalCommission + profit;

                    break;
                case 2:
                    // 我是二级分销商，拿二级分成和三级分成
                    level2Commission = (percent2 + percent3) * totalCommission / 100;
                    shopAccountDetailView.setIncomeAmount(level2Commission + profit);
                    shopAccountDetailView.setIncomeSource(1);

                    // 父级佣金
                    ShopAccountDetailView parentShopAccountDetailView = new ShopAccountDetailView();
                    TorinoSrcBeanUtils.copyBean(shopAccountDetailView, parentShopAccountDetailView);
                    parentShopAccountDetailView.setShopId(parentShopTree.getShopId());
                    parentShopAccountDetailView.setShopAccountId(parentShopAccount.getId());
                    parentShopAccountDetailView.setIncomeSource(0);
                    parentShopAccountDetailView.setIncomeAmount(totalCommission - level2Commission);
                    saveEntity(shopAccountDetailView);
                    saveEntity(parentShopAccountDetailView);

                    // 店铺总收入,现有金额+原有
                    totalAmount2 = totalAmount2 + level2Commission + profit;
                    money2 = money2 + level2Commission + profit;
                    totalAmount1 = totalAmount1 + totalCommission - level2Commission;
                    money1 = money1 + totalCommission - level2Commission;
                    break;
                case 3:
                    // 三级分销商
                    long level3Commission = percent3 * totalCommission / 100;
                    shopAccountDetailView.setIncomeAmount(level3Commission + profit);
                    shopAccountDetailView.setIncomeSource(1);

                    // 二级分销商佣金
                    ShopAccountDetailView level2ShopAccountDetailView = new ShopAccountDetailView();
                    TorinoSrcBeanUtils.copyBean(shopAccountDetailView, level2ShopAccountDetailView);
                    level2ShopAccountDetailView.setShopId(parentShopTree.getShopId());
                    level2ShopAccountDetailView.setShopAccountId(parentShopAccount.getId());
                    level2ShopAccountDetailView.setIncomeSource(0);
                    level2Commission = percent2 * totalCommission / 100;
                    level2ShopAccountDetailView.setIncomeAmount(level2Commission);

                    // 一级分销商佣金
                    Shop grandParentShop = shopDao.getOne(parentShopTree.getParentShopId());
                    ShopAccountDetailView grandParentShopAccountDetailView = new ShopAccountDetailView();
                    TorinoSrcBeanUtils.copyBean(shopAccountDetailView, grandParentShopAccountDetailView);
                    grandParentShopAccountDetailView.setShopId(grandParentShop.getId());
                    ShopAccount grandParentShopAccount = shopAccountDao.findShopAccountByShopId(parentShopTree.getParentShopId());
                    grandParentShopAccountDetailView.setShopAccountId(grandParentShopAccount.getId());
                    grandParentShopAccountDetailView.setIncomeSource(0);
                    Long level1Commission = totalCommission - level3Commission - level2Commission;
                    grandParentShopAccountDetailView.setIncomeAmount(level1Commission);

                    saveEntity(shopAccountDetailView);
                    saveEntity(level2ShopAccountDetailView);
                    saveEntity(grandParentShopAccountDetailView);

                    // 店铺总收入,现有金额+原有
                    totalAmount3 = totalAmount3 + level3Commission + profit;
                    money3 = money3 + level3Commission + profit;
                    totalAmount2 = totalAmount2 + level2Commission;
                    money2 = money2 + level2Commission;
                    totalAmount1 = totalAmount1 + level1Commission;
                    money1 = money1 + level1Commission;
                    break;
                default:
                    throw new TorinoSrcServiceException("不合法的店铺等级，shopLevel = " + shopLevel + " - shopId = " + currentShopId);
            }
        }

        ShopAccount level1ShopAccountFromDB;
        ShopAccount level2ShopAccountFromDB;
        ShopAccountView shopAccountView3;
        ShopAccountView shopAccountView2;
        ShopAccountView shopAccountView1;
        switch (shopLevel) {
            case 1:
                level1ShopAccountFromDB = shopAccountDao.findShopAccountByShopId(currentShopId);
                level1ShopAccountFromDB.setTotalAmount(totalAmount1 + level1ShopAccountFromDB.getTotalAmount());
                level1ShopAccountFromDB.setMoney(money1 + level1ShopAccountFromDB.getMoney());
                shopAccountView1 = new ShopAccountView();
                TorinoSrcBeanUtils.copyBean(level1ShopAccountFromDB, shopAccountView1);
                shopAccountService.updateEntity(shopAccountView1);
                break;
            case 2:
                // 两层
                level2ShopAccountFromDB = shopAccountDao.findShopAccountByShopId(currentShopId);
                level2ShopAccountFromDB.setTotalAmount(totalAmount2 + level2ShopAccountFromDB.getTotalAmount());
                level2ShopAccountFromDB.setMoney(money2 + level2ShopAccountFromDB.getMoney());
                shopAccountView2 = new ShopAccountView();
                TorinoSrcBeanUtils.copyBean(level2ShopAccountFromDB, shopAccountView2);
                shopAccountService.updateEntity(shopAccountView2);

                level1ShopAccountFromDB = shopAccountDao.findShopAccountByShopId(parentShopTree.getShopId());
                level1ShopAccountFromDB.setTotalAmount(totalAmount1 + level1ShopAccountFromDB.getTotalAmount());
                level1ShopAccountFromDB.setMoney(money1 + level1ShopAccountFromDB.getMoney());
                shopAccountView1 = new ShopAccountView();
                TorinoSrcBeanUtils.copyBean(level1ShopAccountFromDB, shopAccountView1);
                shopAccountService.updateEntity(shopAccountView1);
                break;
            case 3:
                // 三层
                ShopAccount level3ShopAccountFromDB = shopAccountDao.findShopAccountByShopId(orderView.getShopId());
                level3ShopAccountFromDB.setTotalAmount(totalAmount3 + level3ShopAccountFromDB.getTotalAmount());
                level3ShopAccountFromDB.setMoney(money3 + level3ShopAccountFromDB.getMoney());
                shopAccountView3 = new ShopAccountView();
                TorinoSrcBeanUtils.copyBean(level3ShopAccountFromDB, shopAccountView3);
                shopAccountService.updateEntity(shopAccountView3);

                level2ShopAccountFromDB = shopAccountDao.findShopAccountByShopId(parentShopAccount.getShopId());
                level2ShopAccountFromDB.setTotalAmount(totalAmount2 + level2ShopAccountFromDB.getTotalAmount());
                level2ShopAccountFromDB.setMoney(money2 + level2ShopAccountFromDB.getMoney());
                shopAccountView2 = new ShopAccountView();
                TorinoSrcBeanUtils.copyBean(level2ShopAccountFromDB, shopAccountView2);
                shopAccountService.updateEntity(shopAccountView2);

                level1ShopAccountFromDB = shopAccountDao.findShopAccountByShopId(parentShopTree.getParentShopId());
                level1ShopAccountFromDB.setTotalAmount(totalAmount1 + level1ShopAccountFromDB.getTotalAmount());
                level1ShopAccountFromDB.setMoney(money1 + level1ShopAccountFromDB.getMoney());
                shopAccountView1 = new ShopAccountView();
                TorinoSrcBeanUtils.copyBean(level1ShopAccountFromDB, shopAccountView1);
                shopAccountService.updateEntity(shopAccountView1);
                break;
            default:
                throw new TorinoSrcServiceException("不合法的店铺等级，shopLevel = " + shopLevel + " - shopId = " + currentShopId);
        }
    }

    /**
     * 获取店铺等级
     * @param shopId
     * @return
     */
    private Integer getShopLevel(Long shopId) {

        ShopTree shopTree = shopTreeDao.findByShopId(shopId);
        int level;

        if (ObjectUtils.isEmpty(shopTree)) {
            throw new TorinoSrcServiceException("shopId 为 " + shopId + " 的店铺不在 shopTree 中");
        }

        if (shopTree.getParentShopId() == 0) {
            level = 1;
        } else {
            ShopTree parentShopTree = shopTreeDao.findByShopId(shopTree.getParentShopId());
            // 有父节点且父节点的父节点不是顶级店铺0
            long grandParentShopId = parentShopTree.getParentShopId();
            if (!ObjectUtils.isEmpty(parentShopTree) && grandParentShopId != 0) {
                level = 3;
            } else {
                level = 2;
            }
        }

        return level;
    }

    // 全部退款（进行店铺账户减款操作）
    @Transactional(rollbackOn = { Exception.class })
    @Override
    public void reduceAccountByOrder(OrderView orderView) {
        // 计算订单各层分销商利润
        Set<OrderDetail> orderDetails=orderView.getOrderDetails();

        Long totalAmount1=0L,totalAmount2=0L,totalAmount3=0L;  // 对应层分销商账号总金额
        Long money1=0L,money2=0L,money3=0L;  // 对应层分销商账号现有金额

        for(OrderDetail orderDetail:orderDetails){
            ShopAccountDetailView shopAccountDetailView =new ShopAccountDetailView();
            ProductDetailSnapshot productDetailSnapshot=productDetailSnapshotDao.getOne(orderDetail.getProductDetailSnapshotId());
            shopAccountDetailView.setOrderDetailId(orderDetail.getId());
            shopAccountDetailView.setOrderId(orderView.getId());
            shopAccountDetailView.setShopProductDetailSnapshotId(orderDetail.getShopProductDetailSnapshotId());
            shopAccountDetailView.setProductName(productDetailSnapshot.getProductSnapshot().getName());
            if(!ObjectUtils.isEmpty(orderDetail.getShopProductDetailSnapshotId())){
                ShopProductDetailSnapshot shopProductDetailSnapshot = shopProductDetailSnapshotDao.getOne(orderDetail.getShopProductDetailSnapshotId());
                shopAccountDetailView.setShopProductSnapshotId(shopProductDetailSnapshot.getShopProductSnapshotId());
            }

            shopAccountDetailView.setProductDetailSnapshotId(orderDetail.getProductDetailSnapshotId());
            shopAccountDetailView.setShopId(orderView.getShopId());
            ShopAccount shopAccount=shopAccountDao.findShopAccountByShopId(orderView.getShopId());
            shopAccountDetailView.setShopAccountId(shopAccount.getId());
            shopAccountDetailView.setSaleCount(orderDetail.getCount());
            //佣金金额
            long commission =productDetailSnapshot.getCommission()* orderDetail.getCount();
            //商品差价=卖出价-商品市场价
            long profit=(orderDetail.getPrice()-productDetailSnapshot.getPrice())*orderDetail.getCount();
            //1、根据shopId查询存在几层分销商
            ShopTree shopTree = shopTreeDao.findByShopId(orderView.getShopId());
            if(!ObjectUtils.isEmpty(shopTree) && shopTree.getParentShopId()!=0){
                ShopTree shopTreeLevel1 = shopTreeDao.findByShopId(shopTree.getParentShopId());
                ShopAccount shopAccount2=shopAccountDao.findShopAccountByShopId(shopTree.getParentShopId());
                if(!ObjectUtils.isEmpty(shopTreeLevel1) && shopTreeLevel1.getParentShopId()!=0){
                    //三级分销商
                    Shop shop = shopDao.getOne(shopTreeLevel1.getParentShopId());
                    Long level3Commission =(long) shop.getPercent3()*commission/100;
                    shopAccountDetailView.setIncomeAmount(level3Commission+profit);
                    shopAccountDetailView.setIncomeSource(1);
                    shopAccountDetailView.setRecordStatus(1);

                    //二级佣金
                    ShopAccountDetailView level2ShopAccountDetailView = new ShopAccountDetailView();
                    TorinoSrcBeanUtils.copyBean(shopAccountDetailView,level2ShopAccountDetailView);
                    level2ShopAccountDetailView.setShopId(shopTreeLevel1.getShopId());

                    level2ShopAccountDetailView.setShopAccountId(shopAccount2.getId());
                    level2ShopAccountDetailView.setIncomeSource(0);
                    level2ShopAccountDetailView.setRecordStatus(1);
                    Long level2Commission =(long) shop.getPercent2()*commission/100;
                    level2ShopAccountDetailView.setIncomeAmount(level2Commission);

                    //一级佣金
                    ShopAccountDetailView parentShopAccountDetailView = new ShopAccountDetailView();
                    TorinoSrcBeanUtils.copyBean(shopAccountDetailView,parentShopAccountDetailView);
                    parentShopAccountDetailView.setShopId(shop.getId());
                    ShopAccount shopAccount3=shopAccountDao.findShopAccountByShopId(shopTreeLevel1.getParentShopId());
                    parentShopAccountDetailView.setShopAccountId(shopAccount3.getId());
                    parentShopAccountDetailView.setIncomeSource(0);
                    parentShopAccountDetailView.setRecordStatus(1);
                    Long level1Commission =commission-level3Commission-level2Commission;
                    parentShopAccountDetailView.setIncomeAmount(level1Commission);

                    saveEntity(shopAccountDetailView);
                    saveEntity(level2ShopAccountDetailView);
                    saveEntity(parentShopAccountDetailView);

                    // 店铺总收入,现有金额+原有
                    totalAmount3=totalAmount3+level3Commission+profit;
                    money3=money3+level3Commission+profit;
                    totalAmount2=totalAmount2+level2Commission ;
                    money2=money2+level2Commission ;
                    totalAmount1=totalAmount1+level1Commission;
                    money1=money1+level1Commission;

                }else{
                    //二级分销商
                    Shop shop = shopDao.getOne(shopTree.getParentShopId());
//                    BigDecimal percent=new BigDecimal((double)(shop.getPercent2()+shop.getPercent3())/100).setScale(2,BigDecimal.ROUND_HALF_UP);
                    Long level2Commission =(shop.getPercent2()+shop.getPercent3())*commission/100;
                    shopAccountDetailView.setIncomeAmount(level2Commission+profit);
                    shopAccountDetailView.setIncomeSource(1);
                    shopAccountDetailView.setRecordStatus(1);

                    //父级佣金
                    ShopAccountDetailView parentShopAccountDetailView = new ShopAccountDetailView();
                    TorinoSrcBeanUtils.copyBean(shopAccountDetailView,parentShopAccountDetailView);
                    parentShopAccountDetailView.setShopId(shopTreeLevel1.getShopId());
                    parentShopAccountDetailView.setShopAccountId(shopAccount2.getId());
                    parentShopAccountDetailView.setIncomeSource(0);
                    parentShopAccountDetailView.setRecordStatus(1);
                    parentShopAccountDetailView.setIncomeAmount(commission-level2Commission);

                    saveEntity(shopAccountDetailView);
                    saveEntity(parentShopAccountDetailView);

                    // 店铺总收入,现有金额+原有
                    totalAmount2=totalAmount2+level2Commission+profit;
                    money2=money2+level2Commission+profit;
                    totalAmount1=totalAmount1+commission-level2Commission;
                    money1=money1+commission-level2Commission;
                }

            }else{
                //一级分销商
                shopAccountDetailView.setIncomeSource(1);
                shopAccountDetailView.setRecordStatus(1);
                shopAccountDetailView.setIncomeAmount(commission+profit);
                saveEntity(shopAccountDetailView);

                // 店铺总收入,现有金额+原有
                totalAmount1=totalAmount1+commission+profit;
                money1=money1+commission+profit;
            }
        }

        ShopTree shopTree = shopTreeDao.findByShopId(orderView.getShopId());
        if(!ObjectUtils.isEmpty(shopTree) && shopTree.getParentShopId()!=0){
            ShopTree shopTreeLevel1 = shopTreeDao.findByShopId(shopTree.getParentShopId());
            if(!ObjectUtils.isEmpty(shopTreeLevel1) && shopTreeLevel1.getParentShopId()!=0){
                // 三层
                ShopAccount shopAccount3=shopAccountDao.findShopAccountByShopId(orderView.getShopId());
                shopAccount3.setTotalAmount(shopAccount3.getTotalAmount()-totalAmount3);
                shopAccount3.setMoney(shopAccount3.getMoney()-money3);
                ShopAccountView shopAccountView3=new ShopAccountView();
                TorinoSrcBeanUtils.copyBean(shopAccount3,shopAccountView3);
                shopAccountService.updateEntity(shopAccountView3);

                ShopAccount shopAccount2=shopAccountDao.findShopAccountByShopId(shopTreeLevel1.getShopId());
                shopAccount2.setTotalAmount(shopAccount2.getTotalAmount()-totalAmount2);
                shopAccount2.setMoney(shopAccount2.getMoney()-money2);
                ShopAccountView shopAccountView2=new ShopAccountView();
                TorinoSrcBeanUtils.copyBean(shopAccount2,shopAccountView2);
                shopAccountService.updateEntity(shopAccountView2);

                ShopAccount shopAccount1=shopAccountDao.findShopAccountByShopId(shopTreeLevel1.getParentShopId());
                shopAccount1.setTotalAmount(shopAccount1.getTotalAmount()-totalAmount1);
                shopAccount1.setMoney(shopAccount1.getMoney()-money1);
                ShopAccountView shopAccountView1=new ShopAccountView();
                TorinoSrcBeanUtils.copyBean(shopAccount1,shopAccountView1);
                shopAccountService.updateEntity(shopAccountView1);

            }else {
                // 两层
                ShopAccount shopAccount2=shopAccountDao.findShopAccountByShopId(orderView.getShopId());
                shopAccount2.setTotalAmount(shopAccount2.getTotalAmount()-totalAmount2);
                shopAccount2.setMoney(shopAccount2.getMoney()-money2);
                ShopAccountView shopAccountView2=new ShopAccountView();
                TorinoSrcBeanUtils.copyBean(shopAccount2,shopAccountView2);
                shopAccountService.updateEntity(shopAccountView2);

                ShopAccount shopAccount1=shopAccountDao.findShopAccountByShopId(shopTreeLevel1.getShopId());
                shopAccount1.setTotalAmount(shopAccount1.getTotalAmount()-totalAmount1);
                shopAccount1.setMoney(shopAccount1.getMoney()-money1);
                ShopAccountView shopAccountView1=new ShopAccountView();
                TorinoSrcBeanUtils.copyBean(shopAccount1,shopAccountView1);
                shopAccountService.updateEntity(shopAccountView1);
            }
        }else{
            // 一层
            ShopAccount shopAccount1=shopAccountDao.findShopAccountByShopId(orderView.getShopId());

            shopAccount1.setTotalAmount(shopAccount1.getTotalAmount()-totalAmount1);
            shopAccount1.setMoney(shopAccount1.getMoney()-money1);
            ShopAccountView shopAccountView=new ShopAccountView();
            TorinoSrcBeanUtils.copyBean(shopAccount1,shopAccountView);
            shopAccountService.updateEntity(shopAccountView);
        }

    }

}
