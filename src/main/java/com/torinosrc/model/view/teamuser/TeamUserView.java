/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.teamuser;

import com.torinosrc.model.entity.teamuser.TeamUser;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * <b><code>TeamUserView</code></b>
 * <p/>
 * TeamUser的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-16 10:54:01.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class TeamUserView extends TeamUser implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
