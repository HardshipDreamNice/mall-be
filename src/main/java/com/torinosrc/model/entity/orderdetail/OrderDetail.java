/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.orderdetail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.order.Order;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.entity.shopproduct.ShopProduct;
import com.torinosrc.model.entity.shopproductdetail.ShopProductDetail;
import com.torinosrc.model.entity.shopproductdetailsnapshot.ShopProductDetailSnapshot;
import com.torinosrc.model.entity.shopproductsnapshot.ShopProductSnapshot;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * <b><code>OrderDetail</code></b>
 * <p/>
 * OrderDetail的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-06 11:31:43.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name = "t_order_detail")
@DynamicInsert
@DynamicUpdate
public class OrderDetail extends BaseEntity implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    public OrderDetail() {
        super();
    }

    @Column(name = "count")
    private Integer count;

    @Column(name = "price")
    private Long price;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_detail_id")
    private Long productDetailId;

    @Column(name = "shop_product_detail_snapshot_id")
    private Long shopProductDetailSnapshotId;

    @Column(name = "product_detail_snapshot_id")
    private Long productDetailSnapshotId;

    @Column(name = "product_free_receive_detail_id")
    private Long productFreeReceiveDetailId;

    @Transient
    private Long shopProductDetailId;

    @Transient
    private Long shoppingCartDetailId;

    @Column(name = "membership_grade_id")
    private Long membershipGradeId;

    public Long getProductFreeReceiveDetailId() {
        return productFreeReceiveDetailId;
    }

    public void setProductFreeReceiveDetailId(Long productFreeReceiveDetailId) {
        this.productFreeReceiveDetailId = productFreeReceiveDetailId;
    }

    public Long getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(Long productDetailId) {
        this.productDetailId = productDetailId;
    }

    public Long getShopProductDetailSnapshotId() {
        return shopProductDetailSnapshotId;
    }

    public void setShopProductDetailSnapshotId(Long shopProductDetailSnapshotId) {
        this.shopProductDetailSnapshotId = shopProductDetailSnapshotId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getProductDetailSnapshotId() {
        return productDetailSnapshotId;
    }

    public void setProductDetailSnapshotId(Long productDetailSnapshotId) {
        this.productDetailSnapshotId = productDetailSnapshotId;
    }

    public Long getShopProductDetailId() {
        return shopProductDetailId;
    }

    public void setShopProductDetailId(Long shopProductDetailId) {
        this.shopProductDetailId = shopProductDetailId;
    }

    public Long getShoppingCartDetailId() {
        return shoppingCartDetailId;
    }

    public void setShoppingCartDetailId(Long shoppingCartDetailId) {
        this.shoppingCartDetailId = shoppingCartDetailId;
    }

    public Long getMembershipGradeId() {
        return membershipGradeId;
    }

    public void setMembershipGradeId(Long membershipGradeId) {
        this.membershipGradeId = membershipGradeId;
    }

}
