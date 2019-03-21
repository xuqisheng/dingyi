package com.zhidianfan.pig.yd.moduler.meituan.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @Author qqx
 * @Description restTemplatePost请求参数
 * @Date Create in 2018/9/03
 * @Modified By:
 */
@Data
public class BasicDTO {
	/**
	 * appAuthToken
	 */
	private String appAuthToken;
	/**
	 * 请求data
	 */
	private JSON data;
	/**
	 * 请求URL
	 */
	private String url;

}
