package com.zhidianfan.pig.yd.moduler.resv.bo;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderAndroid;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-12-24
 * @Time: 15:11
 */
@Data
public class BatchOrderBo extends ResvOrderAndroid {


    private List<OrderTablesStatusBo> orders;

    @ApiModelProperty("评价ID")
    private Integer orderRatingId;


 }
