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
@TableName("business_sms_role")
public class BusinessSmsRole extends Model<BusinessSmsRole> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    @TableField("resv_order_type_id")
    private Integer resvOrderTypeId;
    private String order;
    @TableField("check_in")
    private String checkIn;
    private String change;
    private String cancel;
    private String confirm;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    /**
     * �������ŷ�������
     */
    @TableField("add_table")
    private Integer addTable;
    /**
     * �������Ź����Ƿ���
     */
    private Integer mend;


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

    public Integer getResvOrderTypeId() {
        return resvOrderTypeId;
    }

    public void setResvOrderTypeId(Integer resvOrderTypeId) {
        this.resvOrderTypeId = resvOrderTypeId;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
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

    public Integer getAddTable() {
        return addTable;
    }

    public void setAddTable(Integer addTable) {
        this.addTable = addTable;
    }

    public Integer getMend() {
        return mend;
    }

    public void setMend(Integer mend) {
        this.mend = mend;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusinessSmsRole{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", resvOrderTypeId=" + resvOrderTypeId +
        ", order=" + order +
        ", checkIn=" + checkIn +
        ", change=" + change +
        ", cancel=" + cancel +
        ", confirm=" + confirm +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", addTable=" + addTable +
        ", mend=" + mend +
        "}";
    }
}
