package com.zhidianfan.pig.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-11-07
 * @Time: 10:58
 */
@Data
public class BusinessTableTypeDTO {

    private Integer id;

    @NotNull(message = "酒店id不能空")
    private Integer businessId;


    private String name;

    @ApiModelProperty("0关闭1启用")
    private String status;


    @ApiModelProperty("删除标识")
    private Boolean delete;

    @ApiModelProperty("最小人数")
    private Integer minPeopleNum;


    private Integer maxPeopleNum;
}
