package com.zhidianfan.pig.common.constant;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 通用日志表
 * </p>
 *
 * @author sherry
 * @since 2018-10-31
 */
public class LogCommon implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 微服务名
     */
    private String serverName;
    /**
     * 类的全限定名
     */
    private String className;
    /**
     * 日志产生时间
     */
    private Date logTime;
    /**
     * 日志等级，debug、info、warn、error
     */
    private String logLevel;
    /**
     * 日志内容
     */
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

    public LogCommon() {
    }

    public LogCommon(String serverName, String className, Date logTime, String logLevel, String logData) {
        this.serverName = serverName;
        this.className = className;
        this.logTime = logTime;
        this.logLevel = logLevel;
        this.logData = logData;
    }

    public LogCommon(String serverName, String className, String logLevel, String logData) {
        this.serverName = serverName;
        this.className = className;
        this.logTime = new Date();
        this.logLevel = logLevel;
        this.logData = logData;
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
