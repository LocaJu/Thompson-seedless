package com.seed;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/10 13:59
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.seed.mapper"})
public class SeedBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeedBlogApplication.class,args);
    }
}
