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
 * 
 * </p>
 *
 * @author qqx
 * @since 2018-11-28
 */
@TableName("sn_code_log")
public class SnCodeLog extends Model<SnCodeLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    /**
     * sn码
     */
    @TableField("sn_code")
    private String snCode;
    /**
     * 状态
     */
    private Integer status;
    @TableField("status_name")
    private String statusName;
    /**
     * 经销商
     */
    @TableField("agent_id")
    private Long agentId;
    @TableField("agent_name")
    private String agentName;
    /**
     * 备注
     */
    private String remark;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    /**
     * 操作人
     */
    private String operator;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SnCodeLog{" +
        "id=" + id +
        ", snCode=" + snCode +
        ", status=" + status +
        ", statusName=" + statusName +
        ", agentId=" + agentId +
        ", agentName=" + agentName +
        ", remark=" + remark +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", operator=" + operator +
        "}";
    }
}
