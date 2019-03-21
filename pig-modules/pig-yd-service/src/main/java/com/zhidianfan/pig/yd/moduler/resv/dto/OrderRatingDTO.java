package com.zhidianfan.pig.yd.moduler.resv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-12-19
 * @Time: 13:30
 */
@Data
public class OrderRatingDTO {

    @NotBlank(message = "批次号不能为空")
    @ApiModelProperty("订单批次号")
    private String batchNo;

    private String remarks;

    @ApiModelProperty("评分")
    private Integer grade;

    @NotNull(message = "酒店id不能为空")
    private Integer businessId;

    @NotNull(message = "客户id不能为空")
    private Integer vipId;
}
