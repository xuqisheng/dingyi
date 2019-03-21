package com.zhidianfan.pig.push.config;

import com.zhidianfan.pig.push.constants.QueueName;
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
    public Queue pushSingleQueue() {
        return new Queue(QueueName.PUSH_SINGLE);
    }

    @Bean
    public Queue pushBatchQueue(){
        return new Queue(QueueName.PUSH_BATCH);
    }


    @Bean
    public Queue pushTagQueue(){
        return new Queue(QueueName.PUSH_TAG);
    }

    @Bean
    public Queue pushAliasQueue(){
        return new Queue(QueueName.PUSH_ALIAS);
    }

    @Bean
    public Queue pushExchangeTable(){
        return new Queue(com.zhidianfan.pig.common.constant.QueueName.MONITOR_HZ);
    }

    @Bean
    public Queue pushNotify(){
        return new Queue(com.zhidianfan.pig.common.constant.QueueName.MONITOR_TZ);
    }

    @Bean
    public Queue pushReport(){
        return new Queue(com.zhidianfan.pig.common.constant.QueueName.MONITOR_BB);
    }

}
