package com.zhidianfan.pig.yd.sms.dao.entity;

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
 * @author hzp
 * @since 2018-11-02
 */
@TableName("task_sms_result")
public class TaskSmsResult extends Model<TaskSmsResult> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 运营商名称
     */
    @TableField("operator_name")
    private String operatorName;
    /**
     * 对应通道接口的实现类在Spring中的注册名
     */
    @TableField("bean_name")
    private String beanName;
    /**
     * 当前是否使用此通道 0-不启用 1-启用
     */
    @TableField("operator_status")
    private Integer operatorStatus;
    /**
     * 最近更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 变更理由
     */
    private String remark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Integer getOperatorStatus() {
        return operatorStatus;
    }

    public void setOperatorStatus(Integer operatorStatus) {
        this.operatorStatus = operatorStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TaskSmsResult{" +
        "id=" + id +
        ", operatorName=" + operatorName +
        ", beanName=" + beanName +
        ", operatorStatus=" + operatorStatus +
        ", updateTime=" + updateTime +
        ", remark=" + remark +
        "}";
    }
}
