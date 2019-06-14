package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author huzp
 * @since 2019-05-16
 */
@TableName("sms_marketing_batch")
public class SmsMarketingBatch extends Model<SmsMarketingBatch> {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 营销短信id
     */
    @TableField("sms_marketing_id")
    private Integer smsMarketingId;
    @TableField("msg_id")
    private String msgId;
    /**
     * 目标手机
     */
    @TableField("target_phones")
    private String targetPhones;
    /**
     * 状态0：未发送，1：已经提交给创蓝
     */
    private Integer status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSmsMarketingId() {
        return smsMarketingId;
    }

    public void setSmsMarketingId(Integer smsMarketingId) {
        this.smsMarketingId = smsMarketingId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getTargetPhones() {
        return targetPhones;
    }

    public void setTargetPhones(String targetPhones) {
        this.targetPhones = targetPhones;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SmsMarketingBatch{" +
        "id=" + id +
        ", smsMarketingId=" + smsMarketingId +
        ", msgId=" + msgId +
        ", targetPhones=" + targetPhones +
        ", status=" + status +
        "}";
    }
}
