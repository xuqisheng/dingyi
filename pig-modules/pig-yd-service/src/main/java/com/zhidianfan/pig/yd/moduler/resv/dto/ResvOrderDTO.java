package com.zhidianfan.pig.yd.moduler.resv.dto;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderAndroid;
import lombok.Data;

import java.util.Date;

/**
 * @Author panqianyong
 * @Description
 * @Date Create in 2018/9/5
 * @Modified By:
 */
@Data
public class ResvOrderDTO extends ResvOrderAndroid {
    /**
     * 酒店id
     */
    Integer businessId;
    /**
     * 餐区id
     */
    Integer tableAreaId;
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
     * 订单来源
     */
    String source;
    /**
     * 餐别
     */
    Integer mealTypeId;
    /**
     * 预定台id
     */
    Integer deviceUserId;
    /**
     * 操作（订单创建）日期（起）
     */
    Date operateDateStart;
    /**
     * 操作（订单创建）日期（止）
     */
    Date operateDateEnd;
    /**
     * 订餐类别
     */
    Integer resvOrderType;
    /**
     * 退订理由
     */
    String unorderReason;
    /**
     * 用餐日期（截止，当前日期的前一个月）
     */
    Date resvDateEndLimit;
    /**
     * 订单退订时间（起）
     */
    Date unorderDateStart;
    /**
     * 订单退订时间（止）
     */
    Date unorderDateEnd;
    /**
     * 是否入座
     */
    private int isSeat;

}
