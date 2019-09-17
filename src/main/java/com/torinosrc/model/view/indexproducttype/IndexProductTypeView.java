/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.indexproducttype;

import com.torinosrc.model.entity.indexproducttype.IndexProductType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <b><code>IndexProductTypeView</code></b>
 * <p/>
 * IndexProductType的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-27 11:03:01.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class IndexProductTypeView extends IndexProductType implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "商品数量")
    private Integer productCount;

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }
}
