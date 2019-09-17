/*
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <b><code>TorinosrcSpringBootBeApplication</code></b>
 * <p/>
 * 启动文件
 * <p/>
 * <b>Creation Time:</b> 2018/04/09 21:18.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@SpringBootApplication(scanBasePackages={"com.torinosrc","com.swagger"})
@EnableSwagger2
@EnableScheduling
public class TorinosrcSpringBootBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TorinosrcSpringBootBeApplication.class, args);
	}
}
