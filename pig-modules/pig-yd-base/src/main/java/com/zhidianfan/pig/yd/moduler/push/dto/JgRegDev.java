package com.zhidianfan.pig.yd.moduler.push.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * 极光设备注册
 *
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/20
 * @Modified By:
 */

public class JgRegDev implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户端类型：
     * WX-微信
     * APP-手机
     * PAD-pad预订台
     */
    @NotEmpty
    private String type;
    /**
     * 注册id与设备绑定
     */
    @NotEmpty
    private String registrationId;

    /**
     * 酒店id
     */
    @NotEmpty
    private String businessId;


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("registrationId", registrationId)
                .append("businessId", businessId)
                .toString();
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }
}
