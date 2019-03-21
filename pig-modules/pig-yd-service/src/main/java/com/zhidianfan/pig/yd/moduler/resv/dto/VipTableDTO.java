package com.zhidianfan.pig.yd.moduler.resv.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: huzp
 * @Date: 2018/11/14 15:43
 * @DESCRIPTION
 */
@ApiModel(value="客户列表")
@Data
public class VipTableDTO {

    @ApiModelProperty(value="客户id")
    private Integer id;

    @ApiModelProperty(value="客户名称")
    private String vipName ;

    @ApiModelProperty(value="客户性别")
    private String vipSex;

    @ApiModelProperty(value="客户号码")
    private String vipPhone;

    @ApiModelProperty(value="客户价值")
    private String vipValueName;

    @ApiModelProperty(value="客户分类")
    private String vipClassName;

    @ApiModelProperty(value="就餐次数")
    private Integer actResvTimes;

    @ApiModelProperty(value="上次就餐时间")
    private String lastEatTime;

    @ApiModelProperty(value="客户公司")
    private String vipCompany;

    @ApiModelProperty(value="客户职位")
    private String vipPostion;

    @ApiModelProperty(value="创建时间")
    private String createdAt;

    @ApiModelProperty(value="累积消费")
    private String paySum;
}
