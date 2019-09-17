/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.user;

import com.torinosrc.model.view.user.UserView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>User</code></b>
* <p/>
* User的具体实现
* <p/>
* <b>Creation Time:</b> 2018-04-16 14:17:14.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface UserService extends BaseService<UserView> {
    UserView login(String openId);

}
