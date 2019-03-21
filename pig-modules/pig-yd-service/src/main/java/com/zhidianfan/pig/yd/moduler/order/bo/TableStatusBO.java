package com.zhidianfan.pig.yd.moduler.order.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author mikrotik
 */
@Getter
@Setter
@ToString
public class TableStatusBO extends BasicBO {
	/**
	 * 成功返回true
	 */
	private boolean success;
	/**
	 * 代码
	 */
	private String code;
	/**
	 * 描述
	 */
	private String msg;
	/**
	 * 响应时间
	 */
	private Long ts;
	/**
	 * 额外数据
	 */
	private Map<String, String> extras;
	/**
	 * 业务返回结果
	 */
	private List<Map<String, String>> results;
}
