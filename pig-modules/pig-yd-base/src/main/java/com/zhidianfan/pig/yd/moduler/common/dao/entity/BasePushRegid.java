package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 极光推送客户的regid
 * </p>
 *
 * @author sherry
 * @since 2018-08-20
 */
@TableName("base_push_regid")
public class BasePushRegid extends Model<BasePushRegid> {

    private static final long serialVersionUID = 1L;

    @Version
    private Integer version;

    /**
     * 使用雪花算法生成主键
     */
    @TableId(type = IdType.ID_WORKER)
    private Long id;
    /**
     * 客户端类型：
     * WX-微信
     * APP-手机
     * PAD-pad预订台
     */
    private String type;
    /**
     * 用户名，一般指手机号
     */
    private String username;
    /**
     * 注册id与设备绑定
     */
    @TableField("registration_id")
    private String registrationId;
    @TableField("business_id")
    private String businessId;
    /**
     * 设备注册时间
     */
    @TableField("reg_time")
    private Date regTime;
    @TableField("on_off")
    private Integer onOff;

    public Integer getOnOff() {
        return onOff;
    }

    public void setOnOff(Integer onOff) {
        this.onOff = onOff;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BasePushRegid{" +
                "version=" + version +
                "id=" + id +
                ", type=" + type +
                ", username=" + username +
                ", registrationId=" + registrationId +
                ", regTime=" + regTime +
                "}";
    }
}
