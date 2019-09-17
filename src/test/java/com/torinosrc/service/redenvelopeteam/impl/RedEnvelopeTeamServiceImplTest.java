package com.torinosrc.service.redenvelopeteam.impl;

import com.torinosrc.TorinosrcSpringBootBeApplication;
import com.torinosrc.model.entity.redenvelopeteam.RedEnvelopeTeam;
import com.torinosrc.model.view.redenvelope.RedEnvelopeView;
import com.torinosrc.model.view.redenvelopeteam.RedEnvelopeTeamView;
import com.torinosrc.service.redenvelope.RedEnvelopeService;
import com.torinosrc.service.redenvelopeteam.RedEnvelopeTeamService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TorinosrcSpringBootBeApplication.class)
public class RedEnvelopeTeamServiceImplTest {
    @Autowired
    private RedEnvelopeTeamService redEnvelopeTeamService;

    @Test
    public void testCreateOrReturnRedEnvelopeTeam() {

        Long userId = 11L;
        Long redEnvelopeId = 1L;

        RedEnvelopeTeamView redEnvelopeTeamView = new RedEnvelopeTeamView();
        redEnvelopeTeamView.setUserId(userId);
        redEnvelopeTeamView.setRedEnvelopeId(redEnvelopeId);

        RedEnvelopeTeamView redEnvelopeTeamViewResult = redEnvelopeTeamService.createOrReturnRedEnvelopeTeam(redEnvelopeTeamView);

        Assert.assertNotNull(redEnvelopeTeamViewResult.getId());
    }

    @Test
    public void testGetRedEnvelopeTeamByUserIdAndRedEnvelopeId() {

        Long userId = 11L;
        Long redEnvelopeId = 1L;

        RedEnvelopeTeamView redEnvelopeTeamView = redEnvelopeTeamService.getRedEnvelopeTeamByUserIdAndRedEnvelopeId(userId, redEnvelopeId);

        Assert.assertNotNull(redEnvelopeTeamView);
    }
}
