package com.zhidianfan.pig.yd.moduler.meituan.constant;

/**
 * @Author qqx
 * @Description 基本参数
 * @Date Create in 2018/9/03
 * @Modified By:
 */
public class MeituanMethod {

    /**
     * 美团开发者id
     */
    public static final int DEVELOPERID = 104685;

    public static final String VERSION = "1";

    public static final String CHARSET = "utf-8";

    public static final String CONTENTTYPE = "application/x-www-form-urlencoded";

    /**
     * 美团SignKey
     */
    public static final String SIGNKEY = "ebvdsj1yusyxtbfj";

    /**
     * 美团测试门店名称(永久)
     */
    public static final String STORENAME = "t_UVXaQ28G";

    /**
     * 美团测试门店名称(期限)
     */
    public static final String STORENAME_QX = "t_YjUSVjwY";

    /**
     * 美团1.3.1新增/修改门店时段测试url
     */
    public static final String MEAL_SERVER_URL_CS = "http://api.open.cater.meituan.com/sandbox/resv/v2/config/update";

    /**
     * 美团1.3.1新增/修改门店时段正式url
     */
    public static final String MEAL_SERVER_URL = "http://api.open.cater.meituan.com/resv/v2/table/status/update";

    /**
     * 美团1.1.1新增/修改桌位测试url
     */
    public static final String TABLE_SERVER_URL_CS = "http://api.open.cater.meituan.com/sandbox/resv/v2/table/save";

    /**
     * 美团1.1.1新增/修改桌位正式url
     */
    public static final String TABLE_SERVER_URL = "http://api.open.cater.meituan.com/resv/v2/table/save";

    /**
     * 美团1.2.1删除桌位测试url
     */
    public static final String TABLE_DELETE_SERVER_URL_CS = "http://api.open.cater.meituan.com/sandbox/resv/v2/table/delete";

    /**
     * 美团1.2.1删除桌位正式url
     */
    public static final String TABLE_DELETE_SERVER_URL = "http://api.open.cater.meituan.com/resv/v2/table/delete";

    /**
     * 美团1.4.2 订单变更测试url
     */
    public static final String ORDER_UPDATE_SERVER_URL_CS = "http://api.open.cater.meituan.com/sandbox/resv/v2/order/operation";

    /**
     * 美团1.4.2 订单变更正式url
     */
    public static final String ORDER_UPDATE_SERVER_URL = "http://api.open.cater.meituan.com/resv/v2/order/operation";

    /**
     * 美团1.4.1根据订单id查询订单测试url
     */
    public static final String ORDER_QUERY_SERVER_URL_CS = "http://api.open.cater.meituan.com/sandbox/resv/v2/order/queryById";

    /**
     * 美团1.4.1根据订单id查询订单正式url
     */
    public static final String ORDER_QUERY_SERVER_URL = "http://api.open.cater.meituan.com/resv/v2/order/queryById";

    /**
     * 美团获取获取appAuthToken
     */
    public static final String APP_AUTH_TOKEN = "https://open-erp.meituan.com/storemap";

    /**
     * 美团解绑url
     */
    public static final String UN_APP_AUTH_TOKEN = "https://open-erp.meituan.com/releasebinding";

    /**
     * 美团1.2.1桌位状态变更正式url
     */
    public static final String TABLE_UPDATE_SERVER_URL = "http://api.open.cater.meituan.com/resv/v2/table/status/update";

    /**
     * 美团1.2.1桌位状态变更测试url
     */
    public static final String TABLE_UPDATE_SERVER_URL_CS = "http://api.open.cater.meituan.com/sandbox/resv/v2/table/status/update";



}
