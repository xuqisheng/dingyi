/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto;

import com.zhidianfan.pig.common.vo.SysRole;
import lombok.Data;

/**
 * @author lengleng
 * @date 2018/1/20
 * 角色Dto
 */
@Data
public class RoleDTO extends SysRole {
    /**
     * 角色部门Id
     */
    private Integer roleDeptId;

    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 父节点部门名称
     */
    private String parentDeptName;

    public static class Builder{
        private Integer roleId;
        private String roleName;
        private String roleCode;
        private String roleDesc;
        private String delFlag;

//        private Integer roleDeptId;
        private String deptName;
        private String parentDeptName;

        public Builder roleId(Integer roleId) {
            this.roleId = roleId;
            return this;
        }
        public Builder roleName(String roleName) {
            this.roleName = roleName;
            return this;
        }
        public Builder roleCode(String roleCode) {
            this.roleCode = roleCode;
            return this;
        }
        public Builder roleDesc(String roleDesc) {
            this.roleDesc = roleDesc;
            return this;
        }
        public Builder delFlag(String delFlag) {
            this.delFlag = delFlag;
            return this;
        }

//        public Builder roleDeptId(Integer roleDeptId) {
//            this.roleDeptId = roleDeptId;
//            return this;
//        }
        public Builder deptName(String deptName) {
            this.deptName = deptName;
            return this;
        }
        public Builder parentDeptName(String parentDeptName) {
            this.parentDeptName = parentDeptName;
            return this;
        }

        public RoleDTO build(){
            return new RoleDTO(this);
        }

    }

    public RoleDTO() {
    }

    public RoleDTO(Builder builder){
        this.setRoleId(builder.roleId);
        this.setRoleName(builder.roleName);
        this.setRoleCode(builder.roleCode);
        this.setRoleDesc(builder.roleDesc);
        this.setDelFlag(builder.delFlag);
//        this.roleDeptId = builder.roleDeptId;
        this.deptName = builder.deptName;
        this.parentDeptName = builder.parentDeptName;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RoleDTO{");
        sb.append("roleDeptId=").append(roleDeptId);
        sb.append(", deptName='").append(deptName).append('\'');
        sb.append(", parentDeptName='").append(parentDeptName).append('\'');
        sb.append('}');
        sb.append("\n")
                .append(super.toString());
        return sb.toString();
    }
}
