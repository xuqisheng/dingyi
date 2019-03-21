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
 * @author ljh
 * @since 2018-12-05
 */
@TableName("sys_sync_user_business")
public class SysSyncUserBusiness extends Model<SysSyncUserBusiness> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id",type = IdType.INPUT)
    private Integer userId;
    @TableField("business_id")
    private Integer businessId;
    /**
     * 可操作的客户端
     */
    @TableField("client_id")
    private String clientId;
    /**
     * 所有的角色code
     */
    private String role;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    @Override
    public String toString() {
        return "SysSyncUserBusiness{" +
        "userId=" + userId +
        ", businessId=" + businessId +
        ", clientId=" + clientId +
        ", role=" + role +
        "}";
    }
}
