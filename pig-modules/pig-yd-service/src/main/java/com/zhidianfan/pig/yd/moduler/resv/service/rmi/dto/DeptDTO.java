package com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author Bazinga
 * @Description
 * @Date Create in 2018/9/27
 * @Modified By:
 */
@Data
public class DeptDTO extends SysDept {
    /**
     * 父节点名称
     */
    private String partneName;
    /**
     * 修改前的部门名称
     */
    private String oldDeptName;

    public static class Builder{
        /**
         * 部门名称
         */
        private String name;
        /**
         * 排序
         */
        private Integer orderNum;
        /**
         * 创建时间
         */
        private Date createTime;
        /**
         * 修改时间
         */
        private Date updateTime;
        /**
         * 是否删除  -1：已删除  0：正常
         */
        private String delFlag;

        /**
         * 父节点名称
         */
        private String parentName;

        /**
         * 修改前的部门名称
         */
        private String oldDeptName;

        public Builder() {
        }

//        public Builder deptId(Integer deptId) {
//            this.deptId = deptId;
//            return this;
//        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder orderNum(Integer orderNum) {
            this.orderNum = orderNum;
            return this;
        }

        public Builder createTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder updateTime(Date updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Builder delFlag(String delFlag) {
            this.delFlag = delFlag;
            return this;
        }


        public Builder partneName(String partneName) {
            this.parentName = partneName;
            return this;
        }
        public DeptDTO build(){
            return new DeptDTO(this);
        }
    }
    public DeptDTO(){

    }
    public DeptDTO(Builder builder){
        setName(builder.name);
        setOrderNum(builder.orderNum);
        setCreateTime(builder.createTime);
        setUpdateTime(builder.updateTime);
        setDelFlag(builder.delFlag);
        this.partneName =  builder.parentName;
    }


}
