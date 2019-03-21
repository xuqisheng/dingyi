package com.zhidianfan.pig.yd.moduler.sms.dto.log;

import com.zhidianfan.pig.yd.moduler.sms.dto.BaseDTO;
import lombok.NonNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * Created by Administrator on 2017/12/14.
 */
public class SMSLogDTO extends BaseDTO {

    @NonNull
    private Long id;
    @NonNull
    private Long businessId;
    @NotEmpty
    private String businessName;
    @NotEmpty
    private String content;
    @NotEmpty
    private String vipPhone;
    @NonNull
    private Integer smsNum;
    @NonNull
    private Long appUserId;
    @NotEmpty
    private String appUserName;
    @NonNull
    private Long deviceUserId;
    @NotEmpty
    private String deviceUserName;
    @NotEmpty
    private String msgid;
    @NotEmpty
    private String reportTime;
    @NotEmpty
    private String status;
    @NotEmpty
    private String statusDesc;

    public SMSLogDTO() {

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
