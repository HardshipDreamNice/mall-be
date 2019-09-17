/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.banner;

import com.torinosrc.model.entity.banner.Banner;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * <b><code>BannerView</code></b>
 * <p/>
 * Banner的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-26 20:40:46.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class BannerView extends Banner implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    private Integer sortType;

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }
}
