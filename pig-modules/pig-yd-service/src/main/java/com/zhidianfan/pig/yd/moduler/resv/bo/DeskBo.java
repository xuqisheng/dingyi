package com.zhidianfan.pig.yd.moduler.resv.bo;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LJH
 * @version 1.0
 * @Date 2018/10/16 14:01
 */
@Data
public class DeskBo extends Table {


    private String tableAreaName;

    @ApiModelProperty("桌位类型名称")
    private String tableTypeName;

}
