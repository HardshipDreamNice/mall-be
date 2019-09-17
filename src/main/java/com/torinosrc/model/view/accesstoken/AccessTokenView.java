/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.accesstoken;

import com.torinosrc.model.entity.accesstoken.AccessToken;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * <b><code>AccessTokenView</code></b>
 * <p/>
 * AccessToken的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-08-09 19:42:30.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class AccessTokenView extends AccessToken implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

}
