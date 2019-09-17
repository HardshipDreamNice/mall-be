package com.torinosrc.service.coupon.impl;

import com.torinosrc.TorinosrcSpringBootBeApplication;
import com.torinosrc.dao.coupon.CouponDao;
import com.torinosrc.dao.indexproducttypeproduct.IndexProductTypeProductDao;
import com.torinosrc.model.entity.coupon.Coupon;
import com.torinosrc.model.entity.indexproducttypeproduct.IndexProductTypeProduct;
import com.torinosrc.model.view.coupon.CouponView;
import com.torinosrc.service.coupon.CouponService;
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
public class CouponServiceImplTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponDao couponDao;

    @Test
    public void testSaveEntity(){

        String couponName = "满100减50";
        int totalNumber = 100;
        // 优惠券类型 0 是满减 1 是折扣
        int couponType = 0;
        // 优惠 单位为 分 或 折（如 98折）
        int discountAmount = 5000;
        // 使用门槛，单位为 分
        int usableAmount = 10000;
        // 会员等级 id
        long membershipGradeId = 1L;
        // 可领取数量
        int availableNumber = 3;
        // 有效期类型 0 是固定日期 1 是领到券当日开始 N 天内有效 2 是领到券次日开始 N 天内有效
        int validPeriodType = 0;
        long startTime = 1543420800000L;
        long endTime = 1543420800000L;
        // 适用范围 0：全店商品 1：指定商品 2：指定商品不可用
        int usableScope = 0;
        String useIntroduction = "使用说明：随便用";
        long couponCategoryId = 1L;
        // 剩余数量
        int remainingNumber = 100;

        CouponView couponView = new CouponView();
        couponView.setName(couponName);
        couponView.setTotalNumber(totalNumber);
        couponView.setCouponType(couponType);
        couponView.setDiscountAmount(discountAmount);
        couponView.setUsableAmount(usableAmount);
        couponView.setMembershipGradeId(membershipGradeId);
        couponView.setAvailableNumber(availableNumber);
        couponView.setValidPeriodType(validPeriodType);
        couponView.setStartTime(startTime);
        couponView.setEndTime(endTime);
        couponView.setUsableScope(usableScope);
        couponView.setUseIntroduction(useIntroduction);
        couponView.setCouponCategoryId(couponCategoryId);
        couponView.setRemainingNumber(remainingNumber);

        CouponView couponViewResult = couponService.saveEntity(couponView);

        Assert.assertNotNull(couponViewResult);
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testUpdateCouponRemainingNumberMinusOne() {

        Long couponId = 1L;
        Coupon couponBeforeUpdate = couponDao.getOne(couponId);
        int remainingNumberBeforeUpdate = couponBeforeUpdate.getRemainingNumber();

        couponDao.updateCouponRemainingNumberMinusOne(couponId);

        Coupon couponAfterUpdate = couponDao.getOne(couponId);
        int remainingNumberAfterUpdate = couponAfterUpdate.getRemainingNumber();
        int remainingNumberExpect = remainingNumberBeforeUpdate - 1;

        System.out.println("remainingNumberBeforeUpdate: "  + remainingNumberBeforeUpdate);
        System.out.println("remainingNumberAfterUpdate: "  + remainingNumberAfterUpdate);
        System.out.println("remainingNumberExpect: "  + remainingNumberExpect);

        Assert.assertEquals(remainingNumberExpect, remainingNumberAfterUpdate);
    }
}