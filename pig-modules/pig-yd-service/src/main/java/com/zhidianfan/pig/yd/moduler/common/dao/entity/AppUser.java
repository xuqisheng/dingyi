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
 * @since 2018-08-16
 */
@TableName("app_user")
public class AppUser extends Model<AppUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * app用户名称
     */
    @TableField("app_user_name")
    private String appUserName;

    /**
     * 姓名
     */
    @TableField("user_name")
    private String userName;
    /**
     * 酒店id
     */
    @TableField("business_id")
    private Integer businessId;
    /**
     * 酒店名称
     */
    @TableField("business_name")
    private String businessName;
    /**
     * 用户类型
     */
    @TableField("app_type_id")
    private Integer appTypeId;
    /**
     * app用户绑定手机号
     */
    @TableField("app_user_phone")
    private String appUserPhone;
    /**
     * app用户账户密码
     */
    @TableField("app_user_password")
    private String appUserPassword;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    /**
     * 0,1
     */
    private String status;
    @TableField("app_user_code")
    private String appUserCode;
    /**
     * 区域ID 0表示所有区域
     */
    @TableField("operation_area_id")
    private String operationAreaId;
    /**
     * 0为非品牌权限， 1为有品牌权限
     */
    @TableField("brand_type")
    private Integer brandType;
    @TableField("image_url")
    private String imageUrl;
    private String tag;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppUserName() {
        return appUserName;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
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

    public Integer getAppTypeId() {
        return appTypeId;
    }

    public void setAppTypeId(Integer appTypeId) {
        this.appTypeId = appTypeId;
    }

    public String getAppUserPhone() {
        return appUserPhone;
    }

    public void setAppUserPhone(String appUserPhone) {
        this.appUserPhone = appUserPhone;
    }

    public String getAppUserPassword() {
        return appUserPassword;
    }

    public void setAppUserPassword(String appUserPassword) {
        this.appUserPassword = appUserPassword;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppUserCode() {
        return appUserCode;
    }

    public void setAppUserCode(String appUserCode) {
        this.appUserCode = appUserCode;
    }

    public String getOperationAreaId() {
        return operationAreaId;
    }

    public void setOperationAreaId(String operationAreaId) {
        this.operationAreaId = operationAreaId;
    }

    public Integer getBrandType() {
        return brandType;
    }

    public void setBrandType(Integer brandType) {
        this.brandType = brandType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AppUser{" +
        "id=" + id +
        ", appUserName=" + appUserName +
                ", userName=" + userName +
        ", businessId=" + businessId +
        ", businessName=" + businessName +
        ", appTypeId=" + appTypeId +
        ", appUserPhone=" + appUserPhone +
        ", appUserPassword=" + appUserPassword +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", status=" + status +
        ", appUserCode=" + appUserCode +
        ", operationAreaId=" + operationAreaId +
        ", brandType=" + brandType +
        ", imageUrl=" + imageUrl +
        ", tag=" + tag +
        "}";
    }
}
