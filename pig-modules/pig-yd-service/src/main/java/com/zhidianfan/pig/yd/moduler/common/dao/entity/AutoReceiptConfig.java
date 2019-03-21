package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hzp
 * @since 2019-01-28
 */
@TableName("auto_receipt_config")
public class AutoReceiptConfig extends Model<AutoReceiptConfig> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 酒店id
     */
    @TableField("business_id")
    private Integer businessId;
    /**
     * 酒店名称
     */
    @TableField("business_name")
    private String businessName;
    /**
     * 自动接单状态 0:不启用 1:启用
     */
    private Integer status;
    /**
     * 保留桌位数量
     */
    @TableField("reserved_table_count")
    private Integer reservedTableCount;
    /**
     * 不自动接单的桌位id
     */
    @TableField("reserved_table_ids")
    private String reservedTableIds;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private Date createdAt;
    /**
     * 更新时间
     */
    @TableField("updated_at")
    private Date updatedAt;


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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReservedTableCount() {
        return reservedTableCount;
    }

    public void setReservedTableCount(Integer reservedTableCount) {
        this.reservedTableCount = reservedTableCount;
    }

    public String getReservedTableIds() {
        return reservedTableIds;
    }

    public void setReservedTableIds(String reservedTableIds) {
        this.reservedTableIds = reservedTableIds;
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
        return "AutoReceiptConfig{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", businessName=" + businessName +
        ", status=" + status +
        ", reservedTableCount=" + reservedTableCount +
        ", reservedTableIds=" + reservedTableIds +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
