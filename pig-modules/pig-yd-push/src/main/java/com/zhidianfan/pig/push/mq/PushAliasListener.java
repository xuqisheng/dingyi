package com.zhidianfan.pig.push.mq;

import com.rabbitmq.client.Channel;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.push.constants.QueueName;
import com.zhidianfan.pig.push.dao.entity.PushMessage;
import com.zhidianfan.pig.push.service.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;


/**
 * 监听单设备推送
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/9/2
 * @Modified By:
 */
@Component
public class PushAliasListener {

    private Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    private PushService pushService;

    @RabbitHandler
    @RabbitListener(queues = QueueName.PUSH_TAG)
    public void onMessage(Message message, Channel channel) {
        byte[] body = message.getBody();

        try {
            String logObj = new String(body, 0, body.length, "utf8");
            PushMessage pushMessage = JsonUtils.jsonStr2Obj(logObj, PushMessage.class);
            if(pushMessage == null){
                return;
            }
            Set<String> alias = JsonUtils.jsonStr2Obj(pushMessage.getClient(),Set.class);
            pushService.pushToAlias(alias, pushMessage.getMessage(),pushMessage);
        } catch (Exception e) {
            log.info("日志入库失败：\n{}", e.getMessage());
        }

    }

}
