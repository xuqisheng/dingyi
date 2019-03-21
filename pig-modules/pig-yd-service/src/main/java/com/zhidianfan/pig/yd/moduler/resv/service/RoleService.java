package com.zhidianfan.pig.yd.moduler.resv.service;

import com.zhidianfan.pig.common.util.R;
import com.zhidianfan.pig.common.vo.SysRole;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.ResultTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.SysDeptFeign;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.SysRoleFeign;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.DeptDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.RoleDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.SysDept;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Bazinga
 * @Description
 * @Date Create in 2018/9/21
 * @Modified By:
 */
@Service
public class RoleService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysRoleFeign sysRoleFeign;
    @Autowired
    private SysDeptFeign sysDeptFeign;
    @Autowired
    private DeptService deptService;

    /**
     * 新增、修改角色
     *
     * @param role
     * @return
     */
    public Tip regRole(RoleDTO role) {
        log.info("role:{}", role);
        String roleName = role.getRoleName();
        String roleCode = role.getRoleCode();
        String roleDesc = role.getRoleDesc();
        String delFlag = role.getDelFlag();
        String deptName = role.getDeptName();
        String parentDeptName = role.getParentDeptName();
        //获得部门信息
        log.info("获取部门信息：{}", deptName);
        SysDept sysDept = sysDeptFeign.findDeptByDeptname(deptName);
        if (sysDept == null) {
            //注册部门
            SysDept parentDept = sysDeptFeign.findDeptByDeptname(parentDeptName);
            Tip tip = deptService.regDept(new DeptDTO.Builder().name(deptName).partneName(parentDeptName).delFlag("0").build());
            if(tip.getCode() != 200) return tip;
            sysDept = sysDeptFeign.findDeptByDeptname(deptName);
        }
        Integer roleDeptId = sysDept.getDeptId();
        role.setRoleDeptId(roleDeptId);
        if (roleName == null || roleCode == null || roleDesc == null ||
                delFlag == null || deptName == null || roleDeptId == null) {
            return new ErrorTip(500, "参数不全");
        }

        //先通过角色标识查询 微服务中是否存在该角色
        RoleDTO checkRoleDTO = sysRoleFeign.findRoleByRolecode(roleCode);
        if (checkRoleDTO == null) {
            //如果无该角色则执行新增角色
            R<Boolean> r = sysRoleFeign.role(role);
            if (r == null || !r.getData()) {
                return ErrorTip.ERROR_TIP;
            }
        } else {
            RoleDTO roleDTO = new RoleDTO();
            BeanUtils.copyProperties(checkRoleDTO, roleDTO);

            if (!roleCode.equals(checkRoleDTO.getRoleCode()) && sysRoleFeign.findRoleByRolecode(roleCode) != null) {
                //如果角色标识名称变更，并且新名称已经在微服务中已经存在
                return new ErrorTip(500, "名称冲突，修改失败");
            } else if (!roleName.equals(checkRoleDTO.getRoleName())
                    || !roleCode.equals(checkRoleDTO.getRoleCode())
                    || !roleDesc.equals(checkRoleDTO.getRoleDesc())
                    || !delFlag.equals(checkRoleDTO.getDelFlag())
                    || !deptName.equals(checkRoleDTO.getDeptName())
                    || roleDeptId != checkRoleDTO.getRoleDeptId()) {
                //判断是否有信息变更
                roleDTO.setRoleName(roleName);
                roleDTO.setRoleCode(roleCode);
                roleDTO.setRoleDesc(roleDesc);
                roleDTO.setDelFlag(delFlag);
                roleDTO.setDeptName(deptName);
                roleDTO.setRoleDeptId(roleDeptId);
                R<Boolean> r = sysRoleFeign.roleUpdate(roleDTO);
                if (r == null || !r.getData()) {
                    return ErrorTip.ERROR_TIP;
                }
            }
        }
        return SuccessTip.SUCCESS_TIP;

    }

    public boolean delRole(String roleCode) {
        RoleDTO checkRoleDTO = sysRoleFeign.findRoleByRolecode(roleCode);
        if (checkRoleDTO == null) return true;
        R<Boolean> r = sysRoleFeign.roleDel(checkRoleDTO.getRoleId());
        Boolean flag = r != null && r.getData();
        return flag;
    }

    public Tip regRoleList(List<RoleDTO> roleList) {
        roleList.stream().forEach(role -> {
            regRole(role);
        });
        return SuccessTip.SUCCESS_TIP;
    }

    public Tip delRoleList(List<RoleDTO> roleList) {
        roleList.stream().forEach(role -> {
            delRole(role.getRoleCode());
        });
        return SuccessTip.SUCCESS_TIP;
    }
}
