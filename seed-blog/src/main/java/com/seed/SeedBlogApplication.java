package com.seed;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/10 13:59
 */
@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = {"com.seed.mapper"})
@EnableSwagger2
public class SeedBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeedBlogApplication.class,args);
    }
}
