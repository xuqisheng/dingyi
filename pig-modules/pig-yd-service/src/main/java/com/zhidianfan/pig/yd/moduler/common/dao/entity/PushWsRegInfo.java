package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author qqx
 * @since 2018-11-27
 */
@TableName("push_ws_reg_info")
public class PushWsRegInfo extends Model<PushWsRegInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 代理商id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("hotel_id")
    private Integer hotelId;
    @TableField("device_type")
    private String deviceType;

    @TableField("session_id")
    private String sessionId;

    @TableField("reg_state")
    private Integer regState;

    @TableField("create_date")
    private Date createDate;

    @TableField("update_date")
    private Date updateDate;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getRegState() {
        return regState;
    }

    public void setRegState(Integer regState) {
        this.regState = regState;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "PushWsRegInfo{" +
                "id=" + id +
                ", hotelId=" + hotelId +
                ", deviceType='" + deviceType + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", regState=" + regState +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
