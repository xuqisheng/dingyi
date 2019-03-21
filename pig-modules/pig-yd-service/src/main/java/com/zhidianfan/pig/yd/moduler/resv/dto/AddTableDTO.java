package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
public class AddTableDTO {
	/**
	 * 订单号
	 */
	@NotEmpty(message = "订单号不能为空")
	private String resvOrder;
	/**
	 * 桌位id
	 */
	@NotEmpty(message = "桌位id不能为空")
	private Integer tableId;
}
