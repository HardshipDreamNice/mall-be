/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.userboostteam;

import com.torinosrc.model.entity.userboostteam.UserBoostTeam;
import com.torinosrc.model.view.userboostteam.UserBoostTeamView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>UserBoostTeam</code></b>
* <p/>
* UserBoostTeam的具体实现
* <p/>
* <b>Creation Time:</b> 2018-12-03 15:15:48.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface UserBoostTeamService extends BaseService<UserBoostTeamView> {

    UserBoostTeamView saveOrReturnUserBoostTeam(UserBoostTeamView userBoostTeamView);

}
