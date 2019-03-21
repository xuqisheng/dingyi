package com.zhidianfan.pig.yd.moduler.manage.bo;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomValueConfig;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomValueStatistics;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @User: ljh
 * @Date: 2019-01-17
 * @Time: 16:57
 */
@Data
public class CustomValueCustomerBo extends CustomValueConfig {


    @ApiModelProperty("客户")
    private Integer vips;
}
