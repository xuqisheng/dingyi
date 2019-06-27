package com.zhidianfan.pig.yd.moduler.wechat.util;

import lombok.Data;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2019-06-26
 * @Modified By:
 */
@Data
public class WxConfig {

    private String appId;

    private String timestamp;

    private String nonceStr;

    private String signature;
}
