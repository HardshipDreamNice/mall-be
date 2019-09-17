/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.sysrole;

import com.torinosrc.model.view.sysrole.SysRoleView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

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
@Service
public interface SysRoleService extends BaseService<SysRoleView> {
    public boolean checkIfExist(String chineseName);

}
