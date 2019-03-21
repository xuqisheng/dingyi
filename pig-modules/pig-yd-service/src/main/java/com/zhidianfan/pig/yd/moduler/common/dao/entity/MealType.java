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
 * @author sherry
 * @since 2018-08-16
 */
@TableName("meal_type")
public class MealType extends Model<MealType> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    /**
     * 用餐时段名称
     */
    @TableField("meal_type_name")
    private String mealTypeName;
    @TableField("business_name")
    private String businessName;
    /**
     * 状态：1启动，0不启动
     */
    private String status;
    @TableField("meal_type_code")
    private String mealTypeCode;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    @TableField("resv_start_time")
    private String resvStartTime;
    @TableField("resv_end_time")
    private String resvEndTime;
    @TableField("meal_type_id_a")
    private Integer mealTypeIdA;
    /**
     * 分班次a
     */
    @TableField("meal_type_name_a")
    private String mealTypeNameA;
    @TableField("meal_type_id_b")
    private Integer mealTypeIdB;
    /**
     * 分班次b
     */
    @TableField("meal_type_name_b")
    private String mealTypeNameB;
    /**
     * 分班次时间点
     */
    @TableField("band_end_time")
    private String bandEndTime;
    /**
     * 使用时间，2017-07-06，逗号分割
     */
    @TableField("using_date")
    private String usingDate;
    /**
     * 开餐时间
     */
    @TableField("meal_start_time")
    private String mealStartTime;

    @TableField("config_id")
    private Integer configId;

    @TableField("isnew")
    private Integer isnew;

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

    public String getMealTypeName() {
        return mealTypeName;
    }

    public void setMealTypeName(String mealTypeName) {
        this.mealTypeName = mealTypeName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMealTypeCode() {
        return mealTypeCode;
    }

    public void setMealTypeCode(String mealTypeCode) {
        this.mealTypeCode = mealTypeCode;
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

    public String getResvStartTime() {
        return resvStartTime;
    }

    public void setResvStartTime(String resvStartTime) {
        this.resvStartTime = resvStartTime;
    }

    public String getResvEndTime() {
        return resvEndTime;
    }

    public void setResvEndTime(String resvEndTime) {
        this.resvEndTime = resvEndTime;
    }

    public Integer getMealTypeIdA() {
        return mealTypeIdA;
    }

    public void setMealTypeIdA(Integer mealTypeIdA) {
        this.mealTypeIdA = mealTypeIdA;
    }

    public String getMealTypeNameA() {
        return mealTypeNameA;
    }

    public void setMealTypeNameA(String mealTypeNameA) {
        this.mealTypeNameA = mealTypeNameA;
    }

    public Integer getMealTypeIdB() {
        return mealTypeIdB;
    }

    public void setMealTypeIdB(Integer mealTypeIdB) {
        this.mealTypeIdB = mealTypeIdB;
    }

    public String getMealTypeNameB() {
        return mealTypeNameB;
    }

    public void setMealTypeNameB(String mealTypeNameB) {
        this.mealTypeNameB = mealTypeNameB;
    }

    public String getBandEndTime() {
        return bandEndTime;
    }

    public void setBandEndTime(String bandEndTime) {
        this.bandEndTime = bandEndTime;
    }

    public String getUsingDate() {
        return usingDate;
    }

    public void setUsingDate(String usingDate) {
        this.usingDate = usingDate;
    }

    public String getMealStartTime() {
        return mealStartTime;
    }

    public void setMealStartTime(String mealStartTime) {
        this.mealStartTime = mealStartTime;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Integer getIsnew() {
        return isnew;
    }

    public void setIsnew(Integer isnew) {
        this.isnew = isnew;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public MealType() {
    }


    public MealType(Integer businessId, String mealTypeName, String businessName, String status, String mealTypeCode, Date createdAt, String resvStartTime, String resvEndTime, Integer configId,Integer isnew) {
        this.businessId = businessId;
        this.mealTypeName = mealTypeName;
        this.businessName = businessName;
        this.status = status;
        this.mealTypeCode = mealTypeCode;
        this.createdAt = createdAt;
        this.resvStartTime = resvStartTime;
        this.resvEndTime = resvEndTime;
        this.configId = configId;
        this.isnew = isnew;
    }

    @Override
    public String toString() {
        return "MealType{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", mealTypeName=" + mealTypeName +
        ", businessName=" + businessName +
        ", status=" + status +
        ", mealTypeCode=" + mealTypeCode +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", resvStartTime=" + resvStartTime +
        ", resvEndTime=" + resvEndTime +
        ", mealTypeIdA=" + mealTypeIdA +
        ", mealTypeNameA=" + mealTypeNameA +
        ", mealTypeIdB=" + mealTypeIdB +
        ", mealTypeNameB=" + mealTypeNameB +
        ", bandEndTime=" + bandEndTime +
        ", usingDate=" + usingDate +
        ", mealStartTime=" + mealStartTime +
        "}";
    }
}
