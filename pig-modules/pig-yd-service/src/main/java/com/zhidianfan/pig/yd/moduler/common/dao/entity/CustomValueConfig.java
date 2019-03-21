package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 自定义价值
 * </p>
 *
 * @author ljh
 * @since 2019-01-17
 */
@TableName("custom_value_config")
public class CustomValueConfig extends Model<CustomValueConfig> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    /**
     * 价值名称
     */
    @TableField("value_name")
    private String valueName;
    /**
     * 就餐时间起始
     */
    @TableField("meat_day_start")
    private Integer meatDayStart;
    /**
     * 就餐时间结束
     */
    @TableField("meat_day_end")
    private Integer meatDayEnd;
    /**
     * 消费次数起始
     */
    @TableField("expense_time_start")
    private Integer expenseTimeStart;
    /**
     * 消费次数结束
     */
    @TableField("expense_time_end")
    private Integer expenseTimeEnd;
    /**
     * 消费总金额起始
     */
    @TableField("expense_money_start")
    private Integer expenseMoneyStart;
    /**
     * 消费总金额结束
     */
    @TableField("expense_money_end")
    private Integer expenseMoneyEnd;
    /**
     * 人均消费起始
     */
    @TableField("consume_start")
    private Integer consumeStart;
    /**
     * 人均消费结束
     */
    @TableField("consume_end")
    private Integer consumeEnd;


    @TableField("last_statistics_at")
    private Date lastStatisticsAt;

    @TableField("create_at")
    private Date createAt;


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

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public Integer getMeatDayStart() {
        return meatDayStart;
    }

    public void setMeatDayStart(Integer meatDayStart) {
        this.meatDayStart = meatDayStart;
    }

    public Integer getMeatDayEnd() {
        return meatDayEnd;
    }

    public void setMeatDayEnd(Integer meatDayEnd) {
        this.meatDayEnd = meatDayEnd;
    }

    public Integer getExpenseTimeStart() {
        return expenseTimeStart;
    }

    public void setExpenseTimeStart(Integer expenseTimeStart) {
        this.expenseTimeStart = expenseTimeStart;
    }

    public Integer getExpenseTimeEnd() {
        return expenseTimeEnd;
    }

    public void setExpenseTimeEnd(Integer expenseTimeEnd) {
        this.expenseTimeEnd = expenseTimeEnd;
    }

    public Integer getExpenseMoneyStart() {
        return expenseMoneyStart;
    }

    public void setExpenseMoneyStart(Integer expenseMoneyStart) {
        this.expenseMoneyStart = expenseMoneyStart;
    }

    public Integer getExpenseMoneyEnd() {
        return expenseMoneyEnd;
    }

    public void setExpenseMoneyEnd(Integer expenseMoneyEnd) {
        this.expenseMoneyEnd = expenseMoneyEnd;
    }

    public Integer getConsumeStart() {
        return consumeStart;
    }

    public void setConsumeStart(Integer consumeStart) {
        this.consumeStart = consumeStart;
    }

    public Integer getConsumeEnd() {
        return consumeEnd;
    }

    public void setConsumeEnd(Integer consumeEnd) {
        this.consumeEnd = consumeEnd;
    }

    public Date getLastStatisticsAt() {
        return lastStatisticsAt;
    }

    public void setLastStatisticsAt(Date lastStatisticsAt) {
        this.lastStatisticsAt = lastStatisticsAt;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CustomValueConfig{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", valueName=" + valueName +
        ", meatDayStart=" + meatDayStart +
        ", meatDayEnd=" + meatDayEnd +
        ", expenseTimeStart=" + expenseTimeStart +
        ", expenseTimeEnd=" + expenseTimeEnd +
        ", expenseMoneyStart=" + expenseMoneyStart +
        ", expenseMoneyEnd=" + expenseMoneyEnd +
        ", consumeStart=" + consumeStart +
        ", consumeEnd=" + consumeEnd +
                ", lastStatisticsAt=" + lastStatisticsAt +
                ", createAt=" + createAt +
        "}";
    }
}
