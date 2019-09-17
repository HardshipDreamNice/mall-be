package com.torinosrc.model.view.order;

import java.util.List;

public class OrderPageView {
    private Long totalNum;
    private List<OrderView> orderViewList;

    public List<OrderView> getOrderViewList() {
        return orderViewList;
    }

    public void setOrderViewList(List<OrderView> orderViewList) {
        this.orderViewList = orderViewList;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }
}
