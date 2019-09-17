/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.usersignlog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>UserSignLog</code></b>
 * <p/>
 * UserSignLog的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-29 16:44:39.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_user_sign_log")
@DynamicInsert
@DynamicUpdate
public class UserSignLog extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public UserSignLog(){
        super();
    }

    @Column(name = "sign_user_id")
    private Long signUserId;

    @ApiModelProperty(value = "助签者id")
    @Column(name = "help_user_id")
    private Long helpUserId;

    @Column(name = "sign_time")
    private Long signTime;

    @Column(name = "status")
    private Integer status;

    public Long getSignUserId() {
        return signUserId;
    }

    public void setSignUserId(Long signUserId) {
        this.signUserId = signUserId;
    }

    public Long getHelpUserId() {
        return helpUserId;
    }

    public void setHelpUserId(Long helpUserId) {
        this.helpUserId = helpUserId;
    }

    public Long getSignTime() {
        return signTime;
    }

    public void setSignTime(Long signTime) {
        this.signTime = signTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
