package com.torinosrc.model.view.code;

import io.swagger.annotations.ApiModelProperty;

public class GroupCodeView {
    @ApiModelProperty(value = "目标Url id|")
    private Long redirectUtlId;

    private Long userId;

    private Long teamId;

    public Long getRedirectUtlId() {
        return redirectUtlId;
    }

    public void setRedirectUtlId(Long redirectUtlId) {
        this.redirectUtlId = redirectUtlId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}
