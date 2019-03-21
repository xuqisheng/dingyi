package com.zhidianfan.pig.yd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/1
 * @Modified By:
 */
@SpringBootApplication
//单服务启动测试的时候注释掉
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.zhidianfan.pig.yd", "com.zhidianfan.pig.common.bean"})
@RestController
@EnableFeignClients
public class PigYdBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(PigYdBaseApplication.class, args);
    }
}
