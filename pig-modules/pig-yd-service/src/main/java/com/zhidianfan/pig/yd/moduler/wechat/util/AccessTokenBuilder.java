package com.zhidianfan.pig.yd.moduler.wechat.util;

import java.time.LocalDateTime;

/**
 * 微信access_token
 *
 * @author wangyz
 * @version v 0.1 2019-04-15 15:02 wangyz Exp $
 */
public class AccessTokenBuilder {

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

    public AccessTokenBuilder setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public AccessTokenBuilder setRefresh(Boolean refresh) {
        this.refresh = refresh;
        return this;
    }

    public AccessTokenBuilder setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public AccessTokenBuilder setAccessTokenTime(LocalDateTime accessTokenTime) {
        this.accessTokenTime = accessTokenTime;
        return this;
    }

    public AccessTokenBuilder setOpenid(String openid) {
        this.openid = openid;
        return this;
    }

    public AccessTokenBuilder setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public AccessTokenBuilder setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public AccessToken build() {
        return new AccessToken(accessToken, refresh, expiresIn, accessTokenTime, openid, scope, refreshToken);
    }
}
