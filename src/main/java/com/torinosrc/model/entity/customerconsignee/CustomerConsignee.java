/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.customerconsignee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.user.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>CustomerConsignee</code></b>
 * <p/>
 * CustomerConsignee的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-06 11:29:50.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_customer_consignee")
@DynamicInsert
@DynamicUpdate
public class CustomerConsignee extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public CustomerConsignee(){
        super();
    }

    @Column(name = "address")
    private String address;

    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

    @Column(name = "county")
    private String county;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "contact")
    private String contact;

    @Column(name = "phone")
    private String phone;

    @Column(name = "auto_addr")
    private Integer autoAddr;

    @Column(name = "user_id")
    private Long userId;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
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

    public Integer getAutoAddr() {
        return autoAddr;
    }

    public void setAutoAddr(Integer autoAddr) {
        this.autoAddr = autoAddr;
    }
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
