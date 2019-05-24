package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 细分价值配置表
 * </p>
 *
 * @author 施杰灵
 * @since 2019-05-23
 */
@TableName("loss_value_config")
public class LossValueConfig extends Model<LossValueConfig> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ID_WORKER)
    private Long id;
    /**
     * 酒店id
     */
    @TableField("hotel_id")
    private Integer hotelId;
    /**
     * 配置项
     */
    @TableField("config_item")
    private String configItem;
    /**
     * 细分价值名称
     */
    @TableField("value_name")
    private String valueName;
    /**
     * 人均消费开始值,没配置该项为:-1
     */
    @TableField("customer_person_avg_start")
    private Integer customerPersonAvgStart;
    /**
     * 人均消费结束值,没配置该项为:-1
     */
    @TableField("customer_person_avg_end")
    private Integer customerPersonAvgEnd;
    /**
     * 消费总金额开始值,没配置该项为:-1
     */
    @TableField("customer_total_start")
    private Integer customerTotalStart;
    /**
     * 消费总金额结束值,没配置该:-1
     */
    @TableField("customer_total_end")
    private Integer customerTotalEnd;
    /**
     * 消费次数开始值,没配置该项:-1
     */
    @TableField("customer_count_start")
    private Integer customerCountStart;
    /**
     * 消费次数结束值,没配置该:-1
     */
    @TableField("customer_count_end")
    private Integer customerCountEnd;
    /**
     * 倒序执行
     */
    private Integer sort;
    /**
     * 是否开启,0-不开启,1-开启
     */
    private Integer flag;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    @TableField("update_user_id")
    private Long updateUserId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getConfigItem() {
        return configItem;
    }

    public void setConfigItem(String configItem) {
        this.configItem = configItem;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public Integer getCustomerPersonAvgStart() {
        return customerPersonAvgStart;
    }

    public void setCustomerPersonAvgStart(Integer customerPersonAvgStart) {
        this.customerPersonAvgStart = customerPersonAvgStart;
    }

    public Integer getCustomerPersonAvgEnd() {
        return customerPersonAvgEnd;
    }

    public void setCustomerPersonAvgEnd(Integer customerPersonAvgEnd) {
        this.customerPersonAvgEnd = customerPersonAvgEnd;
    }

    public Integer getCustomerTotalStart() {
        return customerTotalStart;
    }

    public void setCustomerTotalStart(Integer customerTotalStart) {
        this.customerTotalStart = customerTotalStart;
    }

    public Integer getCustomerTotalEnd() {
        return customerTotalEnd;
    }

    public void setCustomerTotalEnd(Integer customerTotalEnd) {
        this.customerTotalEnd = customerTotalEnd;
    }

    public Integer getCustomerCountStart() {
        return customerCountStart;
    }

    public void setCustomerCountStart(Integer customerCountStart) {
        this.customerCountStart = customerCountStart;
    }

    public Integer getCustomerCountEnd() {
        return customerCountEnd;
    }

    public void setCustomerCountEnd(Integer customerCountEnd) {
        this.customerCountEnd = customerCountEnd;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "LossValueConfig{" +
        "id=" + id +
        ", hotelId=" + hotelId +
        ", configItem=" + configItem +
        ", valueName=" + valueName +
        ", customerPersonAvgStart=" + customerPersonAvgStart +
        ", customerPersonAvgEnd=" + customerPersonAvgEnd +
        ", customerTotalStart=" + customerTotalStart +
        ", customerTotalEnd=" + customerTotalEnd +
        ", customerCountStart=" + customerCountStart +
        ", customerCountEnd=" + customerCountEnd +
        ", sort=" + sort +
        ", flag=" + flag +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", updateUserId=" + updateUserId +
        "}";
    }
}
