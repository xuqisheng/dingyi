package com.zhidianfan.pig.yd.moduler.wechat.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wangyz
 * @version v 0.1 2019-04-15 14:13 wangyz Exp $
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessToken implements Serializable {
    /**
     * token内容
     */
    private String accessToken;
    /**
     * 是否需要刷新token
     */
    private Boolean refresh;
    /**
     * token有效时间
     */
    private Long expiresIn;
    /**
     * 获取的token时间
     */
    private LocalDateTime accessTokenTime;
    /**
     * 用户的openid
     */
    private String openid;
    /**
     * 用户授权的作用区域
     */
    private String scope;

    /**
     * 刷新token
     */
    private String refreshToken;
}
