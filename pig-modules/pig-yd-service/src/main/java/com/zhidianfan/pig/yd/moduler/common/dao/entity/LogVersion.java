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
 * 叮叮/手机端账号登录版本记录表 log_version
 * </p>
 *
 * @author 李凌峰
 * @since 2019-03-12
 */
@TableName("log_version")
public class LogVersion extends Model<LogVersion> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * MOBILE-手机端 ANDROID-安卓电话机
     */
    @TableField("client_type")
    private String clientType;
    /**
     * 登陆账号
     */
    @TableField("login_user")
    private String loginUser;
    /**
     * 登陆软件版本
     */
    @TableField("login_version")
    private String loginVersion;
    /**
     * 当前最新版本
     */
    @TableField("lastest_version")
    private String lastestVersion;
    /**
     * 0-不可用 1-可用
     */
    @TableField("is_enable")
    private Integer isEnable;
    /**
     * 第一次登陆时间
     */
    @TableField("created_time")
    private Date createdTime;
    /**
     * 最近登陆时间
     */
    @TableField("updated_time")
    private Date updatedTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getLoginVersion() {
        return loginVersion;
    }

    public void setLoginVersion(String loginVersion) {
        this.loginVersion = loginVersion;
    }

    public String getLastestVersion() {
        return lastestVersion;
    }

    public void setLastestVersion(String lastestVersion) {
        this.lastestVersion = lastestVersion;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "LogVersion{" +
        "id=" + id +
        ", clientType=" + clientType +
        ", loginUser=" + loginUser +
        ", loginVersion=" + loginVersion +
        ", lastestVersion=" + lastestVersion +
        ", isEnable=" + isEnable +
        ", createdTime=" + createdTime +
        ", updatedTime=" + updatedTime +
        "}";
    }
}
