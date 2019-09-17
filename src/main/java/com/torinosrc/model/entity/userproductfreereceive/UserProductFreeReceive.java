/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.userproductfreereceive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>UserProductFreeReceive</code></b>
 * <p/>
 * UserProductFreeReceive的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-27 19:45:53.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_user_product_free_receive")
@DynamicInsert
@DynamicUpdate
public class UserProductFreeReceive extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public UserProductFreeReceive(){
        super();
    }


    @Column(name = "share_user_id")
    private Long shareUserId;

    @Column(name="product_free_receive_id")
    private Long productFreeReceiveId;

    @Column(name = "help_user_id")
    private Long helpUserId;

    @Column(name = "status")
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "product_free_receive_detail_id")
    private Long productFreeReceiveDetailId;

    public Long getProductFreeReceiveDetailId() {
        return productFreeReceiveDetailId;
    }



    public void setProductFreeReceiveDetailId(Long productFreeReceiveDetailId) {
        this.productFreeReceiveDetailId = productFreeReceiveDetailId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(Long shareUserId) {
        this.shareUserId = shareUserId;
    }

    public Long getProductFreeReceiveId() {
        return productFreeReceiveId;
    }

    public void setProductFreeReceiveId(Long productFreeReceiveId) {
        this.productFreeReceiveId = productFreeReceiveId;
    }

    public Long getHelpUserId() {
        return helpUserId;
    }

    public void setHelpUserId(Long helpUserId) {
        this.helpUserId = helpUserId;
    }
}
