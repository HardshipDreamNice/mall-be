package com.torinosrc.commons.utils.image;

import io.swagger.annotations.ApiModelProperty;

public class CompoundImageInfo {

    @ApiModelProperty(value = "图片的全路径，包括文件名")
    private String imagePath;

    @ApiModelProperty(value = "图片所在的位置 - x 轴")
    private Integer x;

    @ApiModelProperty(value = "图片所在的位置 - y 轴")
    private Integer y;

    @ApiModelProperty(value = "图片的宽度，如果是 null 则按图片默认宽度。" +
            "width 和 height 如果只传其中一个，则默认按照原图比例计算另一个的大小")
    private Integer width;

    @ApiModelProperty(value = "图片的高度，如果是 null 则按图片默认高度")
    private Integer height;

    public CompoundImageInfo(String imagePath, Integer x, Integer y, Integer width, Integer height) {
        this.imagePath = imagePath;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
