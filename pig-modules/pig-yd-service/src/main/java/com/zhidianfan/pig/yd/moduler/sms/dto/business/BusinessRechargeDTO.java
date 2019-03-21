package com.zhidianfan.pig.yd.moduler.sms.dto.business;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-11-13
 * @Time: 13:13
 */
@Data
public class BusinessRechargeDTO {


    @NotNull(message = "酒店id不能为空")
    private Integer businessId;

    @ApiModelProperty("充值类型ID")
    @NotNull(message = "充值类型id不能为空")
    private Integer smsTypeId;
}
