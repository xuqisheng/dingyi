package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
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
 * @author huzp
 * @since 2019-05-05
 */
@TableName("business_appuser_statistics")
public class BusinessAppuserStatistics extends Model<BusinessAppuserStatistics> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 年月（如201901）
     */
    @TableField("year_month")
    private String yearMonth;
    @TableField("business_id")
    private Integer businessId;
    @TableField("business_name")
    private String businessName;
    @TableField("app_user_id")
    private Integer appUserId;
    @TableField("app_user_name")
    private String appUserName;
    /**
     * 消费金额
     */
    private Integer payamount;
    /**
     * 预订次数
     */
    @TableField("resv_batch_count")
    private Integer resvBatchCount;
    /**
     * 预订桌数
     */
    @TableField("resv_table_count")
    private Integer resvTableCount;
    /**
     * 消费次数
     */
    @TableField("pay_batch_count")
    private Integer payBatchCount;
    /**
     * 消费桌数
     */
    @TableField("pay_table_count")
    private Integer payTableCount;
    /**
     * 消费（3桌以上）次数
     */
    @TableField("pay_3tables_batch_count")
    private Integer pay3tablesBatchCount;
    /**
     * 场均消费金额
     */
    @TableField("avg_batch_payamount")
    private String avgBatchPayamount;
    /**
     * 桌均消费金额
     */
    @TableField("avg_table_payamount")
    private String avgTablePayamount;
    /**
     * 人均消费金额
     */
    @TableField("avg_custom_payamount")
    private String avgCustomPayamount;
    /**
     * 退订率
     */
    @TableField("cancel_ratio")
    private BigDecimal cancelRatio;
    /**
     * 订编上座率
     */
    @TableField("seat_custom_num_ratio")
    private BigDecimal seatCustomNumRatio;
    /**
     * 有效客户数
     */
    @TableField("valid_vip_count")
    private Integer validVipCount;
    /**
     * 新增客户数
     */
    @TableField("new_vip_count")
    private Integer newVipCount;
    /**
     * 唤醒客户数
     */
    @TableField("awaken_vip_count")
    private Integer awakenVipCount;
    /**
     * 退订桌数
     */
    @TableField("cancel_table_count")
    private Integer cancelTableCount;
    /**
     * 入座人数
     */
    @TableField("seat_custom_num")
    private Integer seatCustomNum;
    /**
     * 最大容纳人数
     */
    @TableField("max_custom_num")
    private Integer maxCustomNum;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private Date createdAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
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

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public String getAppUserName() {
        return appUserName;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }

    public Integer getPayamount() {
        return payamount;
    }

    public void setPayamount(Integer payamount) {
        this.payamount = payamount;
    }

    public Integer getResvBatchCount() {
        return resvBatchCount;
    }

    public void setResvBatchCount(Integer resvBatchCount) {
        this.resvBatchCount = resvBatchCount;
    }

    public Integer getResvTableCount() {
        return resvTableCount;
    }

    public void setResvTableCount(Integer resvTableCount) {
        this.resvTableCount = resvTableCount;
    }

    public Integer getPayBatchCount() {
        return payBatchCount;
    }

    public void setPayBatchCount(Integer payBatchCount) {
        this.payBatchCount = payBatchCount;
    }

    public Integer getPayTableCount() {
        return payTableCount;
    }

    public void setPayTableCount(Integer payTableCount) {
        this.payTableCount = payTableCount;
    }

    public Integer getPay3tablesBatchCount() {
        return pay3tablesBatchCount;
    }

    public void setPay3tablesBatchCount(Integer pay3tablesBatchCount) {
        this.pay3tablesBatchCount = pay3tablesBatchCount;
    }

    public String getAvgBatchPayamount() {
        return avgBatchPayamount;
    }

    public void setAvgBatchPayamount(String avgBatchPayamount) {
        this.avgBatchPayamount = avgBatchPayamount;
    }

    public String getAvgTablePayamount() {
        return avgTablePayamount;
    }

    public void setAvgTablePayamount(String avgTablePayamount) {
        this.avgTablePayamount = avgTablePayamount;
    }

    public String getAvgCustomPayamount() {
        return avgCustomPayamount;
    }

    public void setAvgCustomPayamount(String avgCustomPayamount) {
        this.avgCustomPayamount = avgCustomPayamount;
    }

    public BigDecimal getCancelRatio() {
        return cancelRatio;
    }

    public void setCancelRatio(BigDecimal cancelRatio) {
        this.cancelRatio = cancelRatio;
    }

    public BigDecimal getSeatCustomNumRatio() {
        return seatCustomNumRatio;
    }

    public void setSeatCustomNumRatio(BigDecimal seatCustomNumRatio) {
        this.seatCustomNumRatio = seatCustomNumRatio;
    }

    public Integer getValidVipCount() {
        return validVipCount;
    }

    public void setValidVipCount(Integer validVipCount) {
        this.validVipCount = validVipCount;
    }

    public Integer getNewVipCount() {
        return newVipCount;
    }

    public void setNewVipCount(Integer newVipCount) {
        this.newVipCount = newVipCount;
    }

    public Integer getAwakenVipCount() {
        return awakenVipCount;
    }

    public void setAwakenVipCount(Integer awakenVipCount) {
        this.awakenVipCount = awakenVipCount;
    }

    public Integer getCancelTableCount() {
        return cancelTableCount;
    }

    public void setCancelTableCount(Integer cancelTableCount) {
        this.cancelTableCount = cancelTableCount;
    }

    public Integer getSeatCustomNum() {
        return seatCustomNum;
    }

    public void setSeatCustomNum(Integer seatCustomNum) {
        this.seatCustomNum = seatCustomNum;
    }

    public Integer getMaxCustomNum() {
        return maxCustomNum;
    }

    public void setMaxCustomNum(Integer maxCustomNum) {
        this.maxCustomNum = maxCustomNum;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusinessAppuserStatistics{" +
        "id=" + id +
        ", yearMonth=" + yearMonth +
        ", businessId=" + businessId +
        ", businessName=" + businessName +
        ", appUserId=" + appUserId +
        ", appUserName=" + appUserName +
        ", payamount=" + payamount +
        ", resvBatchCount=" + resvBatchCount +
        ", resvTableCount=" + resvTableCount +
        ", payBatchCount=" + payBatchCount +
        ", payTableCount=" + payTableCount +
        ", pay3tablesBatchCount=" + pay3tablesBatchCount +
        ", avgBatchPayamount=" + avgBatchPayamount +
        ", avgTablePayamount=" + avgTablePayamount +
        ", avgCustomPayamount=" + avgCustomPayamount +
        ", cancelRatio=" + cancelRatio +
        ", seatCustomNumRatio=" + seatCustomNumRatio +
        ", validVipCount=" + validVipCount +
        ", newVipCount=" + newVipCount +
        ", awakenVipCount=" + awakenVipCount +
        ", cancelTableCount=" + cancelTableCount +
        ", seatCustomNum=" + seatCustomNum +
        ", maxCustomNum=" + maxCustomNum +
        ", createdAt=" + createdAt +
        "}";
    }
}
