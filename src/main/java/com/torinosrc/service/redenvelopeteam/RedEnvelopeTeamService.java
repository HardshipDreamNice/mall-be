/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.redenvelopeteam;

import com.torinosrc.model.view.redenvelopeteam.RedEnvelopeTeamView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>RedEnvelopeTeam</code></b>
* <p/>
* RedEnvelopeTeam的具体实现
* <p/>
* <b>Creation Time:</b> 2018-12-04 12:10:39.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface RedEnvelopeTeamService extends BaseService<RedEnvelopeTeamView> {

    /**
     * 创建抢红包团队。如果已开团，则返回团队信息，包括成员的头像
     * @param redEnvelopeTeamView
     * @return
     */
    RedEnvelopeTeamView createOrReturnRedEnvelopeTeam(RedEnvelopeTeamView redEnvelopeTeamView);

    /**
     * 根据 userId 和 redEnvelopeId 获取一个红包团队
     * userId 需是团长的id
     * @param userId
     * @param redEnvelopeId
     * @return
     */
    RedEnvelopeTeamView getRedEnvelopeTeamByUserIdAndRedEnvelopeId(Long userId, Long redEnvelopeId);

}
