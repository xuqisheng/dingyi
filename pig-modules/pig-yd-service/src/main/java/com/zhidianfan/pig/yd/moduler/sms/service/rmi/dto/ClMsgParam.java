package com.zhidianfan.pig.yd.moduler.sms.service.rmi.dto;

import java.util.List;

public class ClMsgParam {
    private List<String> phone;

    private String msg;

    private String sendtime;

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    @Override
    public String toString() {
        return "ClMsgParam{" +
                "phone=" + phone +
                ", msg='" + msg + '\'' +
                ", sendtime='" + sendtime + '\'' +
                '}';
    }
}
