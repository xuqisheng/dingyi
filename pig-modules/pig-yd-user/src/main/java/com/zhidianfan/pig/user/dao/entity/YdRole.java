package com.zhidianfan.pig.user.dao.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 角色权限表
 * </p>
 *
 * @author qqx
 * @since 2018-11-07
 */
@TableName("yd_role")
public class YdRole extends Model<YdRole> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    /**
     * 角色名
     */
    @TableField("role_name")
    private String roleName;
    /**
     * 角色描述
     */
    @TableField("role_desc")
    private String roleDesc;
    /**
     * url的ant表现形式
     */
    @TableField("ant_url")
    private String antUrl;
    /**
     * true-该角色能够访问ant_url
false-该觉得不能访问ant_url（优先）
     */
    private Integer flag;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getAntUrl() {
        return antUrl;
    }

    public void setAntUrl(String antUrl) {
        this.antUrl = antUrl;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "YdRole{" +
        "id=" + id +
        ", roleName=" + roleName +
        ", roleDesc=" + roleDesc +
        ", antUrl=" + antUrl +
        ", flag=" + flag +
        "}";
    }
}
