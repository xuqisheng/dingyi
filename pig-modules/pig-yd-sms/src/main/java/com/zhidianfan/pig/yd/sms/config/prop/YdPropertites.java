package com.zhidianfan.pig.yd.sms.config.prop;

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

    private SmsPropertites sms;

    public SmsPropertites getSms() {
        return sms;
    }

    public void setSms(SmsPropertites sms) {
        this.sms = sms;
    }

    /**
     * 创蓝短信配置
     */
    private ChuangLanPropertites cl;


    /**
     * 创蓝短信配置
     */
    private LeXinPropertities lx;


    public ChuangLanPropertites getCl() {
        return cl;
    }

    public void setCl(ChuangLanPropertites cl) {
        this.cl = cl;
    }


    public LeXinPropertities getLx() {
        return lx;
    }

    public void setLx(LeXinPropertities lx) {
        this.lx = lx;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }


}
