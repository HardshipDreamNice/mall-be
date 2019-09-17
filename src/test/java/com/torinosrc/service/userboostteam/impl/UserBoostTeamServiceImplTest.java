package com.torinosrc.service.userboostteam.impl;

import com.torinosrc.TorinosrcSpringBootBeApplication;
import com.torinosrc.dao.productdetailsnapshot.ProductDetailSnapshotDao;
import com.torinosrc.dao.productsnapshot.ProductSnapshotDao;
import com.torinosrc.model.entity.orderdetail.OrderDetail;
import com.torinosrc.model.view.boostteam.BoostTeamView;
import com.torinosrc.model.view.order.OrderView;
import com.torinosrc.model.view.shopaccount.ShopAccountView;
import com.torinosrc.model.view.userboostteam.UserBoostTeamView;
import com.torinosrc.schedule.OrderScheduler;
import com.torinosrc.service.order.OrderService;
import com.torinosrc.service.shopaccount.ShopAccountService;
import com.torinosrc.service.userboostteam.UserBoostTeamService;
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
public class UserBoostTeamServiceImplTest {
    @Autowired
    private UserBoostTeamService userBoostTeamService;

    @Test
    public void testSaveUserBoostTeam() {

        Long userId = 15L;
        Long boostTeamId = 4L;
        Long orderId = 192L;

        UserBoostTeamView userBoostTeamView = new UserBoostTeamView();
        userBoostTeamView.setUserId(userId);
        userBoostTeamView.setBoostTeamId(boostTeamId);
        userBoostTeamView.setOrderId(orderId);

        UserBoostTeamView userBoostTeamViewResult = userBoostTeamService.saveOrReturnUserBoostTeam(userBoostTeamView);

        Assert.assertNotNull(userBoostTeamViewResult);
    }

}
