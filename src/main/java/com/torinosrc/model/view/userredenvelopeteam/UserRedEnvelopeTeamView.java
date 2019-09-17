/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.userredenvelopeteam;

import com.torinosrc.model.entity.userredenvelopeteam.UserRedEnvelopeTeam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>UserRedEnvelopeTeamView</code></b>
 * <p/>
 * UserRedEnvelopeTeam的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-12-04 12:18:22.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class UserRedEnvelopeTeamView extends UserRedEnvelopeTeam implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "团员头像")
    private List<String> teamMemberAvatars;

    public List<String> getTeamMemberAvatars() {
        return teamMemberAvatars;
    }

    public void setTeamMemberAvatars(List<String> teamMemberAvatars) {
        this.teamMemberAvatars = teamMemberAvatars;
    }
}
