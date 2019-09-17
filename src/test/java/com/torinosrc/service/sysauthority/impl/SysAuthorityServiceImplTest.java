package com.torinosrc.service.sysauthority.impl;

import com.torinosrc.TorinosrcSpringBootBeApplication;
import com.torinosrc.model.view.sysauthority.SysAuthorityView;
import com.torinosrc.service.sysauthority.SysAuthorityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by lvxin on 2018/4/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TorinosrcSpringBootBeApplication.class)
//@WebAppConfiguration
public class SysAuthorityServiceImplTest {

    @Autowired
    SysAuthorityService sysAuthorityService;

    private String condition = "";

    @Before
    public void setup(){
//        this.condition = "{\n" +
//                "\t\t\"where\": {\n" +
//                "\t\t\t\"$or\": [\n" +
//                "\t\t\t\t{\n" +
//                "\t\t\t\t\t\"$and\": [\n" +
//                "\t\t\t\t\t\t{\n" +
//                "\t\t\t\t\t\t\t\"id\": {\"$eq\": 1}\n" +
//                "\t\t\t\t\t\t},\n" +
//                "\t\t\t\t\t\t{\n" +
//                "\t\t\t\t\t\t\t\"id\": {\"$ne\": \"4\"}\n" +
//                "\t\t\t\t\t\t}\n" +
//                "\t\t\t\t\t]\n" +
//                "\t\t\t\t},\n" +
//                "\t\t\t\t{\n" +
//                "\t\t\t\t\t\"name\": {\"$like\": \"es\"}\n" +
//                "\t\t\t\t}\n" +
//                "\t\t\t]\n" +
//                "\t\t},\n" +
//                "\t\t\"orderBy\": {\n" +
//                "\t\t\t\"1\":\"enabled:asc\",\n" +
//                "\t\t\t\"2\":\"id:asc\"\n" +
//                "\t\t}\n" +
//                "\t}";

        this.condition = "{\n" +
                "\t\t\"where\": {\n" +
                "\t\t\t\"$or\": [\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"$and\": [\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"id\": {\"$ne\": 1}\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"id\": {\"$ne\": \"4\"}\n" +
                "\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t]\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"id\": {\"$in\": \"1,2\"}\n" +
                "\t\t\t\t}\n" +
                "\t\t\t]\n" +
                "\t\t},\n" +
                "\t\t\"orderBy\": {\n" +
                "\t\t\t\"1\":\"enabled:asc\",\n" +
                "\t\t\t\"2\":\"id:asc\"\n" +
                "\t\t}\n" +
                "\t}";
    }

    @Test
    public void getEntitiesByParms() throws Exception {
//        System.out.println(condition);
        SysAuthorityView sysAuthorityView = new SysAuthorityView();
        sysAuthorityView.setCondition(condition);
        Page<SysAuthorityView> sysAuthorityViews = sysAuthorityService.getEntitiesByParms(sysAuthorityView,0,10);
//        System.out.println("Start: ");
//        System.out.println("totalPages: " + sysAuthorityViews.getTotalPages());
        List<SysAuthorityView> sysAuthorityViewList = sysAuthorityViews.getContent();
        for(SysAuthorityView s : sysAuthorityViewList){
            System.out.println(s.toString());
        }
    }

}