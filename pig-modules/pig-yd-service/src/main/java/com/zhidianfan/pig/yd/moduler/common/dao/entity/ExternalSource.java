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
 * @author qqx
 * @since 2018-12-06
 */
@TableName("external_source")
public class ExternalSource extends Model<ExternalSource> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    @TableField("external_source_name")
    private String externalSourceName;
    @TableField("discount_standard")
    private String discountStandard;
    /**
     * 合同开始时间
     */
    @TableField("contract_begin")
    private Date contractBegin;
    /**
     * 合同结束时间
     */
    @TableField("contract_end")
    private Date contractEnd;
    /**
     * 是否启用 1 启用 0 停用
     */
    private Integer status;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    @TableField("pic_url")
    private String picUrl;


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

    public String getExternalSourceName() {
        return externalSourceName;
    }

    public void setExternalSourceName(String externalSourceName) {
        this.externalSourceName = externalSourceName;
    }

    public String getDiscountStandard() {
        return discountStandard;
    }

    public void setDiscountStandard(String discountStandard) {
        this.discountStandard = discountStandard;
    }

    public Date getContractBegin() {
        return contractBegin;
    }

    public void setContractBegin(Date contractBegin) {
        this.contractBegin = contractBegin;
    }

    public Date getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(Date contractEnd) {
        this.contractEnd = contractEnd;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ExternalSource{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", externalSourceName=" + externalSourceName +
        ", discountStandard=" + discountStandard +
        ", contractBegin=" + contractBegin +
        ", contractEnd=" + contractEnd +
        ", status=" + status +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", picUrl=" + picUrl +
        "}";
    }
}
