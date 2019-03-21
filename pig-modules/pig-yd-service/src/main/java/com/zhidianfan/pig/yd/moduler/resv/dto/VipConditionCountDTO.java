package com.zhidianfan.pig.yd.moduler.resv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;


/**
 * 查询指定条件
 * @author LJH
 * @version 1.0
 * @Date 2018/9/21 10:42
 */
@Data
public class VipConditionCountDTO {

    @NotNull(message = "酒店id不能为空")
    private Integer businessId;



    /**
     * 预定次数
     */
    @ApiModelProperty("预定次数 范围区间 例如：1-10")
    private String orderCount;


    /**
     * 就餐次数
     */
    @ApiModelProperty("就餐次数 范围区间")
    private String mealCount;


    /**
     * 最近预订时间（距离现在多少天 已内负值 以上正值）
     */

    private Integer nearOrderDay;


    /**
     * 消费金额
     */
    @ApiModelProperty("消费金额 范围区间")
    private String expense;

    /**
     * 查询条件
     */
    private String keyword;


    /**
     * 自定义条件  和sms_marketing对应
     */
    private String custom;


    /**
     * 客户价值名称
     */
    @ApiModelProperty("客户价值名称")
    private String vipValueName;


    @ApiModelProperty("是否正序")
    private Boolean isAsc;

    @ApiModelProperty("排序字段 ")
    private String orderBy;

    @ApiModelProperty("营销经理id ")
    private Integer appUserId;


}
