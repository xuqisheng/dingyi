package com.zhidianfan.pig.common.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * <p>
 * 设备登陆登出日志
 * </p>
 *
 * @author sherry
 * @since 2018-11-15
 */
public class LogDevLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 设备类型
PAD-pad预订台
APP-手机预订台
     */
    @NotBlank
    private String type;

    /**
     * 操作
     * in-登陆
     * out-登出
     */
    @NotBlank
    private String loginType;

    /**
     * 当前软件版本
     */
    @NotBlank
    private String version;
    /**
     * 操作员
     */
    private String username;
    /**
     * 酒店名
     */
    @NotBlank
    private String business;
    @NotBlank
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

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
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
    public String toString() {
        return "LogDevLogin{" +
        "id=" + id +
        ", type=" + type +
        ", version=" + version +
        ", loginType=" + loginType +
        ", username=" + username +
        ", business=" + business +
        ", businessId=" + businessId +
        "}";
    }
}
