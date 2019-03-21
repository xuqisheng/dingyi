package com.zhidianfan.pig.yd.moduler.meituan.service.rmi.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * 极光推送
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/21
 * @Modified By:
 */

public class JgPush implements Serializable {

    //客户端类型
    @NotEmpty
    private String type;
    //用户名
    @NotEmpty
    private String username;
    //消息产生的顺序
    @NotEmpty
    private String msgSeq;
    //酒店ID
    @NotEmpty
    private String businessId;
    //消息
    @NotEmpty
    private String msg;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getMsgSeq() {
        return msgSeq;
    }

    public void setMsgSeq(String msgSeq) {
        this.msgSeq = msgSeq;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
        

    @Override
    public String toString() {
        return "JgPush{" +
                "type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", msgSeq='" + msgSeq + '\'' +
                ", businessId='" + businessId + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
