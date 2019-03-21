package com.zhidianfan.pig.yd.moduler.dbo.log;

import com.zhidianfan.pig.yd.moduler.dbo.BaseDO;

/**
 * Created by Administrator on 2017/12/14.
 */
public class SMSLogDO extends BaseDO {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 酒店id
     */
    private Long businessId;

    /**
     * 酒店名称
     */
    private String businessName;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 客户手机号
     */
    private String vipPhone;

    /**
     * 发送短信数量
     */
    private Integer smsNum;

    /**
     * 营销经理id
     */
    private Long appUserId;

    /**
     * 营销经理姓名
     */
    private String appUserName;

    /**
     * 预定台操作员id
     */
    private Long deviceUserId;

    /**
     * 预定台操作员姓名
     */
    private String deviceUserName;

    /**
     * 短信id
     */
    private String msgid;

    /**
     * 回调时间
     */
    private String reportTime;

    /**
     * 短信发送状态
     */
    private String status;

    /**
     * 短信发送状态描述
     */
    private String statusDesc;

    public SMSLogDO() {

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

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
