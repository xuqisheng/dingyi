package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import lombok.Data;

import java.sql.Date;

/**
 * 客户分析结果对象
 *
 * @author wangyz
 * @version v 0.1 2019-03-19 14:28 wangyz Exp $
 */
@Data
public class BusinessCustomerAnalysis {

    /**
     * 自增id
     */
    private Integer id;
    /**
     * 保存日期  yyyy-MM
     */
    private String date;
    /**
     * 酒店id
     */
    private Integer businessId;
    /**
     * 活跃用户
     */
    private Integer activeVipCount;
    /**
     * 沉睡用户
     */
    private Integer sleepVipCount;
    /**
     * 流失用户
     */
    private Integer flowVipCount;
    /**
     * 恶意用户
     */
    private Integer evilVipCount;
    /**
     * 高价值用户
     */
    private Integer highValueVipCount;
    /**
     * 唤醒用户
     */
    private Integer awakenVipCount;
    /**
     * 新增用户
     */
    private Integer newVipCount;
    /**
     * 创建时间
     */
    private Date createTime;

}
