package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * Websockt 推送注册信息表
 * </p>
 *
 * @author sjl
 * @since 2019-03-18
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("push_ws_reg_info")
public class PushWsRegInfo extends Model<PushWsRegInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    /**
     * 酒店id
     */
    @TableField("hotel_id")
    private Integer hotelId;
    /**
     * 设备类型
     */
    @TableField("device_type")
    private String deviceType;
    /**
     * 当前会话id
     */
    @TableField("session_id")
    private String sessionId;
    /**
     * 小程序端唯一id
     */
    private String openid;
    /**
     * 注册状态 0-断开 1-活动
     */
    @TableField("reg_state")
    private Integer regState;
    /**
     * 创建时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;
    /**
     * 更新时间
     */
    @TableField("update_date")
    private LocalDateTime updateDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getRegState() {
        return regState;
    }

    public void setRegState(Integer regState) {
        this.regState = regState;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PushWsRegInfo{" +
        "id=" + id +
        ", hotelId=" + hotelId +
        ", deviceType=" + deviceType +
        ", sessionId=" + sessionId +
        ", openid=" + openid +
        ", regState=" + regState +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        "}";
    }
}
