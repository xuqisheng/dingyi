package com.zhidianfan.pig.yd.moduler.order.query;

import com.zhidianfan.pig.yd.moduler.order.constant.XopMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class BasicQueryOption {
	/**
	 * 客户AppKey
	 */
	private final String appkey = XopMethod.APPKEY;
	/**
	 * 连接session
	 */
	private String session;
	/**
	 * 服务方法
	 */
	private final String method = XopMethod.GET_POS_TABLES_STATUS;
	/**
	 * 服务方法版本
	 */
	private final String ver = XopMethod.VER;
	/**
	 * 酒店Id
	 */
	private String hotelid;
	/**
	 * 返回信息语种
	 */
	private final String loc = XopMethod.LOC;
	/**
	 * 请求时间
	 */
	private final Long ts = System.currentTimeMillis();
	/**
	 * 数字校验码
	 */
	private String sign;
	/**
	 * 额外的请求参数集
	 */
	private Map<String, String> extras;
	/**
	 * 业务请求参数列表
	 */
	private List<Map<String, String>> params;}
