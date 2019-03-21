package com.zhidianfan.pig.yd.moduler.sms.dto.business;

import com.zhidianfan.pig.yd.moduler.sms.dto.BaseDTO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 酒店对象
 */
public class BusinessDTO extends BaseDTO {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 酒店名称
     */
    private String businessName;

    /**
     * 品牌id
     */
    private Integer brandId;

    /**
     * 酒店电话
     */
    private String businessPhone;

    /**
     * 酒店地址
     */
    private String businessAddress;

    /**
     * 酒店星级
     */
    private String businessStar;

    /**
     * 酒店管理员
     * column:bussiness_manager
     */
    private String businessManager;

    /**
     * 城市id
     */
    private Integer cityId;

    /**
     * 省份id
     */
    private Integer provinceId;

    /**
     * 区域id
     */
    private Integer areaId;

    /**
     * 酒店状态0下线,1上线
     */
    private String status;

    /**
     * 商户后台登入账号
     */
    private String loginUser;

    /**
     * 商户后台登入密码
     */
    private String loginPassword;

    /**
     * 短信风格 1为带客户姓名，2为不带客户姓名
     */
    private Integer smsStyle;

    /**
     *
     */
    private String localUrl;

    /**
     *
     */
    private String serialPort;

    /**
     *
     */
    private String meetingStatus;

    /**
     * 0 和1
     */
    private Integer devicePhoneType;

    /**
     * 1显示  0隐藏    预定台手机号显示
     */
    private Integer isShowDevicePhone;

    /**
     * 0显示姓+性别   1显示全名   台位客户称呼显示类型
     */
    private Integer isShowTableVipname;

    /**
     * 1显示  0隐藏  公共客户手机端号码显示
     */
    private Integer isShowPublicVip;

    /**
     * 宴会订单是否需要审核流程   0不需要  1需要
     */
    private Integer isMeetingCheck;

    /**
     * 换桌审核开关
     */
    private Integer changeTableCheck;

    /**
     * 取消订单审核开关
     */
    private Integer cancelCheck;

    /**
     * 修改菜肴标准审核开关
     */
    private Integer changeDishStandardCheck;

    /**
     * 修改预约桌数审核开关
     */
    private Integer changeResvTableNumCheck;

    /**
     * 0-禁用订单查询 1-启用订单查询
     */
    private Integer isOrderQuery;

    /**
     * 是否跨班次 0-否 1-是 is_kbc is_cross_meal_type
     */
    private Integer isKbc;

    /**
     *
     */
    private String softTitle;

    /**
     * 是否分餐别 0-否 1-是
     */
    private Integer isFcb;

    /**
     *
     */
    private Date openDate;

    /**
     *
     */
    private BigDecimal price;

    /**
     *
     */
    private BigDecimal yearPrice;

    /**
     *
     */
    private BigDecimal yearPriceOne;

    /**
     *
     */
    private BigDecimal yearPriceTwo;

    /**
     *
     */
    private BigDecimal yearPriceThree;

    /**
     *
     */
    private Date dueDate;

    /**
     *
     */
    private Integer delayDays;

    /**
     *
     */
    private String tag;

    /**
     * 商户ID（口碑专用）
     */
    private String merchantPid;

    /**
     * 门店ID（口碑专用）
     */
    private String shopId;

    /**
     * 导出订单表是否显示客户电话 0-否 1-是
     */
    private Integer isExcelVipPhone;

    /**
     * 导出订单是否验证
     */
    private Integer isExcelCheck;

    /**
     * 0不允许1允许 入座后是否允许修改
     */
    private Integer isSeatUpdate;

    /**
     * 纪念日提醒提前时间
     */
    private Integer notifyBeforeDay;

    /**
     * 是否开启微信时段修改 0-否 1-是
     */
    private Integer isWeixin;

    /**
     * 是否发送宴会确认短信
     */
    private Integer meetingConfirmMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getBusinessStar() {
        return businessStar;
    }

    public void setBusinessStar(String businessStar) {
        this.businessStar = businessStar;
    }

    public String getBusinessManager() {
        return businessManager;
    }

