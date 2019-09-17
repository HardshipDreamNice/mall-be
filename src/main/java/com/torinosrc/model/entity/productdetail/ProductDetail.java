/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.productdetail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.entity.productdetailprice.ProductDetailPrice;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * <b><code>ProductDetail</code></b>
 * <p/>
 * ProductDetail的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-06 11:28:08.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_product_detail")
@DynamicInsert
@DynamicUpdate
public class ProductDetail extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public ProductDetail(){
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

    @Column(name="inventory")
    private Long inventory;

    @Column(name="commission")
    private Long commission;

    @Column(name="product_id")
    private Long productId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "product_id",referencedColumnName = "id",insertable = false,updatable = false)
    private Product product;

//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    @OneToOne(targetEntity = ProductDetailPrice.class)
//    @JoinColumn(name = "id",referencedColumnName = "product_detail_id")
//    private  ProductDetailPrice productDetailPrice;

    @JsonIgnore
    @OneToOne(mappedBy = "productDetail", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    private ProductDetailPrice productDetailPrice;

    @OneToMany(mappedBy = "productDetail", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    public List<ProductDetailPrice> productDetailPriceList;

    @Transient
    private Long groupPrice;

    public Long getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(Long groupPrice) {
        this.groupPrice = groupPrice;
    }

    public List<ProductDetailPrice> getProductDetailPriceList() {
        return productDetailPriceList;
    }

    public void setProductDetailPriceList(List<ProductDetailPrice> productDetailPriceList) {
        this.productDetailPriceList = productDetailPriceList;
    }

    public Long getUppestPrice() {
        return uppestPrice;
    }

    public void setUppestPrice(Long uppestPrice) {
        this.uppestPrice = uppestPrice;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getInventory() {
        return inventory;
    }

    public void setInventory(Long inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "size='" + size + '\'' +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", inventory=" + inventory +
                ", commission=" + commission +
                ", product=" + product +
                '}';
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public ProductDetailPrice getProductDetailPrice() {
        return productDetailPrice;
    }

    public void setProductDetailPrice(ProductDetailPrice productDetailPrice) {
        this.productDetailPrice = productDetailPrice;
    }
}
