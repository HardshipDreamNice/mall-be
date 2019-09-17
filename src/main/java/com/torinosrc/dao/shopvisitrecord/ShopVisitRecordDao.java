/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.shopvisitrecord;

import com.torinosrc.model.entity.shopvisitrecord.ShopVisitRecord;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <b><code>ShopVisitRecordDao</code></b>
 * <p/>
 * ShopVisitRecord的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-19 18:21:46.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface ShopVisitRecordDao extends JpaRepository<ShopVisitRecord, Long>, JpaSpecificationExecutor<ShopVisitRecord> {

    ShopVisitRecord findByUserIdAndShopId(Long userId, Long shopId);

    ShopVisitRecord findFirstByUserIdAndEnabledOrderByUpdateTimeDesc(Long userId, Integer enabled);

}
