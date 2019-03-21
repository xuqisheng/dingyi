package com.zhidianfan.pig.yd.sms.dao.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 李凌峰
 * @since 2018-10-29
 */
@TableName("sms_reply")
public class SmsReply extends Model<SmsReply> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("business_id")
    private Integer businessId;
    private String phone;
    private String msgid;
    @TableField("reply_content")
    private String replyContent;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SmsReply{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", phone=" + phone +
        ", msgid=" + msgid +
        ", replyContent=" + replyContent +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
