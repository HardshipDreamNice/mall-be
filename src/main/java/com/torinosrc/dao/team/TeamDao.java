/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.team;

import com.torinosrc.model.entity.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * <b><code>TeamDao</code></b>
 * <p/>
 * Team的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-16 10:53:24.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface TeamDao extends JpaRepository<Team, Long>, JpaSpecificationExecutor<Team> {

    public List<Team> findByExpiredTimeBetween(Long sTime,Long eTime);

    @Query(nativeQuery = true,value = "select *from t_team where UNIX_TIMESTAMP(expired_time)*1000 between ?1 and ?2")
    public List<Team> findByExpiredTimeLong(Long sTime,Long eTime);

    public void deleteById(Long id);

    /**
     * team软删除
     * @param id
     */
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update t_team set enabled=0 where id=?1")
    public void updateTeamEnable(Long id);
}
