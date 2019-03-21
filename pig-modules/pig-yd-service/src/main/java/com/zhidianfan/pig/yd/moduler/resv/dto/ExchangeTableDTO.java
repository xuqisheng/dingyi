package com.zhidianfan.pig.yd.moduler.resv.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

//@Data
@Setter
@Getter
public class ExchangeTableDTO {
	/**
	 * 酒店id
	 */
	@NotNull(message = "酒店id不能为空")
	private Integer businessId;
	/**
	 * 桌位id
	 */
	@NotNull(message = "桌位id不能为空")
	private Integer tableId;
	/**
	 * 订单号
	 */
	@NotEmpty(message = "订单号不能为空")
	private String resvOrder;
	/**
	 * 预定日期
	 */
	@NotNull(message = "预定日期不能为空")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
	private Date resvDate;
	/**
	 * 餐次id
	 */
	@NotNull(message = "餐次id不能为空")
	private Integer mealTypeId;
	/**
	 * 餐次名称
	 */
	private String mealTypeName;
	/**
	 * 餐次类型a
	 */
	private Integer mealTypeIdA;
	/**
	 * 餐次类型b
	 */
	private Integer mealTypeIdB;
	/**
	 * 旧桌位名称
	 */
	private String oldTableName;
	/**
	 * 旧桌位区域名称
	 */
	private String oldTableAreaName;

	/**
	 * 设备类型
	 * 1 为安卓电话机
	 * 2 为小程序
	 */
	private String  deviceType;
}


