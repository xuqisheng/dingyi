package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author sherry
 * @since 2018-08-27
 */
@TableName("seller_user")
public class SellerUser extends Model<SellerUser> {

    private static final long serialVersionUID = 1L;

    @TableField("login_name")
    private String loginName;
    private String name;
    private String password;
    private String telphone;
    @TableField("business_id")
    private String businessId;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("brand_menu_type")
    private Integer brandMenuType;

    @TableField("created_at")
    private Date createdAt;

    @TableField("updated_at")
    private Date updatedAt;


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

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBrandMenuType() {
        return brandMenuType;
    }

    public void setBrandMenuType(Integer brandMenuType) {
        this.brandMenuType = brandMenuType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SellerUser{" +
        "loginName=" + loginName +
        ", name=" + name +
        ", password=" + password +
        ", telphone=" + telphone +
        ", businessId=" + businessId +
        ", id=" + id +
                ", brandMenuType=" + brandMenuType +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
        "}";
    }
}
