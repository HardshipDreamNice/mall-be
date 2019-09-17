/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.category.Category;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * <b><code>Product</code></b>
 * <p/>
 * Product的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-06 11:17:58.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name = "t_product")
@DynamicInsert
@DynamicUpdate
public class Product extends BaseEntity implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    public Product() {
        super();
    }

    @Column(name = "name")
    private String name;

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

    @Column(name = "title")
    private String title;

    @Column(name = "category_id")
    private Long categoryId;

    @ApiModelProperty(value = "0为普通商品，1为拼团，2为兼有 3为助力购")
    @Column(name = "type")
    private Integer type;

    @Column(name = "expired_time")
    private String expiredTime;

    @Column(name = "count")
    private Integer count;

    @Column(name = "member_count")
    private Integer memberCount;

    @Column(name = "weight")
    private Integer weight;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    public List<ProductDetail> productDetailList;

    @Column(name = "boost_number")
    private Integer boostNumber;

    @Column(name = "valid_day")
    private Integer validDay;

    @Column(name = "boost_amount")
    private Integer boostAmount;

    @Column(name = "team_image_url")
    private String teamImageUrl;

    public String getTeamImageUrl() {
        return teamImageUrl;
    }

    public void setTeamImageUrl(String teamImageUrl) {
        this.teamImageUrl = teamImageUrl;
    }

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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public List<ProductDetail> getProductDetailList() {
        return productDetailList;
    }

    public void setProductDetailList(List<ProductDetail> productDetailList) {
        this.productDetailList = productDetailList;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBoostNumber() {
        return boostNumber;
    }

    public void setBoostNumber(Integer boostNumber) {
        this.boostNumber = boostNumber;
    }

    public Integer getValidDay() {
        return validDay;
    }

    public void setValidDay(Integer validDay) {
        this.validDay = validDay;
    }

    public Integer getBoostAmount() {
        return boostAmount;
    }

    public void setBoostAmount(Integer boostAmount) {
        this.boostAmount = boostAmount;
    }
}
