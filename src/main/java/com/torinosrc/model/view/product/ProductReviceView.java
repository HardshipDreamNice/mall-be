package com.torinosrc.model.view.product;

import com.torinosrc.model.entity.product.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel
public class ProductReviceView extends Product implements Serializable {
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 接收参数的view
     */

    @ApiModelProperty(value = "使用范围 0:全部商品 1：指定商品")
    private Integer productUsingRange;

    private List<Product> products;

    public Integer getProductUsingRange() {
        return productUsingRange;
    }

    public void setProductUsingRange(Integer productUsingRange) {
        this.productUsingRange = productUsingRange;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
