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
 * @since 2018-08-27
 */
@TableName("device_user")
public class DeviceUser extends Model<DeviceUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 设备用户
     */
    @TableField("device_user_name")
    private String deviceUserName;
    @TableField("business_id")
    private Integer businessId;
    @TableField("business_name")
    private String businessName;
    /**
     * 设备用户类型id
     */
    @TableField("device_type_id")
    private Integer deviceTypeId;
    /**
     * 设备用户账号
     */
    @TableField("device_user_phone")
    private String deviceUserPhone;
    /**
     * 设备用户密码
     */
    @TableField("device_user_password")
    private String deviceUserPassword;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    /**
     * 0,1
     */
    private String status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceUserName() {
        return deviceUserName;
    }

    public void setDeviceUserName(String deviceUserName) {
        this.deviceUserName = deviceUserName;
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

    public Integer getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Integer deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getDeviceUserPhone() {
        return deviceUserPhone;
    }

    public void setDeviceUserPhone(String deviceUserPhone) {
        this.deviceUserPhone = deviceUserPhone;
    }

    public String getDeviceUserPassword() {
        return deviceUserPassword;
    }

    public void setDeviceUserPassword(String deviceUserPassword) {
        this.deviceUserPassword = deviceUserPassword;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DeviceUser{" +
        "id=" + id +
        ", deviceUserName=" + deviceUserName +
        ", businessId=" + businessId +
        ", businessName=" + businessName +
        ", deviceTypeId=" + deviceTypeId +
        ", deviceUserPhone=" + deviceUserPhone +
        ", deviceUserPassword=" + deviceUserPassword +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", status=" + status +
        "}";
    }
}
