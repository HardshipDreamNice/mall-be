package com.torinosrc.service.captcha.impl;


import com.torinosrc.commons.constants.SmsConstant;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.MD5Utils;
import com.torinosrc.commons.utils.SmsUtils;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.message.MessageCode;
import com.torinosrc.dao.captcha.CaptchaDao;
import com.torinosrc.dao.shop.ShopDao;
import com.torinosrc.model.entity.captcha.Captcha;
import com.torinosrc.model.view.captcha.CaptchaView;
import com.torinosrc.service.captcha.CaptchaService;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * <b><code>CaptchaImpl</code></b>
 * <p/>
 * Captcha的具体实现
 * <p/>
 * <b>Creation Time:</b> Tue Oct 31 14:06:57 CST 2017.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(CaptchaServiceImpl.class);

    @Autowired
    private CaptchaDao captchaDao;

    @Autowired
    private ShopDao shopDao;

    @Override
    public CaptchaView createCaptchaByParams(Integer captchaType, Integer contentType, Integer length, String phoneNumber, String userName) {
        // 参数检验
        if(ObjectUtils.isEmpty(captchaType)){
            throw new TorinoSrcServiceException("验证码类别不能为空");
        }
        if(ObjectUtils.isEmpty(contentType)){
            throw new TorinoSrcServiceException("验证码内容类型不能为空");
        }
        if(ObjectUtils.isEmpty(length)){
            throw new TorinoSrcServiceException("验证码长度不能为空");
        }
        if(StringUtils.isEmpty(userName)){
            throw new TorinoSrcServiceException("用户名不能为空");
        }
        if(length < 0 || length > 9){
            throw new TorinoSrcServiceException("验证码长度不能小于0或者大于9");
        }

        // 返回值
        CaptchaView captchaView = new CaptchaView();
        switch (captchaType) {
            case 1:
                //手机验证码
                if (StringUtils.isEmpty(phoneNumber)) {
                    throw new TorinoSrcServiceException("手机号码不能为空");
                } else {
                    if(!ObjectUtils.isEmpty(shopDao.findByPhone(phoneNumber))){
                        throw new TorinoSrcServiceException("手机号码已被注册",MessageCode.PHONE_NUMBER_HAS_BEEN_REGISTERED);
                    }
                    //生成数字验证码
                    Integer smsCode = TorinoSrcCommonUtils.generateRandomNumber(length);
                    //发送短信
                    SmsUtils.smsVerificationCode(phoneNumber, SmsConstant.SMS_SIGN_NAME, SmsConstant.SMS_TEMPLATE_CODE,
                            SmsConstant.SMS_PARAM_CODE, smsCode);
                    //将验证码插入数据库
                    captchaView.setUserName(userName);
                    captchaView.setType(captchaType);
                    captchaView.setValidateCode(String.valueOf(smsCode));
                    this.saveEntity(captchaView);
                    //将验证码置空，不返回给前端
                    captchaView.setValidateCode(null);
                }
                break;
            case 2:
                // 生成图片验证码
                captchaView = this.createImageCaptcha(length, contentType);
                //验证码内容插入数据库
                captchaView.setUserName(userName);
                captchaView.setType(captchaType);
                this.saveEntity(captchaView);
                captchaView.setValidateCode(null);
                break;
            default:
                throw new TorinoSrcServiceException("请填入正确的验证码类型");
        }

        return captchaView;
    }

    /**
     * 验证验证码是否正确
     * @param captchaView
     * @return
     */
    @Override
    public boolean validateCaptcha(CaptchaView captchaView, int mode) {
        boolean validateCaptcha = false;
        if (!StringUtils.isEmpty(captchaView.getUserName()) && !StringUtils.isEmpty(captchaView.getValidateCode())) {
            Captcha captcha = captchaDao.findCaptchaByUserName(captchaView.getUserName());

            if (!ObjectUtils.isEmpty(captcha)) {
                switch (captcha.getType()) {
                    case 1:
                        //手机验证码
                        if (captcha.getValidateCode().equals(captchaView.getValidateCode())) {
                            validateCaptcha = true;
                        } else {
                            validateCaptcha = false;
                        }
                        break;
                    case 2:
                        //图片验证码
                        String validateCode = MD5Utils.md5("TorinoSrc" + captchaView.getValidateCode().toLowerCase() + "TorinoSrc");
                        if (captcha.getValidateCode().equals(validateCode)) {
                            validateCaptcha = true;
                        } else {
                            validateCaptcha = false;
                        }
                        break;
                    default:
                        throw new TorinoSrcServiceException("验证码类型错误");
                }
                // 如果是后端验证，则删除记录，防止被多次利用
                if(mode == 1){
                    captchaDao.deleteById(captcha.getId());
                }
            }
        }
        return validateCaptcha;
    }

    private String saveEntity(CaptchaView captchaView) {
        //先查看该用户在数据库是否已存在,如果已存在，删除后再新增
        Captcha captcha1 = captchaDao.findCaptchaByUserName(captchaView.getUserName());
        if (!ObjectUtils.isEmpty(captcha1)) {
            captchaDao.delete(captcha1);
        }

        // 保存的业务逻辑
        Captcha captcha = new Captcha();
        TorinoSrcBeanUtils.copyBean(captchaView, captcha);
        // 添加Captcha相关属性
        captcha.setCreateTime(System.currentTimeMillis());
        captcha.setUpdateTime(System.currentTimeMillis());
        captcha.setEnabled(1);
        captchaDao.save(captcha);
        return String.valueOf(captcha.getId());
    }

    /**
     * 生成图片验证码
     * @param length
     * @param contentType 内容类型 1：数字 2：字母 3：混合 4：可扩展
     * @return
     */
    private CaptchaView createImageCaptcha(Integer length, Integer contentType) {
        int width = 100;
        int height = 44;
        String captchaCode = "";
        int bround;

        switch (contentType){
            case 1:
                captchaCode = "1234567890";
                bround = 10;
                break;
            case 2:
                captchaCode = "abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
                bround = 48;
                break;
            case 3:
                captchaCode = "abcdefghijkmnpqrstuvwxyzABCDEFGHIJKMNPQRSTUVWXYZ23456789";
                bround = 56;
                break;
            default:
                captchaCode = "abcdefghijkmnpqrstuvwxyzABCDEFGHIJKMNPQRSTUVWXYZ23456789";
                bround = 56;
                break;
        }

        Random random = new Random();

        BufferedImage image = new BufferedImage(width, height, TYPE_INT_RGB);
        //产生image类的Graphics用于绘制操作
        Graphics g = image.getGraphics();
        //Graphics类的样式
        g.setColor(new Color(255,255,255));

        g.setFont(new Font("Times New Roman", 0, 40));
        g.fillRect(0, 0, width, height);
        //绘制干扰线
        for (int i = 0; i < 40; i++) {
            g.setColor(this.getRandColor(130, 200));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }

        //绘制字符
        StringBuilder strCode = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char rand = captchaCode.charAt(random.nextInt(bround));
            strCode.append(rand);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand+"", 20 * i + 8, 36);
        }
        g.dispose();

        // bufferImage->base64
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Base64 encoder = new Base64();
        String base64Img = new String(encoder.encode(outputStream.toByteArray()));

        CaptchaView captchaView = new CaptchaView();
        captchaView.setValidateCode(MD5Utils.md5("TorinoSrc" + strCode.toString().toLowerCase() + "TorinoSrc"));
        captchaView.setCaptchaImage("data:image/png;base64," + base64Img);
        return captchaView;
    }

    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
