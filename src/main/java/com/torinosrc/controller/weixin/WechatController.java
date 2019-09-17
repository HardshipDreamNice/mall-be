package com.torinosrc.controller.weixin;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcServiceExceptionUtils;
import com.torinosrc.commons.utils.message.MessageCode;
import com.torinosrc.commons.utils.message.MessageDescription;
import com.torinosrc.commons.utils.message.MessageStatus;
import com.torinosrc.commons.utils.message.MessageUtils;
import com.torinosrc.model.view.code.GroupCodeView;
import com.torinosrc.model.view.message.TorinoSrcErrorResponseMessage;
import com.torinosrc.model.view.message.TorinoSrcMessage;
import com.torinosrc.model.view.weixin.WxOrderView;
import com.torinosrc.model.view.weixin.WxPaymentView;
import com.torinosrc.model.view.weixin.WxQrCodeView;
import com.torinosrc.service.weixin.WechatService;
import io.swagger.annotations.*;
import net.sf.json.JSONObject;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Api(value = "[Torino Source]微信接口",tags = "[Torino Source]微信接口",description = "")
@RestController
@RequestMapping(value = "/api")
public class WechatController {
    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(WechatController.class);

    /**
     * The service.
     */
    @Autowired
    private WechatService wechatService;


    @ApiOperation(value = "[Torino Source]微信下单", notes = "微信下单")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error")})
    @RequiresPermissions(value = {"sys:wechat:add"})
    @RequestMapping(value = "/v1/wx/order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> payOrder(
            @ApiParam(value = "订单信息", required = true) @RequestBody WxOrderView wxOrderView, HttpServletRequest request) {
        try {
            String ip = TorinoSrcCommonUtils.getIpAddr(request);

            WxPaymentView wxPaymentView = wechatService.payOrder(wxOrderView, ip);

//            Result result = weixinPay.orderPay(wxOrderView,ip);

            // 封装返回信息
            TorinoSrcMessage<WxPaymentView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, wxPaymentView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to wx pay order! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]微信支付结果通知回调", notes = "微信支付结果通知回调")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error")})
    @RequestMapping(value = "/v1/wx/pay/callback", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> payStatus(
            @ApiParam(value = "微信支付结果通知", required = true) @RequestBody String result) {
        try {
            String returnResult = wechatService.payStatus(result);
            // 封装返回信息
            TorinoSrcMessage<String> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, returnResult);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update wx pay status! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]申请退款", notes = "申请退款")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class)),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 403, message = "forbidden"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:wechat:query"})
    @RequestMapping(value = "/v1/wx/refund", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> userRefund(
            @ApiParam(value = "申请退款订单id", defaultValue = "", required = false) @RequestParam(value = "orderId", defaultValue = "", required = false) Long orderId) {
        try {

            JSONObject result = wechatService.applyRefund(orderId);
            if ("success".equals(result.getString("status"))) {
                TorinoSrcMessage<JSONObject> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, result);
                return new ResponseEntity<>(torinoSrcMessage, HttpStatus.CREATED);
            } else {
                TorinoSrcMessage<JSONObject> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, MessageDescription.OPERATION_FAILURE, result);
                return new ResponseEntity<>(torinoSrcMessage, HttpStatus.FORBIDDEN);
            }
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]微信退款结果通知回调", notes = "微信退款结果通知回调")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error")})
    @RequestMapping(value = "/v1/wx/refund/callback", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> refundStatus(
            @ApiParam(value = "微信退款结果通知", required = true) @RequestBody String result) {
        try {
            String returnResult = wechatService.refundStatus(result);
            // 封装返回信息
            TorinoSrcMessage<String> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, returnResult);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update wx pay status! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取小程序二维码图片", notes = "获取小程序二维码图片")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error")})
    @RequestMapping(value = "/v1/wx/getqrcode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createQrCode(
            @ApiParam(value = "获取小程序二维码", required = true) @RequestBody WxQrCodeView wxQrCodeView
    ) {
        try {
            String qrCodePath = wechatService.getQrCode(wxQrCodeView);
            // 封装返回信息
            TorinoSrcMessage<String> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, qrCodePath);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update wx pay status! " + MessageDescription.OPERATION_QUERY_SUCCESS;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取小程序拼团二维码图片", notes = "获取小程序拼团二维码图片")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error")})
    @RequestMapping(value = "/v1/wx/getgroupqrcode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createGroupQrCode(
            @ApiParam(value = "获取小程序二维码", required = true) @RequestBody GroupCodeView groupCodeView
    ) {
        try {
            String qrCodePath = wechatService.getGroupQrCode(groupCodeView);
            // 封装返回信息
            TorinoSrcMessage<String> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, qrCodePath);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update wx pay status! " + MessageDescription.OPERATION_QUERY_SUCCESS;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @PostMapping("/notify")
    public String notify(HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^notify come");
        return "success";
    }

    //微信公众号点击转跳到小程序
    @ApiOperation(value = "[Torino Source]微信公众号点击转跳到小程序", notes = "微信公众号点击转跳到小程序")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error")})
    @RequestMapping(value = "/v1/wx/replywximagemessage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String replyWxImageMessage(
            @ApiParam(value = "微信公众号请求数据", required = true) @RequestBody String result) {
        try {
            String returnResult = wechatService.wxTurnWeChatApplet(result);
            return returnResult;
        } catch (Throwable t) {
            String error = "Failed to upload WeChat 2D code! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            return result;
        }
    }
}
