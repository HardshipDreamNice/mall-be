/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.shopproductdetailsnapshot;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.entity.productdetailsnapshot.ProductDetailSnapshot;
import com.torinosrc.model.entity.shopproduct.ShopProduct;
import com.torinosrc.model.entity.shopproductdetail.ShopProductDetail;
import com.torinosrc.model.entity.shopproductsnapshot.ShopProductSnapshot;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>ShopProductDetailSnapshot</code></b>
 * <p/>
 * ShopProductDetailSnapshot的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-16 18:36:51.
 *
 * @author ${model.author}
 * @version 2.0.0
 * @since torinosrc-spring-boot-be 2.0.0
 */
@Entity
@Table(name="t_shop_product_detail_snapshot")
@DynamicInsert
@DynamicUpdate
public class ShopProductDetailSnapshot extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public ShopProductDetailSnapshot(){
        super();
    }

    @Column(name = "uppest_price")
    private Integer uppestPrice;

    @Column(name = "advise_price")
    private Integer advisePrice;

    @Column(name = "product_price")
    private Integer productPrice;

    @Column(name="shop_product_detail_id")
    private Long shopProductDetailId;

    @Column(name = "shop_product_snapshot_id")
    private Long shopProductSnapshotId;

    @Column(name = "product_detail_snapshot_id")
    private Long productDetailSnapshotId;

    @OneToOne(targetEntity = ProductDetailSnapshot.class)
    @JoinColumn(name = "product_detail_snapshot_id",referencedColumnName = "id",insertable = false,updatable = false)
    private ProductDetailSnapshot productDetailSnapshot;

    @JsonBackReference
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="shop_product_snapshot_id",insertable = false,updatable = false)
    private ShopProductSnapshot shopProductSnapshot;

    public Long getShopProductDetailId() {
        return shopProductDetailId;
    }

    public void setShopProductDetailId(Long shopProductDetailId) {
        this.shopProductDetailId = shopProductDetailId;
    }

    public Long getShopProductSnapshotId() {
        return shopProductSnapshotId;
    }

    public void setShopProductSnapshotId(Long shopProductSnapshotId) {
        this.shopProductSnapshotId = shopProductSnapshotId;
    }

    public Long getProductDetailSnapshotId() {
        return productDetailSnapshotId;
    }

    public void setProductDetailSnapshotId(Long productDetailSnapshotId) {
        this.productDetailSnapshotId = productDetailSnapshotId;
    }

    public Integer getUppestPrice() {
        return uppestPrice;
    }

    public void setUppestPrice(Integer uppestPrice) {
        this.uppestPrice = uppestPrice;
    }

    public Integer getAdvisePrice() {
        return advisePrice;
    }

    public void setAdvisePrice(Integer advisePrice) {
        this.advisePrice = advisePrice;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public ProductDetailSnapshot getProductDetailSnapshot() {
        return productDetailSnapshot;
    }

    public void setProductDetailSnapshot(ProductDetailSnapshot productDetailSnapshot) {
        this.productDetailSnapshot = productDetailSnapshot;
    }

    public ShopProductSnapshot getShopProductSnapshot() {
        return shopProductSnapshot;
    }

    public void setShopProductSnapshot(ShopProductSnapshot shopProductSnapshot) {
        this.shopProductSnapshot = shopProductSnapshot;
    }

    @Override
    public String toString() {
        return "ShopProductDetailSnapshot{" +
                "uppestPrice=" + uppestPrice +
                ", advisePrice=" + advisePrice +
                ", productPrice=" + productPrice +
                ", shopProductDetailId=" + shopProductDetailId +
                ", shopProductSnapshotId=" + shopProductSnapshotId +
                ", productDetailSnapshotId=" + productDetailSnapshotId +
                ", productDetailSnapshot=" + productDetailSnapshot +
                ", shopProductSnapshot=" + shopProductSnapshot +
                '}';
    }
}
