package com.zhidianfan.pig.push.service.impl;

import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.push.config.YdPropertites;
import com.zhidianfan.pig.push.controller.bo.ScheduleListResult;
import com.zhidianfan.pig.push.controller.bo.ScheduleResult;
import com.zhidianfan.pig.push.controller.dto.schedule.ScheduleDTO;
import com.zhidianfan.pig.push.service.ScheduleService;
import com.zhidianfan.pig.push.utils.AuthorizationUtil;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Description:
 * User: ljh
 * Date: 2018-11-04
 * Time: 19:10
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private YdPropertites ydPropertites;

    @Resource
    private AuthorizationUtil authorizationUtil;


    @Override
    public ScheduleResult createSchedule(ScheduleDTO scheduleDTO) {

        String url = getUrlString();
        ResponseEntity<ScheduleResult> responseEntity = restTemplate.postForEntity(url,
                authorizationUtil.setJGHttpEntity(scheduleDTO),
                ScheduleResult.class);
        return responseEntity.getBody();
    }

    @Override
    public ScheduleListResult schedulesList(Integer page) {
        String url = getUrlString();
        ResponseEntity<ScheduleListResult> responseEntity =
                restTemplate.exchange(url + "/page=" + page, HttpMethod.GET, authorizationUtil.setJGHttpEntity(null), ScheduleListResult.class);
        return responseEntity.getBody();
    }

    @Override
    public Tip deleteSchedule(String scheduleId) {
        String url = getUrlString();
        restTemplate.delete(url,scheduleId);
        return SuccessTip.SUCCESS_TIP;
    }

    private String getUrlString() {
        return ydPropertites.getJiguang().getScheduleHostName() + ydPropertites.getJiguang().getSchedulePath();
    }
}
