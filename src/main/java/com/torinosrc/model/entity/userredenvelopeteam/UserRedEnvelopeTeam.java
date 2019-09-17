/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.userredenvelopeteam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.user.User;
import io.swagger.models.auth.In;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>UserRedEnvelopeTeam</code></b>
 * <p/>
 * UserRedEnvelopeTeam的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-12-04 12:18:22.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name = "t_user_red_envelope_team")
@DynamicInsert
@DynamicUpdate
public class UserRedEnvelopeTeam extends BaseEntity implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    public UserRedEnvelopeTeam() {
        super();
    }

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "red_envelope_team_id")
    private Long redEnvelopeTeamId;

    @Column(name = "type")
    private Integer type;

    @Column(name = "take_amount")
    private Integer takeAmount;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRedEnvelopeTeamId() {
        return redEnvelopeTeamId;
    }

    public void setRedEnvelopeTeamId(Long redEnvelopeTeamId) {
        this.redEnvelopeTeamId = redEnvelopeTeamId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTakeAmount() {
        return takeAmount;
    }

    public void setTakeAmount(Integer takeAmount) {
        this.takeAmount = takeAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
