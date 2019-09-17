/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.controller.sysuser;

import com.torinosrc.commons.exceptions.TorinoSrcApplicationException;
import com.torinosrc.commons.utils.TorinoSrcServiceExceptionUtils;
import com.torinosrc.response.json.JSON;
import com.torinosrc.commons.utils.message.MessageCode;
import com.torinosrc.commons.utils.message.MessageDescription;
import com.torinosrc.commons.utils.message.MessageStatus;
import com.torinosrc.commons.utils.message.MessageUtils;
import com.torinosrc.model.view.captcha.CaptchaView;
import com.torinosrc.model.view.message.TorinoSrcErrorResponseMessage;
import com.torinosrc.model.view.message.TorinoSrcMessage;
import com.torinosrc.model.view.sysuser.SysUserView;
import com.torinosrc.security.JWTUtil;
import com.torinosrc.security.TorinoSrcPasswordHelper;
import com.torinosrc.service.captcha.CaptchaService;
import com.torinosrc.service.sysuser.SysUserService;
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
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

/**
 * <b><code>SysUserController</code></b>
 * <p/>
 * SysUser的具体实现的Api类，提供统一的api调用
 * <p/>
 * <b>Creation Time:</b> 2018-04-13 14:33:50.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Api(value = "[Torino Source]后台用户接口",tags = "[Torino Source]后台用户接口",description = "")
@RestController
@RequestMapping(value = "/api")
public class SysUserController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(SysUserController.class);

    /** The service. */
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CaptchaService captchaService;

    @ApiOperation(value = "[Torino Source]创建后台用户", notes = "创建一个后台用户")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:sysuser:add"})
    @RequestMapping(value = "/v1/sysusers", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createSysUser(
            @ApiParam(value = "后台用户", required = true) @RequestBody SysUserView sysUserView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            SysUserView sysUserView1 = sysUserService.saveEntity(sysUserView);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/sysusers/{id}")
                    .buildAndExpand(sysUserView1.getId()).toUri());
            // 封装返回信息
            TorinoSrcMessage<SysUserView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, sysUserView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除后台用户", notes = "通过id删除后台用户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/v1/sysusers/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysuser:delete"})
    public ResponseEntity<?> deleteSysUser(
            @ApiParam(value = "后台用户id", required = true) @PathVariable(value = "id") Long id) {
        try {
            sysUserService.deleteEntity(id);
            // 封装返回信息
            TorinoSrcMessage<SysUserView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除后台用户", notes = "批量删除后台用户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/v1/sysusers", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysuser:delete"})
    public ResponseEntity<?> deleteSysUsers(
            @ApiParam(value = "后台用户ids，样例 - 1,2,3", required = true) @RequestParam(value = "condition", required = false) String condition) {
        try {
            sysUserService.deleteEntities(condition);
            // 封装返回信息
            TorinoSrcMessage<SysUserView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]更新后台用户", notes = "更新后台用户信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/v1/sysusers/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysuser:update"})
    public ResponseEntity<?> updateSysUsers(
            @ApiParam(value = "后台用户id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "后台用户信息", required = true) @RequestBody SysUserView sysUserView) {
        try {
            sysUserView.setId(id);
            sysUserService.updateEntity(sysUserView);
            // 封装返回信息
            TorinoSrcMessage<SysUserView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, sysUserView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取单个后台用户", notes = "通过id获取后台用户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/v1/sysusers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysuser:query"})
    public ResponseEntity<?> getSysUser(
            @ApiParam(value = "后台用户id", required = true) @PathVariable(value = "id") Long id) {
        try {
            final SysUserView sysUserView = sysUserService.getEntity(id);
            // 封装返回信息
            TorinoSrcMessage<SysUserView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, sysUserView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取后台用户列表", notes = "通过查询条件获取后台用户列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/v1/sysusers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysuser:query"})
    @JSON(type = SysUserView.class)
//    @JSON(type = SysUserView.class, include="id,name,sysRoles")
//    @JSON(type = SysRole.class, filter="sysAuthorities,createTime")
    public ResponseEntity<?> getSysUsers(
            @ApiParam(value = "返回字段(base64编码，include表示包含字段，filter表示排除字段)，比如：{\"include\":\"id,name\"}或者{\"filter\":\"id,name\"}", defaultValue = "", required = false) @RequestParam(value = "field", defaultValue = "", required = false) String field,
            @ApiParam(value = "查询条件", defaultValue = "", required = false) @RequestParam(value = "condition", defaultValue = "", required = false) String condition,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        try {
            // 解析Base64的查询条件
            if(!StringUtils.isEmpty(condition)){
                condition = new String(Base64Utils.decodeFromString(condition));
            }
            SysUserView sysUserView = new SysUserView();
            sysUserView.setCondition(condition);

            Page<SysUserView> sysUserViews = sysUserService
                    .getEntitiesByParms(sysUserView, pageNumber, pageSize);
            // 封装返回信息
            TorinoSrcMessage<Page<SysUserView>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, sysUserViews);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]后台登陆接口", notes = "创建一个后台登陆token")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/v1/sysusers/login", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> login(
            @ApiParam(value = "后台用户", required = true) @RequestBody SysUserView sysUserView,
            UriComponentsBuilder ucBuilder) {
        try {
            CaptchaView captchaView = new CaptchaView();
            captchaView.setUserName(sysUserView.getValidateId());
            captchaView.setValidateCode(sysUserView.getValidateCode());
            if(!captchaService.validateCaptcha(captchaView, 1)){
                // TODO: 需要优化
                throw new TorinoSrcApplicationException("验证码错误");
            }

            // 保存实体
            SysUserView sysUserView1 = sysUserService.login(sysUserView.getUserName(), sysUserView.getPassword());
            TorinoSrcMessage<SysUserView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.LOGIN_SUCCESS, sysUserView1);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity! " + MessageDescription.LOGIN_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = null;
            if("验证码错误".equals(t.getMessage())){
                torinoSrcMessage = MessageUtils.setMessage(MessageCode.LOGIN_VALIDATE_CODE_WRONG, MessageStatus.ERROR, "验证码错误", new TorinoSrcErrorResponseMessage(t.getMessage()));
            }else {
                torinoSrcMessage = MessageUtils.setMessage(MessageCode.LOGIN_FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            }

            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]修改后台用户密码", notes = "修改后台用户密码")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/v1/sysusers/{id}/password", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysuser:update"})
    public ResponseEntity<?> updateSysUserPassword(
            @ApiParam(value = "后台用户id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "后台用户信息", required = true) @RequestBody SysUserView sysUserView,
            @ApiParam(value = "HttpHeaders") @RequestHeader HttpHeaders headers
            ) {
        try {
            Optional<String> tokenOptional = Optional.ofNullable(headers.get("authorization").toString());
            tokenOptional.map(token ->{
                String userId = JWTUtil.getValue(token, "id");
                SysUserView sysUserView1 = sysUserService.getEntity(id);
                if(ObjectUtils.isEmpty(sysUserView1)){
//                    throw new TorinoSrcApplicationException("查无该用户，id："  + id + "！");
                    throw new TorinoSrcApplicationException("账号/密码不正确，请重新输入！");
                }else {
                    if(TorinoSrcPasswordHelper.match(sysUserView.getUserName(), sysUserView.getPassword(),sysUserView1.getPassword())){
                        sysUserView1.setPassword(TorinoSrcPasswordHelper.encode(sysUserView.getUserName(),sysUserView.getNewPassword()));
                        sysUserService.updateEntity(sysUserView1);
                    }else {
                        throw new TorinoSrcApplicationException("账号/密码不正确，请重新输入！");
                    }
                }

//                if(id.toString().equals(userId)){
//
//                }else {
//                    throw new TorinoSrcApplicationException("不能修改其它人的密码！");
//                }
                return token;
            }).orElseThrow(()->new TorinoSrcApplicationException("token没有包含authorization信息，token有误！"));

            // 封装返回信息
            TorinoSrcMessage<SysUserView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, sysUserView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]检查用户名是否存在", notes = "检查用户名是否存在")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/v1/sysusers/userName/{userName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysuser:query"})
    public ResponseEntity<?> checkSysUserName(
            @ApiParam(value = "用户名", required = true) @PathVariable(value = "userName") String userName) {
        try {
            boolean isExist = sysUserService.checkIfExist(userName);
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

    @ApiOperation(value = "[Torino Source]验证后台用户密码", notes = "验证后台用户密码")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/v1/sysusers/{id}/password/verify", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value = {"sys:sysuser:query"})
    public ResponseEntity<?> verifySysUserPassword(
            @ApiParam(value = "后台用户id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "后台用户信息", required = true) @RequestBody SysUserView sysUserView,
            @ApiParam(value = "HttpHeaders") @RequestHeader HttpHeaders headers
    ) {
        try {
            Optional<String> tokenOptional = Optional.ofNullable(headers.get("authorization").toString());
            tokenOptional.map(token ->{
                SysUserView sysUserView1 = sysUserService.getEntity(id);
                if(ObjectUtils.isEmpty(sysUserView1)){
                    throw new TorinoSrcApplicationException("账号/密码不正确，请重新输入！");
                }else {
                    if(!TorinoSrcPasswordHelper.match(sysUserView.getUserName(), sysUserView.getPassword(),sysUserView1.getPassword())){
                        throw new TorinoSrcApplicationException("账号/密码不正确，请重新输入！");
                    }
                }
                return token;
            }).orElseThrow(()->new TorinoSrcApplicationException("token没有包含authorization信息，token有误！"));

            // 封装返回信息
            TorinoSrcMessage<SysUserView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, sysUserView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to verify entity! " + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }



}
