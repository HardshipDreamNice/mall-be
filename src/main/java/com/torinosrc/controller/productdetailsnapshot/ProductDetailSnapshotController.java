/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.controller.productdetailsnapshot;

import com.torinosrc.commons.utils.TorinoSrcServiceExceptionUtils;
import com.torinosrc.commons.utils.message.MessageCode;
import com.torinosrc.commons.utils.message.MessageDescription;
import com.torinosrc.commons.utils.message.MessageStatus;
import com.torinosrc.commons.utils.message.MessageUtils;
import com.torinosrc.model.view.message.TorinoSrcErrorResponseMessage;
import com.torinosrc.model.view.message.TorinoSrcMessage;
import com.torinosrc.model.view.productdetailsnapshot.ProductDetailSnapshotView;
import com.torinosrc.service.productdetailsnapshot.ProductDetailSnapshotService;
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
 * <b><code>ProductDetailSnapshotController</code></b>
 * <p/>
 * ProductDetailSnapshot的具体实现的Api类，提供统一的api调用
 * <p/>
 * <b>Creation Time:</b> 2018-07-16 18:35:34.
 *
 * @author ${model.author}
 * @version 2.0.0
 * @since torinosrc-spring-boot-be 2.0.0
 */
@Api(value = "[Torino Source]商品详情快照表接口",tags = "[Torino Source]商品详情快照表接口",description = "")
@RestController
@RequestMapping(value = "/api")
public class ProductDetailSnapshotController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(ProductDetailSnapshotController.class);

    /** The service. */
    @Autowired
    private ProductDetailSnapshotService productDetailSnapshotService;

    @ApiOperation(value = "[Torino Source]创建商品详情快照表", notes = "创建一个商品详情快照表")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:productdetailsnapshot:add"})
    @RequestMapping(value = "/v1/productdetailsnapshots", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createProductDetailSnapshot(
            @ApiParam(value = "商品详情快照表", required = true) @RequestBody ProductDetailSnapshotView productDetailSnapshotView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            ProductDetailSnapshotView productDetailSnapshotView1 = productDetailSnapshotService.saveEntity(productDetailSnapshotView);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/productdetailsnapshots/{id}")
                    .buildAndExpand(productDetailSnapshotView1.getId()).toUri());
            // 封装返回信息
            TorinoSrcMessage<ProductDetailSnapshotView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, productDetailSnapshotView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除商品详情快照表", notes = "通过id删除商品详情快照表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:productdetailsnapshot:delete"})
    @RequestMapping(value = "/v1/productdetailsnapshots/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteProductDetailSnapshot(
            @ApiParam(value = "商品详情快照表id", required = true) @PathVariable(value = "id") Long id) {
        try {
            productDetailSnapshotService.deleteEntity(id);
            // 封装返回信息
            TorinoSrcMessage<ProductDetailSnapshotView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除商品详情快照表", notes = "批量删除商品详情快照表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:productdetailsnapshot:delete"})
    @RequestMapping(value = "/v1/productdetailsnapshots", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteProductDetailSnapshots(
            @ApiParam(value = "商品详情快照表ids，样例 - 1,2,3", required = true) @RequestBody String ids) {
        try {
            productDetailSnapshotService.deleteEntities(ids);
            // 封装返回信息
            TorinoSrcMessage<ProductDetailSnapshotView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]更新商品详情快照表", notes = "更新商品详情快照表信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequiresPermissions(value = {"sys:productdetailsnapshot:update"})
    @RequestMapping(value = "/v1/productdetailsnapshots/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateProductDetailSnapshots(
            @ApiParam(value = "商品详情快照表id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "商品详情快照表信息", required = true) @RequestBody ProductDetailSnapshotView productDetailSnapshotView) {
        try {
            productDetailSnapshotView.setId(id);
            productDetailSnapshotService.updateEntity(productDetailSnapshotView);
            // 封装返回信息
            TorinoSrcMessage<ProductDetailSnapshotView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, productDetailSnapshotView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取单个商品详情快照表", notes = "通过id获取商品详情快照表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:productdetailsnapshot:query"})
    @RequestMapping(value = "/v1/productdetailsnapshots/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getProductDetailSnapshot(
            @ApiParam(value = "商品详情快照表id", required = true) @PathVariable(value = "id") Long id) {
        try {
            final ProductDetailSnapshotView productDetailSnapshotView = productDetailSnapshotService.getEntity(id);
            // 封装返回信息
            TorinoSrcMessage<ProductDetailSnapshotView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, productDetailSnapshotView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取商品详情快照表列表", notes = "通过查询条件获取商品详情快照表列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequiresPermissions(value = {"sys:productdetailsnapshot:query"})
    @RequestMapping(value = "/v1/productdetailsnapshots", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getProductDetailSnapshots(
            @ApiParam(value = "查询条件", defaultValue = "", required = false) @RequestParam(value = "condition", defaultValue = "", required = false) String condition,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        try {
            // 解析Base64的查询条件
            if(!StringUtils.isEmpty(condition)){
                condition = new String(Base64Utils.decodeFromString(condition));
            }
            ProductDetailSnapshotView productDetailSnapshotView = new ProductDetailSnapshotView();
            productDetailSnapshotView.setCondition(condition);

            Page<ProductDetailSnapshotView> productDetailSnapshotViews = productDetailSnapshotService
                    .getEntitiesByParms(productDetailSnapshotView, pageNumber, pageSize);
            // 封装返回信息
            TorinoSrcMessage<Page<ProductDetailSnapshotView>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, productDetailSnapshotViews);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

}
