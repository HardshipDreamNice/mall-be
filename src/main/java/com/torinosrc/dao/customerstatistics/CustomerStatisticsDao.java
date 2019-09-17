/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.customerstatistics;

import com.torinosrc.model.entity.customerstatistics.CustomerStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <b><code>CustomerStatisticsDao</code></b>
 * <p/>
 * CustomerStatistics的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-06-06 11:32:50.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface CustomerStatisticsDao extends JpaRepository<CustomerStatistics, Long>, JpaSpecificationExecutor<CustomerStatistics> {

}
