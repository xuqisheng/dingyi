package com.zhidianfan.pig.yd.moduler.push.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sjl
 * 2019-03-18 10:12
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OpenIdResultDTO {

    /**
     * 用户唯一标识
     */
    private String openid;

    /**
     * 会话密码钥
     */
    @JsonProperty("session_key")
    private String sessionKey;

    /**
     * 用户在开放平台的唯一标识
     */
    private String unionid;

    /**
     * 错误码,0-请求成功
     */
    private Integer errcode;

    /**
     * 错误信息
     */
    private String errmsg;

}
