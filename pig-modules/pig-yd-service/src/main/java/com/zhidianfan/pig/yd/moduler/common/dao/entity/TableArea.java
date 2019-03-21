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
 * @since 2018-08-16
 */
@TableName("table_area")
public class TableArea extends Model<TableArea> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    @TableField("area_code")
    private String areaCode;
    /**
     * 区域名称
     */
    @TableField("table_area_name")
    private String tableAreaName;
    /**
     * 0停用1启用
     */
    private String status;
    @TableField("sort_id")
    private Integer sortId;
    @TableField("pic_url")
    private String picUrl;
    /**
     * 区域备注
     */
    private String remark;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    @TableField("pccode")
    private String pccode;

    /**
     * 最后一次状态改变的桌位id，英文逗号隔开
     */
    @TableField("status_change_table_ids")
    private String statusChangeTableIds;


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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getTableAreaName() {
        return tableAreaName;
    }

    public void setTableAreaName(String tableAreaName) {
        this.tableAreaName = tableAreaName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getPccode() {
        return pccode;
    }

    public void setPccode(String pccode) {
        this.pccode = pccode;
    }

    public String getStatusChangeTableIds() {
        return statusChangeTableIds;
    }

    public void setStatusChangeTableIds(String statusChangeTableIds) {
        this.statusChangeTableIds = statusChangeTableIds;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TableArea{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", areaCode=" + areaCode +
        ", tableAreaName=" + tableAreaName +
        ", status=" + status +
        ", sortId=" + sortId +
        ", picUrl=" + picUrl +
        ", remark=" + remark +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
                ", statusChangeTableIds=" + statusChangeTableIds +
        "}";
    }
}
