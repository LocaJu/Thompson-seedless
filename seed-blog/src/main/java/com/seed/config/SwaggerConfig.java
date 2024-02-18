package com.seed.config;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 77286
 * @version 1.0
 * @description: Swagger配置
 * @date 2024/1/5 21:04
 */
@Configuration
public class SwaggerConfig {
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.seed.controller"))
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        Contact contact = new Contact("LocaJu", "https://github.com/LocaJu", "772866144@qq.com");
//        return new ApiInfoBuilder()
//                .title("SeedBlog接口文档")
//                .description("SeedBlog接口文档")
//                .version("1.0")
//                .contact(contact)
//                .build();
//    }

    @Bean
    public Docket createRestApi() {
        // 建造者模式构建Docket
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .groupName("DeepMez")
                .select()
                // 需要放出的接口
                .apis(RequestHandlerSelectors.basePackage("com.seed.controller"))
                // 过滤
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("描述")
                .contact(new Contact("root", "demo.com", "easy@163.com"))
                .version("1.0")
                .build();
    }


//    @Bean
//    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier, ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier, EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties, WebEndpointProperties webEndpointProperties, Environment environment) {
//        List<ExposableEndpoint<?>> allEndpoints = new ArrayList();
//        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
//        allEndpoints.addAll(webEndpoints);
//        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
//        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
//        String basePath = webEndpointProperties.getBasePath();
//        EndpointMapping endpointMapping = new EndpointMapping(basePath);
//        boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);
//        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping);
//    }
//
//    private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment, String basePath) {
//        return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
//    }

//    @Bean
//    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(
//            WebEndpointsSupplier webEndpointsSupplier,
//            ServletEndpointsSupplier servletEndpointsSupplier,
//            ControllerEndpointsSupplier controllerEndpointsSupplier,
//            EndpointMediaTypes endpointMediaTypes,
//            CorsEndpointProperties corsProperties,
//            WebEndpointProperties webEndpointProperties,
//            Environment environment) {
//        List<ExposableEndpoint<?>> allEndpoints = new ArrayList();
//        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
//        allEndpoints.addAll(webEndpoints);
//        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
//        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
//        String basePath = webEndpointProperties.getBasePath();
//        EndpointMapping endpointMapping = new EndpointMapping(basePath);
//        boolean shouldRegisterLinksMapping =
//                this.shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);
//        return new WebMvcEndpointHandlerMapping(
//                endpointMapping,
//                webEndpoints,
//                endpointMediaTypes,
//                corsProperties.toCorsConfiguration(),
//                new EndpointLinksResolver(allEndpoints, basePath),
//                shouldRegisterLinksMapping
//        );
//    }
//
//    private boolean shouldRegisterLinksMapping(
//            WebEndpointProperties webEndpointProperties, Environment environment, String basePath) {
//        return webEndpointProperties.getDiscovery().isEnabled()
//                && (StringUtils.hasText(basePath)
//                || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
//    }
}

