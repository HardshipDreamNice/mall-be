/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.customerconsignee;

import com.torinosrc.model.entity.customerconsignee.CustomerConsignee;
import com.torinosrc.model.view.customerconsignee.CustomerConsigneeView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <b><code>CustomerConsignee</code></b>
* <p/>
* CustomerConsignee的具体实现
* <p/>
* <b>Creation Time:</b> 2018-06-06 11:29:50.
*
* @author ${model.author}
* @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public interface CustomerConsigneeService extends BaseService<CustomerConsigneeView> {
    public List<CustomerConsigneeView> findByUserId(Long userId);
    public void updateAutoaAddr (CustomerConsigneeView customerConsigneeView);
    public CustomerConsigneeView findByUserIdIsAutoaAddr(Long userId);
}
