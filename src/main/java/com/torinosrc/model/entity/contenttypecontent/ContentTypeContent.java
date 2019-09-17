/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.contenttypecontent;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>ContentTypeContent</code></b>
 * <p/>
 * ContentTypeContent的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-11 18:15:49.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_content_type_content")
@DynamicInsert
@DynamicUpdate
public class ContentTypeContent implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public ContentTypeContent(){
        super();
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_type_id")
    private Long contenttypeId;

    @Column(name = "content_id")
    private Long contentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContenttypeId() {
        return contenttypeId;
    }

    public void setContenttypeId(Long contenttypeId) {
        this.contenttypeId = contenttypeId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }
}
