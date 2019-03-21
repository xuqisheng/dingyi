package com.zhidianfan.pig.user.dao.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author qqx
 * @since 2018-11-07
 */
@TableName("yd_user_role")
public class YdUserRole extends Model<YdUserRole> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    private String username;
    /**
     * 角色名
     */
    @TableField("role_name")
    private String roleName;
    /**
     * 当前是否启用
     */
    @TableField("is_use")
    private Integer isUse;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "YdUserRole{" +
        "id=" + id +
        ", username=" + username +
        ", roleName=" + roleName +
        ", isUse=" + isUse +
        "}";
    }
}
