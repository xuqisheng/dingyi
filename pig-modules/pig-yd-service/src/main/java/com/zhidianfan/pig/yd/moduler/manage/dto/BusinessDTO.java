package com.zhidianfan.pig.yd.moduler.manage.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/2
 * @Modified By:
 */
@Data
public class BusinessDTO {

    private Long id;
    private String businessName;
    /**
     * 酒店类型0:普通酒店 1:五星酒店
     */
    private Integer businessType;
    private Integer brandId;
    private String businessPhone;
    private String businessAddress;
    private String businessStar;
    private String bussinessManager;
    private String openId;
    private Integer agentId;
    private String agentName;
    private String phone;
    /**
     * 城市id
     */
    private Integer cityId;
    /**
     * 省份id
     */
    private Integer provinceId;
    private Integer areaId;
    private String provinceName;
    private String cityName;
    private String areaName;
    /**
     * 0 待审核 1提供 2拒绝 3停用
     */
    private String status;
    private String loginUser;
    private String loginPassword;
    private Integer device;
    /**
     * 新增时间
     */
    private Date createdAt;
    /**
     * 修改时间
     */
    private Date updatedAt;
    /**
     * 审批时间
     */
    private Date reviewTime;
    private String reviewUser;
    /**
     * 短信风格 1为带客户姓名，2为不带客户姓名
     */
    private Integer smsStyle;
    private String localUrl;
    /**
     * 营业执照
     */
    private String licenceUrl;
    private String serialPort;
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
     * 订单免审核开关 0关 1开
     */
    private Integer orderExemptionVerifyCheck;
    /**
     * 0-禁用订单查询 1-启用订单查询
     */
    private Integer isOrderQuery;
    /**
     * 是否跨班次 0-否 1-是 is_kbc is_cross_meal_type
     */
    private Integer isKbc;
    private String softTitle;
    /**
     * 是否分餐别 0-否 1-是
     */
    private Integer isFcb;
    /**
     * 酒店开户时间
     */
    private Date openDate;
    private BigDecimal price;
    private BigDecimal yearPrice;
    private BigDecimal yearPriceOne;
    private BigDecimal yearPriceTwo;
    private BigDecimal yearPriceThree;
    private Date dueDate;
    private Integer delayDays;
    private String tag;
    private String merchantPid;
    private String shopId;
    /**
     * 导出订单表是否显示客户电话 0-否 1-是
     */
    private Integer isExcelVipPhone;
    /**
     * 导出表格是否短信验证, 0-不验证, 1-验证
     */
    private Integer isExcelCheck;
    /**
     * 0不允许1允许 入座后是否允许修改
     */
    private Integer isSeatUpdate;
    /**
     * 是否允许添加备选客户信息 0-不允许, 1-允许
     */
    private Integer isReVipMessage;
    /**
     * 预订单是否隐藏号码中间四位 0-否 1-是
     */
    private Integer isVipPhoneHidden;
    /**
     * 纪念日提醒提前时间
     */
    private Integer notifyBeforeDay;
    /**
     * 微信预定开关, 该开关打开门店后台才会显示微信预定时间设置按钮
     */
    private Integer isWeixin;
    /**
     * 宴会确认短信, 0-不发送, 1-发送
     */
    private Integer meetingConfirmMessage;
    /**
     * 宴会取消短信, 0-不发送. 1-发送
     */
    private Integer meetingCancelMessage;
    /**
     * 宴会退订短信, 0-不发送, 1-发送
     */
    private Integer meetingUnorderMessage;
    /**
     * 宴会吉日开关,0为关闭 1为开启
     */
    private Integer isLuckyDay;
    private String confirmServiceItems;
    /**
     * 换桌标签开关，0为关闭 1为开启
     */
    private Integer isChangeTable;
    /**
     * 是否可删除订单
     */
    private Integer orderDeleteable;
    /**
     * 是否启用宴会接口0:停用1:启用
     */
    private Integer enableMeetingApi;
    /**
     * 美团token
     */
    private String appToken;
    /**
     * 是否开启美团 0 不启用 1 启用
     */
    private Integer isMeituan;
    /**
     * 是否可取消入座订单 1 可取消 0 不可取消
     */
    private Integer cancelSeatedorderAble;
    /**
     * 是否开启线上订单 0 不启用  1 启用
     */
    private Integer isOnlineOrder;
    /**
     * 是否开启一厅多笔确认单 0 不启用 1启用
     */
    private Integer isMeetingOrderMore;
    /**
     * 酒店默认就餐人数
     */
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
    private String logoPic;

    private Integer oldStatus;

    private String snCodes;

    private int page;

    private int rows;

}
