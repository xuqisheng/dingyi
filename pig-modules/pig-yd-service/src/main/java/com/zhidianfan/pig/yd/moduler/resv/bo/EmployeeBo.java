package com.zhidianfan.pig.yd.moduler.resv.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-11-29
 * @Time: 14:52
 */
@Data
public class EmployeeBo {

    private Integer id;

    private String name;

    private String mobile;

    private String account;

    private String userRole;

    @ApiModelProperty("0-正常，1-关闭")
    private String delFlag;

    private List<String> loginDevices;

    @ApiModelProperty("小程序可切换酒店列表")
    private List<BusinessBrandBo> businessList;

    /**
     * 小程序用户id
     */
    private Integer userId;

    @ApiModelProperty("门店后台可切换酒店列表")
    private List<BusinessBrandBo> sellerBusinessList;

    /**
     * 门店后台用户id
     */
    private Integer sellerUserId;
}
