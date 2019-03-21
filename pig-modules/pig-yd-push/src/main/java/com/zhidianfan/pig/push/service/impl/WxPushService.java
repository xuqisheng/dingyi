package com.zhidianfan.pig.push.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.common.constant.LogPush;
import com.zhidianfan.pig.common.constant.QueueName;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.push.config.YdPropertites;
import com.zhidianfan.pig.push.constants.WxConstant;
import com.zhidianfan.pig.push.utils.WxUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/6
 * @Modified By:
 */
@Service
@Slf4j
public class WxPushService {
    /**
     * 远程调用
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 微信工具列
     */
    @Autowired
    private WxUtils wxUtils;

    /**
     * rabbitmq操作
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 易订自定义配置
     */
    @Autowired
    private YdPropertites ydPropertites;

    /**
     * 推送给单个设备
     *
     * @param openId
     * @param templateId
     * @param params
     * @return
     */
    public boolean pushToSingle(String openId, String templateId, String url, Map<String, Object> params) {
        //推送日志
        LogPush logPush = new LogPush("2", openId, templateId);
        logPush.setPushTime(new Date());
        Map<String, Object> data = Maps.newHashMap();
        data.put("touser", openId);
        data.put("template_id", templateId);
        if (StringUtils.isNotBlank(url)) {
            data.put("url", url);
        }

        if(!CollectionUtils.sizeIsEmpty(params)){
            data.put("data",params);
        }

        String token = wxUtils.getToken(ydPropertites.getWx().getAppId(),ydPropertites.getWx().getAppSecret());
        if(null == token){
            log.info("token不存在");
            logPush.setPushRes("token不存在");
            rabbitTemplate.convertAndSend(QueueName.LOG_PUSH, JsonUtils.obj2Json(logPush));
            return false;
        }

        ResponseEntity<JSONObject> entity = null;
        try {
            entity = restTemplate.postForEntity(WxConstant.SEND_TEMLATE_MSG_URL, data, JSONObject.class, token);
        } catch (RestClientException e) {
            log.error("单机推送异常:{}",e.getMessage());
            logPush.setPushRes("单机推送异常:"+e.getMessage());
            rabbitTemplate.convertAndSend(QueueName.LOG_PUSH, JsonUtils.obj2Json(logPush));
            return false;
        }

        log.info("返回状态:{},返回body:{}",entity.getStatusCode(),entity.getBody());

        JSONObject body = entity.getBody();

        if(body.getInteger("errcode") == 0){
            logPush.setPushRes(body.toJSONString());
            rabbitTemplate.convertAndSend(QueueName.LOG_PUSH, JsonUtils.obj2Json(logPush));
            return true;
        }

        return false;
    }
}
