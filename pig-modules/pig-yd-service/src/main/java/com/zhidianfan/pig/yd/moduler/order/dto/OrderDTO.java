package com.zhidianfan.pig.yd.moduler.order.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 同步订单请求参数
 * @author danda
 */
@Data
public class OrderDTO {
	/**
	 * 订单号
	 */
	@NotEmpty
	private String orderNo;
	/**
	 * 第三方业务酒店id
	 */
	@NotEmpty
	private String businessHotelId;
	/**
	 * 预订员手机号码
	 */
	@NotEmpty
	private String appUserPhone;
	/**
	 * 客户电话
	 */
	@NotEmpty
	private String phone;
	/**
	 * 客户名称
	 */
	@NotEmpty
	private String vipName;
	/**
	 * 性别
	 */
	@NotEmpty
	private String sex;
	/**
	 * 营业点艾玛
	 */
	@NotEmpty
	private String pcCode;
	/**
	 * 货位代码
	 */
	@NotEmpty
	private String tableCode;
	/**
	 * 预定人数
	 */
	@NotEmpty
	private String resvNum;
	/**
	 * 预定日期
	 */
	@NotEmpty
	private String resvDate;
	/**
	 * 就餐时间
	 */
	@NotEmpty
	private String mealTime;
}
