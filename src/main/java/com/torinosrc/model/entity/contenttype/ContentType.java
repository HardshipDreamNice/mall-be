/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.contenttype;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * <b><code>ContentType</code></b>
 * <p/>
 * ContentType的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-05-28 12:08:05.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_content_type")
@DynamicInsert
@DynamicUpdate
public class ContentType extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    @Column(name = "title")
    private String title;

    @Column(name = "level")
    private Integer level;

    @Column(name="parent_id")
    private Long parentId;

    @Column(name="expand")
    private Boolean expand;

//    //父类型
//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name="parent_id",insertable = false,updatable = false)
//    private ContentType parent;

    //子类型
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch=FetchType.EAGER,cascade = CascadeType.REMOVE)
    @JoinColumn(name="parent_id")
    @OrderBy("updateTime ASC")
    private Set<ContentType> children;

//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    @JsonIgnore
//    @ManyToMany(fetch=FetchType.EAGER)
//    @JoinTable(name = "t_content_type_content",
//            joinColumns = @JoinColumn(name="content_type_id",referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name="content_id", referencedColumnName="id"))
//    private Set<Content> contents;

    public ContentType(){
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

//    public ContentType getParent() {
//        return parent;
//    }
//
//    public void setParent(ContentType parent) {
//        this.parent = parent;
//    }


    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getExpand() {
        return expand;
    }

    public void setExpand(Boolean expand) {
        this.expand = expand;
    }

    public Set<ContentType> getChildren() {
        return children;
    }

    public void setChildren(Set<ContentType> children) {
        this.children = children;
    }

//    public Set<Content> getContents() {
//        return contents;
//    }
//
//    public void setContents(Set<Content> contents) {
//        this.contents = contents;
//    }
}
