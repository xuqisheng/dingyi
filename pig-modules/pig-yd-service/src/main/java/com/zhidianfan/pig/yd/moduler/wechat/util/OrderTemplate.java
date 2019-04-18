package com.zhidianfan.pig.yd.moduler.wechat.util;

/**
 * 订单模板
 *
 * @author wangyz
 * @version v 0.1 2019-04-15 16:39 wangyz Exp $
 */
public enum OrderTemplate {
    ORDER_RESV_SUCCESS("jyVhDFUxT7GOPMglkyGeaInP6Gj0VIS4nZhs2wsBTjs", "预定成功"),
    ORDER_RESV_RESULT("yQO05VNN8dkGVopzDOAjHFDzAjAy6LpdBLXZNO-QEoU", "客户取消"),
    ORDER_RESV_HOTEL_CANCEL("mMLp3YGpYV_9avUP-P8VegmpRoTGOVSlorH6zb8QKfw", "商家取消");


    OrderTemplate(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 模板wechat对应编码
     */
    private String code;

    /**
     * 注释
     */
    private String desc;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}