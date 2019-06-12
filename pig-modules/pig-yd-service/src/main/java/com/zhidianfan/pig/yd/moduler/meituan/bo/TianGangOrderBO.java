package com.zhidianfan.pig.yd.moduler.meituan.bo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2019-05-15
 * @Modified By:
 */
@Data
public class TianGangOrderBO {

    /**
     * 天港酒店code
     */
    @NotBlank(message = "酒店code不能为空")
    private String branchCode;

    /**
     * 天港订单号
     */
    @NotBlank(message = "订单号不能为空")
    private String thirdOrderNo;

    /**
     * 预订员code
     */
    @NotBlank(message = "预订员code不能为空")
    private String appUserCode;

    /**
     * 预订员手机号
     */
    @NotBlank(message = "预订员手机号不能为空")
    private String appUserPhone;

    /**
     * 预订员姓名
     */
    @NotBlank(message = "预订员姓名不能为空")
    private String appUserName;

    /**
     * 顾客姓名
     */
    @NotBlank(message = "顾客姓名不能为空")
    private String vipName;

    /**
     * 顾客电话
     */
    @NotBlank(message = "顾客电话不能为空")
    private String vipPhone;

    /**
     * 顾客性别
     */
    @NotBlank(message = "顾客性别不能为空")
    private String vipSex;

    /**
     * 预订日期
     */
    @NotBlank(message = "预订日期不能为空")
    private String resvDate;

    /**
     * 餐别配置id
     */
    @NotNull(message = "餐别配置id不能为空")
    private Integer mealConfigId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 订单类型  1 普通  2 宴会
     */
    @NotNull(message = "订单类型不能为空")
    private Integer orderType;

    /**
     * 普通预订人数
     */
    private Integer resvNum;

    /**
     * 宴会预订桌数
     */
    private Integer resvTableNum;

    /**
     * 宴会类型
     */
    private Integer resvMeetingOrderType;

}
