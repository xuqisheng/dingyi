package com.zhidianfan.pig.push.mq;

import com.rabbitmq.client.Channel;
import com.zhidianfan.pig.common.constant.QueueName;
import com.zhidianfan.pig.common.dto.PushExchangeTable;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.push.constants.enmus.MonitorPushEnum;
import com.zhidianfan.pig.push.feign.BasePushFeign;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/23
 * @Modified By:
 */
@Component
@Slf4j
public class ChangeTableListener {
    @Autowired
    private BasePushFeign basePushFeign;

    @RabbitHandler
    @RabbitListener(queues = QueueName.MONITOR_HZ)
    public void onMessage(Message message, Channel channel) {
        byte[] body = message.getBody();
        String bodyStr = null;
        try {
            bodyStr = new String(body, 0, body.length, "utf8");
        } catch (UnsupportedEncodingException e) {
            log.error("mq换桌数据解析失败:{}", e.getMessage());
        }
        log.info(bodyStr);
        if (StringUtils.isEmpty(bodyStr)) {
            throw new RuntimeException("mq body数据为空");
        }
        PushExchangeTable exchangeTable = JsonUtils.jsonStr2Obj(bodyStr, PushExchangeTable.class);
        if (exchangeTable == null) {
            throw new RuntimeException("换桌数据为空");
        }
        basePushFeign.pushMsg(
                MonitorPushEnum.MONITOR_HZ.getCode()
                , exchangeTable.getUsername()
                , exchangeTable.getMsgSeq()
                , exchangeTable.getBusinessId()
                , JsonUtils.obj2Json(exchangeTable.getMsg())
        );
    }
}
