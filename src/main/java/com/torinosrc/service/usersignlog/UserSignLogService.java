/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.usersignlog;

import com.torinosrc.model.view.usersignlog.UserSignLogView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>UserSignLog</code></b>
* <p/>
* UserSignLog的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-29 16:44:39.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface UserSignLogService extends BaseService<UserSignLogView> {

    /**
     * 用户点击签到
     * 添加签到记录
     * @param userSignLogView
     * @return
     */
    UserSignLogView saveUserSignLog(UserSignLogView userSignLogView);

    /**
     * 用户助力签到
     * 更新签到记录，添加助签者id
     * @param userSignLogView
     * @return
     */
    UserSignLogView saveHelpSignLog(UserSignLogView userSignLogView);

    /**
     * 根据签到用户Id及签到时间获取当天的签到记录
     * @param signUserId
     * @param signTime
     * @return
     */
    UserSignLogView getEntityBySignUserIdAndSignTime(Long signUserId, Long signTime);

}
