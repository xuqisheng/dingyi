package com.zhidianfan.pig.yd.moduler.order.query;

import com.zhidianfan.pig.yd.moduler.order.constant.XopMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mikrotik
 */
@Getter
@Setter
@ToString
public class LoginQueryOption {
    private final String appkey = XopMethod.APPKEY;
    private final String secret = XopMethod.SECRET;
    private final String method = XopMethod.LOGIN;
    private final String ver = XopMethod.VER;
    private String hotelid;
    private final String loc = XopMethod.LOC;
    private final Long ts = System.currentTimeMillis();
    private String sign;
}
