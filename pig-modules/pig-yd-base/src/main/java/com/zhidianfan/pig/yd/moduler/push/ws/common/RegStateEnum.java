package com.zhidianfan.pig.yd.moduler.push.ws.common;

/**
 * @author sjl
 * @date 2019-02-28 17:12:13
 * webSocket 推送注册状态枚举
 */
public enum RegStateEnum {
    /**
     * 活动
     */
    ACTIVE(1, "活动"),

    /**
     * 断开
     */
    DISCONNECT(0, "断开连接");

    private int statCode;
    private String description;

    RegStateEnum(int statCode, String description) {
        this.statCode = statCode;
        this.description = description;
    }

    public int getStatCode() {
        return statCode;
    }

    public String getDescription() {
        return description;
    }
}

