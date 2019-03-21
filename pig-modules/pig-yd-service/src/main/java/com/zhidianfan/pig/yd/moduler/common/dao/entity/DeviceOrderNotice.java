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
 * 通知表:
  手机客户端预定，换桌，需预定台审核的预定内容的通知表。
 * </p>
 *
 * @author qqx
 * @since 2018-11-13
 */
@TableName("device_order_notice")
public class DeviceOrderNotice extends Model<DeviceOrderNotice> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("resv_order")
    private String resvOrder;
    @TableField("business_id")
    private Integer businessId;
    private String type;
    private String status;
    @TableField("created_at")
    private Date createdAt;
    @TableField("vip_name")
    private String vipName;
    @TableField("vip_phone")
    private String vipPhone;
    @TableField("resv_date")
    private Date resvDate;
    @TableField("table_name")
    private String tableName;
    @TableField("old_table_name")
    private String oldTableName;
    @TableField("old_meal_type_name")
    private String oldMealTypeName;
    @TableField("old_resv_date")
    private String oldResvDate;
    private String operater;
    @TableField("resv_num")
    private String resvNum;
    private String remark;
    @TableField("meal_type_name")
    private String mealTypeName;
    @TableField("app_user_name")
    private String appUserName;
    /**
     * 短信失败，状态，不为空即为失败短信
     */
    @TableField("sms_status")
    private String smsStatus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResvOrder() {
        return resvOrder;
    }

    public void setResvOrder(String resvOrder) {
        this.resvOrder = resvOrder;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getVipPhone() {
        return vipPhone;
    }

    public void setVipPhone(String vipPhone) {
        this.vipPhone = vipPhone;
    }

    public Date getResvDate() {
        return resvDate;
    }

    public void setResvDate(Date resvDate) {
        this.resvDate = resvDate;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOldTableName() {
        return oldTableName;
    }

    public void setOldTableName(String oldTableName) {
        this.oldTableName = oldTableName;
    }

    public String getOldMealTypeName() {
        return oldMealTypeName;
    }

    public void setOldMealTypeName(String oldMealTypeName) {
        this.oldMealTypeName = oldMealTypeName;
    }

    public String getOldResvDate() {
        return oldResvDate;
    }

    public void setOldResvDate(String oldResvDate) {
        this.oldResvDate = oldResvDate;
    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater;
    }

    public String getResvNum() {
        return resvNum;
    }

    public void setResvNum(String resvNum) {
        this.resvNum = resvNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMealTypeName() {
        return mealTypeName;
    }

    public void setMealTypeName(String mealTypeName) {
        this.mealTypeName = mealTypeName;
    }

    public String getAppUserName() {
        return appUserName;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }

    public String getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(String smsStatus) {
        this.smsStatus = smsStatus;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DeviceOrderNotice{" +
        "id=" + id +
        ", resvOrder=" + resvOrder +
        ", businessId=" + businessId +
        ", type=" + type +
        ", status=" + status +
        ", createdAt=" + createdAt +
        ", vipName=" + vipName +
        ", vipPhone=" + vipPhone +
        ", resvDate=" + resvDate +
        ", tableName=" + tableName +
        ", oldTableName=" + oldTableName +
        ", oldMealTypeName=" + oldMealTypeName +
        ", oldResvDate=" + oldResvDate +
        ", operater=" + operater +
        ", resvNum=" + resvNum +
        ", remark=" + remark +
        ", mealTypeName=" + mealTypeName +
        ", appUserName=" + appUserName +
        ", smsStatus=" + smsStatus +
        "}";
    }
}
