package com.zhidianfan.pig.yd.mq;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rabbitmq.client.AMQP;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.moduler.common.constant.QueueName;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderAndroid;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderAndroidService;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderThirdService;
import com.zhidianfan.pig.yd.moduler.meituan.service.YdService;
import com.zhidianfan.pig.yd.moduler.meituan.service.rmi.PushFeign;
import com.zhidianfan.pig.yd.moduler.meituan.service.rmi.dto.JgPush;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author huzp
 * @date 2019/2/1  16:40
 * @description 美团订单自动接单第三方订单号接受
 */
@Component
@Slf4j
public class UpdateThirdOrderInfoListener {

    @Autowired
    private YdService ydService;

    @Autowired
    private IResvOrderAndroidService iResvOrderAndroidService;

    @Autowired
    private PushFeign pushFeign;

    @Autowired
    private RedisTemplate redisTemplate;


    @RabbitHandler
    @RabbitListener(queues = QueueName.TRVIPINFO_AUTOUPDATE)
    public void onMessage(String orderNoString) {

        try {
            log.info("第三方订单自动接单开始 :{} ", orderNoString);
            //等自动接单动作完成
            Thread.sleep(1000);
            ydService.updateOrderAndVipInfo(orderNoString);

            //自动接单只接单桌订单
            ResvOrderAndroid resvOrderAndroid = iResvOrderAndroidService.selectOne(new EntityWrapper<ResvOrderAndroid>()
                    .eq("third_order_no", orderNoString));

            //查询是否自动发送短信发送


            PushMes(resvOrderAndroid);
        } catch (Exception e) {
            log.info("自动接单失败：{}", e.getMessage());
        }
    }


    /**
     * 查询是否要发送短信
     */
    private void sendMes() {





    }



    private void PushMes(ResvOrderAndroid resvOrderAndroid) {
        JgPush jgPush = new JgPush();
        jgPush.setUsername("13777575146");
        //自动接单推送内容
        String orderMsg1 = JsonUtils.obj2Json(resvOrderAndroid).replaceAll("\r|\n", "").replaceAll("\\s*", "");
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("data", orderMsg1);
        jsonObject1.put("type", "10");
        jsonObject1.put("orderType", "mt");
        jgPush.setMsg(jsonObject1.toString());
        jgPush.setBusinessId(String.valueOf(resvOrderAndroid.getBusinessId()));
        jgPush.setMsgSeq(String.valueOf(getNextDateId("MT_ORDER")));
        jgPush.setType("ANDROID_PHONE");
        pushFeign.pushMsg(jgPush.getType(), jgPush.getUsername(), jgPush.getMsgSeq(), jgPush.getBusinessId(), jgPush.getMsg());
    }

    /**
     * 获取序列
     *
     * @param type
     * @return
     */
    private long getNextDateId(String type) {
        String todayStr = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd");//历史遗留Key暂不考虑，一天也就一个。
        long l2 = redisTemplate.opsForValue().increment("PUSH:" + type + ":" + todayStr, 1);
        String s1 = StringUtils.leftPad("" + l2, 7, "0");
        return Long.parseLong(todayStr + s1);
    }
}
