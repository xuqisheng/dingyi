package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author sherry
 * @since 2018-08-16
 */
@TableName("resv_order_tem")
public class ResvOrderTem extends Model<ResvOrderTem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("resv_order")
    private String resvOrder;
    @TableField("business_id")
    private Integer businessId;
    @TableField("business_name")
    private String businessName;
    /**
     * 客户id
     */
    @TableField("vip_id")
    private Integer vipId;
    /**
     * 客户电话
     */
    @TableField("vip_phone")
    private String vipPhone;
    /**
     * 客户名称
     */
    @TableField("vip_name")
    private String vipName;
    /**
     * 客户性别
     */
    @TableField("vip_sex")
    private String vipSex;
    /**
     * 预定人数
     */
    @TableField("resv_num")
    private String resvNum;
    /**
     * 预定日期
     */
    @TableField("resv_date")
    private Date resvDate;
    /**
     * 到店时间
     */
    @TableField("dest_time")
    private String destTime;
    /**
     * 1:已预约，2、已入座，3、已结账，4、已退订
     */
    private String status;
    /**
     * 区域id
     */
    @TableField("table_area_id")
    private Integer tableAreaId;
    /**
     * 区域名称
     */
    @TableField("table_area_name")
    private String tableAreaName;
    @TableField("area_code")
    private String areaCode;
    /**
     * 桌位id
     */
    @TableField("table_id")
    private Integer tableId;
    /**
     * 桌位名称
     */
    @TableField("table_name")
    private String tableName;
    @TableField("table_code")
    private String tableCode;
    /**
     * 用餐时段id
     */
    @TableField("meal_type_id")
    private Integer mealTypeId;
    /**
     * 用餐时段name
     */
    @TableField("meal_type_name")
    private String mealTypeName;
    /**
     * 0 表示未被更新 1表示已被更新
     */
    @TableField("update_status")
    private Integer updateStatus;
    /**
     * 西软第三方订单是否更新0:未更新,1:已更新
     */
    @TableField
    private Integer xmsUpdateStatus;
    /**
     * 金额
     */
    private String payamount;
    /**
     * 退订原因
     */
    @TableField("unorder_reason")
    private String unorderReason;
    /**
     * 订单创建时间
     */
    @TableField("created_at")
    private Date createdAt;
    /**
     * 订单写入临时表时间
     */
    @TableField("updated_at")
    private Date updatedAt;
    @TableField("meal_type_code")
    private String mealTypeCode;
    @TableField("t_created_at")
    private Date tCreatedAt;
    @TableField("t_updated_at")
    private Date tUpdatedAt;
    /**
     * 海亨回调备注
     */
    private String remark;
    @TableField("app_user_code")
    private String appUserCode;
    @TableField("app_user_name")
    private String appUserName;
    /**
     * 是否点菜 NULL 0 未点菜，1或者>=1点菜
     */
    @TableField("is_dish")
    private Integer isDish;
    @TableField("third_order_no")
    private String thirdOrderNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
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

    public String getResvNum() {
        return resvNum;
    }

    public void setResvNum(String resvNum) {
        this.resvNum = resvNum;
    }

    public Date getResvDate() {
        return resvDate;
    }

    public void setResvDate(Date resvDate) {
        this.resvDate = resvDate;
    }

    public String getDestTime() {
        return destTime;
    }

    public void setDestTime(String destTime) {
        this.destTime = destTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTableAreaId() {
        return tableAreaId;
    }

    public void setTableAreaId(Integer tableAreaId) {
        this.tableAreaId = tableAreaId;
    }

    public String getTableAreaName() {
        return tableAreaName;
    }

    public void setTableAreaName(String tableAreaName) {
        this.tableAreaName = tableAreaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public Integer getMealTypeId() {
        return mealTypeId;
    }

    public void setMealTypeId(Integer mealTypeId) {
        this.mealTypeId = mealTypeId;
    }

    public String getMealTypeName() {
        return mealTypeName;
    }

    public void setMealTypeName(String mealTypeName) {
        this.mealTypeName = mealTypeName;
    }

    public Integer getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }

    public Integer getXmsUpdateStatus() {
        return xmsUpdateStatus;
    }

    public void setXmsUpdateStatus(Integer xmsUpdateStatus) {
        this.xmsUpdateStatus = xmsUpdateStatus;
    }

    public String getPayamount() {
        return payamount;
    }

    public void setPayamount(String payamount) {
        this.payamount = payamount;
    }

    public String getUnorderReason() {
        return unorderReason;
    }

    public void setUnorderReason(String unorderReason) {
        this.unorderReason = unorderReason;
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

    public String getMealTypeCode() {
        return mealTypeCode;
    }

    public void setMealTypeCode(String mealTypeCode) {
        this.mealTypeCode = mealTypeCode;
    }

    public Date gettCreatedAt() {
        return tCreatedAt;
    }

    public void settCreatedAt(Date tCreatedAt) {
        this.tCreatedAt = tCreatedAt;
    }

    public Date gettUpdatedAt() {
        return tUpdatedAt;
    }

    public void settUpdatedAt(Date tUpdatedAt) {
        this.tUpdatedAt = tUpdatedAt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAppUserCode() {
        return appUserCode;
    }

    public void setAppUserCode(String appUserCode) {
        this.appUserCode = appUserCode;
    }

    public String getAppUserName() {
        return appUserName;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }

    public Integer getIsDish() {
        return isDish;
    }

    public void setIsDish(Integer isDish) {
        this.isDish = isDish;
    }

    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ResvOrderTem{" +
        "id=" + id +
        ", resvOrder=" + resvOrder +
        ", businessId=" + businessId +
        ", businessName=" + businessName +
        ", vipId=" + vipId +
        ", vipPhone=" + vipPhone +
        ", vipName=" + vipName +
        ", vipSex=" + vipSex +
        ", resvNum=" + resvNum +
        ", resvDate=" + resvDate +
        ", destTime=" + destTime +
        ", status=" + status +
        ", tableAreaId=" + tableAreaId +
        ", tableAreaName=" + tableAreaName +
        ", areaCode=" + areaCode +
        ", tableId=" + tableId +
        ", tableName=" + tableName +
        ", tableCode=" + tableCode +
        ", mealTypeId=" + mealTypeId +
        ", mealTypeName=" + mealTypeName +
        ", updateStatus=" + updateStatus +
        ", payamount=" + payamount +
        ", unorderReason=" + unorderReason +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", mealTypeCode=" + mealTypeCode +
        ", tCreatedAt=" + tCreatedAt +
        ", tUpdatedAt=" + tUpdatedAt +
        ", remark=" + remark +
        ", appUserCode=" + appUserCode +
        ", appUserName=" + appUserName +
        ", isDish=" + isDish +
        ", thirdOrderNo=" + thirdOrderNo +
        "}";
    }
}
