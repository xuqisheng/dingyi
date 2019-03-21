package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author hzp
 * @since 2018-10-19
 */
@ApiModel(value="客户分类")
@TableName("vip_class")
public class VipClass extends Model<VipClass> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="分类id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value="分类名称")
    @TableField("vip_class_name")
    private String vipClassName;
    @ApiModelProperty(value="酒店id")
    @TableField("business_id")
    private String businessId;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    @ApiModelProperty(value="备注id")
    private String remark;
    /**
     * 排序字段
     */
    @TableField("sort_id")
    private Integer sortId;
    /**
     * 1启用 0停用
     */
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVipClassName() {
        return vipClassName;
    }

    public void setVipClassName(String vipClassName) {
        this.vipClassName = vipClassName;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "VipClass{" +
        "id=" + id +
        ", vipClassName=" + vipClassName +
        ", businessId=" + businessId +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", remark=" + remark +
        ", sortId=" + sortId +
        ", status=" + status +
        "}";
    }
}
