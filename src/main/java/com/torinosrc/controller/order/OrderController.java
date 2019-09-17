/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.controller.order;

import com.torinosrc.commons.constants.MallConstant;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcServiceExceptionUtils;
import com.torinosrc.commons.utils.message.MessageCode;
import com.torinosrc.commons.utils.message.MessageDescription;
import com.torinosrc.commons.utils.message.MessageStatus;
import com.torinosrc.commons.utils.message.MessageUtils;
import com.torinosrc.model.view.message.TorinoSrcErrorResponseMessage;
import com.torinosrc.model.view.message.TorinoSrcMessage;
import com.torinosrc.model.view.order.GroupOrderView;
import com.torinosrc.model.view.order.MembershipOrderView;
import com.torinosrc.model.view.order.OrderPageView;
import com.torinosrc.model.view.order.OrderView;
import com.torinosrc.service.order.OrderService;
import io.swagger.annotations.*;
import net.sf.json.JSONObject;
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

/**
 * <b><code>OrderController</code></b>
 * <p/>
 * Order的具体实现的Api类，提供统一的api调用
 * <p/>
 * <b>Creation Time:</b> 2018-06-06 11:31:03.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Api(value = "[Torino Source]订单表接口", tags = "[Torino Source]订单表接口", description = "")
@RestController
@RequestMapping(value = "/api")
public class OrderController {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(OrderController.class);

    /**
     * The service.
     */
    @Autowired
    private OrderService orderService;

    private final static Object obj1 = new Object();

    private final static Object obj2 = new Object();

    @ApiOperation(value = "[Torino Source]创建订单表", notes = "创建一个订单表")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class)),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:order:add"})
    @RequestMapping(value = "/v1/orders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createOrder(
            @ApiParam(value = "订单表", required = true) @RequestBody OrderView orderView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            OrderView orderView1 = orderService.saveEntity(orderView);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/orders/{id}")
                    .buildAndExpand(orderView1.getId()).toUri());
            // 封装返回信息
            TorinoSrcMessage<OrderView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, orderView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (TorinoSrcServiceException t) {
            String error = "Failed to create order! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(t.getErrorCode(), MessageStatus.ERROR, t.getMessage(), new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除订单表", notes = "通过id删除订单表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:order:delete"})
    @RequestMapping(value = "/v1/orders/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteOrder(
            @ApiParam(value = "订单表id", required = true) @PathVariable(value = "id") Long id) {
        try {
            orderService.deleteEntity(id);
            // 封装返回信息
            TorinoSrcMessage<OrderView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除订单表", notes = "批量删除订单表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:order:delete"})
    @RequestMapping(value = "/v1/orders", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteOrders(
            @ApiParam(value = "订单表ids，样例 - 1,2,3", required = true) @RequestParam(value = "condition", required = true) String condition) {
        try {
            orderService.deleteEntities(condition);
            // 封装返回信息
            TorinoSrcMessage<OrderView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]更新订单表", notes = "更新订单表信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error")})
    @RequiresPermissions(value = {"sys:order:update"})
    @RequestMapping(value = "/v1/orders/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateOrders(
            @ApiParam(value = "订单表id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "订单表信息", required = true) @RequestBody OrderView orderView) {
        try {
            orderView.setId(id);
            orderService.updateEntity(orderView);
            // 封装返回信息
            TorinoSrcMessage<OrderView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, orderView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]更新订单表状态", notes = "更新订单表状态信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error")})
    @RequiresPermissions(value = {"sys:order:update"})
    @RequestMapping(value = "/v1/orders/{id}/status", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateOrdersStatus(
            @ApiParam(value = "订单表id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "订单表信息", required = true) @RequestBody OrderView orderView) {
        try {
            orderView.setId(id);
            orderService.updateEntityStatus(orderView);
            // 封装返回信息
            TorinoSrcMessage<OrderView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, orderView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (TorinoSrcServiceException t) {
            String error = "Failed to update order status! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(t.getErrorCode(), MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取单个订单表", notes = "通过id获取订单表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:order:query"})
    @RequestMapping(value = "/v1/orders/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getOrder(
            @ApiParam(value = "订单表id", required = true) @PathVariable(value = "id") Long id) {
        try {
            final OrderView orderView = orderService.getEntity(id);
            // 会员订单没有收货地址
            if (!MallConstant.ORDER_TYPE_MEMBER.equals(orderView.getOrderType())) {
                JSONObject customerConsignee = JSONObject.fromObject(orderView.getCustomerConsigneeString());
                String address = customerConsignee.getString("province") + " "
                        + customerConsignee.getString("city") + " "
                        + customerConsignee.getString("county") + " "
                        + customerConsignee.getString("address");
                orderView.setAddress(address);
                orderView.setPhone(customerConsignee.getString("phone"));
                orderView.setContact(customerConsignee.getString("contact"));
            }

            // 封装返回信息
            TorinoSrcMessage<OrderView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, orderView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取订单表列表", notes = "通过查询条件获取订单表列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:order:query"})
    @RequestMapping(value = "/v1/orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getOrders(
            @ApiParam(value = "查询条件", defaultValue = "", required = false) @RequestParam(value = "condition", defaultValue = "", required = false) String condition,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        try {
            // 解析Base64的查询条件
            if (!StringUtils.isEmpty(condition)) {
                condition = new String(Base64Utils.decodeFromString(condition));
            }
            OrderView orderView = new OrderView();
            orderView.setCondition(condition);

            Page<OrderView> orderViews = orderService
                    .getEntitiesByParms(orderView, pageNumber, pageSize);
            // 封装返回信息
            TorinoSrcMessage<Page<OrderView>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, orderViews);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取某用户的订单表", notes = "通过用户id获取订单表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:order:query"})
//    @JSON(type = OrderView.class, include="id,orderDetails")
//    @JSON(type = ProductDetail.class)
//    @JSON(type = Product.class, include="id")
    @RequestMapping(value = "/v1/users/{id}/orders/{status}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUOrder(
            @ApiParam(value = "用户表id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "订单状态", required = true) @PathVariable(value = "status") int status,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        try {
            OrderPageView orderPageView = orderService.findByUserIdAndstatus(id, status, pageNumber, pageSize);
            // 封装返回信息
            TorinoSrcMessage<OrderPageView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, orderPageView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取某用户商铺的订单表", notes = "通过用户id与商铺id获取订单表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:order:query"})
//    @JSON(type = OrderView.class, include="id,orderDetails")
//    @JSON(type = ProductDetail.class)
//    @JSON(type = Product.class, include="id")
    @RequestMapping(value = "/v1/users/{id}/shops/{shopId}/orders/{status}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUSOrder(
            @ApiParam(value = "用户表id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "商铺表id", required = true) @PathVariable(value = "shopId") Long shopId,
            @ApiParam(value = "订单状态", required = true) @PathVariable(value = "status") int status,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        try {
            OrderPageView orderPageView = orderService.findByUserIdAndShopIdAndStatus(id, shopId, status, pageNumber, pageSize);
            // 封装返回信息
            TorinoSrcMessage<OrderPageView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, orderPageView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }


    @ApiOperation(value = "[Torino Source]创建拼团订单表", notes = "创建一个拼团订单表")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class)),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:order:add"})
    @RequestMapping(value = "/v1/ordersspellgroup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> spellGroupOrder(
            @ApiParam(value = "订单表", required = true) @RequestBody GroupOrderView orderView) {
        try {
            // 保存实体
            GroupOrderView orderView1;
            if (orderView.getTeamId() != 0) {
//                synchronized (obj2) {
                orderView1 = orderService.addGroupOrder(orderView);
//                }
            } else {
//                synchronized (obj1){
                orderView1 = orderService.spellGroupOrder(orderView);
//                }
            }
            // 封装返回信息
            TorinoSrcMessage<GroupOrderView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, orderView1);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.CREATED);
        } catch (TorinoSrcServiceException t) {
            String error = "Failed to create order! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(t.getErrorCode(), MessageStatus.ERROR, t.getMessage(), new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]加入并创建拼团订单表", notes = "加入并创建一个拼团订单表")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class)),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:order:add"})
    @RequestMapping(value = "/v1/ordersaddgroup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> addGroupOrder(
            @ApiParam(value = "订单表", required = true) @RequestBody GroupOrderView orderView) {
        try {
            // 保存实体
            GroupOrderView orderView1;
//            synchronized (obj2){
            orderView1 = orderService.addGroupOrder(orderView);
//            }
            // 封装返回信息
            TorinoSrcMessage<GroupOrderView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, orderView1);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.CREATED);
        } catch (TorinoSrcServiceException t) {
            String error = "Failed to create order! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(t.getErrorCode(), MessageStatus.ERROR, t.getMessage(), new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]检测单个订单", notes = "通过id检测订单")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:order:query"})
    @RequestMapping(value = "/v1/orderscheck/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> checkOrder(
            @ApiParam(value = "订单表id", required = true) @PathVariable(value = "id") Long id) {
        try {
            orderService.checkOrder(id);
            // 封装返回信息
            TorinoSrcMessage<OrderView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (TorinoSrcServiceException t) {
            String error = "Failed to get entity! " + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(t.getErrorCode(), MessageStatus.ERROR, t.getMessage(), new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]创建会员订单表", notes = "创建一个会员订单表")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class)),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:order:add"})
    @RequestMapping(value = "/v1/membershiporders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> saveMembershipOrderAndOrderDetail(
            @ApiParam(value = "订单表", required = true) @RequestBody MembershipOrderView membershipOrderView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            OrderView orderView1 = orderService.saveMembershipOrderAndOrderDetail(membershipOrderView);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/orders/{id}")
                    .buildAndExpand(orderView1.getId()).toUri());
            // 封装返回信息
            TorinoSrcMessage<OrderView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, orderView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    //免费领的下单接口
    @ApiOperation(value = "[Torino Source]创建免费领订单表", notes = "创建一个免费领订单表")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class)),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:order:add"})
    @RequestMapping(value = "/v1/productfreereceiveorders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createProductFreeReceiveOrder(
            @ApiParam(value = "订单表", required = true) @RequestBody OrderView orderView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            OrderView orderView1 = orderService.saveProductFreeReceiveOrder(orderView);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/orders/{id}")
                    .buildAndExpand(orderView1.getId()).toUri());
            // 封装返回信息
            TorinoSrcMessage<OrderView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, orderView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (TorinoSrcServiceException t) {
            String error = "Failed to create order! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(t.getErrorCode(), MessageStatus.ERROR, t.getMessage(), new TorinoSrcErrorResponseMessage(t.getMessage()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]创建助力购订单", notes = "创建一个助力购订单")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class)),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:order:add"})
    @RequestMapping(value = "/v1/orders/boost", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createBoostOrder(
            @ApiParam(value = "订单表", required = true) @RequestBody OrderView orderView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            OrderView orderView1 = orderService.saveBoostOrder(orderView);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/orders/{id}")
                    .buildAndExpand(orderView1.getId()).toUri());
            // 封装返回信息
            TorinoSrcMessage<OrderView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, orderView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]通过teamId与userId单取订单", notes = "通过teamId与userId获取订单")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:order:query"})
    @RequestMapping(value = "/v1/ordersbyteamIdanduserId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getOrderIdByTeamId(
            @ApiParam(value = "团队id", required = false) @RequestParam(value = "teamId", required = false) Long teamId,
            @ApiParam(value = "用户id", required = false) @RequestParam(value = "userId", required = false) Long userId) {
        try {
            OrderView orderView=orderService.getOrderIdByTeamIdAndUserId(teamId,userId);
            // 封装返回信息
            TorinoSrcMessage<OrderView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, orderView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        }
        catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

}
