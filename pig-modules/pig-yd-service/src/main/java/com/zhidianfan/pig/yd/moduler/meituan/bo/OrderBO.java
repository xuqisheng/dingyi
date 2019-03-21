package com.zhidianfan.pig.yd.moduler.meituan.bo;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/9/07
 * @Modified By:
 */
@Getter
@Setter
@ToString
public class OrderBO {
	/**
	 * 数据
	 */
	private String data;
	/**
	 * 错误
	 */
	private JSON error;
	/**
	 * sign
	 */
	private String sign;
}
