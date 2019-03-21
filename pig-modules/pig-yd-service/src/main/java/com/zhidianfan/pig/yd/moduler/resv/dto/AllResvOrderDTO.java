package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Future;
import java.util.Date;

@Getter
@Setter
public class AllResvOrderDTO {
	/**
	 * 酒店id
	 */
	@NotEmpty(message = "酒店id不能为空")
	private Integer businessId;
	/**
	 * 预定日期
	 */
	@NotEmpty(message = "到店日期不能为空")
	@Future(message = "不能早于当前日期")
	private Date resvDate;
	/**
	 * 餐次id
	 */
	@NotEmpty(message = "餐次id不能为空")
	private Integer mealTypeId;
	/**
	 * 餐次id a
	 */
	private Integer mealTypeIdA;
	/**
	 * 餐次id b
	 */
	private Integer mealTypeIdB;
	/**
	 * 桌位区域id
	 */
	private String tableAreaId;
	/**
	 * 订单状态
	 */
	private Integer orderStatus;
}
