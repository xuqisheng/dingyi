package com.zhidianfan.pig.yd.moduler.sms.dto;

/**
 * 短信模板枚举
 */
public enum MessageTemplateEnum {

    RESV_MESSAGE(1, "预定短信"),
    CONFIRM_MESSAGE(2, "确认预定短信"),
    SEAT_MESSAGE(3, "入座短信"),
    CHANGE_TABLE_MESSAGE(4, "换桌短信"),
    CANCEL_MESSAGE(5, "退订短信");

    /**
     * 编号
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;

    MessageTemplateEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
