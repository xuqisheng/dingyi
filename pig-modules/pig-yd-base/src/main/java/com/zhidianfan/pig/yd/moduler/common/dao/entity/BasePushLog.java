package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * <p>
 * 极光推送历史表
 * </p>
 *
 * @author sherry
 * @since 2018-08-21
 */
@TableName("base_push_log")
public class BasePushLog extends Model<BasePushLog> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ID_WORKER)
    private Long id;
    @TableField("app_key")
    private String appKey;
    @TableField("master_secret")
    private String masterSecret;
    @TableField("reg_id")
    private String regId;
    /**
     * 推送设备
WX-微信
APP-手机
PAD-pad预订台
     */
    private String type;
    /**
     * 用户名（手机号）
     */
    private String username;
    /**
     * 消息内容
     */
    @TableField("push_msg")
    private String pushMsg;
    @TableField("msg_seq")
    private String msgSeq;
    @TableField("business_id")
    private String businessId;
    /**
     * 推送时间
     */
    @TableField("push_time")
    private Date pushTime;
    @TableField("insert_time")
    private Date insertTime;
    /**
     * 推送是否成功
     */
    @TableField("push_status")
    private Integer pushStatus;
    @TableField("pushed_count")
    private Integer pushedCount;
    /**
     * 备注，一般在推送失败后说明
     */
    private String note;

    public Integer getPushedCount() {
        return pushedCount;
    }

    public void setPushedCount(Integer pushedCount) {
        this.pushedCount = pushedCount;
    }

    public String getMsgSeq() {
        return msgSeq;
    }

    public void setMsgSeq(String msgSeq) {
        this.msgSeq = msgSeq;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getMasterSecret() {
        return masterSecret;
    }

    public void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPushMsg() {
        return pushMsg;
    }

    public void setPushMsg(String pushMsg) {
        this.pushMsg = pushMsg;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getNote() {
        return note;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("appKey", appKey)
                .append("masterSecret", masterSecret)
                .append("regId", regId)
                .append("type", type)
                .append("username", username)
                .append("pushMsg", pushMsg)
                .append("msgSeq", msgSeq)
                .append("businessId", businessId)
                .append("pushTime", pushTime)
                .append("insertTime", insertTime)
                .append("pushStatus", pushStatus)
                .append("note", note)
                .toString();
    }
}
