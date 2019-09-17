/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.teamuser;

import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>TeamUser</code></b>
 * <p/>
 * TeamUser的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-16 10:54:01.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_team_user")
@DynamicInsert
@DynamicUpdate
public class TeamUser extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public TeamUser(){
        super();
    }

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "type")
    private Integer type;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
