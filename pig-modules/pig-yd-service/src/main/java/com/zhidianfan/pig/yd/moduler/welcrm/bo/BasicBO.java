package com.zhidianfan.pig.yd.moduler.welcrm.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/10/09
 * @Modified By:
 */
@Getter
@Setter
@ToString
public class BasicBO {
	/**
	 * 数据
	 */
	private Integer errcode;
	/**
	 * 错误
	 */
	private String errmsg;
	/**
	 * data
	 */
	private JSONArray res;

	private Map<String,Object> data;
}
