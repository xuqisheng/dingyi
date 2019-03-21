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
@TableName("dish_cm")
public class DishCm extends Model<DishCm> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String cmbh;
    private String cmmc;
    private String xlbh;
    private Double cmje;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    private String status;
    @TableField("business_id")
    private Integer businessId;
    @TableField("cmmc_py")
    private String cmmcPy;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCmbh() {
        return cmbh;
    }

    public void setCmbh(String cmbh) {
        this.cmbh = cmbh;
    }

    public String getCmmc() {
        return cmmc;
    }

    public void setCmmc(String cmmc) {
        this.cmmc = cmmc;
    }

    public String getXlbh() {
        return xlbh;
    }

    public void setXlbh(String xlbh) {
        this.xlbh = xlbh;
    }

    public Double getCmje() {
        return cmje;
    }

    public void setCmje(Double cmje) {
        this.cmje = cmje;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getCmmcPy() {
        return cmmcPy;
    }

    public void setCmmcPy(String cmmcPy) {
        this.cmmcPy = cmmcPy;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DishCm{" +
        "id=" + id +
        ", cmbh=" + cmbh +
        ", cmmc=" + cmmc +
        ", xlbh=" + xlbh +
        ", cmje=" + cmje +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", status=" + status +
        ", businessId=" + businessId +
        ", cmmcPy=" + cmmcPy +
        "}";
    }
}
