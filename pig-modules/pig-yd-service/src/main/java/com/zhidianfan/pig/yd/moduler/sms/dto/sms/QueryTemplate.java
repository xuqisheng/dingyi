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
public class QueryTemplate {
	@NotNull
	private Integer businessId;
	private Integer page;
	private Integer limit;

	@ApiModelProperty("内容搜索关键字")
	private String keyword;
}
