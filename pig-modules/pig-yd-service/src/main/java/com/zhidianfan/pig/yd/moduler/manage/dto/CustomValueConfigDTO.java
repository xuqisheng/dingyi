package com.zhidianfan.pig.yd.moduler.manage.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description: 自定义客户价值
 * @User: ljh
 * @Date: 2019-01-17
 * @Time: 14:13
 */
@Data
public class CustomValueConfigDTO {


    private Integer id;
    @NotNull(message = "酒店id不能空")
    private Integer businessId;

    private String valueName;

    @ApiModelProperty("就餐天数")
    private Integer meatDayStart;

    private Integer meatDayEnd;

    @ApiModelProperty("消费次数")
    private Integer expenseTimeStart;

    private Integer expenseTimeEnd;

    @ApiModelProperty("消费总金额")
    private Integer expenseMoneyStart;

    private Integer expenseMoneyEnd;

    @ApiModelProperty("客户人均消费")
    private Integer consumeStart;

    private Integer consumeEnd;
}
