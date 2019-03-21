package com.zhidianfan.pig.yd.moduler.resv.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value="酒店月份数据DTO")
@Data
public class BusinessMonthDataDTO {

    @ApiModelProperty(value="就餐桌数")
    private Integer checktableNum;
    @ApiModelProperty(value="就餐人数")
    private Integer eatingNum;
    @ApiModelProperty(value="酒店桌子数")
    private Integer businessTableNum;
    @ApiModelProperty(value="新增客户数")
    private Integer newVip;
    @ApiModelProperty(value="数据日期")
    private String  dataMonth;

}
