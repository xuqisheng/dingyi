package com.zhidianfan.pig.yd.moduler.resv.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RoleEditBo {

    private Integer businessId;

    @ApiModelProperty("老角色名称")
    private String oldRoleName;
    @ApiModelProperty("新角色名称")
    private String newRoleName;
    @ApiModelProperty("角色编号")
    private  String groupCode;
    @ApiModelProperty("菜单id集合")
    private List<Integer> menuIds;
}
