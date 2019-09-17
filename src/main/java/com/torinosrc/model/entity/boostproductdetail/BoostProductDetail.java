/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.boostproductdetail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.boostproduct.BoostProduct;
import com.torinosrc.model.entity.boostproductdetailprice.BoostProductDetailPrice;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * <b><code>BoostProductDetail</code></b>
 * <p/>
 * BoostProductDetail的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-30 16:10:27.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name = "t_boost_product_detail")
@DynamicInsert
@DynamicUpdate
public class BoostProductDetail extends BaseEntity implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    public BoostProductDetail() {
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

    @Column(name = "boost_product_id")
    private Long boostProductId;

    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boost_product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BoostProduct boostProduct;

    @OneToMany
    @JoinColumn(name = "boost_product_detail_id")
    private List<BoostProductDetailPrice> boostProductDetailPrices;

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

    public Long getBoostProductId() {
        return boostProductId;
    }

    public void setBoostProductId(Long boostProductId) {
        this.boostProductId = boostProductId;
    }

    public BoostProduct getBoostProduct() {
        return boostProduct;
    }

    public void setBoostProduct(BoostProduct boostProduct) {
        this.boostProduct = boostProduct;
    }

    public List<BoostProductDetailPrice> getBoostProductDetailPrices() {
        return boostProductDetailPrices;
    }

    public void setBoostProductDetailPrices(List<BoostProductDetailPrice> boostProductDetailPrices) {
        this.boostProductDetailPrices = boostProductDetailPrices;
    }
}
