package com.zhidianfan.pig.yd.moduler.order.constant;

/**
 * @author danda
 */
public class XopMethod {
	public static final String URL = "http://teckon.xms.foxhis.com:8080/xmsopen-web/rest";
	public static final String APPKEY = "EDING";
	public static final String SECRET = "yVkmQOkPx58d0cm2xv3";
	public static final String VER = "1.0.0";
	public static final String LOC = "zh_CN";
	/**
	 * 登录
	 */
	public static final String LOGIN = "xmsopen.public.login";
	/**
	 * 获取餐饮营业点
	 */
	public static final String GET_POS_PC_CODES = "xmsopen.pos.xopgetpospccodes";
	/**
	 * 获取餐台
	 */
	public static final String GET_POS_TABLES = "xmsopen.pos.xopgetpostables";
	/**
	 * 获取餐台状态
	 */
	public static final String GET_POS_TABLES_STATUS = "xmsopen.pos.xopgettablestatus";
	/**
	 * 获取餐饮预定订餐类别
	 */
	public static final String GET_POS_TAG = "xmsopen.pos.xopgetpostag";
	/**
	 * 根据条件获取餐单信息
	 */
	public static final String GET_POS_INFO = "xmsopen.pos.xopgetposinfo";
	/**
	 * 保存、修改预订单
	 */
	public static final String SAVE_POS_RES_SERVE = "xmsopen.pos.xopsaveposreserve";
	/**
	 * 取消预订单
	 */
	public static final String CANCEL_POS_RESERVE = "xmsopen.pos.xopcancelposreserve";
	public static final String GET_RESERVE_LIST = "xmsopen.pos.xopgetreservelist";
	/**
	 * 获取菜品大类
	 */
	public static final String GET_POS_PLU_CODES = "xmsopen.pos.xopgetposplucodes";
	/**
	 * 获取菜品小类
	 */
	public static final String GET_POS_SORTS = "xmsopen.pos.xopgetpossorts";
	/**
	 * 获取菜品
	 */
	public static final String GET_POS_PLUS = "xmsopen.pos.xopgetposplus";
	/**
	 * 获取价格
	 */
	public static final String GET_POS_PRICES = "xmsopen.pos.xopgetposprices";
	public static final String GET_TABLE_MENUS= "xmsopen.pos.xopgettablemenus";
	public static final String GET_POS_RESERVER = "xmsopen.pos.xopgetposreserve";

	public static final String GET_TABLE_MENU = "xmsopen.pos.xopgettablemenus";

	public static final String GET_ORDER_STATUS = "xmsopen.pos.xopgetipadmenusta";

	public static final String GET_ORDER_DISH = "xmsopen.pos.xopgetorderdishsone";
}
