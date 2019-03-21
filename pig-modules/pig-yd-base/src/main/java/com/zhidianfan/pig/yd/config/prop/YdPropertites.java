package com.zhidianfan.pig.yd.config.prop;

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

    /**
     * 极光配置
     */
    private JiGuangPropertites jg;
    /**
     * 创蓝短信配置
     */
    private ChuangLanPropertites cl;

    /**
     * nodejs推送
     */
    private NodePushPropertites node;

    /**
     * 微信获取 openid
     */
    private Push push;

    private QiNiuProperties qiniu;

    public QiNiuProperties getQiniu() {
        return qiniu;
    }

    public void setQiniu(QiNiuProperties qiniu) {
        this.qiniu = qiniu;
    }

    public NodePushPropertites getNode() {
        return node;
    }

    public void setNode(NodePushPropertites node) {
        this.node = node;
    }

    public ChuangLanPropertites getCl() {
        return cl;
    }

    public void setCl(ChuangLanPropertites cl) {
        this.cl = cl;
    }

    public JiGuangPropertites getJg() {
        return jg;
    }

    public void setJg(JiGuangPropertites jg) {
        this.jg = jg;
    }


    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Push getPush() {
        return push;
    }

    public void setPush(Push push) {
        this.push = push;
    }
}
