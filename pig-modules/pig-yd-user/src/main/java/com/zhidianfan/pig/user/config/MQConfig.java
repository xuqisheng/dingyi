package com.zhidianfan.pig.user.config;

import com.zhidianfan.pig.common.constant.QueueName;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/10/26
 * @Modified By:
 */
@Configuration
public class MQConfig {


    @Bean
    public Queue logTaskQueue() {
        return new Queue(QueueName.LOG_TASK);
    }

    @Bean
    public Queue logApiQueue() {
        return new Queue(QueueName.LOG_API);
    }

}
