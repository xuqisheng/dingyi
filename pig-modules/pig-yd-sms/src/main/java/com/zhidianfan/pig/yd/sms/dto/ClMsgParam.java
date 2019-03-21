package com.zhidianfan.pig.yd.sms.dto;

import java.util.List;

public class ClMsgParam {
    private List<String> phone;

    private String msg;

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
}
