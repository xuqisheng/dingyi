package com.zhidianfan.pig.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author lengleng
 * @date 2017年10月27日13:59:05
 */
@EnableAsync
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.zhidianfan.pig.admin", "com.zhidianfan.pig.common.bean"})
public class PigAdminApplication {
    public static void main(String[] args) {
        System.out.println("-------------------");
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        SpringApplication.run(PigAdminApplication.class, args);
    }
}