package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Data;

/**
 * @Author: huzp
 * @Date: 2018/10/12 15:22
 */
@Data
public class ThirdQueryDTO {
    /**
     * 酒店id
     */
    private Integer businessId;
    /**
     * 订单来源
     */
    private String source;
    /**
     * 订单推送时间
     */
    private String createtime;
    /**
     * 订单状态
     */
    private String orderResult;

    /**
     * 微信openid
     */
    private String openid;
}
