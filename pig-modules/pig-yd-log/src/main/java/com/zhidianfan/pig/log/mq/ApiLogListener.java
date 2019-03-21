package com.zhidianfan.pig.log.mq;

import com.rabbitmq.client.Channel;
import com.zhidianfan.pig.common.constant.QueueName;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.log.dao.entity.LogApi;
import com.zhidianfan.pig.log.dao.service.ILogApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 接收系统中各个模块的日志请求
 *
 * @Author sherry
 * @Description
 * @Date Create in 2018/9/2
 * @Modified By:
 */
@Component
public class ApiLogListener {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ILogApiService logService;

    @RabbitHandler
    @RabbitListener(queues = QueueName.LOG_API)
    public void onMessage(Message message, Channel channel) {
        byte[] body = message.getBody();

        try {
            String logObj = new String(body, 0, body.length, "utf8");
            log.info("\n\n\n\n\n\n\n\n\n\n\n\n消费端接收到消息 :{} \n\n\n\n\n\n\n\n\n\n\n\n", logObj);
            LogApi logApi = JsonUtils.jsonStr2Obj(logObj, LogApi.class);
            logService.insert(logApi);
        } catch (Exception e) {
            log.info("日志入库失败：\n{}", e.getMessage());
        }
    }

}
