package com.zhidianfan.pig.yd.moduler.wechat.util;

/**
 * 订单模板
 *
 * @author wangyz
 * @version v 0.1 2019-04-15 16:39 wangyz Exp $
 */
public enum OrderTemplate {
    ORDER_RESV_SUCCESS("4wmP_1PvP3Wr17dyTm-v5bioYFqpNxJ_kB6nFxlvQCI", "预定成功"),
    ORDER_RESV_REMIND("cHyDgiQtKO8H0KFEHUSDxM7I7zqrDzGcOiMmNX7kziM", "预约提醒"),
    ORDER_RESV_HOTEL_CANCEL("mMLp3YGpYV_9avUP-P8VegmpRoTGOVSlorH6zb8QKfw", "订座失败通知");


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