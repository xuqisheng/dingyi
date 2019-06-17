package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 客户分析信息表
 * </p>
 *
 * @author 
 * @since 2019-05-29
 */
@TableName("business_customer_analysis_info")
public class BusinessCustomerAnalysisInfo extends Model<BusinessCustomerAnalysisInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * business_customer_analysis 表中的 id
     */
    @TableField("business_id")
    private Integer businessId;
    /**
     * vip 表中主键
     */
    @TableField("vip_id")
    private Integer vipId;
    /**
     * 客户姓名
     */
    @TableField("vip_name")
    private String vipName;
    /**
     * 客户性别
     */
    @TableField("vip_sex")
    private String vipSex;
    /**
     * 客户电话
     */
    @TableField("vip_phone")
    private String vipPhone;
    /**
     * 经销经理id
     */
    @TableField("app_user_id")
    private Integer appUserId;
    /**
     * 营销经理名称
     */
    @TableField("app_user_name")
    private String appUserName;
    /**
     * 经销经理电话
     */
    @TableField("app_user_phone")
    private String appUserPhone;
    /**
     * 当前所属营销经理id
     */
    @TableField("current_app_user_id")
    private Integer currentAppUserId;
    /**
     * 当前所属营销经理
     */
    @TableField("current_app_user")
    private String currentAppUser;
    /**
     * 当前所属营销经理电话
     */
    @TableField("current_app_user_phone")
    private String currentAppUserPhone;
    /**
     * 1:活跃用户，2:沉睡用户，3:流失用户，4:意向用户，5:恶意用户，6:高价值用户，7:唤醒，8:新用户
     */
    private Integer type;
    /**
     * 1:活跃用户，2:沉睡用户，3:流失用户，4:意向用户，5:恶意用户，6:高价值用户，7:唤醒，8:新用户
     */
    @TableField("vip_value_type")
    private Integer vipValueType;
    @TableField("date")
    private String date;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


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

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public String getVipName() {
        return vipName;
    }

    public String getVipSex() {
        return vipSex;
    }

    public void setVipSex(String vipSex) {
        this.vipSex = vipSex;
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

    public Integer getCurrentAppUserId() {
        return currentAppUserId;
    }

    public void setCurrentAppUserId(Integer currentAppUserId) {
        this.currentAppUserId = currentAppUserId;
    }

    public String getCurrentAppUser() {
        return currentAppUser;
    }

    public void setCurrentAppUser(String currentAppUser) {
        this.currentAppUser = currentAppUser;
    }

    public String getCurrentAppUserPhone() {
        return currentAppUserPhone;
    }

    public void setCurrentAppUserPhone(String currentAppUserPhone) {
        this.currentAppUserPhone = currentAppUserPhone;
    }

    public Integer getVipValueType() {
        return vipValueType;
    }

    public void setVipValueType(Integer vipValueType) {
        this.vipValueType = vipValueType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusinessCustomerAnalysisInfo{" +
        "id=" + id +
        ", bcaId=" + businessId +
        ", vipId=" + vipId +
        ", vipName=" + vipName +
        ", vipSex=" + vipSex +
        ", vipPhone=" + vipPhone +
        ", appUserId=" + appUserId +
        ", appUserName=" + appUserName +
        ", appUserPhone=" + appUserPhone +
        ", currentAppUserId=" + currentAppUserId +
        ", currentAppUser=" + currentAppUser +
        ", currentAppUserPhone=" + currentAppUserPhone +
        ", vipValueType=" + vipValueType +
        ", type=" + type +
        ", date=" + date +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
