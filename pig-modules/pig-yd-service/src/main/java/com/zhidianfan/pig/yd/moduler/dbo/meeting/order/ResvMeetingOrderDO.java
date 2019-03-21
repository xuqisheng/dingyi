package com.zhidianfan.pig.yd.moduler.dbo.meeting.order;

import com.zhidianfan.pig.yd.moduler.dbo.BaseDO;

import java.util.Date;

/**
 *
 * Created by Administrator on 2018/2/24.
 */
public class ResvMeetingOrderDO extends BaseDO {

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
     * 营销经理名
     */
    private String appUserName;

    /**
     * 营销经理手机号
     */
    private String appUserPhone;

    /**
     * 预订员id
     */
    private Long deviceUserId;

    /**
     * 预定员名
     */
    private String deviceUserName;

    /**
     * 预订员手机号
     */
    private String deviceUserPhone;

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
     * 区域id
     */
    private Long tableAreaId;

    /**
     * 区域名
     */
    private String tableAreaName;

    /**
     * 桌位id
     */
    private Long tableId;

    /**
     * 桌位名
     */
    private String tableName;

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
     * 订单备注
     */
    private String remark;

    /**
     * 宴会配置
     */
    private String notations;

    /**
     * 宴会状态
     */
    private Integer status;

    /**
     * 订单类别
     */
    private Integer resvMeetingOrderType;

    /**
     * 押金金额
     */
    private String deposit;

    /**
     * 支付类型
     */
    private Integer payType;

    /**
     * 预定桌数
     */
    private Integer resvTableNum;

    /**
     * 备用桌数
     */
    private Integer backupTableNum;

    /**
     * 实际桌数
     */
    private Integer actualTableNum;

    /**
     * 客户来源
     */
    private String vipSource;

    /**
     * 接单员电话
     */
    private String workerPhone;

    /**
     * 接待员姓名
     */
    private String workerName;

    /**
     * 新郎姓名
     */
    private String groomName;

    /**
     * 新郎手机号
     */
    private String groomPhone;

    /**
     * 新娘姓名
     */
    private String brideName;

    /**
     * 新娘手机号
     */
    private String bridePhone;

    /**
     * 菜肴标准
     */
    private String dishStandard;

    /**
     * 菜肴明细
     */
    private String dishDetail;

    /**
     * 菜肴明细文件地址
     */
    private String dishDetailUrl;

    /**
     * 菜肴明细图片地址
     */
    private String dishDetailImgUrl;

    /**
     * 收据单号
     */
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
    private String payAmount;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 实际使用桌位code 集合
     */
    private String tableCodes;

    /**
     * 实际使用桌位id 集合
     */
    private String tableIds;

    /**
     * 换衣间id
     */
    private Long lockerRoom;

    /**
     * 实收金额
     */
    private Integer actualPayAmount;

    /**
     * 优惠金额
     */
    private Integer discountAmount;

    /**
     * 抹零金额
     */
    private Integer smallAmount;

    /**
     * 审核状态 0-未审核  1-提交审核  2-审核通过
     */
    private Integer checkStatus;

    /**
     * 合同图片
     */
    private String contractUrl;

    /**
     * 确认订单方式   0未确认 1 押金  2 合同 3-担保人
     */
    private Integer confirmOrderType;

    /**
     * 退订原因
     */
    private String unorderReason;

    /**
     * 跨餐次时间 0-不跨 1-半天 2-全天
     */
    private Integer kccTime;

    /**
     * 担保人ID（店内App 用户）
     */
    private Long dbrId;

    /**
     * 担保人姓名
     */
    private String dbrName;

    /**
     * 宴会承办方 0-自带 1-合作
     */
    private Integer cbf;

    /**
     *
     */
    private Integer tableType;

    /**
     *
     */
    private String meetingTitle;

    /**
     *
     */
    private Date meetingStart;

    /**
     *
     */
    private Date meetingEnd;

    /**
     *
     */
    private String deposit1;

    /**
     *
     */
    private Integer payType1;

    /**
     *
     */
    private String deposit2;

    /**
     *
     */
    private Integer payType2;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
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

    public Long getLockerRoom() {
        return lockerRoom;
    }

    public void setLockerRoom(Long lockerRoom) {
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

    public Long getDbrId() {
        return dbrId;
    }

    public void setDbrId(Long dbrId) {
        this.dbrId = dbrId;
    }

    public String getDbrName() {
        return dbrName;
    }

    public void setDbrName(String dbrName) {
        this.dbrName = dbrName;
    }

    public Integer getCbf() {
        return cbf;
    }

    public void setCbf(Integer cbf) {
        this.cbf = cbf;
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

    public Integer getPayType1() {
        return payType1;
    }

    public void setPayType1(Integer payType1) {
        this.payType1 = payType1;
    }

    public String getDeposit2() {
        return deposit2;
    }

    public void setDeposit2(String deposit2) {
        this.deposit2 = deposit2;
    }

    public Integer getPayType2() {
        return payType2;
    }

    public void setPayType2(Integer payType2) {
        this.payType2 = payType2;
    }
}
































