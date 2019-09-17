/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.userboostteam;

import com.torinosrc.model.entity.userboostteam.UserBoostTeam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <b><code>UserBoostTeamView</code></b>
 * <p/>
 * UserBoostTeam的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-12-03 15:15:48.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class UserBoostTeamView extends UserBoostTeam implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    private Long orderId;

    @ApiModelProperty("小程序发送模板消息需要的formId")
    private String formId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }
}
