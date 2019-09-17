/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.userproductfreereceive;

import com.torinosrc.model.entity.userproductfreereceive.UserProductFreeReceive;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import javax.persistence.*;
/**
 * <b><code>UserProductFreeReceiveView</code></b>
 * <p/>
 * UserProductFreeReceive的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-27 19:45:53.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class UserProductFreeReceiveView extends UserProductFreeReceive implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    @Column(name ="product_free_receive_id")
    private Long productFreeReceiveId;

    @Column(name = "show_user_id")
    private Long showUserId;

    @Column(name = "help_user_id")
    private Long helpUserId;

    @Column(name = "product_free_receive_detail_id")
    private Long productFreeReceiveDetailId;

    //过期时间
    private Long expiredTime;

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Override
    public Long getProductFreeReceiveDetailId() {
        return productFreeReceiveDetailId;
    }

    @Override
    public void setProductFreeReceiveDetailId(Long productFreeReceiveDetailId) {
        this.productFreeReceiveDetailId = productFreeReceiveDetailId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public Long getProductFreeReceiveId() {
        return productFreeReceiveId;
    }

    @Override
    public void setProductFreeReceiveId(Long productFreeReceiveId) {
        this.productFreeReceiveId = productFreeReceiveId;
    }

    public Long getShowUserId() {
        return showUserId;
    }

    public void setShowUserId(Long showUserId) {
        this.showUserId = showUserId;
    }

    @Override
    public Long getHelpUserId() {
        return helpUserId;
    }

    @Override
    public void setHelpUserId(Long helpUserId) {
        this.helpUserId = helpUserId;
    }
}
