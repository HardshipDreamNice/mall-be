/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.customerstatistics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>CustomerStatistics</code></b>
 * <p/>
 * CustomerStatistics的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-06 11:32:50.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_customer_statistics")
@DynamicInsert
@DynamicUpdate
public class CustomerStatistics extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public CustomerStatistics(){
        super();
    }

    @Column(name="open_id")
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
