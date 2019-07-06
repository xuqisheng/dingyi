package com.zhidianfan.pig.yd.moduler.resv.service;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.resv.bo.SellerUserBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginService {


    @Resource
    private ISellerGroupService iSellerGroupService;

    @Resource
    private ISellerGroupMenuService iSellerGroupMenuService;

    @Resource
    private ISellerMenuService iSellerMenuService;


    @Resource
    private ISysSyncUserBusinessService iSysSyncUserBusinessService;

    @Resource
    private ISysSyncUserService iSysSyncUserService;


    @Autowired
    private DDMenuService ddMenuService;



    public SellerUserBo findRoleMenus(SellerUserBo sellerUserBo, String username, Integer businessId) {
        SysSyncUser byUsername = new SysSyncUser();
        byUsername.setUsername(username);
        SysSyncUser sysSyncUser = iSysSyncUserService.selectOne(new EntityWrapper<>(byUsername));
        if(sysSyncUser==null){
            throw new RuntimeException("用户角色数据错误");
        }
        SysSyncUserBusiness byUserIdMore = new SysSyncUserBusiness();
        byUserIdMore.setUserId(sysSyncUser.getUserId());
        byUserIdMore.setClientId("SELLER");
        byUserIdMore.setBusinessId(businessId);
        SysSyncUserBusiness sysSyncUserBusiness = iSysSyncUserBusinessService.selectOne(new EntityWrapper<>(byUserIdMore));
        if(sysSyncUserBusiness==null){
            throw new RuntimeException("用户角色酒店数据错误");
        }
        //获得角色名称
        String roleName = sysSyncUserBusiness.getRole().split(":")[2];
        SellerGroup byRoleName = new SellerGroup();

        if(!"老板".equals(roleName)){
            byRoleName.setBusinessId(businessId);
        }
        byRoleName.setGroupName(roleName);
        byRoleName.setDingdingUse((byte)1);
        SellerGroup sellerGroup = iSellerGroupService.selectOne(new EntityWrapper<>(byRoleName));//or("group_code = 'sadmin'"));
        //角色名称不存在
        if(sellerGroup==null){
            throw new RuntimeException("此账号角色已失效，请联系自己老板重新分配");
        }

        List<SellerGroupMenu> sellerGroupMenus = ddMenuService.getSellerGroupMenus( sellerGroup.getGroupCode());
        //添加默认菜单
        if(sellerGroupMenus.size()==0){
            SellerGroupMenu sellerGroupMenu = new SellerGroupMenu();
            sellerGroupMenu.setClientType("dd-manage");
            sellerGroupMenu.setGroupCode(sellerGroup.getGroupCode());
            sellerGroupMenu.setMenuCode(10001);//默认添加首页权限
            iSellerGroupMenuService.insert(sellerGroupMenu);
            sellerGroupMenus.add(sellerGroupMenu);
        }

        List<Integer> menuIds = sellerGroupMenus.stream().map(SellerGroupMenu::getMenuCode).collect(Collectors.toList());

        List<SellerMenu> sellerMenus = iSellerMenuService.selectBatchIds(menuIds);

        sellerUserBo.setRoleId(sellerGroup.getId());
        sellerUserBo.setRoleCode(sellerGroup.getGroupCode());
        sellerUserBo.setRoleName(sellerGroup.getGroupName());
        sellerUserBo.setMenus(sellerMenus);

        return sellerUserBo;
    }
}



