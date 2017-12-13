package com.bestjlb.demo.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by yydx811 on 2017/10/26.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bestjlb.demo.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .globalResponseMessage(
                        RequestMethod.GET,
                        Lists.newArrayList(
                                new ResponseMessageBuilder()
                                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                        .message("Internal server error!")
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(HttpStatus.FORBIDDEN.value())
                                        .message("Forbidden!")
                                        .build()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("JLB Demo api")
                .description("This is for developers!")
                .termsOfServiceUrl("http://www.zhixuezhen.com")
                .contact("jlb@bestjlb.com")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .build();
    }
}
