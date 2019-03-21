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
@TableName("business_meeting_notice_linkman")
public class BusinessMeetingNoticeLinkman extends Model<BusinessMeetingNoticeLinkman> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    @TableField("business_meeting_notice_id")
    private Integer businessMeetingNoticeId;
    @TableField("app_user_id")
    private Integer appUserId;
    /**
     * 1=菜肴通知 2=宴会通知 3=回访通知 4=回访成功通知
     */
    @TableField("notice_type")
    private Integer noticeType;
    @TableField("linkman_name")
    private String linkmanName;
    @TableField("linkman_phone")
    private String linkmanPhone;
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

    public Integer getBusinessMeetingNoticeId() {
        return businessMeetingNoticeId;
    }

    public void setBusinessMeetingNoticeId(Integer businessMeetingNoticeId) {
        this.businessMeetingNoticeId = businessMeetingNoticeId;
    }

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public String getLinkmanName() {
        return linkmanName;
    }

    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName;
    }

    public String getLinkmanPhone() {
        return linkmanPhone;
    }

    public void setLinkmanPhone(String linkmanPhone) {
        this.linkmanPhone = linkmanPhone;
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
        return "BusinessMeetingNoticeLinkman{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", businessMeetingNoticeId=" + businessMeetingNoticeId +
        ", appUserId=" + appUserId +
        ", noticeType=" + noticeType +
        ", linkmanName=" + linkmanName +
        ", linkmanPhone=" + linkmanPhone +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
