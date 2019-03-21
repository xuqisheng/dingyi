package com.zhidianfan.pig.user.dao.entity;

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
 * @since 2018-11-07
 */
@TableName("business_table_type")
public class BusinessTableType extends Model<BusinessTableType> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    @TableField("table_type_name")
    private String tableTypeName;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    private String status;
    @TableField("image_url")
    private String imageUrl;
    @TableField("table_type_code")
    private String tableTypeCode;


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

    public String getTableTypeName() {
        return tableTypeName;
    }

    public void setTableTypeName(String tableTypeName) {
        this.tableTypeName = tableTypeName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTableTypeCode() {
        return tableTypeCode;
    }

    public void setTableTypeCode(String tableTypeCode) {
        this.tableTypeCode = tableTypeCode;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusinessTableType{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", tableTypeName=" + tableTypeName +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", status=" + status +
        ", imageUrl=" + imageUrl +
        ", tableTypeCode=" + tableTypeCode +
        "}";
    }
}
