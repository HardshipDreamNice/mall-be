/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.shopvisitrecord;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>ShopVisitRecord</code></b>
 * <p/>
 * ShopVisitRecord的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-19 18:21:46.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_shop_visit_record")
@DynamicInsert
@DynamicUpdate
public class ShopVisitRecord extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public ShopVisitRecord(){
        super();
    }


    @Column(name = "user_id")
    private Long userId;

    @Column(name = "shop_id")
    private Long shopId ;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
