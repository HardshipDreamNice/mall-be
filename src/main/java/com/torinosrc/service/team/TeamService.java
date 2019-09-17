/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.team;

import com.torinosrc.model.entity.team.Team;
import com.torinosrc.model.view.team.TeamShowView;
import com.torinosrc.model.view.team.TeamView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>Team</code></b>
* <p/>
* Team的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-16 10:53:24.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface TeamService extends BaseService<TeamView> {
    public TeamShowView getTeamShowView(Long teamId);
}
