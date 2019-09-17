/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.contenttype;

import com.torinosrc.model.entity.contenttype.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <b><code>ContentTypeDao</code></b>
 * <p/>
 * ContentType的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-05-28 12:08:05.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface ContentTypeDao extends JpaRepository<ContentType, Long>, JpaSpecificationExecutor<ContentType> {

}
