/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.customerconsignee.CustomerConsignee;
import com.torinosrc.model.entity.orderdetail.OrderDetail;
import com.torinosrc.model.entity.user.User;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

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
@Entity
@Table(name = "t_order")
@DynamicInsert
@DynamicUpdate
public class Order extends BaseEntity implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    public Order() {
        super();
    }

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "prepay_id")
    private String prepayId;

    @Column(name = "total_fee")
    private Integer totalFee;

    @Column(name = "logistics_num")
    private String logisticsNum;

    @Column(name = "logistics_type")
    private String logisticsType;

    @Column(name = "status")
    private Integer status;

    @Column(name = "pay_status")
    private Integer payStatus;

    @Column(name = "update_status")
    private Integer updateStatus;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "wx_result")
    private String wxResult;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "postage")
    private Integer postage;

    @Column(name = "pay_time_end")
    private String payTimeEnd;

    @Column(name = "shop_id")
    private Long shopId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    @OrderBy("updateTime desc")
    private Set<OrderDetail> orderDetails;

    @Column(name = "customer_consignee_string")
    private String customerConsigneeString;

    @Column(name = "refund")
    private Integer refund;

    @Column(name = "return_address")
    private String returnAddress;

    @Column(name = "return_name")
    private String returnName;

    @Column(name = "return_phone")
    private String returnPhone;

    @Column(name = "service_status")
    private Integer serviceStatus;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "boost_team_id")
    private Long boostTeamId;

    @ApiModelProperty(value = "订单类型 0：普通商品订单 1：拼团商品订单 2：购买会员订单 3：免费领订单 4：助力购商品")
    @Column(name = "order_type")
    private Integer orderType;

    @Column(name = "last_total_fee")
    private Integer lastTotalFee;


    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(String logisticsType) {
        this.logisticsType = logisticsType;
    }

    public String getPayTimeEnd() {
        return payTimeEnd;
    }

    public void setPayTimeEnd(String payTimeEnd) {
        this.payTimeEnd = payTimeEnd;
    }

    public Integer getPostage() {
        return postage;
    }

    public void setPostage(Integer postage) {
        this.postage = postage;
    }

    public String getWxResult() {
        return wxResult;
    }

    public void setWxResult(String wxResult) {
        this.wxResult = wxResult;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getLogisticsNum() {
        return logisticsNum;
    }

    public void setLogisticsNum(String logisticsNum) {
        this.logisticsNum = logisticsNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getCustomerConsigneeString() {
        return customerConsigneeString;
    }

    public void setCustomerConsigneeString(String customerConsigneeString) {
        this.customerConsigneeString = customerConsigneeString;
    }

    public Integer getRefund() {
        return refund;
    }

    public void setRefund(Integer refund) {
        this.refund = refund;
    }

    public Set<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getReturnAddress() {
        return returnAddress;
    }

    public void setReturnAddress(String returnAddress) {
        this.returnAddress = returnAddress;
    }

    public String getReturnName() {
        return returnName;
    }

    public void setReturnName(String returnName) {
        this.returnName = returnName;
    }

    public String getReturnPhone() {
        return returnPhone;
    }

    public void setReturnPhone(String returnPhone) {
        this.returnPhone = returnPhone;
    }

    public Integer getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(Integer serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Long getBoostTeamId() {
        return boostTeamId;
    }

    public void setBoostTeamId(Long boostTeamId) {
        this.boostTeamId = boostTeamId;
    }

    public Integer getLastTotalFee() {
        return lastTotalFee;
    }

    public void setLastTotalFee(Integer lastTotalFee) {
        this.lastTotalFee = lastTotalFee;
    }
}
