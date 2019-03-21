package com.zhidianfan.pig.yd.moduler.order.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class PosPlusBO extends BasicBO{
	/**
	 * 业务返回结果
	 */
	private List<Map<String, String>> results;
}
