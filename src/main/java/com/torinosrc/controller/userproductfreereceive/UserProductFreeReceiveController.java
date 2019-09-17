/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.controller.userproductfreereceive;

import com.torinosrc.commons.utils.TorinoSrcServiceExceptionUtils;
import com.torinosrc.commons.utils.message.MessageCode;
import com.torinosrc.commons.utils.message.MessageDescription;
import com.torinosrc.commons.utils.message.MessageStatus;
import com.torinosrc.commons.utils.message.MessageUtils;
import com.torinosrc.model.entity.userproductfreereceive.UserProductFreeReceive;
import com.torinosrc.model.view.message.TorinoSrcErrorResponseMessage;
import com.torinosrc.model.view.message.TorinoSrcMessage;
import com.torinosrc.model.view.userproductfreereceive.UserProductFreeReceiveView;
import com.torinosrc.service.userproductfreereceive.UserProductFreeReceiveService;
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
 * <b><code>UserProductFreeReceiveController</code></b>
 * <p/>
 * UserProductFreeReceive的具体实现的Api类，提供统一的api调用
 * <p/>
 * <b>Creation Time:</b> 2018-11-27 19:45:53.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Api(value = "[Torino Source]免费领用户与免费领的商品中间表接口",tags = "[Torino Source]免费领用户与免费领的商品中间表接口",description = "")
