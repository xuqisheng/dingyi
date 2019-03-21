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
@TableName("dish_xl")
public class DishXl extends Model<DishXl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String xlbh;
    private String xlmc;
    private String dlbh;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    private String status;
    @TableField("business_id")
    private Integer businessId;
    @TableField("xlmc_py")
    private String xlmcPy;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getXlbh() {
        return xlbh;
    }

    public void setXlbh(String xlbh) {
        this.xlbh = xlbh;
    }

    public String getXlmc() {
        return xlmc;
    }

    public void setXlmc(String xlmc) {
        this.xlmc = xlmc;
    }

    public String getDlbh() {
        return dlbh;
    }

    public void setDlbh(String dlbh) {
        this.dlbh = dlbh;
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

    public String getXlmcPy() {
        return xlmcPy;
    }

    public void setXlmcPy(String xlmcPy) {
        this.xlmcPy = xlmcPy;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DishXl{" +
        "id=" + id +
        ", xlbh=" + xlbh +
        ", xlmc=" + xlmc +
        ", dlbh=" + dlbh +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", status=" + status +
        ", businessId=" + businessId +
        ", xlmcPy=" + xlmcPy +
        "}";
    }
}
