package com.zhidianfan.pig.yd.moduler.resv.qo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 客户分析
 * 查询订单
 *
 * @author wangyz
 * @version v 0.1 2019-03-19 13:52 wangyz Exp $
 */
@Data
public class CustomerAnalysisResvOrderQO {

    /**
     * 酒店id
     */
    private Integer businessId;

    /**
     * 用户id
     */
    private Integer vipId;

    /**
     * 创建时间开始
     */
    private LocalDateTime createBegin;

    /**
     * 创建时间结束
     */
    private LocalDateTime createEnd;
}