package com.zhidianfan.pig.yd.moduler.sms.dto.log;

import com.zhidianfan.pig.yd.moduler.sms.dto.BaseDTO;

/**
 * 短信回复
 * Created by Administrator on 2018/4/12.
 */
public class SMSReplyDTO extends BaseDTO {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 酒店id
     */
    private Long businessId;

    /**
     * 回复的手机号
     */
    private String phone;

    /**
     * 回复短信的 msgid
     */
    private String msgid;

    /**
     * 回复内容
     */
    private String replyContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
}
