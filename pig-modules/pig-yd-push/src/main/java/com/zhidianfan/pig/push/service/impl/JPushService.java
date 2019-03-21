package com.zhidianfan.pig.push.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zhidianfan.pig.common.constant.LogPush;
import com.zhidianfan.pig.common.constant.QueueName;
import com.zhidianfan.pig.common.entity.SysDict;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.push.config.YdPropertites;
import com.zhidianfan.pig.push.constants.JPushConstant;
import com.zhidianfan.pig.push.dao.entity.PushDevice;
import com.zhidianfan.pig.push.dao.entity.PushMessage;
import com.zhidianfan.pig.push.feign.DictFeign;
import com.zhidianfan.pig.push.service.PushService;
import com.zhidianfan.pig.push.service.base.IPushDeviceService;
import com.zhidianfan.pig.push.service.base.IPushMessageService;
import com.zhidianfan.pig.push.utils.JPushUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/1
 * @Modified By:
 */
@Service
@Slf4j
public class JPushService implements PushService {
    @Autowired
    private DictFeign dictFeign;
    /**
     * http请求
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * rabbitmq管理
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * message数据接口
     */
    @Autowired
    private IPushMessageService messageService;
    /**
     * device数据接口
     */
    @Autowired
    private IPushDeviceService deviceService;

    /**
     * 易订属性
     */
    @Autowired
    private YdPropertites ydPropertites;

    /**
     * 重试推送PushMessage对象
     */
    private ThreadLocal<PushMessage> pushMessage = new ThreadLocal<>();

    @Override
    public boolean pushToSingle(String registrationId, String content) {
        return pushToSingle(registrationId, content, null);
    }

    @Override
    public boolean pushToSingle(String registrationId, String content, PushMessage pushMessage) {
        this.pushMessage.set(pushMessage);
        LogPush logPush = new LogPush("1", registrationId, content);
        logPush.setPushTime(new Date());

        //获取推送信息
        List<PushDevice> devices = deviceService.selectList(
                new EntityWrapper<PushDevice>().eq("registration_id", registrationId)
                        .eq("is_enable", 1)
        );
        Set<String> target = getTarget(devices);
        if (target.size() == 0) {
            logPush.setPushRes("没有发送的设备");
            rabbitTemplate.convertAndSend(QueueName.LOG_PUSH, JsonUtils.obj2Json(logPush));
            return false;
        }
        PushMessage message = getPushMessage(content, Collections.singleton(registrationId), target);
        if (message == null) {
            logPush.setPushRes("推送消息写入mysql失败");
            rabbitTemplate.convertAndSend(QueueName.LOG_PUSH, JsonUtils.obj2Json(logPush));
            return false;
        }

        //发送推送请求
        Map<String, Object> params = Maps.newHashMap();
        params.put("platform", "android");
        Map<String, Object> audience = Maps.newHashMap();
        audience.put("registration_id", Collections.singletonList(registrationId));
        params.put("audience", audience);
        Map<String, String> notification = Maps.newHashMap();
        notification.put("alert", content);
        params.put("notification", notification);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add(
                "Authorization",
                "Basic " + JPushUtils.buildAuth(
                        ydPropertites.getJiguang().getDevKey(),
                        ydPropertites.getJiguang().getDevSecret())
        );
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        HttpEntity<Map> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> result = push(httpEntity);

        //往日志发送
        logPush.setPushRes(JsonUtils.obj2Json(message));
        rabbitTemplate.convertAndSend(QueueName.LOG_PUSH, JsonUtils.obj2Json(logPush));

        if (result == null) {
            rabbitTemplate.convertAndSend(com.zhidianfan.pig.push.constants.QueueName.PUSH_SINGLE, JsonUtils.obj2Json(message));
            return false;
        }

        return true;
    }


    @Override
    public boolean pushToAll(Set<String> registrationIds, String content) {
        return pushToAll(registrationIds, content, null);
    }

