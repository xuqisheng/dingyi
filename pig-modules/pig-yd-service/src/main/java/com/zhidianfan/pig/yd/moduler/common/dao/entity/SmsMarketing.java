package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Conan
 * @since 2018-09-10
 */
@TableName("sms_marketing")
public class SmsMarketing extends Model<SmsMarketing> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String content;
    private String variable;
    @TableField("business_id")
    private Integer businessId;
    @TableField("send_type")
    private Integer sendType;
    @TableField("`status`")
    private String status;
    private Date timer;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    private String remark;
    private String custom;
    private String targetPhones;
    private Integer num;
    /**
     * 短信总数  根据短信的每条乘以发送人数计算
     */
    @TableField("sms_num")
    private Integer smsNum;
    @TableField("vip_value_id")
    private String vipValueId;
    @TableField("vip_value_name")
    private String vipValueName;
    @TableField("vip_class_id")
    private String vipClassId;
    @TableField("vip_class_name")
    private String vipClassName;
    @TableField("user_name")
    private String userName;
    @TableField("campaign_id")
    private Integer campaignId;
    private Integer sex;
    private Integer version;
    private Integer client;
    @TableField("error_msg")
    private String errorMsg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTimer() {
        return timer;
    }

    public void setTimer(Date timer) {
        this.timer = timer;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getTargetPhones() {
        return targetPhones;
    }

    public void setTargetPhones(String targetPhones) {
        this.targetPhones = targetPhones;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getSmsNum() {
        return smsNum;
    }

    public void setSmsNum(Integer smsNum) {
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

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SmsMarketing{" +
        "id=" + id +
        ", content=" + content +
        ", variable=" + variable +
        ", businessId=" + businessId +
        ", sendType=" + sendType +
        ", status=" + status +
        ", timer=" + timer +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", remark=" + remark +
        ", targetPhones=" + targetPhones +
        ", num=" + num +
        ", smsNum=" + smsNum +
        ", vipValueId=" + vipValueId +
        ", vipValueName=" + vipValueName +
        ", vipClassId=" + vipClassId +
        ", vipClassName=" + vipClassName +
        ", userName=" + userName +
        ", campaignId=" + campaignId +
        ", sex=" + sex +
        ", version=" + version +
        ", errorMsg=" + errorMsg +
        "}";
    }
}
