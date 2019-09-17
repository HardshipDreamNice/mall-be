/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.controller.sysauthority;

import com.torinosrc.commons.utils.TorinoSrcServiceExceptionUtils;
import com.torinosrc.commons.utils.message.MessageCode;
import com.torinosrc.commons.utils.message.MessageDescription;
import com.torinosrc.commons.utils.message.MessageStatus;
import com.torinosrc.commons.utils.message.MessageUtils;
import com.torinosrc.model.view.message.TorinoSrcErrorResponseMessage;
import com.torinosrc.model.view.message.TorinoSrcMessage;
import com.torinosrc.model.view.sysauthority.SysAuthorityView;
import com.torinosrc.response.json.JSON;
import com.torinosrc.service.sysauthority.SysAuthorityService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.AuthorizationException;
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
 * <b><code>SysAuthorityController</code></b>
 * <p/>
 * SysAuthority的具体实现的Api类，提供统一的api调用
 * <p/>
 * <b>Creation Time:</b> 2018-04-13 14:31:03.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Api(value = "[Torino Source]后台管理权限接口",tags = "[Torino Source]后台管理权限接口",description = "")
@RestController
@RequestMapping(value = "/api")
public class SysAuthorityController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(SysAuthorityController.class);

    /** The service. */
    @Autowired
    private SysAuthorityService sysAuthorityService;

    @ApiOperation(value = "[Torino Source]创建后台管理权限", notes = "创建一个后台管理权限")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/v1/sysauthoritys", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysauthority:add"})
    public ResponseEntity<?> createSysAuthority(
            @ApiParam(value = "后台管理权限", required = true) @RequestBody SysAuthorityView sysAuthorityView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            SysAuthorityView sysAuthorityView1 = sysAuthorityService.saveEntity(sysAuthorityView);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/sysauthoritys/{id}")
                    .buildAndExpand(sysAuthorityView1.getId()).toUri());
            // 封装返回信息
            TorinoSrcMessage<SysAuthorityView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, sysAuthorityView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        }
        catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除后台管理权限", notes = "通过id删除后台管理权限")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/v1/sysauthoritys/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysauthority:delete"})
    public ResponseEntity<?> deleteSysAuthority(
            @ApiParam(value = "后台管理权限id", required = true) @PathVariable(value = "id") Long id) {
        try {
            sysAuthorityService.deleteEntity(id);
            // 封装返回信息
            TorinoSrcMessage<SysAuthorityView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除后台管理权限", notes = "批量删除后台管理权限")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/v1/sysauthoritys", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysauthority:delete"})
    public ResponseEntity<?> deleteSysAuthoritys(
        @ApiParam(value = "后台用户ids，样例 - 1,2,3", required = true) @RequestParam(value = "condition", required = false) String condition) {
        try {
            sysAuthorityService.deleteEntities(condition);
            // 封装返回信息
            TorinoSrcMessage<SysAuthorityView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]更新后台管理权限", notes = "更新后台管理权限信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/v1/sysauthoritys/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysauthority:update"})
    public ResponseEntity<?> updateSysAuthoritys(
            @ApiParam(value = "后台管理权限id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "后台管理权限信息", required = true) @RequestBody SysAuthorityView sysAuthorityView) {
        try {
            sysAuthorityView.setId(id);
            sysAuthorityService.updateEntity(sysAuthorityView);
            // 封装返回信息
            TorinoSrcMessage<SysAuthorityView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, sysAuthorityView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取单个后台管理权限", notes = "通过id获取后台管理权限")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/v1/sysauthoritys/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysauthority:query"})
    public ResponseEntity<?> getSysAuthority(
            @ApiParam(value = "后台管理权限id", required = true) @PathVariable(value = "id") Long id) {
        try {
            final SysAuthorityView sysAuthorityView = sysAuthorityService.getEntity(id);
            // 封装返回信息
            TorinoSrcMessage<SysAuthorityView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, sysAuthorityView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取后台管理权限列表", notes = "通过查询条件获取后台管理权限列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/v1/sysauthoritys", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysauthority:query"})
    @JSON(type = SysAuthorityView.class)
    public ResponseEntity<?> getSysAuthoritys(
            @ApiParam(value = "返回字段(base64编码，include表示包含字段，filter表示排除字段)，比如：{\"include\":\"id,name\"}或者{\"filter\":\"id,name\"}", defaultValue = "", required = false) @RequestParam(value = "include", defaultValue = "", required = false) String include,
            @ApiParam(value = "查询条件", defaultValue = "", required = false) @RequestParam(value = "condition", defaultValue = "", required = false) String condition,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        try {
            // 解析Base64的查询条件
            if(!StringUtils.isEmpty(condition)){
                condition = new String(Base64Utils.decodeFromString(condition));
            }
            SysAuthorityView sysAuthorityView = new SysAuthorityView();
            sysAuthorityView.setCondition(condition);

            Page<SysAuthorityView> sysAuthorityViews = sysAuthorityService
                    .getEntitiesByParms(sysAuthorityView, pageNumber, pageSize);
            // 封装返回信息
            TorinoSrcMessage<Page<SysAuthorityView>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, sysAuthorityViews);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]检查权限名是否存在", notes = "检查权限名是否存在")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/v1/sysauthoritys/name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysauthority:query"})
    public ResponseEntity<?> checkSysAuthorityName(
            @ApiParam(value = "权限名", required = true) @PathVariable(value = "name") String name) {
        try {
            boolean isExist = sysAuthorityService.checkIfExist(name);
            // 封装返回信息
            TorinoSrcMessage<Boolean> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, isExist);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

}
