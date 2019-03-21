package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ljh
 * @since 2018-09-25
 */
@TableName("resv_line")
public class ResvLine extends Model<ResvLine> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("business_id")
    private Integer businessId;
    @TableField("business_name")
    private String businessName;
    /**
     * 排队转入座批次号
     */
    @TableField("batch_no")
    private String batchNo;
    /**
     * 排队序列号  参考订单号
     */
    @TableField("line_No")
    private String lineNo;

    /**
     * 排队号 不同天不同班次重新计数  例如  1  2  3
     */
    @TableField("line_sort")
    private Integer lineSort;
    @TableField("vip_id")
    private Integer vipId;
    @TableField("vip_name")
    private String vipName;
    @TableField("vip_phone")
    private String vipPhone;
    @TableField("vip_sex")
    private String vipSex;

    /**
     * 预订日期
     */
    @TableField("resv_date")
    private Date resvDate;
    /**
     * 预订人数
     */
    @TableField("resv_num")
    private Integer resvNum;
    /**
     * 餐别id
     */
    @TableField("meal_type_id")
    private Integer mealTypeId;
    /**
     * 餐别名称
     */
    @TableField("meal_type_name")
    private String mealTypeName;

    /**
     *推荐到店时间
     */
    @TableField("resv_time")
    private String resvTime;

    /**
     * 外部来源
     */
    @TableField("resv_source_id")
    private Integer resvSourceId;

    @TableField("resv_source_name")
    private String resvSourceName;

    private String remark;

    /**
     * 排队状态
     */
    private Integer status;

    /**
     * 操作者id
     */
    private Integer operation;


    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;

    /**
     * 桌位id
     */
    @TableField("table_id")
    private Integer tableId;
    /**
     * 桌位名称
     */
    @TableField("table_name")
    private String tableName;

    /**
     * 使用id
     */
    @TableField("app_user_id")
    private Integer appUserId;
    @TableField("app_user_name")
    private String appUserName;

    /**
     *设备id
     */
    @TableField("device_user_id")
    private Integer deviceUserId;
    @TableField("device_user_name")
    private String deviceUserName;

    /**
     * 安卓电话机用户id
     */
    @TableField("android_user_id")
    private Integer androidUserId;
    @TableField("android_user_name")
    private String androidUserName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }


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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }



    public Integer getLineSort() {
        return lineSort;
    }

    public void setLineSort(Integer lineSort) {
        this.lineSort = lineSort;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getVipPhone() {
        return vipPhone;
    }

    public void setVipPhone(String vipPhone) {
        this.vipPhone = vipPhone;
    }

    public String getVipSex() {
        return vipSex;
    }

    public void setVipSex(String vipSex) {
        this.vipSex = vipSex;
    }

    public Date getResvDate() {
        return resvDate;
    }

    public void setResvDate(Date resvDate) {
        this.resvDate = resvDate;
    }

    public Integer getResvNum() {
        return resvNum;
    }

    public void setResvNum(Integer resvNum) {
        this.resvNum = resvNum;
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

    public String getResvTime() {
        return resvTime;
    }

    public void setResvTime(String resvTime) {
        this.resvTime = resvTime;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
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

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserid) {
        this.appUserId = appUserid;
    }

    public String getAppUserName() {
        return appUserName;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
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

    public Integer getAndroidUserId() {
        return androidUserId;
    }

    public void setAndroidUserId(Integer androidUserId) {
        this.androidUserId = androidUserId;
    }

    public String getAndroidUserName() {
        return androidUserName;
    }

    public void setAndroidUserName(String androidUserName) {
        this.androidUserName = androidUserName;
    }


    @Override
    public String toString() {
        return "ResvLine{" +
                "id=" + id +
                ", businessId=" + businessId +
                ", businessName='" + businessName + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", lineNo='" + lineNo + '\'' +
                ", lineSort=" + lineSort +
                ", vipId=" + vipId +
                ", vipName='" + vipName + '\'' +
                ", vipPhone='" + vipPhone + '\'' +
                ", vipSex='" + vipSex + '\'' +
                ", resvDate=" + resvDate +
                ", resvNum=" + resvNum +
                ", mealTypeId=" + mealTypeId +
                ", mealTypeName='" + mealTypeName + '\'' +
                ", resvTime='" + resvTime + '\'' +
                ", resvSourceId=" + resvSourceId +
                ", resvSourceName='" + resvSourceName + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", operation=" + operation +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", tableId=" + tableId +
                ", tableName='" + tableName + '\'' +
                ", appUserId=" + appUserId +
                ", appUserName='" + appUserName + '\'' +
                ", deviceUserId=" + deviceUserId +
                ", deviceUserName='" + deviceUserName + '\'' +
                ", androidUserId=" + androidUserId +
                ", androidUserName='" + androidUserName + '\'' +
                '}';
    }
}
