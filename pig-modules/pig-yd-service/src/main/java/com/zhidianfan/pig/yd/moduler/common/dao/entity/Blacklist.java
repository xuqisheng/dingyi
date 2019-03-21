package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author sherry
 * @since 2018-09-26
 */
@TableName("blacklist")
public class Blacklist extends Model<Blacklist> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 酒店id
     */
    @TableField("business_id")
    private Integer businessId;
    /**
     * 酒店名字
     */
    @TableField("business_name")
    private String businessName;
    /**
     * 客户电话
     */
    @TableField("vip_phone")
    private String vipPhone;
    /**
     * 客户姓名
     */
    @TableField("vip_name")
    private String vipName;
    /**
     * 客户性别
     */
    @TableField("vip_sex")
    private String vipSex;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private Date createdAt;
    /**
     * 更新时间
     */
    @TableField("updated_at")
    private Date updatedAt;
    /**
     * 黑名单状态
     */
    private Integer status;

    /**
     * 操作人员id
     */
    private Integer operation;


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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getVipPhone() {
        return vipPhone;
    }

    public void setVipPhone(String vipPhone) {
        this.vipPhone = vipPhone;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getVipSex() {
        return vipSex;
    }

    public void setVipSex(String vipSex) {
        this.vipSex = vipSex;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Blacklist{" +
                "id=" + id +
                ", businessId=" + businessId +
                ", businessName='" + businessName + '\'' +
                ", vipPhone='" + vipPhone + '\'' +
                ", vipName='" + vipName + '\'' +
                ", vipSex='" + vipSex + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", status=" + status +
                ", operation=" + operation +
                '}';
    }
}
