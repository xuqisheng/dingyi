package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.resv.service.RoleService;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Bazinga
 * @Description
 * @Date Create in 2018/9/25
 * @Modified By:
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 新增、修改角色
     *
     * @param sysRole
     * @return
     */
    @PostMapping(value = "/reg")
    public ResponseEntity regRole(@RequestBody RoleDTO role) {
        Tip tip = roleService.regRole(role);
        return ResponseEntity.ok(tip);
    }

    /**
     * 批量新增、修改角色
     *
     * @param sysRole
     * @return
     */
    @PostMapping(value = "/reglist")
    public ResponseEntity regRoleList(@RequestBody List<RoleDTO> roleList) {
        Tip tip = roleService.regRoleList(roleList);
        return ResponseEntity.ok(tip);
    }
    /**
     * 批量删除角色
     *
     * @param sysRole
     * @return
     */
    @PostMapping(value = "/dellist")
    public ResponseEntity delRoleList(@RequestBody List<RoleDTO> roleList) {
        Tip tip = roleService.delRoleList(roleList);
        return ResponseEntity.ok(tip);
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/del", params = {"roleCode"})
    public ResponseEntity delRole(@RequestParam String roleCode) {
        boolean b = roleService.delRole(roleCode);
        return ResponseEntity.ok(b);
    }


}
