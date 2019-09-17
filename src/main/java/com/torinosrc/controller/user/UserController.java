/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.controller.user;

import com.alibaba.fastjson.JSON;
import com.torinosrc.commons.utils.TorinoSrcServiceExceptionUtils;
import com.torinosrc.commons.utils.message.MessageCode;
import com.torinosrc.commons.utils.message.MessageDescription;
import com.torinosrc.commons.utils.message.MessageStatus;
import com.torinosrc.commons.utils.message.MessageUtils;
import com.torinosrc.commons.utils.wechat.WeChatUtils;
import com.torinosrc.model.view.message.TorinoSrcErrorResponseMessage;
import com.torinosrc.model.view.message.TorinoSrcMessage;
import com.torinosrc.model.view.shoppingcart.ShoppingCartView;
import com.torinosrc.model.view.sysuser.SysUserView;
import com.torinosrc.model.view.user.UserView;
import com.torinosrc.service.shop.ShopService;
import com.torinosrc.service.shoppingcart.ShoppingCartService;
import com.torinosrc.service.sysuser.SysUserService;
import com.torinosrc.service.user.UserService;
import com.torinosrc.service.weixin.WechatService;
import io.swagger.annotations.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.time.LocalTime;

/**
 * <b><code>UserController</code></b>
 * <p/>
 * User的具体实现的Api类，提供统一的api调用
 * <p/>
 * <b>Creation Time:</b> 2018-04-16 14:17:14.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Api(value = "[Torino Source]用户接口",tags = "[Torino Source]用户接口",description = "")
@RestController
@RequestMapping(value = "/api")
public class UserController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(UserController.class);

    /** The service. */
    @Autowired
    private UserService userService;

    @Autowired
    private WechatService wechatService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShopService shopService;

    @Value("${authority.userName}")
    private String AUTHORITY_USERNAME;

    @Value("${authority.password}")
    private String AUTHORITY_PASSWORD;

    @ApiOperation(value = "[Torino Source]创建用户", notes = "创建一个用户")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
//    @RequiresPermissions(value = {"sys:user:add"})
    @RequestMapping(value = "/v1/users", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createUser(
            @ApiParam(value = "用户", required = true) @RequestBody UserView userView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            UserView userView1 = userService.saveEntity(userView);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/users/{id}")
                    .buildAndExpand(userView1.getId()).toUri());
            // 封装返回信息
            TorinoSrcMessage<UserView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, userView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除用户", notes = "通过id删除用户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
//    @RequiresPermissions(value = {"sys:user:delete"})
    @RequestMapping(value = "/v1/users/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteUser(
            @ApiParam(value = "用户id", required = true) @PathVariable(value = "id") Long id) {
        try {
            userService.deleteEntity(id);
            // 封装返回信息
            TorinoSrcMessage<UserView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除用户", notes = "批量删除用户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
//    @RequiresPermissions(value = {"sys:user:delete"})
    @RequestMapping(value = "/v1/users", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteUsers(
            @ApiParam(value = "用户ids，样例 - 1,2,3", required = true) @RequestBody String ids) {
        try {
            userService.deleteEntities(ids);
            // 封装返回信息
            TorinoSrcMessage<UserView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]更新用户", notes = "更新用户信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
//    @RequiresPermissions(value = {"sys:user:update"})
    @RequestMapping(value = "/v1/users/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateUsers(
            @ApiParam(value = "用户id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "用户信息", required = true) @RequestBody UserView userView) {
        try {
            userView.setId(id);
            userService.updateEntity(userView);
            // 封装返回信息
            TorinoSrcMessage<UserView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, userView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取单个用户", notes = "通过id获取用户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
//    @RequiresPermissions(value = {"sys:user:query"})
    @RequestMapping(value = "/v1/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUser(
            @ApiParam(value = "用户id", required = true) @PathVariable(value = "id") Long id) {
        try {
            final UserView userView = userService.getEntity(id);
            // 封装返回信息
            TorinoSrcMessage<UserView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, userView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取用户列表", notes = "通过查询条件获取用户列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
//    @RequiresPermissions(value = {"sys:user:query"})
    @RequestMapping(value = "/v1/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUsers(
            @ApiParam(value = "查询条件", defaultValue = "", required = false) @RequestParam(value = "condition", defaultValue = "", required = false) String condition,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        try {
            // 解析Base64的查询条件
            if(!StringUtils.isEmpty(condition)){
                condition = new String(Base64Utils.decodeFromString(condition));
            }
            UserView userView = new UserView();
            userView.setCondition(condition);

            Page<UserView> userViews = userService
                    .getEntitiesByParms(userView, pageNumber, pageSize);
            // 封装返回信息
            TorinoSrcMessage<Page<UserView>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, userViews);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @Transactional
    @ApiOperation(value = "[Torino Source]用户登录", notes = "通过code登录")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class)),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequestMapping(value = "/v1/users/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> userLogin(
            @ApiParam(value = "用户", required = true) @RequestBody UserView userView,
            UriComponentsBuilder ucBuilder) {
        try {
            String openId = wechatService.getOpenId(userView.getCode());
            UserView userView1 = userService.login(openId);

            if (org.springframework.util.ObjectUtils.isEmpty(userView1.getId())) {
                // 登录失败(创建用户表)
                userView.setOpenId(openId);
                userView1=userService.saveEntity(userView);
//                TorinoSrcMessage<UserView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.LOGIN_FAILURE, MessageStatus.SUCCESS, MessageDescription.LOGIN_FAILURE, userView);
//                return new ResponseEntity<>(torinoSrcMessage, HttpStatus.UNAUTHORIZED);
            }

            // token信息
            SysUserView sysUserView = sysUserService.login(AUTHORITY_USERNAME, AUTHORITY_PASSWORD);
            String token = sysUserView.getToken();
            userView1.setToken(token);

            ShoppingCartView shopCartView = new ShoppingCartView();
            shopCartView.setUserId(userView1.getId());
            ShoppingCartView shopCartView1 = shoppingCartService.saveEntityIfHasNot(shopCartView);
            userView1.setShoppingCartView(shopCartView1);

            // 是否开店了
            if(!org.springframework.util.ObjectUtils.isEmpty(shopService.findShopByUserId(userView1.getId()).getId())){
                userView1.setHaveShop(true);
            }else {
                userView1.setHaveShop(false);
            }
            TorinoSrcMessage<UserView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.LOGIN_SUCCESS, MessageStatus.SUCCESS, MessageDescription.LOGIN_SUCCESS, userView1);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);

        } catch (Throwable t) {
            String error = "Failed to login! " + MessageDescription.LOGIN_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

}
