/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.shoptree;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>ShopTree</code></b>
 * <p/>
 * ShopTree的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-12 16:11:55.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_shop_tree")
@DynamicInsert
@DynamicUpdate
public class ShopTree extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public ShopTree(){
        super();
    }

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "parent_shop_id")
    private Long parentShopId;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getParentShopId() {
        return parentShopId;
    }

    public void setParentShopId(Long parentShopId) {
        this.parentShopId = parentShopId;
    }
}
