/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.security;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.model.view.sysuser.SysUserView;
import com.torinosrc.service.sysuser.SysUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * <b><code>TorinoSrcRealm</code></b>
 * <p/>
 * TorinoSrcRealm的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-04-13 14:33:50.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public class TorinoSrcRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LogManager.getLogger(TorinoSrcRealm.class);

    @Autowired
    private SysUserService sysUserService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String username = JWTUtil.getUsername(principals.toString());
        SysUserView sysUserView = null;
        try {
            sysUserView = sysUserService.getUserRoleAuthorityPermissions(username);
        }catch (TorinoSrcServiceException e){
            throw new UnknownAccountException("用户不存在！");
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermissions(sysUserView.getUserInterfacePermissions());
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {

        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        // String exp
        if (StringUtils.isEmpty(username)) {
            throw new IncorrectCredentialsException ("token不正确！");
        }

        // 判断token是否过期
        if(JWTUtil.isExpiresToken(token)){
            throw new ExpiredCredentialsException ("token过期！");
//            throw new AuthenticationException("token expires");
        }

        SysUserView sysUserView = null;
        try {
            // TODO: 为了加快速度，这里可以用缓存技术 - lvxin
            sysUserView = sysUserService.getUserRoleAuthorityPermissions(username);
        }catch (TorinoSrcServiceException e){
//            throw new AuthenticationException("用户不存在！");
            // 用户不存在
            throw new UnknownAccountException("用户不存在！");
        }

        if(ObjectUtils.isEmpty(sysUserView)){
            // 用户不存在
            throw new UnknownAccountException("用户对象不存在！");
        }else if(sysUserView.getEnabled() == 0) {
            // 用户禁用
            throw new DisabledAccountException();
        }else {
            //
        }

        if (! JWTUtil.verify(token, username, sysUserView.getPassword())) {
            throw new IncorrectCredentialsException("用户名/密码错误！");
        }

        return new SimpleAuthenticationInfo(token, token, "torinosrc_realm");
    }
}