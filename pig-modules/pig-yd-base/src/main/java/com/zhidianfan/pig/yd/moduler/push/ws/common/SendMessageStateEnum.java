package com.zhidianfan.pig.yd.moduler.push.ws.common;

/**
 * @author sjl
 * @date 2019-03-01 09:53:07
 */
public enum SendMessageStateEnum {

    /**
     * 发送成功
     */
    SUCCESS(0,"发送成功"),

    /**
     * 发送失败
     */
    FAIL(1, "发送失败"),

    /**
     * 发送出现异常
     */
    ERROR(2, "发送异常");

    private Integer statCode;
    private String description;

    SendMessageStateEnum(Integer stateCode, String description) {
        this.statCode = stateCode;
        this.description = description;
    }

    public Integer getStatCode() {
        return statCode;
    }

    public String getDescription() {
        return description;
    }

}
