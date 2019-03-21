package com.zhidianfan.pig.push.constants;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/6
 * @Modified By:
 */
public class WxConstant {
    public static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={1}&secret={2}";
    public static final String SEND_TEMLATE_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={1}";
}
