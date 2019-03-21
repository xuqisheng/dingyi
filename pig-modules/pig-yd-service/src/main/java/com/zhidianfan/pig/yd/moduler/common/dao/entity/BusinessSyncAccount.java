package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2018-10-22
 */
@TableName("business_sync_account")
public class BusinessSyncAccount extends Model<BusinessSyncAccount> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    @TableField("business_name")
    private String businessName;
    private String appid;
    private String secret;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    private Integer status;
    /**
     * 接口 1.志杰 2.天子星 3.辰森 4.海亨
     */
    @TableField("type_id")
    private Integer typeId;
    @TableField("access_token")
    private String accessToken;
    @TableField("shop_id")
    private String shopId;
    /**
     * 微生活appid
     */
    @TableField("appid_crm")
    private String appidCrm;
    @TableField("appkey_crm")
    private String appkeyCrm;
    private String username;
    private String password;
    @TableField("store_id")
    private String storeId;
    @TableField("created_id")
    private String createdId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getAppidCrm() {
        return appidCrm;
    }

    public void setAppidCrm(String appidCrm) {
        this.appidCrm = appidCrm;
    }

    public String getAppkeyCrm() {
        return appkeyCrm;
    }

    public void setAppkeyCrm(String appkeyCrm) {
        this.appkeyCrm = appkeyCrm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCreatedId() {
        return createdId;
    }

    public void setCreatedId(String createdId) {
        this.createdId = createdId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusinessSyncAccount{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", businessName=" + businessName +
        ", appid=" + appid +
        ", secret=" + secret +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", status=" + status +
        ", typeId=" + typeId +
        ", accessToken=" + accessToken +
        ", shopId=" + shopId +
        ", appidCrm=" + appidCrm +
        ", appkeyCrm=" + appkeyCrm +
        ", username=" + username +
        ", password=" + password +
        ", storeId=" + storeId +
        ", createdId=" + createdId +
        "}";
    }
}
