package com.zhidianfan.pig.yd;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/1
 * @Modified By:
 */
//@SpringCloudApplication
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.zhidianfan.pig.yd", "com.zhidianfan.pig.common.bean"})
@RestController
@EnableScheduling
@EnableAsync
public class PigYdApplication {

    @GetMapping("/helloworld1")
    public ResponseEntity demo1() {
        return ResponseEntity.ok("当前时间1：" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }

    @GetMapping("/helloworld2")
    public ResponseEntity demo2() {
        return ResponseEntity.ok("当前时间2：" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }

    public static void main(String[] args) {
        SpringApplication.run(PigYdApplication.class, args);
    }
}
