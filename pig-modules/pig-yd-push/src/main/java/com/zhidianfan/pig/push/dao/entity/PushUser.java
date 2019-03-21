package com.zhidianfan.pig.push.dao.entity;

import com.baomidou.mybatisplus.enums.FieldFill;
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
 * @author ljh
 * @since 2018-11-02
 */
@TableName("push_user")
public class PushUser extends Model<PushUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 自动编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 酒店id
     */
    @TableField("business_id")
    private Integer businessId;
    /**
     * 设备唯一识别id
     */
    @TableField("device_id")
    private Integer deviceId;
    /**
     * 设备唯一识别码
     */
    @TableField("registration_id")
    private String registrationId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 是否启用接收推送
     */
    @TableField("is_enable")
    private Integer isEnable;
    /**
     * 客户端类型
     */
    @TableField("client_type")
    private String clientType;
    /**
     * 创建时间，设备注册时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PushUser{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", deviceId=" + deviceId +
        ", registrationId=" + registrationId +
        ", username=" + username +
        ", isEnable=" + isEnable +
        ", clientType=" + clientType +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
