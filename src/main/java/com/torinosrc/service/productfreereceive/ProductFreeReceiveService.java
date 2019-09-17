/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.productfreereceive;

import com.torinosrc.model.view.productfreereceive.ProductFreeReceiveView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>ProductFreeReceive</code></b>
* <p/>
* ProductFreeReceive的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-27 19:54:27.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface ProductFreeReceiveService extends BaseService<ProductFreeReceiveView> {

       //免费领的商品状态及商品详情。
       ProductFreeReceiveView getProductFreeReceives(long userId,long productFreeReceiveId);
}
