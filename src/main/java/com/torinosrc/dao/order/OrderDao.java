/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.order;

import com.torinosrc.model.entity.order.Order;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * <b><code>OrderDao</code></b>
 * <p/>
 * Order的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-06 11:31:03.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface OrderDao extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {


    public Order findByOrderNo(String orderNo);

    /**
     * 根据状态获订单
     *
     * @param status
     * @return
     */
    public List<Order> findByStatus(int status);

    // 我的订单
    public List<Order> findByUserIdAndStatusOrderByCreateTimeDesc(Long userId, int status);

    public List<Order> findByUserIdOrderByCreateTimeDesc(Long userId);

    public List<Order> findByUserIdAndStatusInOrderByCreateTimeDesc(Long userId, List<Integer> status);

    // 成交订单（店主店铺）
    public List<Order> findByUserIdAndShopIdAndStatusOrderByCreateTimeDesc(Long userId, Long shopId, int status);

    public List<Order> findByUserIdAndShopIdOrderByCreateTimeDesc(Long userId, Long shopId);

    public List<Order> findByUserIdAndShopIdAndStatusInOrderByCreateTimeDesc(Long userId, Long shopId, List<Integer> status);

    /**
     * 设置为2为用户取消支付
     *
     * @param orderNo
     */
    @Transactional
    @Modifying
    @Query(value = "update t_order p set p.pay_status = 2 where p.order_no = ?1", nativeQuery = true)
    public void updatePayStatusTo2(String orderNo);

    /**
     * 1为已申请支付
     *
     * @param orderNo
     */
    @Transactional
    @Modifying
    @Query(value = "update t_order p set p.pay_status = 1 where p.order_no = ?1", nativeQuery = true)
    public void updatePayStatusTo1(String orderNo);

    /**
     * 3为微信确认支付成功
     *
     * @param orderNo
     */
    @Transactional
    @Modifying
    @Query(value = "update t_order p set p.pay_status = 3 where p.order_no = ?1", nativeQuery = true)
    public void updatePayStatusTo3(String orderNo);


    @Transactional
    @Modifying
    @Query(value = "update t_order p set p.prepay_id = ?2 where p.order_no = ?1", nativeQuery = true)
    public void updatePrePayId(String orderNo, String prepayId);

    /**
     * 根据状态获取店铺的所有订单
     *
     * @param shopId
     * @param status
     * @return
     */
    List<Order> getAllByShopIdAndStatus(Long shopId, Integer status);

    List<Order> getAllByShopIdAndStatusAndOrderType(Long shopId, Integer status,Integer orderType);

    /**
     * 获取店铺在某一段时间内的所有订单
     *
     * @param shopId
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    List<Order> getAllByShopIdAndStatusAndUpdateTimeBetween(Long shopId, Integer status, Long startTime, Long endTime);

    List<Order> getAllByShopIdAndStatusAndUpdateTimeBetweenAndOrderType(Long shopId, Integer status, Long startTime, Long endTime,Integer orderType);

    List<Order> findByStatusAndUpdateStatus(Integer status, Integer updateStatus);


    // 预收益用到的订单方法
    public List<Order> findByShopIdAndStatusIn(Long shopId, List<Integer> status);

    public List<Order> findByShopIdAndStatusInAndOrderType(Long shopId, List<Integer> status,Integer orderType);

    public List<Order> findByShopIdAndStatusInAndUpdateTimeBetween(Long shopId, List<Integer> status, Long startTime, Long endTime);

    public void deleteByTeamId(Long teamId);


    /**
     * 订单软删除
     * @param teamId
     */
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update t_order set status=7 where team_id=?1")
    public void updateOrderStatus(Long teamId);

    public List<Order> findByTeamId(Long teamId);

    /**
     * 根据订单的状态、更新状态、订单类型查找订单
     * updateStatus 0：未更新收益 1：已更新收益
     * orderType 0：普通订单 1：拼团订单 2：购买会员订单
     * @param status
     * @param updateStatus
     * @param orderType
     * @return
     */
    List<Order> findByStatusAndUpdateStatusAndOrderType(Integer status, Integer updateStatus, Integer orderType);

    /**
     * 通过团购id与用户id获取订单
     * @param teamId
     * @param userId
     * @return
     */
    Order findByTeamIdAndUserId(Long teamId,Long userId);
}
