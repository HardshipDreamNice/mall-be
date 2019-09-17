/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.productcategorytable;

import com.torinosrc.model.view.productcategorytable.ProductCategoryTableView;
import com.torinosrc.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>ProductCategoryTable</code></b>
* <p/>
* ProductCategoryTable的具体实现
* <p/>
* <b>Creation Time:</b> 2018-08-30 10:51:55.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface ProductCategoryTableService extends BaseService<ProductCategoryTableView> {
    public Page<ProductCategoryTableView> getEntitiesByContion(ProductCategoryTableView productCategoryTableView, int currentPage, int pageSize);
}