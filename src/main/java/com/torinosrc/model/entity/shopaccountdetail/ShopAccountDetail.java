/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.shopaccountdetail;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.shop.Shop;
import com.torinosrc.model.entity.shopaccount.ShopAccount;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>ShopAccountDetail</code></b>
 * <p/>
 * ShopAccountDetail的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-12 16:34:26.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_shop_account_detail")
@DynamicInsert
@DynamicUpdate
public class ShopAccountDetail extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public ShopAccountDetail(){
        super();
    }

    @Column(name = "product_name")
    private String productName;

    @Column(name = "income_source")
    private Integer incomeSource;

    @Column(name = "income_amount")
    private Long incomeAmount;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "sale_count")
    private Integer saleCount;

    @Column(name = "shop_account_id")
    private Long shopAccountId;

    @JsonBackReference
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="shop_account_id",insertable = false,updatable = false)
    private ShopAccount shopAccount;

    @Column(name = "shop_product_snapshot_id")
    private Long shopProductSnapshotId;

    @Column(name = "shop_product_detail_snapshot_id")
    private Long shopProductDetailSnapshotId;

    @Column(name = "product_detail_snapshot_id")
    private Long productDetailSnapshotId;

    @Column(name = "order_detail_id")
    private Long orderDetailId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "record_status")
    private Integer recordStatus;

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Long getProductDetailSnapshotId() {
        return productDetailSnapshotId;
    }

    public void setProductDetailSnapshotId(Long productDetailSnapshotId) {
        this.productDetailSnapshotId = productDetailSnapshotId;
    }

    public Long getShopAccountId() {
        return shopAccountId;
    }

    public void setShopAccountId(Long shopAccountId) {
        this.shopAccountId = shopAccountId;
    }

    public ShopAccount getShopAccount() {
        return shopAccount;
    }

    public void setShopAccount(ShopAccount shopAccount) {
        this.shopAccount = shopAccount;
    }

    public Long getShopProductSnapshotId() {
        return shopProductSnapshotId;
    }

    public void setShopProductSnapshotId(Long shopProductSnapshotId) {
        this.shopProductSnapshotId = shopProductSnapshotId;
    }

    public Long getShopProductDetailSnapshotId() {
        return shopProductDetailSnapshotId;
    }

    public void setShopProductDetailSnapshotId(Long shopProductDetailSnapshotId) {
        this.shopProductDetailSnapshotId = shopProductDetailSnapshotId;
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getIncomeSource() {
        return incomeSource;
    }

    public void setIncomeSource(Integer incomeSource) {
        this.incomeSource = incomeSource;
    }

    public Long getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(Long incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }


}
