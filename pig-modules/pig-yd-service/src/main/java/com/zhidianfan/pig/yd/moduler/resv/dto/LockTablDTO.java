package com.zhidianfan.pig.yd.moduler.resv.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: huzp
 * @Date: 2018/11/12 10:39
 * @DESCRIPTION 锁台记录查询条件
 */
@ApiModel(value="锁台记录")
@Data
public class LockTablDTO {

    @ApiModelProperty(value="锁台状态名称")
    private String statusName;

    @ApiModelProperty(value="订单号")
    private String resvOrder;

    @ApiModelProperty(value="区域名称")
    private String tableAreaName;

    @ApiModelProperty(value="桌位名称")
    private String tableName;

    @ApiModelProperty(value="餐别名称")
    private String mealTypeName;

    @ApiModelProperty(value="桌子被锁日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private Date resvDate;

    @ApiModelProperty(value="备注")
    private String remark;

    @ApiModelProperty(value="创建日期")
    private Date  logsCreateTime;

    @ApiModelProperty(value="操作人")
    private String operationName;
}
