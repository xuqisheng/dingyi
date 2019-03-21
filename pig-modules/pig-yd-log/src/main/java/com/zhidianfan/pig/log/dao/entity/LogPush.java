package com.zhidianfan.pig.log.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 推送日志
 * </p>
 *
 * @author sherry
 * @since 2018-11-05
 */
@TableName("log_push")
public class LogPush extends Model<LogPush> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    /**
     * 推送服务商
     * <p>
     * 0-本地node服务器
     * 1-极光
     * 2-公众号推送
     * 9-其他
     */
    @TableField("push_server")
    private String pushServer;
    /**
     * 用于标记客户身份，可以是设备id、别名等等
     */
    private String client;
    /**
     * 推送消息内容
     */
    private String note;
    /**
     * 推送结果
     */
    @TableField("push_res")
    private String pushRes;
    /**
     * 推送时间
     */
    @TableField("push_time")
    private Date pushTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPushServer() {
        return pushServer;
    }

    public void setPushServer(String pushServer) {
        this.pushServer = pushServer;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPushRes() {
        return pushRes;
    }

    public void setPushRes(String pushRes) {
        this.pushRes = pushRes;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "LogPush{" +
                "id=" + id +
                ", pushServer=" + pushServer +
                ", client=" + client +
                ", note=" + note +
                ", pushRes=" + pushRes +
                ", pushTime=" + pushTime +
                "}";
    }
}
