package com.zhidianfan.pig.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/20
 * @Modified By:
 */
@Configuration
@ConfigurationProperties(prefix = "yd")
public class YdPropertites {

    @Value("${spring.profiles.active}")
    private String active;



    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
