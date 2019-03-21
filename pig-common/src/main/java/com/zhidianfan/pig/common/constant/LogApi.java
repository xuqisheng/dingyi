package com.zhidianfan.pig.common.constant;

import java.util.Date;

import java.io.Serializable;

/**
 * <p>
 * 基础请求响应日志
 * </p>
 *
 * @author sherry
 * @since 2018-09-02
 */
public class LogApi implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 服务模块名
     */
    private String microName;
    /**
     * 请求路径
     */
    private String url;
    private Date reqTime;
    private Date resTime;
    private String reqData;
    private String resData;
    /**
     * 客户端标识
     */
    private String clientId;
    /**
     * 用户标识
     */
    private String userId;
    /**
     * 备注
     */
    private String note;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMicroName() {
        return microName;
    }

    public void setMicroName(String microName) {
        this.microName = microName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getReqTime() {
        return reqTime;
    }

    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }

    public Date getResTime() {
        return resTime;
    }

    public void setResTime(Date resTime) {
        this.resTime = resTime;
    }

    public String getReqData() {
        return reqData;
    }

    public void setReqData(String reqData) {
        this.reqData = reqData;
    }

    public String getResData() {
        return resData;
    }

    public void setResData(String resData) {
        this.resData = resData;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "LogApi{" +
                "id=" + id +
                ", microName=" + microName +
                ", url=" + url +
                ", reqTime=" + reqTime +
                ", resTime=" + resTime +
                ", reqData=" + reqData +
                ", resData=" + resData +
                ", clientId=" + clientId +
                ", userId=" + userId +
                ", note=" + note +
                "}";
    }
}
