/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.category;

import com.torinosrc.model.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <b><code>CategoryDao</code></b>
 * <p/>
 * Category的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-08-01 14:35:12.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface CategoryDao extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

}
