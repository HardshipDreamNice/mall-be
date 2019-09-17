/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.productdetailsnapshot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.entity.productsnapshot.ProductSnapshot;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>ProductDetailSnapshot</code></b>
 * <p/>
 * ProductDetailSnapshot的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-16 18:35:34.
 *
 * @author ${model.author}
 * @version 2.0.0
 * @since torinosrc-spring-boot-be 2.0.0
 */
@Entity
@Table(name = "t_product_detail_snapshot")
@DynamicInsert
@DynamicUpdate
public class ProductDetailSnapshot extends BaseEntity implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    public ProductDetailSnapshot() {
        super();
    }

    @Column(name = "size")
    private String size;

    @Column(name = "price")
    private Long price;

    @Column(name = "color")
    private String color;

    @Column(name = "inventory")
    private Long inventory;

    @Column(name = "commission")
    private Long commission;

    @Column(name = "product_detail_id")
    private Long productDetailId;

    @Column(name = "product_snapshot_id")
    private Long productSnapshotId;

    @Column(name = "uppest_price")
    private String uppestPrice;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_snapshot_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductSnapshot productSnapshot;

    @Column(name = "product_free_receive_detail_id")
    private Long productFreeReceiveDetailId;

    @Column(name="team_price")
    private Long teamPrice;

    public Long getTeamPrice() {
        return teamPrice;
    }

    public void setTeamPrice(Long teamPrice) {
        this.teamPrice = teamPrice;
    }

    public Long getProductSnapshotId() {
        return productSnapshotId;
    }

    public Long getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(Long productDetailId) {
        this.productDetailId = productDetailId;
    }

    public void setProductSnapshotId(Long productSnapshotId) {
        this.productSnapshotId = productSnapshotId;
    }

    public Long getCommission() {
        return commission;
    }

    public void setCommission(Long commission) {
        this.commission = commission;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ProductSnapshot getProductSnapshot() {
        return productSnapshot;
    }

    public void setProductSnapshot(ProductSnapshot productSnapshot) {
        this.productSnapshot = productSnapshot;
    }

    public Long getInventory() {
        return inventory;
    }

    public void setInventory(Long inventory) {
        this.inventory = inventory;
    }

    public Long getProductFreeReceiveDetailId() {
        return productFreeReceiveDetailId;
    }

    public void setProductFreeReceiveDetailId(Long productFreeReceiveDetailId) {
        this.productFreeReceiveDetailId = productFreeReceiveDetailId;
    }

    public String getUppestPrice() {
        return uppestPrice;
    }

    public void setUppestPrice(String uppestPrice) {
        this.uppestPrice = uppestPrice;
    }

    @Override
    public String toString() {
        return "ProductDetailSnapshot{" +
                "size='" + size + '\'' +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", inventory=" + inventory +
                ", commission=" + commission +
                ", productDetailId=" + productDetailId +
                ", productSnapshotId=" + productSnapshotId +
                ", productSnapshot=" + productSnapshot +
                '}';
    }
}
