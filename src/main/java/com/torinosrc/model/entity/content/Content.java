/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.content;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.contenttype.ContentType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * <b><code>Content</code></b>
 * <p/>
 * Content的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-05-28 12:06:23.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_content")
@DynamicInsert
@DynamicUpdate
public class Content extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public Content(){
        super();
    }

    @Column(name="title")
    private String title;

    @Column(name="sub_title")
    private String subTitle;

    @Column(name="author")
    private String author;

    @Column(name="content")
    private String content;

    @Column(name="weight")
    private Integer weight;

    @Column(name="release_time")
    private Long releaseTime;

    @Column(name="share_title")
    private String shareTitle;

    @Column(name="share_desc")
    private String shareDesc;

    @Column(name="share_img")
    private String shareImg;

    @Column(name="create_user")
    private String createUser;

    @Column(name="modify_user")
    private String modifyUser;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "t_content_type_content",
            joinColumns = @JoinColumn(name="content_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="content_type_id", referencedColumnName="id"))
    private Set<ContentType> contentTypes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Set<ContentType> getContentTypes() {
        return contentTypes;
    }

    public void setContentTypes(Set<ContentType> contentTypes) {
        this.contentTypes = contentTypes;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
