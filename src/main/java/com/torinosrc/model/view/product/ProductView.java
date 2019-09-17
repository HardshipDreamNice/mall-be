/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.product;

import com.torinosrc.model.entity.indexproducttypeproduct.IndexProductTypeProduct;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.view.indexproducttypeproduct.IndexProductTypeProductView;
import com.torinosrc.model.view.productdetail.ProductDetailView;
import com.torinosrc.model.view.productdetailprice.ProductDetailPriceView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.omg.CORBA.INTERNAL;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>ProductView</code></b>
 * <p/>
 * Product的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-06 11:17:58.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class ProductView extends Product implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "最高价")
    private Integer priceIntervalUp;

    @ApiModelProperty(value = "市场最高价")
    private Long marketPriceIntervalUp;

    @ApiModelProperty(value = "最低价")
    private Integer priceIntervaDown;

    @ApiModelProperty(value = "最低拼团价")
    private Integer groupPriceIntervaDown;

    private List<ProductDetail> productDetails;

    @ApiModelProperty(value = "最高利润")
    private Long profitIntervalUp;

    @ApiModelProperty(value = "最低利润")
    private Long profitIntervalDown;

    @ApiModelProperty(value = "搜索内容")
    private String searchName;

    @ApiModelProperty(value = "排序方式 0是按更新时间  1是价格 2是利润")
    private Integer sortType;

    @ApiModelProperty(value = "是否已推荐")
    private Integer onSale;

    private Long shopId;

    private Long shopProductId;

    private List<ProductDetailView> productDetailViews;

    @ApiModelProperty(value = "图片集")
    private List<String> imageUrls;

    private ProductDetailPriceView productDetailPriceView;

    @ApiModelProperty(value = "拼团时间是否结束状态")
    private Integer spellStatus;

    @ApiModelProperty(value = "搜索价格")
    private String searchUpPrice;

    @ApiModelProperty(value = "搜索价格")
    private String searchDownPrice;

    @ApiModelProperty(value = "商品类型")
    private Integer productType;

    private List<IndexProductTypeProductView> indexProductTypeProductView;

    public List<IndexProductTypeProductView> getIndexProductTypeProductView() {
        return indexProductTypeProductView;
    }

    public void setIndexProductTypeProductView(List<IndexProductTypeProductView> indexProductTypeProductView) {
        this.indexProductTypeProductView = indexProductTypeProductView;
    }

    public String getSearchUpPrice() {
        return searchUpPrice;
    }

    public void setSearchUpPrice(String searchUpPrice) {
        this.searchUpPrice = searchUpPrice;
    }

    public String getSearchDownPrice() {
        return searchDownPrice;
    }

    public void setSearchDownPrice(String searchDownPrice) {
        this.searchDownPrice = searchDownPrice;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getSpellStatus() {
        return spellStatus;
    }

    public void setSpellStatus(Integer spellStatus) {
        this.spellStatus = spellStatus;
    }

    public Integer getGroupPriceIntervaDown() {
        return groupPriceIntervaDown;
    }

    public void setGroupPriceIntervaDown(Integer groupPriceIntervaDown) {
        this.groupPriceIntervaDown = groupPriceIntervaDown;
    }

    public ProductDetailPriceView getProductDetailPriceView() {
        return productDetailPriceView;
    }

    public void setProductDetailPriceView(ProductDetailPriceView productDetailPriceView) {
        this.productDetailPriceView = productDetailPriceView;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<ProductDetail> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDetail> productDetails) {
        this.productDetails = productDetails;
    }

    public List<ProductDetailView> getProductDetailViews() {
        return productDetailViews;
    }

    public void setProductDetailViews(List<ProductDetailView> productDetailViews) {
        this.productDetailViews = productDetailViews;
    }

    public Integer getPriceIntervalUp() {
        return priceIntervalUp;
    }

    public void setPriceIntervalUp(Integer priceIntervalUp) {
        this.priceIntervalUp = priceIntervalUp;
    }

    public Integer getPriceIntervaDown() {
        return priceIntervaDown;
    }

    public void setPriceIntervaDown(Integer priceIntervaDown) {
        this.priceIntervaDown = priceIntervaDown;
    }

    public Long getProfitIntervalUp() {
        return profitIntervalUp;
    }

    public void setProfitIntervalUp(Long profitIntervalUp) {
        this.profitIntervalUp = profitIntervalUp;
    }

    public Long getProfitIntervalDown() {
        return profitIntervalDown;
    }

    public void setProfitIntervalDown(Long profitIntervalDown) {
        this.profitIntervalDown = profitIntervalDown;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public Long getShopProductId() {
        return shopProductId;
    }

    public void setShopProductId(Long shopProductId) {
        this.shopProductId = shopProductId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getOnSale() {
        return onSale;
    }

    public void setOnSale(Integer onSale) {
        this.onSale = onSale;
    }

    public Long getMarketPriceIntervalUp() {
        return marketPriceIntervalUp;
    }

    public void setMarketPriceIntervalUp(Long marketPriceIntervalUp) {
        this.marketPriceIntervalUp = marketPriceIntervalUp;
    }
}
