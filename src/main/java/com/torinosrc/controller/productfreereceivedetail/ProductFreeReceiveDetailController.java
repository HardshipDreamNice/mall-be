/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.controller.productfreereceivedetail;

import com.torinosrc.commons.utils.TorinoSrcServiceExceptionUtils;
import com.torinosrc.commons.utils.message.MessageCode;
import com.torinosrc.commons.utils.message.MessageDescription;
import com.torinosrc.commons.utils.message.MessageStatus;
import com.torinosrc.commons.utils.message.MessageUtils;
import com.torinosrc.model.view.message.TorinoSrcErrorResponseMessage;
import com.torinosrc.model.view.message.TorinoSrcMessage;
import com.torinosrc.model.view.productfreereceivedetail.ProductFreeReceiveDetailView;
import com.torinosrc.service.productfreereceivedetail.ProductFreeReceiveDetailService;
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
 * <b><code>ProductFreeReceiveDetailController</code></b>
 * <p/>
 * ProductFreeReceiveDetail的具体实现的Api类，提供统一的api调用
 * <p/>
 * <b>Creation Time:</b> 2018-11-27 19:53:01.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Api(value = "[Torino Source]免费领商品详情表接口",tags = "[Torino Source]免费领商品详情表接口",description = "")
@RestController
@RequestMapping(value = "/api")
public class ProductFreeReceiveDetailController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(ProductFreeReceiveDetailController.class);

    /** The service. */
    @Autowired
    private ProductFreeReceiveDetailService productFreeReceiveDetailService;

    @ApiOperation(value = "[Torino Source]创建免费领商品详情表", notes = "创建一个免费领商品详情表")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:productfreereceivedetail:add"})
    @RequestMapping(value = "/v1/productfreereceivedetails", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createProductFreeReceiveDetail(
            @ApiParam(value = "免费领商品详情表", required = true) @RequestBody ProductFreeReceiveDetailView productFreeReceiveDetailView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            ProductFreeReceiveDetailView productFreeReceiveDetailView1 = productFreeReceiveDetailService.saveEntity(productFreeReceiveDetailView);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/productfreereceivedetails/{id}")
                    .buildAndExpand(productFreeReceiveDetailView1.getId()).toUri());
            // 封装返回信息
            TorinoSrcMessage<ProductFreeReceiveDetailView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, productFreeReceiveDetailView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除免费领商品详情表", notes = "通过id删除免费领商品详情表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:productfreereceivedetail:delete"})
    @RequestMapping(value = "/v1/productfreereceivedetails/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteProductFreeReceiveDetail(
            @ApiParam(value = "免费领商品详情表id", required = true) @PathVariable(value = "id") Long id) {
        try {
            productFreeReceiveDetailService.deleteEntity(id);
            // 封装返回信息
            TorinoSrcMessage<ProductFreeReceiveDetailView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除免费领商品详情表", notes = "批量删除免费领商品详情表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:productfreereceivedetail:delete"})
    @RequestMapping(value = "/v1/productfreereceivedetails", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteProductFreeReceiveDetails(
            @ApiParam(value = "免费领商品详情表ids，样例 - 1,2,3", required = true) @RequestParam(value = "condition", required = true) String condition) {
        try {
            productFreeReceiveDetailService.deleteEntities(condition);
            // 封装返回信息
            TorinoSrcMessage<ProductFreeReceiveDetailView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]更新免费领商品详情表", notes = "更新免费领商品详情表信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequiresPermissions(value = {"sys:productfreereceivedetail:update"})
    @RequestMapping(value = "/v1/productfreereceivedetails/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateProductFreeReceiveDetails(
            @ApiParam(value = "免费领商品详情表id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "免费领商品详情表信息", required = true) @RequestBody ProductFreeReceiveDetailView productFreeReceiveDetailView) {
        try {
            productFreeReceiveDetailView.setId(id);
            productFreeReceiveDetailService.updateEntity(productFreeReceiveDetailView);
            // 封装返回信息
            TorinoSrcMessage<ProductFreeReceiveDetailView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, productFreeReceiveDetailView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取单个免费领商品详情表", notes = "通过id获取免费领商品详情表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:productfreereceivedetail:query"})
    @RequestMapping(value = "/v1/productfreereceivedetails/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getProductFreeReceiveDetail(
            @ApiParam(value = "免费领商品详情表id", required = true) @PathVariable(value = "id") Long id) {
        try {
            final ProductFreeReceiveDetailView productFreeReceiveDetailView = productFreeReceiveDetailService.getEntity(id);
            // 封装返回信息
            TorinoSrcMessage<ProductFreeReceiveDetailView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, productFreeReceiveDetailView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取免费领商品详情表列表", notes = "通过查询条件获取免费领商品详情表列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:productfreereceivedetail:query"})
    @RequestMapping(value = "/v1/productfreereceivedetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getProductFreeReceiveDetails(
            @ApiParam(value = "查询条件", defaultValue = "", required = false) @RequestParam(value = "condition", defaultValue = "", required = false) String condition,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        try {
            // 解析Base64的查询条件
            if(!StringUtils.isEmpty(condition)){
                condition = new String(Base64Utils.decodeFromString(condition));
            }
            ProductFreeReceiveDetailView productFreeReceiveDetailView = new ProductFreeReceiveDetailView();
            productFreeReceiveDetailView.setCondition(condition);

            Page<ProductFreeReceiveDetailView> productFreeReceiveDetailViews = productFreeReceiveDetailService
                    .getEntitiesByParms(productFreeReceiveDetailView, pageNumber, pageSize);
            // 封装返回信息
            TorinoSrcMessage<Page<ProductFreeReceiveDetailView>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, productFreeReceiveDetailViews);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

}
