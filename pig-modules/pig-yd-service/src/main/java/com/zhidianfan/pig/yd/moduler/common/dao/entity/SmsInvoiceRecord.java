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
 * @since 2018-11-09
 */
@TableName("sms_invoice_record")
public class SmsInvoiceRecord extends Model<SmsInvoiceRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 充值记录id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 发票金额
     */
    @TableField("invoice_amount")
    private Double invoiceAmount;
    @TableField("invoice_company")
    private String invoiceCompany;
    /**
     * 发票类型 1:增值税普通发票   2:增值税专用发票
     */
    @TableField("invoice_type")
    private Integer invoiceType;
    @TableField("mailing_person")
    private String mailingPerson;
    @TableField("mailing_phone")
    private String mailingPhone;
    @TableField("mailing_address")
    private String mailingAddress;
    @TableField("zip_code")
    private String zipCode;
    @TableField("taxpayer_number")
    private String taxpayerNumber;
    @TableField("invoice_address")
    private String invoiceAddress;
    @TableField("invoice_bank")
    private String invoiceBank;
    @TableField("invoice_phone")
    private String invoicePhone;
    private String account;
    private String desc;
    /**
     * 0:删除 1:正常
     */
    private Integer status;
    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;
    @TableField("created_person")
    private String createdPerson;
    /**
     * 更新时间
     */
    @TableField("updated_time")
    private Date updatedTime;
    @TableField("updated_person")
    private String updatedPerson;
    /**
     * 酒店id
     */
    @TableField("business_id")
    private Integer businessId;
    /**
     * 集团id
     */
    @TableField("brand_id")
    private Integer brandId;
    /**
     * 充值记录id
     */
    @TableField("recharge_log_id")
    private Integer rechargeLogId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getInvoiceCompany() {
        return invoiceCompany;
    }

    public void setInvoiceCompany(String invoiceCompany) {
        this.invoiceCompany = invoiceCompany;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getMailingPerson() {
        return mailingPerson;
    }

    public void setMailingPerson(String mailingPerson) {
        this.mailingPerson = mailingPerson;
    }

    public String getMailingPhone() {
        return mailingPhone;
    }

    public void setMailingPhone(String mailingPhone) {
        this.mailingPhone = mailingPhone;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTaxpayerNumber() {
        return taxpayerNumber;
    }

    public void setTaxpayerNumber(String taxpayerNumber) {
        this.taxpayerNumber = taxpayerNumber;
    }

    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public String getInvoiceBank() {
        return invoiceBank;
    }

    public void setInvoiceBank(String invoiceBank) {
        this.invoiceBank = invoiceBank;
    }

    public String getInvoicePhone() {
        return invoicePhone;
    }

    public void setInvoicePhone(String invoicePhone) {
        this.invoicePhone = invoicePhone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedPerson() {
        return createdPerson;
    }

    public void setCreatedPerson(String createdPerson) {
        this.createdPerson = createdPerson;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedPerson() {
        return updatedPerson;
    }

    public void setUpdatedPerson(String updatedPerson) {
        this.updatedPerson = updatedPerson;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getRechargeLogId() {
        return rechargeLogId;
    }

    public void setRechargeLogId(Integer rechargeLogId) {
        this.rechargeLogId = rechargeLogId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SmsInvoiceRecord{" +
        "id=" + id +
        ", invoiceAmount=" + invoiceAmount +
        ", invoiceCompany=" + invoiceCompany +
        ", invoiceType=" + invoiceType +
        ", mailingPerson=" + mailingPerson +
        ", mailingPhone=" + mailingPhone +
        ", mailingAddress=" + mailingAddress +
        ", zipCode=" + zipCode +
        ", taxpayerNumber=" + taxpayerNumber +
        ", invoiceAddress=" + invoiceAddress +
        ", invoiceBank=" + invoiceBank +
        ", invoicePhone=" + invoicePhone +
        ", account=" + account +
        ", desc=" + desc +
        ", status=" + status +
        ", createdTime=" + createdTime +
        ", createdPerson=" + createdPerson +
        ", updatedTime=" + updatedTime +
        ", updatedPerson=" + updatedPerson +
        ", businessId=" + businessId +
        ", brandId=" + brandId +
        ", rechargeLogId=" + rechargeLogId +
        "}";
    }
}
