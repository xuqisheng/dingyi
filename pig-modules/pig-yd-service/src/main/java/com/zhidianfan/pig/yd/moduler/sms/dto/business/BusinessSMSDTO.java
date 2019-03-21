package com.zhidianfan.pig.yd.moduler.sms.dto.business;

import com.zhidianfan.pig.yd.moduler.sms.dto.BaseDTO;

/**
 *
 * Created by Administrator on 2017/12/13.
 */
public class BusinessSMSDTO extends BaseDTO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 酒店id
     */
    private Long businessId;

    /**
     * 管理员名称
     */
    private String adminName;

    /**
     * 管理员号码
     */
    private String adminPhone;

    /**
     * 可发送短信条数
     */
    private Integer currentSmsNum;

    /**
     * 酒店自定义短信信息
     */
    private String smsMessage;

    /**
     * 发送短信条数
     */
    private Integer smsNum;

    /**
     * 短信提醒最小值(定时任务)
     */
    private Integer minSmsNum;

    /**
     * 短信是否提醒(定时任务)
     */
    private Integer isSend;

    public BusinessSMSDTO() {
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
