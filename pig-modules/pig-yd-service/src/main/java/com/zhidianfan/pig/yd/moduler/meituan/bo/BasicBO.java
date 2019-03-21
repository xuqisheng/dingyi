package com.zhidianfan.pig.yd.moduler.meituan.bo;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/9/03
 * @Modified By:
 */
@Getter
@Setter
@ToString
public class BasicBO {
	/**
	 * 数据
	 */
	private JSON data;
	/**
	 * 错误
	 */
	private JSON error;
	/**
	 * sign
	 */
	private String sign;
}
