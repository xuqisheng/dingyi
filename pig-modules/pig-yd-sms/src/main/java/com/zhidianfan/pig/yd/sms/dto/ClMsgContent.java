package com.zhidianfan.pig.yd.sms.dto;

/**
 * 创蓝短信
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/30
 * @Modified By:
 */

public class ClMsgContent {
    /**
     * account : N6000001
     * password : 123456
     * msg : 【253】您的验证码是：2530
     * phone : 15800000000
     * sendtime : 201704101400
     * report : true
     * extend : 555
     * uid : 批次编号-场景名（英文或者拼音）
     */

    private String account;
    private String password;
    private String msg;
    private String phone;
    private String sendtime;
    private String report;
    private String extend;
    private String uid;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
