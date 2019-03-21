package com.zhidianfan.pig.yd.moduler.resv.service;

import com.alibaba.fastjson.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;
import com.zhidianfan.pig.yd.moduler.meituan.service.rmi.PushFeign;
import com.zhidianfan.pig.yd.moduler.meituan.service.rmi.dto.JgPush;
import com.zhidianfan.pig.yd.moduler.resv.constants.MyWebSocketClient;
import com.zhidianfan.pig.yd.moduler.resv.dto.WSResultDTO;
import com.zhidianfan.pig.yd.moduler.resv.enums.DeviceType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.zhidianfan.pig.yd.moduler.resv.enums.DeviceType.ANDROIDPHONE;
import static com.zhidianfan.pig.yd.moduler.resv.enums.DeviceType.SMALLAPP;


/**
 * @Author: huzp
 * @Date: 2018/9/20 14:06
 * @desc: 同步安卓电话机与小程序的推送
 */

@Service
@Slf4j
public class SyncPushService {



    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PushFeign pushFeign;

    @Autowired
    RedisTemplate redisTemplate;


    /**
     * 安卓电话机小程序订单状态改变互相推送
     * @param deviceType
     * @param businessId
     */
    public void SyncOrderStatus(String deviceType, Integer businessId) {

        log.info("传入参数: deviceType: " + deviceType + ",businessId: " + businessId);

        //如果是安卓电话机做的改变,则推送小程序端
        DeviceType type = deviceType.equals(ANDROIDPHONE.code) ? SMALLAPP : ANDROIDPHONE;

        switch (type) {
            case SMALLAPP:
                AndroidOrderChange2SmallApp(SMALLAPP, businessId);
                break;

            case ANDROIDPHONE:
                SmallAppOrderChange2Android(ANDROIDPHONE , businessId);
                break;

            default:
                log.info("推送设备类型不存在");
                break;
        }
    }


    /**
     * 安卓电话机订单推送小程序
     * @param type 推送设备类型
     * @param businessId 酒店id
     */
    private void AndroidOrderChange2SmallApp(DeviceType type, Integer businessId) {
        try {



            WSResultDTO onlineSession = pushFeign.getOnlineSession(businessId);

            if (onlineSession == null  || onlineSession.getCode() !=200) {
                log.info("getOnlineSession请求失败");
                return;
            }

            List<WSResultDTO.BodyBean> result = onlineSession.getBody();


            List<String> openids = new ArrayList<>();
            for (WSResultDTO.BodyBean bodyBean : result) {
                //筛选出小程序端的sessionid
                if (bodyBean.getDeviceType().equals(type.code)) {
                    openids.add(bodyBean.getOpenid());
                }
            }

            //构筑需要的发送的信息
            JSONObject jsonObject = new JSONObject();
            //推送的设备
            jsonObject.put("openid", openids);

            JSONObject mes = new JSONObject();
            //推送消息的消息体
            mes.put("type", "1");
            List<Map<String, Object>> orders = new ArrayList<>();
            mes.put("data", JSONUtil.toJsonStr(orders));
            jsonObject.put("message", mes.toString());

            WebSocketClient client = MyWebSocketClient.getInstance();
//            WebSocketClient client = null;
            client.connect();


            for (int i = 0; i < 10; i++) {
                if (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                    TimeUnit.SECONDS.sleep(1);
                } else {
                    client.send(jsonObject.toJSONString());
                    break;
                }
            }

            client.close();
            log.info("同步推送数据完成: " + jsonObject.toJSONString());


        }  catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 小程序端改变推送消息给安卓电话机
     * @param deviceType  推送的设备类型 : ANDROID_PHONE
     * @param businessId
     */
    private void SmallAppOrderChange2Android(DeviceType deviceType , Integer businessId){
        JgPush jgPush = new JgPush();
        //如果是安卓电话机做的改变,则推送小程序端


        JSONObject jsonObject = new JSONObject();

        //消息类型为1
        List<Map<String,Object>> orders = new ArrayList<>();
        jsonObject.put("type", "1");
        jsonObject.put("data", JSONUtil.toJsonStr(orders));

        //推送安卓电话机
        jgPush.setType(deviceType.desc);
        jgPush.setUsername("13777575146");
        jgPush.setMsgSeq(String.valueOf(getNextDateId("SYNC_ORDER")));
        jgPush.setBusinessId(businessId.toString());
        jgPush.setMsg(jsonObject.toString());


        log.info("同步推送数据Jgpush: "+ jgPush.toString());

        pushFeign.pushMsg(jgPush.getType(),jgPush.getUsername(),jgPush.getMsgSeq(),jgPush.getBusinessId(),jgPush.getMsg());
        log.info("小程序安卓电话机同步推送完成");

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

