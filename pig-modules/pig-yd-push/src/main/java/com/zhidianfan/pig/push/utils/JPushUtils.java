package com.zhidianfan.pig.push.utils;

import java.util.Base64;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/4
 * @Modified By:
 */
public class JPushUtils {
    public static String buildAuth(String appKey, String appSecret) {
        return Base64.getEncoder().encodeToString((appKey + ":" + appSecret).getBytes());
    }
}
