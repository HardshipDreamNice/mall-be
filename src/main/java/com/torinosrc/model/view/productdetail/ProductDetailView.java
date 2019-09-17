/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.productdetail;

import com.torinosrc.model.entity.productdetail.ProductDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <b><code>ProductDetailView</code></b>
 * <p/>
 * ProductDetail的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-06 11:28:08.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class ProductDetailView extends ProductDetail implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    // 利润
    private Long profit;

    @ApiModelProperty(value = "搜索内容")
    private String search;

    @ApiModelProperty(value = "按照什么方式排序 0是更新时间  1是价格  2是利润")
    private Integer sortType;

    @ApiModelProperty(value = "商品类别Id")
    private Long categoryId;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Long getProfit() {
        return profit;
    }

    public void setProfit(Long profit) {
        this.profit = profit;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
