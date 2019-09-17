/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.user;

import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>User</code></b>
 * <p/>
 * User的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-04-16 14:17:14.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_user")
@DynamicInsert
@DynamicUpdate
public class User extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public User(){
        super();
    }

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="nick_name")
    private String nickName;

    @Column(name="avatar")
    private String avatar;

    @Column(name="open_id")
    private String openId;

    @Column(name="phone")
    private String phone;

    @Column(name="audit")
    private int audit;

    @Column(name="name")
    private String name;

    @Column(name="id_card")
    private String idCard;

    @Column(name="id_card_front_url")
    private String idCardFrontUrl;

    @Column(name="id_card_back_url")
    private String idCardBackUrl;

//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    @OneToMany(fetch=FetchType.EAGER,cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "user_id")
//    @OrderBy("updateTime desc")
//    private Set<CustomerConsignee> customerConsignees;
//
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    @OneToMany(fetch=FetchType.EAGER,cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "user_id")
//    @OrderBy("updateTime desc")
//    private Set<Order> orders;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

//    public Set<CustomerConsignee> getCustomerConsignees() {
//        return customerConsignees;
//    }
//
//    public void setCustomerConsignees(Set<CustomerConsignee> customerConsignees) {
//        this.customerConsignees = customerConsignees;
//    }
//
//    public Set<Order> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(Set<Order> orders) {
//        this.orders = orders;
//    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCardFrontUrl() {
        return idCardFrontUrl;
    }

    public void setIdCardFrontUrl(String idCardFrontUrl) {
        this.idCardFrontUrl = idCardFrontUrl;
    }

    public String getIdCardBackUrl() {
        return idCardBackUrl;
    }

    public void setIdCardBackUrl(String idCardBackUrl) {
        this.idCardBackUrl = idCardBackUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }
}
