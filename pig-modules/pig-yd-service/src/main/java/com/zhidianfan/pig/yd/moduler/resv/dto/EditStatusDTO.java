package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Data;

import java.util.List;

/**
 * 修改订单状态dto
 */
@Data
public class EditStatusDTO {


    /**
     * 酒店id
     */
    private Integer businessId;
    /**
     * 订单信息
     */
    private List<CheckoutBillDTO> checkoutBills;

    /**
     * 需要修改的状态
     */
    private String status;

    /**
     * 确认是否会来
     */
    private String confirm;

    /**
     * 支付总金额
     */
    private String payamount;

    /**
     * 退订原因
     */
    private Integer unorderReasonId;
    /**
     * 退订原因
     */
    private String unorderReason;

    /**
     * 备注
     */
    private String remark;


    /**
     * 安卓电话机用户信息
     */
    private Integer androidUserId;
    private String androidUserName;


    /**
     * 设备类型
     * 1 为安卓电话机
     * 2 为小程序
     */
    private String  deviceType;
}
