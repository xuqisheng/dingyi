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
 * @since 2018-09-21
 */
@TableName("vip_value_record")
public class VipValueRecord extends Model<VipValueRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    @TableField("active_vip_count")
    private Integer activeVipCount;
    @TableField("sleep_vip_count")
    private Integer sleepVipCount;
    @TableField("flow_vip_count")
    private Integer flowVipCount;
    @TableField("created_at")
    private Date createdAt;
    @TableField("evil_vip_count")
    private Integer evilVipCount;
    @TableField("high_value_vip_count")
    private Integer highValueVipCount;


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

    public Integer getActiveVipCount() {
        return activeVipCount;
    }

    public void setActiveVipCount(Integer activeVipCount) {
        this.activeVipCount = activeVipCount;
    }

    public Integer getSleepVipCount() {
        return sleepVipCount;
    }

    public void setSleepVipCount(Integer sleepVipCount) {
        this.sleepVipCount = sleepVipCount;
    }

    public Integer getFlowVipCount() {
        return flowVipCount;
    }

    public void setFlowVipCount(Integer flowVipCount) {
        this.flowVipCount = flowVipCount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getEvilVipCount() {
        return evilVipCount;
    }

    public void setEvilVipCount(Integer evilVipCount) {
        this.evilVipCount = evilVipCount;
    }

    public Integer getHighValueVipCount() {
        return highValueVipCount;
    }

    public void setHighValueVipCount(Integer highValueVipCount) {
        this.highValueVipCount = highValueVipCount;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "VipValueRecord{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", activeVipCount=" + activeVipCount +
        ", sleepVipCount=" + sleepVipCount +
        ", flowVipCount=" + flowVipCount +
        ", createdAt=" + createdAt +
        ", evilVipCount=" + evilVipCount +
        ", highValueVipCount=" + highValueVipCount +
        "}";
    }
}
