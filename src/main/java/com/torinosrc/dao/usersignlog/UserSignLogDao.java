/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.usersignlog;

import com.torinosrc.model.entity.usersignlog.UserSignLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <b><code>UserSignLogDao</code></b>
 * <p/>
 * UserSignLog的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-29 16:44:39.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface UserSignLogDao extends JpaRepository<UserSignLog, Long>, JpaSpecificationExecutor<UserSignLog> {

    UserSignLog findBySignUserIdAndSignTimeBetween(Long userId, Long startTime, Long endTime);
}
