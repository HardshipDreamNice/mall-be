package com.swagger.sawggerui;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.torinosrc.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Arrays.asList(securitySchemes()))
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("广州都灵源链信息科技公司接口文档")
                .description("广州都灵源链信息科技公司接口文档，如果你不是该公司项目开发人员，请不要随意使用。")
                .termsOfServiceUrl("http://www.torinosrc.com/")
                .version("1.0")
                .build();
    }

    /**
     * 添加Authorization ApiKey字段
     * @return SecurityScheme
     */
    private SecurityScheme securitySchemes() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    /**
     * 判断哪些api需要带Security参数
     *
     * @return List<SecurityContext>
     */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/api/.*"))
                .build());
        return securityContexts;
    }

    /**
     *
     * @return
     */
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }
}