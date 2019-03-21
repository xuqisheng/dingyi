package com.zhidianfan.pig.yd.moduler.resv.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.Brand;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.ResultTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.IBrandService;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.SysDeptFeign;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.DeptDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.SysDept;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Bazinga
 * @Description
 * @Date Create in 2018/9/21
 * @Modified By:
 */
@Service
public class DeptService {

    @Autowired
    private SysDeptFeign sysDeptFeign;
    @Autowired
    private IBrandService iBrandService;

    /**
     * 新增、修改部门
     *
     * @param deptDTO（id，name，parentId，delFlag）
     * @return
     */
    public Tip regDept(DeptDTO deptDTO) {
        String deptname = deptDTO.getName();
        String oldDeptname = deptDTO.getOldDeptName() == null ? deptname : deptDTO.getOldDeptName();
        String parentName = deptDTO.getPartneName();
        String delFlag = deptDTO.getDelFlag();
        if(StringUtils.isEmpty(deptname) || StringUtils.isEmpty(parentName)){
            return new ErrorTip(500, "部门名称不能为空！");
        }
        //获得父节点部门,通过名称精确查找
        SysDept parentDept = sysDeptFeign.findDeptByDeptname(parentName);
        if(parentDept==null && !"客户".equals(parentName)){
            //如果通过父部门名称 获取父级部门的时候，返回为空，则将父部门指向 "客户"
            parentDept = new DeptDTO.Builder().name(parentName).partneName("客户").delFlag("0").build();
            regDept((DeptDTO) parentDept);
            parentDept = findDeptByNameCase(parentName);
        }
        deptDTO.setParentId(parentDept.getDeptId());

        //获取当前修改的部门信息
        SysDept checkSysDept = findDeptByNameCase(oldDeptname);
        if (checkSysDept == null) {
            //true 意味新增
            deptDTO.setOldDeptName(null);
            boolean deptAddFlag = sysDeptFeign.add(deptDTO);
            SysDept resultSysDept = findDeptByNameCase(deptname);
            if (resultSysDept == null) {
                return ErrorTip.ERROR_TIP;
            } else {
                return new ResultTip<SysDept>(resultSysDept);
            }
        } else {
            oldDeptname = checkSysDept.getName();;
            if (!deptname.equals(oldDeptname) && sysDeptFeign.findDeptByDeptname(deptname) != null) {
                //如果酒店名称变更，并且新名称已经在微服务中已经存在
                return new ErrorTip(500, "名称冲突，修改失败");
            } else if (!deptname.equals(oldDeptname)
                    || !delFlag.equals(checkSysDept.getDelFlag())
                    || (deptDTO.getParentId() != null && !deptDTO.getParentId().equals(checkSysDept.getParentId()))) {
                //部门名称、删除标记、父节点  修改过
                checkSysDept.setName(deptname);
                checkSysDept.setDelFlag(delFlag);
                checkSysDept.setParentId(deptDTO.getParentId());
                boolean deptEditFlag = sysDeptFeign.edit(checkSysDept);
                if (!deptEditFlag) {
                    return ErrorTip.ERROR_TIP;
                }
            }
            return new ResultTip<SysDept>(checkSysDept);
        }

    }

    public boolean delDept(String deptName) {
        SysDept sysDept = sysDeptFeign.findDeptByDeptname(deptName);
        if(sysDept == null) return true;
        return sysDeptFeign.delete(sysDept.getDeptId());
    }

    /**
     * 通过部门名称查找，如果包含 "@" 分隔符且无查找结果，则截取 "@" 前面部分再查询一次
     * @param name
     * @return
     */
    public SysDept findDeptByNameCase(String name) {
        SysDept sysDept = sysDeptFeign.findDeptByDeptname(name);
        if(sysDept == null && name.split("@").length > 1){
            sysDept = sysDeptFeign.findDeptByDeptname(name.split("@")[0]);
        }
        return sysDept;
    }
}
