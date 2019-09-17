package com.torinosrc.service.order;

import com.torinosrc.TorinosrcSpringBootBeApplication;
import com.torinosrc.dao.productdetailsnapshot.ProductDetailSnapshotDao;
import com.torinosrc.dao.productsnapshot.ProductSnapshotDao;
import com.torinosrc.model.entity.orderdetail.OrderDetail;
import com.torinosrc.model.view.order.OrderPageView;
import com.torinosrc.model.view.order.OrderView;
import com.torinosrc.model.view.shopaccount.ShopAccountView;
import com.torinosrc.schedule.OrderScheduler;
import com.torinosrc.service.shopaccount.ShopAccountService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TorinosrcSpringBootBeApplication.class)
public class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductSnapshotDao productSnapshotDao;

    @Autowired
    private ProductDetailSnapshotDao productDetailSnapshotDao;

    @Autowired
    private ShopAccountService shopAccountService;

    @Autowired
    private OrderScheduler orderScheduler;

    @Test
    public void testCreateOrder() {
        OrderView orderView = new OrderView();
        orderView.setTotalFee(30);
        orderView.setUserId(1l);
        orderView.setShopId(1l);
        orderView.setCustomerConsigneeString("{\"id\":55,\"createTime\":1531794041480,\"updateTime\":1531794041480,\"enabled\":1,\"condition\":null,\"address\":\"啊啊啊啊啊啊啊啊啊\",\"province\":\"广东省\",\"city\":\"广州市\",\"county\":\"荔湾区\",\"addressDetail\":null,\"contact\":\"啊啊啊\",\"phone\":\"12345667890\",\"autoAddr\":0,\"userId\":13}");
        Set<OrderDetail> orderDetails = new HashSet<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCount(1);
        orderDetail.setProductDetailId(1l);
        orderDetail.setShopProductDetailId(1l);
        orderDetails.add(orderDetail);
        orderView.setOrderDetails(orderDetails);
        orderService.saveEntity(orderView);

    }


    @Test
    public void testCreateOrder2() {
        OrderView orderView = new OrderView();
        orderView.setTotalFee(30);
        orderView.setUserId(1l);
        orderView.setShopId(1l);
        orderView.setCustomerConsigneeString("{\"id\":55,\"createTime\":1531794041480,\"updateTime\":1531794041480,\"enabled\":1,\"condition\":null,\"address\":\"啊啊啊啊啊啊啊啊啊\",\"province\":\"广东省\",\"city\":\"广州市\",\"county\":\"荔湾区\",\"addressDetail\":null,\"contact\":\"啊啊啊\",\"phone\":\"12345667890\",\"autoAddr\":0,\"userId\":13}");
        Set<OrderDetail> orderDetails = new HashSet<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCount(1);
        orderDetail.setProductDetailId(1l);
        orderDetails.add(orderDetail);
        orderView.setOrderDetails(orderDetails);
        orderService.saveEntity(orderView);

    }


    @Test
    public void testGetOrder() {
        OrderView orderView2 = orderService.findByOrderNo("1");
        OrderView orderView = orderService.getEntity(2);
//        orderService.findByUserIdAndstatus(1l,99);
    }

    @Test
    public void testDeleteOrder() {
        orderService.deleteEntities("3");

    }

    @Test
    public void testShopAccountRealIncome() {

        ShopAccountView shopAccountView = new ShopAccountView();
        shopAccountView.setShopId(1L);
        ShopAccountView shopAccountView1 = shopAccountService.getRealIncome(shopAccountView);

        System.out.println("今日收入： " + shopAccountView.getIncomeAmountDay());
    }

    @Test
    public void testOrderScheduler() {
        orderScheduler.updateOrderToCalculateShopAccount();
    }

    @Test
    public void testAOrder() {

        ShopAccountView shopAccountView = new ShopAccountView();
        shopAccountView.setShopId(1L);
        ShopAccountView shopAccountView1 = shopAccountService.getRealIncome(shopAccountView);

        System.out.println("shopAccountView1: " + shopAccountView1.getIncomeAmountAll());
    }

    //免费领下单测试
    @Test
    public void testProductFreeRecevieOrder() {
        Long userId = 15L;
        Long shopId = 12L;
        Integer totalFee = 0;
        Long productFreeReceiveDetailId = 1L;
        String customerConsigneeString = "{\"id\":55,\"createTime\":1531794041480,\"updateTime\":1531794041480,\"enabled\":1,\"condition\":null,\"address\":\"啊啊啊啊啊啊啊啊啊\",\"province\":\"广东省\",\"city\":\"广州市\",\"county\":\"荔湾区\",\"addressDetail\":null,\"contact\":\"啊啊啊\",\"phone\":\"12345667890\",\"autoAddr\":0,\"userId\":13}";

        OrderView orderView = new OrderView();
        orderView.setTotalFee(totalFee);
        orderView.setUserId(userId);
        orderView.setShopId(shopId);
        orderView.setProductFreeReceiveDetailId(productFreeReceiveDetailId);
        orderView.setCustomerConsigneeString(customerConsigneeString);
        Set<OrderDetail> orderDetails = new HashSet<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCount(1);
        orderDetails.add(orderDetail);
        orderView.setOrderDetails(orderDetails);
        orderService.saveProductFreeReceiveOrder(orderView);
    }


    @Test
    public void testSaveBoostOrder() {

        Long productDetailId = 7L;
        Integer totalFee = 4;
        Long userId = 15L;
        Long shopId = 12L;
        Integer buyCount = 2;
        String customerConsigneeString = "{\"id\":55,\"createTime\":1531794041480,\"updateTime\":1531794041480,\"enabled\":1,\"condition\":null,\"address\":\"啊啊啊啊啊啊啊啊啊\",\"province\":\"广东省\",\"city\":\"广州市\",\"county\":\"荔湾区\",\"addressDetail\":null,\"contact\":\"啊啊啊\",\"phone\":\"12345667890\",\"autoAddr\":0,\"userId\":13}";

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductDetailId(productDetailId);
        orderDetail.setCount(buyCount);
        Set<OrderDetail> orderDetailSet = new HashSet<>();
        orderDetailSet.add(orderDetail);

        OrderView orderView = new OrderView();
        orderView.setTotalFee(totalFee);
        orderView.setUserId(userId);
        orderView.setShopId(shopId);
        orderView.setCustomerConsigneeString(customerConsigneeString);
        orderView.setOrderDetails(orderDetailSet);

        OrderView orderViewResult = orderService.saveBoostOrder(orderView);

        Assert.assertNotNull(orderViewResult);
    }

    @Test
    public void testGetEntitiesByUserId() {

        Long userId = 11L;
        Integer status = 99;


        OrderPageView orderPageView = orderService.findByUserIdAndstatus(userId, status, 0, 1000);

        Assert.assertNotNull(orderPageView);
    }

}
