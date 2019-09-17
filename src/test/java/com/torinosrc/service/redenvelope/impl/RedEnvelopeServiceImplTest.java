package com.torinosrc.service.redenvelope.impl;

import com.torinosrc.TorinosrcSpringBootBeApplication;
import com.torinosrc.dao.productdetailsnapshot.ProductDetailSnapshotDao;
import com.torinosrc.dao.productsnapshot.ProductSnapshotDao;
import com.torinosrc.model.entity.orderdetail.OrderDetail;
import com.torinosrc.model.entity.redenvelope.RedEnvelope;
import com.torinosrc.model.entity.redenvelopeteam.RedEnvelopeTeam;
import com.torinosrc.model.view.order.OrderView;
import com.torinosrc.model.view.redenvelope.RedEnvelopeView;
import com.torinosrc.model.view.redenvelopeteam.RedEnvelopeTeamView;
import com.torinosrc.model.view.shopaccount.ShopAccountView;
import com.torinosrc.schedule.OrderScheduler;
import com.torinosrc.service.order.OrderService;
import com.torinosrc.service.redenvelope.RedEnvelopeService;
import com.torinosrc.service.redenvelopeteam.RedEnvelopeTeamService;
import com.torinosrc.service.shopaccount.ShopAccountService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TorinosrcSpringBootBeApplication.class)
public class RedEnvelopeServiceImplTest {
    @Autowired
    private RedEnvelopeService redEnvelopeService;

    @Test
    public void testSaveRedVelope() {

        String name = "现金红包，说现金你就信 ？";
        Integer validDay = 2;
        // 需要多少人一起拆红包
        Integer takeNumber = 4;
        // 0：优惠券红包 1：现金红包
        Integer type = 0;
        Long couponCategoryId = 5L;
        Long expiredTime = 1544198400000L;

        RedEnvelopeView redEnvelopeView = new RedEnvelopeView();
        redEnvelopeView.setName(name);
        redEnvelopeView.setValidDay(validDay);
        redEnvelopeView.setTakeNumber(takeNumber);
        redEnvelopeView.setType(type);
        redEnvelopeView.setCouponCategoryId(couponCategoryId);
        redEnvelopeView.setExpiredTime(expiredTime);

        RedEnvelopeView redEnvelopeViewResult = redEnvelopeService.saveEntity(redEnvelopeView);

        Assert.assertNotNull(redEnvelopeViewResult.getId());
    }
}
