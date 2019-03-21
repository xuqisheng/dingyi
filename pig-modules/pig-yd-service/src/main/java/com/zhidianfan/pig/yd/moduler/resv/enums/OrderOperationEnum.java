package com.zhidianfan.pig.yd.moduler.resv.enums;

import lombok.Getter;

@Getter
public enum OrderOperationEnum {
	/**
	 * 换桌
	 */
	EXCHANGE_TABLE("换桌",1),
	/**
	 * 加桌
	 */
	ADD_TABLE("加桌",2),
	/**
	 * 退订
	 */
	CANCEL("退订",3),
	;

	private String name;
	private Integer index;
	OrderOperationEnum(String name, int index) {
		this.name = name;
		this.index = index;
	}
}
