package com.zhidianfan.pig.yd.moduler.resv.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-12-24
 * @Time: 15:14
 */
@Data
public class OrderTablesStatusBo {

    private String tableName;

    @ApiModelProperty("实际人数")
    private String actualNum;

    private String status;

    private String payamount;

    private String unorderReason;


}
