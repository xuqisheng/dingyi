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
@TableName("config_sms")
public class ConfigSms extends Model<ConfigSms> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 运营商名称
     */
    @TableField("operator_name")
    private String operatorName;

    @TableField("bean_name")
    private String beanName;
    /**
     * 是否为默认通道 0-否,1-是
     */
    @TableField("default_operator")
    private Integer defaultOperator;
    /**
     * 阈值 百分制 当前通道处于正常状态时，其短信到达率的最低值
     */
    private String threshold;
    /**
     * 人工设置通道是否可用 0-不可用 1 - 可用
     */
    private Integer status;
    /**
     * 人工干预开始时间
     */
    @TableField("start_time")
    private Date startTime;
    /**
     * 人工干预结束时间
     */
    @TableField("end_time")
    private Date endTime;
    /**
     * 计算周期,按天技术,N天内发送情况
     */
    @TableField("cal_period")
    private Integer calPeriod;


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

    public Integer getDefaultOperator() {
        return defaultOperator;
    }

    public void setDefaultOperator(Integer defaultOperator) {
        this.defaultOperator = defaultOperator;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getCalPeriod() {
        return calPeriod;
    }

    public void setCalPeriod(Integer calPeriod) {
        this.calPeriod = calPeriod;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    @Override
    public String toString() {
        return "ConfigSms{" +
                "id=" + id +
                ", operatorName='" + operatorName + '\'' +
                ", beanName='" + beanName + '\'' +
                ", defaultOperator=" + defaultOperator +
                ", threshold='" + threshold + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", calPeriod=" + calPeriod +
                '}';
    }
}
