/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.userredenvelopeteam;

import com.torinosrc.model.entity.userredenvelopeteam.UserRedEnvelopeTeam;
import com.torinosrc.model.view.userredenvelopeteam.UserRedEnvelopeTeamView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>UserRedEnvelopeTeam</code></b>
* <p/>
* UserRedEnvelopeTeam的具体实现
* <p/>
* <b>Creation Time:</b> 2018-12-04 12:18:22.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface UserRedEnvelopeTeamService extends BaseService<UserRedEnvelopeTeamView> {

    /**
     * 参与抢红包
     * @param userRedEnvelopeTeamView
     * @return
     */
    UserRedEnvelopeTeamView saveUserRedEnvelopeTeam(UserRedEnvelopeTeamView userRedEnvelopeTeamView);

}
