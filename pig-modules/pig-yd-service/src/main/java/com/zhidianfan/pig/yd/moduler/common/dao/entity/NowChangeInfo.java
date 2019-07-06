package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import java.time.LocalDateTime;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 实时变更信息表
 * </p>
 *
 * @author 
 * @since 2019-05-29
 */
@TableName("now_change_info")
public class NowChangeInfo extends Model<NowChangeInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,vip 表中的主键
     */
    @TableId(value = "vip_id", type = IdType.INPUT)
    private Integer vipId;
    /**
     * 之前的值
     */
    private Integer value;
    /**
     * 改变后的值
     */
    private String type;
    /**
     * 更改时间
     */
    @TableField("change_time")
    private LocalDateTime changeTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(LocalDateTime changeTime) {
        this.changeTime = changeTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.vipId;
    }

    @Override
    public String toString() {
        return "NowChangeInfo{" +
        "vipId=" + vipId +
        ", value=" + value +
        ", type=" + type +
        ", changeTime=" + changeTime +
        ", remark=" + remark +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
