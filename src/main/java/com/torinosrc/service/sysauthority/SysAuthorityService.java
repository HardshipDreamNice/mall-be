/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.sysauthority;

import com.torinosrc.model.view.sysauthority.SysAuthorityView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>SysAuthority</code></b>
* <p/>
* SysAuthority的具体实现
* <p/>
* <b>Creation Time:</b> 2018-04-13 14:31:03.
*
* @author lvxin
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface SysAuthorityService extends BaseService<SysAuthorityView> {
    public boolean checkIfExist(String name);

}
