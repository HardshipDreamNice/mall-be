/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.contenttype;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.model.view.contenttype.ContentTypeView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

/**
* <b><code>ContentType</code></b>
* <p/>
* ContentType的具体实现
* <p/>
* <b>Creation Time:</b> 2018-05-28 12:08:05.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface ContentTypeService extends BaseService<ContentTypeView> {

    //递归删除每个子节点
    public void recursionDelNode(Long id) throws TorinoSrcServiceException;
    //子查询出顶层id，然后通过顶层id级联查询子
//    public ContentTypeView recursionFindParent(Long id);
}
