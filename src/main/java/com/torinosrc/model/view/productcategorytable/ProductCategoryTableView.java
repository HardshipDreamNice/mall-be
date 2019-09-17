/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.productcategorytable;

import com.torinosrc.model.entity.productcategorytable.ProductCategoryTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <b><code>ProductCategoryTableView</code></b>
 * <p/>
 * ProductCategoryTable的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-08-30 10:51:55.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class ProductCategoryTableView extends ProductCategoryTable implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "搜索内容")
    private String search;

    @ApiModelProperty(value = "按照什么方式排序 0是更新时间  1是价格  2是利润")
    private Integer sortType;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }
}
