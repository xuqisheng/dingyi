package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author sherry
 * @since 2018-08-27
 */
@TableName("xt_user")
public class XtUser extends Model<XtUser> {

    private static final long serialVersionUID = 1L;

    @TableId("login_name")
    private String loginName;
    private String name;
    private String password;
    private String telphone;
    @TableField("factory_id")
    private String factoryId;
    @TableField("franchisee_id")
    private String franchiseeId;
    @TableField("center_id")
    private String centerId;
    private Integer level;
    @TableField("last_pass_reset")
    private Date lastPassReset;


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public String getFranchiseeId() {
        return franchiseeId;
    }

    public void setFranchiseeId(String franchiseeId) {
        this.franchiseeId = franchiseeId;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getLastPassReset() {
        return lastPassReset;
    }

    public void setLastPassReset(Date lastPassReset) {
        this.lastPassReset = lastPassReset;
    }

    @Override
    protected Serializable pkVal() {
        return this.loginName;
    }

    @Override
    public String toString() {
        return "XtUser{" +
        "loginName=" + loginName +
        ", name=" + name +
        ", password=" + password +
        ", telphone=" + telphone +
        ", factoryId=" + factoryId +
        ", franchiseeId=" + franchiseeId +
        ", centerId=" + centerId +
        ", level=" + level +
        ", lastPassReset=" + lastPassReset +
        "}";
    }
}
