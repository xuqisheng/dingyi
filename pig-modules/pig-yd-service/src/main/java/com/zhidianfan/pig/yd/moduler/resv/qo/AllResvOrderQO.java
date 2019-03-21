package com.zhidianfan.pig.yd.moduler.resv.qo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class AllResvOrderQO {
	/**
	 * 桌位id列表
	 */
	private List<String> tableAreaId;
	private Integer isKbc;
	/**
	 * 餐次类别id
	 */
	private Integer mealTypeId;
	/**
	 * 餐次类别id a
	 */
	private Integer mealTypeIdA;
	/**
	 * 餐次类别id b
	 */
	private Integer mealTypeIdB;
	/**
	 * 预定日期
	 */
	private Date resvDate;
	/**
	 * 酒店id
	 */
	private Integer businessId;
	/**
	 * 酒店状态
	 */
	private Integer resvOrderStatus;
}
