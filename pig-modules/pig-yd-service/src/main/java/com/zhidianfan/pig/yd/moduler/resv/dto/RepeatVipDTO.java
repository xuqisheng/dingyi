package com.zhidianfan.pig.yd.moduler.resv.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="客户列表")
@Data
public class RepeatVipDTO {

    @ApiModelProperty(value="客户人数")
    private Integer repeatVipNum;

    @ApiModelProperty(value="数据日期")
    private String dataMonth ;


}
