package com.seed.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author 77286
 * @version 1.0
 * @description: Swagger配置
 * @date 2024/1/5 21:04
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.seed.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("LocaJu", "https://github.com/LocaJu", "772866144@qq.com");
        return new ApiInfoBuilder()
                .title("SeedBlog接口文档")
                .description("SeedBlog接口文档")
                .version("1.0")
                .contact(contact)
                .build();
    }
}

