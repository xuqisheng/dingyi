package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 订单评论表
 * </p>
 *
 * @author ljh
 * @since 2018-12-19
 */
@TableName("resv_order_rating")
public class ResvOrderRating extends Model<ResvOrderRating> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    /**
     * 批次号
     */
    @TableField("batch_no")
    private String batchNo;
    /**
     * 客户id
     */
    @TableField("vip_id")
    private Integer vipId;
    /**
     * 评分 
     */
    private Integer grade;
    /**
     * 评论
     */
    private String remarks;
    /**
     * 创建时间
     */
    @TableField("create_at")
    private Date createAt;


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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ResvOrderRating{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", batchNo=" + batchNo +
        ", vipId=" + vipId +
        ", grade=" + grade +
        ", remarks=" + remarks +
        ", createAt=" + createAt +
        "}";
    }
}
