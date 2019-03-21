package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.zhidianfan.pig.yd.moduler.common.constant.ClientId;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.resv.service.DeptService;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.DeptDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.SysDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Bazinga
 * @Description
 * @Date Create in 2018/9/21
 * @Modified By:
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 新增、修改部门
     *
     * @param sysDept
     * @return
     */
    @PostMapping(value = "/reg")
    public ResponseEntity regDept(@RequestBody DeptDTO deptDTO) {
        Tip tip = deptService.regDept(deptDTO);
        return ResponseEntity.ok(tip);
    }

    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/del", params = {"deptName"})
    public ResponseEntity delDept(@RequestParam String deptName) {
        boolean b = deptService.delDept(deptName);
        return ResponseEntity.ok(b);
    }

    /**
     * 是否存在
     *
     * @param name
     * @return
     */
    @GetMapping(value = "/exists", params = {"name"})
    public ResponseEntity existsUser(@RequestParam String name) {
        SysDept sysDept = deptService.findDeptByNameCase(name);
        return ResponseEntity.ok(sysDept != null);
    }


}
