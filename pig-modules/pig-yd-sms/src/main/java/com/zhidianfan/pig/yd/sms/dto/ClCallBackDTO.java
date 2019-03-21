package com.zhidianfan.pig.yd.sms.dto;

/**
 * 创蓝短信发送状态回调DTO
 *
 * @author wangyz
 * @version v 0.1 2018/4/9 下午2:03 wangyz Exp $
 */
public class ClCallBackDTO {
    /*
     *接收验证的用户名（不是账户名），是按照用户要求配置的名称，可以为空
     */
    private String receiver;
    /*
     * 接收验证的密码，可以为空
     */
    private String pswd;
    /*
     *消息id
     */
    private String msgid;
    /*
     *运营商返回的状态更新时间，格式YYMMddHHmm，其中YY=年份的最后两位（00-99）
     */
    private String reportTime;
    /*
     *接收短信的手机号码
     */
    private String mobile;
    /*
     *253平台收到运营商回复状态报告的时间，格式yyyyMMddHHmmss
     */
    private String notifyTime;
    /*
     *用户在提交该短信时提交的uid参数，未提交则无该参数
     */
    private String uid;
    /*
     *状态
     */
    private String status;
    /*
     *状态说明，内容经过URLEncode编码(UTF-8)
     */
    private String statusDesc;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }


    @Override
    public String toString() {
        return "CallBackDTO{" +
                "receiver='" + receiver + '\'' +
                ", pswd='" + pswd + '\'' +
                ", msgid='" + msgid + '\'' +
                ", reportTime='" + reportTime + '\'' +
                ", mobile='" + mobile + '\'' +
                ", notifyTime='" + notifyTime + '\'' +
                ", uid='" + uid + '\'' +
                ", status='" + status + '\'' +
                ", statusDesc='" + statusDesc + '\'' +
                '}';
    }
}