    public void setBusinessManager(String businessManager) {
        this.businessManager = businessManager;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public Integer getSmsStyle() {
        return smsStyle;
    }

    public void setSmsStyle(Integer smsStyle) {
        this.smsStyle = smsStyle;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(String serialPort) {
        this.serialPort = serialPort;
    }

    public String getMeetingStatus() {
        return meetingStatus;
    }

    public void setMeetingStatus(String meetingStatus) {
        this.meetingStatus = meetingStatus;
    }

    public Integer getDevicePhoneType() {
        return devicePhoneType;
    }

    public void setDevicePhoneType(Integer devicePhoneType) {
        this.devicePhoneType = devicePhoneType;
    }

    public Integer getIsShowDevicePhone() {
        return isShowDevicePhone;
    }

    public void setIsShowDevicePhone(Integer isShowDevicePhone) {
        this.isShowDevicePhone = isShowDevicePhone;
    }

    public Integer getIsShowTableVipname() {
        return isShowTableVipname;
    }

    public void setIsShowTableVipname(Integer isShowTableVipname) {
        this.isShowTableVipname = isShowTableVipname;
    }

    public Integer getIsShowPublicVip() {
        return isShowPublicVip;
    }

    public void setIsShowPublicVip(Integer isShowPublicVip) {
        this.isShowPublicVip = isShowPublicVip;
    }

    public Integer getIsMeetingCheck() {
        return isMeetingCheck;
    }

    public void setIsMeetingCheck(Integer isMeetingCheck) {
        this.isMeetingCheck = isMeetingCheck;
    }

    public Integer getChangeTableCheck() {
        return changeTableCheck;
    }

    public void setChangeTableCheck(Integer changeTableCheck) {
        this.changeTableCheck = changeTableCheck;
    }

    public Integer getCancelCheck() {
        return cancelCheck;
    }

    public void setCancelCheck(Integer cancelCheck) {
        this.cancelCheck = cancelCheck;
    }

    public Integer getChangeDishStandardCheck() {
        return changeDishStandardCheck;
    }

    public void setChangeDishStandardCheck(Integer changeDishStandardCheck) {
        this.changeDishStandardCheck = changeDishStandardCheck;
    }

    public Integer getChangeResvTableNumCheck() {
        return changeResvTableNumCheck;
    }

    public void setChangeResvTableNumCheck(Integer changeResvTableNumCheck) {
        this.changeResvTableNumCheck = changeResvTableNumCheck;
    }

    public Integer getIsOrderQuery() {
        return isOrderQuery;
    }

    public void setIsOrderQuery(Integer isOrderQuery) {
        this.isOrderQuery = isOrderQuery;
    }

    public Integer getIsKbc() {
        return isKbc;
    }

    public void setIsKbc(Integer isKbc) {
        this.isKbc = isKbc;
    }

    public String getSoftTitle() {
        return softTitle;
    }

    public void setSoftTitle(String softTitle) {
        this.softTitle = softTitle;
    }

    public Integer getIsFcb() {
        return isFcb;
    }

    public void setIsFcb(Integer isFcb) {
        this.isFcb = isFcb;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getYearPrice() {
        return yearPrice;
    }

    public void setYearPrice(BigDecimal yearPrice) {
        this.yearPrice = yearPrice;
    }

    public BigDecimal getYearPriceOne() {
        return yearPriceOne;
    }

    public void setYearPriceOne(BigDecimal yearPriceOne) {
        this.yearPriceOne = yearPriceOne;
    }

    public BigDecimal getYearPriceTwo() {
        return yearPriceTwo;
    }

    public void setYearPriceTwo(BigDecimal yearPriceTwo) {
        this.yearPriceTwo = yearPriceTwo;
    }

    public BigDecimal getYearPriceThree() {
        return yearPriceThree;
    }

    public void setYearPriceThree(BigDecimal yearPriceThree) {
        this.yearPriceThree = yearPriceThree;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(Integer delayDays) {
        this.delayDays = delayDays;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMerchantPid() {
        return merchantPid;
    }

    public void setMerchantPid(String merchantPid) {
        this.merchantPid = merchantPid;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Integer getIsExcelVipPhone() {
        return isExcelVipPhone;
    }

    public void setIsExcelVipPhone(Integer isExcelVipPhone) {
        this.isExcelVipPhone = isExcelVipPhone;
    }

    public Integer getIsExcelCheck() {
        return isExcelCheck;
    }

    public void setIsExcelCheck(Integer isExcelCheck) {
        this.isExcelCheck = isExcelCheck;
    }

    public Integer getIsSeatUpdate() {
        return isSeatUpdate;
    }

    public void setIsSeatUpdate(Integer isSeatUpdate) {
        this.isSeatUpdate = isSeatUpdate;
    }

    public Integer getNotifyBeforeDay() {
        return notifyBeforeDay;
    }

    public void setNotifyBeforeDay(Integer notifyBeforeDay) {
        this.notifyBeforeDay = notifyBeforeDay;
    }

    public Integer getIsWeixin() {
        return isWeixin;
    }

    public void setIsWeixin(Integer isWeixin) {
        this.isWeixin = isWeixin;
    }

    public Integer getMeetingConfirmMessage() {
        return meetingConfirmMessage;
    }

    public void setMeetingConfirmMessage(Integer meetingConfirmMessage) {
        this.meetingConfirmMessage = meetingConfirmMessage;
    }
}
