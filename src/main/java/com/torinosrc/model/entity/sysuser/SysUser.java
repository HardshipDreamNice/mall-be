/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.sysuser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.sysrole.SysRole;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * <b><code>SysUser</code></b>
 * <p/>
 * SysUser的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-04-13 14:33:50.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_sys_user")
@DynamicInsert
@DynamicUpdate
public class SysUser extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public SysUser(){
        super();
    }

    /**
     * The SysUser name.
     */
    @Column(name = "username")
    private String userName;
    /**
     * The Password.
     */
    @Column(name="password")
    private String password;

    /**
     * The Password.
     */
    @Column(name="name")
    private String name;

    /**
     * The Password.
     */
    @Column(name="email")
    private String email;

    /**
     * The Password.
     */
    @Column(name="mobile")
    private String mobile;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
    @JoinTable(name = "t_sys_user_role",
            joinColumns = @JoinColumn(name="sys_user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="sys_role_id", referencedColumnName="id"))
    private Set<SysRole> sysRoles;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<SysRole> getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(Set<SysRole> sysRoles) {
        this.sysRoles = sysRoles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
