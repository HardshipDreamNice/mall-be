/*
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.base;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <b><code>BaseEntity</code></b>
 * <p/>
 * BaseEntity
 * <p/>
 * <b>Creation Time:</b> 2017/05/17 21:18.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-model 1.0.0
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "create_time")
    private Long createTime;

    @NotNull
    @Column(name = "update_time")
    private Long updateTime;

    @Column(name = "enabled")
    private Integer enabled;

    /**
     * 用于接收前端传回来的条件
     */
    @Transient
    private String condition;

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }


    public Long getCreateTime() {
        return createTime;
    }


    public Long getUpdateTime() {
        return updateTime;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BaseEntity{");
        sb.append("id=").append(id);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", enabled=").append(enabled);
        sb.append(", condition='").append(condition).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
