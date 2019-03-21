package com.zhidianfan.pig.push;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.zhidianfan.pig.push", "com.zhidianfan.pig.common.bean"})
public class PigYdPushApplication {

    public static void main(String[] args) {
        SpringApplication.run(PigYdPushApplication.class, args);
    }
}
