package com.zhidianfan.pig.log.mq;

import com.rabbitmq.client.Channel;
import com.zhidianfan.pig.common.constant.QueueName;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.log.dao.entity.LogTask;
import com.zhidianfan.pig.log.dao.service.ILogTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 定时任务执行情况的日志处理
 * @Author sherry
 * @Description
 * @Date Create in 2018/9/2
 * @Modified By:
 */
@Component
public class TaskLogListener {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ILogTaskService logTaskService;

    @RabbitHandler
    @RabbitListener(queues = QueueName.LOG_TASK)
    public void onMessage(Message message, Channel channel) {
        byte[] body = message.getBody();

        try {
            String logObj = new String(body, 0, body.length, "utf8");
            LogTask logTask = JsonUtils.jsonStr2Obj(logObj, LogTask.class);
            //日志入库
            logTaskService.insert(logTask);
        } catch (Exception e) {
            log.info("日志入库失败：\n{}", e.getMessage());
        }

    }

}
