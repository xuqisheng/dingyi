package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hzp
 * @since 2019-01-21
 */
@TableName("anniversary")
public class Anniversary extends Model<Anniversary> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("vip_id")
    private Integer vipId;
    @TableField("anniversary_type")
    private String anniversaryType;
    @TableField("anniversary_date")
    private Date anniversaryDate;
    /**
     * 日历类型 0-公历 1-农历
     */
    @TableField("calendar_type")
    private Integer calendarType;
    @TableField("anniversary_obj")
    private String anniversaryObj;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    @TableField("business_id")
    private Integer businessId;
    /**
     * 这个纪念日的下一次纪念日时间都为公历
     */
    @TableField("next_anniversary_time")
    private Date nextAnniversaryTime;

    @TableField("anniversary_year_flag")
    private Integer anniversaryYearFlag;

    @TableField("remark")
    private String remark;

    @TableField("anniversary_pic")
    private String anniversaryPic;

    @TableField("anniversary_title")
    private String anniversaryTitle;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public String getAnniversaryType() {
        return anniversaryType;
    }

    public void setAnniversaryType(String anniversaryType) {
        this.anniversaryType = anniversaryType;
    }

    public Date getAnniversaryDate() {
        return anniversaryDate;
    }

    public void setAnniversaryDate(Date anniversaryDate) {
        this.anniversaryDate = anniversaryDate;
    }

    public Integer getCalendarType() {
        return calendarType;
    }

    public void setCalendarType(Integer calendarType) {
        this.calendarType = calendarType;
    }

    public String getAnniversaryObj() {
        return anniversaryObj;
    }

    public void setAnniversaryObj(String anniversaryObj) {
        this.anniversaryObj = anniversaryObj;
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

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Date getNextAnniversaryTime() {
        return nextAnniversaryTime;
    }

    public void setNextAnniversaryTime(Date nextAnniversaryTime) {
        this.nextAnniversaryTime = nextAnniversaryTime;
    }

    public Integer getAnniversaryYearFlag() {
        return anniversaryYearFlag;
    }

    public void setAnniversaryYearFlag(Integer anniversaryYearFlag) {
        this.anniversaryYearFlag = anniversaryYearFlag;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAnniversaryPic() {
        return anniversaryPic;
    }

    public void setAnniversaryPic(String anniversaryPic) {
        this.anniversaryPic = anniversaryPic;
    }

    public String getAnniversaryTitle() {
        return anniversaryTitle;
    }

    public void setAnniversaryTitle(String anniversaryTitle) {
        this.anniversaryTitle = anniversaryTitle;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Anniversary{" +
                "id=" + id +
                ", vipId=" + vipId +
                ", anniversaryType='" + anniversaryType + '\'' +
                ", anniversaryDate=" + anniversaryDate +
                ", calendarType=" + calendarType +
                ", anniversaryObj='" + anniversaryObj + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", businessId=" + businessId +
                ", nextAnniversaryTime=" + nextAnniversaryTime +
                ", anniversaryYearFlag=" + anniversaryYearFlag +
                ", remark='" + remark + '\'' +
                ", anniversaryPic='" + anniversaryPic + '\'' +
                ", anniversaryTitle='" + anniversaryTitle + '\'' +
                '}';
    }
}
