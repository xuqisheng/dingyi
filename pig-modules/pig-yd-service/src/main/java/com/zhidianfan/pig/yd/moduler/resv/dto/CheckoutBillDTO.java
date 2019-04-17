package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Data;

/**
 * @Author: huzp
 * @Date: 2018/12/7 15:00
 * @DESCRIPTION
 */
@Data
public class CheckoutBillDTO {


    /**
     * 订单号
     */
    private String resvOrder;

    /**
     * 实际到的人数
     */
    private String actualNum;

    /**
     * 每笔订单金额
     */
    private Integer everypay;

    private String thirdOrderNo;
}
