package com.zhidianfan.pig.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-11-07
 * @Time: 11:34
 */
@Data
public class TableAreaDTO {

    private Integer id;

    private Integer businessId;

    private String tableAreaName;

    private String status;

    private Boolean delete;

    private Integer sortId;

    @ApiModelProperty("排序")
    private List<SortIds> idAndSortIds;


}
