package com.zhidianfan.pig.yd.moduler.sms.query.order;

import com.zhidianfan.pig.yd.moduler.sms.query.BaseQO;

import java.util.Date;

/**
 *
 * Created by Administrator on 2017/12/19.
 */
public class ResvOrderQO extends BaseQO {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 订单号
     */
    private String resvOrder;

    /**
     * 酒店id
     */
    private Long businessId;

    /**
     * 酒店名
     */
    private String businessName;

    /**
     * 营销经理id
     */
    private Long appUserId;

    /**
     * 营销经理手机
     */
    private String appUserPhone;

    /**
     * 客户id
     */
    private Long vipId;

    /**
     * 客户手机号
     */
    private String vipPhone;

    /**
     * 客户姓名
     */
    private String vipName;

    /**
     * 客户性别
     */
    private String vipSex;

    /**
     * 客户公司
     */
    private String vipCompany;

    /**
     * 桌位区域id
     */
    private Long tableAreaId;

    /**
     * 桌位区域名
     */
    private String tableAreaName;

    /**
     * 桌位id
     */
    private Long tableId;

    /**
     * 桌位名称
     */
    private String tableName;

    /**
     * 最大人数
     */
    private Integer maxPeopleNum;

    /**
     * 预定人数
     */
    private Integer resvNum;

    /**
     * 实际人数
     */
    private Integer actualNum;

    /**
     * 用餐时间
     */
    private Date resvDate;

    /**
     * 餐别id
     */
    private Long mealTypeId;

    /**
     * 餐别名称
     */
    private String mealTypeName;

    /**
     * 餐别a id
     */
    private Long mealTypeIdA;

    /**
     * 餐别a 名称
     */
    private String mealTypeNameA;

    /**
     * 餐别b id
     */
    private Long mealTypeIdB;

    /**
     * 餐别b 名称
     */
    private String mealTypeNameB;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 0是未确认一定会来，1是确认要来就餐
     */
    private String confirm;

    /**
     * 营销经理姓名
     */
    private String appUserName;

    /**
     * 操作员id
     */
    private Long deviceUserId;

    /**
     * 操作员姓名
     */
    private String deviceUserName;

    /**
     * 操作员手机
     */
    private String deviceUserPhone;

    /**
     * 是否配菜
     */
    private String peicai;

    /**
     * 配菜图片
     */
    private String picUrl;

    /**
     * 支付总金额
     */
    private String payAmount;

    /**
     * 退订原因
     */
    private String unorderReason;

    /**
     * 押金
     */
    private String deposit;

    /**
     * 锁台
     */
    private Integer locked;

    /**
     * 押金单号
     */
    private String receiptNo;

    /**
     * 押金总额
     */
    private String depositAmount;

    /**
     * 押金类型
     */
    private Integer payType;

    /**
     * 订单类型
     */
    private Integer resvOrderType;

    /**
     * 锁台原因 1.是因为宴会锁台  2.换衣间锁台
     */
    private Integer lockedType;

    /**
     * 因宴会锁台的关联宴会订单号
     */
    private String resvMeetiingOrderNo;

    /**
     * 外部来源名称
     */
    private String externalSourceName;

    /**
     * 外部来源id
     */
    private Long externalSourceId;

    /**
     * 是否第三方订单
     */
    private Integer isTirdParty;

    /**
     * 是否换桌
     */
    private Integer isChangeTable;

    /**
     * 是否跨餐别 0-否 1-是
     */
    private Integer isKbc;

    /**
     * 标签
     */
    private String tag;

    /**
     * 配菜金额
     */
    private Integer peicaiAmt;

    /**
     * 配菜类型 0-人均 1-桌均
     */
    private Integer peicaiType;

    /**
     * 到店时间
     */
    private String destTime;

    /**
     * 订单属性
     */
    private Long propertyId;

    /**
     * 订单属性名
     */
    private String propertyName;

    /**
     * 0普通预订，1排位预订
     */
    private Integer isRank;

    /**
     * 有无发送短信
     */
    private Integer isSendMsg;

    /**
     * 备选客户手机
     */
    private String reVipPhone;

