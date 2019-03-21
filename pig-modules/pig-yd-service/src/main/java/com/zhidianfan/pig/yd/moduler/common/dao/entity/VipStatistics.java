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
 * 
 * </p>
 *
 * @author zhoubeibei
 * @since 2018-11-15
 */
@TableName("vip_statistics")
public class VipStatistics extends Model<VipStatistics> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 酒店 id
     */
    @TableField("business_id")
    private Integer businessId;
    /**
     * vipId
     */
    @TableField("vip_id")
    private Integer vipId;
    /**
     * 预约次数
     */
    @TableField("resv_batch_count")
    private Integer resvBatchCount;
    /**
     * 预约桌数
     */
    @TableField("resv_order_count")
    private Integer resvOrderCount;
    /**
     * 预约人数
     */
    @TableField("resv_people_num")
    private Integer resvPeopleNum;
    /**
     * 就餐次数
     */
    @TableField("meal_batch_count")
    private Integer mealBatchCount;
    /**
     * 就餐桌数
     */
    @TableField("meal_order_count")
    private Integer mealOrderCount;
    /**
     * 就餐人数
     */
    @TableField("meal_people_num")
    private Integer mealPeopleNum;
    /**
     * 消费总金额
     */
    @TableField("amount")
    private Integer amount;
    /**
     * 最近就餐日期
     */
    @TableField("last_meal_date")
    private Date lastMealDate;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;


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

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public Integer getResvBatchCount() {
        return resvBatchCount;
    }

    public void setResvBatchCount(Integer resvBatchCount) {
        this.resvBatchCount = resvBatchCount;
    }

    public Integer getResvOrderCount() {
        return resvOrderCount;
    }

    public void setResvOrderCount(Integer resvOrderCount) {
        this.resvOrderCount = resvOrderCount;
    }

    public Integer getResvPeopleNum() {
        return resvPeopleNum;
    }

    public void setResvPeopleNum(Integer resvPeopleNum) {
        this.resvPeopleNum = resvPeopleNum;
    }

    public Integer getMealBatchCount() {
        return mealBatchCount;
    }

    public void setMealBatchCount(Integer mealBatchCount) {
        this.mealBatchCount = mealBatchCount;
    }

    public Integer getMealOrderCount() {
        return mealOrderCount;
    }

    public void setMealOrderCount(Integer mealOrderCount) {
        this.mealOrderCount = mealOrderCount;
    }

    public Integer getMealPeopleNum() {
        return mealPeopleNum;
    }

    public void setMealPeopleNum(Integer mealPeopleNum) {
        this.mealPeopleNum = mealPeopleNum;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getLastMealDate() {
        return lastMealDate;
    }

    public void setLastMealDate(Date lastMealDate)
    {
        this.lastMealDate = lastMealDate;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "VipStatistics{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", vipId=" + vipId +
        ", resvBatchCount=" + resvBatchCount +
        ", resvOrderCount=" + resvOrderCount +
        ", resvPeopleNum=" + resvPeopleNum +
        ", mealBatchCount=" + mealBatchCount +
        ", mealOrderCount=" + mealOrderCount +
        ", mealPeopleNum=" + mealPeopleNum +
        ", amount=" + amount +
        ", lastMealDate=" + lastMealDate +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
