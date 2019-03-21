package com.zhidianfan.pig.yd.moduler.resv.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SellerGroup;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SellerGroupMenu;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SellerGroupUser;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SellerMenu;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.ISellerGroupMenuService;
import com.zhidianfan.pig.yd.moduler.common.service.ISellerGroupService;
import com.zhidianfan.pig.yd.moduler.common.service.ISellerGroupUserService;
import com.zhidianfan.pig.yd.moduler.common.service.ISellerMenuService;
import com.zhidianfan.pig.yd.moduler.resv.bo.RoleEditBo;
import com.zhidianfan.pig.yd.moduler.resv.bo.RoleMenuBo;
import com.zhidianfan.pig.yd.moduler.resv.service.DDMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api("叮叮菜单栏配置")
@RestController
@RequestMapping("/menu/dd")
@Slf4j
public class DDMenuController {


    @Autowired
    private ISellerMenuService iSellerMenuService;

    @Autowired
    private ISellerGroupService iSellerGroupService;

    @Autowired
    private ISellerGroupMenuService iSellerGroupMenuService;

    @Autowired
    private DDMenuService ddMenuService;



    private static List banRoleName=Arrays.asList("老板", "店长", "经理", "收银", "前台");

    @ApiOperation("叮叮菜单列表")
    @GetMapping("/list/dingding")
    public ResponseEntity<List<SellerMenu>> dingding(String groupCode) {

        SellerGroupMenu sellerGroupMenu = new SellerGroupMenu();
        sellerGroupMenu.setGroupCode(groupCode);
        sellerGroupMenu.setClientType("dd-manage");
        List<SellerGroupMenu> sellerGroupMenus = iSellerGroupMenuService.selectList(new EntityWrapper<>(sellerGroupMenu));
        if(sellerGroupMenus.size()==0){
            return ResponseEntity.ok(new ArrayList<>());
        }
        List<Integer> menuIds = sellerGroupMenus.stream().map(SellerGroupMenu::getMenuCode).collect(Collectors.toList());
        List<SellerMenu> sellerMenus = iSellerMenuService.selectBatchIds(menuIds);
        return ResponseEntity.ok(sellerMenus);
    }


    @ApiOperation("角色对应菜单权限列表")
    @GetMapping("/role/list")
    public ResponseEntity<List<RoleMenuBo>> roleList(Integer businessId) {
        List<RoleMenuBo> result=new ArrayList<>();
        SellerGroup condition = new SellerGroup();
        condition.setBusinessId(businessId);
        condition.setDingdingUse((byte)1);
        //酒店下的所有角色（包括默认的老板）
        List<SellerGroup> sellerGroups = iSellerGroupService.selectList(new EntityWrapper<>(condition).or("group_code = 'sadmin'"));
        for (SellerGroup sellerGroup : sellerGroups) {
            RoleMenuBo roleMenuBo = new RoleMenuBo();
            BeanUtils.copyProperties(sellerGroup,roleMenuBo);
            //角色对应的所有菜单id
            List<SellerGroupMenu> sellerGroupMenus = ddMenuService.getSellerGroupMenus(sellerGroup.getGroupCode());
            if(sellerGroupMenus.size()==0){
                roleMenuBo.setMenus(Arrays.asList());
                result.add(roleMenuBo);
                continue;
            }
            //id对应的菜单条目
            List<Integer> GroupMenuIds = sellerGroupMenus.stream().map(SellerGroupMenu::getMenuCode).collect(Collectors.toList());
            List<SellerMenu> sellerMenus = iSellerMenuService.selectBatchIds(GroupMenuIds);
            roleMenuBo.setMenus(sellerMenus);
            result.add(roleMenuBo);
        }
        return ResponseEntity.ok(result);
    }



    @ApiOperation("菜单对应角色编辑")
    @PostMapping("/role/edit")
    public ResponseEntity<Tip> roleEdit(@RequestBody RoleEditBo roleEditBo) {
        //替换对应关系
        ddMenuService.replaceMenuRole(roleEditBo.getGroupCode(), roleEditBo.getMenuIds());

        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }


    @ApiOperation("角色新增")
    @PostMapping("/seller/role/add")
    public ResponseEntity<Tip> sellerRoleAdd(@RequestBody RoleEditBo roleEditBo) {

        ddMenuService.roleAdd(roleEditBo);

        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }


    @ApiOperation("角色编辑")
    @PostMapping("/seller/role/edit")
    public ResponseEntity<Tip> sellerRoleEdit(@RequestBody RoleEditBo roleEditBo) {

        String newRoleName = roleEditBo.getNewRoleName().trim();
        if((!newRoleName.equals(roleEditBo.getOldRoleName().trim()))&&banRoleName.contains(newRoleName)){
            return ResponseEntity.ok(new ErrorTip(400,"不能修改成默认角色"));
        }
        //替换对应关系
        ddMenuService.replaceRole(newRoleName,roleEditBo.getGroupCode(),roleEditBo.getMenuIds());

        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }

    @ApiOperation("角色删除")
    @PostMapping("/seller/role/delete")
    public ResponseEntity<Tip> sellerRoleDel(String groupCode) {

        ddMenuService.roleDelete(groupCode);

        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }




}
