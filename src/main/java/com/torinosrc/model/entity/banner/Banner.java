/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.banner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>Banner</code></b>
 * <p/>
 * Banner的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-26 20:40:46.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_banner")
@DynamicInsert
@DynamicUpdate
public class Banner extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public Banner(){
        super();
    }

    @Column(name = "title")
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "content")
    private String content;

    @Column(name = "image")
    private String image;

    @Column(name = "url")
    private String url;

    @Column(name = "weight")
    private Integer weight;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

}
