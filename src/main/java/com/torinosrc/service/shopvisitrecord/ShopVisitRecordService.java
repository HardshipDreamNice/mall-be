/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.shopvisitrecord;

import com.torinosrc.model.view.shopvisitrecord.ShopVisitRecordView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>ShopVisitRecord</code></b>
* <p/>
* ShopVisitRecord的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-19 18:21:46.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface ShopVisitRecordService extends BaseService<ShopVisitRecordView> {

    ShopVisitRecordView saveEntityIfNoExistAndUpdateIfExist(ShopVisitRecordView shopVisitRecordView);

    ShopVisitRecordView getEntityUpdateLastestByUserIdAndEnabled(Long userId, Integer enbaled);

}
