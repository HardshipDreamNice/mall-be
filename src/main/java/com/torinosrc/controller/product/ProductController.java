/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.controller.product;

import com.torinosrc.commons.utils.TorinoSrcServiceExceptionUtils;
import com.torinosrc.commons.utils.message.MessageCode;
import com.torinosrc.commons.utils.message.MessageDescription;
import com.torinosrc.commons.utils.message.MessageStatus;
import com.torinosrc.commons.utils.message.MessageUtils;
import com.torinosrc.model.entity.category.Category;
import com.torinosrc.model.view.common.CustomPage;
import com.torinosrc.model.view.message.TorinoSrcErrorResponseMessage;
import com.torinosrc.model.view.message.TorinoSrcMessage;
import com.torinosrc.model.view.product.*;
import com.torinosrc.response.json.JSON;
import com.torinosrc.service.product.ProductService;
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

/**
 * <b><code>ProductController</code></b>
 * <p/>
 * Product的具体实现的Api类，提供统一的api调用
 * <p/>
 * <b>Creation Time:</b> 2018-06-06 11:17:58.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Api(value = "[Torino Source]商品接口", tags = "[Torino Source]商品接口", description = "")
@RestController
@RequestMapping(value = "/api")
public class ProductController {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ProductController.class);

    /**
     * The service.
     */
    @Autowired
    private ProductService productService;

    @ApiOperation(value = "[Torino Source]创建商品", notes = "创建一个商品")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class)),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:product:add"})
    @RequestMapping(value = "/v1/products", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createProduct(
            @ApiParam(value = "商品", required = true) @RequestBody ProductView productView,
            UriComponentsBuilder ucBuilder) {
        try {
            if (!ObjectUtils.isEmpty(productView.getImageUrls())) {
                Integer size = (productView.getImageUrls().size());
                if (size == 1) {
                    productView.setImageUrl(productView.getImageUrls().get(0));
                    productView.setImageUrl1("");
                    productView.setImageUrl2("");
                } else if (size == 2) {
                    productView.setImageUrl(productView.getImageUrls().get(0));
                    productView.setImageUrl1(productView.getImageUrls().get(1));
                    productView.setImageUrl2("");
                } else if (size == 3) {
                    productView.setImageUrl(productView.getImageUrls().get(0));
                    productView.setImageUrl1(productView.getImageUrls().get(1));
                    productView.setImageUrl2(productView.getImageUrls().get(2));
                }
            }
            // 保存实体
            ProductView productView1 = productService.saveProductProductDetail(productView);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/products/{id}")
                    .buildAndExpand(productView1.getId()).toUri());
            // 封装返回信息
            TorinoSrcMessage<ProductView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, productView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除商品", notes = "通过id删除商品")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:product:delete"})
    @RequestMapping(value = "/v1/products/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteProduct(
            @ApiParam(value = "商品id", required = true) @PathVariable(value = "id") Long id) {
        try {
            productService.deleteEntity(id);
            // 封装返回信息
            TorinoSrcMessage<ProductView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除商品", notes = "批量删除商品")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:product:delete"})
    @RequestMapping(value = "/v1/products", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteProducts(
            @ApiParam(value = "商品ids，样例 - 1,2,3", required = true) @RequestParam(value = "condition", required = true) String condition) {
        try {
            productService.deleteEntities(condition);
            // 封装返回信息
            TorinoSrcMessage<ProductView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }


    @ApiOperation(value = "[Torino Source]更新商品", notes = "更新商品信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error")})
    @RequiresPermissions(value = {"sys:product:update"})
    @RequestMapping(value = "/v1/products/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateProducts(
            @ApiParam(value = "商品id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "商品信息", required = true) @RequestBody ProductView productView) {
        try {
            productView.setId(id);
            productService.updateEntity(productView);

            // 封装返回信息
            TorinoSrcMessage<ProductView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, productView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]更新商品(管理端商品信息更新接口)", notes = "更新商品信息树(管理端商品信息更新接口)")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error")})
    @RequiresPermissions(value = {"sys:product:update"})
    @RequestMapping(value = "/v1/productsmore/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateEntityByProductView(
            @ApiParam(value = "商品id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "商品信息", required = true) @RequestBody ProductView productView) {
        try {
            productView.setId(id);
            if (!ObjectUtils.isEmpty(productView.getImageUrls())) {
                Integer size = (productView.getImageUrls().size());
                if (size == 1) {
                    productView.setImageUrl(productView.getImageUrls().get(0));
                    productView.setImageUrl1("");
                    productView.setImageUrl2("");
                } else if (size == 2) {
                    productView.setImageUrl(productView.getImageUrls().get(0));
                    productView.setImageUrl1(productView.getImageUrls().get(1));
                    productView.setImageUrl2("");
                } else if (size == 3) {
                    productView.setImageUrl(productView.getImageUrls().get(0));
                    productView.setImageUrl1(productView.getImageUrls().get(1));
                    productView.setImageUrl2(productView.getImageUrls().get(2));
                }
            }
            productService.updateEntityByProductView(productView);
            // 封装返回信息
            TorinoSrcMessage<ProductView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, productView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取单个商品", notes = "通过id获取商品")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
//    @RequiresPermissions(value = {"sys:product:query"})
    @RequestMapping(value = "/v1/products/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JSON(type = ProductView.class)
    public ResponseEntity<?> getProduct(
            @ApiParam(value = "返回字段(base64编码，include表示包含字，段filter表示排除字段)，比如：{\"include\":\"id,name\"}或者{\"filter\":\"id,name\"}", defaultValue = "", required = false) @RequestParam(value = "field", defaultValue = "", required = false) String field,
            @ApiParam(value = "商品id", required = true) @PathVariable(value = "id") Long id) {
        try {
            final ProductView productView = productService.getEntity(id);
            // 封装返回信息
            TorinoSrcMessage<ProductView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, productView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取商品列表", notes = "通过查询条件获取商品列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
//    @RequiresPermissions(value = {"sys:product:query"})
    @JSON(type = ProductView.class)
    @RequestMapping(value = "/v1/products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getProducts(
            @ApiParam(value = "返回字段(base64编码，include表示包含字段，filter表示排除字段)，比如：{\"include\":\"id,name\"}或者{\"filter\":\"id,name\"}", defaultValue = "", required = false) @RequestParam(value = "include", defaultValue = "", required = false) String field,
            @ApiParam(value = "查询条件", defaultValue = "", required = false) @RequestParam(value = "condition", defaultValue = "", required = false) String condition,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        try {
            // 解析Base64的查询条件
            if (!StringUtils.isEmpty(condition)) {
                condition = new String(Base64Utils.decodeFromString(condition));
            }
            ProductView productView = new ProductView();
            productView.setCondition(condition);

            Page<ProductView> productViews = productService
                    .getEntitiesByParms(productView, pageNumber, pageSize);
            // 封装返回信息
            TorinoSrcMessage<Page<ProductView>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, productViews);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }


    @ApiOperation(value = "[Torino Source]获取好物品推荐商品列表", notes = "通过查询条件获取商品列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
//    @RequiresPermissions(value = {"sys:product:query"})
    @RequestMapping(value = "/v1/wxproducts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> findProductListForWx(
            @ApiParam(value = "商店Id", defaultValue = "", required = false) @RequestParam(value = "shopId", defaultValue = "", required = true) Long shopId,
            @ApiParam(value = "商品类别 0：普通 1：拼团 99:全部", defaultValue = "", required = true) @RequestParam(value = "productType", defaultValue = "0", required = true) Integer productType,
            @ApiParam(value = "查询条件", defaultValue = "", required = false) @RequestParam(value = "condition", defaultValue = "", required = false) String condition,
            @ApiParam(value = "是否启用", defaultValue = "", required = false) @RequestParam(value = "enabled", defaultValue = "", required = false) Integer enabled,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @ApiParam(value = "排序方式 0：creatTime 1:weight", defaultValue = "", required = false) @RequestParam(value = "sortType", defaultValue = "1", required = false) Integer sortType,
            @ApiParam(value = "商品名称", defaultValue = "", required = false) @RequestParam(value = "searchName", defaultValue = "", required = false) String searchName) {
        try {
            // 解析Base64的查询条件
            if(!StringUtils.isEmpty(condition)){
                condition = new String(Base64Utils.decodeFromString(condition));
            }
            ProductView productView = new ProductView();
            productView.setCondition(condition);
            productView.setShopId(shopId);
            productView.setType(productType);
            productView.setEnabled(enabled);
            productView.setSortType(sortType);
            productView.setName(searchName);

            CustomPage<ProductView> productViews = productService
                    .findProductListForWx(productView, pageNumber, pageSize);
            // 封装返回信息
            TorinoSrcMessage<CustomPage<ProductView>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, productViews);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }


    @ApiOperation(value = "[Torino Source]获取单个商品(含利润)", notes = "通过id获取商品(含利润)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
//    @RequiresPermissions(value = {"sys:product:query"})
    @RequestMapping(value = "/v1/products/{id}/shops/{shopId}/profit", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getProductProfit(
            @ApiParam(value = "商品id", required = true) @PathVariable(value = "id") Long id,
            @ApiParam(value = "商店id", required = true) @PathVariable(value = "shopId") Long shopId) {
        try {
            final ProductView productView = productService.getProductProfit(id, shopId);
            // 封装返回信息
            TorinoSrcMessage<ProductView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, productView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取分类商品", notes = "通过id获取商品")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
//    @RequiresPermissions(value = {"sys:product:query"})
    @JSON(type = ProductView.class)
    @RequestMapping(value = "/v1/products/category/{categoryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getProductsByCategory(
            @ApiParam(value = "返回字段(base64编码，include表示包含字段，filter表示排除字段)，比如：{\"include\":\"id,name\"}或者{\"filter\":\"id,name\"}", defaultValue = "", required = false) @RequestParam(value = "include", defaultValue = "", required = false) String field,
            @ApiParam(value = "分类id", required = true) @PathVariable(value = "categoryId", required = true) Long categoryId,
            @ApiParam(value = "商店id", required = true) @RequestParam(value = "shopId", required = true) Long shopId,
            @ApiParam(value = "搜索内容", required = false) @RequestParam(value = "searchName", required = false) String searchName,
            @ApiParam(value = "排序类型", required = false) @RequestParam(value = "sortType", required = false) Integer sortType,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize
    ) {
        try {
            ProductView productView = new ProductView();
            Category category = new Category();
            category.setId(categoryId);
            productView.setCategory(category);
            productView.setShopId(shopId);
            productView.setSearchName(searchName);
            productView.setSortType(sortType);
            final ProductPageView productPageView = productService.getProductsByCategoryId(productView, pageNumber, pageSize);
            // 封装返回信息
            TorinoSrcMessage<ProductPageView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, productPageView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取首页商品", notes = "获取首页商品")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
//    @RequiresPermissions(value = {"sys:product:query"})
    @RequestMapping(value = "/v1/products/index", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getIndexProducts() {
        try {
            IndexProductView indexProductView = productService.getIndexProductView();
            // 封装返回信息
            TorinoSrcMessage<IndexProductView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, indexProductView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取商品信息", notes = "通过id获取商品列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
//    @RequiresPermissions(value = {"sys:product:query"})
    @JSON(type = ProductView.class)
    @RequestMapping(value = "/v1/products/copy/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getCopyProduct(
            @ApiParam(value = "返回字段(base64编码，include表示包含字段，filter表示排除字段)，比如：{\"include\":\"id,name\"}或者{\"filter\":\"id,name\"}", defaultValue = "", required = false) @RequestParam(value = "include", defaultValue = "", required = false) String field,
            @ApiParam(value = "商品id", required = true) @PathVariable(value = "id") Long id) {
        try {
            ProductView productView = productService.getCopyEntity(id);

            // 封装返回信息
            TorinoSrcMessage<ProductView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, productView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取单个商品，可同时适用上架商品和普通商品", notes = "获取单个商品，可同时适用上架商品和普通商品")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
//    @RequiresPermissions(value = {"sys:product:query"})
    @RequestMapping(value = "/v1/products/{productId}/shops/{shopId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getIntegratedProduct(
            @ApiParam(value = "商品id", required = true) @PathVariable(value = "productId") Long productId,
            @ApiParam(value = "商店id", required = true) @PathVariable(value = "shopId") Long shopId) {
        try {
            final IntegratedProductView integratedProductView = productService.getProductByProductIdAndShopId(productId, shopId);
            // 封装返回信息
            TorinoSrcMessage<IntegratedProductView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, integratedProductView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取商品(后台管理端商品获取接口)", notes = "通过查询条件获取商品")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
//    @RequiresPermissions(value = {"sys:product:query"})
    @JSON(type = ProductView.class)
    @RequestMapping(value = "/v1/products/parmssearch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getEntitiesByParmsSearch(
            @ApiParam(value = "返回字段(base64编码，include表示包含字段，filter表示排除字段)，比如：{\"include\":\"id,name\"}或者{\"filter\":\"id,name\"}", defaultValue = "", required = false) @RequestParam(value = "include", defaultValue = "", required = false) String field,
            @ApiParam(value = "查询条件", defaultValue = "", required = false) @RequestParam(value = "condition", defaultValue = "", required = false) String condition,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize
    ){
        try {
            // 解析Base64的查询条件
            if(!StringUtils.isEmpty(condition)){
                condition = new String(Base64Utils.decodeFromString(condition));
            }
            ProductView productView =  new ProductView();
            productView.setCondition(condition);
            ProductPageView productViews = productService
                    .getEntitiesByParmsSearch(productView, pageNumber, pageSize);
            // 封装返回信息
            TorinoSrcMessage<ProductPageView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, productViews);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }


    @ApiOperation(value = "[Torino Source]保存助力购商品", notes = "保存助力购商品")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class)),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:product:add"})
    @RequestMapping(value = "/v1/products/boostproducts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> saveBoostProduct(
            @ApiParam(value = "商品", required = true) @RequestBody ProductReviceView productReviceView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            productService.saveBoostProduct(productReviceView);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
//            headers.setLocation(ucBuilder.path("/api/v1/products/{id}")
//                    .buildAndExpand(productReviceView.getId()).toUri());
            // 封装返回信息
            TorinoSrcMessage<ProductReviceView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, productReviceView);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }


    @ApiOperation(value = "[Torino Source]删除助力购商品", notes = "通过id删除助力购商品")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:product:delete"})
    @RequestMapping(value = "/v1/products/boostproducts/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteBoostProduct(
            @ApiParam(value = "商品id", required = true) @PathVariable(value = "id") Long id) {
        try {
            productService.deleteBoostProduct(id);
            // 封装返回信息
            TorinoSrcMessage<ProductView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除商品", notes = "批量删除商品")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions(value = {"sys:product:delete"})
    @RequestMapping(value = "/v1/products/boostproducts", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteBoostProductEntities(
            @ApiParam(value = "商品ids，样例 - 1,2,3", required = true) @RequestParam(value = "condition", required = true) String condition) {
        try {
            productService.deleteBoostProductEntities(condition);
            // 封装返回信息
            TorinoSrcMessage<ProductView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            TorinoSrcMessage<TorinoSrcErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new TorinoSrcErrorResponseMessage(t.toString()));
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseTorinoSrcMessage(torinoSrcMessage, t);
        }
    }

}
