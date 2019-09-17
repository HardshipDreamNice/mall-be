package com.torinosrc.service.membershipgrade;

import com.torinosrc.TorinosrcSpringBootBeApplication;
import com.torinosrc.dao.indexproducttypeproduct.IndexProductTypeProductDao;
import com.torinosrc.model.entity.indexproducttypeproduct.IndexProductTypeProduct;
import com.torinosrc.model.entity.membershipgrade.MembershipGrade;
import com.torinosrc.model.view.membershipgrade.MembershipGradeView;
import com.torinosrc.model.view.order.MembershipOrderView;
import com.torinosrc.model.view.order.OrderView;
import com.torinosrc.service.order.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TorinosrcSpringBootBeApplication.class)
public class MembershipGradeServiceImplTest {

    @Autowired
    private MembershipGradeService membershipGradeService;

    @Autowired
    private OrderService orderService;

    @Test
    public void testSaveMemberShipGrade(){

        MembershipGradeView membershipGradeView = new MembershipGradeView();
        String name = "一级会员";
        long commission = 100L;
        long price = 19800L;
        long discount = 95L;
        int grade = 1;
        int percent1 = 20;
        int percent2 = 30;
        int percent3 = 50;

        membershipGradeView.setName(name);
        membershipGradeView.setCommission(commission);
        membershipGradeView.setPrice(price);
        membershipGradeView.setDiscount(discount);
        membershipGradeView.setGrade(grade);
        membershipGradeView.setPercent1(percent1);
        membershipGradeView.setPercent2(percent2);
        membershipGradeView.setPercent3(percent3);

        MembershipGradeView membershipGradeViewResult = membershipGradeService.saveEntity(membershipGradeView);

        Assert.assertNotNull(membershipGradeViewResult);
    }

    @Test
    public void testSaveMembershipOrderAndOrderDetail() {

        long price = 1L;
        long membershipGradeId = 1L;
        MembershipGradeView membershipGradeView = new MembershipGradeView();
        membershipGradeView.setId(membershipGradeId);
        membershipGradeView.setPrice(price);

        long shopId = 9L;
        long userId = 15L;
        String customerConsigneeString = "12121212";
        MembershipOrderView membershipOrderView = new MembershipOrderView();
        membershipOrderView.setShopId(shopId);
        membershipOrderView.setUserId(userId);
        membershipOrderView.setCustomerConsigneeString(customerConsigneeString);
        membershipOrderView.setMembershipGradeView(membershipGradeView);

        OrderView orderView = orderService.saveMembershipOrderAndOrderDetail(membershipOrderView);

        Assert.assertNotNull(orderView);
    }


}