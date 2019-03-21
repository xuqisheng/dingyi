package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
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
 * @author qqx
 * @since 2018-12-11
 */
@TableName("business")
public class Business extends Model<Business> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_name")
    private String businessName;
    /**
     * 酒店类型0:普通酒店 1:五星酒店
     */
    @TableField("business_type")
    private Integer businessType;
    @TableField("brand_id")
    private Integer brandId;
    @TableField("business_phone")
    private String businessPhone;
    /**
     * 注册手机
     */
    private String phone;
    @TableField("business_address")
    private String businessAddress;
    @TableField("business_star")
    private String businessStar;
    @TableField("bussiness_manager")
    private String bussinessManager;
    @TableField("open_id")
    private String openId;
    @TableField("agent_id")
    private Integer agentId;
    @TableField("agent_name")
    private String agentName;
    /**
     * 城市id
     */
    @TableField("city_id")
    private Integer cityId;
    /**
     * 省份id
     */
    @TableField("province_id")
    private Integer provinceId;
    @TableField("area_id")
    private Integer areaId;
    @TableField("province_name")
    private String provinceName;
    @TableField("city_name")
    private String cityName;
    @TableField("area_name")
    private String areaName;
    /**
     * 0 待审核 1提供 2拒绝 3停用
     */
    private String status;
    @TableField("login_user")
    private String loginUser;
    @TableField("login_password")
    private String loginPassword;
    /**
     * 新增时间
     */
    @TableField("created_at")
    private Date createdAt;
    /**
     * 修改时间
     */
    @TableField("updated_at")
    private Date updatedAt;
    /**
     * 审批时间
     */
    @TableField("review_time")
    private Date reviewTime;
    @TableField("review_user")
    private String reviewUser;
    /**
     * 1pad版本  2电话机版本
     */
    private Integer device;
    /**
     * 短信风格 1为带客户姓名，2为不带客户姓名
     */
    @TableField("sms_style")
    private Integer smsStyle;
    @TableField("local_url")
    private String localUrl;
    /**
     * 营业执照
     */
    @TableField("licence_url")
    private String licenceUrl;
    @TableField("serial_port")
    private String serialPort;
    @TableField("meeting_status")
    private String meetingStatus;
    /**
     * 0 和1
     */
    @TableField("device_phone_type")
    private Integer devicePhoneType;
    /**
     * 1显示  0隐藏    预定台手机号显示
     */
    @TableField("is_show_device_phone")
    private Integer isShowDevicePhone;
    /**
     * 0显示姓+性别   1显示全名   台位客户称呼显示类型
     */
    @TableField("is_show_table_vipname")
    private Integer isShowTableVipname;
    /**
     * 1显示  0隐藏  公共客户手机端号码显示
     */
    @TableField("is_show_public_vip")
    private Integer isShowPublicVip;
    /**
     * 宴会订单是否需要审核流程   0不需要  1需要
     */
    @TableField("is_meeting_check")
    private Integer isMeetingCheck;
    /**
     * 换桌审核开关
     */
    @TableField("change_table_check")
    private Integer changeTableCheck;
    /**
     * 取消订单审核开关
     */
    @TableField("cancel_check")
    private Integer cancelCheck;
    /**
     * 修改菜肴标准审核开关
     */
    @TableField("change_dish_standard_check")
    private Integer changeDishStandardCheck;
    /**
     * 修改预约桌数审核开关
     */
    @TableField("change_resv_table_num_check")
    private Integer changeResvTableNumCheck;
    /**
     * 订单免审核开关 0关 1开
     */
    @TableField("order_exemption_verify_check")
    private Integer orderExemptionVerifyCheck;
    /**
     * 0-禁用订单查询 1-启用订单查询
     */
    @TableField("is_order_query")
    private Integer isOrderQuery;
    /**
     * 是否跨班次 0-否 1-是 is_kbc is_cross_meal_type
     */
    @TableField("is_kbc")
    private Integer isKbc;
    @TableField("soft_title")
    private String softTitle;
    /**
     * 是否分餐别 0-否 1-是
     */
    @TableField("is_fcb")
    private Integer isFcb;
    /**
     * 酒店开户时间
     */
    @TableField("open_date")
    private Date openDate;
    private BigDecimal price;
    @TableField("year_price")
    private BigDecimal yearPrice;
    @TableField("year_price_one")
    private BigDecimal yearPriceOne;
    @TableField("year_price_two")
    private BigDecimal yearPriceTwo;
    @TableField("year_price_three")
    private BigDecimal yearPriceThree;
    @TableField("due_date")
    private Date dueDate;
    @TableField("delay_days")
    private Integer delayDays;
    private String tag;
    @TableField("merchant_pid")
    private String merchantPid;
    @TableField("shop_id")
    private String shopId;
    /**
     * 导出订单表是否显示客户电话 0-否 1-是
     */
    @TableField("is_excel_vip_phone")
    private Integer isExcelVipPhone;
    /**
     * 导出表格是否短信验证, 0-不验证, 1-验证
     */
    @TableField("is_excel_check")
    private Integer isExcelCheck;
    /**
     * 0不允许1允许 入座后是否允许修改
     */
    @TableField("is_seat_update")
    private Integer isSeatUpdate;
    /**
     * 是否允许添加备选客户信息 0-不允许, 1-允许
     */
    @TableField("is_re_vip_message")
    private Integer isReVipMessage;
    /**
     * 预订单是否隐藏号码中间四位 0-否 1-是
     */
    @TableField("is_vip_phone_hidden")
    private Integer isVipPhoneHidden;
    /**
     * 纪念日提醒提前时间
     */
    @TableField("notify_before_day")
    private Integer notifyBeforeDay;
    /**
     * 微信预定开关, 该开关打开门店后台才会显示微信预定时间设置按钮
     */
    @TableField("is_weixin")
    private Integer isWeixin;
    /**
     * 宴会确认短信, 0-不发送, 1-发送
     */
    @TableField("meeting_confirm_message")
    private Integer meetingConfirmMessage;
    /**
     * 宴会取消短信, 0-不发送. 1-发送
     */
    @TableField("meeting_cancel_message")
    private Integer meetingCancelMessage;
    /**
     * 宴会退订短信, 0-不发送, 1-发送
     */
    @TableField("meeting_unorder_message")
    private Integer meetingUnorderMessage;
    /**
     * 宴会吉日开关,0为关闭 1为开启
     */
    @TableField("is_lucky_day")
    private Integer isLuckyDay;
    @TableField("confirm_service_items")
    private String confirmServiceItems;
    /**
     * 换桌标签开关，0为关闭 1为开启
     */
    @TableField("is_change_table")
    private Integer isChangeTable;
    /**
     * 是否可删除订单
     */
    @TableField("order_deleteable")
    private Integer orderDeleteable;
    /**
     * 是否启用宴会接口0:停用1:启用
     */
    @TableField("enable_meeting_api")
    private Integer enableMeetingApi;
    /**
     * 美团token
     */
    @TableField("app_token")
    private String appToken;
    /**
     * 是否开启美团 0 不启用 1 启用
     */
    @TableField("is_meituan")
    private Integer isMeituan;
    /**
     * 是否可取消入座订单 1 可取消 0 不可取消
     */
    @TableField("cancel_seatedorder_able")
    private Integer cancelSeatedorderAble;
    /**
     * 是否开启线上订单 0 不启用  1 启用
     */
    @TableField("is_online_order")
    private Integer isOnlineOrder;
    /**
     * 是否开启一厅多笔确认单 0 不启用 1启用
     */
    @TableField("is_meeting_order_more")
    private Integer isMeetingOrderMore;
    /**
     * 酒店默认就餐人数
     */
    @TableField("def_meals_num")
    private Integer defMealsNum;
    /**
     * 纬度
     */
    private String longitude;
    /**
     * 经度
     */
    private String latitude;
    /**
     * logo图
     */
    @TableField("logo_pic")
    private String logoPic;
    /**
     * 桌位标签
     */
    @TableField("table_tag")
    private String tableTag;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getBussinessManager() {
        return bussinessManager;
    }

    public void setBussinessManager(String bussinessManager) {
        this.bussinessManager = bussinessManager;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
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

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getReviewUser() {
        return reviewUser;
    }

    public void setReviewUser(String reviewUser) {
        this.reviewUser = reviewUser;
    }

    public Integer getDevice() {
        return device;
    }

    public void setDevice(Integer device) {
        this.device = device;
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

    public String getLicenceUrl() {
        return licenceUrl;
    }

    public void setLicenceUrl(String licenceUrl) {
        this.licenceUrl = licenceUrl;
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

    public Integer getOrderExemptionVerifyCheck() {
        return orderExemptionVerifyCheck;
    }

    public void setOrderExemptionVerifyCheck(Integer orderExemptionVerifyCheck) {
        this.orderExemptionVerifyCheck = orderExemptionVerifyCheck;
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

    public Integer getIsReVipMessage() {
        return isReVipMessage;
    }

    public void setIsReVipMessage(Integer isReVipMessage) {
        this.isReVipMessage = isReVipMessage;
    }

    public Integer getIsVipPhoneHidden() {
        return isVipPhoneHidden;
    }

    public void setIsVipPhoneHidden(Integer isVipPhoneHidden) {
        this.isVipPhoneHidden = isVipPhoneHidden;
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

    public Integer getMeetingCancelMessage() {
        return meetingCancelMessage;
    }

    public void setMeetingCancelMessage(Integer meetingCancelMessage) {
        this.meetingCancelMessage = meetingCancelMessage;
    }

    public Integer getMeetingUnorderMessage() {
        return meetingUnorderMessage;
    }

    public void setMeetingUnorderMessage(Integer meetingUnorderMessage) {
        this.meetingUnorderMessage = meetingUnorderMessage;
    }

    public Integer getIsLuckyDay() {
        return isLuckyDay;
    }

    public void setIsLuckyDay(Integer isLuckyDay) {
        this.isLuckyDay = isLuckyDay;
    }

    public String getConfirmServiceItems() {
        return confirmServiceItems;
    }

    public void setConfirmServiceItems(String confirmServiceItems) {
        this.confirmServiceItems = confirmServiceItems;
    }

    public Integer getIsChangeTable() {
        return isChangeTable;
    }

    public void setIsChangeTable(Integer isChangeTable) {
        this.isChangeTable = isChangeTable;
    }

    public Integer getOrderDeleteable() {
        return orderDeleteable;
    }

    public void setOrderDeleteable(Integer orderDeleteable) {
        this.orderDeleteable = orderDeleteable;
    }

    public Integer getEnableMeetingApi() {
        return enableMeetingApi;
    }

    public void setEnableMeetingApi(Integer enableMeetingApi) {
        this.enableMeetingApi = enableMeetingApi;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public Integer getIsMeituan() {
        return isMeituan;
    }

    public void setIsMeituan(Integer isMeituan) {
        this.isMeituan = isMeituan;
    }

    public Integer getCancelSeatedorderAble() {
        return cancelSeatedorderAble;
    }

    public void setCancelSeatedorderAble(Integer cancelSeatedorderAble) {
        this.cancelSeatedorderAble = cancelSeatedorderAble;
    }

    public Integer getIsOnlineOrder() {
        return isOnlineOrder;
    }

    public void setIsOnlineOrder(Integer isOnlineOrder) {
        this.isOnlineOrder = isOnlineOrder;
    }

    public Integer getIsMeetingOrderMore() {
        return isMeetingOrderMore;
    }

    public void setIsMeetingOrderMore(Integer isMeetingOrderMore) {
        this.isMeetingOrderMore = isMeetingOrderMore;
    }

    public Integer getDefMealsNum() {
        return defMealsNum;
    }

    public void setDefMealsNum(Integer defMealsNum) {
        this.defMealsNum = defMealsNum;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLogoPic() {
        return logoPic;
    }

    public void setLogoPic(String logoPic) {
        this.logoPic = logoPic;
    }

    public String getTableTag() {
        return tableTag;
    }

    public void setTableTag(String tableTag) {
        this.tableTag = tableTag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Business{" +
        "id=" + id +
        ", businessName=" + businessName +
        ", businessType=" + businessType +
        ", brandId=" + brandId +
        ", businessPhone=" + businessPhone +
        ", phone=" + phone +
        ", businessAddress=" + businessAddress +
        ", businessStar=" + businessStar +
        ", bussinessManager=" + bussinessManager +
        ", openId=" + openId +
        ", agentId=" + agentId +
        ", agentName=" + agentName +
        ", cityId=" + cityId +
        ", provinceId=" + provinceId +
        ", areaId=" + areaId +
        ", provinceName=" + provinceName +
        ", cityName=" + cityName +
        ", areaName=" + areaName +
        ", status=" + status +
        ", loginUser=" + loginUser +
        ", loginPassword=" + loginPassword +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", reviewTime=" + reviewTime +
        ", reviewUser=" + reviewUser +
        ", device=" + device +
        ", smsStyle=" + smsStyle +
        ", localUrl=" + localUrl +
        ", licenceUrl=" + licenceUrl +
        ", serialPort=" + serialPort +
        ", meetingStatus=" + meetingStatus +
        ", devicePhoneType=" + devicePhoneType +
        ", isShowDevicePhone=" + isShowDevicePhone +
        ", isShowTableVipname=" + isShowTableVipname +
        ", isShowPublicVip=" + isShowPublicVip +
        ", isMeetingCheck=" + isMeetingCheck +
        ", changeTableCheck=" + changeTableCheck +
        ", cancelCheck=" + cancelCheck +
        ", changeDishStandardCheck=" + changeDishStandardCheck +
        ", changeResvTableNumCheck=" + changeResvTableNumCheck +
        ", orderExemptionVerifyCheck=" + orderExemptionVerifyCheck +
        ", isOrderQuery=" + isOrderQuery +
        ", isKbc=" + isKbc +
        ", softTitle=" + softTitle +
        ", isFcb=" + isFcb +
        ", openDate=" + openDate +
        ", price=" + price +
        ", yearPrice=" + yearPrice +
        ", yearPriceOne=" + yearPriceOne +
        ", yearPriceTwo=" + yearPriceTwo +
        ", yearPriceThree=" + yearPriceThree +
        ", dueDate=" + dueDate +
        ", delayDays=" + delayDays +
        ", tag=" + tag +
        ", merchantPid=" + merchantPid +
        ", shopId=" + shopId +
        ", isExcelVipPhone=" + isExcelVipPhone +
        ", isExcelCheck=" + isExcelCheck +
        ", isSeatUpdate=" + isSeatUpdate +
        ", isReVipMessage=" + isReVipMessage +
        ", isVipPhoneHidden=" + isVipPhoneHidden +
        ", notifyBeforeDay=" + notifyBeforeDay +
        ", isWeixin=" + isWeixin +
        ", meetingConfirmMessage=" + meetingConfirmMessage +
        ", meetingCancelMessage=" + meetingCancelMessage +
        ", meetingUnorderMessage=" + meetingUnorderMessage +
        ", isLuckyDay=" + isLuckyDay +
        ", confirmServiceItems=" + confirmServiceItems +
        ", isChangeTable=" + isChangeTable +
        ", orderDeleteable=" + orderDeleteable +
        ", enableMeetingApi=" + enableMeetingApi +
        ", appToken=" + appToken +
        ", isMeituan=" + isMeituan +
        ", cancelSeatedorderAble=" + cancelSeatedorderAble +
        ", isOnlineOrder=" + isOnlineOrder +
        ", isMeetingOrderMore=" + isMeetingOrderMore +
        ", defMealsNum=" + defMealsNum +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        ", logoPic=" + logoPic +
        ", tableTag=" + tableTag +
        "}";
    }
}
