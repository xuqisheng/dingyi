package com.zhidianfan.pig.ydmonitor.config;

import com.zhidianfan.pig.common.constant.QueueName;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author bazinga
 * @Description
 * @Date Create in 2018/10/31
 * @Modified By:
 */
@Configuration
public class MQConfig {


    @Bean
    public Queue monitorNoticeQueue() {
        return new Queue(QueueName.MONITOR_NOTICE);
    }

}
