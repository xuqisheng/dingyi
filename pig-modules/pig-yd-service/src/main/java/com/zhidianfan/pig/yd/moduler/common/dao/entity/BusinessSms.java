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
 * @since 2018-09-10
 */
@TableName("business_sms")
public class BusinessSms extends Model<BusinessSms> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    @TableField("admin_name")
    private String adminName;
    @TableField("admin_phone")
    private String adminPhone;
    @TableField("current_sms_num")
    private Integer currentSmsNum;
    @TableField("min_sms_num")
    private Integer minSmsNum;
    @TableField("sms_message")
    private String smsMessage;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    @TableField("is_send")
    private Integer isSend;


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

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusinessSms{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", adminName=" + adminName +
        ", adminPhone=" + adminPhone +
        ", currentSmsNum=" + currentSmsNum +
        ", minSmsNum=" + minSmsNum +
        ", smsMessage=" + smsMessage +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", isSend=" + isSend +
        "}";
    }
}
