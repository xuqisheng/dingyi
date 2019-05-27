package com.zhidianfan.pig.yd.moduler.meituan.dto;

import lombok.Data;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2019-05-23
 * @Modified By:
 */
@Data
public class TGOrderSubmitDTO {

    /**
     * 餐饮类型，1 = 宴会， 2 = 中餐
     */
    private Integer mealCategoryId;

    /**
     * 易订订单号
     */
    private String ydOrderNumber;

    /**
     * 天港订单号
     */
    private String orderNumber;

    /**
     * 结算账单单号
     */
    private String paymentRecipe;

    /**
     * 消费总额，除了宴会付订金不必传之外，其他均必须传
     */
    private Integer bill;

    /**
     * 订金总额，宴会付定金必传
     */
    private Integer bookingBill;

    /**
     * 订金单号
     */
    private String bookingBillRecipe;
}
