package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author qqx
 * @since 2018-12-10
 */
@TableName("seller_group_user")
public class SellerGroupUser extends Model<SellerGroupUser> {

    private static final long serialVersionUID = 1L;

    @TableField("group_code")
    private String groupCode;
    @TableId(value = "login_name",type = IdType.INPUT)
    private String loginName;


    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    protected Serializable pkVal() {
        return this.loginName;
    }

    @Override
    public String toString() {
        return "SellerGroupUser{" +
        "groupCode=" + groupCode +
        ", loginName=" + loginName +
        "}";
    }
}
