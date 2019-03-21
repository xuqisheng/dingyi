package com.zhidianfan.pig.common.constant;

import java.util.Date;

import java.io.Serializable;

/**
 * <p>
 * 短信发送记录
 * </p>
 *
 * @author sherry
 * @since 2018-10-31
 */
public class LogSms implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String phoneNum;
    private String note;
    /**
     * 短信内容

0-普通短信
1-营销短信
     */
    private Integer smsType;
    private Date sendDate;
    /**
     * 短信服务商调用结果
     */
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


    public LogSms() {
    }

    public LogSms(String phoneNum, String note, Integer smsType, Date sendDate, String sendRes) {
        this.phoneNum = phoneNum;
        this.note = note;
        this.smsType = smsType;
        this.sendDate = sendDate;
        this.sendRes = sendRes;
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
