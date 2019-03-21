package com.zhidianfan.pig.yd.moduler.resv.qo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageOrderQO {
	/**
	 * 订单号
	 */
	private String resvOrder;
	/**
	 * 短信类型
	 */
	private String smsType;
	/**
	 * 酒店短信设置类型
	 */
	private Integer type;
}
