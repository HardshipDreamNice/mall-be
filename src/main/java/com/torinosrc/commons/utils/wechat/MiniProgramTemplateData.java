package com.torinosrc.commons.utils.wechat;


import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class MiniProgramTemplateData {

    private String accessToken;

    @ApiModelProperty(value = "接收者（用户）的 openid")
    private String touser;

    @ApiModelProperty(value = "所需下发的模板消息的id")
    private String templateId;

    @ApiModelProperty(value = "点击模板卡片后要跳转的页面地址，支持带参数")
    private String page;

    @ApiModelProperty(value = "表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id")
    private String formId;

    @ApiModelProperty(value = "模板需要放大的关键词，不填则默认无放大；示例：keyword1.DATA")
    private String emphasisKeyword;

    @ApiModelProperty(value = "要发送的模板内容，按 keyword 顺序放入")
    private List<String> data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getEmphasisKeyword() {
        return emphasisKeyword;
    }

    public void setEmphasisKeyword(String emphasisKeyword) {
        this.emphasisKeyword = emphasisKeyword;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
