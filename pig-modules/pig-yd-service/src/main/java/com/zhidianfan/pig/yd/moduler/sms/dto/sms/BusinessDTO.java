package com.zhidianfan.pig.yd.moduler.sms.dto.sms;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author mikrotik
 * @Description
 * @Date Create in 2018/9/25
 * @Modified By:
 */
@Data
public class BusinessDTO {
	@NotNull(message = "酒店id不能为空")
	private Integer businessId;
	@NotNull(message = "当前页数不能为空")
	private Integer page;
	@NotNull(message = "每页显示条数不能为空")
	private Integer limit;
}
