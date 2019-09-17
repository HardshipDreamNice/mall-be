package com.torinosrc.model.view.captcha;

import com.torinosrc.model.entity.captcha.Captcha;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel
public class CaptchaView extends Captcha implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "验证码图片")
    private String captchaImage;

    public String getCaptchaImage() {
        return captchaImage;
    }

    public void setCaptchaImage(String captchaImage) {
        this.captchaImage = captchaImage;
    }
}
