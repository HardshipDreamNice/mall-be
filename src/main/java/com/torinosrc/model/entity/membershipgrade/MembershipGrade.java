/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.membershipgrade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>MembershipGrade</code></b>
 * <p/>
 * MembershipGrade的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-28 18:42:13.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_membership_grade")
@DynamicInsert
@DynamicUpdate
public class MembershipGrade extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public MembershipGrade(){
        super();
    }

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Long price;

    @Column(name = "discount")
    private Long discount;

    @Column(name = "grade")
    private Integer grade;

    @Column(name = "percent_1")
    private Integer percent1;

    @Column(name = "percent_2")
    private Integer percent2;

    @Column(name = "percent_3")
    private Integer percent3;

    @Column(name = "commission")
    private Long commission;

    @Column(name = "benefit")
    private String benefit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getPercent1() {
        return percent1;
    }

    public void setPercent1(Integer percent1) {
        this.percent1 = percent1;
    }

    public Integer getPercent2() {
        return percent2;
    }

    public void setPercent2(Integer percent2) {
        this.percent2 = percent2;
    }

    public Integer getPercent3() {
        return percent3;
    }

    public void setPercent3(Integer percent3) {
        this.percent3 = percent3;
    }

    public Long getCommission() {
        return commission;
    }

    public void setCommission(Long commission) {
        this.commission = commission;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }
}
