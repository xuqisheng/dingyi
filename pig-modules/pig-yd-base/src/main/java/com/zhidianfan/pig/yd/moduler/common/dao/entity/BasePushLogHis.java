package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 极光推送历史表
 * </p>
 *
 * @author sherry
 * @since 2018-08-22
 */
@TableName("base_push_log_his")
public class BasePushLogHis extends Model<BasePushLogHis> {

    private static final long serialVersionUID = 1L;

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
    /**
     * 推送时间
     */
    @TableField("push_time")
    private Date pushTime;
    /**
     * 推送状态
	0-未推送
	1-推送成功
	2-推送失败
	3-新数据覆盖，无需推送
     */
    @TableField("push_status")
    private Integer pushStatus;
    /**
     * 备注，一般在推送失败后说明
     */
    private String note;
    /**
     * 数据入库时间
     */
    @TableField("insert_time")
    private Date insertTime;
    /**
     * 消息顺序
     */
    @TableField("msg_seq")
    private String msgSeq;
    /**
     * 酒店id
     */
    @TableField("business_id")
    private String businessId;
    /**
     * 已被推送次数
     */
    @TableField("pushed_count")
    private Integer pushedCount;


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

    public void setNote(String note) {
        this.note = note;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getMsgSeq() {
        return msgSeq;
    }

    public void setMsgSeq(String msgSeq) {
        this.msgSeq = msgSeq;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Integer getPushedCount() {
        return pushedCount;
    }

    public void setPushedCount(Integer pushedCount) {
        this.pushedCount = pushedCount;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BasePushLogHis{" +
        "id=" + id +
        ", appKey=" + appKey +
        ", masterSecret=" + masterSecret +
        ", regId=" + regId +
        ", type=" + type +
        ", username=" + username +
        ", pushMsg=" + pushMsg +
        ", pushTime=" + pushTime +
        ", pushStatus=" + pushStatus +
        ", note=" + note +
        ", insertTime=" + insertTime +
        ", msgSeq=" + msgSeq +
        ", businessId=" + businessId +
        ", pushedCount=" + pushedCount +
        "}";
    }
}
