package com.zhidianfan.pig.yd.moduler.resv.dto;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderLogs;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: huzp
 * @Date: 2018/12/7 13:09
 * @DESCRIPTION
 */
@ApiModel(value="订单记录详情dto")
@Data
public class ResvOrderLogsDTO extends ResvOrderLogs {

    @ApiModelProperty(value="桌位名称")
    private String tableName;
}
