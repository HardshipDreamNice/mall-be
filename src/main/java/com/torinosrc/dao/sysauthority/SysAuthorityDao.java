/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.sysauthority;

import com.torinosrc.model.entity.sysauthority.SysAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <b><code>SysAuthorityDao</code></b>
 * <p/>
 * SysAuthority的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-04-13 14:31:03.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface SysAuthorityDao extends JpaRepository<SysAuthority, Long>, JpaSpecificationExecutor<SysAuthority> {
    public SysAuthority findByName(String name);

}
