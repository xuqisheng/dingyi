package com.zhidianfan.pig.yd.moduler.sms.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 短信对象
 */
public class SMSDTO {
    /**
     * 酒店id
     */
    @NotNull
    private Long businessId;

    /**
     * 短信内容
     */
    @NotEmpty
    private String messageContent;

    /**
     * 发送手机号
     */
    @NotBlank
    private String phone;

    /**
     * 发送时间，时间戳
     * 默认立即发送
     */
    private Long sendtime;

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getSendtime() {
        return sendtime;
    }

    public void setSendtime(Long sendtime) {
        this.sendtime = sendtime;
    }
}
