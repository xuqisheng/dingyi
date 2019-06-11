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
 * @author sherry
 * @since 2018-09-25
 */
@TableName("business_marketing_sms_template")
public class BusinessMarketingSmsTemplate extends Model<BusinessMarketingSmsTemplate> {

    private static final long serialVersionUID = 1L;

    /**
     * 自动编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 酒店id
     */
    @TableField("business_id")
    private Integer businessId;
    /**
     * 模板标题
     */
    @TableField("template_title")
    private String templateTitle;
    @TableField("template_content")
    private String templateContent;
    /**
     * 模板变量
     */
    @TableField("template_variable")
    private String templateVariable;
    /**
     * 创建时间
     */
    @TableField("create_at")
    private Date createAt;
    /**
     * 更新时间
     */
    @TableField("update_at")
    private Date updateAt;
    /**
     * 是否启用
     */
    @TableField("is_enable")
    private Integer isEnable;

    @TableField("use_num")
    private Integer useNum;

    /**
     * 是否自动发送
     */
    @TableField("is_auto_send")
    private Integer isAutoSend;
    /**
     * 纪念日类型
     */
    @TableField("anniversary_type")
    private Integer anniversaryType;
    /**
     * 纪念日对象
     */
    @TableField("anniversary_obj")
    private String anniversaryObj;
    /**
     * 提前天数
     */
    @TableField("advance_day_num")
    private Integer advanceDayNum;


    /**
     * 发送时间,安卓电话机固定为十点
     */
    @TableField("send_time")
    private String sendTime;




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

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public String getTemplateVariable() {
        return templateVariable;
    }

    public void setTemplateVariable(String templateVariable) {
        this.templateVariable = templateVariable;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getUseNum() {
        return useNum;
    }

    public void setUseNum(Integer useNum) {
        this.useNum = useNum;
    }

    public Integer getIsAutoSend() {
        return isAutoSend;
    }

    public void setIsAutoSend(Integer isAutoSend) {
        this.isAutoSend = isAutoSend;
    }

    public Integer getAnniversaryType() {
        return anniversaryType;
    }

    public void setAnniversaryType(Integer anniversaryType) {
        this.anniversaryType = anniversaryType;
    }

    public String getAnniversaryObj() {
        return anniversaryObj;
    }

    public void setAnniversaryObj(String anniversaryObj) {
        this.anniversaryObj = anniversaryObj;
    }

    public Integer getAdvanceDayNum() {
        return advanceDayNum;
    }

    public void setAdvanceDayNum(Integer advanceDayNum) {
        this.advanceDayNum = advanceDayNum;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    @Override
    public String toString() {
        return "BusinessMarketingSmsTemplate{" +
                "id=" + id +
                ", businessId=" + businessId +
                ", templateTitle='" + templateTitle + '\'' +
                ", templateContent='" + templateContent + '\'' +
                ", templateVariable='" + templateVariable + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", isEnable=" + isEnable +
                ", useNum=" + useNum +
                ", isAutoSend=" + isAutoSend +
                ", anniversaryType=" + anniversaryType +
                ", anniversaryObj='" + anniversaryObj + '\'' +
                ", advanceDayNum=" + advanceDayNum +
                ", sendTime=" + sendTime +
                '}';
    }
}
