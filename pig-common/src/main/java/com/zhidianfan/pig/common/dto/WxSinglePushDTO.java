package com.zhidianfan.pig.common.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Map;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/6
 * @Modified By:
 */
@Data
public class WxSinglePushDTO {
    /**
     * 微信用户openid
     */
    @NotBlank
    private String openId;
    /**
     * 微信公众号模板id
     */
    @NotBlank
    private String templateId;

    /**
     * 消息跳转链接
     */
    private String url;
    /**
     * 模板变量
     */
    private Map<String, Object> params;
}
