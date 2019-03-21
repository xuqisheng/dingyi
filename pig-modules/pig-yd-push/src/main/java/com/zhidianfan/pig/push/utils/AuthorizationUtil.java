package com.zhidianfan.pig.push.utils;

import com.alibaba.fastjson.JSONObject;
import com.zhidianfan.pig.push.config.YdPropertites;
import com.zhidianfan.pig.push.controller.dto.DeviceTagAliasDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.Collections;

@Component
public class AuthorizationUtil {


    @Resource
    private  YdPropertites ydPropertites;

    /**
     * 设置登录请求头
     * @param body
     * @return
     */
    public  HttpEntity setJGHttpEntity(Object body){

        String userMsg = ydPropertites.getJiguang().getDevKey() + ":" + ydPropertites.getJiguang().getDevSecret();
        String base64UserMsg = Base64.getEncoder().encodeToString(userMsg.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic "+base64UserMsg);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        HttpEntity entity ;

        if(body==null){
            entity=new HttpEntity<>(headers) ;
        }else {
            entity=new HttpEntity<>(JSONObject.toJSONString(body), headers);
        }
        return entity;
    }
}
