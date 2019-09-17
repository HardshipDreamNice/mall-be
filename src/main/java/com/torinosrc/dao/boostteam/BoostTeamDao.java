/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.boostteam;

import com.torinosrc.model.entity.boostteam.BoostTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <b><code>BoostTeamDao</code></b>
 * <p/>
 * BoostTeam的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-12-03 15:14:32.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface BoostTeamDao extends JpaRepository<BoostTeam, Long>, JpaSpecificationExecutor<BoostTeam> {

}
