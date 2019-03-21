package com.zhidianfan.pig.yd.moduler.resv.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderBO {
	private int id;

	private String batchNo;
	private String resvOrder;

	private Integer businessId;
	private String businessName;

	private String status;
	private String statusName;
	private Date resvDate;
	private Integer tableAreaId;
	private String tableAreaName;
	private String mealTypeName;
	private Integer tableId;
	private String tableName;
	private String maxPeopleNum;
	private String vipName;
	private String appUserName;
	private String appUserId;
	private String appUserPhone;
	private String vipPhone;
	private String vipSex;
	private String vipCompany;
	private String resvNum;
	private String actualNum;
	private String confirm;
	private String remark;
	private String createdAt;
	private String old_tableName;
	private String type;
	private String deviceUserName;
	private String unorderReason;
	private String unorderName;
	private String unorderedAt;
	private String deposit;
	private String depositAmount;
	private Integer payType;
	private String receiptNo;
	private Integer resvOrderType;
	private Integer lockedType;
	private Integer externalSourceId;
	private String externalSourceName;

	private Integer isKbc;        //是否跨餐别订单
	private Integer nextMealTypeOrderStatus;        //下一个餐别该桌位是否有订单
	private String arrivalTime;
	private String tag;

	private String picUrl;
	private String peicai;
	private String peicaiAmt;
	private String peicaiType;

	private Integer mealTypeId;
	private String mealTypeIdA;
	private String mealTypeIdB;

	private Integer cbf;

	private String resvMeetingOrderNo;
	private String destTime;
	private String isSendMsg;

	/**
	 * 历史状态
	 */
	private Integer orderStatus;
	private Integer depositStatus;

	@ApiModelProperty("批次号对应的所有桌位的名称")
	private List<String> tableNames;
}
