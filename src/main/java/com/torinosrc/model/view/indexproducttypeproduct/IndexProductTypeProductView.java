/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.indexproducttypeproduct;

import com.torinosrc.model.entity.indexproducttypeproduct.IndexProductTypeProduct;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.view.product.ProductView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>IndexProductTypeProductView</code></b>
 * <p/>
 * IndexProductTypeProduct的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-27 11:04:15.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class IndexProductTypeProductView extends IndexProductTypeProduct implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    private ProductView productView;

    private String indexProductTypeName;

    public String getIndexProductTypeName() {
        return indexProductTypeName;
    }

    public void setIndexProductTypeName(String indexProductTypeName) {
        this.indexProductTypeName = indexProductTypeName;
    }

    public ProductView getProductView() {
        return productView;
    }

    public void setProductView(ProductView productView) {
        this.productView = productView;
    }
}
