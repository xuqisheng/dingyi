package com.zhidianfan.pig.yd.moduler.resv.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: huzp
 * @Date: 2018/11/29 14:46
 * @DESCRIPTION
 */
@ApiModel(value="门店后台主页model")
@Data
public class HomePageDTO {

    @ApiModelProperty(value="客户总人数")
    private Integer vipNum;
    @ApiModelProperty(value="成功预定数")
    private String orderSucNum;
    @ApiModelProperty(value="总营业额")
    private String paySum;
    @ApiModelProperty(value="昨天订单总数")
    private String orderSum;
    @ApiModelProperty(value="昨日人均消费")
    private String perConsum;
    @ApiModelProperty(value="昨日就餐人次")
    private String resvSum;
    @ApiModelProperty(value="昨日新增客户")
    private Integer newVipNum;
    @ApiModelProperty(value="总桌子数")
    private Integer tableCount;
}
