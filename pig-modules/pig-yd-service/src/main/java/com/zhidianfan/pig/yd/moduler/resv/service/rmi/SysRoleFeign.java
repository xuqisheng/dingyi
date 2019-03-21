package com.zhidianfan.pig.yd.moduler.resv.service.rmi;

import com.zhidianfan.pig.common.util.R;
import com.zhidianfan.pig.common.vo.SysRole;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.RoleDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.SysDept;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.fallback.SysDeptFeignFactory;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.fallback.SysRoleFeignFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Bazinga
 * @Description
 * @Date Create in 2018/9/25
 * @Modified By:
 */
@FeignClient(name = "pig-upms-service", fallbackFactory = SysRoleFeignFactory.class)
public interface SysRoleFeign {
    /**
     * 通过ID查询角色信息
     *
     * @param id ID
     * @return 角色信息
     */
    @GetMapping("/role/{id}")
    SysRole role(@PathVariable("id") Integer id);

    /**
     * 添加角色
     *
     * @param roleDto 角色信息
     * @return success、false
     */
    @PostMapping("/role")
    R<Boolean> role(@RequestBody RoleDTO roleDto);

    /**
     * 修改角色
     *
     * @param roleDto 角色信息
     * @return success/false
     */
    @PutMapping("/role")
    R<Boolean> roleUpdate(@RequestBody RoleDTO roleDto);

    /**
     * 删除角色
     *
     * @param roleDto 角色信息
     * @return success/false
     */
    @DeleteMapping("/role/{id}")
    R<Boolean> roleDel(@PathVariable("id") Integer id);

    /**
     * 通过角色标识查找角色信息
     *
     * @param roleCode 角色标识
     * @return
     */
    @GetMapping(value = "/role/findRoleByRolecode", params = "roleCode")
    RoleDTO findRoleByRolecode(@RequestParam("roleCode") String roleCode);
}
