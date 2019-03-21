package com.zhidianfan.pig.ydmonitor.dao.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhoubeibei
 * @since 2018-11-08
 */
@TableName("monitor_log")
public class MonitorLog extends Model<MonitorLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 自定编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 服务名称
     */
    @TableField("server_name")
    private String serverName;
    /**
     * ip地址或域名
     */
    private String address;
    /**
     * 端口号
     */
    private Integer port;
    /**
     * 服务是否可用
     */
    @TableField("is_enable")
    private Integer isEnable;
    /**
     * 是否发消息通知
     */
    @TableField("is_send_message")
    private Integer isSendMessage;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getIsSendMessage() {
        return isSendMessage;
    }

    public void setIsSendMessage(Integer isSendMessage) {
        this.isSendMessage = isSendMessage;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MonitorLog{" +
        "id=" + id +
        ", serverName=" + serverName +
        ", address=" + address +
        ", port=" + port +
        ", isEnable=" + isEnable +
        ", isSendMessage=" + isSendMessage +
        ", createTime=" + createTime +
        "}";
    }
}
