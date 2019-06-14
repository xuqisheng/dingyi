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
 * 总体消费行为表
 * </p>
 *
 * @author sjl
 * @since 2019-05-24
 */
@TableName("vip_consume_action_total")
public class VipConsumeActionTotal extends Model<VipConsumeActionTotal> {

    private static final long serialVersionUID = 1L;

    /**
     * vip 表主键
     */
    @TableId(type = IdType.INPUT)
    @TableField("vip_id")
    private Integer vipId;
    /**
     * 消费完成总订单数
     */
    @TableField("total_order_no")
    private Integer totalOrderNo;
    /**
     * 消费完成总桌数
     */
    @TableField("total_table_no")
    private Integer totalTableNo;
    /**
     * 消费完成总人数
     */
    @TableField("total_person_no")
    private Integer totalPersonNo;
    /**
     * 撤单桌数
     */
    @TableField("cancel_table_no")
    private Integer cancelTableNo;
    /**
     * 消费总金额，单位：分
     */
    @TableField("total_consume_avg")
    private Integer totalConsumeAvg;
    /**
     * 桌均消费,单位:分
     */
    @TableField("table_consume_avg")
    private Integer tableConsumeAvg;
    /**
     * 人均消费
     */
    @TableField("person_consume_avg")
    private Integer personConsumeAvg;
    /**
     * 首次消费时间
     */
    @TableField("first_consume_time")
    private LocalDateTime firstConsumeTime;
    /**
     * 消费频次
     */
    @TableField("consume_frequency")
    private Float consumeFrequency;
    /**
     * 最近就餐时间
     */
    @TableField("last_consume_time")
    private LocalDateTime lastConsumeTime;
    /**
     * 创建人 user_id
     */
    @TableField("create_user_id")
    private Long createUserId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    @TableField("update_user_id")
    private Long updateUserId;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public Integer getTotalOrderNo() {
        return totalOrderNo;
    }

    public void setTotalOrderNo(Integer totalOrderNo) {
        this.totalOrderNo = totalOrderNo;
    }

    public Integer getTotalTableNo() {
        return totalTableNo;
    }

    public void setTotalTableNo(Integer totalTableNo) {
        this.totalTableNo = totalTableNo;
    }

    public Integer getTotalPersonNo() {
        return totalPersonNo;
    }

    public void setTotalPersonNo(Integer totalPersonNo) {
        this.totalPersonNo = totalPersonNo;
    }

    public Integer getCancelTableNo() {
        return cancelTableNo;
    }

    public void setCancelTableNo(Integer cancelTableNo) {
        this.cancelTableNo = cancelTableNo;
    }

    public Integer getTotalConsumeAvg() {
        return totalConsumeAvg;
    }

    public void setTotalConsumeAvg(Integer totalConsumeAmount) {
        this.totalConsumeAvg = totalConsumeAmount;
    }

    public Integer getTableConsumeAvg() {
        return tableConsumeAvg;
    }

    public void setTableConsumeAvg(Integer tableConsumeAvg) {
        this.tableConsumeAvg = tableConsumeAvg;
    }

    public Integer getPersonConsumeAvg() {
        return personConsumeAvg;
    }

    public void setPersonConsumeAvg(Integer personConsumeAvg) {
        this.personConsumeAvg = personConsumeAvg;
    }

    public LocalDateTime getFirstConsumeTime() {
        return firstConsumeTime;
    }

    public void setFirstConsumeTime(LocalDateTime firstConsumeTime) {
        this.firstConsumeTime = firstConsumeTime;
    }

    public Float getConsumeFrequency() {
        return consumeFrequency;
    }

    public void setConsumeFrequency(Float consumeFrequency) {
        this.consumeFrequency = consumeFrequency;
    }

    public LocalDateTime getLastConsumeTime() {
        return lastConsumeTime;
    }

    public void setLastConsumeTime(LocalDateTime lastConsumeTime) {
        this.lastConsumeTime = lastConsumeTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.vipId;
    }

    @Override
    public String toString() {
        return "VipConsumeActionTotal{" +
        ", vipId=" + vipId +
        ", totalOrderNo=" + totalOrderNo +
        ", totalTableNo=" + totalTableNo +
        ", totalPersonNo=" + totalPersonNo +
        ", cancelTableNo=" + cancelTableNo +
        ", totalConsumeAvg=" + totalConsumeAvg +
        ", tableConsumeAvg=" + tableConsumeAvg +
        ", personConsumeAvg=" + personConsumeAvg +
        ", firstConsumeTime=" + firstConsumeTime +
        ", consumeFrequency=" + consumeFrequency +
        ", lastConsumeTime=" + lastConsumeTime +
        ", createUserId=" + createUserId +
        ", createTime=" + createTime +
        ", updateUserId=" + updateUserId +
        ", updateTime=" + updateTime +
        "}";
    }
}
