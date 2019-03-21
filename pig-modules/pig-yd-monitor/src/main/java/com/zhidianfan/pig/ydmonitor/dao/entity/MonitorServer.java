package com.zhidianfan.pig.ydmonitor.dao.entity;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2018-11-07
 */
@TableName("monitor_server")
public class MonitorServer extends Model<MonitorServer> {

    private static final long serialVersionUID = 1L;

    /**
     * 自动编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 服务名称
     */
    @TableField("server_name")
    private String serverName;
    /**
     * 域名或ip地址
     */
    private String address;
    /**
     * 监听的端口号
     */
    private Integer port;
    /**
     * 是否启用
     */
    @TableField("is_enable")
    private Integer isEnable;


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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MonitorServer{" +
        "id=" + id +
        ", serverName=" + serverName +
        ", address=" + address +
        ", port=" + port +
        ", isEnable=" + isEnable +
        "}";
    }
}
