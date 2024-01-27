package com.seed;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2024/1/5 21:14
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.seed.mapper"})
@EnableScheduling
public class BlogAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class, args);
    }
}

