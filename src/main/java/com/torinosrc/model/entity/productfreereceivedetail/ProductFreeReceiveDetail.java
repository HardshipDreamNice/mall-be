/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.productfreereceivedetail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.entity.productdetailprice.ProductDetailPrice;
import com.torinosrc.model.entity.productfreereceive.ProductFreeReceive;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * <b><code>ProductFreeReceiveDetail</code></b>
 * <p/>
 * ProductFreeReceiveDetail的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-27 19:53:01.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_product_free_receive_detail")
@DynamicInsert
@DynamicUpdate
public class ProductFreeReceiveDetail extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public ProductFreeReceiveDetail(){
        super();
    }

    @Column(name = "size")
    private String size;

    @Column(name = "price")
    private Long price;

    @Column(name = "uppest_price")
    private Long uppestPrice;

    @Column(name = "color")
    private String color;

    @Column(name = "inventory")
    private Long inventory;

    @Column(name = "commission")
    private Long commission;

    @Column(name = "product_free_receive_id")
    private Long productFreeReceiveId;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "product_free_receive_id",referencedColumnName = "id",insertable = false,updatable = false)
    private ProductFreeReceive productFreeReceive;

    public Long getProductFreeReceiveId() {
        return productFreeReceiveId;
    }

    public void setProductFreeReceiveId(Long productFreeReceiveId) {
        this.productFreeReceiveId = productFreeReceiveId;
    }

    public ProductFreeReceive getProductFreeReceive() {
        return productFreeReceive;
    }

    public void setProductFreeReceive(ProductFreeReceive productFreeReceive) {
        this.productFreeReceive = productFreeReceive;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getUppestPrice() {
        return uppestPrice;
    }

    public void setUppestPrice(Long uppestPrice) {
        this.uppestPrice = uppestPrice;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getInventory() {
        return inventory;
    }

    public void setInventory(Long inventory) {
        this.inventory = inventory;
    }

    public Long getCommission() {
        return commission;
    }

    public void setCommission(Long commission) {
        this.commission = commission;
    }

}
