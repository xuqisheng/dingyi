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
 * @since 2019-05-29
 */
@TableName("business_customer_analysis_detail")
public class BusinessCustomerAnalysisDetail extends Model<BusinessCustomerAnalysisDetail> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("vip_id")
    private Integer vipId;
    @TableField("vip_name")
    private String vipName;
    @TableField("vip_phone")
    private String vipPhone;
    @TableField("business_id")
    private Integer businessId;
    /**
     * 1:活跃用户，2:沉睡用户，3:流失用户，4:意向用户，5:恶意用户，6:高价值用户，7:唤醒，8:新用户
     */
    @TableField("vip_value_type")
    private Integer vipValueType;
    private String date;
    @TableField("create_time")
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getVipPhone() {
        return vipPhone;
    }

    public void setVipPhone(String vipPhone) {
        this.vipPhone = vipPhone;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getVipValueType() {
        return vipValueType;
    }

    public void setVipValueType(Integer vipValueType) {
        this.vipValueType = vipValueType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
        return "BusinessCustomerAnalysisDetail{" +
        "id=" + id +
        ", vipId=" + vipId +
        ", vipName=" + vipName +
        ", vipPhone=" + vipPhone +
        ", businessId=" + businessId +
        ", vipValueType=" + vipValueType +
        ", date=" + date +
        ", createTime=" + createTime +
        "}";
    }
}