    @Override
    public boolean pushToAll(Set<String> registrationIds, String content, PushMessage pushMessage) {
        this.pushMessage.set(pushMessage);
        LogPush logPush = new LogPush("1", registrationIds.toString(), content);
        logPush.setPushTime(new Date());

        //获取推送信息
        List<PushDevice> devices = deviceService.selectList(
                new EntityWrapper<PushDevice>().in("registration_id", registrationIds)
                        .eq("is_enable", 1)
        );
        Set<String> target = getTarget(devices);
        if (target.size() == 0) {
            logPush.setPushRes("没有发送的设备");
            rabbitTemplate.convertAndSend(QueueName.LOG_PUSH, JsonUtils.obj2Json(logPush));
            return false;
        }
        PushMessage message = getPushMessage(content, registrationIds, target);
        if (message == null) {
            logPush.setPushRes("推送消息写入mysql失败");
            rabbitTemplate.convertAndSend(QueueName.LOG_PUSH, JsonUtils.obj2Json(logPush));
            return false;
        }

        //发送推送请求
        Map<String, Object> params = Maps.newHashMap();
        params.put("platform", Collections.singletonList("android"));
        Map<String, Object> audience = Maps.newHashMap();
        audience.put("registration_id", registrationIds);
        params.put("audience", audience);
        Map<String, String> notification = Maps.newHashMap();
        notification.put("alert", content);
        params.put("notification", notification);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authorization", "Basic " + JPushUtils.buildAuth(ydPropertites.getJiguang().getDevKey(), ydPropertites.getJiguang().getDevSecret()));
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        HttpEntity<Map> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> result = push(httpEntity);

        //往日志发送
        logPush.setPushRes(JsonUtils.obj2Json(message));
        rabbitTemplate.convertAndSend(QueueName.LOG_PUSH, JsonUtils.obj2Json(logPush));

        if (result == null) {
            rabbitTemplate.convertAndSend(com.zhidianfan.pig.push.constants.QueueName.PUSH_SINGLE, JsonUtils.obj2Json(message));
            return false;
        }

        return true;
    }

    @Override
    public boolean pushToTag(Set<String> tags, String content) {
        return pushToTag(tags, content, null);
    }

    @Override
    public boolean pushToTag(Set<String> tags, String content, PushMessage pushMessage) {
        this.pushMessage.set(pushMessage);
        LogPush logPush = new LogPush("1", tags.toString(), content);
        logPush.setPushTime(new Date());

        //获取推送信息
        List<PushDevice> devices = deviceService.selectList(
                new EntityWrapper<PushDevice>().in("tag", tags)
                        .eq("is_enable", 1)
        );
        Set<String> target = getTarget(devices);
        if (target.size() == 0) {
            logPush.setPushRes("没有发送的设备");
            rabbitTemplate.convertAndSend(QueueName.LOG_PUSH, JsonUtils.obj2Json(logPush));
            return false;
        }
        PushMessage message = getPushMessage(content, tags, target);
        if (message == null) {
            logPush.setPushRes("推送消息写入mysql失败");
            rabbitTemplate.convertAndSend(QueueName.LOG_PUSH, JsonUtils.obj2Json(logPush));
            return false;
        }

        //发送推送请求
        Map<String, Object> params = Maps.newHashMap();
        params.put("platform", Collections.singletonList("android"));
        Map<String, Object> audience = Maps.newHashMap();
        audience.put("tag", tags);
        params.put("audience", audience);
        Map<String, String> notification = Maps.newHashMap();
        notification.put("alert", content);
        params.put("notification", notification);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authorization", "Basic " + JPushUtils.buildAuth(ydPropertites.getJiguang().getDevKey(), ydPropertites.getJiguang().getDevSecret()));
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        HttpEntity<Map> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> result = push(httpEntity);

        //往日志发送
        logPush.setPushRes(JsonUtils.obj2Json(message));
        rabbitTemplate.convertAndSend(QueueName.LOG_PUSH, JsonUtils.obj2Json(logPush));

        if (result == null) {
            rabbitTemplate.convertAndSend(com.zhidianfan.pig.push.constants.QueueName.PUSH_TAG, JsonUtils.obj2Json(message));
            return false;
        }

        return true;

    }


