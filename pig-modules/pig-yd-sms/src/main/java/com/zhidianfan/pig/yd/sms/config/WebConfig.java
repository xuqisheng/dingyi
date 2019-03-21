package com.zhidianfan.pig.yd.sms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/22
 * @Modified By:
 */

@Configuration
public class WebConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }



}
