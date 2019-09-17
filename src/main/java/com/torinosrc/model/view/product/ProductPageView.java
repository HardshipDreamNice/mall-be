package com.torinosrc.model.view.product;

import java.util.List;

public class ProductPageView {

    Long totalNum;
    Long totalElements;

    List<ProductView> productViewList;

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public List<ProductView> getProductViewList() {
        return productViewList;
    }

    public void setProductViewList(List<ProductView> productViewList) {
        this.productViewList = productViewList;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }
}
