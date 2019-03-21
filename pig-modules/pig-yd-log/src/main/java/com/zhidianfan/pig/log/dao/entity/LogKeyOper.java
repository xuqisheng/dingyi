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
 * 关键操作日志记录
 * </p>
 *
 * @author sherry
 * @since 2018-10-31
 */
@TableName("log_key_oper")
public class LogKeyOper extends Model<LogKeyOper> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    /**
     * 微服务名
     */
    @TableField("server_name")
    private String serverName;
    /**
     * 操作发起的设备

门店后台、总后台、集团后台、手机预订台、pad预订台、Android电话机......
     */
    @TableField("device_name")
    private String deviceName;
    /**
     * 操作人，用户名，姓名
     */
    private String username;
    @TableField("do_time")
    private Date doTime;
    /**
     * 操作类型，
0-新增、1-删除，2-修改，3-查询
     */
    @TableField("do_type")
    private Integer doType;
    /**
     * 操作详情描述
     */
    private String note;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    public Integer getDoType() {
        return doType;
    }

    public void setDoType(Integer doType) {
        this.doType = doType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "LogKeyOper{" +
        "id=" + id +
        ", serverName=" + serverName +
        ", deviceName=" + deviceName +
        ", username=" + username +
        ", doTime=" + doTime +
        ", doType=" + doType +
        ", note=" + note +
        "}";
    }
}
