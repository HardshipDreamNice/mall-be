/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.controller.shopvisitrecord;

import com.torinosrc.commons.utils.TorinoSrcServiceExceptionUtils;
import com.torinosrc.commons.utils.message.MessageCode;
import com.torinosrc.commons.utils.message.MessageDescription;
import com.torinosrc.commons.utils.message.MessageStatus;
import com.torinosrc.commons.utils.message.MessageUtils;
import com.torinosrc.model.view.message.TorinoSrcErrorResponseMessage;
import com.torinosrc.model.view.message.TorinoSrcMessage;
import com.torinosrc.model.view.shopvisitrecord.ShopVisitRecordView;
import com.torinosrc.service.shopvisitrecord.ShopVisitRecordService;
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
 * <b><code>ShopVisitRecordController</code></b>
 * <p/>
 * ShopVisitRecord的具体实现的Api类，提供统一的api调用
 * <p/>
 * <b>Creation Time:</b> 2018-11-19 18:21:46.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Api(value = "[Torino Source]店铺访问记录接口", tags = "[Torino Source]店铺访问记录接口", description = "")
@RestController
@RequestMapping(value = "/api")
public class ShopVisitRecordController {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ShopVisitRecordController.class);

    /**
     * The service.
     */
    @Autowired
    private ShopVisitRecordService shopVisitRecordService;

    @ApiOperation(value = "[Torino Source]创建店铺访问记录", notes = "创建一个店铺访问记录")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class)),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:shopvisitrecord:add"})
    @RequestMapping(value = "/v1/shopvisitrecords", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createShopVisitRecord(
            @ApiParam(value = "店铺访问记录", required = true) @RequestBody ShopVisitRecordView shopVisitRecordView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            ShopVisitRecordView shopVisitRecordView1 = shopVisitRecordService.saveEntityIfNoExistAndUpdateIfExist(shopVisitRecordView);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/shopvisitrecords/{id}")
                    .buildAndExpand(shopVisitRecordView1.getId()).toUri());
            // 封装返回信息
            TorinoSrcMessage<ShopVisitRecordView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, shopVisitRecordView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除店铺访问记录", notes = "通过id删除店铺访问记录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:shopvisitrecord:delete"})
    @RequestMapping(value = "/v1/shopvisitrecords/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteShopVisitRecord(
            @ApiParam(value = "店铺访问记录id", required = true) @PathVariable(value = "id") Long id) {
        try {
            shopVisitRecordService.deleteEntity(id);
            // 封装返回信息
            TorinoSrcMessage<ShopVisitRecordView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除店铺访问记录", notes = "批量删除店铺访问记录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:shopvisitrecord:delete"})
    @RequestMapping(value = "/v1/shopvisitrecords", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteShopVisitRecords(
            @ApiParam(value = "店铺访问记录ids，样例 - 1,2,3", required = true) @RequestParam(value = "condition", required = true) String condition) {
        try {
            shopVisitRecordService.deleteEntities(condition);
            // 封装返回信息
            TorinoSrcMessage<ShopVisitRecordView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]更新店铺访问记录", notes = "更新店铺访问记录信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error")})
    @RequiresPermissions(value = {"sys:shopvisitrecord:update"})
    @RequestMapping(value = "/v1/shopvisitrecords/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateShopVisitRecords(
            @ApiParam(value = "店铺访问记录id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "店铺访问记录信息", required = true) @RequestBody ShopVisitRecordView shopVisitRecordView) {
        try {
            shopVisitRecordView.setId(id);
            shopVisitRecordService.updateEntity(shopVisitRecordView);
            // 封装返回信息
            TorinoSrcMessage<ShopVisitRecordView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, shopVisitRecordView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取单个店铺访问记录", notes = "通过id获取店铺访问记录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:shopvisitrecord:query"})
    @RequestMapping(value = "/v1/shopvisitrecords/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getShopVisitRecord(
            @ApiParam(value = "店铺访问记录id", required = true) @PathVariable(value = "id") Long id) {
        try {
            final ShopVisitRecordView shopVisitRecordView = shopVisitRecordService.getEntity(id);
            // 封装返回信息
            TorinoSrcMessage<ShopVisitRecordView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, shopVisitRecordView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取店铺访问记录列表", notes = "通过查询条件获取店铺访问记录列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:shopvisitrecord:query"})
    @RequestMapping(value = "/v1/shopvisitrecords", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getShopVisitRecords(
            @ApiParam(value = "查询条件", defaultValue = "", required = false) @RequestParam(value = "condition", defaultValue = "", required = false) String condition,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        try {
            // 解析Base64的查询条件
            if (!StringUtils.isEmpty(condition)) {
                condition = new String(Base64Utils.decodeFromString(condition));
            }
            ShopVisitRecordView shopVisitRecordView = new ShopVisitRecordView();
            shopVisitRecordView.setCondition(condition);

            Page<ShopVisitRecordView> shopVisitRecordViews = shopVisitRecordService
                    .getEntitiesByParms(shopVisitRecordView, pageNumber, pageSize);
            // 封装返回信息
            TorinoSrcMessage<Page<ShopVisitRecordView>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, shopVisitRecordViews);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取最新店铺访问记录", notes = "获取最新店铺访问记录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:shopvisitrecord:query"})
    @RequestMapping(value = "/v1/shopvisitrecords/lastest/{userId}/{enabled}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getLatestShopVisitRecord(
            @ApiParam(value = "用户id", required = true) @PathVariable(value = "userId") Long userId,
            @ApiParam(value = "是否启用", required = true) @PathVariable(value = "enabled") Integer enabled) {
        try {
            ShopVisitRecordView shopVisitRecordView = shopVisitRecordService.getEntityUpdateLastestByUserIdAndEnabled(userId, enabled);
            // 封装返回信息
            TorinoSrcMessage<ShopVisitRecordView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, shopVisitRecordView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

}
