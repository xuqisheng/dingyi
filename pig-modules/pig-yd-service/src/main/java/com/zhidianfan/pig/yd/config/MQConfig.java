package com.zhidianfan.pig.yd.config;

import com.zhidianfan.pig.yd.moduler.common.constant.QueueName;
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
}
