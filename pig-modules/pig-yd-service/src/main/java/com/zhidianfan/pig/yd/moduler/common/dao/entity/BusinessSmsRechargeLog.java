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
 * @author Conan
 * @since 2018-09-10
 */
@TableName("business_sms_recharge_log")
public class BusinessSmsRechargeLog extends Model<BusinessSmsRechargeLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_sms_id")
    private Integer businessSmsId;
    @TableField("business_id")
    private Integer businessId;
    @TableField("sms_type_id")
    private Integer smsTypeId;
    private Double payamount;
    @TableField("order_no")
    private String orderNo;
    @TableField("alipay_order_no")
    private String alipayOrderNo;
    private String ispay;
    /**
     * 1新建订单，2支付成功，3支付失败，4取消支付
     */
    private String status;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    @TableField("sms_num")
    private Integer smsNum;
    @TableField("ex_sms_num")
    private Integer exSmsNum;
    private Integer sms;
    @TableField("gift_num")
    private Integer giftNum;
    /**
     * 0.未申请 ，1.申请开票 ， 2.发票完成
     */
    @TableField("invoice_status")
    private Integer invoiceStatus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessSmsId() {
        return businessSmsId;
    }

    public void setBusinessSmsId(Integer businessSmsId) {
        this.businessSmsId = businessSmsId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getSmsTypeId() {
        return smsTypeId;
    }

    public void setSmsTypeId(Integer smsTypeId) {
        this.smsTypeId = smsTypeId;
    }

    public Double getPayamount() {
        return payamount;
    }

    public void setPayamount(Double payamount) {
        this.payamount = payamount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAlipayOrderNo() {
        return alipayOrderNo;
    }

    public void setAlipayOrderNo(String alipayOrderNo) {
        this.alipayOrderNo = alipayOrderNo;
    }

    public String getIspay() {
        return ispay;
    }

    public void setIspay(String ispay) {
        this.ispay = ispay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Integer getSmsNum() {
        return smsNum;
    }

    public void setSmsNum(Integer smsNum) {
        this.smsNum = smsNum;
    }

    public Integer getExSmsNum() {
        return exSmsNum;
    }

    public void setExSmsNum(Integer exSmsNum) {
        this.exSmsNum = exSmsNum;
    }

    public Integer getSms() {
        return sms;
    }

    public void setSms(Integer sms) {
        this.sms = sms;
    }

    public Integer getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(Integer giftNum) {
        this.giftNum = giftNum;
    }

    public Integer getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Integer invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusinessSmsRechargeLog{" +
        "id=" + id +
        ", businessSmsId=" + businessSmsId +
        ", businessId=" + businessId +
        ", smsTypeId=" + smsTypeId +
        ", payamount=" + payamount +
        ", orderNo=" + orderNo +
        ", alipayOrderNo=" + alipayOrderNo +
        ", ispay=" + ispay +
        ", status=" + status +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", smsNum=" + smsNum +
        ", exSmsNum=" + exSmsNum +
        ", sms=" + sms +
        ", giftNum=" + giftNum +
        ", invoiceStatus=" + invoiceStatus +
        "}";
    }
}
