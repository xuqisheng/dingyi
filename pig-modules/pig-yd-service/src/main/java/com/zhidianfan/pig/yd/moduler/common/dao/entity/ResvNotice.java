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
 * @author sherry
 * @since 2018-09-03
 */
@TableName("resv_notice")
public class ResvNotice extends Model<ResvNotice> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    @TableField("app_user_id")
    private Integer appUserId;
    /**
     * 推送内容
     */
    private String notice;
    /**
     * 操作员(来源加user_id)  手机为1 预订台为2 门店后台为3 格式例如 1-43
     */
    private String opreation;
    @TableField("resv_order")
    private String resvOrder;
    /**
     * 1为普通订单 2为宴会订单 3为线索
     */
    @TableField("resv_type")
    private Integer resvType;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    /**
     * 状态 1为未读  2为已读 3为删除
     */
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getOpreation() {
        return opreation;
    }

    public void setOpreation(String opreation) {
        this.opreation = opreation;
    }

    public String getResvOrder() {
        return resvOrder;
    }

    public void setResvOrder(String resvOrder) {
        this.resvOrder = resvOrder;
    }

    public Integer getResvType() {
        return resvType;
    }

    public void setResvType(Integer resvType) {
        this.resvType = resvType;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ResvNotice{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", appUserId=" + appUserId +
        ", notice=" + notice +
        ", opreation=" + opreation +
        ", resvOrder=" + resvOrder +
        ", resvType=" + resvType +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", status=" + status +
        "}";
    }
}
