package com.torinosrc.service.captcha;

import com.torinosrc.model.view.captcha.CaptchaView;
import org.springframework.stereotype.Service;

/**
* <b><code>Captcha</code></b>
* <p/>
* Captcha的具体实现
* <p/>
* <b>Creation Time:</b> Tue Oct 31 14:06:57 CST 2017.
*
* @author panxin
* @version 1.0.0
 * @since torinosrc-core 1.0.0
 */
@Service
public interface CaptchaService{

    /**
     * 根据参数生成不同类型的验证码
     * @param captchaType
     * @param contentType
     * @param length
     * @param phoneNumber
     * @return
     */
    CaptchaView createCaptchaByParams(Integer captchaType, Integer contentType, Integer length, String phoneNumber, String userName);

    /**
     * 验证验证码是否正确
     * @param captchaView
     * @param mode 0为前端验证（不删除数据库记录），1为后端验证（删除数据库记录）
     * @return
     */
    boolean validateCaptcha(CaptchaView captchaView, int mode);


}
