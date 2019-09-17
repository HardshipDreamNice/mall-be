/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.redenvelopeteam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>RedEnvelopeTeam</code></b>
 * <p/>
 * RedEnvelopeTeam的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-12-04 12:10:39.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_red_envelope_team")
@DynamicInsert
@DynamicUpdate
public class RedEnvelopeTeam extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public RedEnvelopeTeam(){
        super();
    }


    @Column(name = "red_envelope_id")
    private Long redEnvelopeId;

    @Column(name = "expired_time")
    private Long expiredTime;

    @Column(name = "red_envelope_name")
    private String redEnvelopeName;

    @ApiModelProperty(value = "需要领取的人数")
    @Column(name = "take_number")
    private Integer takeNumber;

    @ApiModelProperty(value = "红色类型 0：优惠券红包 1：现金红包")
    @Column(name = "red_envelope_type")
    private Integer redEnvelopeType;

    @Column(name = "user_id")
    private Long userId;

    public Long getRedEnvelopeId() {
        return redEnvelopeId;
    }

    public void setRedEnvelopeId(Long redEnvelopeId) {
        this.redEnvelopeId = redEnvelopeId;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getRedEnvelopeName() {
        return redEnvelopeName;
    }

    public void setRedEnvelopeName(String redEnvelopeName) {
        this.redEnvelopeName = redEnvelopeName;
    }

    public Integer getTakeNumber() {
        return takeNumber;
    }

    public void setTakeNumber(Integer takeNumber) {
        this.takeNumber = takeNumber;
    }

    public Integer getRedEnvelopeType() {
        return redEnvelopeType;
    }

    public void setRedEnvelopeType(Integer redEnvelopeType) {
        this.redEnvelopeType = redEnvelopeType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
