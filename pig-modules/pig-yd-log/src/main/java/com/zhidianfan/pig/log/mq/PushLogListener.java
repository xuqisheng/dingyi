package com.zhidianfan.pig.log.mq;

import com.rabbitmq.client.Channel;
import com.zhidianfan.pig.common.constant.QueueName;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.log.dao.entity.LogPush;
import com.zhidianfan.pig.log.dao.service.ILogPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 推送日志
 *
 * @Author sherry
 * @Description
 * @Date Create in 2018/11/5
 * @Modified By:
 */
@Component
public class PushLogListener {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ILogPushService logSmsService;

    @RabbitHandler
    @RabbitListener(queues = QueueName.LOG_PUSH)
    public void onMessage(Message message, Channel channel) {
        byte[] body = message.getBody();

        try {
            String logObj = new String(body, 0, body.length, "utf8");
            LogPush logTask = JsonUtils.jsonStr2Obj(logObj, LogPush.class);
            //日志入库
            logSmsService.insert(logTask);
        } catch (Exception e) {
            log.info("日志入库失败：\n{}", e.getMessage());
        }

    }

}
