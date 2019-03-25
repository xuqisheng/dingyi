package com.zhidianfan.pig.yd.moduler.resv.vo;

import lombok.Data;

/**
 * 登录信息
 */
@Data
public class LoginMessageVo {

    /**
     * 登录验证码
     */
    private String code;

    /**
     * 需要发送的手机号码
     */
    private Long phone;

    /**
     * 酒店id
     */
    private Long businessId;

}