@RestController
@RequestMapping(value = "/api")
public class UserProductFreeReceiveController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(UserProductFreeReceiveController.class);

    /** The service. */
    @Autowired
    private UserProductFreeReceiveService userProductFreeReceiveService;

    @ApiOperation(value = "[Torino Source]创建免费领用户与免费领的商品中间表", notes = "创建一个免费领用户与免费领的商品中间表")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:userproductfreereceive:add"})
    @RequestMapping(value = "/v1/userproductfreereceives", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createUserProductFreeReceive(
            @ApiParam(value = "免费领用户与免费领的商品中间表", required = true) @RequestBody UserProductFreeReceiveView userProductFreeReceiveView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            UserProductFreeReceiveView userProductFreeReceiveView1 = userProductFreeReceiveService.saveEntity(userProductFreeReceiveView);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/userproductfreereceives/{id}")
                    .buildAndExpand(userProductFreeReceiveView1.getId()).toUri());
            // 封装返回信息
            TorinoSrcMessage<UserProductFreeReceiveView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, userProductFreeReceiveView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除免费领用户与免费领的商品中间表", notes = "通过id删除免费领用户与免费领的商品中间表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:userproductfreereceive:delete"})
    @RequestMapping(value = "/v1/userproductfreereceives/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteUserProductFreeReceive(
            @ApiParam(value = "免费领用户与免费领的商品中间表id", required = true) @PathVariable(value = "id") Long id) {
        try {
            userProductFreeReceiveService.deleteEntity(id);
            // 封装返回信息
            TorinoSrcMessage<UserProductFreeReceiveView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除免费领用户与免费领的商品中间表", notes = "批量删除免费领用户与免费领的商品中间表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:userproductfreereceive:delete"})
    @RequestMapping(value = "/v1/userproductfreereceives", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteUserProductFreeReceives(
            @ApiParam(value = "免费领用户与免费领的商品中间表ids，样例 - 1,2,3", required = true) @RequestParam(value = "condition", required = true) String condition) {
        try {
            userProductFreeReceiveService.deleteEntities(condition);
            // 封装返回信息
            TorinoSrcMessage<UserProductFreeReceiveView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]更新免费领用户与免费领的商品中间表", notes = "更新免费领用户与免费领的商品中间表信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequiresPermissions(value = {"sys:userproductfreereceive:update"})
    @RequestMapping(value = "/v1/userproductfreereceives/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateUserProductFreeReceives(
            @ApiParam(value = "免费领用户与免费领的商品中间表id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "免费领用户与免费领的商品中间表信息", required = true) @RequestBody UserProductFreeReceiveView userProductFreeReceiveView) {
        try {
            userProductFreeReceiveView.setId(id);
            userProductFreeReceiveService.updateEntity(userProductFreeReceiveView);
            // 封装返回信息
            TorinoSrcMessage<UserProductFreeReceiveView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, userProductFreeReceiveView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取单个免费领用户与免费领的商品中间表", notes = "通过id获取免费领用户与免费领的商品中间表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:userproductfreereceive:query"})
    @RequestMapping(value = "/v1/userproductfreereceives/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUserProductFreeReceive(
            @ApiParam(value = "免费领用户与免费领的商品中间表id", required = true) @PathVariable(value = "id") Long id) {
        try {
            final UserProductFreeReceiveView userProductFreeReceiveView = userProductFreeReceiveService.getEntity(id);
            // 封装返回信息
            TorinoSrcMessage<UserProductFreeReceiveView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, userProductFreeReceiveView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取免费领用户与免费领的商品中间表列表", notes = "通过查询条件获取免费领用户与免费领的商品中间表列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:userproductfreereceive:query"})
    @RequestMapping(value = "/v1/userproductfreereceives", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUserProductFreeReceives(
            @ApiParam(value = "查询条件", defaultValue = "", required = false) @RequestParam(value = "condition", defaultValue = "", required = false) String condition,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        try {
            // 解析Base64的查询条件
            if(!StringUtils.isEmpty(condition)){
                condition = new String(Base64Utils.decodeFromString(condition));
            }
            UserProductFreeReceiveView userProductFreeReceiveView = new UserProductFreeReceiveView();
            userProductFreeReceiveView.setCondition(condition);

            Page<UserProductFreeReceiveView> userProductFreeReceiveViews = userProductFreeReceiveService
                    .getEntitiesByParms(userProductFreeReceiveView, pageNumber, pageSize);
            // 封装返回信息
            TorinoSrcMessage<Page<UserProductFreeReceiveView>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, userProductFreeReceiveViews);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    //用户点击分享免费领接口
    @ApiOperation(value = "[Torino Source]用户点击分享免费领接口", notes = "用户点击分享免费领接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
//    @RequiresPermissions(value = {"sys:userproductfreereceive:add"})
    @RequestMapping(value = "/v1/usershareproductfreereceives", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> usershowproductfreereceive(
            @ApiParam(value = "分享者id",  required = true) @RequestParam(value = "shareUserId", required = false) Long shareUserId,
            @ApiParam(value = "免费领的商品表中该商品id",  required = true) @RequestParam(value = "productFreeReceiveId", required = false) Long productFreeReceiveId,
            @ApiParam(value = "免费领的商品表中该商品详情id", required = false) @RequestParam(value = "productFreeReceiveDetailId",  required = false) Long productFreeReceiveDetailId){
        try {

            //业务逻辑
            UserProductFreeReceiveView userproductfreereceiveView = new UserProductFreeReceiveView();
            userproductfreereceiveView.setShareUserId(shareUserId);
            userproductfreereceiveView.setProductFreeReceiveId(productFreeReceiveId);
            userproductfreereceiveView.setProductFreeReceiveDetailId(productFreeReceiveDetailId);
            userProductFreeReceiveService.insertUserProductFreeReceiveEntity(userproductfreereceiveView);

            // 封装返回信息
            TorinoSrcMessage<UserProductFreeReceiveView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, userproductfreereceiveView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }
    //其他人帮忙免费领接口
    @ApiOperation(value = "[Torino Source]其他用户帮忙免费领接口", notes = "其他用户帮忙免费领接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
//    @RequiresPermissions(value = {"sys:userproductfreereceive:update"})
    @RequestMapping(value = "/v1/otherhelpproductfreereceives", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> otherhelpproductfreereceive(
            @ApiParam(value = "分享者id", required = false) @RequestParam(value = "shareUserId", required = false) Long shareUserId,
            @ApiParam(value = "帮助者id", required = false) @RequestParam(value = "helpUserId",  required = false) Long helpUserId,
            @ApiParam(value = "免费领的商品表中该商品id", required = false) @RequestParam(value = "productFreeReceiveId",  required = false) Long productFreeReceiveId,
            @ApiParam(value = "免费领的商品表中该商品详情id", required = false) @RequestParam(value = "productFreeReceiveDetailId",  required = false) Long productFreeReceiveDetailId) {
        try {
            //业务逻辑
            UserProductFreeReceiveView userproductfreereceiveView = new UserProductFreeReceiveView();
            userproductfreereceiveView.setShareUserId(shareUserId);
            userproductfreereceiveView.setHelpUserId(helpUserId);
            userproductfreereceiveView.setProductFreeReceiveId(productFreeReceiveId);
            userproductfreereceiveView.setProductFreeReceiveDetailId(productFreeReceiveDetailId);
            userProductFreeReceiveService.otherHelpProductFreeReceivesUpdate(userproductfreereceiveView);

            TorinoSrcMessage<UserProductFreeReceiveView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, userproductfreereceiveView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }
}
