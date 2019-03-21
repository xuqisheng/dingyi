package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 定时任务执行日志
 * </p>
 *
 * @author zhoubeibei
 * @since 2018-12-09
 */
@TableName("resv_task")
public class ResvTask extends Model<ResvTask> {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 任务id，一次调用一个任务id，由xxl-job在调用前生成
     */
    @TableField("task_id")
    private String taskId;
    /**
     * 任务名称
     */
    @TableField("task_name")
    private String taskName;
    /**
     * 任务执行过程的顺序id，标记任务内部执行流程
     */
    private String seq;
    /**
     * 本次调用开始时间
     */
    @TableField("start_time")
    private Date startTime;
    /**
     * 本次调用结束时间
     */
    @TableField("end_time")
    private Date endTime;
    /**
     * 执行此步骤时的系统时间
     */
    @TableField("insert_time")
    private Date insertTime;
    /**
     * 任务执行耗时（毫秒）
     */
    private Integer speed;
    /**
     * 任务执行结果
0-失败
1-成功
     */
    private Integer result;
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
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

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
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
        return "ResvTask{" +
        "id=" + id +
        ", taskId=" + taskId +
        ", taskName=" + taskName +
        ", seq=" + seq +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", insertTime=" + insertTime +
        ", speed=" + speed +
        ", result=" + result +
        ", note=" + note +
        "}";
    }
}
