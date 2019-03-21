package com.zhidianfan.pig.log.mq;

import com.rabbitmq.client.Channel;
import com.zhidianfan.pig.common.constant.QueueName;
import com.zhidianfan.pig.common.dto.LogDevLogin;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.log.dao.service.ILogDevLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * 定时任务执行情况的日志处理
 *
 * @Author sherry
 * @Description
 * @Date Create in 2018/9/2
 * @Modified By:
 */
@Component
public class DevLoginLogListener {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ILogDevLoginService logDevLoginService;

    @RabbitHandler
    @RabbitListener(queues = QueueName.LOG_DEV_LOGIN)
    public void onMessage(Message message, Channel channel) {
        byte[] body = message.getBody();

        try {
            String logObj = new String(body, 0, body.length, "utf8");
            LogDevLogin logDevLogin = JsonUtils.jsonStr2Obj(logObj, LogDevLogin.class);

            com.zhidianfan.pig.log.dao.entity.LogDevLogin logDevLogin1 = new com.zhidianfan.pig.log.dao.entity.LogDevLogin();
            BeanUtils.copyProperties(logDevLogin, logDevLogin1);

            Date now = new Date();

            if ("in".equals(logDevLogin.getLoginType())) {
                logDevLogin1.setLoginTime(now);
            } else {
                logDevLogin1.setLogoutTime(now);
            }


            //日志入库
            logDevLoginService.insert(logDevLogin1);
        } catch (Exception e) {
            log.info("日志入库失败：\n{}", e.getMessage());
        }

    }

}
