package com.torinosrc.model.view.order;

import com.torinosrc.model.view.membershipgrade.MembershipGradeView;
import io.swagger.annotations.ApiModelProperty;

public class MembershipOrderView {

    private MembershipGradeView membershipGradeView;

    private Long shopId;

    private Long userId;

    @ApiModelProperty(value = "收货地址对象的String")
    private String customerConsigneeString;

    public MembershipGradeView getMembershipGradeView() {
        return membershipGradeView;
    }

    public void setMembershipGradeView(MembershipGradeView membershipGradeView) {
        this.membershipGradeView = membershipGradeView;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCustomerConsigneeString() {
        return customerConsigneeString;
    }

    public void setCustomerConsigneeString(String customerConsigneeString) {
        this.customerConsigneeString = customerConsigneeString;
    }
}
