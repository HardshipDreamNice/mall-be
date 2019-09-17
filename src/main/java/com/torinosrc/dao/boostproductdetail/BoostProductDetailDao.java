/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.boostproductdetail;

import com.torinosrc.model.entity.boostproductdetail.BoostProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <b><code>BoostProductDetailDao</code></b>
 * <p/>
 * BoostProductDetail的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-30 16:10:27.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface BoostProductDetailDao extends JpaRepository<BoostProductDetail, Long>, JpaSpecificationExecutor<BoostProductDetail> {

}