    @Override
    public boolean pushToAlias(Set<String> aliases, String content) {
        return pushToAlias(aliases, content, null);
    }

    @Override
    public boolean pushToAlias(Set<String> aliases, String content, PushMessage pushMessage) {
        this.pushMessage.set(pushMessage);
        LogPush logPush = new LogPush("2", aliases.toString(), content);
        logPush.setPushTime(new Date());

        //获取推送信息
        List<PushDevice> devices = deviceService.selectList(
                new EntityWrapper<PushDevice>().in("alias", aliases)
                        .eq("is_enable", 1)
        );
        Set<String> target = getTarget(devices);
        if (target.size() == 0) {
            logPush.setPushRes("没有发送的设备");
            rabbitTemplate.convertAndSend(QueueName.LOG_PUSH, JsonUtils.obj2Json(logPush));
            return false;
        }
        PushMessage message = getPushMessage(content, aliases, target);
        if (message == null) {
            logPush.setPushRes("推送消息写入mysql失败");
            rabbitTemplate.convertAndSend(QueueName.LOG_PUSH, JsonUtils.obj2Json(logPush));
            return false;
        }

        //发送推送请求
        Map<String, Object> params = Maps.newHashMap();
        params.put("platform", Collections.singletonList("android"));
        Map<String, Object> audience = Maps.newHashMap();
        audience.put("alias", aliases);
        params.put("audience", audience);
        Map<String, String> notification = Maps.newHashMap();
        notification.put("alert", content);
        params.put("notification", notification);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authorization", "Basic " + JPushUtils.buildAuth(ydPropertites.getJiguang().getDevKey(), ydPropertites.getJiguang().getDevSecret()));
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        HttpEntity<Map> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> result = push(httpEntity);

        //往日志发送
        logPush.setPushRes(JsonUtils.obj2Json(message));
        rabbitTemplate.convertAndSend(QueueName.LOG_PUSH, JsonUtils.obj2Json(logPush));

        if (result == null) {
            rabbitTemplate.convertAndSend(com.zhidianfan.pig.push.constants.QueueName.PUSH_ALIAS, JsonUtils.obj2Json(message));
            return false;
        }
        return true;
    }

    private ResponseEntity<String> push(HttpEntity<Map> httpEntity) {
        ResponseEntity<String> result = null;
        try {
            result = restTemplate.postForEntity(JPushConstant.PUSH_URL, httpEntity, String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error(e.getMessage());
            log.info(e.getResponseBodyAsString());
        } catch (RestClientException e) {
            log.error(e.getMessage());
        }
        return result;
    }

    private PushMessage getPushMessage(String content, Set<String> clients, Set<String> target) {
        PushMessage message = new PushMessage();
        if (this.pushMessage.get() == null) {
            message.setMessage(content);
            message.setTotalNum(1);
            message.setClient(JsonUtils.obj2Json(clients));
            message.setTarget(JsonUtils.obj2Json(target));
            boolean insert = messageService.insert(message);
            if (!insert) {
                return null;
            }
        } else {
            message = this.pushMessage.get();
            int retry = message.getRetry() + 1;
            List<SysDict> dicts = dictFeign.findDictByType("push_retry_times");
            int retryTimes = ydPropertites.getRetry();
            if (dicts != null && dicts.size() > 0) {
                retryTimes = Integer.valueOf(dicts.get(0).getValue());
            }
            if (retry > retryTimes) {
                log.info("当前重试:{},总共重试:{}", retry, retryTimes);
                return null;
            }
            message.setRetry(retry);
            messageService.updateById(message);
        }
        return message;
    }

    private Set<String> getTarget(List<PushDevice> devices) {
        Set<String> target = Sets.newHashSet();
        devices.forEach(device -> {
            target.add(device.getRegistrationId());
        });

        return target;
    }


}
