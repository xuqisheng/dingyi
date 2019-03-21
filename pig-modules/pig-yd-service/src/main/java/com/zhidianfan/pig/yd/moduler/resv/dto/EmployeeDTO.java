package com.zhidianfan.pig.yd.moduler.resv.dto;

import com.zhidianfan.pig.yd.moduler.resv.bo.BusinessBrandBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-11-30
 * @Time: 08:53
 */
@Data
public class EmployeeDTO {

    private Integer id;

    @ApiModelProperty("ADD表示新增，EDIT表示编辑")
    private String option;

    private String account;

    private String oldPassword;

    private String password;

    private String userName;

    private String mobile;

    @ApiModelProperty("用户角色ID")
    private Integer groupNameId;

    @ApiModelProperty("用户角色名称")
    private String groupName;

    @ApiModelProperty("电话机、门店后台、")
    private List<String> addDevice;


    @ApiModelProperty("电话机、门店后台、")
    private List<String> removeDevice;

    private Integer businessId;

    @ApiModelProperty("1启用0停用")
    private String status;


    private String businessName;

    @ApiModelProperty("可切换酒店列表")
    private List<BusinessBrandBo> businessList;

    @ApiModelProperty("门店后台可切换酒店列表")
    private List<BusinessBrandBo> sellerBusinessList;
}
