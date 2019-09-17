package com.torinosrc.model.view.team;

import com.torinosrc.model.view.teamuser.TeamUserView;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TeamShowView {

    private List<TeamUserView> teamUserViews;

    private String productName;

    private Long productId;

    private String endTime;

    private Integer count;

    private Long teamId;

    private String instructions;

    private String errorStatus;

    private String errorMessage;

    @ApiModelProperty(value = "0:进行中 1：失败 2：成功")
    private Integer teamStatus;

    public Integer getTeamStatus() {
        return teamStatus;
    }

    public void setTeamStatus(Integer teamStatus) {
        this.teamStatus = teamStatus;
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<TeamUserView> getTeamUserViews() {
        return teamUserViews;
    }

    public void setTeamUserViews(List<TeamUserView> teamUserViews) {
        this.teamUserViews = teamUserViews;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
