package com.torinosrc.service.usercoupon.impl;

import com.torinosrc.TorinosrcSpringBootBeApplication;
import com.torinosrc.model.view.coupon.CouponView;
import com.torinosrc.model.view.usercoupon.UserCouponView;
import com.torinosrc.service.coupon.CouponService;
import com.torinosrc.service.usercoupon.UserCouponService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TorinosrcSpringBootBeApplication.class)
public class CouponServiceImplTest {

    @Autowired
    private UserCouponService userCouponService;

    @Test
    public void testSaveEntity(){

        Long couponId = 1L;
        Long userId = 15L;

        UserCouponView userCouponView = new UserCouponView();
        userCouponView.setCouponId(couponId);
        userCouponView.setUserId(userId);

        UserCouponView userCouponViewResult = userCouponService.saveEntity(userCouponView);

        Assert.assertNotNull(userCouponViewResult);
    }
}