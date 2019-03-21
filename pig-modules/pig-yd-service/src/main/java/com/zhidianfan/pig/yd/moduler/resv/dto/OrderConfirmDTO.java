package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
public class OrderConfirmDTO {
	/**
	 * 订单号
	 */
	@NotEmpty(message = "订单号不存在")
	private String resvOrder;
	/**
	 * 确认 1确认
	 */
	@NotEmpty(message = "订单未确认")
	private String confirm;
	/**
	 * 旧桌位名称
	 */
	private String oldTableName;
	/**
	 * 旧桌位区域名称
	 */
	private String oldTableAreaName;
}
