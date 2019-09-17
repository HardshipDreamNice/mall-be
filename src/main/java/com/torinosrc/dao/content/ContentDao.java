/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.content;

import com.torinosrc.model.entity.content.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b><code>ContentDao</code></b>
 * <p/>
 * Content的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-05-28 12:06:23.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface ContentDao extends JpaRepository<Content, Long>, JpaSpecificationExecutor<Content> {

    public List<Content> findByEnabled(Integer enabled);
}
