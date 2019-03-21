package com.zhidianfan.pig.yd.moduler.resv.service.rmi;

import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.SysDept;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.fallback.SysDeptFeignFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Bazinga
 * @Description
 * @Date Create in 2018/9/21
 * @Modified By:
 */
@FeignClient(name = "pig-upms-service", fallbackFactory = SysDeptFeignFactory.class)
public interface SysDeptFeign {
    /**
     * 通过ID查询
     *
     * @param id ID
     * @return SysDept
     */
    @GetMapping("/dept/{id}")
    SysDept get(@PathVariable("id") Integer id);


    /**
     * 通过客户（酒店或集团）名称，获取部门信息
     *
     * @param deptname
     * @return SysDept
     */
    @GetMapping(value = "/dept/findDeptByDeptname", params = "deptname")
    SysDept findDeptByDeptname(@RequestParam("deptname") String deptname);

    /**
     * 添加
     *
     * @param sysDept 实体
     * @return success/false
     */
    @PostMapping("/dept")
    Boolean add(@RequestBody SysDept sysDept);

    /**
     * 删除
     *
     * @param id ID
     * @return success/false
     */
    @DeleteMapping("/dept/{id}")
    Boolean delete(@PathVariable("id") Integer id);

    /**
     * 编辑
     *
     * @param sysDept 实体
     * @return success/false
     */
    @PutMapping("/dept")
    Boolean edit(@RequestBody SysDept sysDept);


}
