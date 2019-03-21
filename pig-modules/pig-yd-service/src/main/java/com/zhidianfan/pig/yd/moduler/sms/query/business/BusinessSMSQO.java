package com.zhidianfan.pig.yd.moduler.sms.query.business;

import com.zhidianfan.pig.yd.moduler.sms.query.BaseQO;

/**
 *
 * Created by Administrator on 2017/12/13.
 */
public class BusinessSMSQO extends BaseQO {

    private Long id;
    private Long businessId;
    private String adminName;
    private String adminPhone;
    private Integer currentSmsNum;
    private Integer minSmsNum;
    private String smsMessage;
    private Integer isSend;

    public BusinessSMSQO() {
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

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public Integer getCurrentSmsNum() {
        return currentSmsNum;
    }

    public void setCurrentSmsNum(Integer currentSmsNum) {
        this.currentSmsNum = currentSmsNum;
    }

    public Integer getMinSmsNum() {
        return minSmsNum;
    }

    public void setMinSmsNum(Integer minSmsNum) {
        this.minSmsNum = minSmsNum;
    }

    public String getSmsMessage() {
        return smsMessage;
    }

    public void setSmsMessage(String smsMessage) {
        this.smsMessage = smsMessage;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }
}
