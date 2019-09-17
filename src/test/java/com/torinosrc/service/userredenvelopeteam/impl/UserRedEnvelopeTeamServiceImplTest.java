package com.torinosrc.service.userredenvelopeteam.impl;

import com.torinosrc.TorinosrcSpringBootBeApplication;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.model.entity.userredenvelopeteam.UserRedEnvelopeTeam;
import com.torinosrc.model.view.redenvelopeteam.RedEnvelopeTeamView;
import com.torinosrc.model.view.userredenvelopeteam.UserRedEnvelopeTeamView;
import com.torinosrc.service.redenvelopeteam.RedEnvelopeTeamService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TorinosrcSpringBootBeApplication.class)
public class UserRedEnvelopeTeamServiceImplTest {

    @Autowired
    private UserRedEnvelopeTeamServiceImpl userRedEnvelopeTeamService;

    @Test
    public void testCreateOrReturnRedEnvelopeTeam() {

        Long userId = 14L;
        Long redEnvelopeTeamId = 1L;

        UserRedEnvelopeTeamView userRedEnvelopeTeamView = new UserRedEnvelopeTeamView();
        userRedEnvelopeTeamView.setUserId(userId);
        userRedEnvelopeTeamView.setRedEnvelopeTeamId(redEnvelopeTeamId);

        try {
            UserRedEnvelopeTeamView userRedEnvelopeTeamViewResult = userRedEnvelopeTeamService.saveUserRedEnvelopeTeam(userRedEnvelopeTeamView);
            Assert.assertNotNull(userRedEnvelopeTeamViewResult);
        } catch (TorinoSrcServiceException e) {
            e.printStackTrace();
        }
    }
}
