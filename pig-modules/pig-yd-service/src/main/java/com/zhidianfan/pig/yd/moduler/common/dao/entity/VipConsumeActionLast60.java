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
 * 最近 60 天消费行为表
 * </p>
 *
 * @author sjl
 * @since 2019-05-24
 */
@TableName("vip_consume_action_last60")
public class VipConsumeActionLast60 extends Model<VipConsumeActionLast60> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
//    @TableId(type = IdType.ID_WORKER)
//    private Long id;
    /**
     * vip 表 id
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
     * 消费总金额,单位:分
     */
    @TableField("total_consume_amount")
    private Integer totalConsumeAmount;
    /**
     * 桌均消费,单位:分
     */
    @TableField("table_consume_avg")
    private Integer tableConsumeAvg;
    /**
     * 人均消费,单位:分
     */
    @TableField("person_consume_avg")
    private Integer personConsumeAvg;
    /**
     * 消费频次
     */
    @TableField("consume_frequency")
    private Integer consumeFrequency;
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
     * 更新人 user_id
     */
    @TableField("update_user_id")
    private Long updateUserId;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


//    public Long getId() {
//        return id;
//    }

//    public void setId(Long id) {
//        this.id = id;
//    }

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

    public Integer getTotalConsumeAmount() {
        return totalConsumeAmount;
    }

    public void setTotalConsumeAmount(Integer totalConsumeAmount) {
        this.totalConsumeAmount = totalConsumeAmount;
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

    public Integer getConsumeFrequency() {
        return consumeFrequency;
    }

    public void setConsumeFrequency(Integer consumeFrequency) {
        this.consumeFrequency = consumeFrequency;
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
        return "VipConsumeActionLast60{" +
//        "id=" + id +
        ", vipId=" + vipId +
        ", totalOrderNo=" + totalOrderNo +
        ", totalTableNo=" + totalTableNo +
        ", totalPersonNo=" + totalPersonNo +
        ", cancelTableNo=" + cancelTableNo +
        ", totalConsumeAmount=" + totalConsumeAmount +
        ", tableConsumeAvg=" + tableConsumeAvg +
        ", personConsumeAvg=" + personConsumeAvg +
        ", consumeFrequency=" + consumeFrequency +
        ", createUserId=" + createUserId +
        ", createTime=" + createTime +
        ", updateUserId=" + updateUserId +
        ", updateTime=" + updateTime +
        "}";
    }
}
