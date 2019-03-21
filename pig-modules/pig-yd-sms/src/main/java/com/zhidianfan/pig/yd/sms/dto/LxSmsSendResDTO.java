package com.zhidianfan.pig.yd.sms.dto;

/**
 * @Author 李凌峰
 * @Description
 * @Date 2018/10/24 0024 下午 3:38
 * @Modified By:
 */
public class LxSmsSendResDTO {
    /**
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

    //提交结果值,返回值为0时，表示提交成功
    private int State;
    //短信批次号
    private String MsgID;
    //返回状态描述
    private String MsgState;
    //当前短信内容拆分条数
    private int Reserve;

    //当返回值不为0时，自定义状态码
    private String code;

    private String msg;

    private String status;

    // 1 创蓝 2 乐信
    private int operator;

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getMsgID() {
        return MsgID;
    }

    public void setMsgID(String msgID) {
        MsgID = msgID;
    }

    public String getMsgState() {
        return MsgState;
    }

    public void setMsgState(String msgState) {
        MsgState = msgState;
    }

    public int getReserve() {
        return Reserve;
    }

    public void setReserve(int reserve) {
        Reserve = reserve;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }
}
