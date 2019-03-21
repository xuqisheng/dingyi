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
 * @author hzp
 * @since 2018-10-22
 */
@TableName("business_unorder_reason")
public class BusinessUnorderReason extends Model<BusinessUnorderReason> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("unorder_reason_name")
    private String unorderReasonName;
    @TableField("business_id")
    private String businessId;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    private String remark;
    /**
     * 排序字段
     */
    @TableField("sort_id")
    private Integer sortId;
    /**
     * 1启用 0停用
     */
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnorderReasonName() {
        return unorderReasonName;
    }

    public void setUnorderReasonName(String unorderReasonName) {
        this.unorderReasonName = unorderReasonName;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusinessUnorderReason{" +
        "id=" + id +
        ", unorderReasonName=" + unorderReasonName +
        ", businessId=" + businessId +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", remark=" + remark +
        ", sortId=" + sortId +
        ", status=" + status +
        "}";
    }
}
