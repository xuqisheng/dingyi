package com.zhidianfan.pig.yd.moduler.sms.dto.business;

import com.zhidianfan.pig.yd.moduler.sms.dto.BaseDTO;

/**
 * 短信充值记录
 * Created by ck on 2018/6/7.
 */
public class BusinessSmsRechargeLogDTO extends BaseDTO {

    /**
     * 自增id
     */
    private Long id;

    /**
     * businessSms id
     */
    private Long businessSmsId;

    /**
     * 酒店id
     */
    private Long businessId;

    /**
     * 短信套餐id
     */
    private Long smsTypeId;

    /**
     * 支付总额
     */
    private Double payamount;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 支付单号
     */
    private String alipayOrderNo;

    /**
     * 是否支付 0-否, 1-是
     */
    private String ispay;

    /**
     * 1新建订单，2支付成功，3支付失败，4取消支付
     */
    private String status;

    /**
     * 总短信条数
     */
    private Long smsNum;

    /**
     * 之前的短信条数
     */
    private Long exSmsNum;

    /**
     * 充值的短信条数
     */
    private Long sms;

    /**
     * 赠送短信数
     */
    private Long giftNum;

    /**
     * 是否开票
     */
    private Long invoiceStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBusinessSmsId() {
        return businessSmsId;
    }

    public void setBusinessSmsId(Long businessSmsId) {
        this.businessSmsId = businessSmsId;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getSmsTypeId() {
        return smsTypeId;
    }

    public void setSmsTypeId(Long smsTypeId) {
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

    public Long getSmsNum() {
        return smsNum;
    }

    public void setSmsNum(Long smsNum) {
        this.smsNum = smsNum;
    }

    public Long getExSmsNum() {
        return exSmsNum;
    }

    public void setExSmsNum(Long exSmsNum) {
        this.exSmsNum = exSmsNum;
    }

    public Long getSms() {
        return sms;
    }

    public void setSms(Long sms) {
        this.sms = sms;
    }

    public Long getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(Long giftNum) {
        this.giftNum = giftNum;
    }

    public Long getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Long invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }
}
