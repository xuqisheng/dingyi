package com.zhidianfan.pig.yd.moduler.resv.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: huzp
 * @Date: 2018/9/21 14:11
 */
@Data
public class AddOrderDTO {

    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 订单号
     */
    private String resvOrder;

    @NotNull(message = "酒店ID不存在")
    private Integer businessId;

    @NotBlank(message = "酒店名字不存在")
    private String businessName;

    private Integer appUserId;

    private String appUserPhone;
    /**
     * 客户id
     */
    private Integer vipId;
    /**
     * 客户电话
     */
    private String vipPhone;
    /**
     * 客户名称
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
     * 桌位信息
     */
    private TableDTO[] tableDTO;

    /**
     * 预定人数
     */
    private String resvNum;
    /**
     * 实际人数
     */

    private String actualNum;
    /**
     * 预定日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private Date resvDate;
    /**
     * 用餐时段id
     */
    @NotEmpty(message = "用餐时段ID不存在")
    private Integer mealTypeId;
    /**
     * 用餐时段name
     */
    @NotBlank(message = "用餐时段名称不存在")
    private String mealTypeName;
    /**
     * 分餐次id a
     */
    private Integer mealTypeIdA;
    /**
     * 分餐次名称a
     */

    private String mealTypeNameA;
    /**
     * 分餐次id b
     */
    private Integer mealTypeIdB;
    /**
     * 分餐次名称b
     */
    private String mealTypeNameB;
    /**
     * 订单备注
     */
    private String remark;

    /**
     * 1:已预约，2、已入座，3、已结账，4、已退订 5、锁台
     */
    private String status;
    /**
     * 0是未确认一定会来，1是确认要来就餐
     */
    private String confirm;

    private String appUserName;

    private Integer deviceUserId;

    private String deviceUserName;

    private String deviceUserPhone;
    private String peicai;

    private String picUrl;
    private String payamount;

    private String unorderReason;
    /**
     * 1是有押金0是没有押金
     */
    private String deposit;
    /**
     * 0是非锁台1是锁台
     */
    private String locked;

    private String receiptNo;

    private String depositAmount;

    private Integer payType;

    private Integer resvOrderType;
    /**
     * 锁台原因 1.是因为宴会锁台  2.换衣间锁台
     */

    private Integer lockedType;
    /**
     * 因宴会锁台的关联宴会订单号
     */

    private String resvMeetingOrderNo;
    /**
     * 外部来源
     */

    private String externalSourceName;

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

    private Double peicaiAmt;
    /**
     * 配菜类型 0-人均 1-桌均
     */

    private Integer peicaiType;

    private String destTime;

    private Integer propertyId;

    private String propertyName;
    /**
     * 0普通预订，1排位预订
     */
    private Integer isrank;
    private Integer issendmsg;

    private String reVipPhone;

    private String reVipName;

    private String reVipSex;
    /**
     * 是否接口同步
     */

    private Integer openIsSync;
    /**
     * 历史状态
     */

    private Integer orderStatus;

    private Integer depositStatus;

    private String depositDesc;

    private Integer isDish;

    private String thirdOrderNo;

    /**
     * 排队号
     */
    private String lineOrderNo;

    /**
     * 退订原因id
     */
    private String unorderReasonId;

    /**
     * 安卓电话机用户信息
     */
    private Integer androidUserId;
    private String androidUserName;

    /**
     * 第三方订单来源 id 2为美团/大众  4为易订公众号
     */
    private Integer thirdPartyId;


    /**
     * 设备类型
     * 1 为安卓电话机
     * 2 为小程序
     */
    private String deviceType;

}
