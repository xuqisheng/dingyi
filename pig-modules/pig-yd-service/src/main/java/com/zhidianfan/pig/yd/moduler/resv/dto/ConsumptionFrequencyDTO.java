package com.zhidianfan.pig.yd.moduler.resv.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="客户列表")
@Data
public class ConsumptionFrequencyDTO {


    @ApiModelProperty(value="客户人数")
    private Integer vipNum;

    @ApiModelProperty(value="消费次数")
    private String eatTimes ;


}
