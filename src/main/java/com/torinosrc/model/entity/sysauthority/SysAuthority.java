/*
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.sysauthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import com.torinosrc.model.entity.sysinterfacepermission.SysInterfacePermission;
import com.torinosrc.model.entity.sysmenupermission.SysMenuPermission;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * <b><code>SysAuthority</code></b>
 * <p/>
 * SysAuthority
 * <p/>
 * <b>Creation Time:</b> 2017/05/17 21:18.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-model 1.0.0
 */
@Entity
@Table(name="t_sys_authority")
public class SysAuthority extends BaseEntity implements Serializable {

    /**
     * 标识当前角色是否拥有该权限
     */
    @Transient
    private boolean hasAuthority = false;

    /**
     * name
     */

    @Column(name = "name")
    private String name;
    /**
     * description
     */
    @Column(name="description")
    private String description;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
    @JoinTable(name = "t_sys_authority_menu_permission",
            joinColumns = @JoinColumn(name="sys_authority_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="sys_menu_permission_id", referencedColumnName="id"))
    private Set<SysMenuPermission> sysMenuPermissions;


    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
    @JoinTable(name = "t_sys_authority_interface_permission",
            joinColumns = @JoinColumn(name="sys_authority_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="sys_interface_permission_id", referencedColumnName="id"))
    private Set<SysInterfacePermission> sysInterfacePermissions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHasAuthority() {
        return hasAuthority;
    }

    public void setHasAuthority(boolean hasAuthority) {
        this.hasAuthority = hasAuthority;
    }

    public Set<SysMenuPermission> getSysMenuPermissions() {
        return sysMenuPermissions;
    }

    public void setSysMenuPermissions(Set<SysMenuPermission> sysMenuPermissions) {
        this.sysMenuPermissions = sysMenuPermissions;
    }

    public Set<SysInterfacePermission> getSysInterfacePermissions() {
        return sysInterfacePermissions;
    }

    public void setSysInterfacePermissions(Set<SysInterfacePermission> sysInterfacePermissions) {
        this.sysInterfacePermissions = sysInterfacePermissions;
    }
}
