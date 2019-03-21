package com.zhidianfan.pig.log.dao.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 设备登陆登出日志
 * </p>
 *
 * @author sherry
 * @since 2018-11-15
 */
@TableName("log_dev_login")
public class LogDevLogin extends Model<LogDevLogin> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    /**
     * 设备类型
PAD-pad预订台
APP-手机预订台
     */
    private String type;
    /**
     * 登陆时间
     */
    @TableField("login_time")
    private Date loginTime;
    /**
     * 登出时间
     */
    @TableField("logout_time")
    private Date logoutTime;
    /**
     * 当前软件版本
     */
    private String version;
    /**
     * 操作员
     */
    private String username;
    /**
     * 酒店名
     */
    private String business;
    @TableField("business_id")
    private String businessId;


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

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "LogDevLogin{" +
        "id=" + id +
        ", type=" + type +
        ", loginTime=" + loginTime +
        ", logoutTime=" + logoutTime +
        ", version=" + version +
        ", username=" + username +
        ", business=" + business +
        ", businessId=" + businessId +
        "}";
    }
}
