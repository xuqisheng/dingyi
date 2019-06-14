package com.zhidianfan.pig.yd.moduler.meituan.dto;

import lombok.Data;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2019-05-23
 * @Modified By:
 */
@Data
public class TGOrderCreateDTO {

    /**
     * 客人称呼
     */
    private String customerName;

    /**
     * 客人手机号
     */
    private String customerPhone;

    /**
     * 客人性别， 1 = 女， 2 = 男
     */
    private Integer customerGender;

    /**
     * 下单人的手机号
     */
    private String ownerPhone;

    /**
     * 西软定义的天港各个酒店的code
     */
    private String branchCode;

    /**
     * 餐饮类型，1 = 宴会， 2 = 中餐
     */
    private Integer mealCategoryId;

    /**
     * 宴会类型，传易订宴会类型所对应的id即可
     */
    private Integer ydBanquetCategoryId;

    /**
     * 用餐日期，必须是 mm/dd/yyyy hh:mm:ss 这种格式，如05/18/2019 18:21:20，保证日期正确即可
     */
    private String date;

    /**
     * 用餐时间类别，1=早餐，2=午餐，3=晚餐，4=夜宵
     */
    private Integer mealTimeTypeId;

    /**
     * 易订订单号
     */
    private String ydOrderNumber;

    /**
     * 桌数
     */
    private Integer tableAmount;

    /**
     * 备注
     */
    private String extraNotes;

    /**
     * 客户人数
     */
    private Integer customerAmount;

    /**
     * 桌号
     */
    private String tableNumber;

}
