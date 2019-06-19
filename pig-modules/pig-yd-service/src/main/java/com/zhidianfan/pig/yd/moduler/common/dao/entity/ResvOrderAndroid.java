package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hzp
 * @since 2019-01-15
 */
@TableName("resv_order_android")
public class ResvOrderAndroid extends Model<ResvOrderAndroid> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 批次号
     */
    @TableField("batch_no")
    private String batchNo;
    /**
     * 订单号
     */
    @TableField("resv_order")
    private String resvOrder;
    @TableField("business_id")
    private Integer businessId;
    @TableField("business_name")
    private String businessName;
    @TableField("app_user_id")
    private Integer appUserId;
    @TableField("app_user_phone")
    private String appUserPhone;
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
     * 客户公司
     */
    @TableField("vip_company")
    private String vipCompany;
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
    /**
     * 桌位容纳最大人数
     */
    @TableField("max_people_num")
    private String maxPeopleNum;
    /**
     * 预定人数
     */
    @TableField("resv_num")
    private String resvNum;
    /**
     * 实际人数
     */
    @TableField("actual_num")
    private String actualNum;
    /**
     * 预定日期
     */
    @TableField("resv_date")
    private Date resvDate;
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
     * 分餐次id a
     */
    @TableField("meal_type_id_a")
    private Integer mealTypeIdA;
    /**
     * 分餐次名称a
     */
    @TableField("meal_type_name_a")
    private String mealTypeNameA;
    /**
     * 分餐次id b
     */
    @TableField("meal_type_id_b")
    private Integer mealTypeIdB;
    /**
     * 分餐次名称b
     */
    @TableField("meal_type_name_b")
    private String mealTypeNameB;
    /**
     * 订单备注
     */
    private String remark;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    /**
     * 1:已预约，2、已入座，3、已结账，4、已退订
     */
    private String status;
    /**
     * 0是未确认一定会来，1是确认要来就餐
     */
    private String confirm;
    @TableField("app_user_name")
    private String appUserName;
    @TableField("device_user_id")
    private Integer deviceUserId;
    @TableField("device_user_name")
    private String deviceUserName;
    @TableField("device_user_phone")
    private String deviceUserPhone;
    private String peicai;
    @TableField("pic_url")
    private String picUrl;
    private String payamount;
    @TableField("unorder_reason")
    private String unorderReason;
    /**
     * 1是有押金0是没有押金
     */
    private String deposit;
    /**
     * 0是非锁台1是锁台
     */
    private String locked;
    @TableField("receipt_no")
    private String receiptNo;
    @TableField("deposit_amount")
    private String depositAmount;
    @TableField("pay_type")
    private Integer payType;
    @TableField("resv_order_type")
    private Integer resvOrderType;
    /**
     * 锁台原因 1.是因为宴会锁台  2.换衣间锁台
     */
    @TableField("locked_type")
    private Integer lockedType;
    /**
     * 因宴会锁台的关联宴会订单号
     */
    @TableField("resv_meeting_order_no")
    private String resvMeetingOrderNo;
    /**
     * 外部来源
     */
    @TableField("external_source_name")
    private String externalSourceName;
    @TableField("external_source_id")
    private Integer externalSourceId;
    /**
     * 是否第三方订单
     */
    private Integer istirdparty;
    /**
     * 是否换桌
     */
    private Integer ischangetable;
    /**
     * 是否跨餐别 0-否 1-是
     */
    private Integer iskbc;
    private String tag;
    /**
     * 配菜金额
     */
    @TableField("peicai_amt")
    private Double peicaiAmt;
    /**
     * 配菜类型 0-人均 1-桌均
     */
    @TableField("peicai_type")
    private Integer peicaiType;
    @TableField("dest_time")
    private String destTime;
    @TableField("property_id")
    private Integer propertyId;
    @TableField("property_name")
    private String propertyName;
    /**
     * 0普通预订，1排位预订
     */
    private Integer isrank;
    private Integer issendmsg;
    @TableField("re_vip_phone")
    private String reVipPhone;
    @TableField("re_vip_name")
    private String reVipName;
    @TableField("re_vip_sex")
    private String reVipSex;
    /**
     * 是否接口同步
     */
    @TableField("open_is_sync")
    private Integer openIsSync;
    /**
     * 历史状态
     */
    @TableField("order_status")
    private Integer orderStatus;
    @TableField("deposit_status")
    private Integer depositStatus;
    @TableField("deposit_desc")
    private String depositDesc;
    @TableField("is_dish")
    private Integer isDish;
    @TableField("third_order_no")
    private String thirdOrderNo;

    @TableField("unorder_reason_id")
    private String unorderReasonId;


    /**
     * 安卓电话机用户id
     */
    @TableField("android_user_id")
    private Integer androidUserId;
    @TableField("android_user_name")
    private String androidUserName;


    @TableField("device_type")
    private String deviceType;


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("batchNo", batchNo)
                .append("resvOrder", resvOrder)
                .append("businessId", businessId)
                .append("businessName", businessName)
                .append("appUserId", appUserId)
                .append("appUserPhone", appUserPhone)
                .append("vipId", vipId)
                .append("vipPhone", vipPhone)
                .append("vipName", vipName)
                .append("vipSex", vipSex)
                .append("vipCompany", vipCompany)
                .append("tableAreaId", tableAreaId)
                .append("tableAreaName", tableAreaName)
                .append("tableId", tableId)
                .append("tableName", tableName)
                .append("maxPeopleNum", maxPeopleNum)
                .append("resvNum", resvNum)
                .append("actualNum", actualNum)
                .append("resvDate", resvDate)
                .append("mealTypeId", mealTypeId)
                .append("mealTypeName", mealTypeName)
                .append("mealTypeIdA", mealTypeIdA)
                .append("mealTypeNameA", mealTypeNameA)
                .append("mealTypeIdB", mealTypeIdB)
                .append("mealTypeNameB", mealTypeNameB)
                .append("remark", remark)
                .append("createdAt", createdAt)
                .append("updatedAt", updatedAt)
                .append("status", status)
                .append("confirm", confirm)
                .append("appUserName", appUserName)
                .append("deviceUserId", deviceUserId)
                .append("deviceUserName", deviceUserName)
                .append("deviceUserPhone", deviceUserPhone)
                .append("peicai", peicai)
                .append("picUrl", picUrl)
                .append("payamount", payamount)
                .append("unorderReason", unorderReason)
                .append("deposit", deposit)
                .append("locked", locked)
                .append("receiptNo", receiptNo)
                .append("depositAmount", depositAmount)
                .append("payType", payType)
                .append("resvOrderType", resvOrderType)
                .append("lockedType", lockedType)
                .append("resvMeetingOrderNo", resvMeetingOrderNo)
                .append("externalSourceName", externalSourceName)
                .append("externalSourceId", externalSourceId)
                .append("istirdparty", istirdparty)
                .append("ischangetable", ischangetable)
                .append("iskbc", iskbc)
                .append("tag", tag)
                .append("peicaiAmt", peicaiAmt)
                .append("peicaiType", peicaiType)
                .append("destTime", destTime)
                .append("propertyId", propertyId)
                .append("propertyName", propertyName)
                .append("isrank", isrank)
                .append("issendmsg", issendmsg)
                .append("reVipPhone", reVipPhone)
                .append("reVipName", reVipName)
                .append("reVipSex", reVipSex)
                .append("openIsSync", openIsSync)
                .append("orderStatus", orderStatus)
                .append("depositStatus", depositStatus)
                .append("depositDesc", depositDesc)
                .append("isDish", isDish)
                .append("thirdOrderNo", thirdOrderNo)
                .append("unorderReasonId",unorderReasonId)
                .append("androidUserId",androidUserId)
                .append("androidUserName",androidUserName)
                .append("deviceType",deviceType)
                .toString();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public String getAppUserPhone() {
        return appUserPhone;
    }

    public void setAppUserPhone(String appUserPhone) {
        this.appUserPhone = appUserPhone;
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

    public String getVipCompany() {
        return vipCompany;
    }

    public void setVipCompany(String vipCompany) {
        this.vipCompany = vipCompany;
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

    public String getMaxPeopleNum() {
        return maxPeopleNum;
    }

    public void setMaxPeopleNum(String maxPeopleNum) {
        this.maxPeopleNum = maxPeopleNum;
    }

    public String getResvNum() {
        return resvNum;
    }

    public void setResvNum(String resvNum) {
        this.resvNum = resvNum;
    }

    public String getActualNum() {
        return actualNum;
    }

    public void setActualNum(String actualNum) {
        this.actualNum = actualNum;
    }

    public Date getResvDate() {
        return resvDate;
    }

    public void setResvDate(Date resvDate) {
        this.resvDate = resvDate;
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

    public Integer getMealTypeIdA() {
        return mealTypeIdA;
    }

    public void setMealTypeIdA(Integer mealTypeIdA) {
        this.mealTypeIdA = mealTypeIdA;
    }

    public String getMealTypeNameA() {
        return mealTypeNameA;
    }

    public void setMealTypeNameA(String mealTypeNameA) {
        this.mealTypeNameA = mealTypeNameA;
    }

    public Integer getMealTypeIdB() {
        return mealTypeIdB;
    }

    public void setMealTypeIdB(Integer mealTypeIdB) {
        this.mealTypeIdB = mealTypeIdB;
    }

    public String getMealTypeNameB() {
        return mealTypeNameB;
    }

    public void setMealTypeNameB(String mealTypeNameB) {
        this.mealTypeNameB = mealTypeNameB;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getAppUserName() {
        return appUserName;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }

    public Integer getDeviceUserId() {
        return deviceUserId;
    }

    public void setDeviceUserId(Integer deviceUserId) {
        this.deviceUserId = deviceUserId;
    }

    public String getDeviceUserName() {
        return deviceUserName;
    }

    public void setDeviceUserName(String deviceUserName) {
        this.deviceUserName = deviceUserName;
    }

    public String getDeviceUserPhone() {
        return deviceUserPhone;
    }

    public void setDeviceUserPhone(String deviceUserPhone) {
        this.deviceUserPhone = deviceUserPhone;
    }

    public String getPeicai() {
        return peicai;
    }

    public void setPeicai(String peicai) {
        this.peicai = peicai;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(String depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getResvOrderType() {
        return resvOrderType;
    }

    public void setResvOrderType(Integer resvOrderType) {
        this.resvOrderType = resvOrderType;
    }

    public Integer getLockedType() {
        return lockedType;
    }

    public void setLockedType(Integer lockedType) {
        this.lockedType = lockedType;
    }

    public String getResvMeetingOrderNo() {
        return resvMeetingOrderNo;
    }

    public void setResvMeetingOrderNo(String resvMeetingOrderNo) {
        this.resvMeetingOrderNo = resvMeetingOrderNo;
    }

    public String getExternalSourceName() {
        return externalSourceName;
    }

    public void setExternalSourceName(String externalSourceName) {
        this.externalSourceName = externalSourceName;
    }

    public Integer getExternalSourceId() {
        return externalSourceId;
    }

    public void setExternalSourceId(Integer externalSourceId) {
        this.externalSourceId = externalSourceId;
    }

    public Integer getIstirdparty() {
        return istirdparty;
    }

    public void setIstirdparty(Integer istirdparty) {
        this.istirdparty = istirdparty;
    }

    public Integer getIschangetable() {
        return ischangetable;
    }

    public void setIschangetable(Integer ischangetable) {
        this.ischangetable = ischangetable;
    }

    public Integer getIskbc() {
        return iskbc;
    }

    public void setIskbc(Integer iskbc) {
        this.iskbc = iskbc;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Double getPeicaiAmt() {
        return peicaiAmt;
    }

    public void setPeicaiAmt(Double peicaiAmt) {
        this.peicaiAmt = peicaiAmt;
    }

    public Integer getPeicaiType() {
        return peicaiType;
    }

    public void setPeicaiType(Integer peicaiType) {
        this.peicaiType = peicaiType;
    }

    public String getDestTime() {
        return destTime;
    }

    public void setDestTime(String destTime) {
        this.destTime = destTime;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Integer getIsrank() {
        return isrank;
    }

    public void setIsrank(Integer isrank) {
        this.isrank = isrank;
    }

    public Integer getIssendmsg() {
        return issendmsg;
    }

    public void setIssendmsg(Integer issendmsg) {
        this.issendmsg = issendmsg;
    }

    public String getReVipPhone() {
        return reVipPhone;
    }

    public void setReVipPhone(String reVipPhone) {
        this.reVipPhone = reVipPhone;
    }

    public String getReVipName() {
        return reVipName;
    }

    public void setReVipName(String reVipName) {
        this.reVipName = reVipName;
    }

    public String getReVipSex() {
        return reVipSex;
    }

    public void setReVipSex(String reVipSex) {
        this.reVipSex = reVipSex;
    }

    public Integer getOpenIsSync() {
        return openIsSync;
    }

    public void setOpenIsSync(Integer openIsSync) {
        this.openIsSync = openIsSync;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(Integer depositStatus) {
        this.depositStatus = depositStatus;
    }

    public String getDepositDesc() {
        return depositDesc;
    }

    public void setDepositDesc(String depositDesc) {
        this.depositDesc = depositDesc;
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

    public String getUnorderReasonId() {
        return unorderReasonId;
    }

    public void setUnorderReasonId(String unorderReasonId) {
        this.unorderReasonId = unorderReasonId;
    }

    public Integer getAndroidUserId() {
        return androidUserId;
    }

    public void setAndroidUserId(Integer androidUserId) {
        this.androidUserId = androidUserId;
    }

    public String getAndroidUserName() {
        return androidUserName;
    }

    public void setAndroidUserName(String androidUserName) {
        this.androidUserName = androidUserName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
