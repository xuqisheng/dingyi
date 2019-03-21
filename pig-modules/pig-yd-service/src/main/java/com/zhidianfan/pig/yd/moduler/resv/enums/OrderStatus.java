package com.zhidianfan.pig.yd.moduler.resv.enums;

import lombok.Data;

/**
 * 订单状态类型
 * @author LJH
 * @version 1.0
 * @Date 2018/9/21 15:36
 */
public enum OrderStatus {

    RESERVE("1","已预定"),
    HAVE_SEAT("2","已入座"),
    SETTLE_ACCOUNTS("3","已结账"),
    DEBOOK("4","已退订"),
    LOCK("5","锁定"),
    UNLOCK("6","解锁"),
    CONFIRMRESE("7","确认预定");

    public String code;

    public String label;
    OrderStatus(String code, String label) {
        this.code=code;
        this.label=label;
    }

}
