package com.zhidianfan.pig.push.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/1
 * @Modified By:
 */
@Configuration
public class WebConfig {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
