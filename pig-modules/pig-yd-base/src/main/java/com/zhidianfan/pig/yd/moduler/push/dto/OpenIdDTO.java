package com.zhidianfan.pig.yd.moduler.push.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author sjl
 * 2019-03-18 10:05
 */
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class OpenIdDTO implements Serializable {

    /**
     * 小程序 appId
     */
    private String appid = "wxd5515c49caab35a5";

    /**
     * 小程序 appSecret
     */
    private String secret = "2838ecba3f715418006d9af7b9c5145c";

    /**
     * 登录时获取的 Code
     */
    @NotBlank
    private String code;

    /**
     * 授权类型，此处只需要填写 authorization_code
     */
    private String grantType = "authorization_code";

}
