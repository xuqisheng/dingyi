package com.zhidianfan.pig.yd.moduler.sms.dto.meetingnotice;

import com.zhidianfan.pig.yd.moduler.sms.dto.BaseDTO;

/**
 * 宴会提醒通知人
 * Created by ck on 2018/6/7.
 */
public class BusinessMeetingNoticeLinkmanDTO extends BaseDTO {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 酒店id
     */
    private Long businessId;

    /**
     * 宴会提醒id
     */
    private Long businessMeetingNoticeId;

    /**
     * 通知人id
     */
    private Long appUserId;

    /**
     * 通知类型
     */
    private Long noticeType;

    /**
     * 联系人姓名
     */
    private String linkmanName;

    /**
     * 联系人电话
     */
    private String linkmanPhone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getBusinessMeetingNoticeId() {
        return businessMeetingNoticeId;
    }

    public void setBusinessMeetingNoticeId(Long businessMeetingNoticeId) {
        this.businessMeetingNoticeId = businessMeetingNoticeId;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public Long getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Long noticeType) {
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
}
