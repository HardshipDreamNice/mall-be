/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.userredenvelopeteam;

import com.torinosrc.model.entity.userredenvelopeteam.UserRedEnvelopeTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b><code>UserRedEnvelopeTeamDao</code></b>
 * <p/>
 * UserRedEnvelopeTeam的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-12-04 12:18:22.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface UserRedEnvelopeTeamDao extends JpaRepository<UserRedEnvelopeTeam, Long>, JpaSpecificationExecutor<UserRedEnvelopeTeam> {

    /**
     * 根据 userId 和 redEnvelopeTeamId 查找唯一的 UserRedEnvelopeTeam
     * @param userId
     * @param redEnvelopeTeamId
     * @return
     */
    UserRedEnvelopeTeam findByUserIdAndRedEnvelopeTeamId(Long userId, Long redEnvelopeTeamId);

    List<UserRedEnvelopeTeam> findByRedEnvelopeTeamId(Long redEnvelopeTeamId);

    Integer countByRedEnvelopeTeamId(Long redEnvelopeTeamId);

}
