/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.sysuser;

import com.torinosrc.model.entity.sysuser.SysUser;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Set;

/**
 * <b><code>SysUserView</code></b>
 * <p/>
 * SysUser的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-04-13 14:33:50.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class SysUserView extends SysUser implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 存储用户的新密码
     */
    private String newPassword;

    private String token;

    private String validateId;

    private String validateCode;

    private Set<String> userRoles;

    private Set<String> userAuthorities;

    private Set<String> userMenuPermissions;

    private Set<String> userInterfacePermissions;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getValidateId() {
        return validateId;
    }

    public void setValidateId(String validateId) {
        this.validateId = validateId;
    }

    public Set<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<String> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<String> getUserAuthorities() {
        return userAuthorities;
    }

    public void setUserAuthorities(Set<String> userAuthorities) {
        this.userAuthorities = userAuthorities;
    }

    public Set<String> getUserMenuPermissions() {
        return userMenuPermissions;
    }

    public void setUserMenuPermissions(Set<String> userMenuPermissions) {
        this.userMenuPermissions = userMenuPermissions;
    }

    public Set<String> getUserInterfacePermissions() {
        return userInterfacePermissions;
    }

    public void setUserInterfacePermissions(Set<String> userInterfacePermissions) {
        this.userInterfacePermissions = userInterfacePermissions;
    }
}
