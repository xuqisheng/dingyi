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
 * @author sherry
 * @since 2018-09-03
 */
@TableName("resv_order_logs")
public class ResvOrderLogs extends Model<ResvOrderLogs> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("resv_order")
    private String resvOrder;
    private String status;
    @TableField("status_name")
    private String statusName;
    private String logs;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    @TableField("app_user_id")
    private Integer appUserId;
    @TableField("device_user_id")
    private Integer deviceUserId;

    /**
     * 安卓电话机用户id
     */
    @TableField("android_user_id")
    private Integer androidUserId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResvOrder() {
        return resvOrder;
    }

    public void setResvOrder(String resvOrder) {
        this.resvOrder = resvOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
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

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public Integer getDeviceUserId() {
        return deviceUserId;
    }

    public void setDeviceUserId(Integer deviceUserId) {
        this.deviceUserId = deviceUserId;
    }

    public Integer getAndroidUserId() {
        return androidUserId;
    }

    public void setAndroidUserId(Integer androidUserId) {
        this.androidUserId = androidUserId;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ResvOrderLogs{" +
                "id=" + id +
                ", resvOrder='" + resvOrder + '\'' +
                ", status='" + status + '\'' +
                ", statusName='" + statusName + '\'' +
                ", logs='" + logs + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", appUserId=" + appUserId +
                ", deviceUserId=" + deviceUserId +
                ", androidUserId=" + androidUserId +
                '}';
    }
}
