/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.merchantconsignee;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>MerchantConsignee</code></b>
 * <p/>
 * MerchantConsignee的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-08 19:11:22.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_merchant_consignee")
@DynamicInsert
@DynamicUpdate
public class MerchantConsignee extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public MerchantConsignee(){
        super();
    }

    @Column(name = "address")
    private String address;

    @Column(name = "contact")
    private String contact;

    @Column(name = "phone")
    private String phone;

    @Column(name = "auto_addr")
    private Integer autoAddr;

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
}
