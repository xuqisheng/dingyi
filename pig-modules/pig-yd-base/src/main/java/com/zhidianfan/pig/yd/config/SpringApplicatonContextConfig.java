package com.zhidianfan.pig.yd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sjl
 * 2019-02-28 15:37
 */
@Configuration
public class SpringApplicatonContextConfig {

    @Bean
    public SpringApplicationContext newConfigure(){
        return new SpringApplicationContext();
    }
}
