/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.productsnapshot;

import com.torinosrc.model.entity.productsnapshot.ProductSnapshot;
import com.torinosrc.model.entity.shoptree.ShopTree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <b><code>ProductSnapshotDao</code></b>
 * <p/>
 * ProductSnapshot的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-16 18:35:02.
 *
 * @author ${model.author}
 * @version 2.0.0
 * @since torinosrc-spring-boot-be 2.0.0
 */
@Repository
public interface ProductSnapshotDao extends JpaRepository<ProductSnapshot, Long>, JpaSpecificationExecutor<ProductSnapshot> {
    public ProductSnapshot findByProductIdAndUpdateTime(Long productId,Long updateTime);

    public ProductSnapshot findByProductId(Long productId);
}
