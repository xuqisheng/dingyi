package com.zhidianfan.pig.log.dao.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 短信发送记录
 * </p>
 *
 * @author sherry
 * @since 2018-10-31
 */
@TableName("log_sms")
public class LogSms extends Model<LogSms> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    @TableField("phone_num")
    private String phoneNum;
    private String note;
    /**
     * 短信内容

0-普通短信
1-营销短信
     */
    @TableField("sms_type")
    private Integer smsType;
    @TableField("send_date")
    private Date sendDate;
    /**
     * 短信服务商调用结果
     */
    @TableField("send_res")
    private String sendRes;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendRes() {
        return sendRes;
    }

    public void setSendRes(String sendRes) {
        this.sendRes = sendRes;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "LogSms{" +
        "id=" + id +
        ", phoneNum=" + phoneNum +
        ", note=" + note +
        ", smsType=" + smsType +
        ", sendDate=" + sendDate +
        ", sendRes=" + sendRes +
        "}";
    }
}
