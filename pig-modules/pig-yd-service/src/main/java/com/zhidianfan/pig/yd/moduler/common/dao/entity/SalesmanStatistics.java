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
 * @author ljh
 * @since 2019-01-27
 */
@TableName("salesman_statistics")
public class SalesmanStatistics extends Model<SalesmanStatistics> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("brand_id")
    private Integer brandId;
    @TableField("app_user_id")
    private Integer appUserId;
    @TableField("app_user_name")
    private String appUserName;
    @TableField("app_user_phone")
    private String appUserPhone;
    @TableField("resv_time")
    private Integer resvTime;
    @TableField("resv_table")
    private Integer resvTable;

    @TableField("resv_sum")
    private String resvSum;
    private Integer num;
    /**
     * 统计类型(1年度2季度3月度)
     */
    @TableField("statistics_type")
    private Integer statisticsType;
    /**
     * 统计季度
     */
    @TableField("statistics_quarter")
    private Integer statisticsQuarter;
    /**
     * 年份
     */
    private Integer year;
    /**
     * 月份
     */
    private Integer month;
    @TableField("create_at")
    private Date createAt;
    @TableField("update_at")
    private Date updateAt;


    @TableField("business_name")
    private String businessName;
    @TableField("business_id")
    private Integer businessId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAppUserPhone() {
        return appUserPhone;
    }

    public void setAppUserPhone(String appUserPhone) {
        this.appUserPhone = appUserPhone;
    }

    public Integer getResvTime() {
        return resvTime;
    }

    public void setResvTime(Integer resvTime) {
        this.resvTime = resvTime;
    }

    public Integer getResvTable() {
        return resvTable;
    }

    public void setResvTable(Integer resvTable) {
        this.resvTable = resvTable;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStatisticsType() {
        return statisticsType;
    }

    public void setStatisticsType(Integer statisticsType) {
        this.statisticsType = statisticsType;
    }

    public Integer getStatisticsQuarter() {
        return statisticsQuarter;
    }

    public void setStatisticsQuarter(Integer statisticsQuarter) {
        this.statisticsQuarter = statisticsQuarter;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
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


    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getResvSum() {
        return resvSum;
    }

    public void setResvSum(String resvSum) {
        this.resvSum = resvSum;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SalesmanStatistics{" +
        "id=" + id +
                ", brandId=" + brandId +
        ", appUserId=" + appUserId +
        ", appUserName=" + appUserName +
        ", appUserPhone=" + appUserPhone +
        ", resvTime=" + resvTime +
        ", resvTable=" + resvTable +
        ", num=" + num +
                ", resvSum=" + resvSum +
        ", statisticsType=" + statisticsType +
        ", statisticsQuarter=" + statisticsQuarter +
        ", year=" + year +
        ", month=" + month +
        ", createAt=" + createAt +
        ", updateAt=" + updateAt +
                ", businessName=" + businessName +
                ", businessId=" + businessId +
        "}";
    }
}
