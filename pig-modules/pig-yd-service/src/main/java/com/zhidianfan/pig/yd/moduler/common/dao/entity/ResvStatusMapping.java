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
@TableName("resv_status_mapping")
public class ResvStatusMapping extends Model<ResvStatusMapping> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单状态编码id
     */
    @TableField("status_id")
    private Integer statusId;
    /**
     * 订单状态编码名称
     */
    @TableField("status_name")
    private String statusName;
    /**
     * 排序
     */
    @TableField("status_seq")
    private String statusSeq;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusSeq() {
        return statusSeq;
    }

    public void setStatusSeq(String statusSeq) {
        this.statusSeq = statusSeq;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ResvStatusMapping{" +
        "id=" + id +
        ", statusId=" + statusId +
        ", statusName=" + statusName +
        ", statusSeq=" + statusSeq +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
