package com.zhidianfan.pig.push.utils;

import com.alibaba.fastjson.JSONObject;
import com.zhidianfan.pig.push.constants.WxConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/6
 * @Modified By:
 */
@Component
@Slf4j
public class WxUtils {
    @Autowired
    private RestTemplate restTemplate;

    @Cacheable(value = "wx_access_token",key = "'wx_access_token:'+#appId+':'+#appSecret",cacheManager = "cacheManager")
    public String getToken(String appId,String appSecret){
        ResponseEntity<JSONObject> entity = null;
        try {
            entity = restTemplate.getForEntity(WxConstant.TOKEN_URL, JSONObject.class, appId, appSecret);
        } catch (RestClientException e) {
            log.info(e.getMessage());
            return null;
        }
        JSONObject body = entity.getBody();
        if(body.getString("errcode") != null){
            log.info("获取token失败,code:{},msg:{}",body.getString("errcode"),body.getString("errmsg"));
            return null;
        }
        return body.getString("access_token");
    }
}
