package com.torinosrc.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.*;
import com.torinosrc.response.filter.*;

/**
 * 增加自定义的ReturnValueHandler到spring返回内容处理器中
 */
@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public JsonReturnHandler JsonReturnHandler(){
        return new JsonReturnHandler();
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(JsonReturnHandler());
    }
}
