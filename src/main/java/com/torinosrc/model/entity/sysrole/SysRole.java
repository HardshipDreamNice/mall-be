/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.sysrole;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.sysauthority.SysAuthority;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * <b><code>SysRole</code></b>
 * <p/>
 * SysRole的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-04-13 14:41:37.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_sys_role")
@DynamicInsert
@DynamicUpdate
public class SysRole extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public SysRole(){
        super();
    }

    /**
     * The SysUser name.
     */

    @Column(name = "english_name")
    private String englishName;
    /**
     * The Password.
     */
    @Column(name="chinese_name")
    private String chineseName;

    @Column(name="description")
    private String description;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
    @JoinTable(name = "t_sys_role_authority",
            joinColumns = @JoinColumn(name="sys_role_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="sys_authority_id", referencedColumnName="id"))
    private Set<SysAuthority> sysAuthorities;


    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<SysAuthority> getSysAuthorities() {
        return sysAuthorities;
    }

    public void setSysAuthorities(Set<SysAuthority> sysAuthorities) {
        this.sysAuthorities = sysAuthorities;
    }
}
