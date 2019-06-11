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
 * @author huzp
 * @since 2019-06-11
 */
@TableName("sms_marketing")
public class SmsMarketing extends Model<SmsMarketing> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String content;
    /**
     * 模板变量信息
     */
    private String variable;
    @TableField("business_id")
    private Integer businessId;
    /**
     * 发送类型0：即时发送,1：定时发送
     */
    @TableField("send_type")
    private Integer sendType;
    /**
     * 1、商户提交审核 2、审核不通过 3、 审核通过  4、商户已发送 5、创蓝提交失败 6、创蓝审核不通过
     */
    private String status;
    /**
     * 定时发送时间
     */
    private Date timer;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    private String remark;
    /**
     * 自定义发送条件
     */
    private String custom;
    /**
     * 发送号码
     */
    @TableField("target_phones")
    private String targetPhones;
    /**
     * 发送目标手机数量(也可能是一条短信的数量)
     */
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
    /**
     * 接收的客户
     */
    @TableField("accept_custome")
    private String acceptCustome;
    /**
     * 锁
     */
    private Integer version;
    /**
     * 客户端,0历史数据,1:安卓电话机
     */
    private Integer client;
    /**
     * 安卓电话机短信发送未通过审核是否返还短信
     */
    @TableField("is_refund")
    private Integer isRefund;
    /**
     * 营销模板id 对应business_marketing_sms_template表
     */
    @TableField("template_id")
    private Integer templateId;
    /**
     * 1 用户类型 2自定义 3 模板 
     */
    @TableField("sms_edit_type")
    private Integer smsEditType;
    /**
     * 营销经理
     */
    @TableField("app_user_id")
    private Integer appUserId;
    /**
     * 审核时间
     */
    @TableField("auditing_at")
    private Date auditingAt;
    /**
     * 审核人账号，一般为总后台用户
     */
    @TableField("auditing_user_id")
    private String auditingUserId;
    /**
     * 短信 msgid
     */
    private String msgid;
    /**
     * 错误信息
     */
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

    public String getAcceptCustome() {
        return acceptCustome;
    }

    public void setAcceptCustome(String acceptCustome) {
        this.acceptCustome = acceptCustome;
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

    public Integer getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(Integer isRefund) {
        this.isRefund = isRefund;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getSmsEditType() {
        return smsEditType;
    }

    public void setSmsEditType(Integer smsEditType) {
        this.smsEditType = smsEditType;
    }

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public Date getAuditingAt() {
        return auditingAt;
    }

    public void setAuditingAt(Date auditingAt) {
        this.auditingAt = auditingAt;
    }

    public String getAuditingUserId() {
        return auditingUserId;
    }

    public void setAuditingUserId(String auditingUserId) {
        this.auditingUserId = auditingUserId;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
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
        ", custom=" + custom +
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
        ", acceptCustome=" + acceptCustome +
        ", version=" + version +
        ", client=" + client +
        ", isRefund=" + isRefund +
        ", templateId=" + templateId +
        ", smsEditType=" + smsEditType +
        ", appUserId=" + appUserId +
        ", auditingAt=" + auditingAt +
        ", auditingUserId=" + auditingUserId +
        ", msgid=" + msgid +
        ", errorMsg=" + errorMsg +
        "}";
    }
}
