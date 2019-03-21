package com.zhidianfan.pig.yd.moduler.sms.query.sms;

import com.zhidianfan.pig.yd.moduler.sms.query.BaseQO;

/**
 * 短信审核
 * Created by ck on 2018/6/7.
 */
public class SmsMarketingQO extends BaseQO {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 酒店id
     */
    private Long businessId;

    /**
     * 状态 1-商户提交审核, 2-审核不通过, 3-审核通过, 4-已发送
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 短信数
     */
    private Long num;

    /**
     * 短信发送总条数
     */
    private Long smsNum;

    /**
     * vip 价值id
     */
    private String vipValueId;

    /**
     * vip 价值名称
     */
    private String vipValueName;

    /**
     * vip 分类id
     */
    private String vipClassId;

    /**
     * vip 分类名称
     */
    private String vipClassName;

    /**
     * 账号
     */
    private String userName;

    /**
     * 营销短信id
     */
    private Long campaignId;

    /**
     * 性别 0-男, 1-女
     */
    private Long sex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Long getSmsNum() {
        return smsNum;
    }

    public void setSmsNum(Long smsNum) {
        this.smsNum = smsNum;
    }

    public String getVipValueId() {
        return vipValueId;
    }

    public void setVipValueId(String vipValueId) {
        this.vipValueId = vipValueId;
    }

    public String getVipValueName() {
        return vipValueName;
    }

    public void setVipValueName(String vipValueName) {
        this.vipValueName = vipValueName;
    }

    public String getVipClassId() {
        return vipClassId;
    }

    public void setVipClassId(String vipClassId) {
        this.vipClassId = vipClassId;
    }

    public String getVipClassName() {
        return vipClassName;
    }

    public void setVipClassName(String vipClassName) {
        this.vipClassName = vipClassName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Long getSex() {
        return sex;
    }

    public void setSex(Long sex) {
        this.sex = sex;
    }
}
