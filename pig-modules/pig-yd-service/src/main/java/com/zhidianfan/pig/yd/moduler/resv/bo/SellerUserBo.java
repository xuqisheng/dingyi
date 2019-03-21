package com.zhidianfan.pig.yd.moduler.resv.bo;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.SellerMenu;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SellerUser;
import lombok.Data;

import java.util.List;

@Data
public class SellerUserBo extends SellerUser {

    private String roleName;

    private String roleCode;

    private Integer roleId;

    private List<SellerMenu> menus;

    private List<BusinessBrandBo> businessList;

}
