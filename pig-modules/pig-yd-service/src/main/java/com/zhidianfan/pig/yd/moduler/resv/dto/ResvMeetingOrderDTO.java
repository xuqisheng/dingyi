package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author panqianyong
 * @Description
 * @Date Create in 2018/9/5
 * @Modified By:
 */
@Data
public class ResvMeetingOrderDTO {
    /**
     * 酒店id
     */
    Integer businessId;
    /**
     * 宴会类型
     */
    Integer resvMeetingOrderType;
    /**
     * 营销经理
     */
    Integer appUserId;
    /**
     * 客户姓名或手机
     */
    String vipNameOrPhone;
    /**
     * 用餐日期（起）
     */
    Date resvDateStart;
    /**
     * 用餐日期（止）
     */
    Date resvDateEnd;
    /**
     * 吉日类型
     */
    Integer luckyDayType;
    /**
     * 订单状态
     */
    Integer status;
    /**
     * 用餐日期（截止，当前日期的前一个月）
     */
    Date resvDateEndLimit;
    /**
     * 回访状态（0：未回访，1：未处理，2：已处理）
     */
    int callOnStatus;

}
