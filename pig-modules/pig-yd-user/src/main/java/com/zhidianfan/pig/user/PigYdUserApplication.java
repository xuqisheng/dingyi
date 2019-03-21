package com.zhidianfan.pig.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/1
 * @Modified By:
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.zhidianfan.pig.user", "com.zhidianfan.pig.common.bean"})
@EnableFeignClients
public class PigYdUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(PigYdUserApplication.class, args);
    }
}
