package com.zhidianfan.pig.log.config;

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

    @Bean
    public Queue logCommonQueue() {
        return new Queue(QueueName.LOG_COMMON);
    }

    @Bean
    public Queue logKeyOperQueue() {
        return new Queue(QueueName.LOG_KEY_OPER);
    }

    @Bean
    public Queue logSmsQueue() {
        return new Queue(QueueName.LOG_SMS);
    }

    @Bean
    public Queue logPushQueue() {
        return new Queue(QueueName.LOG_PUSH);
    }

    @Bean
    public Queue logDevLoginQueue() {
        return new Queue(QueueName.LOG_DEV_LOGIN);
    }
}
