package com.torinosrc.service.usersignlog.impl;

import com.torinosrc.TorinosrcSpringBootBeApplication;
import com.torinosrc.model.view.usersignlog.UserSignLogView;
import com.torinosrc.service.usersignlog.UserSignLogService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TorinosrcSpringBootBeApplication.class)
public class UserSignServiceImplTest {

    @Autowired
    private UserSignLogService userSignLogService;

    @Test
    public void testSaveUserSignLog() {
        long signUserId = 15L;

        UserSignLogView userSignLogView = new UserSignLogView();
        userSignLogView.setSignUserId(signUserId);

        UserSignLogView userSignLogViewResult = userSignLogService.saveUserSignLog(userSignLogView);

        Assert.assertNotNull(userSignLogViewResult);
    }

    @Test
    public void testSaveHelpUserSignLog() {

        long signTime = 1543483404568L;
        long signUserId = 15L;
        long helpUserId = 11L;

        UserSignLogView userSignLogView = new UserSignLogView();
        userSignLogView.setSignTime(signTime);
        userSignLogView.setSignUserId(signUserId);
        userSignLogView.setHelpUserId(helpUserId);

        UserSignLogView userSignLogViewResult = userSignLogService.saveHelpSignLog(userSignLogView);

        Assert.assertNotNull(userSignLogViewResult);
    }

    @Test
    public void testGetEntityBySignUserIdAndSignTime() {
        long signTime = 1543559325000L;
        long signUserId = 11L;
        UserSignLogView userSignLogViewResult = userSignLogService.getEntityBySignUserIdAndSignTime(signUserId, signTime);
        Assert.assertNotNull(userSignLogViewResult);
    }
}