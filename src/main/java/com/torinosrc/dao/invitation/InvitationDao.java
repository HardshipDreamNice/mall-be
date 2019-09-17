/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.invitation;

import com.torinosrc.model.entity.invitation.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <b><code>InvitationDao</code></b>
 * <p/>
 * Invitation的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-12 09:30:20.
 *
 * @author ${model.author}
 * @version 2.0.0
 * @since torinosrc-spring-boot-be 2.0.0
 */
@Repository
public interface InvitationDao extends JpaRepository<Invitation, Long>, JpaSpecificationExecutor<Invitation> {
    public Invitation findByUserId(Long userId);

}
