/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.indexproducttype;

import com.torinosrc.model.view.indexproducttype.IndexProductTypeView;
import com.torinosrc.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>IndexProductType</code></b>
* <p/>
* IndexProductType的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-27 11:03:01.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface IndexProductTypeService extends BaseService<IndexProductTypeView> {

    public Page<IndexProductTypeView> getEntitiesByParmsHaveProductCount(IndexProductTypeView indexProductTypeView, int currentPage, int pageSize);
}
