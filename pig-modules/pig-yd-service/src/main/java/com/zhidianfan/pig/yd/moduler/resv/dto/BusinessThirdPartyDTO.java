package com.zhidianfan.pig.yd.moduler.resv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-11-22
 * @Time: 13:39
 */
@Data
public class BusinessThirdPartyDTO {

    private Integer businessId;


    @ApiModelProperty("排序")
    private List<SortIds> idAndSortIds;


}
