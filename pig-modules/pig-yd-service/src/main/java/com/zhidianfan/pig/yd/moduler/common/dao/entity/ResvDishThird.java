package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
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
 * @since 2018-08-17
 */
@TableName("resv_dish_third")
public class ResvDishThird extends Model<ResvDishThird> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("dish_name")
    private String dishName;
    @TableField("dish_num")
    private Double dishNum;
    @TableField("dish_price")
    private BigDecimal dishPrice;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    @TableField("batch_no")
    private String batchNo;
    private String cmbh;
    private String dlbh;
    @TableField("resv_order")
    private String resvOrder;
    private String dwbh;
    private String dwmc;
    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Double getDishNum() {
        return dishNum;
    }

    public void setDishNum(Double dishNum) {
        this.dishNum = dishNum;
    }

    public BigDecimal getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(BigDecimal dishPrice) {
        this.dishPrice = dishPrice;
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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCmbh() {
        return cmbh;
    }

    public void setCmbh(String cmbh) {
        this.cmbh = cmbh;
    }

    public String getDlbh() {
        return dlbh;
    }

    public void setDlbh(String dlbh) {
        this.dlbh = dlbh;
    }

    public String getResvOrder() {
        return resvOrder;
    }

    public void setResvOrder(String resvOrder) {
        this.resvOrder = resvOrder;
    }

    public String getDwbh() {
        return dwbh;
    }

    public void setDwbh(String dwbh) {
        this.dwbh = dwbh;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ResvDishThird{" +
        "id=" + id +
        ", dishName=" + dishName +
        ", dishNum=" + dishNum +
        ", dishPrice=" + dishPrice +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", batchNo=" + batchNo +
        ", cmbh=" + cmbh +
        ", dlbh=" + dlbh +
        ", resvOrder=" + resvOrder +
        ", dwbh=" + dwbh +
        ", dwmc=" + dwmc +
        ", remark=" + remark +
        "}";
    }
}
