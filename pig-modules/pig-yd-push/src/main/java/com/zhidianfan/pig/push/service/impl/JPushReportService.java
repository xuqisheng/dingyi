package com.zhidianfan.pig.push.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.push.config.JGProperties;
import com.zhidianfan.pig.push.constants.JPushConstant;
import com.zhidianfan.pig.push.dao.entity.PushMessage;
import com.zhidianfan.pig.push.service.ReportService;
import com.zhidianfan.pig.push.service.base.IPushMessageService;
import com.zhidianfan.pig.push.utils.JPushUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/4
 * @Modified By:
 */
@Service
@Slf4j
public class JPushReportService implements ReportService {
    @Autowired
    private JGProperties jgProperties;
    @Autowired
    private IPushMessageService messageService;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean checkStatus(String msgId) {
        PushMessage pushMessage = messageService.selectOne(new EntityWrapper<PushMessage>().eq("push_id", msgId));
        if (pushMessage == null) {
            log.info("msg_id:{}消息不存在", msgId);
            return false;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authorization", "Basic " + JPushUtils.buildAuth(jgProperties.getDevKey(), jgProperties.getDevSecret()));
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        Map<String, Object> params = Maps.newHashMap();
        params.put("msg_id", msgId);
        params.put("registration_ids", JsonUtils.jsonStr2Obj(pushMessage.getTarget(), List.class));
        String date = DateFormatUtils.format(pushMessage.getCreateTime(), "yyyy-mm-dd");
        params.put("date",date);
        HttpEntity<Map> httpEntity = new HttpEntity<>(params, headers);

        ResponseEntity<String> result = null;
        try {
            result = restTemplate.postForEntity(JPushConstant.PUSH_CHECK_URL, httpEntity, String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error(e.getMessage());
            log.info(e.getResponseBodyAsString());
        } catch (RestClientException e) {
            log.error(e.getMessage());
        }
        if (result == null) {
            return false;
        }
        Map<String, Map<String, Integer>> lists = JsonUtils.jsonStr2Obj(result.getBody(), Map.class);
        AtomicReference<Integer> totalNum = new AtomicReference<>(0);
        AtomicReference<Integer> sendNum = new AtomicReference<>(0);
        lists.forEach((key, map) -> {
            Integer status = map.get("status");
            if (status != null && status == 0) {
                sendNum.set(sendNum.get() + 1);
            }
            if (status != null && (status == 0 || status == 1)) {
                totalNum.set(totalNum.get() + 1);
            }
        });

        pushMessage.setTotalNum(totalNum.get());
        pushMessage.setSendNum(sendNum.get());
        if (totalNum.get().equals(sendNum.get())) {
            pushMessage.setSendStatus(1);
        } else if (sendNum.get() == 0) {
            pushMessage.setSendStatus(0);
        } else {
            pushMessage.setSendStatus(2);
        }

        messageService.updateById(pushMessage);

        return true;
    }
}
