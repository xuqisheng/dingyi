package com.zhidianfan.pig.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-11-07
 * @Time: 09:01
 */
@Data
public class TablesDTO {

    private Integer id;

    @NotNull(message = "酒店id不能为空")
    private Integer businessId;

    @ApiModelProperty("区域id")
    private Integer tableAreaId;

    @ApiModelProperty("区域名称")
    private String areaCode;

    @ApiModelProperty("桌位编号")
    private String tableCode;

    @ApiModelProperty("桌位名称")
    private String tableName;

    private String maxPeopleNum;

    private String minPeopleNum;

    @ApiModelProperty("桌型id")
    private String tableType;

    @ApiModelProperty("0-包厢 1-散台 2-卡座")
    private Integer tType;

    @ApiModelProperty("0关闭1开启")
    private String status;


    @ApiModelProperty("删除标识")
    private Boolean delete;

    @ApiModelProperty("桌位标签")
    private String relatedTable;


    @ApiModelProperty("排序")
    private List<SortIds> idAndSortIds;



}
