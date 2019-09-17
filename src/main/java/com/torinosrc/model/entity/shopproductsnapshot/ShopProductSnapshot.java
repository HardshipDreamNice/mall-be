/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.shopproductsnapshot;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.entity.productsnapshot.ProductSnapshot;
import com.torinosrc.model.entity.shop.Shop;
import com.torinosrc.model.entity.shopproductdetail.ShopProductDetail;
import com.torinosrc.model.entity.shopproductdetailsnapshot.ShopProductDetailSnapshot;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * <b><code>ShopProductSnapshot</code></b>
 * <p/>
 * ShopProductSnapshot的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-16 18:36:22.
 *
 * @author ${model.author}
 * @version 2.0.0
 * @since torinosrc-spring-boot-be 2.0.0
 */
@Entity
@Table(name="t_shop_product_snapshot")
@DynamicInsert
@DynamicUpdate
public class ShopProductSnapshot extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public ShopProductSnapshot(){
        super();
    }

    @Column(name = "on_sale")
    private Integer onSale;

    @Column(name = "product_snapshot_id")
    private Long productSnapshotId;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name="shop_product_id")
    private Long shopProductId;

    @OneToOne(targetEntity = ProductSnapshot.class)
    @JoinColumn(name = "product_snapshot_id",referencedColumnName = "id",insertable = false,updatable = false)
    private ProductSnapshot productSnapshot;

//    @JsonBackReference
//    @ManyToOne(fetch=FetchType.EAGER)
//    @JoinColumn(name="shop_id")
//    private Shop shop;

    @OneToMany(mappedBy = "shopProductSnapshot",fetch=FetchType.EAGER,cascade = { CascadeType.PERSIST ,CascadeType.ALL})
    private Set<ShopProductDetailSnapshot> shopProductDetailSnapshots;

    public Long getShopProductId() {
        return shopProductId;
    }

    public void setShopProductId(Long shopProductId) {
        this.shopProductId = shopProductId;
    }

    public Long getProductSnapshotId() {
        return productSnapshotId;
    }

    public void setProductSnapshotId(Long productSnapshotId) {
        this.productSnapshotId = productSnapshotId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Set<ShopProductDetailSnapshot> getShopProductDetailSnapshots() {
        return shopProductDetailSnapshots;
    }

    public void setShopProductDetailSnapshots(Set<ShopProductDetailSnapshot> shopProductDetailSnapshots) {
        this.shopProductDetailSnapshots = shopProductDetailSnapshots;
    }

    public Integer getOnSale() {
        return onSale;
    }

    public void setOnSale(Integer onSale) {
        this.onSale = onSale;
    }

    public ProductSnapshot getProductSnapshot() {
        return productSnapshot;
    }

    public void setProductSnapshot(ProductSnapshot productSnapshot) {
        this.productSnapshot = productSnapshot;
    }

//    @Override
//    public String toString() {
//        return "ShopProductSnapshot{" +
//                "onSale=" + onSale +
//                ", productSnapshotId=" + productSnapshotId +
//                ", shopId=" + shopId +
//                ", shopProductId=" + shopProductId +
//                ", productSnapshot=" + productSnapshot +
//                ", shopProductDetailSnapshots=" + shopProductDetailSnapshots +
//                '}';
//    }
}
