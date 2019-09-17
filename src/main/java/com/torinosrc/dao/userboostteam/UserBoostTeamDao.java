/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.userboostteam;

import com.torinosrc.model.entity.userboostteam.UserBoostTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b><code>UserBoostTeamDao</code></b>
 * <p/>
 * UserBoostTeam的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-12-03 15:15:48.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface UserBoostTeamDao extends JpaRepository<UserBoostTeam, Long>, JpaSpecificationExecutor<UserBoostTeam> {

    /**
     * 根据 userId 和 boostTeamId 查找到唯一的一个 砍价记录，即 userBoostTeam
     * @param userId
     * @param boostTeamId
     * @return
     */
    UserBoostTeam findOneByUserIdAndBoostTeamId(Long userId, Long boostTeamId);

    /**
     * 根据 boostTeamId 查找所有砍价人员，包括发起者
     * @param boostTeamId
     * @return
     */
    List<UserBoostTeam> findByBoostTeamId(Long boostTeamId);

    /**
     * 根据 boostTeamId 和 type(是否团长) 查找所有砍价人员
     * @param boostTeamId
     * @param type
     * @return
     */
    List<UserBoostTeam> findByBoostTeamIdAndType(Long boostTeamId, Integer type);
}
