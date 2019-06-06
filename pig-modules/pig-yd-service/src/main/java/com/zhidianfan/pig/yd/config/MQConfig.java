package com.zhidianfan.pig.yd.config;

import com.zhidianfan.pig.yd.moduler.common.constant.QueueName;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/27
 * @Modified By:
 */
@Configuration
public class MQConfig {
    @Bean
    public Queue queue(){
        return new Queue(QueueName.MARKETING_SMS);
    }

    /**
     * 推送自动更新的第三方订单号
     */
    @Bean
    public Queue pushTRNo(){
        return new Queue(QueueName.TRVIPINFO_AUTOUPDATE);
    }

    /**
     * 客户价值交换器
     */
    @Bean
    public DirectExchange customerValueDirectExchange() {
        return new DirectExchange(QueueName.CUSTOMER_VALUE_DIRECT_EXCHANGE);
    }
    /**
     * 客户价值队列
     */
    @Bean
    public Queue customerValueQueue() {
        return new Queue(QueueName.CUSTOMER_VALUE_QUEUE);
    }

    /**
     * 队列，交换器绑定
     * @param customerValueDirectExchange 交换器
     * @param customerValueQueue 绑定队列
     */
    @Bean
    public Binding bindingSmsMarking(DirectExchange customerValueDirectExchange, Queue customerValueQueue) {
        return BindingBuilder.bind(customerValueQueue).to(customerValueDirectExchange).with(QueueName.CUSTOMER_VALUE_ROUTINGKEY);
    }

}