    /**
     * 备选客户姓名
     */
    private String reVipName;

    /**
     * 备选客户性别
     */
    private String reVipSex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public String getAppUserPhone() {
        return appUserPhone;
    }

    public void setAppUserPhone(String appUserPhone) {
        this.appUserPhone = appUserPhone;
    }

    public Long getVipId() {
        return vipId;
    }

    public void setVipId(Long vipId) {
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

    public Long getTableAreaId() {
        return tableAreaId;
    }

    public void setTableAreaId(Long tableAreaId) {
        this.tableAreaId = tableAreaId;
    }

    public String getTableAreaName() {
        return tableAreaName;
    }

    public void setTableAreaName(String tableAreaName) {
        this.tableAreaName = tableAreaName;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getMaxPeopleNum() {
        return maxPeopleNum;
    }

    public void setMaxPeopleNum(Integer maxPeopleNum) {
        this.maxPeopleNum = maxPeopleNum;
    }

    public Integer getResvNum() {
        return resvNum;
    }

    public void setResvNum(Integer resvNum) {
        this.resvNum = resvNum;
    }

    public Integer getActualNum() {
        return actualNum;
    }

    public void setActualNum(Integer actualNum) {
        this.actualNum = actualNum;
    }

    public Date getResvDate() {
        return resvDate;
    }

    public void setResvDate(Date resvDate) {
        this.resvDate = resvDate;
    }

    public Long getMealTypeId() {
        return mealTypeId;
    }

    public void setMealTypeId(Long mealTypeId) {
        this.mealTypeId = mealTypeId;
    }

    public String getMealTypeName() {
        return mealTypeName;
    }

    public void setMealTypeName(String mealTypeName) {
        this.mealTypeName = mealTypeName;
    }

    public Long getMealTypeIdA() {
        return mealTypeIdA;
    }

    public void setMealTypeIdA(Long mealTypeIdA) {
        this.mealTypeIdA = mealTypeIdA;
    }

    public String getMealTypeNameA() {
        return mealTypeNameA;
    }

    public void setMealTypeNameA(String mealTypeNameA) {
        this.mealTypeNameA = mealTypeNameA;
    }

    public Long getMealTypeIdB() {
        return mealTypeIdB;
    }

    public void setMealTypeIdB(Long mealTypeIdB) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public Long getDeviceUserId() {
        return deviceUserId;
    }

    public void setDeviceUserId(Long deviceUserId) {
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

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
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

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
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

    public String getResvMeetiingOrderNo() {
        return resvMeetiingOrderNo;
    }

    public void setResvMeetiingOrderNo(String resvMeetiingOrderNo) {
        this.resvMeetiingOrderNo = resvMeetiingOrderNo;
    }

    public String getExternalSourceName() {
        return externalSourceName;
    }

    public void setExternalSourceName(String externalSourceName) {
        this.externalSourceName = externalSourceName;
    }

    public Long getExternalSourceId() {
        return externalSourceId;
    }

    public void setExternalSourceId(Long externalSourceId) {
        this.externalSourceId = externalSourceId;
    }

    public Integer getIsTirdParty() {
        return isTirdParty;
    }

    public void setIsTirdParty(Integer isTirdParty) {
        this.isTirdParty = isTirdParty;
    }

    public Integer getIsChangeTable() {
        return isChangeTable;
    }

    public void setIsChangeTable(Integer isChangeTable) {
        this.isChangeTable = isChangeTable;
    }

    public Integer getIsKbc() {
        return isKbc;
    }

    public void setIsKbc(Integer isKbc) {
        this.isKbc = isKbc;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getPeicaiAmt() {
        return peicaiAmt;
    }

    public void setPeicaiAmt(Integer peicaiAmt) {
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

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Integer getIsRank() {
        return isRank;
    }

    public void setIsRank(Integer isRank) {
        this.isRank = isRank;
    }

    public Integer getIsSendMsg() {
        return isSendMsg;
    }

    public void setIsSendMsg(Integer isSendMsg) {
        this.isSendMsg = isSendMsg;
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
}






























