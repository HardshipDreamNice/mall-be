/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.indexproducttypeproduct;

import com.torinosrc.model.entity.indexproducttype.IndexProductType;
import com.torinosrc.model.entity.indexproducttypeproduct.IndexProductTypeProduct;
import com.torinosrc.model.view.indexproducttypeproduct.IndexProductTypeProductView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>IndexProductTypeProduct</code></b>
* <p/>
* IndexProductTypeProduct的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-27 11:04:15.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface IndexProductTypeProductService extends BaseService<IndexProductTypeProductView> {

    List<IndexProductTypeProduct> getEntitiesByIndexProductTypeIds(String indexProductTypeIds);

}
