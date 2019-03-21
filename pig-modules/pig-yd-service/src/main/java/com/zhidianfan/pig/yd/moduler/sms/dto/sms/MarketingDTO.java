package com.zhidianfan.pig.yd.moduler.sms.dto.sms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author mikrotik
 * @Description
 * @Date Create in 2018/9/26
 * @Modified By:
 */
@Data
public class MarketingDTO {
	@NotNull
	private Integer businessId;
	private Integer page;
	private Integer limit;

	@ApiModelProperty("1-已发送，未确认 2-发送后被确认成功 3-发送后被确认失败 4-发送失败")
	private String status;
}
