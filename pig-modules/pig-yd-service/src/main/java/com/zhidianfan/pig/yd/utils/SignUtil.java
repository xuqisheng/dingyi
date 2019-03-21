package com.zhidianfan.pig.yd.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.*;

/**
 * Created by chenxin on 2017/9/14.
 */
public class SignUtil {
    private static final String REQUEST_PARAM_SIGN = "sign";

    private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);

    public static boolean verifySignature(Map<String, Object> params, String signKey){
        String signature = calcSign(params, signKey);
        String erpSignature = (String) params.get(REQUEST_PARAM_SIGN);
        return signature.equals(erpSignature);
    }

    public static String calcSign(Map<String, Object> params, String signKey){
        String signStr = signKey + genSignStr(params);
        logger.debug("sign string = {}", signStr);
        return Sha1Util.SHA1(signStr).toLowerCase();
    }

    public static String genSignStr(Map<String, Object> params){
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        StringBuilder signStr = new StringBuilder();
        for (String key : keys){
            Object value = params.get(key);
            if(key.equalsIgnoreCase(REQUEST_PARAM_SIGN) || value == null || value.equals("")){
                continue;
            }
            signStr.append(key);
            if(value instanceof JSONObject || value instanceof JSONArray){
                //处理默认toJSONString会过滤为null的key value情况
                signStr.append(JSON.toJSONString(value, SerializerFeature.WriteNullStringAsEmpty));
            } else {
                signStr.append(value);
            }
        }

        return signStr.toString();
    }

    public static String genSignStr1(Map<String, Object> params) throws Exception{
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        StringBuilder signStr = new StringBuilder();
        for (String key : keys){
            Object value = params.get(key);
            if(key.equalsIgnoreCase(REQUEST_PARAM_SIGN) || value == null || value.equals("")){
                continue;
            }
//            signStr.append(key);
            if(value instanceof JSONObject || value instanceof JSONArray){
                //处理默认toJSONString会过滤为null的key value情况
                signStr.append(URLEncoder.encode(key,"utf-8")).append("=").append(URLEncoder.encode(JSON.toJSONString(value, SerializerFeature.WriteNullStringAsEmpty),"utf-8")).append("&");
//                signStr.append(JSON.toJSONString(value, SerializerFeature.WriteNullStringAsEmpty));
            } else {
                signStr.append(URLEncoder.encode(key,"utf-8")).append("=").append(URLEncoder.encode(value.toString(),"utf-8")).append("&");
//                signStr.append(value);
            }
        }

        return signStr.toString();
    }
}
