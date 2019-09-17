/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.distributionpriceconfig;

import com.torinosrc.model.entity.distributionpriceconfig.DistributionPriceConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <b><code>DistributionPriceConfigDao</code></b>
 * <p/>
 * DistributionPriceConfig的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-12 15:13:23.
 *
 * @author ${model.author}
 * @version 2.0.0
 * @since torinosrc-spring-boot-be 2.0.0
 */
@Repository
public interface DistributionPriceConfigDao extends JpaRepository<DistributionPriceConfig, Long>, JpaSpecificationExecutor<DistributionPriceConfig> {

    public DistributionPriceConfig findByPercentConfig(String percentConfig);

}
