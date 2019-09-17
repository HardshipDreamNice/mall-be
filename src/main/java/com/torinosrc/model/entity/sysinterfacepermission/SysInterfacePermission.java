/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.sysinterfacepermission;

import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <b><code>SysInterfacePermission</code></b>
 * <p/>
 * SysInterfacePermission的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-04-14 17:27:52.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Entity
@Table(name="t_sys_interface_permission")
@DynamicInsert
@DynamicUpdate
public class SysInterfacePermission extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public SysInterfacePermission(){
        super();
    }

    /**
     * name
     */
    @Column(name = "name")
    private String name;
    /**
     * description
     */
    @Column(name="permission")
    private String permission;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
