package com.torinosrc.commons.utils.image;

import io.swagger.annotations.ApiModelProperty;

import java.awt.*;

public class CompoundTextInfo {

    @ApiModelProperty(value = "文字内容")
    private String text;

    @ApiModelProperty(value = "文字位置- x 轴")
    private Integer x;

    @ApiModelProperty(value = "文字位置 - y 轴")
    private Integer y;

    @ApiModelProperty(value = "字体大小")
    private Integer fontSize;

    @ApiModelProperty(value = "字体颜色")
    private Color color;

    public CompoundTextInfo(String text, Integer x, Integer y, Integer fontSize, Color color) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }
}
