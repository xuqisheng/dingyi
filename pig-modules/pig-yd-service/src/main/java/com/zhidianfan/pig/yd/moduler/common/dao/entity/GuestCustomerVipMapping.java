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
 * @author 
 * @since 2019-06-14
 */
@TableName("guest_customer_vip_mapping")
public class GuestCustomerVipMapping extends Model<GuestCustomerVipMapping> {

    private static final long serialVersionUID = 1L;

    @TableField("business_id")
    private Integer businessId;
    @TableField("guest_customer_id")
    private Integer guestCustomerId;
    @TableField("batch_no")
    private String batchNo;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("property_id")
    private Integer propertyId;
    @TableField("property_name")
    private String propertyName;


    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getGuestCustomerId() {
        return guestCustomerId;
    }

    public void setGuestCustomerId(Integer guestCustomerId) {
        this.guestCustomerId = guestCustomerId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "GuestCustomerVipMapping{" +
        "businessId=" + businessId +
        ", guestCustomerId=" + guestCustomerId +
        ", batchNo=" + batchNo +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", id=" + id +
        ", propertyId=" + propertyId +
        ", propertyName=" + propertyName +
        "}";
    }
}
