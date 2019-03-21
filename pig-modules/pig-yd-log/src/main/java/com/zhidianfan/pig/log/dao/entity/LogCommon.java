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
 * 通用日志表
 * </p>
 *
 * @author sherry
 * @since 2018-10-31
 */
@TableName("log_common")
public class LogCommon extends Model<LogCommon> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    /**
     * 微服务名
     */
    @TableField("server_name")
    private String serverName;
    /**
     * 类的全限定名
     */
    @TableField("class_name")
    private String className;
    /**
     * 日志产生时间
     */
    @TableField("log_time")
    private Date logTime;
    /**
     * 日志等级，debug、info、warn、error
     */
    @TableField("log_level")
    private String logLevel;
    /**
     * 日志内容
     */
    @TableField("log_data")
    private String logData;


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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogData() {
        return logData;
    }

    public void setLogData(String logData) {
        this.logData = logData;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "LogCommon{" +
        "id=" + id +
        ", serverName=" + serverName +
        ", className=" + className +
        ", logTime=" + logTime +
        ", logLevel=" + logLevel +
        ", logData=" + logData +
        "}";
    }
}
