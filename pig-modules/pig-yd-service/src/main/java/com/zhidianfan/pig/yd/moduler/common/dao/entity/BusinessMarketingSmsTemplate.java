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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusinessMarketingSmsTemplate{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", templateTitle=" + templateTitle +
        ", templateContent=" + templateContent +
        ", templateVariable=" + templateVariable +
        ", createAt=" + createAt +
        ", updateAt=" + updateAt +
        ", isEnable=" + isEnable +
                ", useNum=" + useNum +
        "}";
    }
}
