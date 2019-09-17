/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.productfreereceive;

import com.torinosrc.model.entity.productfreereceive.ProductFreeReceive;
import com.torinosrc.model.entity.productfreereceivedetail.ProductFreeReceiveDetail;
import com.torinosrc.model.view.productfreereceivedetail.ProductFreeReceiveDetailView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.io.Serializable;

/**
 * <b><code>ProductFreeReceiveView</code></b>
 * <p/>
 * ProductFreeReceive的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-27 19:54:27.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class ProductFreeReceiveView extends ProductFreeReceive implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

//    @ApiModelProperty(value = "免费领订单的状态 0是分享免费领  1是免费领帮助成功 2是订单完成")
    private  Integer status=0;

     private ProductFreeReceiveDetailView productFreeReceiveDetail;



    public ProductFreeReceiveDetailView getProductFreeReceiveDetail() {
        return productFreeReceiveDetail;
    }

    public void setProductFreeReceiveDetail(ProductFreeReceiveDetailView productFreeReceiveDetail) {
        this.productFreeReceiveDetail = productFreeReceiveDetail;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
