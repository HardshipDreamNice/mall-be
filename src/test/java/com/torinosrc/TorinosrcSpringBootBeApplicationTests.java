package com.torinosrc;

import com.torinosrc.service.weixin.WechatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TorinosrcSpringBootBeApplicationTests {

	@Autowired
	WechatService wechatService;
	@Test
	public void contextLoads() {

		String result = "<xml><appid><![CDATA[wxf3fd67da75d45f08]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[35]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1482420382]]></mch_id><nonce_str><![CDATA[664f80ad82d041c5904942b72197c649]]></nonce_str><openid><![CDATA[oHMkP0VcPuNvqCnm7l60f0Ihi7dk]]></openid><out_trade_no><![CDATA[152947109868980242]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[9A58CF9AFADC12B278EFBA0E131CEC21]]></sign><time_end><![CDATA[20180620195908]]></time_end><total_fee>35</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4200000147201806209446976573]]></transaction_id></xml>" ;
		wechatService.payStatus(result);

	}

}
