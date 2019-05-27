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
 * @author sherry
 * @since 2019-05-15
 */
@TableName("resv_meeting_key")
public class ResvMeetingKey extends Model<ResvMeetingKey> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("key_no")
    private String keyNo;
    @TableField("key_no_business")
    private String keyNoBusiness;
    @TableField("batch_no")
    private String batchNo;
    @TableField("business_id")
    private Integer businessId;
    @TableField("business_name")
    private String businessName;
    @TableField("app_user_id")
    private Integer appUserId;
    @TableField("app_user_code")
    private String appUserCode;
    @TableField("app_user_name")
    private String appUserName;
    @TableField("app_user_phone")
    private String appUserPhone;
    @TableField("device_user_id")
    private Integer deviceUserId;
    @TableField("device_user_name")
    private String deviceUserName;
    @TableField("device_user_phone")
    private String deviceUserPhone;
    @TableField("vip_id")
    private Integer vipId;
    @TableField("vip_phone")
    private String vipPhone;
    @TableField("vip_name")
    private String vipName;
    @TableField("vip_sex")
    private String vipSex;
    @TableField("vip_status")
    private String vipStatus;
    @TableField("resv_date")
    private Date resvDate;
    @TableField("meal_type_id")
    private Integer mealTypeId;
    @TableField("meal_type_name")
    private String mealTypeName;
    @TableField("third_order_no")
    private String thirdOrderNo;
    /**
     * 宴会类型
     */
    @TableField("resv_meeting_order_type")
    private Integer resvMeetingOrderType;
    /**
     * 预计桌数
     */
    @TableField("resv_table_num")
    private Integer resvTableNum;
    /**
     * 备用桌数
     */
    @TableField("backup_table_num")
    private Integer backupTableNum;
    @TableField("resv_amount")
    private String resvAmount;
    /**
     * 获取途径(主)
     */
    @TableField("resv_source_id")
    private Integer resvSourceId;
    @TableField("resv_source_name")
    private String resvSourceName;
    /**
     * 获取途径(明细)
     */
    @TableField("resv_source_detail_id")
    private Integer resvSourceDetailId;
    @TableField("resv_source_detail_name")
    private String resvSourceDetailName;
    private String remark;
    /**
     * 1 为手机 2为预订台 3为天港钉钉
     */
    private Integer source;
    /**
     * 线索状态
     */
    private Integer status;
    @TableField("status_name")
    private String statusName;
    /**
     * 1为有效 0为无效
     */
    @TableField("is_back")
    private Integer isBack;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyNo() {
        return keyNo;
    }

    public void setKeyNo(String keyNo) {
        this.keyNo = keyNo;
    }

    public String getKeyNoBusiness() {
        return keyNoBusiness;
    }

    public void setKeyNoBusiness(String keyNoBusiness) {
        this.keyNoBusiness = keyNoBusiness;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public String getAppUserName() {
        return appUserName;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }

    public String getAppUserPhone() {
        return appUserPhone;
    }

    public void setAppUserPhone(String appUserPhone) {
        this.appUserPhone = appUserPhone;
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

    public String getDeviceUserPhone() {
        return deviceUserPhone;
    }

    public void setDeviceUserPhone(String deviceUserPhone) {
        this.deviceUserPhone = deviceUserPhone;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public String getVipPhone() {
        return vipPhone;
    }

    public void setVipPhone(String vipPhone) {
        this.vipPhone = vipPhone;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getVipSex() {
        return vipSex;
    }

    public void setVipSex(String vipSex) {
        this.vipSex = vipSex;
    }

    public String getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(String vipStatus) {
        this.vipStatus = vipStatus;
    }

    public Date getResvDate() {
        return resvDate;
    }

    public void setResvDate(Date resvDate) {
        this.resvDate = resvDate;
    }

    public Integer getMealTypeId() {
        return mealTypeId;
    }

    public void setMealTypeId(Integer mealTypeId) {
        this.mealTypeId = mealTypeId;
    }

    public String getMealTypeName() {
        return mealTypeName;
    }

    public void setMealTypeName(String mealTypeName) {
        this.mealTypeName = mealTypeName;
    }

    public Integer getResvMeetingOrderType() {
        return resvMeetingOrderType;
    }

    public void setResvMeetingOrderType(Integer resvMeetingOrderType) {
        this.resvMeetingOrderType = resvMeetingOrderType;
    }

    public Integer getResvTableNum() {
        return resvTableNum;
    }

    public void setResvTableNum(Integer resvTableNum) {
        this.resvTableNum = resvTableNum;
    }

    public Integer getBackupTableNum() {
        return backupTableNum;
    }

    public void setBackupTableNum(Integer backupTableNum) {
        this.backupTableNum = backupTableNum;
    }

    public String getResvAmount() {
        return resvAmount;
    }

    public void setResvAmount(String resvAmount) {
        this.resvAmount = resvAmount;
    }

    public Integer getResvSourceId() {
        return resvSourceId;
    }

    public void setResvSourceId(Integer resvSourceId) {
        this.resvSourceId = resvSourceId;
    }

    public String getResvSourceName() {
        return resvSourceName;
    }

    public void setResvSourceName(String resvSourceName) {
        this.resvSourceName = resvSourceName;
    }

    public Integer getResvSourceDetailId() {
        return resvSourceDetailId;
    }

    public void setResvSourceDetailId(Integer resvSourceDetailId) {
        this.resvSourceDetailId = resvSourceDetailId;
    }

    public String getResvSourceDetailName() {
        return resvSourceDetailName;
    }

    public void setResvSourceDetailName(String resvSourceDetailName) {
        this.resvSourceDetailName = resvSourceDetailName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getIsBack() {
        return isBack;
    }

    public void setIsBack(Integer isBack) {
        this.isBack = isBack;
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

    public String getAppUserCode() {
        return appUserCode;
    }

    public void setAppUserCode(String appUserCode) {
        this.appUserCode = appUserCode;
    }

    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ResvMeetingKey{" +
        "id=" + id +
        ", keyNo=" + keyNo +
        ", keyNoBusiness=" + keyNoBusiness +
        ", batchNo=" + batchNo +
        ", businessId=" + businessId +
        ", businessName=" + businessName +
        ", appUserId=" + appUserId +
        ", appUserName=" + appUserName +
        ", appUserPhone=" + appUserPhone +
        ", deviceUserId=" + deviceUserId +
        ", deviceUserName=" + deviceUserName +
        ", deviceUserPhone=" + deviceUserPhone +
        ", vipId=" + vipId +
        ", vipPhone=" + vipPhone +
        ", vipName=" + vipName +
        ", vipSex=" + vipSex +
        ", vipStatus=" + vipStatus +
        ", resvDate=" + resvDate +
        ", mealTypeId=" + mealTypeId +
        ", mealTypeName=" + mealTypeName +
        ", resvMeetingOrderType=" + resvMeetingOrderType +
        ", resvTableNum=" + resvTableNum +
        ", backupTableNum=" + backupTableNum +
        ", resvAmount=" + resvAmount +
        ", resvSourceId=" + resvSourceId +
        ", resvSourceName=" + resvSourceName +
        ", resvSourceDetailId=" + resvSourceDetailId +
        ", resvSourceDetailName=" + resvSourceDetailName +
        ", remark=" + remark +
        ", source=" + source +
        ", status=" + status +
        ", statusName=" + statusName +
        ", isBack=" + isBack +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", appUserCode=" + appUserCode +
        ", thirdOrderNo=" + thirdOrderNo +
        "}";
    }
}
