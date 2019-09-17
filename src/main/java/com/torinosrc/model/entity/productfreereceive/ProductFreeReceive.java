/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.productfreereceive;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.entity.productfreereceivedetail.ProductFreeReceiveDetail;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * <b><code>ProductFreeReceive</code></b>
 * <p/>
 * ProductFreeReceive的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-27 19:54:27.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name = "t_product_free_receive")
@DynamicInsert
@DynamicUpdate
public class ProductFreeReceive extends BaseEntity implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    public ProductFreeReceive() {
        super();
    }

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "image_url1")
    private String imageUrl1;

    @Column(name = "image_url2")
    private String imageUrl2;

    @Column(name = "small_image_url")
    private String smallImageUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "size_desc")
    private String sizeDesc;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "type")
    private Integer type;

    @Column(name = "start_time")
    private Long startTime;

    @Column(name = "expired_time")
    private Long expiredTime;

    @Column(name = "count")
    private Integer count;

    @Column(name = "weight")
    private Integer weight;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "productFreeReceive", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    public List<ProductFreeReceiveDetail> productFreeReceiveDetailList;

    public List<ProductFreeReceiveDetail> getProductFreeReceiveDetailList() {
        return productFreeReceiveDetailList;
    }

    public void setProductFreeReceiveDetailList(List<ProductFreeReceiveDetail> productFreeReceiveDetailList) {
        this.productFreeReceiveDetailList = productFreeReceiveDetailList;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSizeDesc() {
        return sizeDesc;
    }

    public void setSizeDesc(String sizeDesc) {
        this.sizeDesc = sizeDesc;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }
}
