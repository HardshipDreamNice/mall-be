package com.torinosrc.service.couponproduct.impl;

import com.torinosrc.TorinosrcSpringBootBeApplication;
import com.torinosrc.dao.coupon.CouponDao;
import com.torinosrc.model.entity.coupon.Coupon;
import com.torinosrc.model.entity.couponproduct.CouponProduct;
import com.torinosrc.model.view.coupon.CouponView;
import com.torinosrc.model.view.product.ProductView;
import com.torinosrc.service.coupon.CouponService;
import com.torinosrc.service.couponproduct.CouponProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TorinosrcSpringBootBeApplication.class)
public class CouponProductServiceImplTest {

    @Autowired
    private CouponProductService couponProductService;

    @Test
    public void testGetProductsByCouponId(){

        Long couponId = 1L;

        List<ProductView> productViews = couponProductService.getProductsByCouponId(couponId);

        Assert.assertNotNull(productViews);
    }
}