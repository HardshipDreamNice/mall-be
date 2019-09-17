package com.torinosrc.controller.captcha;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcServiceExceptionUtils;
import com.torinosrc.commons.utils.message.MessageCode;
import com.torinosrc.commons.utils.message.MessageDescription;
import com.torinosrc.commons.utils.message.MessageStatus;
import com.torinosrc.commons.utils.message.MessageUtils;
import com.torinosrc.model.view.captcha.CaptchaView;
import com.torinosrc.model.view.message.TorinoSrcErrorResponseMessage;
import com.torinosrc.model.view.message.TorinoSrcMessage;
import com.torinosrc.service.captcha.CaptchaService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * <b><code>CaptchaController</code></b>
 * <p/>
 * Captcha的具体实现
 * <p/>
 * <b>Creation Time:</b> Tue Oct 31 14:06:57 CST 2017.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Api(value = "[Torino Source]验证码接口",tags = "[Torino Source]验证码接口",description = "")
@RestController
@RequestMapping(value = "/api")
public class CaptchaController {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(CaptchaController.class);

    /**
     * The service.
     */
    @Autowired
    private CaptchaService captchaService;

    @ApiOperation(value = "[Torino Source]获取验证码", notes = "获取验证码")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class)),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequestMapping(value = "/v1/captchas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createCaptchaByJcaptcha(
            HttpServletResponse response,
            @ApiParam(value = "验证码类别，1：手机验证码 2：传统图片验证码", defaultValue = "", required = true) @RequestParam(value = "captchaType", defaultValue = "", required = true) Integer captchaType,
            @ApiParam(value = "验证码内容类别，1：数字 2：字母 3：混合", defaultValue = "", required = true) @RequestParam(value = "contentType", defaultValue = "", required = true) Integer contentType,
            @ApiParam(value = "验证码长度（1-9）", defaultValue = "", required = true) @RequestParam(value = "length", defaultValue = "", required = true) Integer length,
            @ApiParam(value = "手机号码", defaultValue = "", required = false) @RequestParam(value = "phoneNumber", defaultValue = "", required = false) String phoneNumber,
            @ApiParam(value = "用户名", defaultValue = "", required = false) @RequestParam(value = "userName", defaultValue = "", required = true) String userName) {
        try {
            CaptchaView captchaView = captchaService.createCaptchaByParams(captchaType, contentType, length, phoneNumber, userName);
            TorinoSrcMessage<CaptchaView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_SUCCESS, captchaView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        }catch (TorinoSrcServiceException t){
            String error = "Failed to add captcha! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(t.getErrorCode(), MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
        catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]校验验证码", notes = "校验验证码")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class)),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequestMapping(value = "/v1/captchas/verify", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> verifyCaptchaByJcaptcha(
            @ApiParam(value = "验证码类别", required = true) @RequestBody CaptchaView captchaView) {
        try {
            Boolean result = captchaService.validateCaptcha(captchaView, 0);
            TorinoSrcMessage<Boolean> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_SUCCESS, result);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }
}
