package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import java.time.LocalDateTime;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 客户价值列表页信息表
 * </p>
 *
 * @author sjl
 * @since 2019-05-24
 */
@TableName("customer_value_list")
public class CustomerValueList extends Model<CustomerValueList> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * vip 表中的 id
     */
    @TableField("vip_id")
    private Integer vipId;
    /**
     * 姓名
     */
    @TableField("vip_name")
    private String vipName;
    /**
     * 男-先生，女-女士,空字符串表示无
     */
    @TableField("vip_sex")
    private String vipSex;
    /**
     * 手机号
     */
    @TableField("vip_phone")
    private String vipPhone;
    /**
     * 年龄，-1 表示无
     */
    @TableField("vip_age")
    private Integer vipAge;
    /**
     * 单位，空字符串表示无
     */
    @TableField("vip_company")
    private String vipCompany;
    /**
     * 营销经理 id,-1:无
     */
    @TableField("app_user_id")
    private Integer appUserId;
    /**
     * 营销经理名称,空字符串:无
     */
    @TableField("app_user_name")
    private String appUserName;
    /**
     * 酒店id
     */
    @TableField("hotel_id")
    private Integer hotelId;
    /**
     * 总消费次数
     */
    @TableField("customer_count")
    private Integer customerCount;
    /**
     * 总消费桌数
     */
    @TableField("customer_table")
    private Integer customerTable;
    /**
     * 总消费人数
     */
    @TableField("customer_person")
    private Integer customerPerson;
    /**
     * 撤单,0:代表无
     */
    @TableField("cancel_order")
    private Integer cancelOrder;
    /**
     * 总消费金额,单位:分
     */
    @TableField("customer_amount_total")
    private Integer customerAmountTotal;
    /**
     * 桌均消费金额,单位:分/每桌
     */
    @TableField("customer_amount_table")
    private Integer customerAmountTable;
    /**
     * 人均消费,单位:分
     */
    @TableField("customer_amount_avg")
    private Integer customerAmountAvg;
    /**
     * 最近就餐时间，2000-1-1 0:0:0 表示无
     */
    @TableField("last_eat_time")
    private LocalDateTime lastEatTime;
    /**
     * 一级分类,1-意向客户，2-活跃客户，3-沉睡客户，4-流失客户
     */
    @TableField("first_class_value")
    private Integer firstClassValue;
    /**
     * 细分价值，取值根据酒店配置项,空字符串代表:无该项配置
     */
    @TableField("sub_value")
    private String subValue;
    /**
     * 自定义分类
     */
    @TableField("customer_class")
    private String customerClass;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getVipSex() {
        return vipSex;
    }

    public void setVipSex(String vipSex) {
        this.vipSex = vipSex;
    }

    public String getVipPhone() {
        return vipPhone;
    }

    public void setVipPhone(String vipPhone) {
        this.vipPhone = vipPhone;
    }

    public Integer getVipAge() {
        return vipAge;
    }

    public void setVipAge(Integer vipAge) {
        this.vipAge = vipAge;
    }

    public String getVipCompany() {
        return vipCompany;
    }

    public void setVipCompany(String vipCompany) {
        this.vipCompany = vipCompany;
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

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(Integer customerCount) {
        this.customerCount = customerCount;
    }

    public Integer getCustomerTable() {
        return customerTable;
    }

    public void setCustomerTable(Integer customerTable) {
        this.customerTable = customerTable;
    }

    public Integer getCustomerPerson() {
        return customerPerson;
    }

    public void setCustomerPerson(Integer customerPerson) {
        this.customerPerson = customerPerson;
    }

    public Integer getCancelOrder() {
        return cancelOrder;
    }

    public void setCancelOrder(Integer cancelOrder) {
        this.cancelOrder = cancelOrder;
    }

    public Integer getCustomerAmountTotal() {
        return customerAmountTotal;
    }

    public void setCustomerAmountTotal(Integer customerAmountTotal) {
        this.customerAmountTotal = customerAmountTotal;
    }

    public Integer getCustomerAmountTable() {
        return customerAmountTable;
    }

    public void setCustomerAmountTable(Integer customerAmountTable) {
        this.customerAmountTable = customerAmountTable;
    }

    public Integer getCustomerAmountAvg() {
        return customerAmountAvg;
    }

    public void setCustomerAmountAvg(Integer customerAmountAvg) {
        this.customerAmountAvg = customerAmountAvg;
    }

    public LocalDateTime getLastEatTime() {
        return lastEatTime;
    }

    public void setLastEatTime(LocalDateTime lastEatTime) {
        this.lastEatTime = lastEatTime;
    }

    public Integer getFirstClassValue() {
        return firstClassValue;
    }

    public void setFirstClassValue(Integer firstClassValue) {
        this.firstClassValue = firstClassValue;
    }

    public String getSubValue() {
        return subValue;
    }

    public void setSubValue(String subValue) {
        this.subValue = subValue;
    }

    public String getCustomerClass() {
        return customerClass;
    }

    public void setCustomerClass(String customerClass) {
        this.customerClass = customerClass;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CustomerValueList{" +
        "id=" + id +
        ", vipId=" + vipId +
        ", vipName=" + vipName +
        ", vipSex=" + vipSex +
        ", vipPhone=" + vipPhone +
        ", vipAge=" + vipAge +
        ", vipCompany=" + vipCompany +
        ", appUserId=" + appUserId +
        ", appUserName=" + appUserName +
        ", hotelId=" + hotelId +
        ", customerCount=" + customerCount +
        ", customerTable=" + customerTable +
        ", customerPerson=" + customerPerson +
        ", cancelOrder=" + cancelOrder +
        ", customerAmountTotal=" + customerAmountTotal +
        ", customerAmountTable=" + customerAmountTable +
        ", customerAmountAvg=" + customerAmountAvg +
        ", lastEatTime=" + lastEatTime +
        ", firstClassValue=" + firstClassValue +
        ", subValue=" + subValue +
        ", customerClass=" + customerClass +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
