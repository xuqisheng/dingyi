package com.zhidianfan.pig.push.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


/**
 * @Author ljh
 * @Description
 * @Date Create in 2018/11/2
 */
@Data
@Component
//@Configuration
//@ConfigurationProperties(prefix = "jiguang")
public class JGProperties {


    private String devKey;

    private String devSecret;

    private String deviceHostName;


    private String devicesPath;

    private String tagsPath;

    private String aliasesPath;

    private String scheduleHostName;

    private String schedulePath;

}
