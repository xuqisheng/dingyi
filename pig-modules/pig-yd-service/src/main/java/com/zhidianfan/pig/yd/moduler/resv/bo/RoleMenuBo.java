package com.zhidianfan.pig.yd.moduler.resv.bo;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.SellerGroup;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SellerMenu;
import lombok.Data;

import java.util.List;

@Data
public class RoleMenuBo extends SellerGroup {

    private List<SellerMenu> menus;


}
