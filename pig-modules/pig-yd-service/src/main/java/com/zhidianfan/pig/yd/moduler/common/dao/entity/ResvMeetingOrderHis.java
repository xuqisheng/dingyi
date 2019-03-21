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
 * @since 2018-09-03
 */
@TableName("resv_meeting_order_his")
public class ResvMeetingOrderHis extends Model<ResvMeetingOrderHis> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 批次
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
    /**
     * 营销人员
     */
    @TableField("app_user_id")
    private Integer appUserId;
    @TableField("app_user_name")
    private String appUserName;
    @TableField("app_user_phone")
    private String appUserPhone;
    @TableField("device_user_id")
    private Integer deviceUserId;
    @TableField("device_user_name")
    private String deviceUserName;
    @TableField("device_user_phone")
    private String deviceUserPhone;
    /**
     * 客户id
     */
    @TableField("vip_id")
    private Integer vipId;
    @TableField("vip_phone")
    private String vipPhone;
    @TableField("vip_name")
    private String vipName;
    @TableField("vip_sex")
    private String vipSex;
    @TableField("vip_company")
    private String vipCompany;
    @TableField("table_area_id")
    private Integer tableAreaId;
    @TableField("table_area_name")
    private String tableAreaName;
    @TableField("table_id")
    private Integer tableId;
    @TableField("table_name")
    private String tableName;
    /**
     * 预定日期
     */
    @TableField("resv_date")
    private Date resvDate;
    /**
     * 餐别 
     */
    @TableField("meal_type_id")
    private Integer mealTypeId;
    @TableField("meal_type_name")
    private String mealTypeName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 宴会配置
     */
    private String notations;
    /**
     * 1=待确认  2=已确认  3=已完成  4=已取消 5=锁台 6=挂起
     */
    private String status;
    /**
     * 宴会类型（1.婚宴）
     */
    @TableField("resv_meeting_order_type")
    private Integer resvMeetingOrderType;
    /**
     * 押金金额
     */
    private String deposit;
    /**
     * 1=现金  2=刷卡 3=第三方网络支付
     */
    @TableField("pay_type")
    private String payType;
    /**
     * 预订桌数
     */
    @TableField("resv_table_num")
    private Integer resvTableNum;
    /**
     * 备用桌数
     */
    @TableField("backup_table_num")
    private Integer backupTableNum;
    /**
     * 实际使用桌数
     */
    @TableField("actual_table_num")
    private Integer actualTableNum;
    /**
     * 客户来源
     */
    @TableField("vip_source")
    private String vipSource;
    /**
     * 接待员电话
     */
    @TableField("worker_phone")
    private String workerPhone;
    /**
     * 接待员姓名
     */
    @TableField("worker_name")
    private String workerName;
    /**
     * 客户1性别
     */
    @TableField("vip_sex1")
    private String vipSex1;
    /**
     * 新郎姓名
     */
    @TableField("groom_name")
    private String groomName;
    /**
     * 新郎电话
     */
    @TableField("groom_phone")
    private String groomPhone;
    /**
     * 客户2性别
     */
    @TableField("vip_sex2")
    private String vipSex2;
    /**
     * 新娘姓名
     */
    @TableField("bride_name")
    private String brideName;
    /**
     * 新娘电话
     */
    @TableField("bride_phone")
    private String bridePhone;
    /**
     * 菜肴标准
     */
    @TableField("dish_standard")
    private String dishStandard;
    /**
     * 菜肴标准(最初的)
     */
    @TableField("dish_standard_original")
    private String dishStandardOriginal;
    /**
     * 菜肴明细   url 实际上是word文件
     */
    @TableField("dish_detail")
    private String dishDetail;
    /**
     * 七牛中文件存放路径
     */
    @TableField("dish_detail_url")
    private String dishDetailUrl;
    /**
     * 菜肴明细图片
     */
    @TableField("dish_detail_img_url")
    private String dishDetailImgUrl;
    /**
     * 收据单号
     */
    @TableField("receipt_no")
    private String receiptNo;
    /**
     * 家庭住址
     */
    private String address;
    /**
     * 工作单位
     */
    private String company;
    /**
     * 补交金额
     */
    @TableField("pay_amount")
    private String payAmount;
    /**
     * 婚庆入场时间
     */
    @TableField("attend_time")
    private String attendTime;
    /**
     * cre
     */
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    /**
     * 合同编号
     */
    @TableField("contract_no")
    private String contractNo;
    /**
     * 实际使用桌位code集合
     */
    @TableField("table_codes")
    private String tableCodes;
    /**
     * 实际使用桌位id集合
     */
    @TableField("table_ids")
    private String tableIds;
    /**
     * 换衣间id
     */
    @TableField("locker_room")
    private Integer lockerRoom;
    /**
     * 实收金额
     */
    @TableField("actual_pay_amount")
    private Integer actualPayAmount;
    /**
     * 优惠金额
     */
    @TableField("discount_amount")
    private Integer discountAmount;
    /**
     * 抹零金额
     */
    @TableField("small_amount")
    private Integer smallAmount;
    /**
     * 0是未审核  1是提交审核  2是审核通过
     */
    @TableField("check_status")
    private Integer checkStatus;
    /**
     * 合同图片
     */
    @TableField("contract_url")
    private String contractUrl;
    /**
     * 确认订单方式   0未确认 1 押金  2 合同 3-担保人
     */
    @TableField("confirm_order_type")
    private Integer confirmOrderType;
    /**
     * 退订原因
     */
    @TableField("unorder_reason")
    private String unorderReason;
    /**
     * 跨餐次时间 0-不跨 1-半天 2-全天
     */
    @TableField("kcc_time")
    private Integer kccTime;
    /**
     * 担保人ID（店内App用户）
     */
    @TableField("dbr_id")
    private Integer dbrId;
    /**
     * 宴会承办方 0-自带 1-合作 2-自营
     */
    private Integer cbf;
    /**
     * 婚庆公司id
     */
    @TableField("wedding_company_id")
    private Integer weddingCompanyId;
    /**
     * 婚庆公司名称
     */
    @TableField("wedding_company_name")
    private String weddingCompanyName;
    @TableField("table_type")
    private Integer tableType;
    @TableField("meeting_title")
    private String meetingTitle;
    @TableField("meeting_start")
    private Date meetingStart;
    @TableField("meeting_end")
    private Date meetingEnd;
    private String deposit1;
    @TableField("pay_type1")
    private String payType1;
    private String deposit2;
    @TableField("pay_type2")
    private String payType2;
    /**
     * 是否配菜，默认不配菜0
     */
    private String peicai;
    /**
     * 订金状态
     */
    @TableField("deposit_status")
    private Integer depositStatus;
    /**
     * 订金降序
     */
    @TableField("deposit_desc")
    private String depositDesc;
    @TableField("update_status")
    private Integer updateStatus;
    /**
     * 订单详情打印次数
     */
    @TableField("print_time")
    private Integer printTime;
    /**
     * 确认单注意事项
     */
    @TableField("service_items")
    private String serviceItems;
    /**
     * 确认单特殊说明
     */
    @TableField("confirm_remark")
    private String confirmRemark;


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

    public String getAppUserName() {
        return appUserName;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }

    public String getAppUserPhone() {
        return appUserPhone;
    }

    public void setAppUserPhone(String appUserPhone) {
        this.appUserPhone = appUserPhone;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNotations() {
        return notations;
    }

    public void setNotations(String notations) {
        this.notations = notations;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getResvMeetingOrderType() {
        return resvMeetingOrderType;
    }

    public void setResvMeetingOrderType(Integer resvMeetingOrderType) {
        this.resvMeetingOrderType = resvMeetingOrderType;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getResvTableNum() {
        return resvTableNum;
    }

    public void setResvTableNum(Integer resvTableNum) {
        this.resvTableNum = resvTableNum;
    }

    public Integer getBackupTableNum() {
        return backupTableNum;
    }

    public void setBackupTableNum(Integer backupTableNum) {
        this.backupTableNum = backupTableNum;
    }

    public Integer getActualTableNum() {
        return actualTableNum;
    }

    public void setActualTableNum(Integer actualTableNum) {
        this.actualTableNum = actualTableNum;
    }

    public String getVipSource() {
        return vipSource;
    }

    public void setVipSource(String vipSource) {
        this.vipSource = vipSource;
    }

    public String getWorkerPhone() {
        return workerPhone;
    }

    public void setWorkerPhone(String workerPhone) {
        this.workerPhone = workerPhone;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getVipSex1() {
        return vipSex1;
    }

    public void setVipSex1(String vipSex1) {
        this.vipSex1 = vipSex1;
    }

    public String getGroomName() {
        return groomName;
    }

    public void setGroomName(String groomName) {
        this.groomName = groomName;
    }

    public String getGroomPhone() {
        return groomPhone;
    }

    public void setGroomPhone(String groomPhone) {
        this.groomPhone = groomPhone;
    }

    public String getVipSex2() {
        return vipSex2;
    }

    public void setVipSex2(String vipSex2) {
        this.vipSex2 = vipSex2;
    }

    public String getBrideName() {
        return brideName;
    }

    public void setBrideName(String brideName) {
        this.brideName = brideName;
    }

    public String getBridePhone() {
        return bridePhone;
    }

    public void setBridePhone(String bridePhone) {
        this.bridePhone = bridePhone;
    }

    public String getDishStandard() {
        return dishStandard;
    }

    public void setDishStandard(String dishStandard) {
        this.dishStandard = dishStandard;
    }

    public String getDishStandardOriginal() {
        return dishStandardOriginal;
    }

    public void setDishStandardOriginal(String dishStandardOriginal) {
        this.dishStandardOriginal = dishStandardOriginal;
    }

    public String getDishDetail() {
        return dishDetail;
    }

    public void setDishDetail(String dishDetail) {
        this.dishDetail = dishDetail;
    }

    public String getDishDetailUrl() {
        return dishDetailUrl;
    }

    public void setDishDetailUrl(String dishDetailUrl) {
        this.dishDetailUrl = dishDetailUrl;
    }

    public String getDishDetailImgUrl() {
        return dishDetailImgUrl;
    }

    public void setDishDetailImgUrl(String dishDetailImgUrl) {
        this.dishDetailImgUrl = dishDetailImgUrl;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getAttendTime() {
        return attendTime;
    }

    public void setAttendTime(String attendTime) {
        this.attendTime = attendTime;
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

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getTableCodes() {
        return tableCodes;
    }

    public void setTableCodes(String tableCodes) {
        this.tableCodes = tableCodes;
    }

    public String getTableIds() {
        return tableIds;
    }

    public void setTableIds(String tableIds) {
        this.tableIds = tableIds;
    }

    public Integer getLockerRoom() {
        return lockerRoom;
    }

    public void setLockerRoom(Integer lockerRoom) {
        this.lockerRoom = lockerRoom;
    }

    public Integer getActualPayAmount() {
        return actualPayAmount;
    }

    public void setActualPayAmount(Integer actualPayAmount) {
        this.actualPayAmount = actualPayAmount;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getSmallAmount() {
        return smallAmount;
    }

    public void setSmallAmount(Integer smallAmount) {
        this.smallAmount = smallAmount;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }

    public Integer getConfirmOrderType() {
        return confirmOrderType;
    }

    public void setConfirmOrderType(Integer confirmOrderType) {
        this.confirmOrderType = confirmOrderType;
    }

    public String getUnorderReason() {
        return unorderReason;
    }

    public void setUnorderReason(String unorderReason) {
        this.unorderReason = unorderReason;
    }

    public Integer getKccTime() {
        return kccTime;
    }

    public void setKccTime(Integer kccTime) {
        this.kccTime = kccTime;
    }

    public Integer getDbrId() {
        return dbrId;
    }

    public void setDbrId(Integer dbrId) {
        this.dbrId = dbrId;
    }

    public Integer getCbf() {
        return cbf;
    }

    public void setCbf(Integer cbf) {
        this.cbf = cbf;
    }

    public Integer getWeddingCompanyId() {
        return weddingCompanyId;
    }

    public void setWeddingCompanyId(Integer weddingCompanyId) {
        this.weddingCompanyId = weddingCompanyId;
    }

    public String getWeddingCompanyName() {
        return weddingCompanyName;
    }

    public void setWeddingCompanyName(String weddingCompanyName) {
        this.weddingCompanyName = weddingCompanyName;
    }

    public Integer getTableType() {
        return tableType;
    }

    public void setTableType(Integer tableType) {
        this.tableType = tableType;
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public Date getMeetingStart() {
        return meetingStart;
    }

    public void setMeetingStart(Date meetingStart) {
        this.meetingStart = meetingStart;
    }

    public Date getMeetingEnd() {
        return meetingEnd;
    }

    public void setMeetingEnd(Date meetingEnd) {
        this.meetingEnd = meetingEnd;
    }

    public String getDeposit1() {
        return deposit1;
    }

    public void setDeposit1(String deposit1) {
        this.deposit1 = deposit1;
    }

    public String getPayType1() {
        return payType1;
    }

    public void setPayType1(String payType1) {
        this.payType1 = payType1;
    }

    public String getDeposit2() {
        return deposit2;
    }

    public void setDeposit2(String deposit2) {
        this.deposit2 = deposit2;
    }

    public String getPayType2() {
        return payType2;
    }

    public void setPayType2(String payType2) {
        this.payType2 = payType2;
    }

    public String getPeicai() {
        return peicai;
    }

    public void setPeicai(String peicai) {
        this.peicai = peicai;
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

    public Integer getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }

    public Integer getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Integer printTime) {
        this.printTime = printTime;
    }

    public String getServiceItems() {
        return serviceItems;
    }

    public void setServiceItems(String serviceItems) {
        this.serviceItems = serviceItems;
    }

    public String getConfirmRemark() {
        return confirmRemark;
    }

    public void setConfirmRemark(String confirmRemark) {
        this.confirmRemark = confirmRemark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ResvMeetingOrderHis{" +
        "id=" + id +
        ", batchNo=" + batchNo +
        ", resvOrder=" + resvOrder +
        ", businessId=" + businessId +
        ", businessName=" + businessName +
        ", appUserId=" + appUserId +
        ", appUserName=" + appUserName +
        ", appUserPhone=" + appUserPhone +
        ", deviceUserId=" + deviceUserId +
        ", deviceUserName=" + deviceUserName +
        ", deviceUserPhone=" + deviceUserPhone +
        ", vipId=" + vipId +
        ", vipPhone=" + vipPhone +
        ", vipName=" + vipName +
        ", vipSex=" + vipSex +
        ", vipCompany=" + vipCompany +
        ", tableAreaId=" + tableAreaId +
        ", tableAreaName=" + tableAreaName +
        ", tableId=" + tableId +
        ", tableName=" + tableName +
        ", resvDate=" + resvDate +
        ", mealTypeId=" + mealTypeId +
        ", mealTypeName=" + mealTypeName +
        ", remark=" + remark +
        ", notations=" + notations +
        ", status=" + status +
        ", resvMeetingOrderType=" + resvMeetingOrderType +
        ", deposit=" + deposit +
        ", payType=" + payType +
        ", resvTableNum=" + resvTableNum +
        ", backupTableNum=" + backupTableNum +
        ", actualTableNum=" + actualTableNum +
        ", vipSource=" + vipSource +
        ", workerPhone=" + workerPhone +
        ", workerName=" + workerName +
        ", vipSex1=" + vipSex1 +
        ", groomName=" + groomName +
        ", groomPhone=" + groomPhone +
        ", vipSex2=" + vipSex2 +
        ", brideName=" + brideName +
        ", bridePhone=" + bridePhone +
        ", dishStandard=" + dishStandard +
        ", dishStandardOriginal=" + dishStandardOriginal +
        ", dishDetail=" + dishDetail +
        ", dishDetailUrl=" + dishDetailUrl +
        ", dishDetailImgUrl=" + dishDetailImgUrl +
        ", receiptNo=" + receiptNo +
        ", address=" + address +
        ", company=" + company +
        ", payAmount=" + payAmount +
        ", attendTime=" + attendTime +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", contractNo=" + contractNo +
        ", tableCodes=" + tableCodes +
        ", tableIds=" + tableIds +
        ", lockerRoom=" + lockerRoom +
        ", actualPayAmount=" + actualPayAmount +
        ", discountAmount=" + discountAmount +
        ", smallAmount=" + smallAmount +
        ", checkStatus=" + checkStatus +
        ", contractUrl=" + contractUrl +
        ", confirmOrderType=" + confirmOrderType +
        ", unorderReason=" + unorderReason +
        ", kccTime=" + kccTime +
        ", dbrId=" + dbrId +
        ", cbf=" + cbf +
        ", weddingCompanyId=" + weddingCompanyId +
        ", weddingCompanyName=" + weddingCompanyName +
        ", tableType=" + tableType +
        ", meetingTitle=" + meetingTitle +
        ", meetingStart=" + meetingStart +
        ", meetingEnd=" + meetingEnd +
        ", deposit1=" + deposit1 +
        ", payType1=" + payType1 +
        ", deposit2=" + deposit2 +
        ", payType2=" + payType2 +
        ", peicai=" + peicai +
        ", depositStatus=" + depositStatus +
        ", depositDesc=" + depositDesc +
        ", updateStatus=" + updateStatus +
        ", printTime=" + printTime +
        ", serviceItems=" + serviceItems +
        ", confirmRemark=" + confirmRemark +
        "}";
    }
}
