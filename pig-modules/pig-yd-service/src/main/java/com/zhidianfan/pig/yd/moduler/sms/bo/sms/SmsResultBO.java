package com.zhidianfan.pig.yd.moduler.sms.bo.sms;

import java.io.Serializable;

public class SmsResultBO implements Serializable {

    //响应时间
    private String time;
    //消息id
    private Long msgId;
    //状态码说明（成功返回空）
    private String errorMsg;
    //状态码（详细参考提交响应状态码）
    private String code;
    //状态信息
    private String text;
    //状态
    private String status;
    //是否低于阈值
    private Boolean remind;
    //酒店设定的阈值
    private Integer minSmsNum;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
    }

    public Integer getMinSmsNum() {
        return minSmsNum;
    }

    public void setMinSmsNum(Integer minSmsNum) {
        this.minSmsNum = minSmsNum;
    }

}
