package com.torinosrc.schedule;


import com.torinosrc.dao.order.OrderDao;
import com.torinosrc.dao.orderdetail.OrderDetailDao;
import com.torinosrc.dao.productdetail.ProductDetailDao;
import com.torinosrc.model.entity.order.Order;
import com.torinosrc.model.entity.orderdetail.OrderDetail;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.service.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ProductInventoryScheduler {

    private static final Logger LOG = LoggerFactory
            .getLogger(ProductInventoryScheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private OrderService orderService;

    /**
     * 30分钟 1800000L
     */
    private static Long PAY_DEAD_LINE = 1800000L;

    /**
     * 超过30分钟未支付，则关闭订单，并还原库存
     */
    @Scheduled(fixedRate = 60000)
    @Transactional(rollbackOn = {Exception.class})
    public void productDetailInventory() {

        orderService.closeUnpaidOrderAndRestoreInventory();

//        // 根据没有支付状态查找对应的订单
//        List<Order> orders = orderDao.findByStatus(0);
//        System.out.println("orders size: " + orders.size());
//        Long currentDateTime = System.currentTimeMillis();
//
//        for (Order order : orders) {
//            // 如果当前时间大于updateTime 30分钟，则关闭订单
//            Long timeSlap = currentDateTime - order.getUpdateTime();
//            if (timeSlap > PAY_DEAD_LINE) {
//                // 关闭交易
//                order.setStatus(6);
//                order.setUpdateTime(currentDateTime);
//                orderDao.save(order);
//
//                // 0：普通订单 1：拼团订单 2：购买会员订单 3：免费领订单 4：助力购订单
//                Integer orderType = order.getOrderType();
//
//                // 对应增加库存
//                List<OrderDetail> orderDetails = orderDetailDao.findByOrderId(order.getId());
//                for (OrderDetail orderDetail : orderDetails) {
//                    System.out.println("开始处理orderDetail==" + orderDetail.getId());
//                    Integer buyCount = orderDetail.getCount();
//
//                    Long productDetailId = orderDetail.getProductDetailId();
//                    System.out.println("productDetailId == " + productDetailId);
//                    ProductDetail productDetail = productDetailDao.getOne(productDetailId);
//
//                    // 加到库存
//                    System.out.println("product detail id:" + productDetailId + ",原来库存: " + productDetail.getInventory() + "，购买件数:" + buyCount);
//                    productDetail.setInventory(productDetail.getInventory() + buyCount);
//                    productDetail.setUpdateTime(System.currentTimeMillis());
//                    productDetailDao.save(productDetail);
//                    System.out.println("现在库存: " + productDetail.getInventory());
//                }
//            }
//        }
    }


}
