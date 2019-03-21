package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Data;

/**
 * @Author: huzp
 * @Date: 2018/11/12 14:46
 * @DESCRIPTION 酒店客戶价值信息
 */
@Data
public class VipValueDTO {

    /**
     * 酒店id
     */
    private Integer businessId;

    /**
     * 活跃会员天数
     */
    private Integer activeDays;

    /**
     * 沉睡会员开始天数
     */
    private Integer sleepDaysBegin;

    /**
     * 沉睡会员结束天数
     */
    private Integer sleepDaysEnd;

    /**
     * 流失天数
     */
    private Integer flowDays;

    /**
     * 恶意会员退订次数
     */
    private Integer unorderCount;

    /**
     * 高价值会员天数天数
     */
    private Integer highValueDays;

    /**
     * 成功预订次数
     */
    private Integer highValueCounts;

    /**
     * 消费总金额
     */
    private Integer highValueAmount;

}
