package com.zhidianfan.pig.ydmonitor.mq;

import com.rabbitmq.client.Channel;
import com.zhidianfan.pig.common.constant.QueueName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * 接收系统中各个模块的日志请求
 * @Author bazinga
 * @Description
 * @Date Create in 2018/10/31
 * @Modified By:
 */
@Component
public class MonitorNoticeListener {

    private Logger log = LoggerFactory.getLogger(getClass());


    @RabbitHandler
    @RabbitListener(queues = QueueName.MONITOR_NOTICE)
    public void onMessage(Message message, Channel channel) {
        byte[] body = message.getBody();

        try {
            String noticeObj = new String(body, 0, body.length, "utf8");
            log.info("消费端接收到监控消息：\n{}", noticeObj);
//            LogNotice logNotice = JsonUtils.jsonStr2Obj(noticeObj, LogNotice.class);
//            logNoticeService.insert(logNotice);

            log.info("执行发送动作：\n{}", noticeObj);
        } catch (Exception e) {
            log.info("日志入库失败：\n{}", e.getMessage());
        }

    }

}
