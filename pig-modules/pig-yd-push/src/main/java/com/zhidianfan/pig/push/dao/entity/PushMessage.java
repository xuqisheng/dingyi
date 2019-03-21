package com.zhidianfan.pig.push.dao.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ljh
 * @since 2018-11-02
 */
@TableName("push_message")
public class PushMessage extends Model<PushMessage> {

    private static final long serialVersionUID = 1L;

    /**
     * 自动编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 消息类型
     */
    @TableField("message_type")
    private Integer messageType;
    /**
     * 消息内容
     */
    private String message;
    /**
     * 重试次数
     */
    private Integer retry;
    /**
     * 消息发送状态
     */
    @TableField("send_status")
    private Integer sendStatus;
    /**
     * 发送目标
     */
    private String target;
    private String client;
    /**
     * 推送的平台
     */
    @TableField("push_platform")
    private String pushPlatform;
    /**
     * 推送id
     */
    @TableField("push_id")
    private String pushId;
    /**
     * 送达数量
     */
    @TableField("send_num")
    private Integer sendNum;
    /**
     * 总共需要送达数量
     */
    @TableField("total_num")
    private Integer totalNum;
    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getPushPlatform() {
        return pushPlatform;
    }

    public void setPushPlatform(String pushPlatform) {
        this.pushPlatform = pushPlatform;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public Integer getSendNum() {
        return sendNum;
    }

    public void setSendNum(Integer sendNum) {
        this.sendNum = sendNum;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PushMessage{" +
        "id=" + id +
        ", messageType=" + messageType +
        ", message=" + message +
        ", retry=" + retry +
        ", sendStatus=" + sendStatus +
        ", target=" + target +
        ", pushPlatform=" + pushPlatform +
        ", pushId=" + pushId +
        ", sendNum=" + sendNum +
        ", totalNum=" + totalNum +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
