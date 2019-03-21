package com.zhidianfan.pig.yd.moduler.push.ws;

import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.config.SpringApplicationContext;
import com.zhidianfan.pig.yd.moduler.push.ws.dto.HttpCommonRes;
import com.zhidianfan.pig.yd.moduler.push.ws.exception.WSCloseException;
import com.zhidianfan.pig.yd.moduler.push.ws.service.WSMessagePushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author sjl
 * 2019-02-28 14:24
 * websocket 方式消息推送
 */
@ServerEndpoint(value = "/wspush/{hotelId}/{deviceType}/{openid}", configurator = SpringApplicationContext.class)
@Component
@Slf4j
public class WSMessagePush {

    /**
     * 维护的注册信息表，所有注册信息维护到该 map 中
     */
    private static Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * 推送服务类
     */
    @Autowired
    private WSMessagePushService messagePushService;

    /**
     * 连接
     *
     * @param session    会话信息
     * @param hotelId    酒店 id
     * @param deviceType 设备类型
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam("hotelId") Integer hotelId,
                       @PathParam("deviceType") String deviceType,
                       @PathParam("openid") String openid) {
        log.info("连接进入-hotelID:{}, deviceTypeId:{}, openid:{}", hotelId, deviceType, openid);
        // 连接上限检查
        if (messagePushService.checkConnectMax(sessionMap.size())) {
            messagePushService.sendResponseMessage(session, JsonUtils.obj2Json(HttpCommonRes.ERROR));
            log.error("连接数到达上限, sessionId:{}", getSessionId(session));
            return;
        }

        String sessionId = getSessionId(session);
        log.info("本次会话 sessionId:{}", sessionId);
        //保存连接信息到数据库中
        boolean b = messagePushService.saveClientInfo(sessionId, hotelId, deviceType, openid);
        // 建立连接成功，连接信息维护进 sessionMap 中
        sessionMap.put(sessionId, session);
        if (b) {
            messagePushService.sendResponseMessage(session, JsonUtils.obj2Json(HttpCommonRes.SUCCESS));
            log.info("连接建立成功,sessionId:{}", sessionId);
        } else {
            messagePushService.sendResponseMessage(session, JsonUtils.obj2Json(HttpCommonRes.ERROR));
            log.info("连接建立不成功, sessionId:{}", openid);
        }
    }

    /**
     * 生成本次会话的 sessionId
     *
     * @param session 连接会话
     * @return 生成的 sessionId
     */
    private String getSessionId(Session session) {
        return session.getId();
    }


    /**
     * 发送消息
     *
     * @param content 发送的内容
     */
    @OnMessage
    public void onSendMessage(String content, Session sendSession) {
        log.info("发送消息,消息内容：{}", content);
        try {
            // openid 列表
            List<String> receiveOpenidList = messagePushService.parseOpenid(content);
            // 查出所有的 sessionId
            List<String> receiveSessionIdList = messagePushService.getSessiondIdList(receiveOpenidList);
            List<Session> receiveSessionList = getReceiveSessionList(sendSession, receiveSessionIdList);
            if (receiveSessionList == null) {
                return;
            }
            String sendSessionId = getSessionId(sendSession);
            messagePushService.sendMessage(sendSession, receiveSessionList, content);
            log.info("消息发送完成");
        } catch (Exception e) {
            log.error("发送消息发生异常", e);
            messagePushService.sendResponseMessage(sendSession, JsonUtils.obj2Json(HttpCommonRes.ERROR));
        }

    }

    /**
     * 得到接收消息的 session 列表
     *
     * @param sendSession          发送者
     * @param receiveSessionIdList 接收者 session 列表
     * @return List<Session> 列表
     */
    private List<Session> getReceiveSessionList(Session sendSession, List<String> receiveSessionIdList) {
        List<Session> receiveSessionList = new ArrayList<>();
        for (String receiveSessionId : receiveSessionIdList) {
            Session receiveSession = sessionMap.get(receiveSessionId);
            if (receiveSession != null && receiveSession != sendSession) {
                receiveSessionList.add(receiveSession);
            }
        }
        return receiveSessionList;
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose(Session session) {
        try {
            log.info("连接关闭,sessionID:{}", getSessionId(session));
            messagePushService.closeConnect(getSessionId(session));
            log.info("连接关闭完成");
        } catch (Exception e) {
            throw new WSCloseException("连接关闭异常", e);
        }
    }

    /**
     * 连接异常或发生错误
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        if (throwable instanceof WSCloseException) {
            // 进入关闭异常处理
            log.info("连接关闭发生异常", throwable);
            messagePushService.errorCloseException(getSessionId(session));
        } else {
            // 其他异常处理
            log.error("wspush 异常", throwable);
            messagePushService.otherException(getSessionId(session));
        }
        log.info("连接关闭异常处理完毕");
    }

    /**
     * 获取连接的 map 信息
     */
    public static Map<String, Session> getSessionMap() {
        return sessionMap;
    }
}
