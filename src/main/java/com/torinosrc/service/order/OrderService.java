/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.order;

import com.torinosrc.model.entity.membershipgrade.MembershipGrade;
import com.torinosrc.model.view.order.GroupOrderView;
import com.torinosrc.model.view.order.MembershipOrderView;
import com.torinosrc.model.view.order.OrderPageView;
import com.torinosrc.model.view.order.OrderView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

/**
* <b><code>Order</code></b>
* <p/>
* Order的具体实现
* <p/>
* <b>Creation Time:</b> 2018-06-06 11:31:03.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface OrderService extends BaseService<OrderView> {
    public OrderView findByOrderNo(String orderNo);
    public OrderPageView findByUserIdAndstatus(Long userId, int status, int pageNumber, int pageSize);
    public OrderPageView findByUserIdAndShopIdAndStatus(Long userId,Long shopId,int status,int pageNumber,int pageSize);
    public void updateEntityStatus(OrderView orderView);
    public GroupOrderView spellGroupOrder(GroupOrderView groupOrderView);
    public GroupOrderView addGroupOrder(GroupOrderView groupOrderView);
    public void checkFailGroup();
    public void saveTeamUser(Long teamId,Long userId,Long productId);
    public Long saveTeam(Long productId,Long userId);
    public void checkOrder(Long orderId);

    /**
     * 购买会员下单
     * @param membershipOrderView
     * @return
     */
    public OrderView saveMembershipOrderAndOrderDetail(MembershipOrderView membershipOrderView);

    /**
     * 升级店铺
     * @param orderView
     */
    void upgradeShop(OrderView orderView);

    /**
     * 免费领下单
     * @param orderView
     * @return
     */
    OrderView saveProductFreeReceiveOrder(OrderView orderView);

    /**
     * 助力购下单
     * @param orderView
     * @return
     */
    OrderView saveBoostOrder(OrderView orderView);

    /**
     * 关闭未支付的订单，并还原库存
     * 定时器用
     */
    void  closeUnpaidOrderAndRestoreInventory();

    /**
     * 订单退款操作(全额退款情况)
     * @param orderView
     */
    void refundThenAddInventory(OrderView orderView);

    /**
     *  teamId与userId获取订单
     * @param teamId
     * @param userId
     * @return
     */
    OrderView getOrderIdByTeamIdAndUserId(Long teamId,Long userId);

}
