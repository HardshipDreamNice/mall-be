package com.torinosrc.schedule;


import com.alibaba.fastjson.JSON;
import com.torinosrc.commons.constants.MallConstant;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.dao.order.OrderDao;
import com.torinosrc.model.entity.order.Order;
import com.torinosrc.model.entity.shopaccount.ShopAccount;
import com.torinosrc.model.view.order.OrderView;
import com.torinosrc.service.order.OrderService;
import com.torinosrc.service.shopaccount.ShopAccountService;
import com.torinosrc.service.shopaccountdetail.ShopAccountDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 订单状态自动更新job
 */
@Component
public class OrderScheduler {

    private static final Logger LOG = LoggerFactory
            .getLogger(OrderScheduler.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ShopAccountDetailService shopAccountDetailService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShopAccountService shopAccountService;

    /**
     * 10天
     */
    private static Long PRODUCT_RECEIVE_DEAD_LINE = 10 * 24 * 60 * 60 * 1000L;

//    private static Long PRODUCT_RECEIVE_DEAD_LINE= 2*60*1000L;

    /**
     * 每 1 分跑 1 次
     * 卖家发货10天后，若客户未确认收货，自动更新订单到已收货
     * 买家收货10天后，自动更新订单到交易成功
     */
    @Transactional(rollbackOn = {Exception.class})
    @Scheduled(cron = "0 0 1 * * *")
    public void orderManager() {
        updateOrderToReceive();
        updateOrderToComplete();
    }

    /**
     * 卖家发货10天后，若客户未确认收货，自动更新订单到已收货
     */
    public void updateOrderToReceive() {
        //根据没有支付状态查找对应的订单
        List<Order> orders = orderDao.findByStatus(2);
        Long currentDateTime = System.currentTimeMillis();

        for (Order order : orders) {
            //如果当前时间大于updateTime 10天，则更新订单到已收货
            Long timeSlap = currentDateTime - order.getUpdateTime();
            if (timeSlap > PRODUCT_RECEIVE_DEAD_LINE) {
                //自动更新订到状态到已收货
                LOG.info("自动更新订单状态到已收货: " + order.getOrderNo());
                order.setStatus(3);
                order.setUpdateTime(currentDateTime);
                orderDao.save(order);
                LOG.info("成功自动更新订单状态到已收货: " + order.getOrderNo());
            }
        }

    }

    /**
     * 卖家收货10天后，自动更新订单到交易成功
     */
    public void updateOrderToComplete() {
        //根据没有支付状态查找对应的订单
        List<Order> orders = orderDao.findByStatus(3);
        Long currentDateTime = System.currentTimeMillis();

        for (Order order : orders) {
            //如果当前时间大于updateTime 10天，则订单交易成功
            Long timeSlap = currentDateTime - order.getUpdateTime();
            if (timeSlap > PRODUCT_RECEIVE_DEAD_LINE) {
                //自动更新订到状态到交易成功
                LOG.info("自动更新订单状态到交易成功: " + order.getOrderNo());
                order.setStatus(5);
                order.setUpdateTime(currentDateTime);
                orderDao.save(order);
                LOG.info("成功自动更新订单状态到交易成功: " + order.getOrderNo());
            }
        }

    }

    /**
     * 每10分,更新商户的收益
     */
    @Transactional(rollbackOn = {Exception.class})
    @Scheduled(fixedRate = 60 * 1000 * 2)
    public void updateOrderToCalculateShopAccount() {
        List<Order> orderList = orderDao.findByStatusAndUpdateStatusAndOrderType(5, 0,MallConstant.ORDER_TYPE_GENERAL);
        if (!ObjectUtils.isEmpty(orderList)) {
            for (Order order : orderList) {
                OrderView orderView = new OrderView();
                TorinoSrcBeanUtils.copyBean(order, orderView);
                shopAccountDetailService.calculateOrderProfit(orderView);
                // 更新状态
                order.setUpdateStatus(1);
                order.setUpdateTime(System.currentTimeMillis());
                orderDao.save(order);
                LOG.info("以下订单的分成已成功更新各商户的收入中:" + JSON.toJSONString(order));
            }
        }
    }

    /**
     * 每 2 分钟，更新商户的收益（销售会员的收益）
     */
    @Transactional(rollbackOn = {Exception.class})
    @Scheduled(fixedRate = 60 * 1000 * 2)
    public void updateMemberOrderToCalculateShopAccount() {
        shopAccountService.updateShopAccountSaleMember();
    }

    // 1分钟检测不成团的订单，进行退款操作
//    @Scheduled(fixedRate = 60000)
    public void checkFailGroup() {
        orderService.checkFailGroup();
    }


}
