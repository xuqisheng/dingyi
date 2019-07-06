package com.zhidianfan.pig.yd.mq;

import com.alibaba.fastjson.JSON;
import com.zhidianfan.pig.yd.moduler.common.constant.QueueName;
import com.zhidianfan.pig.yd.moduler.resv.dto.CustomerValueChangeFieldDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author sjl
 * 2019-06-10 11:36
 */
@Component
public class MQSender {

    private static final Logger log = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMQ(CustomerValueChangeFieldDTO customerValueChangeFieldDTO) {
        log.info("客户价值实时更新字段,更新内容:[{}]", customerValueChangeFieldDTO);
        String content = JSON.toJSONString(customerValueChangeFieldDTO);
        rabbitTemplate.convertAndSend(QueueName.CUSTOMER_VALUE_DIRECT_EXCHANGE, QueueName.CUSTOMER_VALUE_ROUTINGKEY, content);
    }
}
