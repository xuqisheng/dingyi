package com.zhidianfan.pig.yd.moduler.resv.bo;



import lombok.Data;

import java.math.BigDecimal;


@Data
/**
 * 价值客户统计
 */
public class VipValueBo {

    /**
     * 客户价值名称
     */
    private String vipValueName;

    /**
     * 数量
     */
    private Integer peopleNum;

    /**
     * 价值id
     */
    private Integer vipValueId;


    /**
     * 1活跃用户 2沉睡用户 3流失用户 4意向用户 5恶意用户 6高价值用户
     */
    private Integer type;

    /**
     * 退订次数
     */
    private Integer unorderCount;

    /**
     *活跃统计天数
     */
    private Integer activeDays;
    /**
     * 流失天数
     */
    private Integer flowDays;

    /**
     * 高价值统计时间
     */
    private Integer highValueDays;

    /**
     * 高价值预订成功次数
     */
    private Integer highValueCounts;

    /**
     * 高价值消费金额额度
     */
    private BigDecimal highValueAmount;


}
