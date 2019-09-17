/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.productsnapshot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>ProductSnapshot</code></b>
 * <p/>
 * ProductSnapshot的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-16 18:35:02.
 *
 * @author ${model.author}
 * @version 2.0.0
 * @since torinosrc-spring-boot-be 2.0.0
 */
@Entity
@Table(name="t_product_snapshot")
@DynamicInsert
@DynamicUpdate
public class ProductSnapshot extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public ProductSnapshot(){
        super();
    }

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "small_image_url")
    private String smallImageUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "size_desc")
    private String sizeDesc;

    @Column(name="product_id")
    private Long productId;

    @Column(name = "type")
    private Integer type;

    @Column(name = "expired_time")
    private String expiredTime;

    @Column(name = "count")
    private Integer count;

    @Column(name = "member_count")
    private Integer memberCount;

    @Column(name = "product_type")
    private Integer productType;

    @Column(name = "membership_grade_id")
    private Long membershipGradeId;

    @Column(name = "product_free_receive_id")
    private Long productFreeReceiveId;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSizeDesc() {
        return sizeDesc;
    }

    public void setSizeDesc(String sizeDesc) {
        this.sizeDesc = sizeDesc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Long getMembershipGradeId() {
        return membershipGradeId;
    }

    public void setMembershipGradeId(Long membershipGradeId) {
        this.membershipGradeId = membershipGradeId;
    }

    public Long getProductFreeReceiveId() {
        return productFreeReceiveId;
    }

    public void setProductFreeReceiveId(Long productFreeReceiveId) {
        this.productFreeReceiveId = productFreeReceiveId;
    }

    @Override
    public String toString() {
        return "ProductSnapshot{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", smallImageUrl='" + smallImageUrl + '\'' +
                ", description='" + description + '\'' +
                ", sizeDesc='" + sizeDesc + '\'' +
                ", productId=" + productId +
                '}';
    }
}
