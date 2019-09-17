/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.teamuser;

import com.torinosrc.model.entity.teamuser.TeamUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * <b><code>TeamUserDao</code></b>
 * <p/>
 * TeamUser的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-16 10:54:01.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface TeamUserDao extends JpaRepository<TeamUser, Long>, JpaSpecificationExecutor<TeamUser> {

    public List<TeamUser> findByTeamId(Long teamId);

//    @Query(nativeQuery = true,value = "select *from t_team_user where team_id=?1 order by type desc")
    public List<TeamUser> findByTeamIdOrderByTypeDesc(Long teamId);

    public void deleteByTeamId(Long teamId);

    public Integer countByTeamId(Long teamId);

    /**
     * team_user软删除
     * @param id
     */
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update t_team_user set enabled=0 where team_id=?1")
    public void updateTeamUserEnable(Long id);
}
