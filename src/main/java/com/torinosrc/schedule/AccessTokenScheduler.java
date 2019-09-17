package com.torinosrc.schedule;

import com.torinosrc.service.accesstoken.AccessTokenService;
import com.torinosrc.service.weixin.WechatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * access_token定时获取
 */
@Component
public class AccessTokenScheduler {

    private static final Logger LOG = LoggerFactory
            .getLogger(OrderScheduler.class);


    @Autowired
    private AccessTokenService accessTokenService;

       @Scheduled(fixedRate =90*60*1000)
    public void updateAccessToken() {
//        System.out.println("run access_token");
//        wechatService.createOrUpdateAccessToken();
        accessTokenService.createOrUpdateAccessToken();
    }

}
