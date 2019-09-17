/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.controller.shopaccount;

import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcServiceExceptionUtils;
import com.torinosrc.commons.utils.message.MessageCode;
import com.torinosrc.commons.utils.message.MessageDescription;
import com.torinosrc.commons.utils.message.MessageStatus;
import com.torinosrc.commons.utils.message.MessageUtils;
import com.torinosrc.model.entity.user.User;
import com.torinosrc.model.view.message.TorinoSrcErrorResponseMessage;
import com.torinosrc.model.view.message.TorinoSrcMessage;
import com.torinosrc.model.view.shopaccount.ShopAccountView;
import com.torinosrc.model.view.user.UserView;
import com.torinosrc.service.shopaccount.ShopAccountService;
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

import java.util.List;

/**
 * <b><code>ShopAccountController</code></b>
 * <p/>
 * ShopAccount的具体实现的Api类，提供统一的api调用
 * <p/>
 * <b>Creation Time:</b> 2018-07-12 16:33:53.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Api(value = "[Torino Source]商店账户接口",tags = "[Torino Source]商店账户接口",description = "")
@RestController
@RequestMapping(value = "/api")
public class ShopAccountController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(ShopAccountController.class);

    /** The service. */
    @Autowired
    private ShopAccountService shopAccountService;

    @ApiOperation(value = "[Torino Source]创建商店账户", notes = "创建一个商店账户")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:shopaccount:add"})
    @RequestMapping(value = "/v1/shopaccounts", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createShopAccount(
            @ApiParam(value = "商店账户", required = true) @RequestBody ShopAccountView shopAccountView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            ShopAccountView shopAccountView1 = shopAccountService.saveEntity(shopAccountView);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/shopaccounts/{id}")
                    .buildAndExpand(shopAccountView1.getId()).toUri());
            // 封装返回信息
            TorinoSrcMessage<ShopAccountView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, shopAccountView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除商店账户", notes = "通过id删除商店账户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:shopaccount:delete"})
    @RequestMapping(value = "/v1/shopaccounts/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteShopAccount(
            @ApiParam(value = "商店账户id", required = true) @PathVariable(value = "id") Long id) {
        try {
            shopAccountService.deleteEntity(id);
            // 封装返回信息
            TorinoSrcMessage<ShopAccountView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除商店账户", notes = "批量删除商店账户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:shopaccount:delete"})
    @RequestMapping(value = "/v1/shopaccounts", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteShopAccounts(
            @ApiParam(value = "商店账户ids，样例 - 1,2,3", required = true) @RequestParam(value = "condition", required = true) String condition) {
        try {
            shopAccountService.deleteEntities(condition);
            // 封装返回信息
            TorinoSrcMessage<ShopAccountView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]更新商店账户", notes = "更新商店账户信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequiresPermissions(value = {"sys:shopaccount:update"})
    @RequestMapping(value = "/v1/shopaccounts/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateShopAccounts(
            @ApiParam(value = "商店账户id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "商店账户信息", required = true) @RequestBody ShopAccountView shopAccountView) {
        try {
            shopAccountView.setId(id);
            shopAccountService.updateEntity(shopAccountView);
            // 封装返回信息
            TorinoSrcMessage<ShopAccountView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, shopAccountView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取单个商店账户", notes = "通过id获取商店账户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:shopaccount:query"})
    @RequestMapping(value = "/v1/shopaccounts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getShopAccount(
            @ApiParam(value = "商店账户id", required = true) @PathVariable(value = "id") Long id) {
        try {
            final ShopAccountView shopAccountView = shopAccountService.getEntity(id);
            // 封装返回信息
            TorinoSrcMessage<ShopAccountView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, shopAccountView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取单个商店账户", notes = "通过商店id获取商店账户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:shopaccount:query"})
    @RequestMapping(value = "/v1/shopaccounts/shops/{shopId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getShopAccountByShop(
            @ApiParam(value = "商店id", required = true) @PathVariable(value = "shopId") Long shopId) {
        try {
            final ShopAccountView shopAccountView = shopAccountService.findShopAccountByShopId(shopId);
            // 封装返回信息
            TorinoSrcMessage<ShopAccountView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, shopAccountView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取商店账户列表", notes = "通过查询条件获取商店账户列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:shopaccount:query"})
    @RequestMapping(value = "/v1/shopaccounts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getShopAccounts(
            @ApiParam(value = "查询条件", defaultValue = "", required = false) @RequestParam(value = "condition", defaultValue = "", required = false) String condition,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        try {
            // 解析Base64的查询条件
            if(!StringUtils.isEmpty(condition)){
                condition = new String(Base64Utils.decodeFromString(condition));
            }
            ShopAccountView shopAccountView = new ShopAccountView();
            shopAccountView.setCondition(condition);

            Page<ShopAccountView> shopAccountViews = shopAccountService
                    .getEntitiesByParms(shopAccountView, pageNumber, pageSize);
            // 封装返回信息
            TorinoSrcMessage<Page<ShopAccountView>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, shopAccountViews);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取商店总收入", notes = "获取商店总收入")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
//    @RequiresPermissions(value = {"sys:shopaccount:query"})
    @RequestMapping(value = "/v1/shopaccounts/getincome", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getRealIncome(
            @ApiParam(value = "商店账户", required = true) @RequestBody ShopAccountView shopAccountView) {
        try {
            ShopAccountView shopAccountView1 = shopAccountService.getRealIncome(shopAccountView);
            // 封装返回信息
            TorinoSrcMessage<ShopAccountView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, shopAccountView1);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取商店总预收入", notes = "获取商店总预收入")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
//    @RequiresPermissions(value = {"sys:shopaccount:query"})
    @RequestMapping(value = "/v1/shopaccounts/getadvanceincome", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getAdvanceIncome(
            @ApiParam(value = "商店账户", required = true) @RequestBody ShopAccountView shopAccountView) {
        try {
            ShopAccountView shopAccountView1 = shopAccountService.getAdvanceIncome(shopAccountView);
            // 封装返回信息
            TorinoSrcMessage<ShopAccountView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, shopAccountView1);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取商店总销售额", notes = "获取商店总销售额")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
//    @RequiresPermissions(value = {"sys:shopaccount:query"})
    @RequestMapping(value = "/v1/shopaccounts/getsales", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getSalesAmount(
            @ApiParam(value = "商店账户", required = true) @RequestBody ShopAccountView shopAccountView) {
        try {
            ShopAccountView shopAccountView1 = shopAccountService.getSalesAmount(shopAccountView);
            // 封装返回信息
            TorinoSrcMessage<ShopAccountView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, shopAccountView1);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取我的团队收益", notes = "获取我的团队收益")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
//    @RequiresPermissions(value = {"sys:shopaccount:query"})
    @RequestMapping(value = "/v1/shopaccounts/getteamincome", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getMyTeamIncomeAmount(
            @ApiParam(value = "商店账户", required = true) @RequestBody ShopAccountView shopAccountView) {
        try {
            ShopAccountView shopAccountView1 = shopAccountService.getTeamIncome(shopAccountView);
            // 封装返回信息
            TorinoSrcMessage<ShopAccountView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, shopAccountView1);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取我的队员列表", notes = "获取我的队员列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
//    @RequiresPermissions(value = {"sys:shopaccount:query"})
    @RequestMapping(value = "/v1/shopaccounts/getteammember", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getTeamMembers(
            @ApiParam(value = "商店账户", required = true) @RequestBody ShopAccountView shopAccountView) {
        try {
            List<UserView> userViewList = shopAccountService.getTeamMembers(shopAccountView);
            // 封装返回信息
            TorinoSrcMessage<List<UserView>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, userViewList);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }
    @ApiOperation(value = "[Torino Source]获取单个商店账户及团队收益商店总销售额商店总预收入", notes = "获取单个商店账户及团队收益商店总销售额商店总预收入")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:shopaccount:query"})
    @RequestMapping(value = "/v1/shopaccounts/shops/allincomeandsales/{shopId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getShopAccountAllIncomeAndSales(
            @ApiParam(value = "商店id", required = true) @PathVariable(value = "shopId") Long shopId) {
        try {

               ShopAccountView shopAccountView = shopAccountService.findShopAccountByShopId(shopId);

               ShopAccountView    shopAccountView2=new ShopAccountView();
               TorinoSrcBeanUtils.copyBean(shopAccountView, shopAccountView2);
               //获取我的团队收益
               ShopAccountView   shopAccountView1 = shopAccountService.getTeamIncome(shopAccountView);
               shopAccountView2.setTeamIncomeAmountAll(shopAccountView1.getTeamIncomeAmountAll());
               shopAccountView2.setTeamIncomeAmountDay(shopAccountView1.getTeamIncomeAmountDay());
               shopAccountView2.setTeamIncomeAmountWeek(shopAccountView1.getTeamIncomeAmountWeek());
               shopAccountView2.setTeamIncomeAmountMonth(shopAccountView1.getTeamIncomeAmountMonth());
              //获取商店总销售额
                                 shopAccountView1 = shopAccountService.getSalesAmount(shopAccountView);
               shopAccountView2.setSalesAmountAll(shopAccountView1.getSalesAmountAll());
               shopAccountView2.setSalesAmountDay(shopAccountView1.getSalesAmountDay());
               shopAccountView2.setSalesAmountWeek(shopAccountView1.getSalesAmountWeek());
               shopAccountView2.setSalesAmountMonth(shopAccountView1.getSalesAmountMonth());
               // 获取商店总预收入
//                                 shopAccountView1 = shopAccountService.getAdvanceIncome(shopAccountView);;
//            shopAccountView2.setIncomeAmountAll(shopAccountView1.getIncomeAmountAll());
              // 获取商店总收入
                                 shopAccountView1 = shopAccountService.getRealIncome(shopAccountView);
            shopAccountView2.setIncomeAmountAll(shopAccountView1.getIncomeAmountAll());
            shopAccountView2.setIncomeAmountDay(shopAccountView1.getIncomeAmountDay());
            shopAccountView2.setIncomeAmountWeek(shopAccountView1.getIncomeAmountWeek());
            shopAccountView2.setIncomeAmountMonth(shopAccountView1.getIncomeAmountMonth());

            // 封装返回信息
            TorinoSrcMessage<ShopAccountView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, shopAccountView2);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }
}
