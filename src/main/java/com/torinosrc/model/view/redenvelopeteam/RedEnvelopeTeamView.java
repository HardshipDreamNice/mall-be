/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.redenvelopeteam;

import com.torinosrc.model.entity.redenvelopeteam.RedEnvelopeTeam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>RedEnvelopeTeamView</code></b>
 * <p/>
 * RedEnvelopeTeam的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-12-04 12:10:39.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class RedEnvelopeTeamView extends RedEnvelopeTeam implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "团员头像")
    private List<String> teamMemberAvatars;

    @ApiModelProperty(value = "状态 0：未完成 1：已完成 2：还没开始拆红包")
    private Integer status;

    @ApiModelProperty(value = "剩余人数")
    private Integer remainingNumber;

    @ApiModelProperty(value = "总面值")
    private Integer totalAmount;

    public List<String> getTeamMemberAvatars() {
        return teamMemberAvatars;
    }

    public void setTeamMemberAvatars(List<String> teamMemberAvatars) {
        this.teamMemberAvatars = teamMemberAvatars;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRemainingNumber() {
        return remainingNumber;
    }

    public void setRemainingNumber(Integer remainingNumber) {
        this.remainingNumber = remainingNumber;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }
}
