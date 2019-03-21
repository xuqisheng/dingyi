package com.zhidianfan.pig.yd.sms.dto;


/**
 * @Author Conan
 * @Description
 * @Date: 2018/9/11 0011 下午 4:00
 * @Modified By:
 */
public class SmsSendResDTO {

    /**
     * 乐信
     * {
     *     "MsgState": "无效计费条数,号码不规则,过滤[1:111111,]",
     *     "State": 1023,
     *     "MsgID": "0",
     *     "Reserve": 0
     * }
     *
     * {
     *     "MsgState": "提交成功",
     *     "State": 0,
     *     "MsgID": "1810291703095226144",
     *     "Reserve": 1
     * }
     */

    //响应时间
    private String time;
    //消息id
    private Long msgId;
    //状态码说明（成功返回空）
    private String errorMsg;
    //状态码（详细参考提交响应状态码）
    private String code;
    //状态信息
    private String text;
    //状态
    private String status;
    //是否低于阈值
    private Boolean remind;
    //酒店设定的阈值
    private Integer minSmsNum;

    private String msg;
    //1 创蓝 2 乐信
    private int operator;

    //乐信当前短信内容拆分条数
    private int Reserve;

    //乐信返回状态描述
    private String MsgState;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
    }

    public Integer getMinSmsNum() {
        return minSmsNum;
    }

    public void setMinSmsNum(Integer minSmsNum) {
        this.minSmsNum = minSmsNum;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public int getReserve() {
        return Reserve;
    }

    public void setReserve(int reserve) {
        Reserve = reserve;
    }

    public String getMsgState() {
        return MsgState;
    }

    public void setMsgState(String msgState) {
        MsgState = msgState;
    }
}
