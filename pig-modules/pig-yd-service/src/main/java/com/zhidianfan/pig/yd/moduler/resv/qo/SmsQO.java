package com.zhidianfan.pig.yd.moduler.resv.qo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SmsQO {
	/**
	 * 酒店id
	 */
	private Integer businessId;
	/**
	 * 模板烈性
	 */
	private Integer templateType;
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 订单烈性id
	 */
	private Integer resvOrderTypeId;
	/**
	 * 订单号
	 */
	private String resvOrder;
	/**
	 * 旧桌位名称
	 */
	private String oldTableName;
	/**
	 * 旧桌位区域
	 */
	private String oldTableAreaName;
	/**
	 * 确认所有桌位
	 */
	private List<CheckedTableQO> checkedTables;
}
