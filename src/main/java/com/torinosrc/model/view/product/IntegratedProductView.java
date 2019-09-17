package com.torinosrc.model.view.product;

import com.torinosrc.model.view.shopproduct.ShopProductView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(value = "获取单个商品时返回的实体")
public class IntegratedProductView {

    private ProductView productView;

    private ShopProductView shopProductView;

    @ApiModelProperty(value = "是否是上架商品 0：不是 1：是")
    private Integer isShopProduct;

    public ProductView getProductView() {
        return productView;
    }

    public void setProductView(ProductView productView) {
        this.productView = productView;
    }

    public ShopProductView getShopProductView() {
        return shopProductView;
    }

    public void setShopProductView(ShopProductView shopProductView) {
        this.shopProductView = shopProductView;
    }

    public Integer getIsShopProduct() {
        return isShopProduct;
    }

    public void setIsShopProduct(Integer isShopProduct) {
        this.isShopProduct = isShopProduct;
    }
}
