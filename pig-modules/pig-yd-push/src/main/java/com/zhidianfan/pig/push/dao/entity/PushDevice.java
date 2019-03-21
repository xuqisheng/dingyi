package com.zhidianfan.pig.push.dao.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ljh
 * @since 2018-11-02
 */
@TableName("push_device")
public class PushDevice extends Model<PushDevice> {

    private static final long serialVersionUID = 1L;

    /**
     * 自动编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 设备id
     */
    @TableField("registration_id")
    private String registrationId;
    /**
     * 设备类型 android   ios
     */
    @TableField("device_type")
    private String deviceType;
    /**
     * 设备名称（别名）
     */
    private String alias;
    /**
     * 标签（, 分隔）
     */
    private String tag;
    /**
     * 设备在线状态
     */
    @TableField("online_status")
    private Integer onlineStatus;
    /**
     * 是否启用
     */
    @TableField("is_enable")
    private Integer isEnable;
    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 手机号码
     */
    private String mobile;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PushDevice{" +
        "id=" + id +
        ", registrationId=" + registrationId +
        ", deviceType=" + deviceType +
        ", alias=" + alias +
        ", tag=" + tag +
        ", onlineStatus=" + onlineStatus +
        ", isEnable=" + isEnable +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", mobile=" + mobile +
        "}";
    }
}
