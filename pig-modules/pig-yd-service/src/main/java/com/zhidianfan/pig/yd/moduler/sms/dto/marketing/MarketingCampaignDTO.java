package com.zhidianfan.pig.yd.moduler.sms.dto.marketing;

import com.zhidianfan.pig.yd.moduler.sms.dto.BaseDTO;

/**
 * 精准营销
 * Created by ck on 2018/6/8.
 */
public class MarketingCampaignDTO extends BaseDTO {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 营销名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 建议
     */
    private String suggestion;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态 0-禁用, 1-启用
     */
    private Long status;

    /**
     * 排序id
     */
    private Long sortId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }
}
