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
 * @since 2019-06-21
 */
@TableName("business_customer_analysis")
public class BusinessCustomerAnalysis extends Model<BusinessCustomerAnalysis> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String date;
    @TableField("business_id")
    private Integer businessId;
    @TableField("active_vip_count")
    private Integer activeVipCount;
    @TableField("sleep_vip_count")
    private Integer sleepVipCount;
    @TableField("flow_vip_count")
    private Integer flowVipCount;
    @TableField("evil_vip_count")
    private Integer evilVipCount;
    @TableField("high_value_vip_count")
    private Integer highValueVipCount;
    @TableField("awaken_vip_count")
    private Integer awakenVipCount;
    @TableField("new_vip_count")
    private Integer newVipCount;
    @TableField("create_time")
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public Integer getAwakenVipCount() {
        return awakenVipCount;
    }

    public void setAwakenVipCount(Integer awakenVipCount) {
        this.awakenVipCount = awakenVipCount;
    }

    public Integer getNewVipCount() {
        return newVipCount;
    }

    public void setNewVipCount(Integer newVipCount) {
        this.newVipCount = newVipCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusinessCustomerAnalysis{" +
        "id=" + id +
        ", date=" + date +
        ", businessId=" + businessId +
        ", activeVipCount=" + activeVipCount +
        ", sleepVipCount=" + sleepVipCount +
        ", flowVipCount=" + flowVipCount +
        ", evilVipCount=" + evilVipCount +
        ", highValueVipCount=" + highValueVipCount +
        ", awakenVipCount=" + awakenVipCount +
        ", newVipCount=" + newVipCount +
        ", createTime=" + createTime +
        "}";
    }
}
