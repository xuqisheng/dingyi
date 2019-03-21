package com.zhidianfan.pig.yd.moduler.sms.query.business;

import com.zhidianfan.pig.yd.moduler.sms.query.BaseQO;

/**
 * 通知短信模板
 * Created by Administrator on 2017/12/27.
 */
public class BusinessSMSTemplateQO extends BaseQO {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 模板内容
     */
    private String templateContent;

    /**
     * 模板类型
     */
    private Integer templateType;

    /**
     * 模板状态
     */
    private Integer status;

    /**
     * 酒店id
     */
    private Long businessId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }
}
