package com.zhidianfan.pig.yd.moduler.sms.bo.marketing;

import com.zhidianfan.pig.yd.moduler.sms.bo.BaseBO;

/**
 * 营销短信模板
 * Created by ck on 2018/6/8.
 */
public class CampaignSmsTemplateBO extends BaseBO {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 精准营销id
     */
    private Long campaignId;

    /**
     * 内容
     */
    private String content;

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

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
