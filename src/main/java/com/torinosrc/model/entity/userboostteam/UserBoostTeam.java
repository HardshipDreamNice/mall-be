/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.userboostteam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>UserBoostTeam</code></b>
 * <p/>
 * UserBoostTeam的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-12-03 15:15:48.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_user_boost_team")
@DynamicInsert
@DynamicUpdate
public class UserBoostTeam extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public UserBoostTeam(){
        super();
    }

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "boost_team_id")
    private Long boostTeamId;

    @ApiModelProperty(value = "是否助力购发起人 0：不是 1：是")
    @Column(name = "type")
    private Integer type;

    @ApiModelProperty(value = "砍价金额")
    @Column(name = "discount_amount")
    private Integer discountAmount;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBoostTeamId() {

        return boostTeamId;
    }

    public void setBoostTeamId(Long boostTeamId) {
        this.boostTeamId = boostTeamId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }
}
