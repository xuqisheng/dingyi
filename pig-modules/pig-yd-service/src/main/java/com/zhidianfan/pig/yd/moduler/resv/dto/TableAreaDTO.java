package com.zhidianfan.pig.yd.moduler.resv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

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


    @ApiModelProperty("状态改变 0恢复最后一次桌位状态 1全部执行")
    private Integer recover;

    private String remark;
}
