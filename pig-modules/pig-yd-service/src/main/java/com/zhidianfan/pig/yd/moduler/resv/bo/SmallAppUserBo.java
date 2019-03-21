package com.zhidianfan.pig.yd.moduler.resv.bo;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmallAppUser;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-12-24
 * @Time: 14:11
 */
@Data
public class SmallAppUserBo extends SmallAppUser {

    @ApiModelProperty("职位")
    private String groupName;

    @ApiModelProperty("酒店名称")
    private String businessName;
}
