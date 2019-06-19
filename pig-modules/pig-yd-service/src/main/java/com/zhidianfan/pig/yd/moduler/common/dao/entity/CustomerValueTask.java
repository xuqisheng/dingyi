package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 任务表
 * </p>
 *
 * @author 施杰灵
 * @since 2019-05-21
 */
@TableName("customer_value_task")
public class CustomerValueTask extends Model<CustomerValueTask> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ID_WORKER)
    private Long id;
    /**
     * 任务类型
     */
    @TableField("task_type")
    private String taskType;
    /**
     * 任务批次号
     */
    @TableField("task_batch_no")
    private Long taskBatchNo;
    /**
     * 酒店id
     */
    @TableField("hotel_id")
    private Long hotelId;
    /**
     * 集团 id
     */
    @TableField("brand_id")
    private Integer brandId;
    /**
     * 计划开始时间
     */
    @TableField("plan_time")
    private LocalDate planTime;
    /**
     * 开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;
    /**
     * 消耗时间,单位:毫秒
     */
    @TableField("spend_time")
    private Integer spendTime;
    /**
     * 任务执行标记,0-未开始,1-执行中,2-执行成功,3-执行异常
     */
    private Integer flag;
    /**
     * 备注,如果任务执行过程中发生异常,异常可以添加到此处
     */
    private String remark;
    /**
     * 排序,序号越大越先执行
     */
    private Integer sort;
    /**
     * 创建人user_id
     */
    @TableField("create_user_id")
    private Long createUserId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
    /**
     * 更新人 user_id
     */
    @TableField("update_user_id")
    private Long updateUserId;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Long getTaskBatchNo() {
        return taskBatchNo;
    }

    public void setTaskBatchNo(Long taskBatchNo) {
        this.taskBatchNo = taskBatchNo;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public LocalDate getPlanTime() {
        return planTime;
    }

    public void setPlanTime(LocalDate planTime) {
        this.planTime = planTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(Integer spendTime) {
        this.spendTime = spendTime;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CustomerValueTask{" +
        "id=" + id +
        ", taskType=" + taskType +
        ", taskBatchNo=" + taskBatchNo +
        ", hotelId=" + hotelId +
        ", brandId=" + brandId +
        ", planTime=" + planTime +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", spendTime=" + spendTime +
        ", flag=" + flag +
        ", remark=" + remark +
        ", sort=" + sort +
        ", createUserId=" + createUserId +
        ", createTime=" + createTime +
        ", updateUserId=" + updateUserId +
        ", updateTime=" + updateTime +
        "}";
    }
}
