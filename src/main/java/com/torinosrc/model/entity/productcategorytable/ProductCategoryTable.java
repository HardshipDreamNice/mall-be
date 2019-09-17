/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.productcategorytable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>ProductCategoryTable</code></b>
 * <p/>
 * ProductCategoryTable的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-08-30 10:51:55.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="v_product_category_table")
@DynamicInsert
@DynamicUpdate
public class ProductCategoryTable extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public ProductCategoryTable(){
        super();
    }

    @Column(name = "max_price")
    private Long maxPrice;

    @Column(name = "min_price")
    private Long minPrice;

    @Column(name = "max_commission")
    private Long maxCommission;

    @Column(name = "min_commission")
    private Long minCommission;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "name")
    private String name;

    @Column(name="index_product_type_id")
    private String indexProductTypeId;

    @Column(name="type")
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIndexProductTypeId() {
        return indexProductTypeId;
    }

    public void setIndexProductTypeId(String indexProductTypeId) {
        this.indexProductTypeId = indexProductTypeId;
    }

    public Long getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Long maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Long minPrice) {
        this.minPrice = minPrice;
    }

    public Long getMaxCommission() {
        return maxCommission;
    }

    public void setMaxCommission(Long maxCommission) {
        this.maxCommission = maxCommission;
    }

    public Long getMinCommission() {
        return minCommission;
    }

    public void setMinCommission(Long minCommission) {
        this.minCommission = minCommission;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
