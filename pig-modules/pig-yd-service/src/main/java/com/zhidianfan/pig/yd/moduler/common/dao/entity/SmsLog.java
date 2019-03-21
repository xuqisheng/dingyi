package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Conan
 * @since 2018-09-06
 */
@TableName("sms_log")
public class SmsLog extends Model<SmsLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    @TableField("business_name")
    private String businessName;
    private String content;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    @TableField("app_user_id")
    private Integer appUserId;
    @TableField("vip_phone")
    private String vipPhone;
    @TableField("app_user_name")
    private String appUserName;
    @TableField("sms_num")
    private Integer smsNum;
    @TableField("device_user_id")
    private Integer deviceUserId;
    @TableField("device_user_name")
    private String deviceUserName;
    /**
     * 每条短信数
     */
    private Integer num;
    private String msgid;
    /**
     * 接收发送状态时间
     */
    @TableField("report_time")
    private Date reportTime;
    private String status;
    @TableField("status_desc")
    private String statusDesc;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
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

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public String getVipPhone() {
        return vipPhone;
    }

    public void setVipPhone(String vipPhone) {
        this.vipPhone = vipPhone;
    }

    public String getAppUserName() {
        return appUserName;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }

    public Integer getSmsNum() {
        return smsNum;
    }

    public void setSmsNum(Integer smsNum) {
        this.smsNum = smsNum;
    }

    public Integer getDeviceUserId() {
        return deviceUserId;
    }

    public void setDeviceUserId(Integer deviceUserId) {
        this.deviceUserId = deviceUserId;
    }

    public String getDeviceUserName() {
        return deviceUserName;
    }

    public void setDeviceUserName(String deviceUserName) {
        this.deviceUserName = deviceUserName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SmsLog{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", businessName=" + businessName +
        ", content=" + content +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", appUserId=" + appUserId +
        ", vipPhone=" + vipPhone +
        ", appUserName=" + appUserName +
        ", smsNum=" + smsNum +
        ", deviceUserId=" + deviceUserId +
        ", deviceUserName=" + deviceUserName +
        ", num=" + num +
        ", msgid=" + msgid +
        ", reportTime=" + reportTime +
        ", status=" + status +
        ", statusDesc=" + statusDesc +
        "}";
    }
}
