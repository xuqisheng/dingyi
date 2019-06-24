package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import java.time.LocalDateTime;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 客户分析酒店记录表
 * </p>
 *
 * @author qqx
 * @since 2019-06-23
 */
@TableName("business_customer_analysis_info_task")
public class BusinessCustomerAnalysisInfoTask extends Model<BusinessCustomerAnalysisInfoTask> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 酒店id
     */
    @TableField("business_id")
    private Integer businessId;
    /**
     * 月份, yyyy-MM
     */
    private String date;
    /**
     * 0-未执行完成，1-已执行完成
     */
    @TableField("use_tag")
    private Integer useTag;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getUseTag() {
        return useTag;
    }

    public void setUseTag(Integer useTag) {
        this.useTag = useTag;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusinessCustomerAnalysisInfoTask{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", date=" + date +
        ", useTag=" + useTag +
        ", updateTime=" + updateTime +
        "}";
    }
}
