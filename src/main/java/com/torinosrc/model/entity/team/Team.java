/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>Team</code></b>
 * <p/>
 * Team的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-16 10:53:24.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_team")
@DynamicInsert
@DynamicUpdate
public class Team extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public Team(){
        super();
    }

    @Column(name = "expired_time")
    private String expiredTime;

    @Column(name = "count")
    private Integer count;

    @Column(name = "product_snapshot_id")
    private Long productSnapshotId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getProductSnapshotId() {
        return productSnapshotId;
    }

    public void setProductSnapshotId(Long productSnapshotId) {
        this.productSnapshotId = productSnapshotId;
    }
}
