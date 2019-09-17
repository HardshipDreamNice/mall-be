/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.indexproducttypeproduct;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.product.Product;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>IndexProductTypeProduct</code></b>
 * <p/>
 * IndexProductTypeProduct的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-27 11:04:15.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name = "t_index_product_type_product")
@DynamicInsert
@DynamicUpdate
public class IndexProductTypeProduct extends BaseEntity implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    public IndexProductTypeProduct() {
        super();
    }

    @Column(name = "index_product_type_id")
    private Long indexProductTypeId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "weight")
    private Integer weight;


    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    public Long getIndexProductTypeId() {
        return indexProductTypeId;
    }

    public void setIndexProductTypeId(Long indexProductTypeId) {
        this.indexProductTypeId = indexProductTypeId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
