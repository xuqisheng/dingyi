package com.zhidianfan.pig.yd.sms.bo;

public class LoginMessageBO {

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
