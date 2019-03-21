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
@TableName("dish_dl")
public class DishDl extends Model<DishDl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String dlbh;
    private String dlmc;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    private String status;
    @TableField("business_id")
    private Integer businessId;
    @TableField("dlmc_py")
    private String dlmcPy;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDlbh() {
        return dlbh;
    }

    public void setDlbh(String dlbh) {
        this.dlbh = dlbh;
    }

    public String getDlmc() {
        return dlmc;
    }

    public void setDlmc(String dlmc) {
        this.dlmc = dlmc;
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

    public String getDlmcPy() {
        return dlmcPy;
    }

    public void setDlmcPy(String dlmcPy) {
        this.dlmcPy = dlmcPy;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DishDl{" +
        "id=" + id +
        ", dlbh=" + dlbh +
        ", dlmc=" + dlmc +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", status=" + status +
        ", businessId=" + businessId +
        ", dlmcPy=" + dlmcPy +
        "}";
    }
}
