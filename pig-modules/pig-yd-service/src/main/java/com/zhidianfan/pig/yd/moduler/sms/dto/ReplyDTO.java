package com.zhidianfan.pig.yd.moduler.sms.dto;

/**
 * 短信回复DTO
 *
 * @author wangyz
 * @version v 0.1 2018/4/10 下午2:27 wangyz Exp $
 */
public class ReplyDTO {
    /*
     * 接收验证的用户名（不是账户名），是按照用户要求配置的名称，可以为空
     */
    private String receiver;
    /*
     * 接收验证的密码，可以为空
     */
    private String pswd;
    /*
     * 上行时间，格式yyMMddHHmm，其中yy=年份的最后两位（00-99）
     */
    private String moTime;
    /*
     * 上行手机号码
     */
    private String mobile;
    /*
     * 上行内容，内容经过URLEncode编码(UTF-8)
     */
    private String msg;
    /*
     * 运营商通道码
     */
    private String destcode;
    /*
     * 平台通道码
     */
    private String spCode;
    /*
     * 253平台收到运营商回复上行短信的时间，格式yyyyMMDDhhmmss
     */
    private String notifyTime;
    /*
     * 是否为长短信的一部分，1:是，0，不是。不带该参数，默认为普通短信
     */
    private String isems;
    /*
     * isems为1时，本参数以ASCII码形式显示长短信的头信息。
     * 用“,”隔开，分为三个部分，
     * 第一部分标识该条长短信的ID（该ID为短信中心生成）；
     * 第二部分，表明该长短信的总条数（pk_total）；
     * 第三部分，该条短信为该长短信的第几条(pk_number)。
     * 例如：234,4,1，该短信的ID为234,该长短信的总长度为4条，1，当前为第一条。
     */
    private String emshead;

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

    public String getMoTime() {
        return moTime;
    }

    public void setMoTime(String moTime) {
        this.moTime = moTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDestcode() {
        return destcode;
    }

    public void setDestcode(String destcode) {
        this.destcode = destcode;
    }

    public String getSpCode() {
        return spCode;
    }

    public void setSpCode(String spCode) {
        this.spCode = spCode;
    }

    public String getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getIsems() {
        return isems;
    }

    public void setIsems(String isems) {
        this.isems = isems;
    }

    public String getEmshead() {
        return emshead;
    }

    public void setEmshead(String emshead) {
        this.emshead = emshead;
    }

    @Override
    public String toString() {
        return "ReplyDTO{" +
                "receiver='" + receiver + '\'' +
                ", pswd='" + pswd + '\'' +
                ", moTime='" + moTime + '\'' +
                ", mobile='" + mobile + '\'' +
                ", msg='" + msg + '\'' +
                ", destcode='" + destcode + '\'' +
                ", spCode='" + spCode + '\'' +
                ", notifyTime='" + notifyTime + '\'' +
                ", isems='" + isems + '\'' +
                ", emshead='" + emshead + '\'' +
                '}';
    }
}
