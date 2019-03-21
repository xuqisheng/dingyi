package com.zhidianfan.pig.yd.sms.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 乐信短信发送状态回调DTO
 *
 * @Author 李凌峰
 * @Description
 * @Date 2018/10/29 0029 下午 1:39
 * @Modified By:
 */
public class    LxCallBackDTO {
    /**
     * 客户账号
     */
    private String AccountID;
    /**
     *MessageID(短信提交成功后会生成MsgID)
     */
    @NotBlank(message = "MsgID不能为空")
    private String MsgID;
    /**
     *客户提交手机号码
     */
    private String MobilePhone;
    /**
     *状态报告信息
     */
    private String ReportResultInfo;
    /**
     *状态报告，bit类型,TRUE/FALSE
     */
    private String ReportState;
    /**
     *状态报告时间
     */
    private String ReportTime;
    /**
     *信息发送结果信息
     */
    private String SendResultInfo;
    /**
     *信息发送状态,供应商送达运营商网关的状态，True是成功，False是失败
     */
    private String SendState;
    /**
     *信息发送时间
     */
    private String SendedTime;
    /**
     *长号码（下发端口号）
     */
    private long SPNumber;
    /**
     * 自定义参数（可选），与短信提交接口
     */
    private String ClientMsgId;

    public String getAccountID() {
        return AccountID;
    }

    public void setAccountID(String accountID) {
        AccountID = accountID;
    }

    public String getMsgID() {
        return MsgID;
    }

    public void setMsgID(String msgID) {
        MsgID = msgID;
    }

    public String getMobilePhone() {
        return MobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        MobilePhone = mobilePhone;
    }

    public String getReportResultInfo() {
        return ReportResultInfo;
    }

    public void setReportResultInfo(String reportResultInfo) {
        ReportResultInfo = reportResultInfo;
    }

    public String getReportState() {
        return ReportState;
    }

    public void setReportState(String reportState) {
        ReportState = reportState;
    }



    public String getSendResultInfo() {
        return SendResultInfo;
    }

    public void setSendResultInfo(String sendResultInfo) {
        SendResultInfo = sendResultInfo;
    }

    public String getSendState() {
        return SendState;
    }

    public void setSendState(String sendState) {
        SendState = sendState;
    }

    public String getReportTime() {
        return ReportTime;
    }

    public void setReportTime(String reportTime) {
        ReportTime = reportTime;
    }

    public String getSendedTime() {
        return SendedTime;
    }

    public void setSendedTime(String sendedTime) {
        SendedTime = sendedTime;
    }

    public long getSPNumber() {
        return SPNumber;
    }

    public void setSPNumber(long SPNumber) {
        this.SPNumber = SPNumber;
    }

    public String getClientMsgId() {
        return ClientMsgId;
    }

    public void setClientMsgId(String clientMsgId) {
        ClientMsgId = clientMsgId;
    }

    @Override
    public String toString() {
        return "LxCallBackDTO{" +
                "AccountID='" + AccountID + '\'' +
                ", MsgID='" + MsgID + '\'' +
                ", MobilePhone='" + MobilePhone + '\'' +
                ", ReportResultInfo='" + ReportResultInfo + '\'' +
                ", ReportState='" + ReportState + '\'' +
                ", ReportTime='" + ReportTime + '\'' +
                ", SendResultInfo='" + SendResultInfo + '\'' +
                ", SendState='" + SendState + '\'' +
                ", SendedTime='" + SendedTime + '\'' +
                ", SPNumber=" + SPNumber +
                ", ClientMsgId='" + ClientMsgId + '\'' +
                '}';
    }
}
