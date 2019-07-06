package com.zhidianfan.pig.yd.moduler.order.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author mikrotik
 * @Description
 * @Date Create in 2018/9/28
 * @Modified By:
 */
@Data
public class OrderTem {
	private Integer id;
	private Integer tableAreaId;
	private String tableAreaName;
	private String areaCode;
	private Integer tableId;
	private String tableCode;
	private String tableName;
	private String resvOrder;
	private String batchNo;
	private Integer businessId;
	private String businessName;
	private Integer vipId;
	private String vipName;
	private String vipPhone;
	private String vipSex;
	private String resvNum;
	private Date resvDate;
	private Integer mealTypeId;
	private String mealTypeName;
	private String mealTypeCode;
	private Date createdAt;
	private Date updatedAt;
	private String payamount;
	private String unorderReason;
	private String status;
	private Integer isChangeTable;
	private String remark;
	private String destTime;
	private String appUserName;
	private String appUserCode;
	private String isDish;
	private String thirdOrderNo;
	private Integer isTirdparty;
}
