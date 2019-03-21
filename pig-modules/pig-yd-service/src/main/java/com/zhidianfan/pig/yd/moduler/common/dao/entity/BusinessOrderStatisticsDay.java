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
 * @author hzp
 * @since 2018-12-20
 */
@TableName("business_order_statistics_day")
public class BusinessOrderStatisticsDay extends Model<BusinessOrderStatisticsDay> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 酒店id
     */
    @TableField("business_id")
    private Integer businessId;
    /**
     * 统计时间
     */
    @TableField("cal_date")
    private Date calDate;
    /**
     * 餐别id
     */
    @TableField("meal_type_id")
    private Integer mealTypeId;
    /**
     * 餐别名称
     */
    @TableField("meal_type_name")
    private String mealTypeName;
    /**
     * 结账桌数
     */
    @TableField("checktable_num")
    private Integer checktableNum;
    /**
     * 就餐人数
     */
    @TableField("eating_num")
    private Integer eatingNum;
    /**
     * 该餐别总金额
     */
    @TableField("payamount")
    private Integer payamount;
    /**
     * 新增客户数
     */
    @TableField("new_vip")
    private Integer newVip;
    /**
     * 新增客户数
     */
    @TableField("repeat_vip")
    private Integer repeatVip;
    /**
     * 散客订单数
     */
    @TableField("travelers_num")
    private Integer travelersNum;

    /**
     * 酒店桌数
     */
    @TableField("business_table_num")
    private Integer businessTableNum;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private Date createdAt;
    /**
     * 更新时间
     */
    @TableField("updated_at")
    private Date updatedAt;


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

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public Integer getMealTypeId() {
        return mealTypeId;
    }

    public void setMealTypeId(Integer mealTypeId) {
        this.mealTypeId = mealTypeId;
    }

    public String getMealTypeName() {
        return mealTypeName;
    }

    public void setMealTypeName(String mealTypeName) {
        this.mealTypeName = mealTypeName;
    }

    public Integer getChecktableNum() {
        return checktableNum;
    }

    public void setChecktableNum(Integer checktableNum) {
        this.checktableNum = checktableNum;
    }

    public Integer getEatingNum() {
        return eatingNum;
    }

    public void setEatingNum(Integer eatingNum) {
        this.eatingNum = eatingNum;
    }

    public Integer getNewVip() {
        return newVip;
    }

    public void setNewVip(Integer newVip) {
        this.newVip = newVip;
    }

    public Integer getRepeatVip() {
        return repeatVip;
    }

    public void setRepeatVip(Integer repeatVip) {
        this.repeatVip = repeatVip;
    }

    public Integer getTravelersNum() {
        return travelersNum;
    }

    public void setTravelersNum(Integer travelersNum) {
        this.travelersNum = travelersNum;
    }

    public Integer getBusinessTableNum() {
        return businessTableNum;
    }

    public void setBusinessTableNum(Integer businessTableNum) {
        this.businessTableNum = businessTableNum;
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

    public Integer getPayamount() {
        return payamount;
    }

    public void setPayamount(Integer payamount) {
        this.payamount = payamount;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }



    @Override
    public String toString() {
        return "BusinessOrderStatisticsDay{" +
                "id=" + id +
                ", businessId=" + businessId +
                ", calDate=" + calDate +
                ", mealTypeId=" + mealTypeId +
                ", mealTypeName='" + mealTypeName + '\'' +
                ", checktableNum=" + checktableNum +
                ", eatingNum=" + eatingNum +
                ", payamount=" + payamount +
                ", newVip=" + newVip +
                ", repeatVip=" + repeatVip +
                ", travelersNum=" + travelersNum +
                ", businessTableNum=" + businessTableNum +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
