package com.zhidianfan.pig.yd.moduler.sms.dto.meetingnotice;

import com.zhidianfan.pig.yd.moduler.sms.dto.BaseDTO;

/**
 * 宴会提醒设定
 * Created by ck on 2018/6/7.
 */
public class BusinessMeetingNoticeDTO extends BaseDTO {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 酒店id
     */
    private Long businessId;

    /**
     * 提醒类型
     */
    private Long noticeType;

    /**
     * 提醒状态 0-否, 1-是
     */
    private Long noticeStatus;

    /**
     * 提醒天数
     */
    private Long noticeDay;

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

    public Long getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Long noticeType) {
        this.noticeType = noticeType;
    }

    public Long getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(Long noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public Long getNoticeDay() {
        return noticeDay;
    }

    public void setNoticeDay(Long noticeDay) {
        this.noticeDay = noticeDay;
    }
}
