package com.torinosrc.commons.utils.image;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CompoundPirtureAndFontParams {

    @ApiModelProperty(value = "背景图的全路径，包括文件名")
    private String baseImagePath;

    @ApiModelProperty(value = "合成后的图片的存放路径，如 /www/root/mall_160/public/")
    private String outPutImagePath;

    @ApiModelProperty(value = "合成后的图片的文件名")
    private String outPutImageFileName;

    @ApiModelProperty(value = "要合成的文字的集合")
    private List<CompoundTextInfo> compoundTextInfoList;

    @ApiModelProperty(value = "要合成的图片的集合")
    private List<CompoundImageInfo> compoundImageInfoList;

    public String getBaseImagePath() {
        return baseImagePath;
    }

    public void setBaseImagePath(String baseImagePath) {
        this.baseImagePath = baseImagePath;
    }

    public String getOutPutImagePath() {
        return outPutImagePath;
    }

    public void setOutPutImagePath(String outPutImagePath) {
        this.outPutImagePath = outPutImagePath;
    }

    public String getOutPutImageFileName() {
        return outPutImageFileName;
    }

    public void setOutPutImageFileName(String outPutImageFileName) {
        this.outPutImageFileName = outPutImageFileName;
    }

    public List<CompoundTextInfo> getCompoundTextInfoList() {
        return compoundTextInfoList;
    }

    public void setCompoundTextInfoList(List<CompoundTextInfo> compoundTextInfoList) {
        this.compoundTextInfoList = compoundTextInfoList;
    }

    public List<CompoundImageInfo> getCompoundImageInfoList() {
        return compoundImageInfoList;
    }

    public void setCompoundImageInfoList(List<CompoundImageInfo> compoundImageInfoList) {
        this.compoundImageInfoList = compoundImageInfoList;
    }
}
