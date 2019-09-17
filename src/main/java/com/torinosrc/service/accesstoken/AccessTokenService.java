/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.accesstoken;

import com.torinosrc.model.view.accesstoken.AccessTokenView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>AccessToken</code></b>
* <p/>
* AccessToken的具体实现
* <p/>
* <b>Creation Time:</b> 2018-08-09 19:42:30.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface AccessTokenService extends BaseService<AccessTokenView> {

    /**
     * 获取 accessToken
     * 如果数据库中没有 accessToken，则获取 accessToken 并插入到数据库
     * 如果数据库中 accessToken 的状态为 0，则重新获取 accessToken
     * 如果数据库中 accessToken 的状态为 1，直接返回 accessToken
     * @return
     */
    String getAccessToken();

    /**
     * 如果没有 accessToken，则新建
     * 如果已有 accessToken，则更新
     * @return
     */
    String createOrUpdateAccessToken();

}
