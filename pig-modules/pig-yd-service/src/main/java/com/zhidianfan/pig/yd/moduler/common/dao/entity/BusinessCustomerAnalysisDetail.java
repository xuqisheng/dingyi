package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import lombok.Data;

/**
 * @author wangyz
 * @version v 0.1 2019-03-21 16:27 wangyz Exp $
 */
@Data
public class BusinessCustomerAnalysisDetail {

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer vipId;

    /**
     * 用户名称
     */
    private String vipName;

    /**
     * 用户手机号码
     */
    private String vipPhone;

    /**
     * 酒店id
     */
    private Integer businessId;

    /**
     * 用户归类  1:活跃用户  2:沉睡用户  3:流失用户  4:意向用户   5:恶意用户  6:高价值用户  7:唤醒   8:新用户
     */
    private Integer vipValueType;

    /**
     * 日期  yyyy-MM
     */
    private String date;

}
