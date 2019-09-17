package com.torinosrc.service.messageTemplate;

import com.torinosrc.TorinosrcSpringBootBeApplication;
import com.torinosrc.dao.coupon.CouponDao;
import com.torinosrc.model.entity.coupon.Coupon;
import com.torinosrc.model.view.coupon.CouponView;
import com.torinosrc.service.coupon.CouponService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TorinosrcSpringBootBeApplication.class)
public class MessageTemplateServiceImplTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponDao couponDao;

    @Test
    public void testPayOrderTempalte(){
        String prepayId = "wx11175805403404daabb168683959213223";
    }
}