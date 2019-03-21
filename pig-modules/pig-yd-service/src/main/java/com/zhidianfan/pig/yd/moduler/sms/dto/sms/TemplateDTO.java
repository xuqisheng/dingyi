package com.zhidianfan.pig.yd.moduler.sms.dto.sms;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @Author mikrotik
 * @Description
 * @Date Create in 2018/9/25
 * @Modified By:
 */
@Data
public class TemplateDTO {
	private Integer id;
	@NotNull(message = "酒店id不能为空")
	private Integer businessId;
	@NotBlank(message = "模板标题不能为空")
	private String templateTitle;
	@NotBlank(message = "模板内容不能为空")
	private String templateContent;
	private String templateVariable;
}
