package com.zhidianfan.pig.yd.moduler.sms.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 登录信息
 */
public class LoginMessageDTO {

    /**
     * 登录验证码
     */
    @NotBlank
    private String code;

    /**
     * 需要发送的手机号码
     */
    @NotNull
    private Long phone;

    /**
     * 酒店id
     */
    @NotNull
    private Long businessId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }
}
