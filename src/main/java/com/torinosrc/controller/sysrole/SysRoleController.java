/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.controller.sysrole;

import com.torinosrc.commons.utils.TorinoSrcServiceExceptionUtils;
import com.torinosrc.commons.utils.message.MessageCode;
import com.torinosrc.commons.utils.message.MessageDescription;
import com.torinosrc.commons.utils.message.MessageStatus;
import com.torinosrc.commons.utils.message.MessageUtils;
import com.torinosrc.model.view.message.TorinoSrcErrorResponseMessage;
import com.torinosrc.model.view.message.TorinoSrcMessage;
import com.torinosrc.model.view.sysrole.SysRoleView;
import com.torinosrc.response.json.JSON;
import com.torinosrc.service.sysrole.SysRoleService;
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
 * <b><code>SysRoleController</code></b>
 * <p/>
 * SysRole的具体实现的Api类，提供统一的api调用
 * <p/>
 * <b>Creation Time:</b> 2018-04-13 14:41:37.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Api(value = "[Torino Source]系统角色接口",tags = "[Torino Source]系统角色接口",description = "")
@RestController
@RequestMapping(value = "/api")
public class SysRoleController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(SysRoleController.class);

    /** The service. */
    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "[Torino Source]创建系统角色", notes = "创建一个系统角色")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/v1/sysroles", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysrole:add"})
    public ResponseEntity<?> createSysRole(
            @ApiParam(value = "系统角色", required = true) @RequestBody SysRoleView sysRoleView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            SysRoleView sysRoleView1 = sysRoleService.saveEntity(sysRoleView);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/sysroles/{id}")
                    .buildAndExpand(sysRoleView1.getId()).toUri());
            // 封装返回信息
            TorinoSrcMessage<SysRoleView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, sysRoleView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除系统角色", notes = "通过id删除系统角色")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/v1/sysroles/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysrole:delete"})
    public ResponseEntity<?> deleteSysRole(
            @ApiParam(value = "系统角色id", required = true) @PathVariable(value = "id") Long id) {
        try {
            sysRoleService.deleteEntity(id);
            // 封装返回信息
            TorinoSrcMessage<SysRoleView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除系统角色", notes = "批量删除系统角色")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/v1/sysroles", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysrole:delete"})
    public ResponseEntity<?> deleteSysRoles(
            @ApiParam(value = "后台用户ids，样例 - 1,2,3", required = true) @RequestParam(value = "condition", required = false) String condition) {
        try {
            sysRoleService.deleteEntities(condition);
            // 封装返回信息
            TorinoSrcMessage<SysRoleView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]更新系统角色", notes = "更新系统角色信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/v1/sysroles/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysrole:update"})
    public ResponseEntity<?> updateSysRoles(
            @ApiParam(value = "系统角色id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "系统角色信息", required = true) @RequestBody SysRoleView sysRoleView) {
        try {
            sysRoleView.setId(id);
            sysRoleService.updateEntity(sysRoleView);
            // 封装返回信息
            TorinoSrcMessage<SysRoleView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, sysRoleView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取单个系统角色", notes = "通过id获取系统角色")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/v1/sysroles/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysrole:query"})
    public ResponseEntity<?> getSysRole(
            @ApiParam(value = "系统角色id", required = true) @PathVariable(value = "id") Long id) {
        try {
            final SysRoleView sysRoleView = sysRoleService.getEntity(id);
            // 封装返回信息
            TorinoSrcMessage<SysRoleView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, sysRoleView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取系统角色列表", notes = "通过查询条件获取系统角色列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/v1/sysroles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysrole:query"})
    @JSON(type = SysRoleView.class)
    public ResponseEntity<?> getSysRoles(
            @ApiParam(value = "返回字段(base64编码，include表示包含字段，filter表示排除字段)，比如：{\"include\":\"id,name\"}或者{\"filter\":\"id,name\"}", defaultValue = "", required = false) @RequestParam(value = "include", defaultValue = "", required = false) String include,
            @ApiParam(value = "查询条件", defaultValue = "", required = false) @RequestParam(value = "condition", defaultValue = "", required = false) String condition,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        try {
            // 解析Base64的查询条件
            if(!StringUtils.isEmpty(condition)){
                condition = new String(Base64Utils.decodeFromString(condition));
            }
            SysRoleView sysRoleView = new SysRoleView();
            sysRoleView.setCondition(condition);

            Page<SysRoleView> sysRoleViews = sysRoleService
                    .getEntitiesByParms(sysRoleView, pageNumber, pageSize);
            // 封装返回信息
            TorinoSrcMessage<Page<SysRoleView>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, sysRoleViews);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]检查角色名是否存在", notes = "检查角色名是否存在")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/v1/sysroles/chineseName/{chineseName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysrole:query"})
    public ResponseEntity<?> checkSysRoleName(
            @ApiParam(value = "角色名", required = true) @PathVariable(value = "chineseName") String chineseName) {
        try {
            boolean isExist = sysRoleService.checkIfExist(chineseName);
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
