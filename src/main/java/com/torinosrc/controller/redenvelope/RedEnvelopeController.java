/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.controller.redenvelope;

import com.torinosrc.commons.utils.TorinoSrcServiceExceptionUtils;
import com.torinosrc.commons.utils.message.MessageCode;
import com.torinosrc.commons.utils.message.MessageDescription;
import com.torinosrc.commons.utils.message.MessageStatus;
import com.torinosrc.commons.utils.message.MessageUtils;
import com.torinosrc.model.view.message.TorinoSrcErrorResponseMessage;
import com.torinosrc.model.view.message.TorinoSrcMessage;
import com.torinosrc.model.view.redenvelope.RedEnvelopeView;
import com.torinosrc.service.redenvelope.RedEnvelopeService;
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
 * <b><code>RedEnvelopeController</code></b>
 * <p/>
 * RedEnvelope的具体实现的Api类，提供统一的api调用
 * <p/>
 * <b>Creation Time:</b> 2018-12-04 12:09:47.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Api(value = "[Torino Source]红包接口",tags = "[Torino Source]红包接口",description = "")
@RestController
@RequestMapping(value = "/api")
public class RedEnvelopeController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(RedEnvelopeController.class);

    /** The service. */
    @Autowired
    private RedEnvelopeService redEnvelopeService;

    @ApiOperation(value = "[Torino Source]创建红包", notes = "创建一个红包")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:redenvelope:add"})
    @RequestMapping(value = "/v1/redenvelopes", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createRedEnvelope(
            @ApiParam(value = "红包", required = true) @RequestBody RedEnvelopeView redEnvelopeView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            RedEnvelopeView redEnvelopeView1 = redEnvelopeService.saveEntity(redEnvelopeView);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/redenvelopes/{id}")
                    .buildAndExpand(redEnvelopeView1.getId()).toUri());
            // 封装返回信息
            TorinoSrcMessage<RedEnvelopeView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, redEnvelopeView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除红包", notes = "通过id删除红包")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:redenvelope:delete"})
    @RequestMapping(value = "/v1/redenvelopes/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteRedEnvelope(
            @ApiParam(value = "红包id", required = true) @PathVariable(value = "id") Long id) {
        try {
            redEnvelopeService.deleteEntity(id);
            // 封装返回信息
            TorinoSrcMessage<RedEnvelopeView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除红包", notes = "批量删除红包")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:redenvelope:delete"})
    @RequestMapping(value = "/v1/redenvelopes", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteRedEnvelopes(
            @ApiParam(value = "红包ids，样例 - 1,2,3", required = true) @RequestParam(value = "condition", required = true) String condition) {
        try {
            redEnvelopeService.deleteEntities(condition);
            // 封装返回信息
            TorinoSrcMessage<RedEnvelopeView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]更新红包", notes = "更新红包信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequiresPermissions(value = {"sys:redenvelope:update"})
    @RequestMapping(value = "/v1/redenvelopes/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateRedEnvelopes(
            @ApiParam(value = "红包id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "红包信息", required = true) @RequestBody RedEnvelopeView redEnvelopeView) {
        try {
            redEnvelopeView.setId(id);
            redEnvelopeService.updateEntity(redEnvelopeView);
            // 封装返回信息
            TorinoSrcMessage<RedEnvelopeView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, redEnvelopeView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取单个红包", notes = "通过id获取红包")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:redenvelope:query"})
    @RequestMapping(value = "/v1/redenvelopes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getRedEnvelope(
            @ApiParam(value = "红包id", required = true) @PathVariable(value = "id") Long id) {
        try {
            final RedEnvelopeView redEnvelopeView = redEnvelopeService.getEntity(id);
            // 封装返回信息
            TorinoSrcMessage<RedEnvelopeView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, redEnvelopeView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取红包列表", notes = "通过查询条件获取红包列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:redenvelope:query"})
    @RequestMapping(value = "/v1/redenvelopes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getRedEnvelopes(
            @ApiParam(value = "查询条件", defaultValue = "", required = false) @RequestParam(value = "condition", defaultValue = "", required = false) String condition,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        try {
            // 解析Base64的查询条件
            if(!StringUtils.isEmpty(condition)){
                condition = new String(Base64Utils.decodeFromString(condition));
            }
            RedEnvelopeView redEnvelopeView = new RedEnvelopeView();
            redEnvelopeView.setCondition(condition);

            Page<RedEnvelopeView> redEnvelopeViews = redEnvelopeService
                    .getEntitiesByParms(redEnvelopeView, pageNumber, pageSize);
            // 封装返回信息
            TorinoSrcMessage<Page<RedEnvelopeView>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, redEnvelopeViews);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

}
