package com.zhidianfan.pig.ydmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.zhidianfan.pig.ydmonitor", "com.zhidianfan.pig.common.bean"})
@EnableFeignClients
public class PigYdMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PigYdMonitorApplication.class, args);
	}
}
