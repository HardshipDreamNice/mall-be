/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.sysuser;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.model.view.sysuser.SysUserView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

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
@Service
public interface SysUserService extends BaseService<SysUserView> {

    /**
     * 通过用户id查询用户权限
     * @param username 用户账号
     * @return 用户权限id
     * @throws TorinoSrcServiceException
     */
    public SysUserView getUserRoleAuthorityPermissions(String username) throws TorinoSrcServiceException;

    /**
     * 通过用户名密码进行登陆
     * @param username
     * @param password
     * @return
     * @throws TorinoSrcServiceException
     */
    public SysUserView login(String username, String password) throws TorinoSrcServiceException;

    /**
     * 检查用户名是否存在
     * @param userName
     * @return
     */
    public boolean checkIfExist(String userName);
}
