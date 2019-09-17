package com.torinosrc.model.view.order;

import com.torinosrc.model.entity.merchantconsignee.MerchantConsignee;
import com.torinosrc.model.entity.order.Order;
import com.torinosrc.model.view.orderdetail.OrderDetailView;

import java.io.Serializable;

public class GroupOrderView extends Order implements Serializable {
    private OrderDetailView orderDetailView;

    private String orderTime;

    private String address;

    private String contact;

    private String phone;

    private MerchantConsignee merchantConsignee;

    public OrderDetailView getOrderDetailView() {
        return orderDetailView;
    }

    public void setOrderDetailView(OrderDetailView orderDetailView) {
        this.orderDetailView = orderDetailView;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
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

    public MerchantConsignee getMerchantConsignee() {
        return merchantConsignee;
    }

    public void setMerchantConsignee(MerchantConsignee merchantConsignee) {
        this.merchantConsignee = merchantConsignee;
    }
}
