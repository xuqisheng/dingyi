package com.zhidianfan.pig.log.web;

import com.zhidianfan.pig.common.constant.*;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.log.dto.SuccessTip;
import com.zhidianfan.pig.log.dto.Tip;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/10/31
 * @Modified By:
 */
@RestController
public class TestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/test1")
    public Tip test1() {

        LogCommon logCommon = new LogCommon("pig-yd-log", this.getClass().getCanonicalName(), new Date(), LogLevel.INFO, "哇哈哈，随便做个测试");
        rabbitTemplate.convertAndSend(QueueName.LOG_COMMON, JsonUtils.obj2Json(logCommon));

        LogKeyOper logKeyOper = new LogKeyOper("pig-yd-log","测试设备","张柳宁", OperType.SELECT.ordinal(),"做个ces ");
        rabbitTemplate.convertAndSend(QueueName.LOG_KEY_OPER, JsonUtils.obj2Json(logKeyOper));

        return SuccessTip.SUCCESS_TIP;
    }

}
