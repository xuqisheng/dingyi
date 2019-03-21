package com.zhidianfan.pig.push.constants.enmus;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/23
 * @Modified By:
 */
public enum MonitorPushEnum {
    MONITOR_HZ("换桌",6),
    MONITOR_TZ("通知",4),
    MONITOR_BB("报备",3);

    private String name;
    private Integer code;

    MonitorPushEnum(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }
}
