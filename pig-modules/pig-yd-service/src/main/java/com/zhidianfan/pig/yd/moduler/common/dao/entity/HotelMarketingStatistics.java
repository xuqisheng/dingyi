package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 门店营销统计
 * </p>
 *
 * @author ljh
 * @since 2019-01-17
 */
@TableName("hotel_marketing_statistics")
public class HotelMarketingStatistics extends Model<HotelMarketingStatistics> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    @TableField("business_name")
    private String businessName;
    /**
     * 营业收入
     */
    private String taking;
    /**
     * app预定次数
     */
    @TableField("app_reserve_time")
    private Integer appReserveTime;
    /**
     * 总预定次数
     */
    @TableField("all_reserve_time")
    private Integer allReserveTime;
    /**
     * 已消费次数
     */
    @TableField("expense_time")
    private Integer expenseTime;
    /**
     * 退订次数
     */
    @TableField("recede_time")
    private Integer recedeTime;
    /**
     * 预定桌数
     */
    @TableField("reserve_table_num")
    private Integer reserveTableNum;
    @TableField("create_at")
    private Date createAt;
    @TableField("update_at")
    private Date updateAt;
    /**
     * 更新次数
     */
    @TableField("update_time")
    private Integer updateTime;
    /**
     * 统计年份
     */
    @TableField("statistics_year")
    private Integer statisticsYear;
    /**
     * 统计月份
     */
    @TableField("statistics_month")
    private Integer statisticsMonth;


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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getTaking() {
        return taking;
    }

    public void setTaking(String taking) {
        this.taking = taking;
    }

    public Integer getAppReserveTime() {
        return appReserveTime;
    }

    public void setAppReserveTime(Integer appReserveTime) {
        this.appReserveTime = appReserveTime;
    }

    public Integer getAllReserveTime() {
        return allReserveTime;
    }

    public void setAllReserveTime(Integer allReserveTime) {
        this.allReserveTime = allReserveTime;
    }

    public Integer getExpenseTime() {
        return expenseTime;
    }

    public void setExpenseTime(Integer expenseTime) {
        this.expenseTime = expenseTime;
    }

    public Integer getRecedeTime() {
        return recedeTime;
    }

    public void setRecedeTime(Integer recedeTime) {
        this.recedeTime = recedeTime;
    }

    public Integer getReserveTableNum() {
        return reserveTableNum;
    }

    public void setReserveTableNum(Integer reserveTableNum) {
        this.reserveTableNum = reserveTableNum;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatisticsYear() {
        return statisticsYear;
    }

    public void setStatisticsYear(Integer statisticsYear) {
        this.statisticsYear = statisticsYear;
    }

    public Integer getStatisticsMonth() {
        return statisticsMonth;
    }

    public void setStatisticsMonth(Integer statisticsMonth) {
        this.statisticsMonth = statisticsMonth;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HotelMarketingStatistics{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", businessName=" + businessName +
        ", taking=" + taking +
        ", appReserveTime=" + appReserveTime +
        ", allReserveTime=" + allReserveTime +
        ", expenseTime=" + expenseTime +
        ", recedeTime=" + recedeTime +
        ", reserveTableNum=" + reserveTableNum +
        ", createAt=" + createAt +
        ", updateAt=" + updateAt +
        ", updateTime=" + updateTime +
        ", statisticsYear=" + statisticsYear +
        ", statisticsMonth=" + statisticsMonth +
        "}";
    }
}
