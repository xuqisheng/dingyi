package com.zhidianfan.pig.yd.moduler.resv.enums;

public enum DeviceType {


    ANDROIDPHONE("1","ANDROID_PHONE"),
    SMALLAPP("2","SMALL_APP");

    public String code;

    public String desc;
    DeviceType(String code, String desc) {
        this.code=code;
        this.desc=desc;
    }
}
