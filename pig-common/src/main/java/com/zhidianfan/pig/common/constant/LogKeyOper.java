package com.zhidianfan.pig.common.constant;

import java.util.Date;

import java.io.Serializable;

/**
 * <p>
 * 关键操作日志记录
 * </p>
 *
 * @author sherry
 * @since 2018-10-31
 */
public class LogKeyOper implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 微服务名
     */
    private String serverName;
    /**
     * 操作发起的设备

门店后台、总后台、集团后台、手机预订台、pad预订台、Android电话机......
     */
    private String deviceName;
    /**
     * 操作人，用户名，姓名
     */
    private String username;
    private Date doTime;
    /**
     * 操作类型，
0-新增、1-删除，2-修改，3-查询
     */
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

    public LogKeyOper() {
    }

    public LogKeyOper(String serverName, String deviceName, String username, Date doTime, Integer doType, String note) {
        this.serverName = serverName;
        this.deviceName = deviceName;
        this.username = username;
        this.doTime = doTime;
        this.doType = doType;
        this.note = note;
    }

    public LogKeyOper(String serverName, String deviceName, String username, Integer doType, String note) {
        this.serverName = serverName;
        this.deviceName = deviceName;
        this.username = username;
        this.doTime = new Date();
        this.doType = doType;
        this.note = note;
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
