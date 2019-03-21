package com.zhidianfan.pig.yd.moduler.common.web;

import com.zhidianfan.pig.common.constant.QueueName;
import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.common.dto.LogDevLogin;
import com.zhidianfan.pig.common.util.JsonUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/10/31
 * @Modified By:
 */
@RestController
@RequestMapping("/log")
public class LogControllerV2 {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 设备登陆登出日志
     *
     * @return
     */
    @PostMapping("/dev/login")
    public Tip loginDevLogin(@Valid @RequestBody LogDevLogin logDevLogin) {

        rabbitTemplate.convertAndSend(QueueName.LOG_DEV_LOGIN, JsonUtils.obj2Json(logDevLogin));

        return SuccessTip.SUCCESS_TIP;
    }

}
