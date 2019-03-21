package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 短信发送记录（与业务无关）
 * </p>
 *
 * @author sherry
 * @since 2018-08-31
 */
@TableName("base_sms_log")
public class BaseSmsLog extends Model<BaseSmsLog> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ID_WORKER)
    private Long id;
    /**
     * 可以是批量短信，多个手机号使用英文分隔符分割
     */
    private String phone;
    @TableField("req_time")
    private Date reqTime;
    @TableField("res_time")
    private Date resTime;
    /**
     * 短信内容
     */
    @TableField("sms_content")
    private String smsContent;
    /**
     * 短信发送，第三方接口返回信息
     */
    @TableField("send_res")
    private String sendRes;
    /**
     短信发送状态
     0-准备发送
     1-已发送，未确认
     2-发送后被确认成功
     3-发送后被确认失败
     4-发送失败
     */
    private Integer status = 0;
    /**
     * 备注
     */
    private String note;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getReqTime() {
        return reqTime;
    }

    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }

    public Date getResTime() {
        return resTime;
    }

    public void setResTime(Date resTime) {
        this.resTime = resTime;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public String getSendRes() {
        return sendRes;
    }

    public void setSendRes(String sendRes) {
        this.sendRes = sendRes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BaseSmsLog{" +
        "id=" + id +
        ", phone=" + phone +
        ", reqTime=" + reqTime +
        ", resTime=" + resTime +
        ", smsContent=" + smsContent +
        ", sendRes=" + sendRes +
        ", status=" + status +
        ", note=" + note +
        "}";
    }
}
