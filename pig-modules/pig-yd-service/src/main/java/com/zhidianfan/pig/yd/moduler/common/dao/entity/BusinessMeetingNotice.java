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
 * @author Conan
 * @since 2018-09-10
 */
@TableName("business_meeting_notice")
public class BusinessMeetingNotice extends Model<BusinessMeetingNotice> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    /**
     * 1=菜肴通知 2=宴会通知 3=回访通知 4=回访成功通知
     */
    @TableField("notice_type")
    private Integer noticeType;
    /**
     * 0=不发  1=发
     */
    @TableField("notice_status")
    private Integer noticeStatus;
    @TableField("notice_day")
    private Integer noticeDay;
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

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public Integer getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(Integer noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public Integer getNoticeDay() {
        return noticeDay;
    }

    public void setNoticeDay(Integer noticeDay) {
        this.noticeDay = noticeDay;
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
        return "BusinessMeetingNotice{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", noticeType=" + noticeType +
        ", noticeStatus=" + noticeStatus +
        ", noticeDay=" + noticeDay +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
