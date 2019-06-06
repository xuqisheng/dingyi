package com.zhidianfan.pig.yd.moduler.resv.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: huzp
 * @Description:
 * @Date: Created in  19:38 2018/11/29
 * @param:
 */
@ApiModel(value="餐别model")
@Data
public class MealTypeDTO {


    @ApiModelProperty(value="餐别id")
    private Integer id;

    @ApiModelProperty(value="酒店id")
    private Integer businessId;
    /**
     * 用餐时段名称
     */
    @ApiModelProperty(value="餐别名称")
    private String mealTypeName;


    @ApiModelProperty(value="同步餐别id")
    private String mealTypeCode;

    /**
     * 状态：1启动，0不启动
     */
    @ApiModelProperty(value="是否启用")
    private String status;

    @ApiModelProperty(value="餐别预定开始时间")
    private String resvStartTime;
    @ApiModelProperty(value="餐别预定结束时间")
    private String resvEndTime;





}
