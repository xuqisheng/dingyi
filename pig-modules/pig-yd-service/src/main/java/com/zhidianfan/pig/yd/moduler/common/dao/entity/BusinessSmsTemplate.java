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
@TableName("business_sms_template")
public class BusinessSmsTemplate extends Model<BusinessSmsTemplate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 酒店id
     */
    @TableField("business_id")
    private Integer businessId;
    /**
     * 模板文案
     */
    @TableField("template_content")
    private String templateContent;
    /**
     * 类型  1-预订,2-确认, 3-入座,4-换桌, 5-退订
     */
    @TableField("template_type")
    private Integer templateType;
    /**
     * 0:删除  1:启用    2:禁用
     */
    private Integer status;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 创建人
     */
    @TableField("create_person")
    private String createPerson;
    /**
     * 更新人
     */
    @TableField("update_person")
    private String updatePerson;


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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusinessSmsTemplate{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", templateContent=" + templateContent +
        ", templateType=" + templateType +
        ", status=" + status +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", createPerson=" + createPerson +
        ", updatePerson=" + updatePerson +
        "}";
    }
}
