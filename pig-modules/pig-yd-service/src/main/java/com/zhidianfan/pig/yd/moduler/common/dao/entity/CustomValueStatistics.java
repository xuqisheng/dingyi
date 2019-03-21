package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 自定义价值客户统计
 * </p>
 *
 * @author ljh
 * @since 2019-01-17
 */
@TableName("custom_value_statistics")
public class CustomValueStatistics extends Model<CustomValueStatistics> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 价值id
     */
    @TableField("custom_value_config_id")
    private Integer customValueConfigId;
    @TableField("business_id")
    private Integer businessId;
    @TableField("vip_id")
    private Integer vipId;
    /**
     * 会员号码
     */
    @TableField("vip_phone")
    private String vipPhone;
    /**
     * 会员姓名
     */
    @TableField("vip_name")
    private String vipName;
    /**
     * 消费次数
     */
    @TableField("expense_time")
    private Integer expenseTime;
    /**
     * 消费总金额
     */
    @TableField("expense_money")
    private BigDecimal expenseMoney;
    /**
     * 人均消费
     */
    private BigDecimal consume;
    /**
     * 创建时间
     */
    @TableField("create_at")
    private Date createAt;
    /**
     * 更新时间
     */
    @TableField("update_at")
    private Date updateAt;


    @TableField("statistics_year")
    private Integer statisticsYear;


    @TableField("statistics_month")
    private Integer statisticsMonth;

    /**
     * 最近就餐日期
     */
    @TableField("last_meat_day")
    private Date lastMeatDay;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomValueConfigId() {
        return customValueConfigId;
    }

    public void setCustomValueConfigId(Integer customValueConfigId) {
        this.customValueConfigId = customValueConfigId;
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

    public String getVipPhone() {
        return vipPhone;
    }

    public void setVipPhone(String vipPhone) {
        this.vipPhone = vipPhone;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public Integer getExpenseTime() {
        return expenseTime;
    }

    public void setExpenseTime(Integer expenseTime) {
        this.expenseTime = expenseTime;
    }

    public BigDecimal getExpenseMoney() {
        return expenseMoney;
    }

    public void setExpenseMoney(BigDecimal expenseMoney) {
        this.expenseMoney = expenseMoney;
    }

    public BigDecimal getConsume() {
        return consume;
    }

    public void setConsume(BigDecimal consume) {
        this.consume = consume;
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

    public Date getLastMeatDay() {
        return lastMeatDay;
    }

    public void setLastMeatDay(Date lastMeatDay) {
        this.lastMeatDay = lastMeatDay;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CustomValueStatistics{" +
        "id=" + id +
        ", customValueConfigId=" + customValueConfigId +
        ", businessId=" + businessId +
        ", vipId=" + vipId +
        ", vipPhone=" + vipPhone +
        ", vipName=" + vipName +
        ", expenseTime=" + expenseTime +
        ", expenseMoney=" + expenseMoney +
        ", consume=" + consume +
        ", createAt=" + createAt +
        ", updateAt=" + updateAt +
                ", statisticsYear=" + statisticsYear +
                ", statisticsMonth=" + statisticsMonth +
                ", lastMeatDay=" + lastMeatDay +
        "}";
    }
}
