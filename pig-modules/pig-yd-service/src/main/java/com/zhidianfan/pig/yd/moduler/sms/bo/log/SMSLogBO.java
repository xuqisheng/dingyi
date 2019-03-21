package com.zhidianfan.pig.yd.moduler.sms.bo.log;

import com.zhidianfan.pig.yd.moduler.sms.bo.BaseBO;

/**
 *
 * Created by Administrator on 2017/12/18.
 */
public class SMSLogBO extends BaseBO {

    private Long id;
    private Long businessId;
    private String businessName;
    private String content;
    private String vipPhone;
    private Integer smsNum;
    private Long appUserId;
    private String appUserName;
    private Long deviceUserId;
    private String deviceUserName;
    private String msgid;
    private String reportTime;
    private String status;
    private String statusDesc;

    public SMSLogBO() {
    }

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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVipPhone() {
        return vipPhone;
    }

    public void setVipPhone(String vipPhone) {
        this.vipPhone = vipPhone;
    }

    public Integer getSmsNum() {
        return smsNum;
    }

    public void setSmsNum(Integer smsNum) {
        this.smsNum = smsNum;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public String getAppUserName() {
        return appUserName;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }

    public Long getDeviceUserId() {
        return deviceUserId;
    }

    public void setDeviceUserId(Long deviceUserId) {
        this.deviceUserId = deviceUserId;
    }

    public String getDeviceUserName() {
        return deviceUserName;
    }

    public void setDeviceUserName(String deviceUserName) {
        this.deviceUserName = deviceUserName;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

}
