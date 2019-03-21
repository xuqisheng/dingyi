package com.zhidianfan.pig.yd.moduler.dbo.log;

import com.zhidianfan.pig.yd.moduler.sms.dto.BaseDTO;

/**
 * 短信回复
 * Created by Administrator on 2018/4/12.
 */
public class SMSReplyDO extends BaseDTO {

    private Long id;

    private Long businessId;

    private String phone;

    private String msgid;

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
