package com.zhidianfan.pig.yd.moduler.meituan.constant;

/**
 * @Author qqx
 * @Description 天港基本参数
 * @Date Create in 2019-05-23
 * @Modified By:
 */
public class TianGangMethod {

    /**
     * 获取token用户名
     */
    public static final String TIANGANG_USERNAME = "prd";

    /**
     * 获取token密码
     */
    public static final String TIANGANG_PASSWORD = "u91oaTmx01pnqwz";

    /**
     * 获取tokenURL
     */
    public static final String GET_TOKEN_URL = "http://qy.teckongroup.com:8080/teckon-all-man-sale/oauth/token";

    /**
     * 健康检查URL
     */
    public static final String HEALTH_URL = "http://qy.teckongroup.com:8080/teckon-all-man-sale/svc/yiding/health";

    /**
     * 创建订单URL
     */
    public static final String CREATE_ORDER_URL = "http://qy.teckongroup.com:8080/teckon-all-man-sale/svc/yiding/create/meal";

    /**
     * 修改订单URL
     */
    public static final String UPDATE_ORDER_URL = "http://qy.teckongroup.com:8080/teckon-all-man-sale/svc/yiding/update/meal";

    /**
     * 取消订单URL
     */
    public static final String CANCEL_ORDER_URL = "http://qy.teckongroup.com:8080/teckon-all-man-sale/svc/yiding/cancel/meal";

    /**
     * 结账URL
     */
    public static final String SUBMIT_ORDER_URL = "http://qy.teckongroup.com:8080/teckon-all-man-sale/svc/yiding/submit/payment";

}
