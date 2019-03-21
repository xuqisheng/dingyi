package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SellerGroup;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SellerGroupMenu;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SellerGroupUser;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SysSyncUserBusiness;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.service.ISellerGroupMenuService;
import com.zhidianfan.pig.yd.moduler.common.service.ISellerGroupService;
import com.zhidianfan.pig.yd.moduler.common.service.ISellerGroupUserService;
import com.zhidianfan.pig.yd.moduler.common.service.ISysSyncUserBusinessService;
import com.zhidianfan.pig.yd.moduler.resv.bo.RoleEditBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DDMenuService {


    @Autowired
    private ISellerGroupMenuService iSellerGroupMenuService;

    @Autowired
    private ISellerGroupService iSellerGroupService;

    @Autowired
    private ISysSyncUserBusinessService iSysSyncUserBusinessService;

    @Autowired
    private ISellerGroupUserService iSellerGroupUserService;


    @Transactional(rollbackFor = Exception.class)
    public void replaceMenuRole(String groupCode, List<Integer> menuIds) {
        //删除
        SellerGroupMenu byGroupCode = new SellerGroupMenu();
        byGroupCode.setGroupCode(groupCode);
        byGroupCode.setClientType("dd-manage");
        iSellerGroupMenuService.delete(new EntityWrapper<>(byGroupCode));

        //添加
        List<SellerGroupMenu> list = new ArrayList<>();
        for (Integer menuId : menuIds) {
            SellerGroupMenu sellerGroupMenu = new SellerGroupMenu();
            sellerGroupMenu.setGroupCode(groupCode);
            sellerGroupMenu.setMenuCode(menuId);
            sellerGroupMenu.setClientType("dd-manage");
            list.add(sellerGroupMenu);
        }
        iSellerGroupMenuService.insertBatch(list);
    }


    @Transactional(rollbackFor = Exception.class)
    public void replaceRole(String roleName,String groupCode, List<Integer> menuIds) {

        SellerGroup byGroupCode = new SellerGroup();
        byGroupCode.setGroupCode(groupCode);
        SellerGroup sellerGroup = iSellerGroupService.selectOne(new EntityWrapper<>(byGroupCode));
        if(sellerGroup==null){
            throw  new RuntimeException("角色编号不存在");
        }

        //更新sys_sync_user_business
        SysSyncUserBusiness sysSyncUserBusiness = new SysSyncUserBusiness();
        sysSyncUserBusiness.setClientId("SELLER");
        sysSyncUserBusiness.setBusinessId(sellerGroup.getBusinessId());
        List<SysSyncUserBusiness> sysSyncUserBusinesses = iSysSyncUserBusinessService.selectList(new EntityWrapper<>(sysSyncUserBusiness));
        for (SysSyncUserBusiness syncUserBusiness : sysSyncUserBusinesses) {
            String role = syncUserBusiness.getRole();
            String sellerRoleName = role.split(":")[2];
            if(sellerGroup.getGroupName().equals(sellerRoleName)){
                role.replaceFirst(sellerRoleName,roleName);
                syncUserBusiness.setRole(role);

                SysSyncUserBusiness byUserId = new SysSyncUserBusiness();
                byUserId.setUserId(syncUserBusiness.getUserId());
                byUserId.setBusinessId(syncUserBusiness.getBusinessId());
                byUserId.setClientId(syncUserBusiness.getClientId());
                iSysSyncUserBusinessService.update(syncUserBusiness,new EntityWrapper<>(byUserId));
            }
        }


        //更新角色表
        sellerGroup.setGroupName(roleName);
        iSellerGroupService.updateById(sellerGroup);

        replaceMenuRole(groupCode,menuIds);
    }


    @Transactional(rollbackFor = Exception.class)
    public void roleAdd(RoleEditBo roleEditBo) {
        SellerGroup byBusinessId = new SellerGroup();
        byBusinessId.setBusinessId(roleEditBo.getBusinessId());
        SellerGroup role = iSellerGroupService.selectOne(new EntityWrapper<>(byBusinessId).orderBy("id", false));
        if(role==null){
            throw new RuntimeException("初始化的角色数据错误");
        }

        Integer rank;
        try {
            rank = Integer.valueOf(role.getGroupCode().split("-")[1]);
        }catch (Exception e){
            throw new RuntimeException("存在错误的角色编号，检查数据库数据");
        }

        SellerGroup newRole = new SellerGroup();
        newRole.setBusinessId(roleEditBo.getBusinessId());
        newRole.setGroupName(roleEditBo.getNewRoleName());
        newRole.setDingdingUse((byte)1);
        int count = iSellerGroupService.selectCount(new EntityWrapper<>(newRole));
        if(count>0){
           throw  new RuntimeException("角色名称已存在");
        }

        newRole.setGroupCode(roleEditBo.getBusinessId()+"-"+(++rank));

        //新增角色
        iSellerGroupService.insert(newRole);
        //新增角色菜单
        replaceMenuRole(newRole.getGroupCode(),roleEditBo.getMenuIds());

    }


    /**
     * 角色code获取角色菜单
     * @param roleCode 角色编号
     * @return
     */
    public List<SellerGroupMenu> getSellerGroupMenus(String roleCode) {
        SellerGroupMenu byRole = new SellerGroupMenu();
        byRole.setGroupCode(roleCode);
        byRole.setClientType("dd-manage");
        return iSellerGroupMenuService.selectList(new EntityWrapper<>(byRole));
    }


    /**
     * 删除角色
     * @param groupCode
     */

    @Transactional(rollbackFor = Exception.class)
    public void roleDelete(String groupCode) {
        SellerGroupUser byGroupCode = new SellerGroupUser();
        byGroupCode.setGroupCode(groupCode);
        int count = iSellerGroupUserService.selectCount(new EntityWrapper<>(byGroupCode));
        if (count != 0) {
            throw new RuntimeException("角色正在被使用，无法删除");
        }
        SellerGroup sellerGroup = new SellerGroup();
        sellerGroup.setGroupCode(groupCode);
        iSellerGroupService.delete(new EntityWrapper<>(sellerGroup));


        SellerGroupMenu sellerGroupMenu = new SellerGroupMenu();
        sellerGroupMenu.setClientType("dd-manage");
        sellerGroupMenu.setGroupCode(groupCode);
        iSellerGroupMenuService.delete(new EntityWrapper<>(sellerGroupMenu));
    }
}
