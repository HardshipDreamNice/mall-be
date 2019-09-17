/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.controller.weixin;

import com.torinosrc.commons.utils.TorinoSrcServiceExceptionUtils;
import com.torinosrc.commons.utils.message.MessageCode;
import com.torinosrc.commons.utils.message.MessageDescription;
import com.torinosrc.commons.utils.message.MessageStatus;
import com.torinosrc.commons.utils.message.MessageUtils;
import com.torinosrc.model.view.message.TorinoSrcErrorResponseMessage;
import com.torinosrc.model.view.message.TorinoSrcMessage;
import com.torinosrc.model.view.weixin.WxRefundView;
import com.torinosrc.service.weixin.WxRefundService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * <b><code>WxRefundController</code></b>
 * <p/>
 * WxRefund的具体实现的Api类，提供统一的api调用
 * <p/>
 * <b>Creation Time:</b> 2018-12-10 17:30:46.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Api(value = "[Torino Source]申请退款接口",tags = "[Torino Source]申请退款接口",description = "")
@RestController
@RequestMapping(value = "/api")
public class WxRefundController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(WxRefundController.class);

    /** The service. */
    @Autowired
    private WxRefundService wxRefundService;

    @ApiOperation(value = "[Torino Source]创建申请退款", notes = "创建一个申请退款")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:wxrefund:add"})
    @RequestMapping(value = "/v1/wxrefunds", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createWxRefund(
            @ApiParam(value = "申请退款", required = true) @RequestBody WxRefundView wxRefundView,
            UriComponentsBuilder ucBuilder) {

        try {
            // 保存实体
            WxRefundView wxRefundView1 = wxRefundService.saveEntity(wxRefundView);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/wxrefunds/{id}")
                    .buildAndExpand(wxRefundView1.getId()).toUri());
            // 封装返回信息
            TorinoSrcMessage<WxRefundView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, wxRefundView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除申请退款", notes = "通过id删除申请退款")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:wxrefund:delete"})
    @RequestMapping(value = "/v1/wxrefunds/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteWxRefund(
            @ApiParam(value = "申请退款id", required = true) @PathVariable(value = "id") Long id) {
        try {

            wxRefundService.deleteEntity(id);
            // 封装返回信息
            TorinoSrcMessage<WxRefundView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除申请退款", notes = "批量删除申请退款")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:wxrefund:delete"})
    @RequestMapping(value = "/v1/wxrefunds", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteWxRefunds(
            @ApiParam(value = "申请退款ids，样例 - 1,2,3", required = true) @RequestParam(value = "condition", required = true) String condition) {

        try {
            wxRefundService.deleteEntities(condition);
            // 封装返回信息
            TorinoSrcMessage<WxRefundView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]更新申请退款", notes = "更新申请退款信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequiresPermissions(value = {"sys:wxrefund:update"})
    @RequestMapping(value = "/v1/wxrefunds/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateWxRefunds(
            @ApiParam(value = "申请退款id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "申请退款信息", required = true) @RequestBody WxRefundView wxRefundView) {
        try {

            wxRefundView.setId(id);
            wxRefundService.updateEntity(wxRefundView);
            // 封装返回信息
            TorinoSrcMessage<WxRefundView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, wxRefundView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取单个申请退款", notes = "通过id获取申请退款")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:wxrefund:query"})
    @RequestMapping(value = "/v1/wxrefunds/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getWxRefund(
            @ApiParam(value = "申请退款id", required = true) @PathVariable(value = "id") Long id) {

        try {
            final WxRefundView wxRefundView = wxRefundService.getEntity(id);
            // 封装返回信息
            TorinoSrcMessage<WxRefundView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, wxRefundView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取申请退款列表", notes = "通过查询条件获取申请退款列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:wxrefund:query"})
    @RequestMapping(value = "/v1/wxrefunds", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getWxRefunds(
            @ApiParam(value = "查询条件", defaultValue = "", required = false) @RequestParam(value = "condition", defaultValue = "", required = false) String condition,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {

        try {
            // 解析Base64的查询条件
            if(!StringUtils.isEmpty(condition)){
                condition = new String(Base64Utils.decodeFromString(condition));
            }
            WxRefundView wxRefundView = new WxRefundView();
            wxRefundView.setCondition(condition);

            Page<WxRefundView> wxRefundViews = wxRefundService
                    .getEntitiesByParms(wxRefundView, pageNumber, pageSize);
            // 封装返回信息
            TorinoSrcMessage<Page<WxRefundView>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, wxRefundViews);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

}
