package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
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
 * @since 2018-10-29
 */
@TableName("vip_value")
public class VipValue extends Model<VipValue> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("vip_value_name")
    private String vipValueName;
    @TableField("business_id")
    private Integer businessId;
    /**
     * 退订次数
     */
    @TableField("unorder_count")
    private Integer unorderCount;
    @TableField("active_days")
    private Integer activeDays;
    /**
     * 流失天数
     */
    @TableField("flow_days")
    private Integer flowDays;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    private Integer type;
    @TableField("high_value_days")
    private Integer highValueDays;
    @TableField("high_value_counts")
    private Integer highValueCounts;
    @TableField("high_value_amount")
    private BigDecimal highValueAmount;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVipValueName() {
        return vipValueName;
    }

    public void setVipValueName(String vipValueName) {
        this.vipValueName = vipValueName;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getUnorderCount() {
        return unorderCount;
    }

    public void setUnorderCount(Integer unorderCount) {
        this.unorderCount = unorderCount;
    }

    public Integer getActiveDays() {
        return activeDays;
    }

    public void setActiveDays(Integer activeDays) {
        this.activeDays = activeDays;
    }

    public Integer getFlowDays() {
        return flowDays;
    }

    public void setFlowDays(Integer flowDays) {
        this.flowDays = flowDays;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getHighValueDays() {
        return highValueDays;
    }

    public void setHighValueDays(Integer highValueDays) {
        this.highValueDays = highValueDays;
    }

    public Integer getHighValueCounts() {
        return highValueCounts;
    }

    public void setHighValueCounts(Integer highValueCounts) {
        this.highValueCounts = highValueCounts;
    }

    public BigDecimal getHighValueAmount() {
        return highValueAmount;
    }

    public void setHighValueAmount(BigDecimal highValueAmount) {
        this.highValueAmount = highValueAmount;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "VipValue{" +
        "id=" + id +
        ", vipValueName=" + vipValueName +
        ", businessId=" + businessId +
        ", unorderCount=" + unorderCount +
        ", activeDays=" + activeDays +
        ", flowDays=" + flowDays +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", type=" + type +
        ", highValueDays=" + highValueDays +
        ", highValueCounts=" + highValueCounts +
        ", highValueAmount=" + highValueAmount +
        "}";
    }
}
