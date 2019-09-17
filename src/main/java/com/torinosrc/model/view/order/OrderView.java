/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.order;

import com.torinosrc.model.entity.merchantconsignee.MerchantConsignee;
import com.torinosrc.model.entity.order.Order;
import com.torinosrc.model.view.orderdetail.OrderDetailView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>OrderView</code></b>
 * <p/>
 * Order的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-06 11:31:03.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class OrderView extends Order implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    private String orderTime;

    private String address;

    private String contact;

    private String phone;

    private MerchantConsignee merchantConsignee;

    private List<OrderDetailView> orderDetailViews;

    private Integer orderSpellTeamStatus;

   //免费领商品详情id
    private Long productFreeReceiveDetailId;

    public Long getProductFreeReceiveDetailId() {
        return productFreeReceiveDetailId;
    }

    public void setProductFreeReceiveDetailId(Long productFreeReceiveDetailId) {
        this.productFreeReceiveDetailId = productFreeReceiveDetailId;
    }

    public Integer getOrderSpellTeamStatus() {
        return orderSpellTeamStatus;
    }

    public void setOrderSpellTeamStatus(Integer orderSpellTeamStatus) {
        this.orderSpellTeamStatus = orderSpellTeamStatus;
    }

    public MerchantConsignee getMerchantConsignee() {
        return merchantConsignee;
    }

    public void setMerchantConsignee(MerchantConsignee merchantConsignee) {
        this.merchantConsignee = merchantConsignee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public List<OrderDetailView> getOrderDetailViews() {
        return orderDetailViews;
    }

    public void setOrderDetailViews(List<OrderDetailView> orderDetailViews) {
        this.orderDetailViews = orderDetailViews;
    }
}
