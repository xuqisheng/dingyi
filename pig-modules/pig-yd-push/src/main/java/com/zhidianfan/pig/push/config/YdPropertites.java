package com.zhidianfan.pig.push.config;

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

    private JGProperties jiguang;

    private WxProperties wx;

    private Integer retry;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public JGProperties getJiguang() {
        return jiguang;
    }

    public void setJiguang(JGProperties jiguang) {
        this.jiguang = jiguang;
    }

    public WxProperties getWx() {
        return wx;
    }

    public void setWx(WxProperties wx) {
        this.wx = wx;
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }
}
