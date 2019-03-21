package com.zhidianfan.pig.yd.moduler.push.service.ext;

import com.zhidianfan.pig.common.constant.LogPush;
import com.zhidianfan.pig.common.constant.QueueName;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.config.prop.YdPropertites;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.push.dto.NodePush;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 *
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/21
 * @Modified By:
 */
@Component
public class PushSupport {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private YdPropertites ydPropertites;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * nodejs批量推送
     *
     * @param regIds
     * @param msg
     * @return
     */
    public Tip pushMsgByRedIdsWithNode(List<String> regIds, String msg) {

        LogPush logPush = new LogPush("0",regIds.toString(),msg);

        Map<String, Object> m1 = new HashMap<>();
        try {
            m1 = JsonUtils.jsonStr2Obj(msg, Map.class);
        } catch (Exception e) {
            log.info(msg + "\njson类型转化异常");
        }
        NodePush nodePush = createNodePush(regIds, msg, m1.get("code") == null || !StringUtils.isNumeric(String.valueOf(m1.get("code"))) ? 200 : (int) m1.get("code"));//待发送的消息

        CountDownLatch countDownLatch = new CountDownLatch(1);
        final Map<Boolean, String> resMap1 = new HashMap<>();

        String param = JsonUtils.obj2Json(nodePush);
        log.info("待推送数据：{}", param);

        String url = ydPropertites.getNode().getUrl();
        Tip tip = SuccessTip.SUCCESS_TIP;
        Socket socket1 = null;
        try {
            final Socket socket = IO.socket(url);
            socket1 = socket;
            log.info("创建Socket.IO连接:{}", socket);
            socket.on(Socket.EVENT_CONNECT, args -> {

            }).on("conn", args -> {
                log.info("已连接:{}", args);
                socket.emit("pushMessage", param);
                log.info("等待本次推送结果");
            }).on(Socket.EVENT_DISCONNECT, args -> {

                log.info("已断开{}", args);

            }).on("pushReceive", args1 -> {

                log.info("调用结果：\n{}", args1[0]);//{"msg":"Ef-lJrMN8VQn7C1pAAAG该用户未登陆","code":500}
                logPush.setPushRes(args1[0].toString());
                Map<String, Object> resMap = JsonUtils.jsonStr2Obj(args1[0].toString(), Map.class);
                int count = (int) resMap.get("code");
                if (count != 200) {
                    resMap1.put(false, (String) resMap.get("msg"));
                }
                countDownLatch.countDown();
            }).on("errorMsg", args -> {
                log.error("errorMsg:{}", args);
                resMap1.put(false, ""+args[0].toString());
                countDownLatch.countDown();
            });
            socket.connect();


            boolean f1 = countDownLatch.await(ydPropertites.getNode().getTimeout(), TimeUnit.SECONDS);
            log.info("本次推送结束");

            if (!f1) {
                log.error("=======================>>>等待推送结果 pushReceive 超时");
                logPush.setPushRes("=======================>>>等待推送结果 pushReceive 超时");
                rabbitTemplate.convertAndSend(QueueName.LOG_PUSH,JsonUtils.obj2Json(logPush));
                return ErrorTip.ERROR_TIP_TIMEOUT;
            }
            if (resMap1.get(false) != null) {
                tip = new ErrorTip(500, resMap1.get(false));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            tip = new ErrorTip(500, e.getMessage());
        } finally {
            if (socket1 != null) {
                socket1.disconnect();
            }
        }
        rabbitTemplate.convertAndSend(QueueName.LOG_PUSH,JsonUtils.obj2Json(logPush));
        return tip;
    }

    private NodePush createNodePush(List<String> regIds, String msg, int code) {
        NodePush nodePush = new NodePush();
        nodePush.setCode(code);
        nodePush.setRegistrations(regIds);
        nodePush.setMessage(msg);
        return nodePush;
    }

    /**
     * 往指定多台设备推送
     *
     * @param regIds
     * @param msg    推送字符串必须是JSON对象，否则程序报错
     * @return
     */
    public Tip pushMsgByRegIds(List<String> regIds, String msg) {
        Tip tip = SuccessTip.SUCCESS_TIP;
        String appKey = ydPropertites.getJg().getMap().get("pad").get("appKey");
        String masterSecret = ydPropertites.getJg().getMap().get("pad").get("masterSecret");
        String base64 = Base64.getEncoder().encodeToString((appKey + ":" + masterSecret).getBytes(Charset.forName("utf8")));
        String authorization = "Basic " + base64;
        String url = ydPropertites.getJg().getMap().get("pad").get("url");//POST方法
        String param = createParams(regIds, msg);

        log.info("准备推送消息：{}", param);

        //设置头信息
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", authorization);
        httpHeaders.add("Content-Type", "application/json;charset=UTF-8");


        HttpEntity httpEntity = new HttpEntity(param, httpHeaders);


        ResponseEntity<String> responseEntity = null;
        try {

            responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        } catch (Exception e) {
            log.info("推送失败");
            log.error(e.getMessage(), e);
            tip = new ErrorTip(500, e.getMessage());
        }

        if (responseEntity != null) {
            if (responseEntity.getStatusCode().value() == 200) {
                return tip;
            } else {
                tip = new ErrorTip(500, responseEntity.getBody());
            }
        }

        return tip;
    }

    /**
     * 往指定设备推送
     *
     * @param regId
     * @param msg
     * @return
     */
    public Tip pushMsgByRegId(String regId, String msg) {
        Tip tip = SuccessTip.SUCCESS_TIP;
        String appKey = ydPropertites.getJg().getMap().get("pad").get("appKey");
        String masterSecret = ydPropertites.getJg().getMap().get("pad").get("masterSecret");
        String base64 = Base64.getEncoder().encodeToString((appKey + ":" + masterSecret).getBytes(Charset.forName("utf8")));
        String authorization = "Basic " + base64;
        String url = ydPropertites.getJg().getMap().get("pad").get("url");//POST方法
        String param = createParams(regId, msg);

        log.info("准备推送消息：{}", param);

        //设置头信息
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", authorization);
        httpHeaders.add("Content-Type", "application/json;charset=UTF-8");


        HttpEntity httpEntity = new HttpEntity(param, httpHeaders);


        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

        if (responseEntity.getStatusCode().value() == 200) {
            return tip;
        } else {
            tip = new ErrorTip(500, responseEntity.getBody());
        }
        return tip;
    }

    private String createParams(List<String> regIds, String msg) {
        return "{\"platform\" : \"android\",\"audience\" : {\"registration_id\" : " + JsonUtils.obj2Json(regIds) + "},\"message\" : {\"msg_content\":" + msg + "}}";
    }

    private String createParams(String regId, String msg) {
        return "{\"platform\" : \"android\",\"audience\" : {\"registration_id\" : [\"" + regId + "\"]},\"message\" : {\"msg_content\":" + msg + "}}";
    }

}
