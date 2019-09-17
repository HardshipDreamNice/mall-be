/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.userproductfreereceive;

import com.torinosrc.model.view.userproductfreereceive.UserProductFreeReceiveView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>UserProductFreeReceive</code></b>
* <p/>
* UserProductFreeReceive的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-27 19:45:53.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface UserProductFreeReceiveService extends BaseService<UserProductFreeReceiveView> {

     //用户点击分享免费领接口
     UserProductFreeReceiveView insertUserProductFreeReceiveEntity(UserProductFreeReceiveView userProductFreeReceiveView);

     // 其他人帮忙免费领接口
      void otherHelpProductFreeReceivesUpdate(UserProductFreeReceiveView userProductFreeReceiveView);

}
