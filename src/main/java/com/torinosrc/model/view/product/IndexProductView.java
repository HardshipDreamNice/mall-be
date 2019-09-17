package com.torinosrc.model.view.product;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class IndexProductView implements Serializable {

    @ApiModelProperty(value = "首页展示的热门活动商品")
    List<ProductView> hotProducts;

    @ApiModelProperty(value = "首页展示的真爱新品")
    List<ProductView> newProducts;

    @ApiModelProperty(value = "首页展示的精选优品")
    List<ProductView> chosenProducts;

    public List<ProductView> getHotProducts() {
        return hotProducts;
    }

    public void setHotProducts(List<ProductView> hotProducts) {
        this.hotProducts = hotProducts;
    }

    public List<ProductView> getNewProducts() {
        return newProducts;
    }

    public void setNewProducts(List<ProductView> newProducts) {
        this.newProducts = newProducts;
    }

    public List<ProductView> getChosenProducts() {
        return chosenProducts;
    }

    public void setChosenProducts(List<ProductView> chosenProducts) {
        this.chosenProducts = chosenProducts;
    }
}
