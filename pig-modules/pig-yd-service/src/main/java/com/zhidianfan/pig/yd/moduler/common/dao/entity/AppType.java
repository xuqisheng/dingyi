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
 * @author ljh
 * @since 2018-11-29
 */
@TableName("app_type")
public class AppType extends Model<AppType> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("app_type_name")
    private String appTypeName;
    @TableField("app_module_id")
    private String appModuleId;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    @TableField("app_operation_id")
    private String appOperationId;
    private String status;
    @TableField("operation_type")
    private String operationType;
    @TableField("business_id")
    private Integer businessId;
    /**
     * 0为不隐藏，1为隐藏（中间4）
     */
    @TableField("hidden_phonenum")
    private Integer hiddenPhonenum;
    /**
     * 0 是只能查自己的 1是可以查全部的
     */
    @TableField("query_type")
    private Integer queryType;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppTypeName() {
        return appTypeName;
    }

    public void setAppTypeName(String appTypeName) {
        this.appTypeName = appTypeName;
    }

    public String getAppModuleId() {
        return appModuleId;
    }

    public void setAppModuleId(String appModuleId) {
        this.appModuleId = appModuleId;
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

    public String getAppOperationId() {
        return appOperationId;
    }

    public void setAppOperationId(String appOperationId) {
        this.appOperationId = appOperationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getHiddenPhonenum() {
        return hiddenPhonenum;
    }

    public void setHiddenPhonenum(Integer hiddenPhonenum) {
        this.hiddenPhonenum = hiddenPhonenum;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AppType{" +
        "id=" + id +
        ", appTypeName=" + appTypeName +
        ", appModuleId=" + appModuleId +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", appOperationId=" + appOperationId +
        ", status=" + status +
        ", operationType=" + operationType +
        ", businessId=" + businessId +
        ", hiddenPhonenum=" + hiddenPhonenum +
        ", queryType=" + queryType +
        "}";
    }
}
